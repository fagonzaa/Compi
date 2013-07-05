class Program {

	iostream : IO <- new IO;
	expression : Expr;

	main() : Object {{
		expression	<- int(15).add(30).mult(2).neg().sub(10).mult(5); -- (-((15 + 30)*2) - 10)*5
		expression.print(iostream);
		iostream.out_string(" = ").out_int(expression.compute()); -- = 500
	}};

	int(i : Int) : IntExpr {
		new IntExpr.init(i)
	};
};


class Expr {

	add(e : Int) : SumOp {
		new SumOp.init(self, new IntExpr.init(e))
	};

	sub(e : Int) : SubOp {
		new SubOp.init(self, new IntExpr.init(e))
	};

	mult(e : Int) : MultOp {
		new MultOp.init(self, new IntExpr.init(e))
	};

	neg() : NegOp {
		new NegOp.init(self)
	};

	print(iostream : IO) : IO {
		case self of 
			i : IntExpr => iostream.out_int(i.getValue());
			sum : SumOp => sum.print(iostream);
			sub : SubOp => sub.print(iostream);
			mult : MultOp => mult.print(iostream);
			neg : NegOp => { 
				iostream.out_string(" - ");
				neg.print(iostream);
			};
		esac
	};

	compute() : Int {
		case self of 
			i : IntExpr => i.getValue();
			sum : SumOp => sum.getLeft().compute() + sum.getRight().compute();	
			sub : SubOp => sub.getLeft().compute() - sub.getRight().compute();
			mult : MultOp => mult.getLeft().compute() * mult.getRight().compute();
			neg : NegOp => 0 - neg.getValue().compute();
		esac
	};
};

class BinaryOp inherits Expr {

	left : Expr;
	getLeft() : Expr { left };

	right : Expr;
	getRight() : Expr { right };

	print(iostream : IO) : IO {{
		iostream.out_string("(");
		left.print(iostream);
		iostream.out_string(
			case self of 
				sum : SumOp => " + ";
				sub : SubOp => " - ";
				mult : MultOp => " * ";
				o : Object => " (undefined) ";
			esac
		);
		right.print(iostream);
		iostream.out_string(")");
	}};

	init(l : Expr, r : Expr ) : SELF_TYPE {{
		left <- l;
		right <- r;
		self;
	}};

};

class SumOp inherits BinaryOp {
	stub : Object;
};

class SubOp inherits BinaryOp {
	stub : Object;
};

class MultOp inherits BinaryOp { 
	stub : Object;
};

class NegOp inherits Expr {
	value : Expr;
	getValue() : Expr { value };

	init(v : Expr) : SELF_TYPE {{
		value <- v;
		self;
	}};
};


class IntExpr inherits Expr {
	value : Int;
	getValue() : Int { value };

	init(v : Int) : SELF_TYPE {{
		value <- v;
		self;
	}};
};