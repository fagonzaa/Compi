package coolc.symtable;

import coolc.parser.*;
import coolc.parser.Parser.Location;
import coolc.ast.*;

import java.io.*;
import java.util.*;

public class SymTable {
	
	LinkedHashMap<String, ClassScope> classes;
	String filename;

	
	public LinkedHashMap<String, ClassScope> getClassesScope()
	{
		return classes;
	}
	
	public ClassScope getClassScope(String nameclass)
	{
		return classes.get(nameclass);
	}
	
	public SymTable(Program root, String filename) {
	
		this.filename = filename;
		this.classes = new LinkedHashMap<String, ClassScope>();
		this.filename = "";
		
		for(ClassDef c: root) {  
			ClassScope scope = new ClassScope(c);
			addClass(c, scope);
            
            
            for(Feature f: c.getBody()) {
            	if(f instanceof Method) {
                    Method m = (Method)f;
                    addMethod(m, scope);
                }
                else if (f instanceof Variable) {
                    Variable var = (Variable)f;
                    addField(var, scope);
                }
                else {
                }
            }
        }
	}
	
	private void addClass(ClassDef c, ClassScope scope){
		if(classes.get(c.getType()) != null){
			show(filename + "" + c.getPosition().line + ":" + c.getPosition().column + " Duplicate class " + c.getType(), 0);
		}
		else{
			classes.put(c.getType(), scope);
		}
	}
	
	private void addField(Variable var, Scope scope) {
		
		
		String name = var.getId();
		String type = var.getType();
		
		if(scope.get(name) != null){
			show(filename + "" + var.getPosition().line + ":" + var.getPosition().column + " Duplicate var " + name, 0);
		}
		else{
			Symbol s = new Symbol( name, type, scope);
    		scope.add(name, s);
		}
    }
	
	private void addFieldCase(Case var, Scope scope) {
		
		String name = var.getId();
		String type = var.getType();
		
		if(scope.get(name) != null){
			show(filename + "" + var.getPosition().line + ":" + var.getPosition().column + " Duplicate var " + name, 0);
		}
		else{
			Symbol s = new Symbol( name, type, scope);
    		scope.add(name, s);
		}
    }
	
	private void addMethod(Method method, ClassScope classScope) {

		String name = method.getName();
        String type = method.getType();
        
		if(classes.get(name) != null){
			show(filename + "" + method.getPosition().line + ":" + method.getPosition().column + " Duplicate method " + name, 0);
		}
		else{
			MethodScope scope = new MethodScope(classScope);

	        MethodSymbol s = new MethodSymbol(name, type, classScope, scope);
	        
	        classScope.add(name, s);
	        
	        addParameterList(method, s);

	        fillBlock(method.getBody(),	scope, scope);
		}
    }
	
	private void addParameterList(Method method, MethodSymbol symbol) {
        
		for(Variable var: method.getParams()) {
			
			String name = var.getId();
			String type = var.getType();
			
			if(symbol.methodScope.get(name) != null){
				show(filename + "" + var.getPosition().line + ":" + var.getPosition().column + " Duplicate var " + name, 0);
			}
			else{
				Symbol s = new Symbol( name, type, symbol.methodScope);
				symbol.methodScope.addParameter(name, s);
			}
        }
    }
	
