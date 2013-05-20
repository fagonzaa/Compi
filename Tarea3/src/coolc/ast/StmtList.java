package coolc.ast;

import java.util.ArrayList;

import coolc.parser.Position;

public class StmtList extends ArrayList<Expr> { 
	
	private Position _position;
    public Position getPosition() {
        return _position;
    }
    
    public StmtList(Expr s, Position position) {
        this.add(s);
        _position = position;
    }
}
