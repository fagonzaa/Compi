package coolc.ast;

import coolc.parser.Position;

public class UnaryExpr extends ValueExpr<Expr> {

    private UnaryOp _op;
    public UnaryOp getOp() {
        return _op;
    }
    
    private Position _position;
    public Position getPosition() {
        return _position;
    }

    public UnaryExpr(UnaryOp op, Expr value, Position position) {
        super(value, position);
        _op = op;
        _position = position;
    }
}
