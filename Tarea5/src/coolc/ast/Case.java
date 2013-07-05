package coolc.ast;

import coolc.infrastructure.*;

public class Case extends Node {

    private String _id;
    public String getId() {
        return _id;
    }

    private String _type;
    public String getType() {
        return _type;
    }

    private Expr _value;
    public Expr getValue() {
        return _value;
    }

    private Scope _scope;
    public Scope getScope() {
        return _scope;
    }
    public void setScope(Scope scope) {
        _scope = scope;
    }

    public Case(String id, String type, Expr value) {
        _id = id;
        _type = type;
        _value = value;
    }
}
