package core.di.factory;

import com.google.common.collect.Maps;
import core.annotation.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.*;

public class BeanFactory {
    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

    private Set<Class<?>> preInstanticateBeans;

    private Map<Class<?>, Object> beans = Maps.newHashMap();

    public BeanFactory(Set<Class<?>> preInstanticateBeans) {
        this.preInstanticateBeans = preInstanticateBeans;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }

    public void initialize() {
        for(Class<?> clazz : preInstanticateBeans){
            if(getBean(clazz) == null)
                instantiateClass(clazz);
        }
    }

    public Map<Class<?>, Object> getControllers(){
        Map<Class<?>, Object> controllers = new HashMap<>();
        for(Class<?> clazz : preInstanticateBeans){
            Annotation annotation = clazz.getAnnotation(Controller.class);
            if(annotation != null)
                controllers.put(clazz, beans.get(clazz));
        }
        return controllers;
    }

    private Object instantiateClass(Class<?> clazz){
        Object bean = beans.get(clazz);
        if(bean != null)
            return bean;

        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(clazz);

        if(injectedConstructor == null){
            bean = BeanUtils.instantiate(clazz);
            beans.put(clazz, bean);
            return bean;
        }
        logger.debug("Constructor : {}", injectedConstructor);
        bean = instantiateConstructor(injectedConstructor);
        beans.put(clazz, bean);
        return bean;
    }

    private Object instantiateConstructor(Constructor<?> constructor){
        Class<?>[] pTypes = constructor.getParameterTypes();
        List<Object> args = new ArrayList<>();
        for(Class<?> clazz : pTypes){
            Class<?> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, preInstanticateBeans);
            if(!preInstanticateBeans.contains(concreteClazz))
                throw new IllegalStateException(clazz + "is not bean");
            Object bean = beans.get(concreteClazz);
            if(bean == null)
                bean = instantiateClass(concreteClazz);
            args.add(bean);
        }
        return BeanUtils.instantiateClass(constructor, args.toArray());
    }
}
