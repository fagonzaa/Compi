package coolc.ast;

public class DispatchExpr extends Expr {

    private String _name;
    public String getName() {
        return _name;
    }

    private String _type;
    public String getType() {
        return _type;
    }

    private Expr _expr;
    public Expr getExpr() {
        return _expr;
    }

    private Args _args;
    public Args getArgs() {
        return _args;
    }

    public DispatchExpr(Expr expr, String type, String name, Args args) {
        _expr = expr;
        _type = type;
        _name = name;
        _args = args;
    }

    public DispatchExpr(Expr expr, String name, Args args) {
        this(expr, null, name, args);
    }


    public DispatchExpr(String name, Args args) {
        this(null, null, name, args);
    }
}
