class Main inherits IO {
    
    result : Bool;
    num1 : Int;
    num2 : Int;

    main() : Object {{        
        out_string( if 1 < 5  then "1 < 5\n" else "impossiburuuu\n" fi);
        out_string( if( 3 <= 5 ) then "3 <= 5\n" else "impossiburuuu too\n" fi);
        num1 <- 1;
        num2 <- 1;
        result <- num1 < num2;
        out_string( if result then "1 < 1\n" else "yay! it works\n" fi);
        result <- num1 <= num2;
        out_string( if result then "1 <= 1\n" else "this is getting even more ridiculous\n" fi);

        out_string(if 34 < 5 then "whut?\n" else "!34 < 5\n" fi);
        out_string(if 53 <= num1 then "so, this is the end"  else "whoopitiwhoo!" fi);

        }};
};