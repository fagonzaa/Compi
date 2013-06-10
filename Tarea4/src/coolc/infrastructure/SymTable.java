package coolc.infrastructure;

import java.util.*;
import coolc.ast.*;
import coolc.parser.*;
import coolc.*;

public class SymTable {

    private LinkedHashMap<String, ClassScope> _classes;
    private HashMap<String, List<String>> _ancestors;
    private TreeSet<SemanticError> _errors;
    private final Class valueClass = new ValueExpr<Object>(null).getClass();

    public SortedSet<SemanticError> getErrors() {
        return _errors;
    }

    public SymTable(Program root) {
        _classes = new LinkedHashMap<String, ClassScope>();
        _classes.put("Object", ClassScope.ObjectClass);
        _classes.put("Int", ClassScope.IntClass);
        _classes.put("String", ClassScope.StringClass);
        _classes.put("Bool", ClassScope.BoolClass);
        _classes.put("IO", ClassScope.IOClass);

        _errors = new TreeSet<SemanticError>(new Comparator<SemanticError>() {
            @Override
            public int compare(SemanticError first, SemanticError second) {
                Position fPos = first.getPos();
                Position sPos = second.getPos();


                if(fPos.line < sPos.line) {
                    return -1;
                }
                else if(fPos.line > sPos.line) {
                    return 1;
                }
                else {
                    if(fPos.column < sPos.column) {
                        return -1;
                    }
                    else if(fPos.column > sPos.column) {
                        return 1;
                    }
                }

                return 0;
            };

        });
        _ancestors = new HashMap<String, List<String>>();

        processClasses(root);
    }

    public String findCommonClass(String t1, String t2) {

        if(t1 == null || t2 == null) {            
            return null;
        }

        System.err.printf("Finding common class between %s and %s\n", t1, t2);

        List<String> chain1 = getAncestors(t1);
        List<String> chain2 = getAncestors(t2);

        

        // System.err.printf("Ancestors for %s: ", t1);
        // for(String c1 : chain1) {
        //     System.err.printf("%s ", c1);
        // }
        // System.err.println();

        // System.err.printf("Ancestors for %s: ", t2);
        // for(String c2 : chain2) {
        //     System.err.printf("%s ", c2);
        // }
        // System.err.println();



        for(String c1 : chain1) {
            for(String c2: chain2) {
                if(c1.equals(c2)) {
                    // System.err.printf("Ancestor found: %s\n\n", c1);
                    return c1;
                }
            }
        }

        // System.err.println("Ancestor not found");

        return null;
    }

    public boolean instanceOf(String valueType, String slotType) {
        
        assert ! "SELF_TYPE".equals(valueType) : "value type must not be SELF_TYPE"; 
        assert ! "SELF_TYPE".equals(slotType) : "slot type must not be SELF_TYPE"; 


        if(valueType == null || slotType == null ) {
            return false;
        }

        boolean result = false;

        for(String type: getAncestors(valueType)) {
            if( type.equals(slotType) ) {
                result = true;
            }
        }
        return result;
    }

    public ClassScope findClass(String name) {
        return _classes.get(name);
    }

    private List<String> getAncestors(String type) {

        assert !"SELF_TYPE".equals(type) : "type must not be SELF_TYPE"; 

        if(type == null) {
            return null;
        }

        List<String> ancestors = _ancestors.get(type);

        if( ancestors == null ) {
            ancestors = new ArrayList<String>();

            String generation = type;
            while( generation != null ) {
                ancestors.add(generation);
                generation = _classes.get(generation).getSuperType();
            }

            _ancestors.put(type, ancestors);

        }

        return ancestors;
    }

    private boolean classExists(String name) {
        return findClass(name) != null || "SELF_TYPE".equals(name);
    }

    private void checkFieldsClasses(Scope scope) {
        List<String> failed = new ArrayList<String>();

        for(Map.Entry<String, Field> fieldEntry: scope.listFields()) {
            Field field = fieldEntry.getValue();

            if( !classExists(field.getType()) ) {
                failed.add(fieldEntry.getKey());
                _errors.add(new SemanticError(String.format("Undefined class %s", field.getType()), field.getNode().getPos()));
            }
        }   

        for(String name: failed) {
            scope.removeField(name);
        }
    }

