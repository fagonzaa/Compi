package coolc.ast;

import coolc.infrastructure.*;

public class Method extends Feature {

    private String _name;
    public String getName() {
        return _name;
    }

    private DeclList _params;
    public DeclList getParams() {
        return _params;
    }

    private String _type;
    public String getType() {
        return _type;
    }

    public Expr _body;
    public Expr getBody() {
        return _body;
    }

    private Scope _scope;
    public Scope getScope() {
        return _scope;
    }
    
    public void setScope(Scope scope) {
        _scope = scope;
    }

    public Method(String name, DeclList params, String type, Expr body) {
        _name = name;
        _params = params;
        _type = type;
        _body = body;
    }
}