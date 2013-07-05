/* A Bison parser, made by GNU Bison 2.5.  */

/* Skeleton implementation for Bison LALR(1) parsers in Java
   
      Copyright (C) 2007-2011 Free Software Foundation, Inc.
   
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.
   
   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

package coolc.parser;
/* First part of user declarations.  */

/* Line 32 of lalr1.java  */
/* Line 1 of "cool.y"  */

    import coolc.ast.*;


/**
 * A Bison parser, automatically generated from <tt>cool.y</tt>.
 *
 * @author LALR (1) parser skeleton written by Paolo Bonzini.
 */
public class Parser
{
    /** Version number for the Bison executable that generated this parser.  */
  public static final String bisonVersion = "2.5";

  /** Name of the skeleton that generated this parser.  */
  public static final String bisonSkeleton = "lalr1.java";


  /** True if verbose error messages are enabled.  */
  public boolean errorVerbose = false;


  /**
   * A class defining a pair of positions.  Positions, defined by the
   * <code>Position</code> class, denote a point in the input.
   * Locations represent a part of the input through the beginning
   * and ending positions.  */
  public class Location {
    /** The first, inclusive, position in the range.  */
    public Position begin;

    /** The first position beyond the range.  */
    public Position end;

    /**
     * Create a <code>Location</code> denoting an empty range located at
     * a given point.
     * @param loc The position at which the range is anchored.  */
    public Location (Position loc) {
      this.begin = this.end = loc;
    }

    /**
     * Create a <code>Location</code> from the endpoints of the range.
     * @param begin The first position included in the range.
     * @param end   The first position beyond the range.  */
    public Location (Position begin, Position end) {
      this.begin = begin;
      this.end = end;
    }

    /**
     * Print a representation of the location.  For this to be correct,
     * <code>Position</code> should override the <code>equals</code>
     * method.  */
    public String toString () {
      if (begin.equals (end))
        return begin.toString ();
      else
        return begin.toString () + "-" + end.toString ();
    }
  }



  /** Token returned by the scanner to signal the end of its input.  */
  public static final int EOF = 0;

/* Tokens.  */
  /** Token number, to be returned by the scanner.  */
  public static final int T_LPAREN = 258;
  /** Token number, to be returned by the scanner.  */
  public static final int T_RPAREN = 259;
  /** Token number, to be returned by the scanner.  */
  public static final int T_LCURLY = 260;
  /** Token number, to be returned by the scanner.  */
  public static final int T_RCURLY = 261;
  /** Token number, to be returned by the scanner.  */
  public static final int T_COLON = 262;
  /** Token number, to be returned by the scanner.  */
  public static final int T_SEMICOLON = 263;
  /** Token number, to be returned by the scanner.  */
  public static final int T_DOT = 264;
  /** Token number, to be returned by the scanner.  */
  public static final int T_COMMA = 265;
  /** Token number, to be returned by the scanner.  */
  public static final int T_DBLQUOTE = 266;
  /** Token number, to be returned by the scanner.  */
  public static final int T_BACKSLASH = 267;
  /** Token number, to be returned by the scanner.  */
  public static final int T_CLASS = 268;
  /** Token number, to be returned by the scanner.  */
  public static final int T_ELSE = 269;
  /** Token number, to be returned by the scanner.  */
  public static final int T_FALSE = 270;
  /** Token number, to be returned by the scanner.  */
  public static final int T_FI = 271;
  /** Token number, to be returned by the scanner.  */
  public static final int T_IF = 272;
  /** Token number, to be returned by the scanner.  */
  public static final int T_IN = 273;
  /** Token number, to be returned by the scanner.  */
  public static final int T_INHERITS = 274;
  /** Token number, to be returned by the scanner.  */
  public static final int T_ISVOID = 275;
  /** Token number, to be returned by the scanner.  */
  public static final int T_LET = 276;
  /** Token number, to be returned by the scanner.  */
  public static final int T_LOOP = 277;
  /** Token number, to be returned by the scanner.  */
  public static final int T_POOL = 278;
  /** Token number, to be returned by the scanner.  */
  public static final int T_THEN = 279;
  /** Token number, to be returned by the scanner.  */
  public static final int T_WHILE = 280;
  /** Token number, to be returned by the scanner.  */
  public static final int T_CASE = 281;
  /** Token number, to be returned by the scanner.  */
  public static final int T_ESAC = 282;
  /** Token number, to be returned by the scanner.  */
  public static final int T_NEW = 283;
  /** Token number, to be returned by the scanner.  */
  public static final int T_OF = 284;
  /** Token number, to be returned by the scanner.  */
  public static final int T_NOT = 285;
  /** Token number, to be returned by the scanner.  */
  public static final int T_TRUE = 286;
  /** Token number, to be returned by the scanner.  */
  public static final int T_CAST = 287;
  /** Token number, to be returned by the scanner.  */
  public static final int T_NEGATE = 288;
  /** Token number, to be returned by the scanner.  */
  public static final int T_MULT = 289;
  /** Token number, to be returned by the scanner.  */
  public static final int T_DIV = 290;
  /** Token number, to be returned by the scanner.  */
  public static final int T_PLUS = 291;
  /** Token number, to be returned by the scanner.  */
  public static final int T_MINUS = 292;
  /** Token number, to be returned by the scanner.  */
  public static final int T_LTE = 293;
  /** Token number, to be returned by the scanner.  */
  public static final int T_LT = 294;
  /** Token number, to be returned by the scanner.  */
  public static final int T_EQUALS = 295;
  /** Token number, to be returned by the scanner.  */
  public static final int T_ASSIGN = 296;
  /** Token number, to be returned by the scanner.  */
  public static final int T_FAT = 297;
  /** Token number, to be returned by the scanner.  */
  public static final int T_UNKNOWN = 298;
  /** Token number, to be returned by the scanner.  */
  public static final int TYPE = 299;
  /** Token number, to be returned by the scanner.  */
  public static final int ID = 300;
  /** Token number, to be returned by the scanner.  */
  public static final int STRING = 301;
  /** Token number, to be returned by the scanner.  */
  public static final int BOOL = 302;
  /** Token number, to be returned by the scanner.  */
  public static final int INTEGER = 303;



  
  private Location yylloc (YYStack rhs, int n)
  {
    if (n > 0)
      return new Location (rhs.locationAt (n-1).begin, rhs.locationAt (0).end);
    else
      return new Location (rhs.locationAt (0).end);
  }

  /**
   * Communication interface between the scanner and the Bison-generated
   * parser <tt>Parser</tt>.
   */
  public interface Lexer {
    /**
     * Method to retrieve the beginning position of the last scanned token.
     * @return the position at which the last scanned token starts.  */
    Position getStartPos ();

    /**
     * Method to retrieve the ending position of the last scanned token.
     * @return the first position beyond the last scanned token.  */
    Position getEndPos ();

    /**
     * Method to retrieve the semantic value of the last scanned token.
     * @return the semantic value of the last scanned token.  */
    Object getLVal ();

    /**
     * Entry point for the scanner.  Returns the token identifier corresponding
     * to the next token and prepares to return the semantic value
     * and beginning/ending positions of the token.
     * @return the token identifier corresponding to the next token. */
    int yylex () throws java.io.IOException;

    /**
     * Entry point for error reporting.  Emits an error
     * referring to the given location in a user-defined way.
     *
     * @param loc The location of the element to which the
     *                error message is related
     * @param s The string for the error message.  */
     void yyerror (Location loc, String s);
  }

