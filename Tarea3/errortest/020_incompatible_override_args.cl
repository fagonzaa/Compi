
class Number inherits Decimal {

    parse(number : String, base : Int) : Int {
        new Int
    };
};

class Decimal {

    parse(number : String) : Int {
        new Int
    };

    main() : SELF_TYPE {{    
        parse("17");
        self;
    }};
};