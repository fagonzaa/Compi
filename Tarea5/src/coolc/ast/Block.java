package coolc.ast;

public class Block extends Expr {
    
    private StmtList _statements;
    public StmtList getStatements() {
        return _statements;
    }


    public Block(StmtList statements) {
        _statements = statements;
    }
}