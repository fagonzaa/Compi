package coolc.ast;

public class ValueExpr<T> extends Expr {

    private T _value;
    public T getValue() {
        return _value;
    }

    public ValueExpr(T value) {
        _value = value;
    }
}
