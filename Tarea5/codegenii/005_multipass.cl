class Program {

	iostream : IO <- new IO;

	main() : Object {
		iostream.out_string(new Network.createConnection("WorldTree").getDescription())
	};
};

class Network {

	hostname : String <- "Archimonde";
	getHostname() : String { hostname };
	
	createConnection(endpoint : String) : Connection {
		new Connection.open(self, endpoint)
	};
};

class Connection {
	
	endpoint : String;
	local : String;

	open(net : Network, remote : String) : SELF_TYPE {{
		endpoint <- remote;
		local <- net.getHostname();
		self;
	}};


	getDescription() : String {
		"Connection [".concat(local).concat("] -> [").concat(endpoint).concat("]\n")
	};
};