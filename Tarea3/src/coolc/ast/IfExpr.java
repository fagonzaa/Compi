package coolc.ast;

import coolc.parser.Position;

public class IfExpr extends Expr {

    private Expr _cond;
    public Expr getCond() {
        return _cond;
    }

    private Expr _true;
    public Expr getTrue() {
        return _true;
    }

    private Expr _false;
    public Expr getFalse() {
        return _false;
    }
    
    private Position _position;
    public Position getPosition() {
        return _position;
    }

    public IfExpr(Expr cond, Expr if_true, Expr if_false, Position position) { 
    	super(position);
    	
    	_cond = cond;
        _true = if_true;
        _false = if_false;
        _position = position;
    }
}
