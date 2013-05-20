package coolc.ast;

import coolc.parser.Position;

public class Expr {
	private Position _position;
    public Position getPosition() {
        return _position;
    }
    
    public Expr(Position position){
    	this._position = position;
    }
}