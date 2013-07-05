class Main inherits IO {
    
    result : Int;
    a : Int;

    main() : Object {{
        out_int(3 * 5); -- 15
        out_string("\n");
        
        a <- 392;
        out_int(32 * a); -- 12544
        out_string("\n");
        
        out_int(a * 15); -- 5880
        out_string("\n");
        
        a <- 0 - 24;
        result <- a * 100;
        out_int(result); -- -2400
        out_string("\n");

        a <- a * a; -- 576
        out_int(a);
    }};
};