package coolc.infrastructure;

import java.util.*;
import coolc.ast.*;

public class Scope {

    private LinkedHashMap<String, Field> _fields;
    private Scope _parent;
    private Node _node;

    private List<Scope> _scopes;
    public List<Scope> getScopes() {
        return _scopes;
    }

    protected Scope getParent() {
        return _parent;
    }

    public Node getNode() {
        return _node;
    }

    public void setNode(Node node) {
        _node = node;
    }

    public Scope(Scope parent) {
        _fields = new LinkedHashMap<String, Field>();
        _parent = parent;
        _scopes = new ArrayList<Scope>();
    }

    public void addField(String name, Field field) {

        if(_fields.get(name) != null) {
            throw new RuntimeException(String.format("Field %s already defined in this scope", name));
        }
        _fields.put(name, field);
    }

    public Field getLocalField(String name) {
        return _fields.get(name);
    }

    public Field getField(String name) {
        Field field = _fields.get(name);

        if(field == null && _parent != null) {
            field = _parent.getField(name);
        }

        return field;

    }

    public void removeField(String name) {
        _fields.remove(name);
    }

    public Set<Map.Entry<String, Field>> listFields() {
        return _fields.entrySet();
    }

    public ClassScope getClassScope() {
        Scope s = this;
        while(!(s instanceof ClassScope)) {
            s = s._parent;
        }
        return (ClassScope)s;
    }


}