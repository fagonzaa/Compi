package coolc.ast;

public class WhileExpr extends ValueExpr<Expr> {
    private Expr _cond;
    public Expr getCond() {
        return _cond;
    }

    public WhileExpr(Expr cond, Expr value) {
        super(value);
        _cond = cond;
    }
}