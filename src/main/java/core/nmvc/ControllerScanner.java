package core.nmvc;

import core.annotation.Controller;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ControllerScanner {
    private static final Logger log = LoggerFactory.getLogger(ControllerScanner.class);
    private Map<Class<?>, Object> controllers = new HashMap<>();
    private Reflections reflections;

    public ControllerScanner(Object... params){
        this.reflections = new Reflections(params);
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Controller.class);
        for(Class clazz : annotated){
            log.debug("Create instance of {}", clazz.getName());
            try {
                controllers.put(clazz, clazz.newInstance());
            } catch (InstantiationException e) {
                log.error(e.getMessage());
            } catch (IllegalAccessException e) {
                log.error(e.getMessage());
            }
        }
    }

    public Map<Class<?>, Object> getControllers(){
        return this.controllers;
    }
}
