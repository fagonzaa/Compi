package coolc.symtable;


import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MethodScope extends Scope {
    
    ClassScope parent;
    LinkedHashMap<String, Symbol> parameters;

    public Symbol getParameter(String param){
    	return parameters.get(param);
    }
    public ClassScope getClassScope() {
        return parent;
    }

    public MethodScope(ClassScope parent) {
        this.parameters = new LinkedHashMap<String, Symbol>();
        this.parent = parent;
    }
    
    public String[] getStringParams(){
    	Set<Map.Entry<String, Symbol>> set = parameters.entrySet();
	    Iterator<Map.Entry<String, Symbol>> it = set.iterator();
	    
	    String[] textparams = new String[parameters.size()];
	    
	    int i = 0;
	    while (it.hasNext()) {
	      Map.Entry<String, Symbol> entry = it.next();
	      String id = entry.getKey();
	      Symbol s = entry.getValue();
	     
	      textparams[i] = s.type;
	      i++;
	    }
	    
	    return textparams;
    }

    @Override
    public Symbol find(String id) {
        Symbol s = parameters.get(id);
        if ( s == null ) {
            s = this.get(id);
        }

        if ( s == null ) {
            s = parent.get(id);
        }
        return s;
    }

    public void add(String id, Symbol s) {
        this.put(id, s);        
    }

    public void addParameter(String id, Symbol s)
    {
        this.parameters.put(id, s);
    }


}
