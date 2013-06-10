class Main inherits IO {
    
    main() : Object {{
        out_int(cosa_compleja(9, 5, 6, 7)); -- 34
        out_string("\n");
        out_int(cosa_compleja(1, 6, 7, 8)); -- 1
        out_string("\n");
        out_int(op_simple(2904)); -- 2905
        out_string("\n");
        printIsBig(13);
        printIsBig(2344);
        printIsBig(1000);

    }};

    cosa_compleja(a : Int, b : Int, c : Int, d : Int) : Int {
        if a < b + c then
            a
        else 
            d * 4 + c
        fi
    };

    op_simple(n : Int) : Int {
        n + 1
    };

    isBig(n : Int) : Bool {
        1000 < n
    };

    printIsBig(n : Int) : Object {{
        out_int(n);
        if isBig(n) then 
            out_string(" is big\n")
        else
            out_string(" is small\n")
        fi;
    }};
};