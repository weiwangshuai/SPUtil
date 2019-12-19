package com.bhj.sputil_compiler;

import com.bhj.sputil_annation.Storage;
import com.bhj.sputil_annation.KeyValueStorageModule;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
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
        TypeName className = null;
        for (Element element : roundEnvironment.getElementsAnnotatedWith(KeyValueStorageModule.class)) {
            if (element.getKind() == ElementKind.CLASS) {
                className = ClassName.get(element.asType());
            }
        }
        for (Element elem : roundEnvironment.getElementsAnnotatedWith(Storage.class)) {
            if (elem.getKind() == ElementKind.CLASS) {
                TypeSpec.Builder typeSpec = CompilerUtil.compilerTypeSpec(elem);
                for (Element enclosedElement : elem.getEnclosedElements()) {
                    if (enclosedElement.getKind() == ElementKind.FIELD) {
                        MethodSpec getMethodSpec = CompilerUtil.compilerGetMethodSpec(enclosedElement, elem.asType().toString(), false, className);
                        MethodSpec putMethodSpec = CompilerUtil.compilerPutMethodSpec(enclosedElement, elem.asType().toString(), className);
                        if (getMethodSpec != null) {
                            typeSpec.addMethod(getMethodSpec);
                            typeSpec.addMethod(CompilerUtil.compilerGetMethodSpec(enclosedElement, elem.asType().toString(), true, className));
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
        annotations.add(Storage.class.getCanonicalName());
        annotations.add(KeyValueStorageModule.class.getCanonicalName());
        return annotations;
    }
}
