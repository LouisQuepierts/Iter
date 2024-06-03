package quepierts.iter.observer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SubscribeMethod implements ISubscribedMethod {
    private final Method method;
    private final Object object;

    public SubscribeMethod(Method method, Object object) {
        this.method = method;
        this.object = object;
    }

    @Override
    public void publish(IEvent event) {
        method.setAccessible(true);
        try {
            method.invoke(event, object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
