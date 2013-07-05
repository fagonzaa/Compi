package coolc.ast;

import java.util.ArrayList;

public class StmtList extends ArrayList<Expr> { 
    public StmtList(Expr s) {
        this.add(s);
    }
}