  /** The object doing lexical analysis for us.  */
  private Lexer yylexer;
  
  



  /**
   * Instantiates the Bison-generated parser.
   * @param yylexer The scanner that will supply tokens to the parser.
   */
  public Parser (Lexer yylexer) {
    this.yylexer = yylexer;
    
  }

  private java.io.PrintStream yyDebugStream = System.err;

  /**
   * Return the <tt>PrintStream</tt> on which the debugging output is
   * printed.
   */
  public final java.io.PrintStream getDebugStream () { return yyDebugStream; }

  /**
   * Set the <tt>PrintStream</tt> on which the debug output is printed.
   * @param s The stream that is used for debugging output.
   */
  public final void setDebugStream(java.io.PrintStream s) { yyDebugStream = s; }

  private int yydebug = 0;

  /**
   * Answer the verbosity of the debugging output; 0 means that all kinds of
   * output from the parser are suppressed.
   */
  public final int getDebugLevel() { return yydebug; }

  /**
   * Set the verbosity of the debugging output; 0 means that all kinds of
   * output from the parser are suppressed.
   * @param level The verbosity level for debugging output.
   */
  public final void setDebugLevel(int level) { yydebug = level; }

  private final int yylex () throws java.io.IOException {
    return yylexer.yylex ();
  }
  protected final void yyerror (Location loc, String s) {
    yylexer.yyerror (loc, s);
  }

  
  protected final void yyerror (String s) {
    yylexer.yyerror ((Location)null, s);
  }
  protected final void yyerror (Position loc, String s) {
    yylexer.yyerror (new Location (loc), s);
  }

  protected final void yycdebug (String s) {
    if (yydebug > 0)
      yyDebugStream.println (s);
  }

  private final class YYStack {
    private int[] stateStack = new int[16];
    private Location[] locStack = new Location[16];
    private Object[] valueStack = new Object[16];

    public int size = 16;
    public int height = -1;

    public final void push (int state, Object value			    , Location loc) {
      height++;
      if (size == height)
        {
	  int[] newStateStack = new int[size * 2];
	  System.arraycopy (stateStack, 0, newStateStack, 0, height);
	  stateStack = newStateStack;
	  
	  Location[] newLocStack = new Location[size * 2];
	  System.arraycopy (locStack, 0, newLocStack, 0, height);
	  locStack = newLocStack;

	  Object[] newValueStack = new Object[size * 2];
	  System.arraycopy (valueStack, 0, newValueStack, 0, height);
	  valueStack = newValueStack;

	  size *= 2;
	}

      stateStack[height] = state;
      locStack[height] = loc;
      valueStack[height] = value;
    }

    public final void pop () {
      pop (1);
    }

    public final void pop (int num) {
      // Avoid memory leaks... garbage collection is a white lie!
      if (num > 0) {
	java.util.Arrays.fill (valueStack, height - num + 1, height + 1, null);
        java.util.Arrays.fill (locStack, height - num + 1, height + 1, null);
      }
      height -= num;
    }

    public final int stateAt (int i) {
      return stateStack[height - i];
    }

    public final Location locationAt (int i) {
      return locStack[height - i];
    }

    public final Object valueAt (int i) {
      return valueStack[height - i];
    }

    // Print the state stack on the debug stream.
    public void print (java.io.PrintStream out)
    {
      out.print ("Stack now");

      for (int i = 0; i <= height; i++)
        {
	  out.print (' ');
	  out.print (stateStack[i]);
        }
      out.println ();
    }
  }

  /**
   * Returned by a Bison action in order to stop the parsing process and
   * return success (<tt>true</tt>).  */
  public static final int YYACCEPT = 0;

  /**
   * Returned by a Bison action in order to stop the parsing process and
   * return failure (<tt>false</tt>).  */
  public static final int YYABORT = 1;

  /**
   * Returned by a Bison action in order to start error recovery without
   * printing an error message.  */
  public static final int YYERROR = 2;

  // Internal return codes that are not supported for user semantic
  // actions.
  private static final int YYERRLAB = 3;
  private static final int YYNEWSTATE = 4;
  private static final int YYDEFAULT = 5;
  private static final int YYREDUCE = 6;
  private static final int YYERRLAB1 = 7;
  private static final int YYRETURN = 8;

  private int yyerrstatus_ = 0;

  /**
   * Return whether error recovery is being done.  In this state, the parser
   * reads token until it reaches a known state, and then restarts normal
   * operation.  */
  public final boolean recovering ()
  {
    return yyerrstatus_ == 0;
  }

