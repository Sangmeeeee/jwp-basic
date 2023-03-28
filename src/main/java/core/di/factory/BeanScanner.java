package core.di.factory;

import core.annotation.Controller;
import core.annotation.Repository;
import core.annotation.Service;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

public class BeanScanner {
    private static final Logger log = LoggerFactory.getLogger(BeanScanner.class);
    private Reflections reflections;

    public BeanScanner(Object... basePackage){
        reflections = new Reflections(basePackage);
    }

    public Set<Class<?>> scan(){
        return getTypesAnnotatedWith(Controller.class, Service.class, Repository.class);
    }

    private Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation>... annotations){
        Set<Class<?>> preInstantiateBeans = new HashSet<>();
        for(Class<? extends Annotation> annotation : annotations){
            preInstantiateBeans.addAll(reflections.getTypesAnnotatedWith(annotation));
        }
        return preInstantiateBeans;
    }
}
