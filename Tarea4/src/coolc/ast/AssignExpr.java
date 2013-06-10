package coolc.ast;

public class AssignExpr extends IdExpr {

    private Expr _value;
    public Expr getValue() {
        return _value;
    }


    public AssignExpr(String id, Expr value) {
        super(id);
        _value = value;
    }
}
