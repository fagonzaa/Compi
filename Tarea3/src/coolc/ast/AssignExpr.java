package coolc.ast;

import coolc.parser.Position;

public class AssignExpr extends IdExpr {

    private Expr _value;
    public Expr getValue() {
        return _value;
    }

    private Position _position;
    public Position getPosition() {
        return _position;
    }

    public AssignExpr(String id, Expr value, Position position) {
        super(id, position);
        _value = value;
        _position = position;
    }
}
