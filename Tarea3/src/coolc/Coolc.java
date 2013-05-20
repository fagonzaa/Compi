package coolc;

import coolc.parser.*;
import coolc.ast.*;
import coolc.semantic.Semantic;
import coolc.semantic.SemanticPrint;
import coolc.symtable.*;

import java.io.*;
import java.util.*;


import com.sun.org.apache.xerces.internal.util.SymbolTable;

public class Coolc {

    public static void printInstructions() {
        System.out.println(
            "Usage Coolc <action> file1 file2 ... filen\n" +
            "Available actions: \n" +
            "scan - scans each files and outputs a token list\n" +
            "parse - parses each file and outputs the syntax tree\n"
            );
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {

        if (args.length < 2) {
            printInstructions();
            return;
        }


        String action = args[0];

        List<String> files = Arrays.asList(args).subList(1, args.length);

        switch(action) {

            case "scan":
                scan(files);
                break;

            case "parse":
                parse(files);
                break;
                
            case "symtable":
            	symtable(files);
                break;
                
            case "semantic":
            	semantic(files);
                break;

            default:
                printInstructions();
                return;
        }


    }

    private static void scan(List<String> files) throws FileNotFoundException, IOException {

        for(String filename: files) {
            File f = new File(filename);
            Reader r = new FileReader(f);
            Lexer lex = new Lexer(r);
            Random rand = new Random();

            for(int token = lex.yylex(); token != Parser.EOF; token = lex.yylex()) {

                Position pos = lex.getStartPos();

                switch(token) {

                    case Parser.ID:
                        System.out.printf("%s:%2d:%2d  Id<%s>\n", f.getPath(), pos.line, pos.column, lex.getLVal());
                        break;

                    case Parser.INTEGER:
                        System.out.printf("%s:%2d:%2d  Int<%s>\n", f.getPath(), pos.line, pos.column, lex.getLVal());
                        break;
                        

                    case Parser.BOOL:
                        System.out.printf("%s:%2d:%2d  Boolean<%s>\n", f.getPath(), pos.line, pos.column, (boolean)lex.getLVal() ? "True" : "False");
                        break;
                        
                    case Parser.TYPE:
                        System.out.printf("%s:%2d:%2d  Type<%s>\n", f.getPath(), pos.line, pos.column, lex.getLVal());
                        break;

                    case Parser.STRING:
                        String strval =((String)lex.getLVal()).replace("\n","").replace("\b", "").replace("\t", "").replace(">", "");
                        strval = strval.substring(0, Math.min(strval.length(), 20));

                        System.out.printf("%s:%2d:%2d  String<%s>\n", f.getPath(), pos.line, pos.column, strval);
                        break;

                    default:
                        System.out.printf("%s:%2d:%2d  %s\n", f.getPath(), pos.line, pos.column, Parser.getTokenName(token));
                        break;
                }

            }
        }
    }

    private static void parse(List<String> files) throws FileNotFoundException, IOException { 



        for(String filename: files) {
            File f = new File(filename);
            Reader r = new FileReader(f);
            Lexer lex = new Lexer(r);

            Parser p = new Parser(lex);

            System.err.printf("parsing %s\n", filename);

            p.parse();

            print(p.getRoot());
        }

    }
    
    private static void symtable(List<String> files) throws FileNotFoundException, IOException { 

        for(String filename: files) {
            System.err.printf("%s\n", filename);
            File f = new File(filename);
            Reader r = new FileReader(f);
            Lexer lex = new Lexer(r);
            Parser p = new Parser(lex);
            p.parse();
            
            SymTable table = new SymTable(p.getRoot(), filename);
            
            table.print();
            System.err.printf("-------------------------------------\n");
        }

    }
    
    private static void semantic(List<String> files) throws FileNotFoundException, IOException { 

        for(String filename: files) {
            System.err.printf("%s\n", filename);
            File f = new File(filename);
            Reader r = new FileReader(f);
            Lexer lex = new Lexer(r);
            Parser p = new Parser(lex);
            p.parse();
            
            SymTable table = new SymTable(p.getRoot(), filename);
            
            Semantic semantic = new Semantic(table, filename);
            semantic.print(p.getRoot());
            
            SemanticPrint semprint = new SemanticPrint(table, filename);
            semprint.print(p.getRoot());
            System.err.printf("-------------------------------------\n");
        }

    }

    private static void print(Program root) {
        System.out.println("program");
        for(ClassDef c: root) {            
            print(c);
        }
    }

    private static void print(ClassDef c) {
        printIndent(1);
        System.out.printf("class %s", c.getType());
        if( c.getSuper() != null ) {
            System.out.printf(" : %s", c.getSuper());
        }
        System.out.println();

        for(Feature f: c.getBody()) {
            print(f);
        }
    }

    private static void print(Feature f) {
        if(f instanceof Method) {
            Method m = (Method)f;
            printIndent(2);
            System.out.printf("method %s : ", m.getName());
            for(Variable var: m.getParams()) {
                System.out.printf("%s %s -> ", var.getType(), var.getId());

                if( var.getValue() != null ){
                    throw new RuntimeException("WTF? initializing parameter definition?");
                }
            }
            System.out.println(m.getType());

            print(m.getBody(), 3);

        }
        else if (f instanceof Variable) {
            Variable var = (Variable)f;
            printIndent(2);
            System.out.printf("field %s %s\n", var.getType(), var.getId());
            if( var.getValue() != null ) {
                print(var.getValue(), 3);
            }
        }
        else {
            throw new RuntimeException("Unknown feature type " + f.getClass());
        }
    }

    private static void printIndent(int indent) {
        for (int i = indent; i > 0 ; i-- ) {
            System.out.print("    ");
        }  
    }

    @SuppressWarnings("unchecked")
    private static void print(Expr e, int indent) {

        assert e != null : "node is null";

        printIndent(indent);

        if(e instanceof Block) {
            System.out.println("block");
            for(Expr child: ((Block)e).getStatements()) {
                print(child, indent+1);
            }
        }
        else if(e instanceof WhileExpr) {
            WhileExpr loop = (WhileExpr)e;

            System.out.println("while");
            print(loop.getCond(), indent+1);

            printIndent(indent);
            System.out.println("loop");
            print(loop.getValue(), indent+1);


        }
        else if(e instanceof AssignExpr) {
            System.out.printf("assign %s\n", ((AssignExpr)e).getId());
            print(((AssignExpr)e).getValue(), indent+1);
        }        
        else if(e instanceof DispatchExpr) {
            DispatchExpr call = (DispatchExpr)e;

            System.out.printf("call %s", call.getName());
            if(call.getType() != null) {
                System.out.printf(" as %s", call.getType());
            }
            System.out.println();
            if( call.getExpr() != null ) {
                printIndent(indent+1);
                System.out.println("callee");
                print(call.getExpr(), indent+2);
            }
            if (call.getArgs().size() > 0) {
                printIndent(indent+1);
                System.out.println("args");
                for(Expr arg: call.getArgs()) {
                    print(arg, indent+2);
                }
            }
        }
        else if(e instanceof IfExpr) {
            IfExpr cond = (IfExpr)e;

            System.out.println("if");
            print(cond.getCond(), indent+1);

            printIndent(indent);
            System.out.println("then");
            print(cond.getTrue(), indent+1);

            printIndent(indent);
            System.out.println("else");
            print(cond.getFalse(), indent+1);

        }
        else if(e instanceof NewExpr) 
        {
            System.out.printf("new %s\n",((NewExpr)e).getType());
        }
        else if(e instanceof UnaryExpr) {
            UnaryExpr expr = (UnaryExpr)e;
            System.out.printf("unary %s\n", operator(expr.getOp()));
            print(expr.getValue(), indent + 1);
        }
        else if(e instanceof BinaryExpr) {
            BinaryExpr expr = (BinaryExpr)e;
            System.out.printf("binary %s\n", operator(expr.getOp()));
            print(expr.getLeft(), indent + 1);   
            print(expr.getRight(), indent + 1);   
        }
        else if (e instanceof CaseExpr) {
            CaseExpr caseExpr = ((CaseExpr)e);
            System.out.println("instanceof");
            print(caseExpr.getValue(), indent+1);

            for(Case c : caseExpr.getCases()) {
                printIndent(indent+1);
                System.out.printf("case %s %s\n", c.getType(), c.getId());
                print(c.getValue(), indent+2);
            }

        }
        else if (e instanceof LetExpr) {
            LetExpr let = (LetExpr)e;
            System.out.println("let");
            printIndent(indent+1);
            System.out.println("vars");
            for(Variable var : let.getDecl()) {
                printIndent(indent+2);
                System.out.printf("%s %s\n", var.getType(), var.getId());
                if(var.getValue() != null) {
                    print(var.getValue(), indent+3);
                }
            }

            print(let.getValue(), indent+1);
        }
        else if(e instanceof IdExpr) {
            System.out.printf("id %s\n", ((IdExpr)e).getId());
        }
        else if(e instanceof ValueExpr) {
            Object value = ((ValueExpr)e).getValue();

            if(value instanceof String) {
                System.out.printf("str \"%s\"\n", ((String)value).replace("\n", "\\n")
                    .replace("\t", "\\t").replace("\f", "\\f").replace("\b", "\\b"));
            }
            else if(value instanceof Integer) {
                System.out.printf("int %d\n", value);
            }
            else if(value instanceof Boolean) {
                System.out.printf("bool %s\n", value);
            }
            else {
                throw new RuntimeException(String.format("Unsupported constant type %s\n", e.getClass()));
            }
        }
        else {

            if( e != null) {
                throw new RuntimeException(String.format("Unsupported node type %s\n", e.getClass()));
            }
            else {
                throw new RuntimeException(String.format("Null node", e.getClass()));
            }

        }

    }


    public static String operator(UnaryOp op) {
        switch(op) {
            case ISVOID:    return "isvoid";
            case NEGATE:    return "~"; 
            case NOT:       return "not";
        }

        return null;
    }


    public static String operator(BinaryOp op) {
        switch(op) {
            case PLUS:      return "+";
            case MINUS:     return "-";
            case MULT:      return "*";
            case DIV:       return "/";            
            case LT:        return "<";
            case LTE:       return "<=";
            case EQUALS:    return "=";
        }
        return null;
    }




}  