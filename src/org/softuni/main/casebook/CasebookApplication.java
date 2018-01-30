package casebook;

import casebook.handlers.fixed.ErrorHandler;
import casebook.utilities.HandlerLoader;
import javache.Application;
import javache.http.HttpContext;
import javache.http.HttpResponse;
import javache.http.HttpSession;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;

public class CasebookApplication implements Application {
    private HttpSession session;

    private HashMap<String, HashMap<String, Function<HttpContext, byte[]>>> routesTable;

    public CasebookApplication() {
        this.initializeRoutes();
    }

    private void initializeRoutes() {
        this.routesTable = new HashMap<>();

        HandlerLoader handlerLoader = new HandlerLoader();
        this.loadActionsForMethod("GET", handlerLoader);
        this.loadActionsForMethod("POST", handlerLoader);
    }

    private void loadActionsForMethod(String method, HandlerLoader handlerLoader){
        Map<String, Method> actions = handlerLoader.getRouteActionsByRequestMethod(method);

        for (Map.Entry<String, Method> stringMethodEntry : actions.entrySet()) {
            try {
                Object handlerObject = stringMethodEntry
                        .getValue()
                        .getDeclaringClass()
                        .getConstructor()
                        .newInstance();

                this.routesTable.putIfAbsent(method, new HashMap<>());
                this.routesTable.get(method)
                        .put(stringMethodEntry.getKey(),
                        (HttpContext httpContext) -> {
                            try {
                                return ((HttpResponse) stringMethodEntry
                                        .getValue()
                                        .invoke(handlerObject,
                                                httpContext.getHttpRequest(),
                                                httpContext.getHttpResponse()))
                                        .getBytes();
                            } catch (Exception e) {
                                e.printStackTrace();
                                return null;
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public Map<String, HashMap<String, Function<HttpContext, byte[]>>> getRoutes() {
        return Collections.unmodifiableMap(this.routesTable);
    }

    @Override
    public byte[] handleRequest(HttpContext httpContext) {
        String requestMethod = httpContext.getHttpRequest().getMethod();
        String requestUrl = httpContext.getHttpRequest().getRequestUrl();

        if(!this.getRoutes().get(requestMethod).containsKey(requestUrl)) {
            return new ErrorHandler().notFound(httpContext).getBytes();
        }

        return this.getRoutes().get(requestMethod).get(requestUrl).apply(httpContext);
    }

    @Override
    public HttpSession getSession() {
        return this.session;
    }

    @Override
    public void setSession(HttpSession session) {
        this.session = session;
    }

}
