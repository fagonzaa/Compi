class Main inherits IO {
    
    locale : String <- "es-CL";

    main() : Object {
        {
            out_string("d__<".concat(">--b"));
            out_string("\nlang: ");
            out_string(locale.substr(0, 2));
            out_string("\ncountry: ");
            out_string(locale.substr(3, 2));
        }
    };
};