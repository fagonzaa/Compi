package coolc.ast;

import coolc.parser.Position;

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
    
    private Position _position;
    public Position getPosition() {
        return _position;
    }

    public DispatchExpr(Expr expr, String type, String name, Args args, Position position) {
    	super(position);
    	
    	_expr = expr;
        _type = type;
        _name = name;
        _args = args;
        _position = position;
    }

    public DispatchExpr(Expr expr, String name, Args args, Position position) {
        this(expr, null, name, args, position);
    }


    public DispatchExpr(String name, Args args, Position position) {
        this(null, null, name, args, position);
    }
}