  private int yyaction (int yyn, YYStack yystack, int yylen) 
  {
    Object yyval;
    Location yyloc = yylloc (yystack, yylen);

    /* If YYLEN is nonzero, implement the default value of the action:
       `$$ = $1'.  Otherwise, use the top of the stack.

       Otherwise, the following line sets YYVAL to garbage.
       This behavior is undocumented and Bison
       users should not rely upon it.  */
    if (yylen > 0)
      yyval = yystack.valueAt (yylen - 1);
    else
      yyval = yystack.valueAt (0);

    yy_reduce_print (yyn, yystack);

    switch (yyn)
      {
	  case 2:
  if (yyn == 2)
    
/* Line 351 of lalr1.java  */
/* Line 99 of "cool.y"  */
    { _root = ((Program)(yystack.valueAt (1-(1)))); };
  break;
    

  case 3:
  if (yyn == 3)
    
/* Line 351 of lalr1.java  */
/* Line 102 of "cool.y"  */
    { yyval = new Program(((ClassDef)(yystack.valueAt (2-(1))))); };
  break;
    

  case 4:
  if (yyn == 4)
    
/* Line 351 of lalr1.java  */
/* Line 103 of "cool.y"  */
    { ((Program)(yystack.valueAt (3-(1)))).add(((ClassDef)(yystack.valueAt (3-(2))))); yyval = ((Program)(yystack.valueAt (3-(1)))); };
  break;
    

  case 5:
  if (yyn == 5)
    
/* Line 351 of lalr1.java  */
/* Line 108 of "cool.y"  */
    { 
                yyval = new ClassDef(((String)(yystack.valueAt (5-(2)))), ((FeatureList)(yystack.valueAt (5-(4)))));
                ((Node)yyval).setPos(yystack.locationAt (5-(1)).begin);
            };
  break;
    

  case 6:
  if (yyn == 6)
    
/* Line 351 of lalr1.java  */
/* Line 113 of "cool.y"  */
    { yyval = new ClassDef(((String)(yystack.valueAt (7-(2)))), ((String)(yystack.valueAt (7-(4)))), ((FeatureList)(yystack.valueAt (7-(6))))); ((Node)yyval).setPos(yystack.locationAt (7-(1)).begin); };
  break;
    

  case 7:
  if (yyn == 7)
    
/* Line 351 of lalr1.java  */
/* Line 117 of "cool.y"  */
    { 
        yyval = new FeatureList(((Feature)(yystack.valueAt (2-(1))))); 
    };
  break;
    

  case 8:
  if (yyn == 8)
    
/* Line 351 of lalr1.java  */
/* Line 120 of "cool.y"  */
    { 
        ((FeatureList)(yystack.valueAt (3-(1)))).add(((Feature)(yystack.valueAt (3-(2))))); yyval = ((FeatureList)(yystack.valueAt (3-(1)))); 
    };
  break;
    

  case 9:
  if (yyn == 9)
    
/* Line 351 of lalr1.java  */
/* Line 126 of "cool.y"  */
    { 
        yyval = new Method(((String)(yystack.valueAt (9-(1)))), ((DeclList)(yystack.valueAt (9-(3)))), ((String)(yystack.valueAt (9-(6)))), ((Expr)(yystack.valueAt (9-(8))))); 
        ((Node)yyval).setPos(yystack.locationAt (9-(1)).begin);
    };
  break;
    

  case 10:
  if (yyn == 10)
    
/* Line 351 of lalr1.java  */
/* Line 131 of "cool.y"  */
    {
            yyval = ((Variable)(yystack.valueAt (1-(1)))); 
        };
  break;
    

  case 11:
  if (yyn == 11)
    
/* Line 351 of lalr1.java  */
/* Line 137 of "cool.y"  */
    { yyval = new DeclList(); };
  break;
    

  case 12:
  if (yyn == 12)
    
/* Line 351 of lalr1.java  */
/* Line 138 of "cool.y"  */
    { yyval = ((DeclList)(yystack.valueAt (1-(1)))); };
  break;
    

  case 13:
  if (yyn == 13)
    
/* Line 351 of lalr1.java  */
/* Line 143 of "cool.y"  */
    { yyval = new DeclList(((Variable)(yystack.valueAt (1-(1))))); };
  break;
    

  case 14:
  if (yyn == 14)
    
/* Line 351 of lalr1.java  */
/* Line 146 of "cool.y"  */
    { ((DeclList)(yystack.valueAt (3-(1)))).add(((Variable)(yystack.valueAt (3-(3))))); yyval = ((DeclList)(yystack.valueAt (3-(1)))); };
  break;
    

  case 15:
  if (yyn == 15)
    
/* Line 351 of lalr1.java  */
/* Line 150 of "cool.y"  */
    { yyval = new Variable(((String)(yystack.valueAt (3-(1)))), ((String)(yystack.valueAt (3-(3))))); ((Node)yyval).setPos(yystack.locationAt (3-(1)).begin); };
  break;
    

  case 16:
  if (yyn == 16)
    
/* Line 351 of lalr1.java  */
/* Line 155 of "cool.y"  */
    { yyval = new Args(); };
  break;
    

  case 17:
  if (yyn == 17)
    
/* Line 351 of lalr1.java  */
/* Line 156 of "cool.y"  */
    { yyval = ((Args)(yystack.valueAt (1-(1)))); };
  break;
    

  case 18:
  if (yyn == 18)
    
/* Line 351 of lalr1.java  */
/* Line 160 of "cool.y"  */
    {   yyval = new Args(((Expr)(yystack.valueAt (1-(1))))); };
  break;
    

  case 19:
  if (yyn == 19)
    
/* Line 351 of lalr1.java  */
/* Line 162 of "cool.y"  */
    { ((Args)(yystack.valueAt (3-(1)))).add(((Expr)(yystack.valueAt (3-(3))))); yyval = ((Args)(yystack.valueAt (3-(1)))); };
  break;
    

  case 20:
  if (yyn == 20)
    
/* Line 351 of lalr1.java  */
/* Line 166 of "cool.y"  */
    {  yyval = new StmtList(((Expr)(yystack.valueAt (2-(1))))); };
  break;
    

  case 21:
  if (yyn == 21)
    
/* Line 351 of lalr1.java  */
/* Line 168 of "cool.y"  */
    {
                ((StmtList)(yystack.valueAt (3-(1)))).add(((Expr)(yystack.valueAt (3-(2)))));
                yyval = ((StmtList)(yystack.valueAt (3-(1))));
            };
  break;
    

  case 22:
  if (yyn == 22)
    
/* Line 351 of lalr1.java  */
/* Line 175 of "cool.y"  */
    {  yyval = new Variable(((String)(yystack.valueAt (3-(1)))), ((String)(yystack.valueAt (3-(3))))); ((Node)yyval).setPos(yystack.locationAt (3-(1)).begin); };
  break;
    

  case 23:
  if (yyn == 23)
    
/* Line 351 of lalr1.java  */
/* Line 177 of "cool.y"  */
    {  yyval = new Variable(((String)(yystack.valueAt (5-(1)))), ((String)(yystack.valueAt (5-(3)))), ((Expr)(yystack.valueAt (5-(5))))); ((Node)yyval).setPos(yystack.locationAt (5-(1)).begin); };
  break;
    

  case 24:
  if (yyn == 24)
    
/* Line 351 of lalr1.java  */
/* Line 182 of "cool.y"  */
    {   yyval = new DeclList(((Variable)(yystack.valueAt (1-(1))))); };
  break;
    

  case 25:
  if (yyn == 25)
    
/* Line 351 of lalr1.java  */
/* Line 184 of "cool.y"  */
    { 
                ((DeclList)(yystack.valueAt (3-(1)))).add(((Variable)(yystack.valueAt (3-(3)))));
                yyval = ((DeclList)(yystack.valueAt (3-(1))));
            };
  break;
    

  case 26:
  if (yyn == 26)
    
/* Line 351 of lalr1.java  */
/* Line 192 of "cool.y"  */
    { yyval =  new Case(((String)(yystack.valueAt (6-(1)))), ((String)(yystack.valueAt (6-(3)))), ((Expr)(yystack.valueAt (6-(5))))); ((Node)yyval).setPos(yystack.locationAt (6-(1)).begin); };
  break;
    

  case 27:
  if (yyn == 27)
    
/* Line 351 of lalr1.java  */
/* Line 198 of "cool.y"  */
    {   yyval = new CaseList(((Case)(yystack.valueAt (1-(1))))); };
  break;
    

  case 28:
  if (yyn == 28)
    
/* Line 351 of lalr1.java  */
/* Line 201 of "cool.y"  */
    {   ((CaseList)(yystack.valueAt (2-(1)))).add(((Case)(yystack.valueAt (2-(2))))); yyval = ((CaseList)(yystack.valueAt (2-(1)))); };
  break;
    

  case 29:
  if (yyn == 29)
    
/* Line 351 of lalr1.java  */
/* Line 206 of "cool.y"  */
    { yyval = new AssignExpr(((String)(yystack.valueAt (3-(1)))), ((Expr)(yystack.valueAt (3-(3))))); ((Node)yyval).setPos(yystack.locationAt (3-(1)).begin); };
  break;
    

  case 30:
  if (yyn == 30)
    
/* Line 351 of lalr1.java  */
/* Line 209 of "cool.y"  */
    { yyval = new DispatchExpr(((Expr)(yystack.valueAt (8-(1)))), ((String)(yystack.valueAt (8-(3)))), ((String)(yystack.valueAt (8-(5)))), ((Args)(yystack.valueAt (8-(7)))));  ((Node)yyval).setPos(((Node)((Expr)(yystack.valueAt (8-(1))))).getPos());  };
  break;
    

  case 31:
  if (yyn == 31)
    
/* Line 351 of lalr1.java  */
/* Line 212 of "cool.y"  */
    { yyval = new DispatchExpr(((Expr)(yystack.valueAt (6-(1)))), ((String)(yystack.valueAt (6-(3)))), ((Args)(yystack.valueAt (6-(5)))));  ((Node)yyval).setPos(((Node)((Expr)(yystack.valueAt (6-(1))))).getPos());  };
  break;
    

  case 32:
  if (yyn == 32)
    
/* Line 351 of lalr1.java  */
/* Line 215 of "cool.y"  */
    { yyval = new DispatchExpr(((String)(yystack.valueAt (4-(1)))), ((Args)(yystack.valueAt (4-(3)))) ); ((Node)yyval).setPos(yystack.locationAt (4-(1)).begin); };
  break;
    

  case 33:
  if (yyn == 33)
    
/* Line 351 of lalr1.java  */
/* Line 218 of "cool.y"  */
    { yyval = new IfExpr(((Expr)(yystack.valueAt (7-(2)))), ((Expr)(yystack.valueAt (7-(4)))), ((Expr)(yystack.valueAt (7-(6)))));  ((Node)yyval).setPos(yystack.locationAt (7-(1)).begin); };
  break;
    

  case 34:
  if (yyn == 34)
    
/* Line 351 of lalr1.java  */
/* Line 221 of "cool.y"  */
    { yyval = new WhileExpr(((Expr)(yystack.valueAt (5-(2)))), ((Expr)(yystack.valueAt (5-(4))))); ((Node)yyval).setPos(yystack.locationAt (5-(1)).begin); };
  break;
    

  case 35:
  if (yyn == 35)
    
/* Line 351 of lalr1.java  */
/* Line 224 of "cool.y"  */
    { yyval = new Block(((StmtList)(yystack.valueAt (3-(2))))); ((Node)yyval).setPos(yystack.locationAt (3-(1)).begin); };
  break;
    

  case 36:
  if (yyn == 36)
    
/* Line 351 of lalr1.java  */
/* Line 227 of "cool.y"  */
    { yyval = new LetExpr(((DeclList)(yystack.valueAt (4-(2)))), ((Expr)(yystack.valueAt (4-(4))))); ((Node)yyval).setPos(yystack.locationAt (4-(1)).begin); };
  break;
    

  case 37:
  if (yyn == 37)
    
/* Line 351 of lalr1.java  */
/* Line 230 of "cool.y"  */
    { yyval = new CaseExpr(((Expr)(yystack.valueAt (5-(2)))), ((CaseList)(yystack.valueAt (5-(4))))); ((Node)yyval).setPos(yystack.locationAt (5-(1)).begin); };
  break;
    

  case 38:
  if (yyn == 38)
    
/* Line 351 of lalr1.java  */
/* Line 232 of "cool.y"  */
    { yyval = new NewExpr(((String)(yystack.valueAt (2-(2))))); ((Node)yyval).setPos(yystack.locationAt (2-(1)).begin); };
  break;
    

  case 39:
  if (yyn == 39)
    
/* Line 351 of lalr1.java  */
/* Line 233 of "cool.y"  */
    { yyval = new UnaryExpr( UnaryOp.ISVOID, ((Expr)(yystack.valueAt (2-(2))))); ((Node)yyval).setPos(yystack.locationAt (2-(1)).begin); };
  break;
    

  case 40:
  if (yyn == 40)
    
/* Line 351 of lalr1.java  */
/* Line 234 of "cool.y"  */
    { yyval = new BinaryExpr( BinaryOp.PLUS, ((Expr)(yystack.valueAt (3-(1)))), ((Expr)(yystack.valueAt (3-(3))))); ((Node)yyval).setPos(((Node)((Expr)(yystack.valueAt (3-(1))))).getPos()); };
  break;
    

  case 41:
  if (yyn == 41)
    
/* Line 351 of lalr1.java  */
/* Line 235 of "cool.y"  */
    { yyval = new BinaryExpr( BinaryOp.MINUS, ((Expr)(yystack.valueAt (3-(1)))), ((Expr)(yystack.valueAt (3-(3))))); ((Node)yyval).setPos(((Node)((Expr)(yystack.valueAt (3-(1))))).getPos()); };
  break;
    

  case 42:
  if (yyn == 42)
    
/* Line 351 of lalr1.java  */
/* Line 236 of "cool.y"  */
    { yyval = new BinaryExpr( BinaryOp.MULT, ((Expr)(yystack.valueAt (3-(1)))), ((Expr)(yystack.valueAt (3-(3))))); ((Node)yyval).setPos(((Node)((Expr)(yystack.valueAt (3-(1))))).getPos()); };
  break;
    

  case 43:
  if (yyn == 43)
    
/* Line 351 of lalr1.java  */
/* Line 237 of "cool.y"  */
    { yyval = new BinaryExpr( BinaryOp.DIV, ((Expr)(yystack.valueAt (3-(1)))), ((Expr)(yystack.valueAt (3-(3))))); ((Node)yyval).setPos(((Node)((Expr)(yystack.valueAt (3-(1))))).getPos()); };
  break;
    

  case 44:
  if (yyn == 44)
    
/* Line 351 of lalr1.java  */
/* Line 238 of "cool.y"  */
    { yyval = new UnaryExpr( UnaryOp.NEGATE, ((Expr)(yystack.valueAt (2-(2))))); ((Node)yyval).setPos(yystack.locationAt (2-(1)).begin); };
  break;
    

  case 45:
  if (yyn == 45)
    
/* Line 351 of lalr1.java  */
/* Line 239 of "cool.y"  */
    { yyval = new BinaryExpr( BinaryOp.LT, ((Expr)(yystack.valueAt (3-(1)))), ((Expr)(yystack.valueAt (3-(3))))); ((Node)yyval).setPos(((Node)((Expr)(yystack.valueAt (3-(1))))).getPos()); };
  break;
    

  case 46:
  if (yyn == 46)
    
/* Line 351 of lalr1.java  */
/* Line 240 of "cool.y"  */
    { yyval = new BinaryExpr( BinaryOp.LTE, ((Expr)(yystack.valueAt (3-(1)))), ((Expr)(yystack.valueAt (3-(3))))); ((Node)yyval).setPos(((Node)((Expr)(yystack.valueAt (3-(1))))).getPos()); };
  break;
    

  case 47:
  if (yyn == 47)
    
/* Line 351 of lalr1.java  */
/* Line 241 of "cool.y"  */
    { yyval = new BinaryExpr( BinaryOp.EQUALS, ((Expr)(yystack.valueAt (3-(1)))), ((Expr)(yystack.valueAt (3-(3))))); ((Node)yyval).setPos(((Node)((Expr)(yystack.valueAt (3-(1))))).getPos()); };
  break;
    

  case 48:
  if (yyn == 48)
    
/* Line 351 of lalr1.java  */
/* Line 242 of "cool.y"  */
    { yyval = new UnaryExpr( UnaryOp.NOT, ((Expr)(yystack.valueAt (2-(2))))); ((Node)yyval).setPos(yystack.locationAt (2-(1)).begin); };
  break;
    

  case 49:
  if (yyn == 49)
    
/* Line 351 of lalr1.java  */
/* Line 243 of "cool.y"  */
    { yyval = ((Expr)(yystack.valueAt (3-(2)))); };
  break;
    

  case 50:
  if (yyn == 50)
    
/* Line 351 of lalr1.java  */
/* Line 244 of "cool.y"  */
    { yyval = new IdExpr(((String)(yystack.valueAt (1-(1))))); ((Node)yyval).setPos(yystack.locationAt (1-(1)).begin); };
  break;
    

  case 51:
  if (yyn == 51)
    
/* Line 351 of lalr1.java  */
/* Line 245 of "cool.y"  */
    { yyval = new ValueExpr<Integer>(((Integer)(yystack.valueAt (1-(1))))); ((Node)yyval).setPos(yystack.locationAt (1-(1)).begin); };
  break;
    

  case 52:
  if (yyn == 52)
    
/* Line 351 of lalr1.java  */
/* Line 246 of "cool.y"  */
    { yyval = new ValueExpr<String>(((String)(yystack.valueAt (1-(1))))); ((Node)yyval).setPos(yystack.locationAt (1-(1)).begin); };
  break;
    

  case 53:
  if (yyn == 53)
    
/* Line 351 of lalr1.java  */
/* Line 247 of "cool.y"  */
    { yyval = new ValueExpr<Boolean>(true); ((Node)yyval).setPos(yystack.locationAt (1-(1)).begin); };
  break;
    

  case 54:
  if (yyn == 54)
    
/* Line 351 of lalr1.java  */
/* Line 248 of "cool.y"  */
    { yyval = new ValueExpr<Boolean>(false); ((Node)yyval).setPos(yystack.locationAt (1-(1)).begin); };
  break;
    



/* Line 351 of lalr1.java  */
/* Line 938 of "src/coolc/parser/Parser.java"  */
	default: break;
      }

    yy_symbol_print ("-> $$ =", yyr1_[yyn], yyval, yyloc);

    yystack.pop (yylen);
    yylen = 0;

    /* Shift the result of the reduction.  */
    yyn = yyr1_[yyn];
    int yystate = yypgoto_[yyn - yyntokens_] + yystack.stateAt (0);
    if (0 <= yystate && yystate <= yylast_
	&& yycheck_[yystate] == yystack.stateAt (0))
      yystate = yytable_[yystate];
    else
      yystate = yydefgoto_[yyn - yyntokens_];

    yystack.push (yystate, yyval, yyloc);
    return YYNEWSTATE;
  }

