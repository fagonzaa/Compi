%{
    import coolc.ast.*;
%}

%language "Java"

%define package coolc.parser;
%define parser_class_name "Parser"
%define public
%locations

%code {
    private Program _root;
    public Program getRoot() {
        return _root;
    }

    public static String getTokenName(int t) {
        return yytname_[t-255];
    }

}

%right "<-"
%nonassoc "not"
%left "<=" "<" "="
%left "+" "-" 
%left "*" "/"
%nonassoc "isvoid"
%nonassoc "~"
%nonassoc "@"
%nonassoc "."


%token  T_LPAREN "(" T_RPAREN ")"
%token  T_LCURLY "{" T_RCURLY "}"
%token  T_COLON ":" T_SEMICOLON ";"
%token  T_DOT "." T_COMMA ","
%token  T_DBLQUOTE "\""
%token  T_BACKSLASH "\\"

%token T_CLASS      "class"
%token T_ELSE       "else"
%token T_FALSE      "false"
%token T_FI         "fi"
%token T_IF         "if"
%token T_IN         "in"
%token T_INHERITS   "inherits"
%token T_ISVOID     "isvoid"
%token T_LET        "let"
%token T_LOOP       "loop"
%token T_POOL       "pool"
%token T_THEN       "then"
%token T_WHILE      "while"
%token T_CASE       "case"
%token T_ESAC       "esac"
%token T_NEW        "new"
%token T_OF         "of"
%token T_NOT        "not"
%token T_TRUE       "true"

%token T_CAST "@"
%token T_NEGATE "~"
%token T_MULT "*" T_DIV "/"
%token T_PLUS "+" T_MINUS "-"
%token T_LTE "<=" T_LT "<" T_EQUALS "="
%token T_ASSIGN "<-"
%token T_FAT "=>"

%token T_UNKNOWN

%token <String> TYPE
%token <String> ID
%token <String> STRING
%token <Boolean> BOOL
%token <Integer> INTEGER

%type <Program> program;
%type <ClassDef> class;
%type <FeatureList> class_body;
%type <Feature> feature;

%type <Variable> formal var_declaration
%type <Node> input
%type <CaseList> case_list
%type <Case> case
%type <Args> args args_non_empty
%type <StmtList> stmt_list
%type <DeclList> formal_list formal_list_non_empty let_list

%type <Expr> expr

%%

input
    : program { _root = $1; }

program
    : class ";" { $$ = new Program($class, new Position(yyloc.begin.line, yyloc.begin.column)); }
    | program class ";" { $1.add($class); $$ = $1; }
    ;

class
    : "class" TYPE "{" class_body "}"    
            { $$ = new ClassDef($TYPE, new Position(yyloc.begin.line, yyloc.begin.column), $class_body); }
    | "class" TYPE[type] "inherits" TYPE[super] "{" class_body "}"    
            { $$ = new ClassDef($type, $super, new Position(yyloc.begin.line, yyloc.begin.column), $class_body); }
    ;

class_body
    : feature ";"   { 
        $$ = new FeatureList($feature, new Position(yyloc.begin.line, yyloc.begin.column)); 
    }
    | class_body feature ";" { 
        $1.add($feature); $$ = $1; 
    }
    ;

feature
    : ID "(" formal_list ")" ":" TYPE "{" expr "}" { 
        $$ = new Method($ID, $formal_list, $TYPE, new Position(yyloc.begin.line, yyloc.begin.column), $expr); 
    }
    | var_declaration
        {
            $$ = $1; 
        }
    ;

formal_list
    : /* empty */             { $$ = new DeclList(new Position(yyloc.begin.line, yyloc.begin.column)); }
    | formal_list_non_empty   { $$ = $1; }
    ;

formal_list_non_empty
    : formal                  
            { $$ = new DeclList($1, new Position(yyloc.begin.line, yyloc.begin.column)); }

    | formal_list_non_empty[list] "," formal
            { $list.add($formal); $$ = $list; }
    ;   

formal
    : ID ":" TYPE   { $$ = new Variable($ID, $TYPE, new Position(yyloc.begin.line, yyloc.begin.column)); }
    ;


args
    : /* empty */      { $$ = new Args(new Position(yyloc.begin.line, yyloc.begin.column)); }
    | args_non_empty   { $$ = $1; }
    ;

args_non_empty
    : expr  {   $$ = new Args($expr, new Position(yyloc.begin.line, yyloc.begin.column));   }
    | args_non_empty[args] "," expr 
            { $args.add($expr); $$ = $args; }

stmt_list
    : expr ";"
            {  $$ = new StmtList($expr, new Position(yyloc.begin.line, yyloc.begin.column)); }
    | stmt_list expr ";"
            {
                $1.add($expr);
                $$ = $1;
            }

var_declaration
    : ID ":" TYPE 
            {  $$ = new Variable($ID, $TYPE, new Position(yyloc.begin.line, yyloc.begin.column)); }
    | ID ":" TYPE "<-" expr
            {  $$ = new Variable($ID, $TYPE, new Position(yyloc.begin.line, yyloc.begin.column), $expr); }
    ;

