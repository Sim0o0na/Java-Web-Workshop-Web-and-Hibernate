package casebook.utilities;

import casebook.annotations.Get;
import casebook.annotations.Post;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class HandlerLoader {
    private final static String DYNAMIC_HANDLERS_FULL_PATH = System.getProperty("user.dir") + "\\src\\org\\softuni\\main\\casebook\\handlers\\dynamic\\";
    private final static String DYNAMIC_HANDLERS_PACKAGE =  "casebook.handlers.dynamic.";

    private HashMap<String, HashMap<String, Method>> routeActions;

    public HandlerLoader() {
        this.loadRoutes();
    }

    public void loadRoutes(){
        this.routeActions = new HashMap<>();
        this.routeActions.put("GET", new HashMap<>());
        this.routeActions.put("POST", new HashMap<>());

        File directory = new File(DYNAMIC_HANDLERS_FULL_PATH);
        List<Class<?>> foundClasses = null;

        foundClasses = Arrays.asList(directory.listFiles())
                .stream()
                .map(x -> {
                    try {
                        String fullPath = String.valueOf(x);
                        String className = fullPath
                                .substring(fullPath.lastIndexOf("\\") + 1)
                                .replace(".java", "");
                        return Class.forName(DYNAMIC_HANDLERS_PACKAGE + className);
                    } catch (ClassNotFoundException e) {
                        return null;
                    }
                })
                .collect(Collectors.toList());

        for (Class<?> foundClass : foundClasses) {
            Method[] handlerMethods = foundClass.getDeclaredMethods();
            for (Method handlerMethod : handlerMethods) {
                handlerMethod.setAccessible(true);
                Annotation[] annotations = handlerMethod.getDeclaredAnnotations();
                for (Annotation annotation : annotations) {
                    String annotationClassName = annotation.annotationType().getSimpleName();
                    if(annotationClassName.equals("Get")){
                        this.routeActions.get("GET").put(((Get) annotation).route(), handlerMethod);
                    } else if (annotationClassName.equals("Post")){
                        this.routeActions.get("POST").put(((Post)annotation).route(), handlerMethod);
                    }
                }
            }
        }
    }

    public Map<String, Method> getRouteActionsByRequestMethod(String method) {
        return Collections.unmodifiableMap(this.routeActions.get(method));
    }


}
