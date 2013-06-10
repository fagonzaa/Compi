package coolc.ast;

public class NewExpr extends Expr {

    private String _type;
    public String getType() {
        return _type;
    }

    public NewExpr(String type) {
        _type = type;
    }

}
