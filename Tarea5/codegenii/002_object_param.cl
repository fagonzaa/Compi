class Program {

	verdura : Brocoli <- new Brocoli;

	main() : Object {
		verdura.print(new IO)
	};
};

class Brocoli {

	print(iostream : IO) : Object {
		iostream.out_string("What a terrible night to be cursed!\n")
	};
};