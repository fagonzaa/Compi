package coolc.ast;

import java.util.ArrayList;

import coolc.parser.Position;

public class FeatureList extends ArrayList<Feature> {
    
	private Position _position;
    public Position getPosition() {
        return _position;
    }
	
	public FeatureList(Feature f, Position position) {
        this.add(f);
        _position = position;
    }
}