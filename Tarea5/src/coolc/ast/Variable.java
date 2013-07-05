package coolc.ast;

public class Variable extends Feature {

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

    public Variable(String id, String type, Expr value) {
        _id = id;
        _type = type;
        _value = value;
    }

    public Variable(String id, String type) {
        this(id, type, null);
    }

}
