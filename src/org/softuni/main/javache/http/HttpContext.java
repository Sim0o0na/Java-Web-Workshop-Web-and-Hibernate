package javache.http;

public interface HttpContext {
    HttpRequest getHttpRequest();

    HttpResponse getHttpResponse();
}
