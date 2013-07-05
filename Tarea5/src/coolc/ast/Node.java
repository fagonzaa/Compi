package coolc.ast;

import coolc.parser.*;

public abstract class Node {

    private Position _pos;

    public Position getPos() {
        return _pos;
    }

    public void setPos(Position pos) {
        _pos = pos;
    }

}