class Program {

	iostream : IO <- new IO;

	main() : Object {{
		print("Hola, mundo");
		print(1145);
		print(true);
		print(self);
		print(iostream);
		print(new Object);
		print(new Int);
	}};


	print(o : Object) : Object {{
		iostream.out_string(o.type_name()).out_string(": ");
		case o of 
			i : Int => iostream.out_int(i);
			s : String => iostream.out_string(s);
			b : Bool => iostream.out_string(if b then "true" else "false" fi);
			o : Object => iostream.out_string("[Unprintable]");
			io : IO => iostream.out_string("Trying to print a IO? WTF? You want to create a singularity?");
		esac;
		iostream.out_string("\n");
	}};
};

