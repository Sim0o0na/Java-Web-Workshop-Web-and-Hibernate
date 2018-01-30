package casebook.handlers.dynamic;

import casebook.annotations.Get;
import casebook.annotations.Post;
import casebook.handlers.fixed.ResourceHandler;
import javache.http.HttpRequest;
import javache.http.HttpResponse;
import javache.http.HttpStatus;

public class HomeHandler extends BaseHandler {
    @Get(route = "/")
    public HttpResponse index(HttpRequest request, HttpResponse response) {
        response.setStatusCode(HttpStatus.OK);
        response.addHeader("Content-Type", "text/html");
        response.setContent(this.getView("index").getBytes());
        return response;
    }

    @Get(route = "/login")
    public HttpResponse login(HttpRequest request, HttpResponse response) {
        response.setStatusCode(HttpStatus.OK);
        response.addHeader("Content-Type", "text/html");
        response.setContent(this.getView("login").getBytes());
        return response;
    }

    @Post(route = "/login")
    public HttpResponse loginPost(HttpRequest request, HttpResponse response) {
        response.setStatusCode(HttpStatus.OK);
        response.addHeader("Content-Type", "text/html");
        response.setContent(("<h2>You posted from login</h2>").getBytes());
        return response;
    }
}
