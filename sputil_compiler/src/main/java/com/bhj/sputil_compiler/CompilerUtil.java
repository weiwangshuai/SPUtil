package com.bhj.sputil_compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

class CompilerUtil {
    static TypeSpec.Builder compilerTypeSpec(Element elem) {
        return TypeSpec.classBuilder(String.format("%sStorage", elem.getSimpleName().toString()))
                .addModifiers(Modifier.PUBLIC);
    }


    static MethodSpec compilerGetMethodSpec(Element elem, String className, boolean needDefaultValue, TypeName aClass) {
        MethodSpec.Builder methodSpec = MethodSpec.methodBuilder(String.format("get%s", captureName(elem.getSimpleName().toString())));
        if (needDefaultValue) {
            methodSpec.addParameter(ClassName.get(elem.asType()), "defaultValue");
        }

        methodSpec.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ClassName.get(elem.asType()));
        String format;
        Object[] args;
        if (needDefaultValue) {
            format = "return ($T)new $T().get($S,$T.class,defaultValue)";
        } else {
            format = "return ($T)new $T().get($S,$T.class,null)";
        }
        args = new Object[4];
        args[0] = ClassName.get(elem.asType());
        args[1] = aClass;
        args[2] = String.format("%s_%s", className, elem.getSimpleName().toString());
        args[3] = args[0];
        return methodSpec.addStatement(format, args).build();
    }

    static MethodSpec compilerPutMethodSpec(Element elem, String className, TypeName aClass) {
        String format;
        Object[] args;
        format = "new $T().put($S,$L,$T.class)";
        args = new Object[4];
        args[0] = aClass;
        args[1] = String.format("%s_%s", className, elem.getSimpleName().toString());
        args[2] = elem.getSimpleName().toString();
        args[3] = ClassName.get(elem.asType());
        return MethodSpec.methodBuilder(String.format("put%s", captureName(elem.getSimpleName().toString())))
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(ClassName.get(elem.asType()), elem.getSimpleName().toString())
                .addStatement(format, args)
                .build();
    }

    static void write(TypeSpec typeSpec, Filer filer) {
        JavaFile javaFile = JavaFile.builder("com.bhj.sp", typeSpec)
                .build();
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String captureName(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }
}
