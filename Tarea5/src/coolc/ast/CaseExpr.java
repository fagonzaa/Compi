package coolc.ast;

public class CaseExpr extends ValueExpr<Expr> {

    private CaseList _cases;
    public CaseList getCases() {
        return _cases;
    }

    public CaseExpr(Expr value, CaseList cases) {
        super(value);
        _cases = cases;
    }

}