    private void processClasses(Program root)  {

        List<Method> allMethods = new ArrayList<Method>();

        for(ClassDef c: root) {

            if(_classes.containsKey(c.getType())) {
                _errors.add(new SemanticError(String.format("Duplicate class %s", c.getType()), c.getPos()));
            }

            ClassScope scope;
            if( c.getSuper() != null) {
                switch(c.getSuper()) {

                    case "Int":
                    case "Bool":
                    case "String":
                        _errors.add(new SemanticError(String.format("Can't inherit from %s", c.getSuper()), c.getPos()));
                        scope = new ClassScope(c.getType(), c);
                        break;

                    default:
                        scope = new ClassScope(c.getType(), c.getSuper(), c);
                        break;
                }
            }
            else {
                scope = new ClassScope(c.getType(), c);
            }

            for(Feature feature: c.getBody()) {
                if(feature instanceof Method) {
                    Method method = (Method)feature;
                    String type = method.getType();
                    MethodScope methodScope = new MethodScope(scope, type, method);
                    method.setScope(methodScope);

                    for(Variable var : method.getParams()) {

                        assert var.getValue() == null;                        
                        Field field = new Field(var.getType(), methodScope, var);
                        methodScope.addField(var.getId(), field);

                    }

                    allMethods.add(method);

                    scope.addMethod(method.getName(), methodScope);

                } 
                else if(feature instanceof Variable) {
                    Variable var = (Variable)feature;
                    Field field = new Field(var.getType(), scope, var);
                    
                    scope.addField(var.getId(), field);

                }
                    
            }

            _classes.put(c.getType(), scope);
        }

        
        // Check classes
        for(Map.Entry<String, ClassScope> entry: _classes.entrySet()) {

            ClassScope c = entry.getValue();

            // Connect super-type scopes
            if(c.getSuperType() != null) {
                ClassScope superScope = _classes.get(c.getSuperType());
                if(superScope != null) {
                    c.setSuperScope(superScope);
                }
                else {
                    _errors.add(new SemanticError(String.format("Undefined class %s", c.getSuperType()), c.getNode().getPos()));
                }
            }

            checkFieldsClasses(c);

            List<String> failedMethods = new ArrayList<String>();

            for(Map.Entry<String, MethodScope> methodEntry : c.listMethods()) {
                    MethodScope method = methodEntry.getValue();

                if(!classExists(method.getReturnType())) {
                    failedMethods.add(methodEntry.getKey());
                    _errors.add(new SemanticError(String.format("Undefined class %s", method.getReturnType()), method.getNode().getPos()));
                }
                else {
                    checkFieldsClasses(method);
                }
            }

            for(String name: failedMethods) {
                c.removeMethod(name);
            }

        }



        // Find incorrectly overloaded methods 
        for(Map.Entry<String, ClassScope> entry: _classes.entrySet()) {

            ClassScope c = entry.getValue();
            ClassScope superScope = c.getSuperScope();

            if(superScope != null) {

                for(Map.Entry<String, MethodScope> methodEntry : c.listMethods()) {
                    MethodScope method = methodEntry.getValue();
                    MethodScope superMethod = superScope.getMethod(methodEntry.getKey());

                    if(superMethod != null) {                    

                        boolean canOverride = true;

                        if(!method.getReturnType().equals(superMethod.getReturnType())) {
                            canOverride = false;
                        }

                        List<String> methodArgNames = method.getParams();
                        List<String> superArgNames = superMethod.getParams();


                        if(methodArgNames.size() != superArgNames.size()) {
                            canOverride = false;
                        }
                        else {

                            for (int i = 0; i < methodArgNames.size(); i++ ) {
                                Field argField = method.getLocalField(methodArgNames.get(i));
                                Field superField = superMethod.getLocalField(methodArgNames.get(i));
                                
                                if(!argField.getType().equals(superField.getType())) {
                                    canOverride = false;
                                }
                            }
                        }



                        if(!canOverride) {
                            
                            _errors.add(new SemanticError(
                                String.format("Cannot override method %s.%s %s with %s.%s %s", 
                                    superScope.getClassType(), methodEntry.getKey(), superMethod.getSignature(), 
                                    c.getClassType(), methodEntry.getKey(), method.getSignature()
                                    ),
                                method.getNode().getPos()
                            ));
                        }

                    }
                }
            }

        }


        for(Map.Entry<String, ClassScope> entry: _classes.entrySet()) {

            switch(entry.getKey()) {
                case "Object":
                case "Int":
                case "String":
                case "Bool":
                case "IO":
                    continue;
            }

            ClassScope c = entry.getValue();

            for(Feature feature : ((ClassDef)c.getNode()).getBody())
            {
                if(feature instanceof Variable) {
                    Variable var = (Variable)feature;
                    Field field = c.getLocalField(var.getId());
                    if(var.getValue() != null) {
                        field.getScopes().addAll( processExpr(var.getValue(), field));
                    }
                }
            }

        }

        for(Method method: allMethods) {
            method.getScope().getScopes().addAll(
                processExpr(method.getBody(), method.getScope())
                );
        }

    }

