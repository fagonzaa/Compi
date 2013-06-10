package coolc.parser;

public class Position {

    public final int line;

    public final int column;

    public Position(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public boolean equals(Object obj) {
        if( !(obj instanceof Position) ) {
            return false;
        }
        else {
            Position other = (Position)obj;
            return this.line == other.line && this.column == other.column;
        }
    }

    @Override
    public String toString() {
        return "{%s %s, %s}".format(this.getClass().getName(), line, column);
    }
}