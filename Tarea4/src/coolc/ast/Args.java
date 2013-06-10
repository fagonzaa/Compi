package coolc.ast;

import java.util.ArrayList;

public class Args extends ArrayList<Expr> { 
    public Args() { }

    public Args(Expr arg) {
        this.add(arg);
    }
}
