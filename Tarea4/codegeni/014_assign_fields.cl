class Main inherits IO {
    
    entero : Int;
    booleano : Bool;

    main() : Object {{
        
        cadena <- "I'm a big string!!";
        entero <- 80085;
        booleano <- true;

        out_string(cadena);
        out_string("\n");
        out_int(entero);
        out_string("\n");

        if booleano then
            out_string("true")
        else
            out_string("false")
        fi;


    }};
};