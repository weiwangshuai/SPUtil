package com.bhj.sputil_compiler;

import com.bhj.sputil_annation.SharedPreferences;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
public class Compiler extends AbstractProcessor {

    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        for (Element elem : roundEnvironment.getElementsAnnotatedWith(SharedPreferences.class)) {
            if (elem.getKind() == ElementKind.CLASS) {
                TypeSpec.Builder typeSpec = CompilerUtil.compilerTypeSpec(elem);
                for (Element enclosedElement : elem.getEnclosedElements()) {
                    if (enclosedElement.getKind() == ElementKind.FIELD) {
                        MethodSpec getMethodSpec = CompilerUtil.compilerGetMethodSpec(enclosedElement, elem.getSimpleName().toString());
                        MethodSpec putMethodSpec = CompilerUtil.compilerPutMethodSpec(enclosedElement, elem.getSimpleName().toString());
                        if (getMethodSpec != null) {
                            typeSpec.addMethod(getMethodSpec);
                            typeSpec.addMethod(putMethodSpec);
                        }

                    }
                }
                CompilerUtil.write(typeSpec.build(), filer);
            }
        }
        return true;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(SharedPreferences.class.getCanonicalName());
        return annotations;
    }
}