    private void fillBlock(Expr e, Scope parent, MethodScope methodScope) {

    	LocalScope localScope;

        if(e instanceof Block) {
            for(Expr child: ((Block)e).getStatements()) {
            	fillBlock(child, parent, methodScope);
            }
        }
        else if(e instanceof WhileExpr) {
            WhileExpr loop = (WhileExpr)e;

        	fillBlock(loop.getCond(), parent, methodScope);
            
        	fillBlock(loop.getValue(), parent, methodScope);
        }
        else if(e instanceof AssignExpr) {
            fillBlock(((AssignExpr)e).getValue(), parent, methodScope);
        }        
        else if(e instanceof DispatchExpr) {
            DispatchExpr call = (DispatchExpr)e;

            if( call.getExpr() != null ) {
                fillBlock(call.getExpr(), parent, methodScope);
            }
            if (call.getArgs().size() > 0) {
                for(Expr arg: call.getArgs()) {
                    fillBlock(call.getExpr(), parent, methodScope);
                }
            }
        }
        else if(e instanceof IfExpr) {
            IfExpr cond = (IfExpr)e;

            fillBlock(cond.getCond(), parent, methodScope);
        	fillBlock(cond.getTrue(), parent, methodScope);
        	fillBlock(cond.getFalse(), parent, methodScope);
        }
        else if(e instanceof NewExpr) 
        {
        }
        else if(e instanceof UnaryExpr) {
            UnaryExpr expr = (UnaryExpr)e;
            fillBlock(expr.getValue(), parent, methodScope);
        }
        else if(e instanceof BinaryExpr) {
            BinaryExpr expr = (BinaryExpr)e;  
            
            fillBlock(expr.getLeft(), parent, methodScope);
            fillBlock(expr.getRight(), parent, methodScope);
        }
        else if (e instanceof CaseExpr) {
        	
            CaseExpr caseExpr = ((CaseExpr)e);
            fillBlock(caseExpr.getValue(), parent, methodScope);

            for(Case c : caseExpr.getCases()) {
                
            	localScope = new LocalScope(parent, methodScope);
            	addFieldCase(c, localScope);
            	
            	fillBlock(c.getValue(), parent, methodScope);
            }
        }
        else if (e instanceof LetExpr) {
        	
            localScope = new LocalScope(parent, methodScope);
        	
            LetExpr let = (LetExpr)e;
            
            for(Variable var : let.getDecl()) {
            	addField(var, localScope);
            	
            	if(var.getValue() != null) {
                    fillBlock(var.getValue(), localScope, methodScope);
                }
            }

        	fillBlock(let.getValue(), localScope, methodScope); 
        }
        else if(e instanceof IdExpr) {
        	//System.out.printf("id %s\n", ((IdExpr)e).getId());
        	
        	IdExpr idExpr = (IdExpr)e; 
        	String name = idExpr.getId();
        	if(!isDefined(parent, name)){
        		if(name.compareTo("self") != 0){
        			show(filename + "" + idExpr.getPosition().line + ":" + idExpr.getPosition().column + " Undefined var " + name, 0);
        		}
        	}
        }
        else if(e instanceof ValueExpr) {	
        }
        else {

        }

    }
	
    public boolean isMethodDefine(ClassScope scope, String nameMethod){
    	
    	Symbol s = scope.get(nameMethod);
    	
    	if(s == null){
    		
    		if(scope.classDef.getSuper() != null){
    			while(scope.classDef.getSuper() != null){
    				scope = classes.get(scope.classDef.getSuper());
    				
    				if(scope == null){
    					return false;
    				}
    				else{
    					s = scope.get(nameMethod);
    					
    					if(s != null){
    						return true;
    					}
    				}
    			}
    		}
    		
    		return false;
    	}
    	
    	return true;
    }
    
    public Symbol getSymbol(Scope scope, String val){
    	
    	if(scope instanceof ClassScope){
    		ClassScope s = (ClassScope)scope;

    		return s.get(val);
    	}
    	else if(scope instanceof MethodScope){
    		MethodScope s = (MethodScope)scope;
    		
    		Symbol symbol = s.get(val);
    		
    		if(symbol != null){
    			return symbol;
    		}
    		else{
    			Symbol symbolParam = s.getParameter(val);
    			
    			if(symbolParam != null)
    				return symbolParam;
    			else
    				return getSymbol(s.parent, val);
    		}
    	}
    	else if(scope instanceof LocalScope){
    		LocalScope s = (LocalScope)scope;
    		Symbol symbol = s.get(val);
    		
    		if(symbol != null){
    			return symbol;
    		}
    		else{
    			return getSymbol(s.parent, val);
    		}
    	}
    	
    	return null;
    }
    
    public boolean isDefined(Scope scope, String val){
    	
    	return (getSymbol(scope, val) != null);
    }
	
	
	
