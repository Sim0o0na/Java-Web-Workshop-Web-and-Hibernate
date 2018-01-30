package javache.http;


public class HttpContextImpl implements HttpContext {

    private HttpRequest httpRequest;

    private HttpResponse httpResponse;

    public HttpContextImpl(HttpRequest httpRequest, HttpResponse httpResponse) {
        this.setHttpRequest(httpRequest);
        this.setHttpResponse(httpResponse);
    }

    @Override
    public HttpRequest getHttpRequest() {
        return this.httpRequest;
    }

    private void setHttpRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    @Override
    public HttpResponse getHttpResponse() {
        return this.httpResponse;
    }

    private void setHttpResponse(HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }
}
