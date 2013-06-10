class Main inherits IO {
    
    main() : Object {{
        out_string("I'm a ");
        out_string(
            if true then
                "Marvel"
            else
                "DC"
            fi
        );
        out_string("\nSince year ");
        out_int(
            if false then
                1978
            else
                3915
            fi
        );
    }};
};