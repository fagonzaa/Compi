class Program {
    
    iostream : IO <- new IO;

    main() : Object {
        iostream.out_string("Sabias que puedes encadenar mas de ")
                .out_int(1500)
                .out_string(" llamadas a las funciones\n")
                .out_string("out_string y out_int de la clase IO\n")
                .out_string("ya que las ").out_int(2).out_string(" devuelven el objeto sobre el cual se llamaron?")
    };

};