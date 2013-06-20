package coolc;

import java.util.LinkedHashMap;

import coolc.ast.*;

public class CodegenConstantsPrinter {

	private LinkedHashMap<String, String> _vars;
	private LinkedHashMap<Integer, String> _vari;

	private LinkedHashMap<String, Object> _fields;

    private Program _root;
    private boolean _printTypes;
    
    ClassDef current_class = null;
    

    int c_vars = 0;
    int c_vari = 0;
    int c_varf = 0;
    
    
    public Object getValueFields(String name){
    	return _fields.get(name);
    }
    
    public String getNameVars(String value){
    	return _vars.get(value);
    }
    
    public String getNameVari(int value){
    	return _vari.get(value);
    }
    
    
    public CodegenConstantsPrinter(Program root) {
        this(root, false);
    }

    public CodegenConstantsPrinter(Program root, boolean printTypes) {
        _root = root;
        _printTypes = printTypes;
        _vars = new LinkedHashMap<String, String>();
        _vari = new LinkedHashMap<Integer, String>();
        _fields = new LinkedHashMap<String, Object>();
        
    }

    public void print() {
       
    	System.out.println("@true = global i1 1");
        System.out.println("@false = global i1 0");
        System.out.println("@zero = global i32 0");

        
        // recorrer arbol
        for(ClassDef c: _root) {            
        	current_class = c;
        	print(c);
        }
    }        
    
    private void print(ClassDef c) {
        //printIndent(1);

        //System.out.printf("class %s", c.getType());
        if( c.getSuper() != null ) {
            //System.out.printf(" : %s", c.getSuper());
        }
        //System.out.println();

        for(Feature f: c.getBody()) {
            print(f);
        }
    }

