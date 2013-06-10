package coolc.ast;

public class ClassDef extends Node {

    private String _type;
    public String getType() {
        return _type;
    }

    private String _super;
    public String getSuper() {
        return _super;
    }

    private FeatureList _body;
    public FeatureList getBody() {
        return _body;
    }

    public ClassDef(String type, String parent, FeatureList body) {
        _type = type;
        _super = parent;
        _body = body;
    }

    public ClassDef(String type, FeatureList body) {
        this(type, null, body);
    }
}
