package coolc;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import coolc.ast.*;
import coolc.infrastructure.ClassScope;
import coolc.infrastructure.MethodScope;
import coolc.infrastructure.SymTable;

public class CodegenPrinter {

	boolean file_output = true;
	
    private Program _root;
    private boolean _printTypes;
    
    private SymTable _symTable;
    ClassScope classScope;
    MethodScope methodScope;
    
    
    ClassDef current_class = null;
    
    CodegenConstantsPrinter constants;

    String namelocalvars = "lvars_";
    String namelocalvari = "lvari_";
    String namelocalvarb = "lvarb_";
    int local_vars = 0;
    int local_vari = 0;
    int local_varb = 0;
    
    int ifcounter = 0;

    public CodegenPrinter(Program root) {
        this(root, false);
    }

    public CodegenPrinter(Program root, boolean printTypes) {
        
    	if(file_output){
	    	try {
				System.setOut( new PrintStream("/home/komandox/Escritorio/Compiladores/4/out.ll"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	_root = root;
        _printTypes = printTypes;
        
        _symTable = new SymTable(root);
        // 
    }

    public void print() {
        
    	// imprimir constantes
    	constants = new CodegenConstantsPrinter(_root);
    	constants.print();
    	
    	System.out.println();
    	
    	//System.out.println("program");
    	
    	System.out.println("%Object = type { i8* }");
    	System.out.println("%IO = type { i8* }");
    	System.out.println();
        
    	// clases
        for(ClassDef c: _root) { 
        	current_class = c;
            printClass(c);
        }
        
        
        System.out.println();
        System.out.println("define i32 @main() {");
        printIndent(1);
        System.out.println("call " + GetCurrentClassLL() +" @" + current_class.getType() + "_main(" + GetCurrentClassLL() + " null)");
        printIndent(1);
        System.out.println("ret i32 0");
        System.out.println("}");
        System.out.println();
        
        // recorrer arbol
        for(ClassDef c: _root) {            
        	current_class = c;
        	print(c);
        }
        
        if(file_output){
	        System.out.println();
	        System.out.println("declare %Object* @Object_abort(%Object*)");
	        System.out.println("declare i8* @Object_type_name(%Object*)");
	        System.out.println("declare %IO* @IO_out_string(%IO*, i8*)");
	        System.out.println("declare %IO* @IO_out_int(%IO*, i32 )");
	        System.out.println("declare i8* @IO_in_string(%IO*)");
	        System.out.println("declare i32 @IO_in_int(%IO*)");
	        System.out.println("declare i32 @String_length(i8*)");
	        System.out.println("declare i8* @String_concat(i8*, i8*)");
	        System.out.println("declare i8* @String_substr(i8*, i32, i32 )");
	        System.out.println("declare i32 @strcmp(i8*, i8*)");
	        
	          
        }
    }

    private void printClass(ClassDef c) {
        //printIndent(1);
        System.out.println("%" + c.getType() + "= type { i8* }");

        //System.out.printf("class %s", c.getType());
        if( c.getSuper() != null ) {
            //System.out.printf(" : %s", c.getSuper());
        }
        //System.out.println();
        
        
        classScope = _symTable.findClass(c.getType());
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
            
            
            methodScope = classScope.getMethod(m.getName());
            
            System.out.print("define " + GetTypeLL(m.getType()) + " @" + current_class.getType() + "_" + m.getName() + "(");
            
            System.out.print(GetCurrentClassLL() + " %m");
            
            for(Variable var: m.getParams()) {
            	
                System.out.print(", " + GetTypeLL(var.getType()) + " %" + var.getId());

                if( var.getValue() != null ){
                    throw new RuntimeException("WTF? initializing parameter definition?");
                }
            }
            
            System.out.println(") {");
            //System.out.println(m.getType());            
            
            printIndent(1);
            System.out.println("%_tmp_1 = bitcast " + GetCurrentClassLL() +" %m to " + GetCurrentSuperClassLL());

            print(m.getBody(), 3);
            
            //     ret %Main* %m
            printIndent(1);
            
            String type = m.getType();
            
            if(type.equals("String")){
                System.out.println("ret " + GetTypeLL(m.getType()) + " %" + getLocalVars());
        	}
        	else if(type.equals("Int")){
                System.out.println("ret " + GetTypeLL(m.getType()) + " %" + getLocalVari());
        	}
        	else if(type.equals("Bool")){
                System.out.println("ret " + GetTypeLL(m.getType()) + " %" + getLocalVarb());
        	}
        	else if(type.equals("SELF_TYPE") || type.equals("Object")){
                System.out.println("ret " + GetTypeLL(m.getType()) + " %m");
        	}
            
            
            
            
            System.out.println("}");
            System.out.println();
        }
        else if (f instanceof Variable) {
            Variable var = (Variable)f;
            //printIndent(2);
            //System.out.printf("field %s %s\n", var.getType(), var.getId());
            if( var.getValue() != null ) {
               // print(var.getValue(), 3);
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
        	//System.out.println("ASSIGN");
        	
        	//             	

        	
        	
            //printTag(String.format("assign %s", ((AssignExpr)e).getId()), e);

            print(((AssignExpr)e).getValue(), indent+1);
            

        	AssignExpr assign = (AssignExpr)e;
        	
        	if(assign.getExprType().equals("Int")){
        		String varname = assign.getId();
        		printIndent(1);
        		System.out.println("store i32 %" + getLocalVari() +", i32* @" + varname);
        	}
        	else if(assign.getExprType().equals("String")){
        		String varname = assign.getId();
        		printIndent(1);
        		System.out.println("store i8* %" + getLocalVars() +", i8** @" + varname);
        	}
        	else if(assign.getExprType().equals("Bool")){
        		String varname = assign.getId();
        		printIndent(1);
        		System.out.println("store i1 %" + getLocalVarb() +", i1* @" + varname);
        	}
        }        
        else if(e instanceof DispatchExpr) {
            DispatchExpr call = (DispatchExpr)e;

            
            String name_var_callee = "";
            
            // printTag(out.toString(), e);
            

            if( call.getExpr() != null ) {
                
            	//printIndent(indent+1);
                //System.out.println("callee");
                print(call.getExpr(), indent+2);
                
                if(call.getExpr().getExprType().equals("Int")){
                	name_var_callee = getLocalVari();
                }
            	else if(call.getExpr().getExprType().equals("String")){
            		name_var_callee = getLocalVars();
            	}
            	else if(call.getExpr().getExprType().equals("Bool")){
            		name_var_callee = getLocalVarb();
            	}
            }
            
            String argumentos = "";
            if (call.getArgs().size() > 0) {
                //printIndent(indent+1);
                //System.out.println("args");
            	
            	 
            	int iarg = 0;
                for(Expr arg: call.getArgs()) {
                    
                	
                    String returnvar = "";
                
                    if(!call.getName().equals("out_string") && !call.getName().equals("out_int") && !call.getName().equals("in_string") && !call.getName().equals("in_int")){
                    	
                    
                    }
                    
                    print(arg, indent+2); // SSS
                    
                    if(!call.getName().equals("out_string") && !call.getName().equals("out_int") && !call.getName().equals("in_string") && !call.getName().equals("in_int")){
                    	
                    	if(iarg != 0){
                    		argumentos += ", ";
                    	}
                    
	                    if(arg.getExprType().equals("Int")){
	                        argumentos += "i32 %" + getLocalVari();
	                    }
	                	else if(arg.getExprType().equals("String")){
	                        argumentos += "i8* %" + getLocalVars();
	                    }
	                	else if(arg.getExprType().equals("Bool")){
	                        argumentos += "i1 %" + getLocalVarb();
	                	}
                    }
                    
                    iarg++;
                    
                }
            }
            
            String callname = call.getName();
            
            printIndent(1);
            //System.out.print("call ");
            
            // IO
            if(callname.equals("out_string")){
                //call %IO* @IO_out_string(%IO* %_tmp_1, i8* bitcast ([13 x i8]* @msg to i8*))
            	
            	String vars_name = "";//constants.getValueVar(); 
            	
            	//System.out.println("%IO* @IO_out_string(%IO* %_tmp_1, i8* bitcast ([" + _var_value.length() + " x i8]* @"+_var_name+" to i8*))");
            	System.out.println("call %IO* @IO_out_string(%IO* %_tmp_1, i8* %" + getLocalVars() +")");

            }
            
            else if(callname.equals("out_int")){

            	//String _var_name = "vari_" + c_vari++;
            	//String _var_value = constants.getValueVar(_var_name); 
            	
            	
                System.out.println("call %IO* @IO_out_int(%IO* %_tmp_1, i32 %" + getLocalVari() + ")");
            }
            else if(callname.equals("in_string")){
            	// i8* @in_string(%IO* %self)
            	System.out.println("%" + getNextLocalVars() + " = call i8* @IO_in_string(%IO* %_tmp_1)");
            }
            else if(callname.equals("in_int")){
            	// i32 @in_int(%IO* %self)
            	System.out.println("%" + getNextLocalVars() + " = call i32 @IO_in_int(%IO* %_tmp_1)");
            }
            
            // STRING
            else if(callname.equals("length")){
            	// i32 @String_length(i8* %self)
            	
            	System.out.println("%" + getNextLocalVari() + " = call i32 @String_length(i8* %" + name_var_callee + ")");
            }
            else if(callname.equals("concat")){
            	// i8* @String_concat(i8* %self, i8* %other)
            	System.out.println("%" + getNextLocalVars() + " = call i8* @String_concat(i8* %" + name_var_callee + ", " + argumentos + ")");
            	//name_var_callee
            	
            	// "d__<".concat(">--b")
            }
			else if(callname.equals("substr")){
				// i8* @String_substr(i8* %self, i32 %i, %32 %l)
            	System.out.println("%" + getNextLocalVars() + " = call i8* @String_substr(i8* %" + name_var_callee + ", " + argumentos + ")");

			}
            else{
            	
      //      	System.out.println(call.getName());
                
            	if(call.getExprType().equals("Int")){
            		System.out.print("%" + getNextLocalVari() + " = ");
            		System.out.println("call " + GetTypeLL(call.getExprType()) + " @Main_" + call.getName() + "(%Main* null, "+ argumentos +")");
            	}
            	else if(call.getExprType().equals("String")){
            		System.out.print("%" + getNextLocalVars() + " = ");
            		System.out.println("call " + GetTypeLL(call.getExprType()) + " @Main_" + call.getName() + "(%Main* null, "+ argumentos +")");
            	}
            	else if(call.getExprType().equals("Bool")){
            		System.out.print("%" + getNextLocalVarb() + " = ");
            		System.out.println("call " + GetTypeLL(call.getExprType()) + " @Main_" + call.getName() + "(%Main* null, "+ argumentos +")");
            	}
            	else {
            		System.out.println("call " + GetTypeLL(call.getExprType()) + " @Main_" + call.getName() + "(%Main* null, "+ argumentos +")");
            	}
            }
        }
        else if(e instanceof IfExpr) {
            IfExpr cond = (IfExpr)e;

            //printTag("if", e);
            print(cond.getCond(), indent+1);

            assert "Bool".equals(cond.getCond().getExprType());

            System.out.println();
            

            String returnvar = "";
            
            // return if
            if(cond.getExprType().equals("Int")){

                returnvar = getNextLocalVari();
                printout(1,"%" + returnvar + " = alloca i32");
            }
        	else if(cond.getExprType().equals("String")){
        		returnvar = getNextLocalVars();
                printout(1,"%" + returnvar + " = alloca i8*");
            }
        	else if(cond.getExprType().equals("Bool")){
        		returnvar = getNextLocalVarb();
                printout(1,"%" + returnvar + " = alloca i1");
        	}
            
            

            
            printIndent(1);
            int varif = ++ifcounter;
            System.out.println("br i1 %" + getLocalVarb() + ", label %then" + ifcounter + ", label %else" + ifcounter);
            //
            
            //printIndent(indent);
            System.out.println("then" + varif + ":");
            print(cond.getTrue(), indent+1);
            
            
            
            // return if
            if(cond.getExprType().equals("Int")){
            	printIndent(1);
            	System.out.println("store i32 %"+ getLocalVari() +", i32* %" + returnvar);
            }
        	else if(cond.getExprType().equals("String")){
        		printIndent(1);
            	System.out.println("store i8* %"+ getLocalVars() +", i8** %" + returnvar);
            }
        	else if(cond.getExprType().equals("Bool")){
        		printIndent(1);
            	System.out.println("store i1 %"+ getLocalVarb() +", i1* %" + returnvar);
        	}
            
            
            printIndent(1);
            System.out.println("br label %ifcont" + varif);
            
            //printIndent(indent);
            System.out.println("else" + varif + ":");
            print(cond.getFalse(), indent+1);
            
            
            // return else
            if(cond.getExprType().equals("Int")){        		
            	printIndent(1);
            	System.out.println("store i32 %"+ getLocalVari() +", i32* %" + returnvar);
        	}
        	else if(cond.getExprType().equals("String")){
        		printIndent(1);
            	System.out.println("store i8* %"+ getLocalVars() +", i8** %" + returnvar);
           	}
        	else if(cond.getExprType().equals("Bool")){
        		printIndent(1);
            	System.out.println("store i1 %"+ getLocalVarb() +", i1* %" + returnvar);
        	}
            
            
            printIndent(1);
            System.out.println("br label %ifcont" + varif);
            
         
            
            System.out.println("ifcont" + varif + ":");
            System.out.println();
            
            
            
            // return if
            if(cond.getExprType().equals("Int")){        		
            	String returnvar2 = getNextLocalVari();
                printout(1,"%" + returnvar2 + " = load i32* %" + returnvar);
        	}
        	else if(cond.getExprType().equals("String")){
        		String returnvar2 = getNextLocalVars();
                printout(1,"%" + returnvar2 + " = load i8** %" + returnvar);
        	}
        	else if(cond.getExprType().equals("Bool")){
        		String returnvar2 = getNextLocalVarb();
                printout(1,"%" + returnvar2 + " = load i1* %" + returnvar);
        	}
            
            // AQUI
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
            
            if(expr.getOp() == UnaryOp.NEGATE){
            	
            	String namelocal = getLocalVari();
            	
            	printIndent(1);
            	System.out.println("%" + getNextLocalVari() + " = mul i32 %" + namelocal + ", -1");
            	
            	printIndent(1);
            	System.out.println("%" + getNextLocalVari() + " = sub i32 -1, %" + namelocal + "");

            }
            else if(expr.getOp() == UnaryOp.NOT){
            	printIndent(1);
            	String namelocal = getLocalVarb();
            	System.out.println("%" + getNextLocalVarb() + " = icmp eq i1 %" + namelocal + ", 0");
            }
        }
        else if(e instanceof BinaryExpr) {
            
        	BinaryExpr expr = (BinaryExpr)e;
            //printTag(String.format("binary %s", operator(expr.getOp())), e);

        	/*
        	 else if(expr.getOp() == BinaryOp.EQUALS){
            	printIndent(1);
            	System.out.println("%" + getNextLocalVarb() + " = icmp eq i32 %" + name3 + ", %" + name4);
            }
        	 */
        	
        	String var_ant = "";
        	String name1 = "";
        	String name2 = "";
        	
        	if(expr.getLeft().getExprType().equals("String")){

        		name1 = getNextLocalVars();
        		printout(1,"%" + name1 + " = alloca i8*");
        	
        		name2 = getNextLocalVars();
        		printout(1,"%" + name2 + " = alloca i8*");
        	}
        	else{
        		name1 = getNextLocalVari();
        		printout(1,"%" + name1 + " = alloca i32");
        	
        		name2 = getNextLocalVari();
        		printout(1,"%" + name2 + " = alloca i32");
        	}
        	
        	// Expresion 1
            print(expr.getLeft(), indent + 1);    
            
            if(expr.getLeft().getExprType().equals("String")){
                System.out.println("store i8* %"+ getLocalVars() +", i8** %" + name1);
        	}
        	else{
                System.out.println("store i32 %"+ getLocalVari() +", i32* %" + name1);
        	}

            print(expr.getRight(), indent + 1);
            
            if(expr.getLeft().getExprType().equals("String")){
                System.out.println("store i8* %"+ getLocalVars() +", i8** %" + name2);
        	}
        	else{
                System.out.println("store i32 %"+ getLocalVari() +", i32* %" + name2);
        	}

            
            String name3 = "";
            String name4 = "";
            
            
            
            if(expr.getLeft().getExprType().equals("String")){
            	name3 = getNextLocalVars();
                printout(1,"%" + name3 + " = load i8** %" + name1);
                
                name4 = getNextLocalVars();
                printout(1,"%" + name4 + " = load i8** %" + name2);
            }
        	else{
                name3 = getNextLocalVari();
                printout(1,"%" + name3 + " = load i32* %" + name1);
                
                name4 = getNextLocalVari();
                printout(1,"%" + name4 + " = load i32* %" + name2);
        	}
            
            
            if(expr.getOp() == BinaryOp.PLUS){
            	printIndent(1);
            	System.out.println("%" + getNextLocalVari() + " = add i32 %" + name3 + ", %" + name4);
            }
            else if(expr.getOp() == BinaryOp.MINUS){
            	printIndent(1);
            	System.out.println("%" + getNextLocalVari() + " = sub i32 %" + name3 + ", %" + name4);
            }
            else if(expr.getOp() == BinaryOp.MULT){
            	printIndent(1);
            	System.out.println("%" + getNextLocalVari() + " = mul i32 %" + name3 + ", %" + name4);
            }
            else if(expr.getOp() == BinaryOp.DIV){
            	printIndent(1);
            	System.out.println("%" + getNextLocalVari() + " = sdiv i32 %" + name3 + ", %" + name4);
            }
            else if(expr.getOp() == BinaryOp.EQUALS){
            	
            	if(expr.getLeft().getExprType().equals("String")){
            		printIndent(1);
                	System.out.println("%" + getNextLocalVari() + " = call i8* @strcmp(i8* %"+name3 + ", i8* %" + name4 + ")");
                	printIndent(1);
                	System.out.println("%" + getNextLocalVarb() + " = icmp eq i32 0, %" + getLocalVari());

            	}
            	else{
            		printIndent(1);
                	System.out.println("%" + getNextLocalVarb() + " = icmp eq i32 %" + name3 + ", %" + name4);
            	}
            	
            }
            else if(expr.getOp() == BinaryOp.LT){
            	printIndent(1); 
            	System.out.println("%" + getNextLocalVarb() + " = icmp slt i32 %" + name3 + ", %" + name4);
            }
            else if(expr.getOp() == BinaryOp.LTE){
            	printIndent(1);
            	System.out.println("%" + getNextLocalVarb() + " = icmp sle i32 %" + name3 + ", %" + name4);
            }

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
        	
        	IdExpr id = ((IdExpr)e);
        	
        	if(id.getExprType().equals("Int")){        		
        		
                String globalvar = id.getId();
                String localvar = getNextLocalVari();
                
                if(methodScope.hasParamField(globalvar)){
                	printout(1,"%" + localvar + " = add i32 %" + globalvar + ", 0");
                }
                else{
                	printout(1,"%" + localvar + " = load i32* @" + globalvar);
                }
                
        	}
        	else if(id.getExprType().equals("String")){
                String globalvar = id.getId();
                String localvar = getNextLocalVars();
            	printIndent(1);
            	
            	if(methodScope.hasParamField(globalvar)){
                	printout(1,"%" + localvar + " = load i8* %" + globalvar);
                }
                else{
                	printout(1,"%" + localvar + " = load i8** @" + globalvar);
                }

        	}
        	else if(id.getExprType().equals("Bool")){
        		
        		String globalvar = id.getId();
                String localvar = getNextLocalVarb();
                
                if(methodScope.hasParamField(globalvar)){
                	
                	printIndent(1);
                	System.out.println("%" + getNextLocalVarb() + " = icmp eq i1 %" + globalvar + ", 1");
                }
                else{
                	printout(1,"%" + localvar + " = load ii* @" + globalvar);
                }
        	}
        	else{
        		
        	}
        	
        	
        }
        else if(e instanceof ValueExpr) {
            Object value = ((ValueExpr)e).getValue();

            if(value instanceof String) {

            	String val = ((String)value);
                String globalvar = constants.getNameVars(val);
                String localvar = getNextLocalVars();
                
            	printIndent(1);
            	System.out.println("%" + localvar +" = load i8** @" + globalvar);

                //printTag(String.format("str \"%s\"", value), e);
            }
            else if(value instanceof Integer) {
                
                assert "Int".equals(e.getExprType());
                //printTag(String.format("int %d", value), e);
                
                int val = ((int)value);
                String globalvar = constants.getNameVari(val);
                String localvar = getNextLocalVari();
                
                printout(1,"%" + localvar + " = load i32* @" + globalvar);
            }
            else if(value instanceof Boolean) {
                assert "Bool".equals(e.getExprType());
                //printTag(String.format("bool %s", value), e);
            
                boolean val = ((boolean)value);
                
                String localvar = getNextLocalVarb();
                String globalvar = "false";
                
                if(val == true){
                	globalvar = "true";
                }
                
                printout(1,"%" + localvar + " = load i1* @" + globalvar);
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

    public String getLocalVari(){
    	return namelocalvari + local_vari;
    }
    
    public String getNextLocalVari(){
    	return namelocalvari + ++local_vari;
    }
    
    
    public String getLocalVars(){
    	return namelocalvars + local_vars;
    }
    
    public String getNextLocalVars(){
    	return namelocalvars + ++local_vars;
    }
    
    public String getLocalVarb(){
    	return namelocalvarb + local_varb;
    }
    
    public String getNextLocalVarb(){
    	return namelocalvarb + ++local_varb;
    }
    
    
    public void printout(int indent, String text){
    	printIndent(indent);
        System.out.println(text);
    }
    
    public String GetCurrentClassLL(){
    	return "%" + current_class.getType() + "*";
    }
    
    public String GetCurrentSuperClassLL(){
    	return "%" + current_class.getSuper() + "*";
    }
    
    public String GetTypeLL(String type){
    	
    	if(type.equals("String")){
    		return "i8*";
    	}
    	else if(type.equals("Int")){
    		return "i32";
    	}
    	else if(type.equals("Bool")){
    		return "i1";
    	}
    	else if(type.equals("SELF_TYPE") || type.equals("Object")){
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