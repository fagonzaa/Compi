package coolc.ast;

import coolc.parser.Position;

public class Variable extends Feature {

    private String _id;
    public String getId() {
        return _id;
    }

    private Position _position;
    public Position getPosition() {
        return _position;
    }
    
    private String _type;
    public String getType() {
        return _type;
    }

    private Expr _value;
    public Expr getValue() {
        return _value;
    }

    public Variable(String id, String type, Position position, Expr value) {
        _id = id;
        _type = type;
        _value = value;
        _position = position;
    }

    public Variable(String id, String type, Position position) {
        this(id, type, position, null);
    }

}