  /* Return YYSTR after stripping away unnecessary quotes and
     backslashes, so that it's suitable for yyerror.  The heuristic is
     that double-quoting is unnecessary unless the string contains an
     apostrophe, a comma, or backslash (other than backslash-backslash).
     YYSTR is taken from yytname.  */
  private final String yytnamerr_ (String yystr)
  {
    if (yystr.charAt (0) == '"')
      {
        StringBuffer yyr = new StringBuffer ();
        strip_quotes: for (int i = 1; i < yystr.length (); i++)
          switch (yystr.charAt (i))
            {
            case '\'':
            case ',':
              break strip_quotes;

            case '\\':
	      if (yystr.charAt(++i) != '\\')
                break strip_quotes;
              /* Fall through.  */
            default:
              yyr.append (yystr.charAt (i));
              break;

            case '"':
              return yyr.toString ();
            }
      }
    else if (yystr.equals ("$end"))
      return "end of input";

    return yystr;
  }

  /*--------------------------------.
  | Print this symbol on YYOUTPUT.  |
  `--------------------------------*/

  private void yy_symbol_print (String s, int yytype,
			         Object yyvaluep				 , Object yylocationp)
  {
    if (yydebug > 0)
    yycdebug (s + (yytype < yyntokens_ ? " token " : " nterm ")
	      + yytname_[yytype] + " ("
	      + yylocationp + ": "
	      + (yyvaluep == null ? "(null)" : yyvaluep.toString ()) + ")");
  }

