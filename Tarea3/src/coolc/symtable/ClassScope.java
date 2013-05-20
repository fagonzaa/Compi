package coolc.symtable;

import coolc.*;
import coolc.ast.ClassDef;

public class ClassScope extends Scope {
    
	ClassDef classDef;
	
	public ClassDef getClassDef(){
		return classDef;
	}
	
	public ClassScope(ClassDef classDef){
		this.classDef = classDef;
	}
	
    public Symbol find(String id) {
        return this.get(id);
    }

    public void add(String id, Symbol s) {
        this.put(id, s);
    }


}
