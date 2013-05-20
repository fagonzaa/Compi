package coolc.ast;

import java.util.ArrayList;

import coolc.parser.Position;

public class CaseList extends ArrayList<Case> {
	
	private Position _position;
    public Position getPosition() {
        return _position;
    }
    
    
    public CaseList(Case c, Position position) {
        this.add(c);
        _position = position;
    }
}