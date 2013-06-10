package coolc.infrastructure;

import coolc.ast.*;
import java.util.*;


public class Field extends Scope {
    
    private String _type;

    public String getType() {
        return _type;
    }

    public Field(String type, Scope parent, Node expr) {
        super(parent);
        _type = type;
        setNode(expr);
    }

}