package coolc.symtable;

import java.util.List;
import java.util.ArrayList;

import coolc.symtable.Symbol;

public class MethodSymbol extends Symbol {

    public MethodScope methodScope;

    public MethodSymbol(String id, String t, Scope s, MethodScope methodScope) {
        super(id, t, s);
        this.methodScope = methodScope;
    }
}