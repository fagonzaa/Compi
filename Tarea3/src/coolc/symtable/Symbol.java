package coolc.symtable;


public class Symbol {

    public String id;
    public String type;
    public Scope scope;

    public Symbol(String id, String t, Scope s) {
        this.id = id;
        this.type = t;
        this.scope = s;   
    }

}