    public List<Scope> processExpr(Expr expr, Scope parent) {

        ArrayList<Scope> scopes = new ArrayList<Scope>();

        if (expr instanceof LetExpr ) {
            LetExpr let = (LetExpr)expr;
            Scope letScope = new Scope(parent);
            for(Variable var : let.getDecl()){
                addVariable(var, letScope);
            }
            letScope.getScopes().addAll(processExpr(let.getValue(), letScope));
            let.setScope(letScope);
            scopes.add(letScope);
        }
        else if (expr instanceof CaseExpr) {            
            CaseExpr cExpr = (CaseExpr)expr;

            for(Case c: cExpr.getCases()) {

                Scope caseScope = new Scope(parent);
                addCase(c, caseScope);
                
                c.setScope(caseScope);
                scopes.add(caseScope);
            
            }
        }
        else if( expr instanceof Block) {
            for(Expr stmt : ((Block)expr).getStatements()) {
                scopes.addAll(processExpr(stmt, parent));
            }
        }
        else if( expr instanceof IfExpr ) {
            IfExpr ifExpr = (IfExpr)expr;
            scopes.addAll(processExpr(ifExpr.getCond(), parent));
            scopes.addAll(processExpr(ifExpr.getTrue(), parent));
            scopes.addAll(processExpr(ifExpr.getFalse(), parent));

        }
        else if(expr instanceof DispatchExpr ) {
            DispatchExpr call = (DispatchExpr)expr;
            if( call.getExpr() != null ) {
                scopes.addAll(processExpr(call.getExpr(), parent));
            }
            for (Expr arg : call.getArgs() ) {
                scopes.addAll(processExpr(arg, parent));
            }

        }
        else if( expr instanceof BinaryExpr ) {
            scopes.addAll(processExpr(((BinaryExpr)expr).getLeft(), parent));
            scopes.addAll(processExpr(((BinaryExpr)expr).getRight(), parent));
        }
        else if( expr instanceof UnaryExpr ) {
            scopes.addAll(processExpr(((UnaryExpr)expr).getValue(), parent));

        }
        else if( expr instanceof WhileExpr ) {
            scopes.addAll(processExpr(((WhileExpr)expr).getCond(), parent));
            scopes.addAll(processExpr(((WhileExpr)expr).getValue(), parent));
        }
        else if( expr instanceof AssignExpr ) {
            scopes.addAll(processExpr(((AssignExpr)expr).getValue(), parent));
        }
        else if( expr.getClass().equals(valueClass) || expr instanceof NewExpr || expr instanceof IdExpr ) {
            // Nothing to do here
        }
        else {
            throw new RuntimeException(String.format("Unmanaged node type %s", expr.getClass()));
        }

        return scopes;
    }

    private void addCase(Case c, Scope scope) {

        String type = c.getType();

        if(!classExists(type)) {
            _errors.add(new SemanticError(String.format("Undefined class %s", type), c.getPos()));
            type = "Object";
        }

        Field field = new Field(type, scope, c);

        field.getScopes().addAll(processExpr(c.getValue(), scope));

        scope.addField(c.getId(), field);
    }

    private void addVariable(Variable var, Scope scope) {
        String type = var.getType();

        if(!classExists(type)) {
            _errors.add(new SemanticError(String.format("Undefined class %s", type), var.getPos()));
            type = "Object";
        }

        Field field = new Field(type, scope, var);

        if( var.getValue() != null) {
            field.getScopes().addAll(processExpr(var.getValue(), scope));
        }
        
        scope.addField(var.getId(), field);
    }


    public void print() {

        StringBuilder out = new StringBuilder();


        for(Map.Entry<String, ClassScope> c: _classes.entrySet())  {

            switch(c.getKey()) {

                case "Object":
                case "Int":
                case "String":
                case "Bool":
                case "IO":
                    continue;
            }

            String superType = c.getValue().getSuperType();

            out.append("class ").append(c.getKey())
                .append(" : ").append(superType).append("\n");
            
            out.append("    fields\n");


            for(Map.Entry<String, Field> field: c.getValue().listFields()) {
                if("self".equals(field.getKey())) {
                    continue;
                }

                printField(field, 2, out);

            }

            out.append("    methods\n");

            for(Map.Entry<String, MethodScope> method: c.getValue().listMethods()) {
                out.append("        ").append(method.getValue().getReturnType()).append(" ")
                    .append(method.getKey()).append("\n");

                Set<Map.Entry<String, Field>> args = method.getValue().listFields();

                if( args.size() > 0 ) {
                    out.append("            arguments\n");

                    for(Map.Entry<String, Field> field : args) {
                        printField(field, 4, out);
                    }
                    
                }

                out.append("            body\n");
                for(Scope s: method.getValue().getScopes()) {
                    printScope(s, 4, out);
                }
            }

        }

        System.out.println(out);
    }

    private void printField(Map.Entry<String, Field> field, int indent, StringBuilder out) {
        for(int i = indent; i > 0; i--) {
            out.append("    ");
        }

        out.append(field.getValue().getType()).append(" ")
                            .append(field.getKey()).append("\n");

        for(Scope s: field.getValue().getScopes()) {
            printScope(s, indent+1, out);
        }

    }

    private void printScope(Scope scope, int indent, StringBuilder out) {
        for(int i = indent; i > 0; i--) {
            out.append("    ");
        }

        out.append("scope\n");

        for(Map.Entry<String, Field> f: scope.listFields()) {
            printField(f, indent+1, out);
        }


        for(Scope s: scope.getScopes()) {
            printScope(s, indent+1, out);
        }

    }

    public void printErrors() {
        for(SemanticError e: _errors) {
            String desc = String.format("%d:%d %s", e.getPos().line, e.getPos().column, e.getError());
            System.out.println(desc);
        }
    }

}