package coolc.semantic;

import coolc.ast.*;
import coolc.symtable.*;

public class SemanticPrint {

	SymTable symtable;
	String filename;
	String typeReturn = "";
	
	ClassScope classScope = null;
	
	public SemanticPrint(SymTable symtable, String filename){
		this.symtable = symtable;
		this.filename = filename;
		this.filename = "";
	}
	
	

    public void print(Program root) {
    	
        System.out.println("program");
        for(ClassDef c: root) {            
        	print(c);
        }
    }

    private void print(ClassDef c) {
        printIndent(1);
        
        classScope = symtable.getClassScope(c.getType());
        
        System.out.printf("class %s", c.getType());
        if( c.getSuper() != null ) {
          System.out.printf(" : %s", c.getSuper());
        	if(!canInherit(c.getSuper())){
        		//System.out.println(filename + "" + c.getPosition().line + ":" + c.getPosition().column + " Can't inherit from " + c.getSuper());
        	}
        	if(!isTypeBasic(c.getSuper())){
            	if(!isClassDefine(c.getSuper())){
            		//System.out.println(filename + "" + c.getPosition().line + ":" + c.getPosition().column + " Undefined class " + c.getSuper());
            	}
            }
        }
        System.out.println();

        for(Feature f: c.getBody()) {
            print(f, classScope);
        }
    }

    private void print(Feature f, ClassScope classScope) {
        if(f instanceof Method) {
            Method m = (Method)f;
            printIndent(2);
            
            if(!isTypeBasic(m.getType())){
            	if(!isClassDefine(m.getType())){
            		//System.out.println(filename + "" + m.getPosition().line + ":" + m.getPosition().column + " Undefined class " + m.getType());
            	}
            }
            
         System.out.printf("method %s : ", m.getName());
            for(Variable var: m.getParams()) {
                System.out.printf("%s %s -> ", var.getType(), var.getId());

            	if(!isTypeBasic(var.getType())){
                	if(!isClassDefine(var.getType())){
                		//System.out.println(filename + "" + var.getPosition().line + ":" + var.getPosition().column + " Undefined class " + var.getType());
                	}
                }
            	
                if( var.getValue() != null ){
                    throw new RuntimeException("WTF? initializing parameter definition?");
                }
            }
            System.out.println(m.getType());

            
            MethodSymbol symbol = (MethodSymbol)classScope.get(m.getName());
            
            if(symbol == null)
            	return;
            
            
            Symbol mo = getSymbolMethodOverride(m.getName());
            
            if(mo != null){
            	MethodSymbol methodOverride = (MethodSymbol)mo;
            	
            	String nameClassSuper = methodOverride.methodScope.getClassScope().getClassDef().getType();
        		
        		String[] paramsOverride = methodOverride.methodScope.getStringParams();
        		
        		
            	boolean arg_error = false;
            	
            	if(m.getParams().size() != methodOverride.methodScope.getStringParams().length){
            		arg_error = true;
            	}
            	else{
            		int i = 0;
            		for(Variable var: m.getParams()) {
            			if(var.getType().compareTo(paramsOverride[i]) != 0){
            				arg_error = true;
            				break;
            			}
            		}
            	}
            	
            	if(m.getType().compareTo(methodOverride.type) != 0){
            		arg_error = true;
            	}
            	
            	
            	if(arg_error){
            		
            		//System.out.print(filename + "" + m.getPosition().line + ":" + m.getPosition().column + " Cannot override method " + nameClassSuper + "." +  m.getName() + " ");

            		for(int i = 0; i<paramsOverride.length; ++i){
            			//System.out.print(paramsOverride[i] + " -> ");
            		}
            		
            		//System.out.print(methodOverride.type + " with " + classScope.getClassDef().getType() + "." + m.getName() + " ");
            		
            		
            		for(Variable var: m.getParams()) {
            			//System.out.print(var.getType() + " -> ");
            		}            		
            		
            		//System.out.println(m.getType());
            	}
            }
            
            
            print(m.getBody(), 3, symbol.methodScope);

            if(typeReturn.compareTo("") != 0){
	            if(m.getType().compareTo("SELF_TYPE") == 0 || m.getType().compareTo("Object") == 0){
	            	if(typeReturn.compareTo("Int") != 0 && typeReturn.compareTo("self") != 0){
	            		//System.out.println(filename + "" + m.getPosition().line + ":" + m.getPosition().column + " Expected " + m.getName() + ", received " + typeReturn);
	            	}
	            }
	            else if(!isTypeBasic(m.getType())){
	            	if(typeReturn.compareTo("Int") != 0){
	            		//System.out.println(filename + "" + m.getPosition().line + ":" + m.getPosition().column + " Expected " + m.getName() + ", received " + typeReturn);
	            	}
	            }
	            else if(m.getType().compareTo(typeReturn) != 0){
	        		//System.out.println(filename + "" + m.getPosition().line + ":" + m.getPosition().column + " Expected " + m.getName() + ", received " + typeReturn);
	            }
            }
        }
        else if (f instanceof Variable) {
            Variable var = (Variable)f;
            printIndent(2);
            
            Symbol symbol = classScope.get(var.getId());
            
             System.out.printf("field %s %s\n", var.getType(), var.getId());
            
            if(!isTypeBasic(var.getType())){
            	if(!isClassDefine(var.getType())){
            		//System.out.println(filename + "" + var.getPosition().line + ":" + var.getPosition().column + " Undefined class " + var.getType());
            	}
            }
            
            
            if( var.getValue() != null ) {
                print(var.getValue(), 3, classScope);
            }
        }
        else {
            throw new RuntimeException("Unknown feature type " + f.getClass());
        }
    }

