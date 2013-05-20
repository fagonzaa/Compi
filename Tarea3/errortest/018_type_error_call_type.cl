class SubMain {
    shoot(angle : Int) : SELF_TYPE {
        self
    };
};

class Main {
    main() : SELF_TYPE {
        let submarine : SubMain <- new SubMain in
            submarine@Main.main()
    };
};