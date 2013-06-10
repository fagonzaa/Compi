package coolc.infrastructure;

import coolc.ast.*;
import java.util.*;

public class ClassScope extends Scope {
    
    public static final ClassScope ObjectClass;
    public static final ClassScope IntClass;
    public static final ClassScope StringClass;
    public static final ClassScope BoolClass;
    public static final ClassScope IOClass;

    static {
        ObjectClass = new ClassScope("Object", null);
        ObjectClass._superType = null;

        ObjectClass.addMethod("abort", new MethodScope(ObjectClass, "Object", null));
        ObjectClass.addMethod("type_name", new MethodScope(ObjectClass, "String", null));
        ObjectClass.addMethod("copy", new MethodScope(ObjectClass, "SELF_TYPE", null));

        IntClass = new ClassScope("Int", null);

        StringClass = new ClassScope("String", null);
        MethodScope concat = new MethodScope(StringClass, "String", null);
        concat.addField("s", new Field("String", concat, null));

        MethodScope substr = new MethodScope(StringClass, "String", null);
        substr.addField("i", new Field("Int", substr, null));
        substr.addField("l", new Field("Int", substr, null));

        StringClass.addMethod("length", new MethodScope(StringClass, "Int", null));
        StringClass.addMethod("concat", concat);
        StringClass.addMethod("substr", substr);

        
        BoolClass = new ClassScope("Bool", null);

        IOClass = new ClassScope("IO", null);
        MethodScope out_string = new MethodScope(IOClass, "SELF_TYPE", null);
        out_string.addField("x", new Field("String", out_string, null));

        MethodScope out_int = new MethodScope(IOClass, "SELF_TYPE", null);
        out_int.addField("x", new Field("Int", out_int, null));


        IOClass.addMethod("out_string", out_string);
        IOClass.addMethod("out_int", out_int);
        IOClass.addMethod("in_string", new MethodScope(IOClass, "String", null));
        IOClass.addMethod("in_int", new MethodScope(IOClass, "Int", null));

    }

    private LinkedHashMap<String, MethodScope> _methods;
    private ClassScope _superScope;
    
    private String _superType;
    public String getSuperType() {
        return _superType;
    }

    private String _classType;
    public String getClassType() {
        return _classType;
    }

    public ClassScope getSuperScope() {        
        return _superScope;
    }

    public void setSuperScope(ClassScope superScope) {
        _superScope = superScope;
    }

    public ClassScope(String classType, Node node) {
        this(classType, "Object", null);
        setNode(node);

    }

    public ClassScope(String classType, String superType, Node node) {
        super(null);
        _classType = classType;
        _superType = superType;
        _methods = new LinkedHashMap<String, MethodScope>();
        addField("self", new Field("SELF_TYPE", this, null));
        setNode(node);
    }

    public void addMethod(String name, MethodScope type) {
        if(_methods.get(name) != null) {
            throw new RuntimeException(String.format("Method %s already defined in this scope", name));
        }
        _methods.put(name, type);
    }

    public MethodScope getMethod(String name) {
        MethodScope method = _methods.get(name);
        if(method == null && _superScope != null) {
            method = _superScope.getMethod(name);
        }
        return method;
    }

    public void removeMethod(String name) {
        _methods.remove(name);
    }

    @Override
    public Field getField(String name) {
        Field field = super.getField(name);
        if(field == null && _superScope != null) {
            field = _superScope.getField(name);            
        }        
        return field;
    }

    public Set<Map.Entry<String, MethodScope>> listMethods() {
        return _methods.entrySet();
    }

}
