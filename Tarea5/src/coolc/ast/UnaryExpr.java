package coolc.ast;

public class UnaryExpr extends ValueExpr<Expr> {

    private UnaryOp _op;
    public UnaryOp getOp() {
        return _op;
    }

    public UnaryExpr(UnaryOp op, Expr value) {
        super(value);
        _op = op;        
    }
}
