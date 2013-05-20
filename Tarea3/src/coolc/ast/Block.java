package coolc.ast;

import coolc.parser.Position;

public class Block extends Expr {
    
    private StmtList _statements;
    public StmtList getStatements() {
        return _statements;
    }

    private Position _position;
    public Position getPosition() {
        return _position;
    }

    public Block(StmtList statements, Position position) {
    	super(position);
        _statements = statements;
        _position = position;
    }
}