package coolc.ast;

import java.util.ArrayList;

public class DeclList extends ArrayList<Variable> {

    public DeclList() { }

    public DeclList(Variable var) {
        this.add(var);
    }
 }
