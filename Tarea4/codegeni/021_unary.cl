class Main inherits IO {
    
    main() : Object {{
        out_int(~ 0); -- -1
        out_int(if not 1 < 0 then 4 else 5 fi); -- 4
        out_int(~ (0 - 46)); -- 45        
    }};
};