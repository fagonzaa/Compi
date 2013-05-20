package coolc.ast;

import coolc.parser.Position;

public class ValueExpr<T> extends Expr {

    private T _value;
    public T getValue() {
        return _value;
    }
    
    private Position _position;
    public Position getPosition() {
        return _position;
    }

    public ValueExpr(T value, Position position) {
    	super(position);
    	_value = value;
        _position = position;
    }
}
