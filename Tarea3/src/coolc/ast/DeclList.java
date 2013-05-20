package coolc.ast;

import java.util.ArrayList;

import coolc.parser.Position;

public class DeclList extends ArrayList<Variable> {

	private Position _position;
    public Position getPosition() {
        return _position;
    }

    
    
    public DeclList(Position position) { 
    	_position = position;
    }

    public DeclList(Variable var, Position position) {
        this.add(var);
        _position = position;
    }
 }
