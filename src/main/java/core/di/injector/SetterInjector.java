package core.di.injector;

import core.di.factory.BeanFactory;
import core.di.factory.BeanFactoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class SetterInjector extends AbstractInjector{
    private static final Logger log = LoggerFactory.getLogger(SetterInjector.class);

    public SetterInjector(BeanFactory beanFactory){
        super(beanFactory);
    }

    @Override
    Set<?> getInjectedBeans(Class<?> clazz) {
        return BeanFactoryUtils.getInjectedMethods(clazz);
    }

    @Override
    void inject(Object injectedBean, Object bean, BeanFactory beanFactory) {
        Method method = (Method) injectedBean;
        try{
            method.invoke(beanFactory.getBean(method.getDeclaringClass()), bean);
        }catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
            log.error(e.getMessage());
        }
    }

    @Override
    Class<?> getBeanClass(Object injectedBean) {
        Method method = (Method) injectedBean;
        log.debug("invoke method : {}", method);
        Class<?>[] paramTypes = method.getParameterTypes();
        if(paramTypes.length != 1)
            throw new IllegalStateException("DI method parameter need to have only one parameter");
        return paramTypes[0];
    }
}
