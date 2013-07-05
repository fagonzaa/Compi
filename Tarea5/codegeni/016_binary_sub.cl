class Main inherits IO {
    
    result : Int;
    a : Int;

    main() : Object {{
        out_int(3 - 5); -- -2
        out_string("\n");
        
        a <- 392;
        out_int(32 - a); -- -360
        out_string("\n");
        
        out_int(a - 15); -- 377
        out_string("\n");
        
        a <- 224;
        result <- a - 100;
        out_int(result); -- 124
        out_string("\n");

        a <- a - a; -- 0
        out_int(a);
    }};
};