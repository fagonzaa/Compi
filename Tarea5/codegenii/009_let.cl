class Program {

	iostream : IO <- new IO;


	main() : Object {
		let i : Int <- 0 in
			while i < 20 loop { 
				freefall(i); 
				i <- i + 1;
			} pool
	};

	freefall(time: Int) : IO {
		let accel : Int <- 98,
			speed : Int <- accel*time,
			distance : Int <- speed*time/2 in
				iostream.out_int(distance).out_string("\n")
	};
};