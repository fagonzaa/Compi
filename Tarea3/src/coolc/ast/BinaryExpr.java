package coolc.ast;

import coolc.parser.Position;

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
    
    private Position _position;
    public Position getPosition() {
        return _position;
    }
    	

    public BinaryExpr(BinaryOp op, Expr left, Expr right, Position position) {
        super(position);
    	_op = op;
        _left = left;
        _right = right;
        _position = position;
    }
}