  /**
   * Parse input from the scanner that was specified at object construction
   * time.  Return whether the end of the input was reached successfully.
   *
   * @return <tt>true</tt> if the parsing succeeds.  Note that this does not
   *          imply that there were no syntax errors.
   */
  public boolean parse () throws java.io.IOException
  {
    /// Lookahead and lookahead in internal form.
    int yychar = yyempty_;
    int yytoken = 0;

    /* State.  */
    int yyn = 0;
    int yylen = 0;
    int yystate = 0;

    YYStack yystack = new YYStack ();

    /* Error handling.  */
    int yynerrs_ = 0;
    /// The location where the error started.
    Location yyerrloc = null;

    /// Location of the lookahead.
    Location yylloc = new Location (null, null);

    /// @$.
    Location yyloc;

    /// Semantic value of the lookahead.
    Object yylval = null;

    int yyresult;

    yycdebug ("Starting parse\n");
    yyerrstatus_ = 0;


    /* Initialize the stack.  */
    yystack.push (yystate, yylval, yylloc);

    int label = YYNEWSTATE;
    for (;;)
      switch (label)
      {
        /* New state.  Unlike in the C/C++ skeletons, the state is already
	   pushed when we come here.  */
      case YYNEWSTATE:
        yycdebug ("Entering state " + yystate + "\n");
        if (yydebug > 0)
          yystack.print (yyDebugStream);

        /* Accept?  */
        if (yystate == yyfinal_)
          return true;

        /* Take a decision.  First try without lookahead.  */
        yyn = yypact_[yystate];
        if (yy_pact_value_is_default_ (yyn))
          {
            label = YYDEFAULT;
	    break;
          }

        /* Read a lookahead token.  */
        if (yychar == yyempty_)
          {
	    yycdebug ("Reading a token: ");
	    yychar = yylex ();
            
	    yylloc = new Location(yylexer.getStartPos (),
				            yylexer.getEndPos ());
            yylval = yylexer.getLVal ();
          }

        /* Convert token to internal form.  */
        if (yychar <= EOF)
          {
	    yychar = yytoken = EOF;
	    yycdebug ("Now at end of input.\n");
          }
        else
          {
	    yytoken = yytranslate_ (yychar);
	    yy_symbol_print ("Next token is", yytoken,
			     yylval, yylloc);
          }

        /* If the proper action on seeing token YYTOKEN is to reduce or to
           detect an error, take that action.  */
        yyn += yytoken;
        if (yyn < 0 || yylast_ < yyn || yycheck_[yyn] != yytoken)
          label = YYDEFAULT;

        /* <= 0 means reduce or error.  */
        else if ((yyn = yytable_[yyn]) <= 0)
          {
	    if (yy_table_value_is_error_ (yyn))
	      label = YYERRLAB;
	    else
	      {
	        yyn = -yyn;
	        label = YYREDUCE;
	      }
          }

        else
          {
            /* Shift the lookahead token.  */
	    yy_symbol_print ("Shifting", yytoken,
			     yylval, yylloc);

            /* Discard the token being shifted.  */
            yychar = yyempty_;

            /* Count tokens shifted since error; after three, turn off error
               status.  */
            if (yyerrstatus_ > 0)
              --yyerrstatus_;

            yystate = yyn;
            yystack.push (yystate, yylval, yylloc);
            label = YYNEWSTATE;
          }
        break;

      /*-----------------------------------------------------------.
      | yydefault -- do the default action for the current state.  |
      `-----------------------------------------------------------*/
      case YYDEFAULT:
        yyn = yydefact_[yystate];
        if (yyn == 0)
          label = YYERRLAB;
        else
          label = YYREDUCE;
        break;

      /*-----------------------------.
      | yyreduce -- Do a reduction.  |
      `-----------------------------*/
      case YYREDUCE:
        yylen = yyr2_[yyn];
        label = yyaction (yyn, yystack, yylen);
	yystate = yystack.stateAt (0);
        break;

      /*------------------------------------.
      | yyerrlab -- here on detecting error |
      `------------------------------------*/
      case YYERRLAB:
        /* If not already recovering from an error, report this error.  */
        if (yyerrstatus_ == 0)
          {
            ++yynerrs_;
            if (yychar == yyempty_)
              yytoken = yyempty_;
            yyerror (yylloc, yysyntax_error (yystate, yytoken));
          }

        yyerrloc = yylloc;
        if (yyerrstatus_ == 3)
          {
	    /* If just tried and failed to reuse lookahead token after an
	     error, discard it.  */

	    if (yychar <= EOF)
	      {
	      /* Return failure if at end of input.  */
	      if (yychar == EOF)
	        return false;
	      }
	    else
	      yychar = yyempty_;
          }

        /* Else will try to reuse lookahead token after shifting the error
           token.  */
        label = YYERRLAB1;
        break;

      /*---------------------------------------------------.
      | errorlab -- error raised explicitly by YYERROR.  |
      `---------------------------------------------------*/
      case YYERROR:

        yyerrloc = yystack.locationAt (yylen - 1);
        /* Do not reclaim the symbols of the rule which action triggered
           this YYERROR.  */
        yystack.pop (yylen);
        yylen = 0;
        yystate = yystack.stateAt (0);
        label = YYERRLAB1;
        break;

      /*-------------------------------------------------------------.
      | yyerrlab1 -- common code for both syntax error and YYERROR.  |
      `-------------------------------------------------------------*/
      case YYERRLAB1:
        yyerrstatus_ = 3;	/* Each real token shifted decrements this.  */

        for (;;)
          {
	    yyn = yypact_[yystate];
	    if (!yy_pact_value_is_default_ (yyn))
	      {
	        yyn += yyterror_;
	        if (0 <= yyn && yyn <= yylast_ && yycheck_[yyn] == yyterror_)
	          {
	            yyn = yytable_[yyn];
	            if (0 < yyn)
		      break;
	          }
	      }

	    /* Pop the current state because it cannot handle the error token.  */
	    if (yystack.height == 1)
	      return false;

	    yyerrloc = yystack.locationAt (0);
	    yystack.pop ();
	    yystate = yystack.stateAt (0);
	    if (yydebug > 0)
	      yystack.print (yyDebugStream);
          }

	
	/* Muck with the stack to setup for yylloc.  */
	yystack.push (0, null, yylloc);
	yystack.push (0, null, yyerrloc);
        yyloc = yylloc (yystack, 2);
	yystack.pop (2);

        /* Shift the error token.  */
        yy_symbol_print ("Shifting", yystos_[yyn],
			 yylval, yyloc);

        yystate = yyn;
	yystack.push (yyn, yylval, yyloc);
        label = YYNEWSTATE;
        break;

        /* Accept.  */
      case YYACCEPT:
        return true;

        /* Abort.  */
      case YYABORT:
        return false;
      }
  }

