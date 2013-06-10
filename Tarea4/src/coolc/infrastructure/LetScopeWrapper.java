package coolc.infrastructure;

import java.util.*;


public class LetScopeWrapper extends Scope {

    private String _until;
    private Scope _scope;

    public LetScopeWrapper(Scope scope, String until) {
        super(scope.getParent());
        _until = until;
        _scope = scope;

    }
                        
    @Override
    public Field getField(String name) {
        Field field = null;
        for(Map.Entry<String, Field> entry : _scope.listFields()) {
            if(entry.getKey().equals(_until)) {
                break;
            }

            if(entry.getKey().equals(name)) {
                field = entry.getValue();
                break;
            }
        }

        if(field == null && _scope.getParent() != null) {
            field = _scope.getParent().getField(name);
        }

        return field;        

    }
}