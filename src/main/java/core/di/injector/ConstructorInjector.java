package core.di.injector;

import core.di.factory.BeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class ConstructorInjector extends AbstractInjector{
    private static final Logger log = LoggerFactory.getLogger(ConstructorInjector.class);

    public ConstructorInjector(BeanFactory beanFactory){
        super(beanFactory);
    }

    @Override
    Set<?> getInjectedBeans(Class<?> clazz) {
        return new HashSet<>();
    }

    @Override
    void inject(Object injectedBean, Object instantiateClass, BeanFactory beanFactory) {

    }

    @Override
    Class<?> getBeanClass(Object injectedBean) {
        return null;
    }
}
