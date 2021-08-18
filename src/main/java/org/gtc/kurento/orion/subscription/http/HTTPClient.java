package org.gtc.kurento.orion.subscription.http;

import java.util.List;

import org.apache.http.Header;
import org.apache.http.client.fluent.Response;

public interface HTTPClient {
    public Response put(String uri, List<Header> headers, String body);
    public Response post(String uri, List<Header> headers, String body);
    public Response delete(String uri, List<Header> headers, String body);
    public Response get(String uri, List<Header> headers, String body);
}
