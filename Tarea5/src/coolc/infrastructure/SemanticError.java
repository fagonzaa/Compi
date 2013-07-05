package coolc.infrastructure;

import coolc.parser.*;

public class SemanticError {
    
    private Position _pos;
    private String _error;


    public Position getPos() {
        return _pos;
    }

    public String getError() {
        return _error;
    }

    public SemanticError(String error, Position pos) {
        _error = error;
        
        _pos = pos;
    }

}