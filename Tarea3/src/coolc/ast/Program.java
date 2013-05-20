package coolc.ast;

import java.util.ArrayList;

import coolc.parser.Position;

public class Program extends ArrayList<ClassDef> {
	private Position _position;
    public Position getPosition() {
        return _position;
    }
	
	public Program(ClassDef c, Position position) {
        this.add(c);
        
        _position = position;
    }
}
