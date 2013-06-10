@var_0 = constant [13 x i8] c"Hello World!\0A"

%Object = type { i8* }
%IO = type { i8* }

%Main= type { i8* }

define i32 @main() {
    call %Main* @Main_main(%Main* null)
    ret i32 0
}

define %Main* @Main_main(%Main* %m) {
    %_tmp_1 = bitcast %Main* %m to %IO*
    call %IO @IO_out_string(%IO* %_tmp_1, i8* bitcast ([13 x i8]* @var_0 to i8*))
    ret %Main* %m
}
