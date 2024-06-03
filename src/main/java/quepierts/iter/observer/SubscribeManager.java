package quepierts.iter.observer;

import quepierts.iter.reflex.ClassFinder;
import quepierts.iter.reflex.Execute;
import quepierts.iter.reflex.ExecuteMethod;
import quepierts.iter.util.IterLogManager;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

public class SubscribeManager {
    private final static Map<Class<?>, List<ExecuteMethod>> subscribe = new HashMap<>();
    private final static Map<Class<?>, List<ISubscribedMethod>> methods = new HashMap<>();

    private final static Set<Class<?>> eventClasses = new HashSet<>();

    private static final List<Object> subscribeCache = new ArrayList<>();
    private static boolean init = false;

    public static <T extends IEvent> void subscribe(ISubscribedMethod<T> method, Class<T> type) {
        List<ISubscribedMethod> methodList = methods.get(type);
        if (methodList == null) {
            methodList = new ArrayList<>();
            methods.put(type, methodList);
        }

        methodList.add(method);
    }

    public static void subscribe(Object object) {
        if (init) {
            subscribeObject(object);
        } else {
            subscribeCache.add(object);
        }
    }

    public static void publish(IEvent iEvent) {
        try {
            Class<? extends IEvent> iEventClass = iEvent.getClass();

            List<ExecuteMethod> executeMethods = subscribe.get(iEventClass);

            if (executeMethods != null) {
                for (ExecuteMethod method : executeMethods) {
                    method.invoke(iEvent);
                }
            }

            List<ISubscribedMethod> methodList = methods.get(iEventClass);
            if (methodList != null) {
                for (ISubscribedMethod method : methodList) {
                    method.publish(iEvent);
                }
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Execute
    private static void init() {
        IterLogManager.getLogger().info("[Iter Initialization] Auto Subscribe Methods");

        List<Class<?>> loadedClasses = ClassFinder.getLoadedClasses();
        List<Method> subscribedMethods = new ArrayList<>();

        Method[] methods;
        for (Class<?> loadedClass : loadedClasses) {
            Annotation annotation = loadedClass.getAnnotation(Event.Register.class);

            if (annotation != null) {
                if (IEvent.class.isAssignableFrom(loadedClass)) {
                    subscribe.put(loadedClass, new ArrayList<>());
                    eventClasses.add(loadedClass);
                }
            } else {
                methods = loadedClass.getDeclaredMethods();
                for (Method method : methods) {
                    if (Modifier.isStatic(method.getModifiers())) {
                        annotation = method.getAnnotation(Event.Subscribe.class);

                        if (annotation != null) {
                            subscribedMethods.add(method);

                        }
                    }
                }
            }
        }

        for (Method method : subscribedMethods) {
            trySubscribeMethod(method, null);
        }

        for (Object object : subscribeCache) {
            subscribeObject(object);
        }

        subscribeCache.clear();

        init = true;
    }

    private static void subscribeObject(Object object) {
        Class<?> clazz = object.getClass();

        Method[] methods = clazz.getDeclaredMethods();
        Annotation annotation;

        for (Method method : methods) {
            if (!Modifier.isStatic(method.getModifiers())) {
                annotation = method.getAnnotation(Event.Subscribe.class);

                if (annotation != null) {
                    trySubscribeMethod(method, object);
                }
            }
        }
    }

    private static void trySubscribeMethod(Method method, Object object) {
        if (method.getParameterCount() == 1) {
            Class<?> parameterType = method.getParameterTypes()[0];
            if (eventClasses.contains(parameterType)) {
                subscribe.get(parameterType).add(new ExecuteMethod(object, method));
            }
        }
    }

}
