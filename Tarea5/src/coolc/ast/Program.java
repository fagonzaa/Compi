package coolc.ast;

import java.util.ArrayList;

public class Program extends ArrayList<ClassDef> {
    public Program(ClassDef c) {
        this.add(c);
    }
}
