package coolc.infrastructure;

import coolc.ast.*;
import java.util.*;

public class SemanticAnalizer {

    private SymTable _symTable;
    private Program _root;

    public SemanticAnalizer(Program root) {

        _root = root;
        _symTable = new SymTable(root);

        analizeClasses(_root);
    }

    private boolean checkTypes(String valueType, String slotType, Node node) {
        if( valueType != null && !_symTable.instanceOf(valueType, slotType)) {
            if(node != null) {
                _symTable.getErrors().add(new SemanticError(String.format("Expected %s, received %s", slotType, valueType), node.getPos()));

            }

            return false;
        }
        return true;
    }

    private ClassScope findClass(String name) {
        ClassScope scope = _symTable.findClass(name);


        return scope;
    }

    private String classIfExists(String name, Scope scope) {

        return name.equals("SELF_TYPE") ? scope.getClassScope().getClassType() : (findClass(name) != null ? name : null);
    }

    private void analizeClasses(Program root)  {

        for(ClassDef c: root) {

            ClassScope scope = _symTable.findClass(c.getType());

            assert scope != null : "Fatal error, this is impossible!";

            for(Feature feature: c.getBody()) {
                if(feature instanceof Method) {
                    Method method = (Method)feature;
                    String returnType = method.getType();
                    // MethodScope methodScope = new MethodScope(scope, type);
                    // method.setScope(methodScope);

                    // for(Variable var : method.getParams()) {

                    //     assert var.getValue() == null;                        
                    //     Field field = new Field(var.getType(), methodScope);                        
                    //     methodScope.addField(var.getId(), field);

                    // }

                    String valueType = processExpr(method.getBody(), method.getScope());

                    if( "SELF_TYPE".equals(returnType) ) {
                        returnType = scope.getClassType();
                    }

                    checkTypes(valueType, returnType, method);

                    // scope.addMethod(method.getName(), methodScope);

                } 
                else if(feature instanceof Variable) {
                    Variable var =  (Variable)feature;
                    String returnType = var.getType();

                    if( "SELF_TYPE".equals(returnType) ) {
                        returnType = scope.getClassType();
                    }

                    if(var.getValue() != null) {
                        String valueType = processExpr(var.getValue(), scope);
                        checkTypes(valueType, returnType, var);
                    }

                }
                    
            }

            //_classes.put(c.getType(), scope);
        }

    }

