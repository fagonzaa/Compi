class Main inherits IO {
    
    result : Int;
    a : Int;

    main() : Object {{
        out_int(3 / 5); -- 0
        out_string("\n");
        
        a <- 0 - 392;
        out_int(323042 / a); -- -824
        out_string("\n");
        
        out_int(a / 15); -- -26
        out_string("\n");
        
        a <- 224;
        result <- a / 10;
        out_int(result); -- 24
        out_string("\n");

        a <- a / a; -- 1
        out_int(a);
    }};
};