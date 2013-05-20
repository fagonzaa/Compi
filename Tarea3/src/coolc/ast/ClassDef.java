package coolc.ast;

import coolc.parser.Parser.Location;
import coolc.parser.Position;

public class ClassDef extends Node {

    private String _type;
    public String getType() {
        return _type;
    }
    
    private Position _position;
    public Position getPosition() {
        return _position;
    }

    private String _super;
    public String getSuper() {
        return _super;
    }

    private FeatureList _body;
    public FeatureList getBody() {
    	
        return _body;
    }

    public ClassDef(String type, String parent, FeatureList body) {
        _type = type;
        _super = parent;
        _body = body;
    }
    
    public ClassDef(String type, String parent, Position position, FeatureList body) {
        _type = type;
        _super = parent;
        _body = body;
        _position = position;
    }

    public ClassDef(String type, Position position, FeatureList body) {
    	 _position = position;
    	 _type = type;
         _super = null;
         _body = body;
//        this(type, null, body);
    }
    
    public ClassDef(String type, FeatureList body) {
       // this(type, null, body);
    	 _type = type;
         _super = null;
         _body = body;
    }
}
