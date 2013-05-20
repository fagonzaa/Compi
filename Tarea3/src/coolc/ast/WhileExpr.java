package coolc.ast;

import coolc.parser.Position;

public class WhileExpr extends ValueExpr<Expr> {
    private Expr _cond;
    public Expr getCond() {
        return _cond;
    }
    
    private Position _position;
    public Position getPosition() {
        return _position;
    }

    public WhileExpr(Expr cond, Expr value, Position position) {
        super(value, position);
        _cond = cond;
        _position = position;
    }
}