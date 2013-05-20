package coolc.ast;

import coolc.parser.Position;

public class Case extends Node {

    private String _id;
    public String getId() {
        return _id;
    }

    private String _type;
    public String getType() {
        return _type;
    }

    private Expr _value;
    public Expr getValue() {
        return _value;
    }
    
    private Position _position;
    public Position getPosition() {
        return _position;
    }


    public Case(String id, String type, Expr value, Position position) {
        _id = id;
        _type = type;
        _value = value;
        _position = position;
    }
}