    private void print(Feature f) {
        if(f instanceof Method) {
            Method m = (Method)f;
            //printIndent(2);
            //System.out.printf("method %s : ", m.getName());
            
            for(Variable var: m.getParams()) {
                //System.out.printf("%s %s -> ", var.getType(), var.getId());

                if( var.getValue() != null ){
                    throw new RuntimeException("WTF? initializing parameter definition?");
                }
            }
            
            //System.out.println(m.getType());

            print(m.getBody(), 3);
        }
        else if (f instanceof Variable) {
            Variable var = (Variable)f;
            //printIndent(2);
            //System.out.printf("field %s %s\n", var.getType(), var.getId());
            
            ValueExpr valueexpr = (ValueExpr)var.getValue();

            String value = "";
            
            if(var.getType().equals("Int")){
	            if( var.getValue() != null ) {
	                value = valueexpr.getValue().toString();
	            }
	            else{
	            	value = "0";
	            }
	            	
	            
	            System.out.println("@" + var.getId() + " = global i32 " + value);
            }
            else if(var.getType().equals("Bool")){
	            if( var.getValue() != null ) {
	            	if((boolean)valueexpr.getValue() == true){
	            		value = "1";
	            	}
	            	else{
	            		value = "0";
	            	}
	                
	            }
	            else{
	            	value = "0";
	            }
	            
	            System.out.println("@" + var.getId() + " = global i1 " + value);
            }
            else if(var.getType().equals("String")){
            	int length = 1;
            	
            	if( var.getValue() != null ) {
 	                value = valueexpr.getValue().toString();
 	               length = ((String)value).length();   
 	            }
            	
                _fields.put(var.getId(), value);
                
                value =  ((String)value).replace("\n", "\\0A")
	                     .replace("\t", "\\09").replace("\f", "\\0C").replace("\b", "\\08");                 
                value += "";
                
                
           
            	System.out.println("@" + var.getId() + "_c = constant [" + length + " x i8] c\"" + value + "\"");
            	System.out.println("@" + var.getId() + " = global i8* bitcast([" + length + " x i8]* @" + var.getId() + "_c to i8*)");
                //printTag(String.format("str \"%s\"", value), e);
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
        //System.out.print(tag);
        if(_printTypes) {
            String type = e.getExprType();
            if(type != null) {
                //System.out.printf(" [%s]", type);
            }
            else {
                //System.out.print(" ERROR");
            }
        }
        //System.out.println();
    }


    @SuppressWarnings("unchecked")
    private void print(Expr e, int indent) {

        assert e != null : "node is null";

        //printIndent(indent);

        if(e instanceof Block) {
            //printTag("block", e);

            for(Expr child: ((Block)e).getStatements()) {
                print(child, indent+1);
            }
        }
        else if(e instanceof WhileExpr) {
            WhileExpr loop = (WhileExpr)e;

            assert "Object".equals(loop.getExprType()) : "while type must be Object";

            //printTag("while", e);

            print(loop.getCond(), indent+1);

            //printIndent(indent);
            //System.out.println("loop");

            print(loop.getValue(), indent+1);


        }
        else if(e instanceof AssignExpr) {

            //printTag(String.format("assign %s", ((AssignExpr)e).getId()), e);

            print(((AssignExpr)e).getValue(), indent+1);
        }        
        else if(e instanceof DispatchExpr) {
            DispatchExpr call = (DispatchExpr)e;

            StringBuilder out = new StringBuilder();

            out.append("call ").append(call.getName());
            if(call.getType() != null) {
                out.append(" as ").append(call.getType());
            }
            //printTag(out.toString(), e);
            

            if( call.getExpr() != null ) {
                //printIndent(indent+1);
                //System.out.println("callee");
                print(call.getExpr(), indent+2);
            }
            if (call.getArgs().size() > 0) {
                //printIndent(indent+1);
                //System.out.println("args");
                for(Expr arg: call.getArgs()) {
                    print(arg, indent+2);
                }
            }
        }
        else if(e instanceof IfExpr) {
            IfExpr cond = (IfExpr)e;

            //printTag("if", e);
            print(cond.getCond(), indent+1);

            assert "Bool".equals(cond.getCond().getExprType());

            //printIndent(indent);
            //System.out.println("then");
            print(cond.getTrue(), indent+1);

            //printIndent(indent);
            //System.out.println("else");
            print(cond.getFalse(), indent+1);

        }
        else if(e instanceof NewExpr) 
        {
            NewExpr newExpr = (NewExpr)e;
            //printTag(String.format("new %s",newExpr.getType()), e);

            assert newExpr.getType().equals(e.getExprType()) : String.format("Incompatible types %s %s", newExpr.getType(), e.getExprType());
        }
        else if(e instanceof UnaryExpr) {
            UnaryExpr expr = (UnaryExpr)e;

            //printTag(String.format("unary %s", operator(expr.getOp())), e);
            print(expr.getValue(), indent + 1);
        }
        else if(e instanceof BinaryExpr) {
            BinaryExpr expr = (BinaryExpr)e;
            //printTag(String.format("binary %s", operator(expr.getOp())), e);

            print(expr.getLeft(), indent + 1);   
            print(expr.getRight(), indent + 1);
        }
        else if (e instanceof CaseExpr) {
            CaseExpr caseExpr = ((CaseExpr)e);
            
            //printTag("instanceof", e);
            print(caseExpr.getValue(), indent+1);

            for(Case c : caseExpr.getCases()) {
                //printIndent(indent+1);
                //System.out.printf("case %s %s\n", c.getType(), c.getId());
                print(c.getValue(), indent+2);
            }

        }
        else if (e instanceof LetExpr) {
            LetExpr let = (LetExpr)e;
            //printTag("let", e);

            //printIndent(indent+1);
            //System.out.println("vars");
            for(Variable var : let.getDecl()) {
                //printIndent(indent+2);
                //System.out.printf("%s %s\n", var.getType(), var.getId());
                if(var.getValue() != null) {
                    print(var.getValue(), indent+3);
                }
            }

            print(let.getValue(), indent+1);
        }
        else if(e instanceof IdExpr) {
            //printTag(String.format("id %s", ((IdExpr)e).getId()), e);
        }
        else if(e instanceof ValueExpr) {
            Object value = ((ValueExpr)e).getValue();

            if(value instanceof String) {
            	
            	if(!_vars.containsKey(value.toString())){
	            	int length = ((String)value).length();
	                _vars.put(value.toString(), "vars_"+c_vars );
	
	                value =  ((String)value).replace("\n", "\\0A")
	                    .replace("\t", "\\09").replace("\f", "\\0C").replace("\b", "\\08");
	                
	                value += "";
	                
	                
	            	System.out.println("@vars_" + c_vars + "_c = constant [" + length + " x i8] c\"" + value + "\"");
	            	System.out.println("@vars_" + c_vars + " = global i8* bitcast([" + length + " x i8]* @vars_" + c_vars + "_c to i8*)");
	            	c_vars++;
	
	                //printTag(String.format("str \"%s\"", value), e);
            	}
            }
            else if(value instanceof Integer) {
                
                assert "Int".equals(e.getExprType());
                //printTag(String.format("int %d", value), e);

                int val = ((int)value);
                if(!_vari.containsKey(val)){
                	_vari.put(val, "vari_" + c_vari);
                	System.out.println("@vari_" + c_vari++ + " = global i32 " + ((int)value));
                	//@X = global i32 17
                
                }
            }
            else if(value instanceof Boolean) {
                assert "Bool".equals(e.getExprType());
                //printTag(String.format("bool %s", value), e);
                
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

    
    
    public String GetCurrentClassLL(){
    	return "%" + current_class.getType() + "*";
    }
    
    public String GetCurrentSuperClassLL(){
    	return "%" + current_class.getSuper() + "*";
    }
    
    public String GetTypeLL(String type){
    	
    	if(type.equals("String")){
    		
    	}
    	else if(type.equals("Int")){
    		return "i32";
    	}
    	else if(type.equals("Bool")){
    		return "i1";
    	}
    	else if(type.equals("SELF_TYPE")){
    		return "%" + current_class.getType() + "*";
    	}
    	
    	return "";
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