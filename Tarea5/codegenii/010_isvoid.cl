class Program {

    iostream : IO <- new IO;
    codes : Program;
    paella : String;

    main() : IO {{
        printBoolean(isvoid codes); -- true
        printBoolean(isvoid 138); -- false
        printBoolean(isvoid paella); -- false
        let pot : IO,
            pantruca : Program <- codes,
            chemilico : Bool <- false
                in {
                    printBoolean(isvoid chemilico); -- false
                    printBoolean(isvoid pantruca); -- true
                    printBoolean(isvoid pot); -- true
                };

    }};

    printBoolean(value : Bool) : IO {
        iostream.out_string(if value then "true" else "false" fi).out_string("\n")
    };
};
