class Main {

    thing : Object;

    main() : Object {
        case thing of
            i : Int => 0;
            v : TV => 0;
            o : Object => self;
        esac
    };
};