
class Number inherits Decimal {

    parse(number : String) : String {
        new String
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