package coolc.ast;

import coolc.parser.Position;

public class LetExpr extends ValueExpr<Expr> {

    private DeclList _decl;
    public  DeclList getDecl() {
        return _decl;
    }

    private Position _position;
    public Position getPosition() {
        return _position;
    }
    
    public LetExpr(DeclList decl, Expr value, Position position) {
        super(value, position);
        _decl = decl;
        _position = position;
    }
}
