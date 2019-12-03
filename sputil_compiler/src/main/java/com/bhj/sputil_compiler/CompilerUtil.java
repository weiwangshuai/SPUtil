package com.bhj.sputil_compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

class CompilerUtil {
    static TypeSpec.Builder compilerTypeSpec(Element elem) {
        return TypeSpec.classBuilder(String.format("SP%s", elem.getSimpleName().toString()))
                .addModifiers(Modifier.PUBLIC);
    }


    static MethodSpec compilerGetMethodSpec(Element elem, String className, boolean needDefaultValue) {
        // SPUtils.getInstance().getString("user_username")
        String methodType = getMethodType(elem);
        if (methodType == null) return null;
        ClassName aClass = ClassName.get("com.bhj.sputil_lib", "SPUtils");
        MethodSpec.Builder methodSpec = MethodSpec.methodBuilder(String.format("get%s", captureName(elem.getSimpleName().toString())));
        if (needDefaultValue) {
            methodSpec.addParameter(ClassName.get(elem.asType()), "defaultValue");
        }
        methodSpec.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ClassName.get(elem.asType()));
        if (needDefaultValue) {
            methodSpec.addStatement("return $T.getInstance().$L($S,defaultValue)",
                    aClass,
                    methodType,
                    String.format("%s_%s", className, elem.getSimpleName().toString()));
        } else {
            methodSpec.addStatement("return $T.getInstance().$L($S)",
                    aClass,
                    methodType,
                    String.format("%s_%s", className, elem.getSimpleName().toString()));
        }
        return methodSpec.build();
    }

    static MethodSpec compilerPutMethodSpec(Element elem, String className) {
        // SPUtils.getInstance().put("user_username",username)
        ClassName aClass = ClassName.get("com.bhj.sputil_lib", "SPUtils");
        return MethodSpec.methodBuilder(String.format("put%s", captureName(elem.getSimpleName().toString())))
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(ClassName.get(elem.asType()), elem.getSimpleName().toString())
                .addStatement("$T.getInstance().put($S,$L)",
                        aClass,
                        String.format("%s_%s", className, elem.getSimpleName().toString()),
                        elem.getSimpleName().toString()
                )
                .build();
    }

    private static String getMethodType(Element elem) {
        String className = ClassName.get(elem.asType()).toString();
        if (className.equals("java.lang.String")) {
            return "getString";
        } else if (className.equals("java.lang.Float")
                || className.equals("float")) {
            return "getFloat";
        } else if (className.equals("java.lang.Long")
                || className.equals("long")) {
            return "getLong";
        } else if (className.equals("java.lang.Integer")
                || className.equals("int")) {
            return "getInt";
        } else if (className.equals("java.lang.Boolean")
                || className.equals("boolean")) {
            return "getBoolean";
        }
        return null;
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
