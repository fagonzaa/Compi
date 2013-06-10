class Main inherits IO {

    digits : String <- "0123456789";

    main() : Object  {{
        out_string(itoa(1898524, false)); -- 1898524
        out_string("\n");
        out_string(itoa(123 - 11, false)); -- 112
        out_string("\n");
        out_string(itoa(126, true)); -- 1111110
        out_string("\n");
        out_string(itoa(2*10, true)); -- 10100
    }};

    itoa(number : Int, binary : Bool ) : String {
        if binary then itoa_binary(number) else itoa_decimal(number) fi
    };

    itoa_decimal(number : Int ) : String  {
        if number < 10 then
            digits.substr(number, 1)
        else
            -- implementación de operación módulo:
            --     n mod a =  n - ((n/a)*a)
            itoa_decimal(number/10).concat(itoa_decimal(number - (number/10)*10))
        fi
    };

    itoa_binary(n : Int) : String  {
        if n < 2 then
            if n = 0 then "0" else "1" fi
        else
            itoa_binary(n/2).concat(itoa_binary(n - ((n/2)*2)))
        fi
    };

};