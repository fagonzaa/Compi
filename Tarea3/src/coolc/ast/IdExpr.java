package coolc.ast;

import coolc.parser.Position;

public class IdExpr extends Expr {

    private String _id;
    public String getId() {
        return _id;
    }
    
    private Position _position;
    public Position getPosition() {
        return _position;
    }

    
    public IdExpr(String id, Position position) {
    	super(position);
    	
    	_id = id;
        _position = position;
    }
}