  // Generate an error message.
  private String yysyntax_error (int yystate, int tok)
  {
    if (errorVerbose)
      {
        /* There are many possibilities here to consider:
           - Assume YYFAIL is not used.  It's too flawed to consider.
             See
             <http://lists.gnu.org/archive/html/bison-patches/2009-12/msg00024.html>
             for details.  YYERROR is fine as it does not invoke this
             function.
           - If this state is a consistent state with a default action,
             then the only way this function was invoked is if the
             default action is an error action.  In that case, don't
             check for expected tokens because there are none.
           - The only way there can be no lookahead present (in tok) is
             if this state is a consistent state with a default action.
             Thus, detecting the absence of a lookahead is sufficient to
             determine that there is no unexpected or expected token to
             report.  In that case, just report a simple "syntax error".
           - Don't assume there isn't a lookahead just because this
             state is a consistent state with a default action.  There
             might have been a previous inconsistent state, consistent
             state with a non-default action, or user semantic action
             that manipulated yychar.  (However, yychar is currently out
             of scope during semantic actions.)
           - Of course, the expected token list depends on states to
             have correct lookahead information, and it depends on the
             parser not to perform extra reductions after fetching a
             lookahead from the scanner and before detecting a syntax
             error.  Thus, state merging (from LALR or IELR) and default
             reductions corrupt the expected token list.  However, the
             list is correct for canonical LR with one exception: it
             will still contain any token that will not be accepted due
             to an error action in a later state.
        */
        if (tok != yyempty_)
          {
            // FIXME: This method of building the message is not compatible
            // with internationalization.
            StringBuffer res =
              new StringBuffer ("syntax error, unexpected ");
            res.append (yytnamerr_ (yytname_[tok]));
            int yyn = yypact_[yystate];
            if (!yy_pact_value_is_default_ (yyn))
              {
                /* Start YYX at -YYN if negative to avoid negative
                   indexes in YYCHECK.  In other words, skip the first
                   -YYN actions for this state because they are default
                   actions.  */
                int yyxbegin = yyn < 0 ? -yyn : 0;
                /* Stay within bounds of both yycheck and yytname.  */
                int yychecklim = yylast_ - yyn + 1;
                int yyxend = yychecklim < yyntokens_ ? yychecklim : yyntokens_;
                int count = 0;
                for (int x = yyxbegin; x < yyxend; ++x)
                  if (yycheck_[x + yyn] == x && x != yyterror_
                      && !yy_table_value_is_error_ (yytable_[x + yyn]))
                    ++count;
                if (count < 5)
                  {
                    count = 0;
                    for (int x = yyxbegin; x < yyxend; ++x)
                      if (yycheck_[x + yyn] == x && x != yyterror_
                          && !yy_table_value_is_error_ (yytable_[x + yyn]))
                        {
                          res.append (count++ == 0 ? ", expecting " : " or ");
                          res.append (yytnamerr_ (yytname_[x]));
                        }
                  }
              }
            return res.toString ();
          }
      }

    return "syntax error";
  }

  /**
   * Whether the given <code>yypact_</code> value indicates a defaulted state.
   * @param yyvalue   the value to check
   */
  private static boolean yy_pact_value_is_default_ (int yyvalue)
  {
    return yyvalue == yypact_ninf_;
  }

  /**
   * Whether the given <code>yytable_</code> value indicates a syntax error.
   * @param yyvalue   the value to check
   */
  private static boolean yy_table_value_is_error_ (int yyvalue)
  {
    return yyvalue == yytable_ninf_;
  }

  /* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
     STATE-NUM.  */
  private static final short yypact_ninf_ = -47;
  private static final short yypact_[] =
  {
         6,   -18,    58,     6,    20,    47,   -47,    60,   -47,    26,
      29,   -47,    17,    11,    68,   -47,    73,    35,    38,   -47,
      76,   -47,    26,    79,    87,    85,   -47,    53,   -47,    12,
      54,    93,    35,    82,   -47,   -47,    57,   -47,    82,    82,
     -47,    82,    82,    59,    82,    82,    65,    82,   -47,    82,
       7,   -47,   -47,   214,   106,    84,    44,    97,   139,    -7,
     107,   -47,    -3,   150,   171,   -47,   214,    -7,    82,    82,
      96,   102,    82,    82,    82,    82,    82,    82,    82,    82,
     -47,   -47,   117,   -47,    82,    59,    82,    82,   105,   143,
     148,   214,   214,   157,   152,    -7,    -7,    28,    28,   108,
     108,   108,    -5,   -47,   182,   -47,   214,   192,   174,   -47,
     -24,   -47,    82,    82,   138,   -47,    82,   -47,   149,   -47,
     -47,   214,   188,   191,   224,   153,   -47,    82,   -47,    82,
     193,   130,   -47,   -47
  };

