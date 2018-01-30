package casebook.handlers.fixed;

import javache.http.HttpContext;
import javache.http.HttpResponse;
import javache.http.HttpStatus;

public class ErrorHandler {

    public ErrorHandler() {
    }

    public HttpResponse notFound(HttpContext httpContext) {
        HttpResponse httpResponse = httpContext.getHttpResponse();

        httpResponse.setStatusCode(HttpStatus.NOT_FOUND);
        httpResponse.addHeader("Content-Type", "text/html");
        httpResponse.setContent("<h1>Error (404): The page or resource you are looking for is invalid.</h1>".getBytes());

        return httpResponse;
    }
}
