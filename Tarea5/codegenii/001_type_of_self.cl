class Program {

	iostream : IO <- new IO;

	main() : SELF_TYPE {
		iostream.out_string(self.type_name())
	};
};