package quepierts.iter.reflex;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ExecuteMethod {
    private final Method method;
    private final Object object;

    public ExecuteMethod(Object object, Method method) {
        this.method = method;
        this.object = object;
    }

    public void invoke(Object... args) throws InvocationTargetException, IllegalAccessException {
        method.setAccessible(true);
        method.invoke(object, args);
    }
}
