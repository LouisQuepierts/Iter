package quepierts.iter.reflex;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClassFinder {
    private static final List<Class<?>> classes = new ArrayList<>();
    private static final String SYSTEM_PATH = ClassLoader.getSystemResource("").getPath();

    private static boolean searched = false;

    public static List<Class<?>> getLoadedClasses() {
        return new ArrayList<>(classes);
    }

    public static void searchFromRoot(Class<?> c) {
        if (!searched) {
            searched = true;
            String path = SYSTEM_PATH + c.getPackage().getName().replace(".", "\\");

            try {
                searchClasses(path);
                executeMethod();
            } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static void searchClasses(String path) throws ClassNotFoundException {
        File root = new File(path);
        File[] files = root.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                searchClasses(file.getPath());
            } else {
                String classPath = file.getPath()
                        .substring(SYSTEM_PATH.length() - 1)
                        .replace("\\", ".")
                        .replace(".class", "");

                Class<?> gotClass = Class.forName(classPath);

                classes.add(gotClass);
            }
        }
    }

    private static void executeMethod() throws InvocationTargetException, IllegalAccessException {
        Method[] methods;
        Execute annotation;
        List<DataStoredMethod> executableMethods = new ArrayList<>();
        for (Class<?> aClass : classes) {
            methods = aClass.getDeclaredMethods();
            for (Method method : methods) {
                if (Modifier.isStatic(method.getModifiers())) {
                    annotation = method.getAnnotation(Execute.class);

                    if (annotation != null) {
                        if (method.getParameterCount() == 0) {
                            executableMethods.add(new DataStoredMethod(method, annotation.priority()));
                        }
                    }
                }
            }
        }

        executableMethods.sort(Comparator.comparingInt(o -> (int) o.data[0]));
        for (DataStoredMethod executableMethod : executableMethods) {
            executableMethod.invoke();
        }
    }

}
