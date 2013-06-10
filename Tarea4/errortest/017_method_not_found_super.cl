class Main {

    app : SubMain <- new SubMain;

    main() : SELF_TYPE {{
        app.exit();
        app@Main.exit();
        self;
    }};
};

class SubMain inherits Main {

    exit() : Object {
        abort()
    };

};