class Program {

	iostream : IO <- new IO;

	main() : Object {{
		iostream.out_string("Arbol ").out_string(new Arbol.getFruto()).out_string("\n");
		iostream.out_string("Manzano ").out_string(new Manzano.getFruto()).out_string("\n");
		iostream.out_string("GrannySmith ").out_string(new GrannySmith.getFruto()).out_string("\n");
		iostream.out_string("ManzanoRojo ").out_string(new ManzanoRojo.getFruto()).out_string("\n");
		iostream.out_string("Sequoia ").out_string(new Sequoia.getFruto()).out_string("\n");
		iostream.out_string("Nogal ").out_string(new Nogal.getFruto()).out_string("\n");
	}};
};

class Arbol {
	getFruto() : String { "I have not idea what I am doing" };	
};

class Nogal inherits Arbol {
	getFruto() : String { "Nuez" };		
};

class Sequoia inherits Arbol {
	stub : Object;	
};

class Manzano inherits Arbol {
	getFruto() : String { "Manzana" };	
};

class ManzanoRojo inherits Manzano {
	getFruto() : String { "Manzana roja" };	
};

class GrannySmith inherits Manzano {
	getFruto() : String { "Manzana verde" };		
};