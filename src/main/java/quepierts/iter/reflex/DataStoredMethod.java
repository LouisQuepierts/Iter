package quepierts.iter.reflex;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DataStoredMethod {
    final Method method;
    final Object[] data;

    public DataStoredMethod(Method method, Object... data) {
        this.method = method;
        this.data = data;
    }

    public void invoke() throws InvocationTargetException, IllegalAccessException {
        method.trySetAccessible();
        method.invoke(null);
    }
}
