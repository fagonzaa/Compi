package coolc.ast;

public class IdExpr extends Expr {

    private String _id;
    public String getId() {
        return _id;
    }

    public IdExpr(String id) {
        _id = id;
    }
}
