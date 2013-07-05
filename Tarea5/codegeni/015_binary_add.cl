class Main inherits IO {
    
    result : Int;
    a : Int;

    main() : Object {{
        out_int(3 + 5); -- 8
        out_string("\n");
        
        a <- 392;
        out_int(32 + a); -- 424
        out_string("\n");
        
        out_int(a + 15); -- 407
        out_string("\n");
        
        a <- 224;
        result <- a + 100;
        out_int(result); -- 324
        out_string("\n");

        a <- a + a + 1; -- 448
        out_int(a);
    }};
};