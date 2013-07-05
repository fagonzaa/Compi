class Program {

	iostream : IO <- new IO;
	surface : Int;

	main() : SELF_TYPE {{
		surface <- 115000;
		iostream.out_string("La superficie es ");
		iostream.out_int(surface);
	}};
};