    private String processExpr(Expr e, Scope scope) {

        assert e != null : "node is null";

        String returnType = null;

        if(e instanceof Block) {

            boolean hasErrors = false;

            for(Expr child: ((Block)e).getStatements()) {
                returnType = processExpr(child, scope);
                hasErrors = hasErrors || returnType == null;
            }

            if(hasErrors) {
                returnType = null;
            }
        }
        else if(e instanceof WhileExpr) {
            WhileExpr loop = (WhileExpr)e;

            String condType = processExpr(loop.getCond(), scope);
            String valueType = processExpr(loop.getValue(), scope);
            if(checkTypes(condType, "Bool", loop) && valueType != null ) {
                returnType = "Object";
            }


        }
        else if(e instanceof AssignExpr) {

            AssignExpr expr = (AssignExpr)e;

            Field field = scope.getField(expr.getId());

            if(field == null) {
                _symTable.getErrors().add(new SemanticError(String.format("Undefined variable %s", expr.getId()), expr.getPos()));
            }

            String valueType = processExpr(expr.getValue(), scope);

            if( field != null && checkTypes(valueType, field.getType(), expr)) {
                returnType = valueType;
            }

        }        
        else if(e instanceof DispatchExpr) {
            DispatchExpr call = (DispatchExpr)e;

            String exprType;

            ClassScope calleeScope;


            if(call.getExpr() != null) {
                calleeScope = _symTable.findClass(processExpr(call.getExpr(), scope));
            }
            else {
                calleeScope = scope.getClassScope();

            }


            exprType = calleeScope != null ? calleeScope.getClassType() : null;

            if( calleeScope != null && call.getType() != null ) {

                ClassScope castScope = _symTable.findClass(call.getType());

                if(castScope == null) {
                    _symTable.getErrors().add(new SemanticError(String.format("Undefined class %s", call.getType()), call.getPos()));
                    calleeScope = null;
                }
                else if (!_symTable.instanceOf(calleeScope.getClassType(), call.getType())) {
                    _symTable.getErrors().add(new SemanticError(String.format("Expected %s, received %s", call.getType(), calleeScope.getClassType()), call.getPos()));
                    calleeScope = null;
                }
                else {
                    calleeScope = castScope;
                }

            }
            
            MethodScope method = calleeScope != null ? calleeScope.getMethod(call.getName()) : null ;

            if(method == null) {
                _symTable.getErrors().add(new SemanticError(String.format("Undefined method %s", call.getName()), call.getPos()));
            }
            else {

                int paramCount = method.getParams().size();
                int argCount = call.getArgs().size();
                StringBuilder callSignature = new StringBuilder();
                callSignature.append("(");

                boolean hasErrors = false;

                if(paramCount != argCount) {
                    hasErrors = true;                    
                }
                for(int i = 0; i < argCount; i++) {

                    Expr arg = call.getArgs().get(i);
                    String argType = processExpr(arg, scope);
                    callSignature.append(argType).append(",");

                    if(i < paramCount) {
                        Field field = method.getLocalField(method.getParams().get(i));
                        boolean argValid = checkTypes(argType, field.getType(), null);
                        hasErrors = hasErrors || !argValid;
                    }
                    
                }

                
                if(callSignature.length() > 1) {
                    callSignature.setLength(callSignature.length()-1);
                }
                callSignature.append(")");
                
                if(hasErrors) {
                    _symTable.getErrors().add(new SemanticError(String.format("Cannot call method %s.%s %s with arguments %s",
                        method.getClassScope().getClassType(), call.getName(), method.getSignature(),
                        callSignature), call.getPos()
                    ));

                }
                else {
                    returnType = classIfExists(method.getReturnType(), calleeScope);
                }
            }
            

        }
        else if(e instanceof IfExpr) {

            IfExpr expr = (IfExpr)e;


            String condType = processExpr(expr.getCond(), scope);

            String trueType = processExpr(expr.getTrue(), scope);
            String falseType = processExpr(expr.getFalse(), scope);


            if(checkTypes(condType, "Bool", expr) && trueType != null && falseType != null ) {
                returnType = _symTable.findCommonClass(trueType, falseType);
            }

        }
        else if(e instanceof NewExpr) 
        {
            NewExpr newExpr = (NewExpr)e;

            returnType = classIfExists(newExpr.getType(), scope);
            if(returnType == null) {
                _symTable.getErrors().add(new SemanticError(String.format("Undefined class %s", newExpr.getType()), newExpr.getPos()));
            }

        }
        else if(e instanceof UnaryExpr) {
            UnaryExpr expr = (UnaryExpr)e;

            String valueType = processExpr(expr.getValue(), scope);

            if(valueType != null) {

                switch(expr.getOp()) {

                    case ISVOID:
                        returnType = "Bool";
                        break;

                    case NEGATE:
                        if(checkTypes(valueType, "Int", expr)) {
                            returnType = "Int";
                        }
                        break;

                    case NOT:
                        if(checkTypes(valueType, "Bool", expr)) {
                            returnType = "Bool";
                        }
                        break;

                    default:
                        throw new RuntimeException("Invalid unary op");
                }
            }
        }
        else if(e instanceof BinaryExpr) {
            BinaryExpr expr = (BinaryExpr)e;


            String leftType = processExpr(expr.getLeft(), scope);
            String rightType = processExpr(expr.getRight(), scope);

            if(leftType != null && rightType != null) {
                boolean okA;
                boolean okB;

                switch(expr.getOp()) {

                    
                    case PLUS:
                    case MINUS:
                    case MULT:
                    case DIV:
                        okA = checkTypes(leftType, "Int", expr.getLeft());
                        okB = checkTypes(rightType, "Int", expr.getRight());
                        
                        if( okA && okB ){
                            returnType = "Int";
                        }
                        break;

                    case LT:
                    case LTE:
                        okA = checkTypes(leftType, "Int", expr.getLeft());
                        okB = checkTypes(rightType, "Int", expr.getRight());

                        if(okA && okB) {
                            returnType = "Bool";
                        }
                        break;

                    case EQUALS:
                        if("Int".equals(leftType) || "String".equals(leftType) || "Bool".equals(leftType)) {
                            if(checkTypes(rightType, leftType, expr.getRight())) {
                                    returnType = "Bool";
                                }
                        }
                        else if("Int".equals(rightType) || "String".equals(rightType) || "Bool".equals(rightType)) {
                            if(checkTypes(leftType, rightType, expr.getLeft())) {
                                    returnType = "Bool";
                                }   
                        }
                        else {
                            returnType = "Bool";
                        }
                        break;

                    default:
                        throw new RuntimeException("Invalid unary op");
                }
            }
        }
        else if (e instanceof CaseExpr) {
            CaseExpr caseExpr = ((CaseExpr)e);
            
            String valueType = processExpr(caseExpr.getValue(), scope);

            List<String> caseTypes = new ArrayList<String>();

            String commonType = null;
            boolean first = true;

            for(Case c : caseExpr.getCases()) {

                ClassScope caseClass = findClass(c.getType());
                String caseType = processExpr(c.getValue(), c.getScope());

                if(caseClass == null) {
                    _symTable.getErrors().add(new SemanticError(String.format("Undefined class %s", c.getType()), c.getPos()));
                } 

                if(caseType != null) {
                    if(first) {
                        commonType = caseType;
                        first = false;
                    }
                    else {
                        commonType = _symTable.findCommonClass(commonType, caseType);
                    }
                }
            }

            returnType = commonType;



        }
        else if (e instanceof LetExpr) {
            LetExpr let = (LetExpr)e;
            
            boolean hasErrors = false;

            for(Variable var : let.getDecl()) {
                if(classIfExists(var.getType(), scope) == null) {
                    hasErrors = true;
                    _symTable.getErrors().add(new SemanticError(String.format("Undefined class %s", var.getType()), var.getPos()));
                }
                if(var.getValue() != null) {

                    String valueType = processExpr(var.getValue(), new LetScopeWrapper(let.getScope(), var.getId()));

                    hasErrors = hasErrors || (valueType == null);
                }
            }
            if(!hasErrors) {
                returnType = processExpr(let.getValue(), let.getScope());                
            }

        }
        else if(e instanceof IdExpr) {
            IdExpr id = (IdExpr)e;
            Field field = scope.getField(id.getId());
            
            if(field != null) {
                returnType = field.getType();
            }
            else {
                _symTable.getErrors().add(new SemanticError(String.format("Undefined variable %s", id.getId()), id.getPos()));
            }

        }
        else if(e instanceof ValueExpr) {
            Object value = ((ValueExpr)e).getValue();

            if(value instanceof String) {
                returnType = "String";
            }
            else if(value instanceof Integer) {

                returnType = "Int";

            }
            else if(value instanceof Boolean) {

                returnType = "Bool";
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

        if("SELF_TYPE".equals(returnType)) {

            ClassScope classScope = scope.getClassScope();
            returnType = classScope.getClassType();

        }


        e.setExprType(returnType);

        return returnType;

    }


    public void printErrors() {
        _symTable.printErrors();
    }
}

