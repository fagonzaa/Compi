package coolc;

import coolc.ast.*;

public class AstPrinter {

    private Program _root;
    private boolean _printTypes;

    public AstPrinter(Program root) {
        this(root, false);
    }

    public AstPrinter(Program root, boolean printTypes) {
        _root = root;
        _printTypes = printTypes;
    }

    public void print() {
        System.out.println("program");
        for(ClassDef c: _root) {            
            print(c);
        }
    }

    private void print(ClassDef c) {
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

    private void print(Feature f) {
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

    public static void printIndent(int indent) {
        for (int i = indent; i > 0 ; i-- ) {
            System.out.print("    ");
        }  
    }

    public static void printIndent(int indent, StringBuilder sb) {
        for (int i = indent; i > 0 ; i-- ) {
            sb.append("    ");
        }
    }

    private void printTag(String tag, Expr e) {
        System.out.print(tag);
        if(_printTypes) {
            String type = e.getExprType();
            if(type != null) {
                System.out.printf(" [%s]", type);
            }
            else {
                System.out.print(" ERROR");
            }
        }
        System.out.println();
    }


    @SuppressWarnings("unchecked")
    private void print(Expr e, int indent) {

        assert e != null : "node is null";

        printIndent(indent);

        if(e instanceof Block) {
            printTag("block", e);

            for(Expr child: ((Block)e).getStatements()) {
                print(child, indent+1);
            }
        }
        else if(e instanceof WhileExpr) {
            WhileExpr loop = (WhileExpr)e;

            assert "Object".equals(loop.getExprType()) : "while type must be Object";

            printTag("while", e);

            print(loop.getCond(), indent+1);

            printIndent(indent);
            System.out.println("loop");

            print(loop.getValue(), indent+1);


        }
        else if(e instanceof AssignExpr) {

            printTag(String.format("assign %s", ((AssignExpr)e).getId()), e);

            print(((AssignExpr)e).getValue(), indent+1);
        }        
        else if(e instanceof DispatchExpr) {
            DispatchExpr call = (DispatchExpr)e;

            StringBuilder out = new StringBuilder();

            out.append("call ").append(call.getName());
            if(call.getType() != null) {
                out.append(" as ").append(call.getType());
            }
            printTag(out.toString(), e);
            

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

            printTag("if", e);
            print(cond.getCond(), indent+1);

            assert "Bool".equals(cond.getCond().getExprType());

            printIndent(indent);
            System.out.println("then");
            print(cond.getTrue(), indent+1);

            printIndent(indent);
            System.out.println("else");
            print(cond.getFalse(), indent+1);

        }
        else if(e instanceof NewExpr) 
        {
            NewExpr newExpr = (NewExpr)e;
            printTag(String.format("new %s",newExpr.getType()), e);

            assert newExpr.getType().equals(e.getExprType()) : String.format("Incompatible types %s %s", newExpr.getType(), e.getExprType());
        }
        else if(e instanceof UnaryExpr) {
            UnaryExpr expr = (UnaryExpr)e;

            printTag(String.format("unary %s", operator(expr.getOp())), e);
            print(expr.getValue(), indent + 1);
        }
        else if(e instanceof BinaryExpr) {
            BinaryExpr expr = (BinaryExpr)e;
            printTag(String.format("binary %s", operator(expr.getOp())), e);

            print(expr.getLeft(), indent + 1);   
            print(expr.getRight(), indent + 1);
        }
        else if (e instanceof CaseExpr) {
            CaseExpr caseExpr = ((CaseExpr)e);
            
            printTag("instanceof", e);
            print(caseExpr.getValue(), indent+1);

            for(Case c : caseExpr.getCases()) {
                printIndent(indent+1);
                System.out.printf("case %s %s\n", c.getType(), c.getId());
                print(c.getValue(), indent+2);
            }

        }
        else if (e instanceof LetExpr) {
            LetExpr let = (LetExpr)e;
            printTag("let", e);

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
            printTag(String.format("id %s", ((IdExpr)e).getId()), e);
        }
        else if(e instanceof ValueExpr) {
            Object value = ((ValueExpr)e).getValue();

            if(value instanceof String) {

                value =  ((String)value).replace("\n", "\\n")
                    .replace("\t", "\\t").replace("\f", "\\f").replace("\b", "\\b");

                printTag(String.format("str \"%s\"", value), e);
            }
            else if(value instanceof Integer) {
                
                assert "Int".equals(e.getExprType());
                printTag(String.format("int %d", value), e);

            }
            else if(value instanceof Boolean) {
                assert "Bool".equals(e.getExprType());
                printTag(String.format("bool %s", value), e);
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


    public String operator(UnaryOp op) {
        switch(op) {
            case ISVOID:    return "isvoid";
            case NEGATE:    return "~"; 
            case NOT:       return "not";
        }

        return null;
    }


    public String operator(BinaryOp op) {
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