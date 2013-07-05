package coolc.parser;

%%

%{

    private int comment_nesting = 0;
    private boolean use_string_pos = false;

    private Object yylval;

    private StringBuilder strval = new StringBuilder();
    private int strline, strcol;

    public Object getLVal() {
        return yylval;
    }

    public Position getStartPos() {

        if ( use_string_pos ) {            
            return new Position(strline, strcol);
        }

        return new Position(yyline, yycolumn);
    }

    public Position getEndPos() {
        


        if ( use_string_pos ) {

            return new Position(strline, strcol);
        }

        return new Position(yyline, yycolumn);
    }

    public void yyerror(Parser.Location pos, String msg) {
        throw new RuntimeException(String.format("error %d:%d %s", pos.begin.line, pos.begin.column, msg));
    }


    public int yylex() throws java.io.IOException {
        yylval = null;
        use_string_pos = false;
        return __yylex();
    }


%}

%function __yylex

%public
%class Lexer
%unicode
%line
%column


%ignorecase

TYPE   =  [A-Z][A-Za-z0-9_]*
ID     = [a-z][A-Za-z0-9_]*

%x comment
%x multiline_comment
%x string

%implements Parser.Lexer
%int

%%

<YYINITIAL>      "--"        { yybegin(comment); }
<comment>[^\n]   { }
<comment>\n     { yybegin(YYINITIAL);}

<YYINITIAL>         "(*"        { yybegin(multiline_comment); comment_nesting = 0; }
<multiline_comment> {
    "(*"    { comment_nesting++; }
    [^*]    {}
    "*"[^)]  {}
    "*)"    {   if( comment_nesting > 0) { comment_nesting--; }
                else { yybegin(YYINITIAL); }
            }
        }


    "\""    {   
                strval.setLength(0); 
                strline = yyline;
                strcol = yycolumn;
                yybegin(string); 
            }
<string>    {

    "\\b"   { strval.append("\b"); }
    "\\t"   { strval.append("\t"); }
    ("\\n"|"\\"\n)   { strval.append("\n"); }
    "\\f"   { strval.append("\f"); }
    "\\".   { strval.append(yytext().charAt(1)); }

    [^\"\\]  { strval.append(yytext()); }
    "\""    { 
                yylval = strval.toString(); 
                use_string_pos = true;
                yybegin(YYINITIAL);
                return Parser.STRING;
            }



            }


    [0-9]+  {   
                yylval = Integer.parseInt(yytext());
                return Parser.INTEGER; 
            }
    "+"     { return Parser.T_PLUS; }
    "-"     { return Parser.T_MINUS; }
    "*"     { return Parser.T_MULT; }
    "/"     { return Parser.T_DIV; }
    "~"     { return Parser.T_NEGATE; }
    "not"   { return Parser.T_NOT; }
    "<"     { return Parser.T_LT; }
    "<="    { return Parser.T_LTE; }
    "="     { return Parser.T_EQUALS; }
    "@"	    { return Parser.T_CAST; }
    "=>"    { return Parser.T_FAT; }
    "("     { return Parser.T_LPAREN; }
    ")"     { return Parser.T_RPAREN; }
    "{"     { return Parser.T_LCURLY; }
    "}"     { return Parser.T_RCURLY; }
    ":"     { return Parser.T_COLON; }
    ";"     { return Parser.T_SEMICOLON; }
    "."     { return Parser.T_DOT; }
    ","     { return Parser.T_COMMA; }
    
    "\\"    { return Parser.T_BACKSLASH; }
    class   { return Parser.T_CLASS; }
    else    { return Parser.T_ELSE; }
    [f]alse   {     return Parser.T_FALSE; }
    fi      { return Parser.T_FI; }
    if      { return Parser.T_IF; }
    in      { return Parser.T_IN; }
    inherits { return Parser.T_INHERITS; }
    isvoid  { return Parser.T_ISVOID; }
    let     { return Parser.T_LET; }
    loop    { return Parser.T_LOOP; }
    pool    { return Parser.T_POOL; }
    then    { return Parser.T_THEN; }
    while   { return Parser.T_WHILE; }
    case    { return Parser.T_CASE; }
    esac    { return Parser.T_ESAC; }
    new     { return Parser.T_NEW; }
    of      { return Parser.T_OF; }
    not     { return Parser.T_NOT; }
    [t]rue    { return Parser.T_TRUE; }

    "<-"    { return Parser.T_ASSIGN; }



    {TYPE}  {   
                yylval = yytext();
                return Parser.TYPE; 
            }

    {ID}    {
                yylval = yytext();
                return Parser.ID; 
            }
    [ \t\r\n]+  {}

    .           { System.out.printf("Unknown character [%s]\n",yytext());
                       return Parser.T_UNKNOWN;    }
    
    <<EOF>> { return Parser.EOF; }
