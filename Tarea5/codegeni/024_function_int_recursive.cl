class Main inherits IO {
    
    pow(base: Int, exp: Int) : Int  {
        if exp = 0 then
            1
        else
            base * pow(base, exp - 1)
        fi
    };


    main() : Object {{
        out_int(pow(2, 5)); -- 32
        out_string("\n");
        out_int(pow(1, 30)); -- 1
        out_string("\n");
        out_int(pow(156, 3)); -- 3796416
    }};
};