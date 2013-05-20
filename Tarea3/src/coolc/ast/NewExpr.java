package coolc.ast;

import coolc.parser.Position;

public class NewExpr extends Expr {

    private String _type;
    public String getType() {
        return _type;
    }
    
    private Position _position;
    public Position getPosition() {
        return _position;
    }

    public NewExpr(String type, Position position) {
    	super(position);
    	_type = type;
        _position = position;
    }

}
