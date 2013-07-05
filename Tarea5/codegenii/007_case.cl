class Program {

	iostream : IO <- new IO;

	main() : Object {{
		print(new Trabajo);
		print(new Cine);
		print(iostream);
		print(new Deportes);
	}};

	print(o : Object) : Object {
		case o of
			work : Trabajo => iostream.out_string("Yo trabajo en ").out_string(work.getEmpresa()).out_string("\n");
			sport : Deportes => iostream.out_string("Me encanta practicar ").out_string(sport.getFunFunFun()).out_string("\n");
			fun : Diversion => iostream.out_string("En mis ratos libres, ").out_string(fun.getFunFunFun()).out_string("\n");
			object : Object => iostream.out_string("Soy un ").out_string(object.type_name()).out_string(" inimprimible\n");
		esac
	};

};

class Trabajo {
	getEmpresa() : String { "Agromapas" };
};

class Diversion {
	getFunFunFun() : String { "Pasatiempos" };
};

class Cine inherits Diversion {
	getFunFunFun() : String { "The Avengers" };
};

class Deportes inherits Diversion {
	getFunFunFun() : String { "Ciclismo" };
};