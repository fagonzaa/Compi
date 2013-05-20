package coolc.ast;

import coolc.parser.Position;

public class CaseExpr extends ValueExpr<Expr> {

    private CaseList _cases;
    public CaseList getCases() {
        return _cases;
    }
    
    private Position _position;
    public Position getPosition() {
        return _position;
    }

    public CaseExpr(Expr value, CaseList cases, Position position) {
        super(value, position);
        _cases = cases;
        _position = position;
    }

}