let_list
    : var_declaration
            {   $$ = new DeclList($var_declaration, new Position(yyloc.begin.line, yyloc.begin.column)); }
    | let_list "," var_declaration
            { 
                $1.add($var_declaration);
                $$ = $1;
            }
    ;

case
    : ID ":" TYPE "=>" expr ";" 
            { $$ =  new Case($ID, $TYPE, $expr, new Position(yyloc.begin.line, yyloc.begin.column)); }
    ;


case_list
    : case  
            {   $$ = new CaseList($case, new Position(yyloc.begin.line, yyloc.begin.column)); }

    | case_list case    
            {   $1.add($case); $$ = $1; }
    ;

expr 
    : ID "<-" expr      
            { $$ = new AssignExpr($1, $3, new Position(yyloc.begin.line, yyloc.begin.column)); }

    | expr[obj] "@" TYPE "." ID "(" args  ")" 
            { $$ = new DispatchExpr($obj, $TYPE, $ID, $args, new Position(yyloc.begin.line, yyloc.begin.column)); }

    | expr[obj] "." ID "(" args  ")"
            { $$ = new DispatchExpr($obj, $ID, $args, new Position(yyloc.begin.line, yyloc.begin.column)); }

    | ID "(" args ")"  
            { $$ = new DispatchExpr($ID, $args, new Position(yyloc.begin.line, yyloc.begin.column)); }

    | "if" expr[cond] "then" expr[true] "else" expr[false] "fi"
            { $$ = new IfExpr($cond, $true, $false, new Position(yyloc.begin.line, yyloc.begin.column)); }

    | "while" expr[cond] "loop" expr[stmt] "pool"
            { $$ = new WhileExpr($cond, $stmt, new Position(yyloc.begin.line, yyloc.begin.column)); }

    | "{" stmt_list "}"
            { $$ = new Block($stmt_list, new Position(yyloc.begin.line, yyloc.begin.column)); }

    | "let" let_list "in" expr[value]
            { $$ = new LetExpr($let_list, $value, new Position(yyloc.begin.line, yyloc.begin.column)); }

    | "case" expr[value] "of" case_list "esac" 
            { $$ = new CaseExpr($value, $case_list, new Position(yyloc.begin.line, yyloc.begin.column)); }

    | "new" TYPE        { $$ = new NewExpr($TYPE, new Position(yyloc.begin.line, yyloc.begin.column)); }
    | "isvoid" expr     { $$ = new UnaryExpr( UnaryOp.ISVOID, $2, new Position(yyloc.begin.line, yyloc.begin.column)); }
    | expr "+" expr     { $$ = new BinaryExpr( BinaryOp.PLUS, $1, $3, new Position(yyloc.begin.line, yyloc.begin.column));}
    | expr "-" expr     { $$ = new BinaryExpr( BinaryOp.MINUS, $1, $3, new Position(yyloc.begin.line, yyloc.begin.column)); }
    | expr "*" expr     { $$ = new BinaryExpr( BinaryOp.MULT, $1, $3, new Position(yyloc.begin.line, yyloc.begin.column)); }
    | expr "/" expr     { $$ = new BinaryExpr( BinaryOp.DIV, $1, $3, new Position(yyloc.begin.line, yyloc.begin.column)); } 
    | "~" expr          { $$ = new UnaryExpr( UnaryOp.NEGATE, $2, new Position(yyloc.begin.line, yyloc.begin.column)); }
    | expr "<" expr     { $$ = new BinaryExpr( BinaryOp.LT, $1, $3, new Position(yyloc.begin.line, yyloc.begin.column)); } 
    | expr "<=" expr    { $$ = new BinaryExpr( BinaryOp.LTE, $1, $3, new Position(yyloc.begin.line, yyloc.begin.column)); } 
    | expr "=" expr     { $$ = new BinaryExpr( BinaryOp.EQUALS, $1, $3, new Position(yyloc.begin.line, yyloc.begin.column)); }
    | "not" expr        { $$ = new UnaryExpr( UnaryOp.NOT, $2, new Position(yyloc.begin.line, yyloc.begin.column)); }
    | "(" expr ")"   { $$ = $2; }
    | ID        { $$ = new IdExpr($1, new Position(yyloc.begin.line, yyloc.begin.column)); }
    | INTEGER   { $$ = new ValueExpr<Integer>($1, new Position(yyloc.begin.line, yyloc.begin.column)); }
    | STRING    { $$ = new ValueExpr<String>($1, new Position(yyloc.begin.line, yyloc.begin.column)); }
    | "true"    { $$ = new ValueExpr<Boolean>(true, new Position(yyloc.begin.line, yyloc.begin.column)); }
    | "false"   { $$ = new ValueExpr<Boolean>(false, new Position(yyloc.begin.line, yyloc.begin.column)); }
    ;


%%



