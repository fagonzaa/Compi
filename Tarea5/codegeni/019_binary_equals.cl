class Main inherits IO {
    
    chainmail : String;
    counter : Int;
    blu : Bool;

    main() : Object {{
        out_string( if "azul" = "azul" then "ok\n" else "not ok\n" fi); -- ok
        out_string( if "azul" = "rojo" then "ok\n" else "not ok\n" fi); -- not ok
        out_string( if "azul" = "a".concat("zul") then "ok\n" else "not ok\n" fi); -- ok
        out_string( if "asul"  = "a".concat("zul") then "ok\n" else "not ok\n" fi); -- not ok
        chainmail <- "codigo rojo";
        out_string(if chainmail = "el codigo rosso".substr(3, 9).concat("jo") 
            then "ok\n" else "not ok\n" fi); -- ok
        out_string(if "el codigo rosso" = chainmail
            then "ok\n" else "not ok\n" fi); -- not ok

        blu <- true;
        out_string( if blu = true then "ok\n" else "not ok\n" fi); -- ok
        out_string( if blu = false then "ok\n" else "not ok\n" fi); -- not ok


        counter <- 114;

        out_string( if 115 = 114 then "ok\n" else "not ok\n" fi); -- ok
        out_string( if 0 = 1 then "ok\n" else "not ok\n" fi); -- not ok
        out_string( if 114 = counter then "ok\n" else "not ok\n" fi); -- ok
        out_string( if counter = 33 then "ok\n" else "not ok\n" fi); -- not ok

    }};
};