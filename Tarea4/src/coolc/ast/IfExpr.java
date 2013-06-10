package coolc.ast;

public class IfExpr extends Expr {

    private Expr _cond;
    public Expr getCond() {
        return _cond;
    }

    private Expr _true;
    public Expr getTrue() {
        return _true;
    }

    private Expr _false;
    public Expr getFalse() {
        return _false;
    }

    public IfExpr(Expr cond, Expr if_true, Expr if_false) { 
        _cond = cond;
        _true = if_true;
        _false = if_false;
    }
}