	public void print(){		
		
		Set<Map.Entry<String, ClassScope>> set = classes.entrySet();
	    Iterator<Map.Entry<String, ClassScope>> it = set.iterator();
	    
	    while (it.hasNext()) {
	      Map.Entry<String, ClassScope> entry = it.next();
	      String id = entry.getKey();
	      

			ClassScope classScope = classes.get(id);
			
			String class_super = "Object";
			if( classScope.getClassDef().getSuper() != null ) {
				class_super = classScope.getClassDef().getSuper();
	        }
			
			show("class " + id + " : " + class_super, 0);
			
			show("fields", 1);
			for( Symbol s : classScope.getSymbols().values() ) {
              
              if(!(s instanceof MethodSymbol)) {
            	  show(s.type + " " + s.id, 2);
              }
			}
			
			show("methods", 1);
			for( Symbol s : classScope.getSymbols().values() ) {
              
              if(s instanceof MethodSymbol) {
            	  show(s.type + " " + s.id, 2);

            	  MethodScope methodScope = ((MethodSymbol)s).methodScope;
            	  if(!methodScope.parameters.isEmpty()){
            		  show("arguments", 3);
            		  
            		  for( Symbol p : methodScope.parameters.values() ) {
            			  show(p.type + " " + p.id, 4);
            		  }
            	  }
            	  
            	  show("body", 3);
            	  printScope(methodScope, 4);
              }
			}
	    }
	}
	
	
	private void printScope(Scope parent, int indent){
		
		for(Scope scope : parent.subscopes){
			show("scope", indent);
			
			for( Symbol s : scope.getSymbols().values() ) {
				show(s.type + " " + s.id, indent + 1);
			}
			
			printScope(scope, indent + 1);
		}
	}
	
	
	public void show(String text, int indent){
		
		String pre = "";
		
		for (int i = indent; i > 0 ; i-- ) {
            pre += "    ";
        }
		
		System.out.println(pre + text);
	}
	
/*    public void print()
    {
        for( Enumeration<String> it = classes.keys(); it.hasMoreElements(); ) {
            String id = it.nextElement();
            System.out.println("type: " + id);
            for( Symbol s : classes.get(id).getSymbols().values() ) {
                
                if(s instanceof MethodSymbol) {

                    StringBuilder paramBuilder = new StringBuilder();

                    System.out.print(String.format("%s %s %s(", modifierStr(s.visibility), s.type, s.id ));
                    
                    for( Symbol p : ((MethodSymbol)s).methodScope.parameters.values() ) {
                        paramBuilder.append(p.type);
                        paramBuilder.append(" ");
                        paramBuilder.append(p.id);
                        paramBuilder.append(", ");
                    }

                    // Quitamos la coma de al final
                    if( paramBuilder.length() >= 2) {
                        paramBuilder.setLength(paramBuilder.length() - 2);
                    }

                    System.out.println(String.format("%s)", paramBuilder));

                    print(((MethodSymbol)s).methodScope, 1);

                }
                else {
                    System.out.println(String.format("%s %s %s", modifierStr(s.visibility), s.type, s.id ));
                }

            }
        }
    }

    public String modifierStr(Visibility v) {
        switch(v) {
            case PUBLIC:
                return "public";

            case PROTECTED:
                return "protected";

            case PRIVATE:
                return "private";

            default:
                throw new IllegalArgumentException("Invalid modifier ");
        }
    }
    
    public void print(Scope scope, int indent) {

        StringBuilder indentation = new StringBuilder();
        for(int i = 0; i < indent; i++) {
            indentation.append("    ");
        }
        for(int i = 0; i < indent; i++) {
            indentation.append(">");
        }

        for( Enumeration<Symbol> sit = scope.getSymbols().elements(); sit.hasMoreElements(); ) {            
                Symbol s = sit.nextElement();
                System.out.println(String.format("%s %s %s", indentation, s.type, s.id ));
        }
        
        for( Scope s : scope.subscopes )
        {
            System.out.println(String.format("%s BLOCK", indentation));
            print(s, indent+1);
        }
    }

    public SymTable(Tree root) {

        this.classes = new Hashtable<String, ClassScope>();
        this.scopes = new Hashtable<Tree, Scope>();

        if ( root.getType() != MintLexer.PROGRAM ) {
            // ERROR
            return;
        }

        for( int j = 0; j < root.getChildCount(); j++) {

            ClassScope scope = new ClassScope();
            Tree classNode = root.getChild(j);
            String id = classNode.getChild(0).getText();

            if( classes.containsKey(id) ) {
                throw new IllegalArgumentException("class " + id + " already exists");
            }

            classes.put(id, scope);

            for(int i = 1; i < classNode.getChildCount(); i++) {

                Tree member = classNode.getChild(i);
                switch(member.getType())
                {
                    case MintLexer.FIELD:
                        addField(member, scope);
                        break;

                    case MintLexer.METHOD:
                        addMethod(member, scope);
                        break;

                    case MintLexer.CTOR:
                        addCtor(member, scope);
                        break;
                }

            }
        }

    }

    private Visibility parseModifier(int m) {

        switch(m) {
            case MintLexer.PUBLIC:
                return Visibility.PUBLIC;
            
            case MintLexer.PROTECTED:
                return Visibility.PROTECTED;

            case MintLexer.PRIVATE:
                return Visibility.PRIVATE;

            default:
                throw new IllegalArgumentException("Invalid modifier type");
        }

    }

    public static Type parseType(Tree t) {

        switch(t.getType())
        {
            case MintLexer.INT:
                return Type.Int;

            case MintLexer.FLOAT:
                return Type.Float;

            case MintLexer.BOOLEAN:
                return Type.Boolean;

            case MintLexer.VOID:
                return Type.Void;

            case MintLexer.IDENTIFIER:
                if( t.getText().equals("String") ) {
                    return Type.String;
                }
                else {
                    return new Type(t.getText());
                }

            case MintLexer.ARRAY_TYPE:
                return parseType(t.getChild(0)).arrayType();

            default:
                throw new IllegalArgumentException("Invalid modifier type");
        }
    }

    private void addField(Tree node, Scope scope) {

        assert node.getType() == MintLexer.FIELD;

        Visibility modifier = parseModifier(node.getChild(0).getType());
        Type type = parseType(node.getChild(1));
        String name = node.getChild(2).getText();

        Symbol s = new Symbol(name, modifier, type, scope);

        scope.add(name, s);
    }

    private void addParameterList(Tree params, MethodSymbol s) {
        
        assert params.getType() == MintLexer.PARAMS;

        for( int i = 0; i < params.getChildCount(); i++) {

            Tree p = params.getChild(i);

            Tree idTree;

            Type t = parseType(p);

            if( p.getType() == MintLexer.ARRAY_TYPE) {
                
                idTree = p.getChild(1);
                assert t.isArray();
            }
            else {
                
                idTree = p.getChild(0);
                assert !Type.Int.isArray();
                assert !t.isArray();
            }

            assert idTree.getType() == MintLexer.IDENTIFIER;

            String id = idTree.getText();
            s.methodScope.addParameter(id, new Symbol(id, null, t, s.methodScope));
        }
        
    }

    private void addMethod(Tree node, ClassScope classScope) {

        assert node.getType() == MintLexer.METHOD;

        Visibility modifier = parseModifier(node.getChild(0).getType());
        Type type = parseType(node.getChild(1));
        String name = node.getChild(2).getText();

        MethodScope scope = new MethodScope(classScope);

        MethodSymbol s = new MethodSymbol(name, modifier, type, classScope, scope);

        Tree params = node.getChild(3);

        addParameterList(params, s);
        
        fillBlock(node.getChild(4), scope, scope);
        
        classScope.add(name, s);

    }

    private void addCtor(Tree node, ClassScope classScope) {

        assert node.getType() == MintLexer.CTOR;

        Visibility modifier = parseModifier(node.getChild(0).getType());
        String name = node.getChild(1).getText();

        MethodScope scope = new MethodScope(classScope);
        MethodSymbol s = new MethodSymbol(name, modifier, null, classScope, scope);
        
        classScope.add(name, s);

        fillBlock(node.getChild(3), scope, scope);
        
        
    }

    private void fillBlock(Tree node, Scope scope, MethodScope method) {
    
        assert node != null;
        assert scope != null;
        assert scopes != null;
        assert node.getType() == MintLexer.BLOCK;

        scopes.put(node, scope);     
        

        for(int i = 0; i < node.getChildCount(); i++) {

            Tree sentence = node.getChild(i);

            switch (sentence.getType()) {

                case MintLexer.FIELD:
                    addLocal(sentence, scope);
                    break;

                case MintLexer.IF:
                    fillBlock(sentence.getChild(1), new LocalScope(scope, method), method);
                    if( sentence.getChildCount() > 2) {
                        for( int j = 2; j < sentence.getChildCount(); j++ ) {
                            Tree eli = sentence.getChild(j);
                            if( eli.getType() == MintLexer.ELSIF ) {
                                fillBlock(eli.getChild(1), new LocalScope(scope, method), method);
                            }
                            else if( eli.getType() == MintLexer.ELSE ) {
                                fillBlock(eli.getChild(0), new LocalScope(scope, method), method);
                            }
                        }
                    }
                    break;
                
                case MintLexer.DO:
                    fillBlock(sentence.getChild(1), new LocalScope(scope, method), method);
                    break;

                case MintLexer.WHILE:
                    fillBlock(sentence.getChild(1), new LocalScope(scope, method), method);
                    break;
                
                default:
                    return;
            }

        }
    }

    private void addLocal(Tree node, Scope scope) {

        assert node.getType() == MintLexer.FIELD;

        Type type = parseType(node.getChild(0));
        
        for(int i = 1; i < node.getChildCount(); i++) {
            Tree declarator = node.getChild(i);
            assert declarator.getType() == MintLexer.IDENTIFIER;

            String id  = declarator.getText();
            try {
                scope.add(id, new Symbol(id, null, type, scope));    
            }
            catch(IllegalArgumentException e)
            {
                throw new IllegalArgumentException("hsad");
            }
            

        }

    }
*/
}