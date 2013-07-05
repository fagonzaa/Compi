class Program {

    iostream : IO <- new IO;
    pc : X86 <- new X86;
    lo_mobile : ARM16 <- new ARM16;
    llvm : LLVM <- new LLVM;
    puntonet : CIL <- new CIL;


    main() : Object {{
        iostream.out_string(pc.platform()).out_string("\n"); -- Intel i686
        iostream.out_string(pc@MachineCode.platform()).out_string("\n"); -- Native
        iostream.out_string(pc@Program.platform()).out_string("\n"); -- Computer

        iostream.out_string(lo_mobile.platform()).out_string("\n"); -- ARMv6 Thumb
        iostream.out_string(lo_mobile@MachineCode.platform()).out_string("\n"); -- Native
        iostream.out_string(lo_mobile@Program.platform()).out_string("\n"); -- Computer

        iostream.out_string(llvm.platform()).out_string("\n"); -- LLVM IR
        iostream.out_string(llvm@VirtualMachine.platform()).out_string("\n"); -- Virtual
        iostream.out_string(llvm@Program.platform()).out_string("\n"); -- Computer

        iostream.out_string(puntonet.platform()).out_string("\n"); -- CIL        
        iostream.out_string(puntonet@VirtualMachine.platform()).out_string("\n"); -- Virtual
        iostream.out_string(puntonet@Program.platform()).out_string("\n"); -- Computer



    }};

    platform() : String {
        "Computer"
    };
};

class MachineCode inherits Program {
    platform() : String {
        "Native"
    };
};

class X86 inherits MachineCode {
    platform() : String {
        "Intel i686"
    };
};

class ARM16 inherits MachineCode {
    platform() : String {
        "ARMv6 Thumb"
    };
};

class VirtualMachine inherits Program {
    platform() : String {
        "Virtual"
    };
};

class LLVM inherits VirtualMachine {
    platform() : String {
        "LLVM IR"
    };
};

class CIL inherits VirtualMachine {
    platform() : String {
        "CIL"
    };
};