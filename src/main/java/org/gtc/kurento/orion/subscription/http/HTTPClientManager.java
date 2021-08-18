package org.gtc.kurento.orion.subscription.http;

import static org.apache.http.entity.ContentType.APPLICATION_JSON;

import java.io.IOException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.gtc.kurento.orion.subscription.OrionSubscriptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTTPClientManager implements HTTPClient {
    private static final Logger log = LoggerFactory.getLogger(HTTPClientManager.class);

    private static HTTPClientManager instance;

    public static HTTPClientManager getInstance() {
        if (instance == null) 
            instance = new HTTPClientManager();
        return instance;
    }

    private HTTPClientManager() {
        
    }

    @Override
    public Response put(String uri, List<Header> headers, String body) {
        return null;
    }

    @Override
    public Response post(String uri, List<Header> headers, String body) {
        Request req = Request.Post(uri);

        log.info("Post Request to {}: {}", uri, body);
        buildRequestContent(req, headers, body);

        return executeRequest(req);
    }

    @Override
    public Response delete(String uri, List<Header> headers, String body) {
        Request req = Request.Delete(uri);

        log.info("Delete Request to {}: {}", uri, body);
        buildRequestContent(req, headers, body);

        return executeRequest(req);
    }

    @Override
    public Response get(String uri, List<Header> headers, String body) {
        Request req = Request.Get(uri);

        log.info("Get Request to {}: {}", uri, body);
        buildRequestContent(req, headers, body);

        return executeRequest(req);
    }

    private void buildRequestContent(Request req, List<Header> headers, String body) {
        for (Header hd : headers) {
            req.addHeader(hd);
        }
        req.socketTimeout(5000);

        if (body != null) {
            req.bodyString(body, APPLICATION_JSON).connectTimeout(5000);
        }
    }

    private Response executeRequest(Request request) {
        Response response;
        try {
            response = request.execute();
        } catch (IOException e) {
            throw new OrionSubscriptionException("Could not execute HTTP request", e);
        }
        return response;
    }
    
}
