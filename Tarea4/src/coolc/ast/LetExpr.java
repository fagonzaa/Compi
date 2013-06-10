package coolc.ast;

import coolc.infrastructure.*;

public class LetExpr extends ValueExpr<Expr> {

    private DeclList _decl;
    public  DeclList getDecl() {
        return _decl;
    }

    private Scope _scope;
    public Scope getScope() {
        return _scope;
    }
    public void setScope(Scope scope) {
        _scope = scope;
    }

    public LetExpr(DeclList decl, Expr value) {
        super(value);
        _decl = decl;
    }
}
