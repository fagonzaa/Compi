package coolc.ast;

public class BinaryExpr extends Expr {
    
    private BinaryOp _op;
    public BinaryOp getOp() {
        return _op;
    }

    private Expr _left;
    public Expr getLeft() {
        return _left;
    }

    private Expr _right;
    public Expr getRight() {
        return _right;
    }

    public BinaryExpr(BinaryOp op, Expr left, Expr right) {
        _op = op;
        _left = left;
        _right = right;
    }
}
