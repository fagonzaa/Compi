package coolc.ast;

import java.util.ArrayList;

import coolc.parser.Position;

public class Args extends ArrayList<Expr> { 
	
	private Position _position;
    public Position getPosition() {
        return _position;
    }
    
    public Args(Position position) { 
    	_position = position;
    }

    public Args(Expr arg, Position position) {
        this.add(arg);
        _position = position;
    }
}
