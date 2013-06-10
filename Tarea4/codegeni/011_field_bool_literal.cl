class Main inherits IO {
    
    truth : Bool <- true;

    main() : Object {{
        out_string("The truth is ");
        if truth then
            out_string("True")
        else
            out_string("False")
        fi;
    }};
};