  /* YYDEFACT[S] -- default reduction number in state S.  Performed when
     YYTABLE doesn't specify something else to do.  Zero means the
     default is an error.  */
  private static final byte yydefact_[] =
  {
         0,     0,     0,     2,     0,     0,     1,     0,     3,     0,
       0,     4,     0,     0,     0,    10,     0,    11,     0,     5,
       0,     7,     0,     0,     0,    12,    13,    22,     8,     0,
       0,     0,     0,     0,     6,    15,     0,    14,     0,     0,
      54,     0,     0,     0,     0,     0,     0,     0,    53,     0,
      50,    52,    51,    23,     0,     0,     0,     0,     0,    39,
       0,    24,     0,     0,     0,    38,    48,    44,    16,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
      49,    35,     0,    20,     0,     0,     0,     0,     0,     0,
      17,    18,    29,     0,     0,    42,    43,    40,    41,    46,
      45,    47,     0,    21,     0,    25,    36,     0,     0,    27,
       0,    32,     0,    16,     0,     9,     0,    34,     0,    37,
      28,    19,     0,     0,     0,     0,    31,    16,    33,     0,
       0,     0,    30,    26
  };

  /* YYPGOTO[NTERM-NUM].  */
  private static final short yypgoto_[] =
  {
       -47,   -47,   -47,   195,   177,     9,   -47,   -47,   170,   -46,
     -47,   -47,   -30,   -47,    94,   -47,   -33
  };

  /* YYDEFGOTO[NTERM-NUM].  */
  private static final byte
  yydefgoto_[] =
  {
        -1,     2,     3,     4,    13,    14,    24,    25,    26,    89,
      90,    56,    15,    62,   109,   110,    91
  };

  /* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule which
     number is the opposite.  If YYTABLE_NINF_, syntax error.  */
  private static final short yytable_ninf_ = -1;
  private static final short
  yytable_[] =
  {
        53,   115,    70,   119,    70,    55,    57,    85,    58,    59,
      68,    63,    64,    61,    66,    86,    67,    19,    34,     1,
      17,   108,    20,    82,    18,    71,     5,    71,     8,    72,
      73,    74,    75,    76,    77,    78,    92,    70,    20,    95,
      96,    97,    98,    99,   100,   101,   102,    38,    69,    39,
      81,   104,     9,   106,   107,   105,    12,    12,     6,    40,
      71,    41,    72,    73,    42,    43,    10,   122,    11,    44,
      45,    12,    46,    16,    47,    48,    21,    49,    22,   121,
      23,   130,    27,   124,    28,    38,    30,    39,    80,    50,
      51,    31,    52,    70,    33,    32,   131,    40,    35,    41,
      36,    54,    42,    43,    60,    83,    70,    44,    45,    65,
      46,    79,    47,    48,    18,    49,    71,    70,    72,    73,
      74,    75,    76,    77,    78,   103,    70,    50,    51,    71,
      52,    72,    73,    74,    75,    76,    77,    78,   133,    70,
      71,    93,    72,    73,    74,    75,    94,   111,    70,    71,
     108,    72,    73,    74,    75,    76,    77,    78,   112,    70,
     113,   114,    71,    84,    72,    73,    74,    75,    76,    77,
      78,    71,    87,    72,    73,    74,    75,    76,    77,    78,
      70,   118,    71,   123,    72,    73,    74,    75,    76,    77,
      78,    70,   126,   125,   127,   129,   116,   132,     7,    29,
      88,    70,    37,    71,   120,    72,    73,    74,    75,    76,
      77,    78,     0,     0,    71,   117,    72,    73,    74,    75,
      76,    77,    78,    70,    71,     0,    72,    73,    74,    75,
      76,    77,    78,    70,     0,     0,     0,     0,     0,     0,
     128,     0,     0,     0,     0,     0,    71,     0,    72,    73,
      74,    75,    76,    77,    78,     0,    71,     0,    72,    73,
      74,    75,    76,    77,    78
  };

  /* YYCHECK.  */
  private static final short
  yycheck_[] =
  {
        33,     6,     9,    27,     9,    38,    39,    10,    41,    42,
       3,    44,    45,    43,    47,    18,    49,     6,     6,    13,
       3,    45,    13,    56,     7,    32,    44,    32,     8,    34,
      35,    36,    37,    38,    39,    40,    69,     9,    29,    72,
      73,    74,    75,    76,    77,    78,    79,     3,    41,     5,
       6,    84,     5,    86,    87,    85,    45,    45,     0,    15,
      32,    17,    34,    35,    20,    21,    19,   113,     8,    25,
      26,    45,    28,    44,    30,    31,     8,    33,     5,   112,
      45,   127,    44,   116,     8,     3,     7,     5,     4,    45,
      46,     4,    48,     9,    41,    10,   129,    15,    44,    17,
       7,    44,    20,    21,    45,     8,     9,    25,    26,    44,
      28,     5,    30,    31,     7,    33,    32,     9,    34,    35,
      36,    37,    38,    39,    40,     8,     9,    45,    46,    32,
      48,    34,    35,    36,    37,    38,    39,    40,     8,     9,
      32,    45,    34,    35,    36,    37,    44,     4,     9,    32,
      45,    34,    35,    36,    37,    38,    39,    40,    10,     9,
       3,     9,    32,    24,    34,    35,    36,    37,    38,    39,
      40,    32,    22,    34,    35,    36,    37,    38,    39,    40,
       9,     7,    32,    45,    34,    35,    36,    37,    38,    39,
      40,     9,     4,    44,     3,    42,    14,     4,     3,    22,
      29,     9,    32,    32,   110,    34,    35,    36,    37,    38,
      39,    40,    -1,    -1,    32,    23,    34,    35,    36,    37,
      38,    39,    40,     9,    32,    -1,    34,    35,    36,    37,
      38,    39,    40,     9,    -1,    -1,    -1,    -1,    -1,    -1,
      16,    -1,    -1,    -1,    -1,    -1,    32,    -1,    34,    35,
      36,    37,    38,    39,    40,    -1,    32,    -1,    34,    35,
      36,    37,    38,    39,    40
  };

  /* STOS_[STATE-NUM] -- The (internal number of the) accessing
     symbol of state STATE-NUM.  */
  private static final byte
  yystos_[] =
  {
         0,    13,    50,    51,    52,    44,     0,    52,     8,     5,
      19,     8,    45,    53,    54,    61,    44,     3,     7,     6,
      54,     8,     5,    45,    55,    56,    57,    44,     8,    53,
       7,     4,    10,    41,     6,    44,     7,    57,     3,     5,
      15,    17,    20,    21,    25,    26,    28,    30,    31,    33,
      45,    46,    48,    65,    44,    65,    60,    65,    65,    65,
      45,    61,    62,    65,    65,    44,    65,    65,     3,    41,
       9,    32,    34,    35,    36,    37,    38,    39,    40,     5,
       4,     6,    65,     8,    24,    10,    18,    22,    29,    58,
      59,    65,    65,    45,    44,    65,    65,    65,    65,    65,
      65,    65,    65,     8,    65,    61,    65,    65,    45,    63,
      64,     4,    10,     3,     9,     6,    14,    23,     7,    27,
      63,    65,    58,    45,    65,    44,     4,     3,    16,    42,
      58,    65,     4,     8
  };

  /* TOKEN_NUMBER_[YYLEX-NUM] -- Internal symbol number corresponding
     to YYLEX-NUM.  */
  private static final short
  yytoken_number_[] =
  {
         0,   256,   257,   258,   259,   260,   261,   262,   263,   264,
     265,   266,   267,   268,   269,   270,   271,   272,   273,   274,
     275,   276,   277,   278,   279,   280,   281,   282,   283,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303
  };