    private void printIndent(int indent) {
        for (int i = indent; i > 0 ; i-- ) {
            System.out.print("    ");
        }  
    }

    @SuppressWarnings("unchecked")
    private void print(Expr e, int indent, Scope scope) {

        assert e != null : "node is null";

        printIndent(indent);

        if(e instanceof Block) {
        	 
            for(Expr child: ((Block)e).getStatements()) {
                print2(child, indent+1, scope);
            }
            
            System.out.println("block " + typeReturn);
            
            for(Expr child: ((Block)e).getStatements()) {
                print(child, indent+1, scope);
            }
            typeReturn = "";
        }
        else if(e instanceof WhileExpr) {
            WhileExpr loop = (WhileExpr)e;

            System.out.print("while");
            print2(loop.getCond(), indent+1, scope);

            if(typeReturn.compareTo("Bool") != 0){
            	System.out.print(" ERROR");
            	typeReturn = "ERROR";
        		//System.out.println(filename + "" + loop.getPosition().line + ":" + loop.getPosition().column + " Expected Bool, received " + typeReturn);
            }
            else{
            	System.out.print(" [Object]");
            }
            System.out.println();
            print(loop.getCond(), indent+1, scope);
            
            printIndent(indent);
            System.out.println("loop");
            print(loop.getValue(), indent+1, scope);

            typeReturn = "";
        }
        else if(e instanceof AssignExpr) {
            System.out.printf("assign %s\n", ((AssignExpr)e).getId());
            print(((AssignExpr)e).getValue(), indent+1, scope);
            
            Symbol symbol = symtable.getSymbol(scope, ((AssignExpr)e).getId());
            
            if(symbol != null){
            	String typeReturn1 = symbol.type;
            	
            	if(typeReturn1.compareTo("Object") != 0 && typeReturn1.compareTo(typeReturn) != 0){
            		//System.out.println(filename + "" + e.getPosition().line + ":" + e.getPosition().column + " Expected " + typeReturn1 + ", received " + typeReturn);
            	}
            	typeReturn = typeReturn1;
            }
        }        
        else if(e instanceof DispatchExpr) {
            DispatchExpr call = (DispatchExpr)e;

            System.out.printf("call %s", call.getName());
            
            String callname = call.getName();
            String calltype = call.getType();
            
            if(call.getType() == null){
            	
            	if(callname.compareTo("type_name") != 0 && callname.compareTo("abort") != 0 && callname.compareTo("copy") != 0){
            		if(!symtable.isMethodDefine(classScope, callname)){
            			//System.out.println(filename + "" + call.getPosition().line + ":" + call.getPosition().column + " Undefined method " + call.getName());
                	}
            	}
            }
            else {
            	Symbol symbol = symtable.getSymbol(scope, call.getType());
            	if(symbol == null && symtable.getClassScope(call.getType()) == null){
            		//System.out.println(filename + "" + e.getPosition().line + ":" + e.getPosition().column + " Undefined class " + call.getType());
            	}
                System.out.printf(" as %s", call.getType());
            }
            System.out.println();
            if( call.getExpr() != null ) {
                printIndent(indent+1);
                System.out.println("callee");
            	print(call.getExpr(), indent+2, scope);
            	
            	if(call.getType() != null && call.getType().compareTo("") != 0 && typeReturn.compareTo("") != 0){
	            	if(typeReturn.compareTo(call.getType()) != 0){
	            		//System.out.println(filename + "" + e.getPosition().line + ":" + e.getPosition().column + " Expected " + call.getType() + ", received " + typeReturn );
	            	}
            	}
            }
            if (call.getArgs().size() > 0) {
                printIndent(indent+1);
                System.out.println("args");
                String[] args = new String[call.getArgs().size()];
                String argtext = "";
                
                int iargs = 0;
                for(Expr arg: call.getArgs()) {
                    print(arg, indent+2, scope);
                    args[iargs] = typeReturn;
                    
                    if(iargs!=0){
                    	argtext += ",";
                    }
                    
                    argtext += typeReturn;
                    
                    iargs++;
                }
                
                
                
                if(!IsDefinedMethod(args, callname, scope)){
                	if(calltype == null){
                		calltype = classScope.getClassDef().getType();
                	}
                	
                	String getRealParams = getStringParams(callname, scope);
                	String methodType = symtable.getSymbol(scope, callname).type;
                	
            		//System.out.println(filename + "" + e.getPosition().line + ":" + e.getPosition().column + " Cannot call method " + calltype + "." + callname + " " + getRealParams + " -> " + methodType + ", with arguments (" + argtext + ")");
                }
            }
        }
        else if(e instanceof IfExpr) {
            IfExpr cond = (IfExpr)e;

            System.out.print("if");
            print2(cond.getCond(), indent+1, scope);

            if(typeReturn.compareTo("Bool") != 0){
                System.out.print(" ERROR");
                typeReturn = "ERROR";
        		//System.out.println(filename + "" + cond.getPosition().line + ":" + cond.getPosition().column + " Expected Bool, received " + typeReturn);
            }
            else{
                System.out.print(" [Bool]");
            }
            System.out.println();
            print(cond.getCond(), indent+1, scope);

            
            printIndent(indent);
            System.out.println("then");
            print(cond.getTrue(), indent+1, scope);

            printIndent(indent);
            System.out.println("else");
            print(cond.getFalse(), indent+1, scope);

            typeReturn = "";
        }
        else if(e instanceof NewExpr) 
        {
        	NewExpr newExpr = (NewExpr)e;
        	
        	typeReturn = newExpr.getType();
        	
        	System.out.printf("new %s [%s]",((NewExpr)e).getType(), ((NewExpr)e).getType());
        	
        	if(!isTypeBasic(newExpr.getType())){
            	if(!isClassDefine(newExpr.getType())){
            		//System.out.println(filename + "" + newExpr.getPosition().line + ":" + newExpr.getPosition().column + " Undefined class " + newExpr.getType());
            		System.out.printf(" ERROR");
            		typeReturn = "ERROR";
            	}
            }
            
        	System.out.println();
        }
        else if(e instanceof UnaryExpr) {
            UnaryExpr expr = (UnaryExpr)e;
            System.out.printf("unary %s ", operator(expr.getOp()));
            print2(expr.getValue(), indent + 1, scope);
            
            
            if(expr.getOp() == UnaryOp.NOT){
            	if(typeReturn.compareTo("Bool") != 0){
            		//System.out.println(filename + "" + expr.getPosition().line + ":" + expr.getPosition().column + " Expected Bool, received " + typeReturn);
            		System.out.println("ERROR");
            		typeReturn = "ERROR";
            	}
            	else{
            		System.out.println("[Bool]");
            	}
            }
            else if(expr.getOp() == UnaryOp.NEGATE){
            	if(typeReturn.compareTo("Int") != 0){
            		//System.out.println(filename + "" + expr.getPosition().line + ":" + expr.getPosition().column + " Expected Int, received " + typeReturn);
            		System.out.println("ERROR");
            		typeReturn = "ERROR";
            	}
            	else{
            		System.out.println("[Int]");
            	}
            }  
            
            print(expr.getValue(), indent + 1, scope);

        }
        else if(e instanceof BinaryExpr) {
            BinaryExpr expr = (BinaryExpr)e;
            System.out.printf("binary %s", operator(expr.getOp()));
            String typeReturn1 = "";
            String typeReturn2 = "";
            
            print2(expr.getLeft(), indent + 1, scope);   
            typeReturn1 = typeReturn;
            
            print2(expr.getRight(), indent + 1, scope);
            typeReturn2 = typeReturn;
            
            boolean error = false;
            if(expr.getOp() == BinaryOp.PLUS || expr.getOp() == BinaryOp.MINUS || expr.getOp() == BinaryOp.MULT || expr.getOp() == BinaryOp.DIV){
            	if(typeReturn1.compareTo("Int") != 0){
            		//System.out.println(filename + "" + expr.getLeft().getPosition().line + ":" + expr.getLeft().getPosition().column + " Expected Int, received " + typeReturn1);
            		error = true;
            		typeReturn = "ERROR";
            	}
            	if(typeReturn2.compareTo("Int") != 0){
            		//System.out.println(filename + "" + expr.getRight().getPosition().line + ":" + expr.getRight().getPosition().column + " Expected Int, received " + typeReturn2);
            		error = true;
            		typeReturn = "ERROR";
            	}
            	
            	if(error){
            		System.out.println(" ERROR");
            	}
            	else{
            		System.out.println(" INT");
            	}
            }
            
            if(expr.getOp() == BinaryOp.LT || expr.getOp() == BinaryOp.LTE ){
            	if(typeReturn1.compareTo("Int") != 0){
            		error = true;
            		typeReturn = "ERROR";
            		//System.out.println(filename + "" + expr.getLeft().getPosition().line + ":" + expr.getLeft().getPosition().column + " Expected Int, received " + typeReturn1);
                }
            	if(typeReturn2.compareTo("Int") != 0){
            		error = true;
            		typeReturn = "ERROR";
            		//System.out.println(filename + "" + expr.getRight().getPosition().line + ":" + expr.getRight().getPosition().column + " Expected Int, received " + typeReturn2);
                }
            	
            	if(error){
            		System.out.println(" ERROR");
            	}
            	else{
            		System.out.println(" Int");
            	}
            		
            }
            
            if(expr.getOp() == BinaryOp.EQUALS){
            	if(typeReturn1.compareTo(typeReturn2) != 0){
            		if(!isTypeBasic(typeReturn1) && isTypeBasic(typeReturn2)){
            			error = true;
            			typeReturn = "ERROR";
            			//System.out.println(filename + "" + expr.getLeft().getPosition().line + ":" + expr.getLeft().getPosition().column + " Expected " + typeReturn2 + ", received " + typeReturn1);
            		}
            		else
            		{
            			error = true;
            			typeReturn = "ERROR";
            			//System.out.println(filename + "" + expr.getRight().getPosition().line + ":" + expr.getRight().getPosition().column + " Expected " + typeReturn1 + ", received " + typeReturn2);
            		}
            		
            		if(error){
                		System.out.println(" ERROR");
                	}
                	else{
                		System.out.println(" Bool");
                	}
            	}
            }
            
            print(expr.getLeft(), indent + 1, scope);   
            typeReturn1 = typeReturn;
            
            print(expr.getRight(), indent + 1, scope);
            typeReturn2 = typeReturn;
        }
        else if (e instanceof CaseExpr) {
            CaseExpr caseExpr = ((CaseExpr)e);
            System.out.println("instanceof");
            print(caseExpr.getValue(), indent+1, scope);

            for(Case c : caseExpr.getCases()) {
                printIndent(indent+1);
                System.out.printf("case %s %s\n", c.getType(), c.getId());
                print(c.getValue(), indent+2, scope);
                
                if(!isTypeBasic(c.getType())){
                	if(!isClassDefine(c.getType())){
                		//System.out.println(filename + "" + c.getPosition().line + ":" + c.getPosition().column + " Undefined class " + c.getType());
                	}
                }
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
                    print(var.getValue(), indent+3, scope);
                }
                
                if(!isTypeBasic(var.getType())){
                	if(!isClassDefine(var.getType())){
                		//System.out.println(filename + "" + var.getPosition().line + ":" + var.getPosition().column + " Undefined class " + var.getType());
                	}
                }
            }

            print(let.getValue(), indent+1, scope);
            
            typeReturn = "";
        }
        else if(e instanceof IdExpr) {
        	String id = ((IdExpr)e).getId();
            System.out.printf("id %s ", id);
            
            if(id.compareTo("self") == 0){
            	System.out.println("[" + classScope.getClassDef().getType() + "]");
            }
            else{
            
            	Symbol symbol = symtable.getSymbol(scope, ((IdExpr)e).getId());
            	
            	if(symbol != null){
            		typeReturn = symbol.type;
            		System.out.println("[" + typeReturn + "]");
            	}
            	else{
            		typeReturn = "ERROR";
            		System.out.println("ERROR");
            	}
            	
            	
            }
            
            
        	
        }
        else if(e instanceof ValueExpr) {
            Object value = ((ValueExpr)e).getValue();

            if(value instanceof String) {
            	typeReturn = "String";
            	System.out.printf("str \"%s\" [String]\n", ((String)value).replace("\n", "\\n")
                    .replace("\t", "\\t").replace("\f", "\\f").replace("\b", "\\b"));
            }
            else if(value instanceof Integer) {
                System.out.printf("int %d [Int]\n", value);
            	typeReturn = "Int";
            }
            else if(value instanceof Boolean) {
                System.out.printf("bool %s [Bool]\n", value);
            	typeReturn = "Bool";
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
    
    public boolean IsDefinedMethod(String[] args, String callname, Scope scope){
    	
        MethodSymbol symbol = (MethodSymbol)symtable.getSymbol(scope, callname);

        String[] real_params = symbol.methodScope.getStringParams();
        
        if(real_params.length != args.length){
        	return false;
        }
        
        for(int i=0; i<args.length; ++i){
        	if(args[i].compareTo(real_params[i]) != 0){
        		return false;
        	}
        }
    	
    	return true;
    }
    
    public String getStringParams(String callname, Scope scope){
    	String params = "";
    	
    	MethodSymbol symbol = (MethodSymbol)symtable.getSymbol(scope, callname);

        String[] real_params = symbol.methodScope.getStringParams();
        
        for(int i=0; i<real_params.length; ++i){
        	
        	if(i != 0){
        		params += ",";
        	}
        	params += real_params[i];
        }
        
        return params;
    }
    
    public Symbol getSymbolMethodOverride(String nameMethod){
    	
    	ClassScope scope = classScope;
    	Symbol s = null;
    	
    	if(scope.getClassDef().getSuper() != null){
			while(scope.getClassDef().getSuper() != null){
				scope = symtable.getClassScope(scope.getClassDef().getSuper());
				
				if(scope == null){
					return null;
				}
				else{
					s = scope.get(nameMethod);
					
					if(s != null){
						return s;
					}
				}
			}
		}
    	
    	
    	return s;
    }

    public boolean isTypeBasic(String type){
    	if(type.compareTo("Int") == 0 || type.compareTo("String") == 0 || type.compareTo("Bool") == 0 || type.compareTo("Object") == 0 || type.compareTo("IO") == 0){
    		return true;
    	}
    	
    	return false;
    }
    
    public boolean canInherit(String type){
    	
    	return !(type.compareTo("Int") == 0 || type.compareTo("String") == 0 || type.compareTo("Bool") == 0);
    }
    
    public boolean isClassDefine(String name){
    	
    	return (symtable.getClassScope(name) != null) || name.compareTo("SELF_TYPE") == 0;
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
    
    
    
    @SuppressWarnings("unchecked")
    private void print2(Expr e, int indent, Scope scope) {

        assert e != null : "node is null";


        if(e instanceof Block) {
            for(Expr child: ((Block)e).getStatements()) {
                print2(child, indent+1, scope);
            }
            typeReturn = "";
        }
        else if(e instanceof WhileExpr) {
            WhileExpr loop = (WhileExpr)e;

            print2(loop.getCond(), indent+1, scope);

            if(typeReturn.compareTo("Bool") != 0){
        		//System.out.println(filename + "" + loop.getPosition().line + ":" + loop.getPosition().column + " Expected Bool, received " + typeReturn);
            	typeReturn = "ERROR";
            }
            
            print2(loop.getValue(), indent+1, scope);

            typeReturn = "";
        }
        else if(e instanceof AssignExpr) {
            print2(((AssignExpr)e).getValue(), indent+1, scope);
            
            Symbol symbol = symtable.getSymbol(scope, ((AssignExpr)e).getId());
            
            if(symbol != null){
            	String typeReturn1 = symbol.type;
            	
            	if(typeReturn1.compareTo("Object") != 0 && typeReturn1.compareTo(typeReturn) != 0){
            		//System.out.println(filename + "" + e.getPosition().line + ":" + e.getPosition().column + " Expected " + typeReturn1 + ", received " + typeReturn);
            	}
            	typeReturn = typeReturn1;
            }
        }        
        else if(e instanceof DispatchExpr) {
            DispatchExpr call = (DispatchExpr)e;
            
            String callname = call.getName();
            String calltype = call.getType();
            
            if(call.getType() == null){
            	
            	if(callname.compareTo("type_name") != 0 && callname.compareTo("abort") != 0 && callname.compareTo("copy") != 0){
            		if(!symtable.isMethodDefine(classScope, callname)){
            			//System.out.println(filename + "" + call.getPosition().line + ":" + call.getPosition().column + " Undefined method " + call.getName());
            		}
            	}
            }
            else {
            	Symbol symbol = symtable.getSymbol(scope, call.getType());
            	if(symbol == null && symtable.getClassScope(call.getType()) == null){
            		//System.out.println(filename + "" + e.getPosition().line + ":" + e.getPosition().column + " Undefined class " + call.getType());
            	}
            }
            if( call.getExpr() != null ) {
            	print2(call.getExpr(), indent+2, scope);
            	
            	if(call.getType() != null && call.getType().compareTo("") != 0 && typeReturn.compareTo("") != 0){
	            	if(typeReturn.compareTo(call.getType()) != 0){
	            		//System.out.println(filename + "" + e.getPosition().line + ":" + e.getPosition().column + " Expected " + call.getType() + ", received " + typeReturn );
	            		
	            	}
            	}
            }
            if (call.getArgs().size() > 0) {
                String[] args = new String[call.getArgs().size()];
                String argtext = "";
                
                int iargs = 0;
                for(Expr arg: call.getArgs()) {
                    print2(arg, indent+2, scope);
                    args[iargs] = typeReturn;
                    
                    if(iargs!=0){
                    	argtext += ",";
                    }
                    
                    argtext += typeReturn;
                    
                    iargs++;
                }
                
                
                
                if(!IsDefinedMethod(args, callname, scope)){
                	if(calltype == null){
                		calltype = classScope.getClassDef().getType();
                	}
                	
                	String getRealParams = getStringParams(callname, scope);
                	String methodType = symtable.getSymbol(scope, callname).type;
                	
            		//System.out.println(filename + "" + e.getPosition().line + ":" + e.getPosition().column + " Cannot call method " + calltype + "." + callname + " " + getRealParams + " -> " + methodType + ", with arguments (" + argtext + ")");
                }
            }
        }
        else if(e instanceof IfExpr) {
            IfExpr cond = (IfExpr)e;

            print2(cond.getCond(), indent+1, scope);

            if(typeReturn.compareTo("Bool") != 0){
        		//System.out.println(filename + "" + cond.getPosition().line + ":" + cond.getPosition().column + " Expected Bool, received " + typeReturn);
            }
            
            print2(cond.getTrue(), indent+1, scope);

            print2(cond.getFalse(), indent+1, scope);

            typeReturn = "";
        }
        else if(e instanceof NewExpr) 
        {
        	NewExpr newExpr = (NewExpr)e;
        	
        	typeReturn = newExpr.getType();
        	
        	
        	if(!isTypeBasic(newExpr.getType())){
            	if(!isClassDefine(newExpr.getType())){
            		//System.out.println(filename + "" + newExpr.getPosition().line + ":" + newExpr.getPosition().column + " Undefined class " + newExpr.getType());
            		typeReturn = "ERROR";
            	}
            }
            
        }
        else if(e instanceof UnaryExpr) {
            UnaryExpr expr = (UnaryExpr)e;
            print2(expr.getValue(), indent + 1, scope);
            
            
            if(expr.getOp() == UnaryOp.NOT){
            	if(typeReturn.compareTo("Bool") != 0){
            		//System.out.println(filename + "" + expr.getPosition().line + ":" + expr.getPosition().column + " Expected Bool, received " + typeReturn);
            		typeReturn = "ERROR";
            	}
            }
            else if(expr.getOp() == UnaryOp.NEGATE){
            	if(typeReturn.compareTo("Int") != 0){
            		//System.out.println(filename + "" + expr.getPosition().line + ":" + expr.getPosition().column + " Expected Int, received " + typeReturn);
            		typeReturn = "ERROR";
            	}
            }            
        }
        else if(e instanceof BinaryExpr) {
            BinaryExpr expr = (BinaryExpr)e;
            String typeReturn1 = "";
            String typeReturn2 = "";
            
            print2(expr.getLeft(), indent + 1, scope);   
            typeReturn1 = typeReturn;
            
            print2(expr.getRight(), indent + 1, scope);
            typeReturn2 = typeReturn;
            
            boolean error = false;
            if(expr.getOp() == BinaryOp.PLUS || expr.getOp() == BinaryOp.MINUS || expr.getOp() == BinaryOp.MULT || expr.getOp() == BinaryOp.DIV){
            	if(typeReturn1.compareTo("Int") != 0){
            		//System.out.println(filename + "" + expr.getLeft().getPosition().line + ":" + expr.getLeft().getPosition().column + " Expected Int, received " + typeReturn1);
            		error = true;
            		typeReturn = "ERROR";
            	}
            	if(typeReturn2.compareTo("Int") != 0){
            		//System.out.println(filename + "" + expr.getRight().getPosition().line + ":" + expr.getRight().getPosition().column + " Expected Int, received " + typeReturn2);
            		error = true;
            		typeReturn = "ERROR";
            	}
            }
            
            if(expr.getOp() == BinaryOp.LT || expr.getOp() == BinaryOp.LTE ){
            	if(typeReturn1.compareTo("Int") != 0){
            		error = true;
            		typeReturn = "ERROR";
            		//System.out.println(filename + "" + expr.getLeft().getPosition().line + ":" + expr.getLeft().getPosition().column + " Expected Int, received " + typeReturn1);
                }
            	if(typeReturn2.compareTo("Int") != 0){
            		error = true;
            		typeReturn = "ERROR";
            		//System.out.println(filename + "" + expr.getRight().getPosition().line + ":" + expr.getRight().getPosition().column + " Expected Int, received " + typeReturn2);
                }
            		
            }
            
            if(expr.getOp() == BinaryOp.EQUALS){
            	if(typeReturn1.compareTo(typeReturn2) != 0){
            		if(!isTypeBasic(typeReturn1) && isTypeBasic(typeReturn2)){
            			error = true;
            			typeReturn = "ERROR";
            			//System.out.println(filename + "" + expr.getLeft().getPosition().line + ":" + expr.getLeft().getPosition().column + " Expected " + typeReturn2 + ", received " + typeReturn1);
            		}
            		else
            		{
            			error = true;
            			typeReturn = "ERROR";
            			//System.out.println(filename + "" + expr.getRight().getPosition().line + ":" + expr.getRight().getPosition().column + " Expected " + typeReturn1 + ", received " + typeReturn2);
            		}
            	}
            }
            
            
        }
        else if (e instanceof CaseExpr) {
            CaseExpr caseExpr = ((CaseExpr)e);
            print2(caseExpr.getValue(), indent+1, scope);

            for(Case c : caseExpr.getCases()) {
                print2(c.getValue(), indent+2, scope);
                
                if(!isTypeBasic(c.getType())){
                	if(!isClassDefine(c.getType())){
                		//System.out.println(filename + "" + c.getPosition().line + ":" + c.getPosition().column + " Undefined class " + c.getType());
                	}
                }
            }

        }
        else if (e instanceof LetExpr) {
            LetExpr let = (LetExpr)e;
            for(Variable var : let.getDecl()) {
                if(var.getValue() != null) {
                    print2(var.getValue(), indent+3, scope);
                }
                
                if(!isTypeBasic(var.getType())){
                	if(!isClassDefine(var.getType())){
                		//System.out.println(filename + "" + var.getPosition().line + ":" + var.getPosition().column + " Undefined class " + var.getType());
                	}
                }
            }

            print2(let.getValue(), indent+1, scope);
            
            typeReturn = "";
        }
        else if(e instanceof IdExpr) {
        	String id = ((IdExpr)e).getId();
            
            if(id.compareTo("self") == 0){
            }
            else{
            
            	Symbol symbol = symtable.getSymbol(scope, ((IdExpr)e).getId());
            	
            	if(symbol != null){
            		typeReturn = symbol.type;
            	}
            	else{
            		typeReturn = "ERROR";
            	}
            	
            	
            }
            
            
        	
        }
        else if(e instanceof ValueExpr) {
            Object value = ((ValueExpr)e).getValue();

            if(value instanceof String) {
            	typeReturn = "String";
            	            }
            else if(value instanceof Integer) {
            	typeReturn = "Int";
            }
            else if(value instanceof Boolean) {
            	typeReturn = "Bool";
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
}
