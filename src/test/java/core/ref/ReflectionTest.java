package core.ref;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.model.Question;
import next.model.User;

import java.lang.reflect.*;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());
        for(Field field : clazz.getFields())
            logger.debug(field.getName());
        for(Field field : clazz.getDeclaredFields())
            logger.debug(field.getName());
        for(Constructor<?> constructor : clazz.getDeclaredConstructors()){
            StringBuilder sb = new StringBuilder();
            for(Parameter parameter : constructor.getParameters())
                sb.append("(" + parameter.getParameterizedType() + ")");
            logger.debug("{} : {}", constructor.getName(), sb.toString());
        }
        for(Method method : clazz.getDeclaredMethods()){
            logger.debug(method.getName());
        }

    }
    
    @Test
    public void newInstanceWithConstructorArgs() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());
        for(Constructor<?> constructor : clazz.getDeclaredConstructors()){
            User user = (User) constructor.newInstance("userId", "password", "name", "email");
        }
    }
    
    @Test
    public void privateFieldAccess() {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());
    }
}