  /* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */
  private static final byte
  yyr1_[] =
  {
         0,    49,    50,    51,    51,    52,    52,    53,    53,    54,
      54,    55,    55,    56,    56,    57,    58,    58,    59,    59,
      60,    60,    61,    61,    62,    62,    63,    64,    64,    65,
      65,    65,    65,    65,    65,    65,    65,    65,    65,    65,
      65,    65,    65,    65,    65,    65,    65,    65,    65,    65,
      65,    65,    65,    65,    65
  };

  /* YYR2[YYN] -- Number of symbols composing right hand side of rule YYN.  */
  private static final byte
  yyr2_[] =
  {
         0,     2,     1,     2,     3,     5,     7,     2,     3,     9,
       1,     0,     1,     1,     3,     3,     0,     1,     1,     3,
       2,     3,     3,     5,     1,     3,     6,     1,     2,     3,
       8,     6,     4,     7,     5,     3,     4,     5,     2,     2,
       3,     3,     3,     3,     2,     3,     3,     3,     2,     3,
       1,     1,     1,     1,     1
  };

  /* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
     First, the terminals, then, starting at \a yyntokens_, nonterminals.  */
  private static final String yytname_[] =
  {
    "$end", "error", "$undefined", "\"(\"", "\")\"", "\"{\"", "\"}\"",
  "\":\"", "\";\"", "\".\"", "\",\"", "\"\\\"\"", "\"\\\\\"", "\"class\"",
  "\"else\"", "\"false\"", "\"fi\"", "\"if\"", "\"in\"", "\"inherits\"",
  "\"isvoid\"", "\"let\"", "\"loop\"", "\"pool\"", "\"then\"", "\"while\"",
  "\"case\"", "\"esac\"", "\"new\"", "\"of\"", "\"not\"", "\"true\"",
  "\"@\"", "\"~\"", "\"*\"", "\"/\"", "\"+\"", "\"-\"", "\"<=\"", "\"<\"",
  "\"=\"", "\"<-\"", "\"=>\"", "T_UNKNOWN", "TYPE", "ID", "STRING", "BOOL",
  "INTEGER", "$accept", "input", "program", "class", "class_body",
  "feature", "formal_list", "formal_list_non_empty", "formal", "args",
  "args_non_empty", "stmt_list", "var_declaration", "let_list", "case",
  "case_list", "expr", null
  };

  /* YYRHS -- A `-1'-separated list of the rules' RHS.  */
  private static final byte yyrhs_[] =
  {
        50,     0,    -1,    51,    -1,    52,     8,    -1,    51,    52,
       8,    -1,    13,    44,     5,    53,     6,    -1,    13,    44,
      19,    44,     5,    53,     6,    -1,    54,     8,    -1,    53,
      54,     8,    -1,    45,     3,    55,     4,     7,    44,     5,
      65,     6,    -1,    61,    -1,    -1,    56,    -1,    57,    -1,
      56,    10,    57,    -1,    45,     7,    44,    -1,    -1,    59,
      -1,    65,    -1,    59,    10,    65,    -1,    65,     8,    -1,
      60,    65,     8,    -1,    45,     7,    44,    -1,    45,     7,
      44,    41,    65,    -1,    61,    -1,    62,    10,    61,    -1,
      45,     7,    44,    42,    65,     8,    -1,    63,    -1,    64,
      63,    -1,    45,    41,    65,    -1,    65,    32,    44,     9,
      45,     3,    58,     4,    -1,    65,     9,    45,     3,    58,
       4,    -1,    45,     3,    58,     4,    -1,    17,    65,    24,
      65,    14,    65,    16,    -1,    25,    65,    22,    65,    23,
      -1,     5,    60,     6,    -1,    21,    62,    18,    65,    -1,
      26,    65,    29,    64,    27,    -1,    28,    44,    -1,    20,
      65,    -1,    65,    36,    65,    -1,    65,    37,    65,    -1,
      65,    34,    65,    -1,    65,    35,    65,    -1,    33,    65,
      -1,    65,    39,    65,    -1,    65,    38,    65,    -1,    65,
      40,    65,    -1,    30,    65,    -1,     3,    65,     4,    -1,
      45,    -1,    48,    -1,    46,    -1,    31,    -1,    15,    -1
  };

  /* YYPRHS[YYN] -- Index of the first RHS symbol of rule number YYN in
     YYRHS.  */
  private static final short yyprhs_[] =
  {
         0,     0,     3,     5,     8,    12,    18,    26,    29,    33,
      43,    45,    46,    48,    50,    54,    58,    59,    61,    63,
      67,    70,    74,    78,    84,    86,    90,    97,    99,   102,
     106,   115,   122,   127,   135,   141,   145,   150,   156,   159,
     162,   166,   170,   174,   178,   181,   185,   189,   193,   196,
     200,   202,   204,   206,   208
  };

  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
  private static final short yyrline_[] =
  {
         0,    99,    99,   102,   103,   107,   112,   117,   120,   126,
     130,   137,   138,   142,   145,   150,   155,   156,   160,   161,
     165,   167,   174,   176,   181,   183,   191,   197,   200,   205,
     208,   211,   214,   217,   220,   223,   226,   229,   232,   233,
     234,   235,   236,   237,   238,   239,   240,   241,   242,   243,
     244,   245,   246,   247,   248
  };

  // Report on the debug stream that the rule yyrule is going to be reduced.
  private void yy_reduce_print (int yyrule, YYStack yystack)
  {
    if (yydebug == 0)
      return;

    int yylno = yyrline_[yyrule];
    int yynrhs = yyr2_[yyrule];
    /* Print the symbols being reduced, and their result.  */
    yycdebug ("Reducing stack by rule " + (yyrule - 1)
	      + " (line " + yylno + "), ");

    /* The symbols being reduced.  */
    for (int yyi = 0; yyi < yynrhs; yyi++)
      yy_symbol_print ("   $" + (yyi + 1) + " =",
		       yyrhs_[yyprhs_[yyrule] + yyi],
		       ((yystack.valueAt (yynrhs-(yyi + 1)))),
		       yystack.locationAt (yynrhs-(yyi + 1)));
  }

  /* YYTRANSLATE(YYLEX) -- Bison symbol number corresponding to YYLEX.  */
  private static final byte yytranslate_table_[] =
  {
         0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22,    23,    24,
      25,    26,    27,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    38,    39,    40,    41,    42,    43,    44,
      45,    46,    47,    48
  };

  private static final byte yytranslate_ (int t)
  {
    if (t >= 0 && t <= yyuser_token_number_max_)
      return yytranslate_table_[t];
    else
      return yyundef_token_;
  }

  private static final int yylast_ = 264;
  private static final int yynnts_ = 17;
  private static final int yyempty_ = -2;
  private static final int yyfinal_ = 6;
  private static final int yyterror_ = 1;
  private static final int yyerrcode_ = 256;
  private static final int yyntokens_ = 49;

  private static final int yyuser_token_number_max_ = 303;
  private static final int yyundef_token_ = 2;

/* User implementation code.  */
/* Unqualified %code blocks.  */

/* Line 927 of lalr1.java  */
/* Line 12 of "cool.y"  */

    private Program _root;
    public Program getRoot() {
        return _root;
    }

    public static String getTokenName(int t) {
        return yytname_[t-255];
    }




/* Line 927 of lalr1.java  */
/* Line 1701 of "src/coolc/parser/Parser.java"  */

}


/* Line 931 of lalr1.java  */
/* Line 252 of "cool.y"  */





