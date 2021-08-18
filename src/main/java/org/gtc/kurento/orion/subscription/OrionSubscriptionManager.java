package org.gtc.kurento.orion.subscription;

import static org.apache.http.entity.ContentType.APPLICATION_JSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Response;
import org.apache.http.message.BasicHeader;
import org.gtc.kurento.orion.subscription.entities.SubscriptionRequest;
import org.gtc.kurento.orion.subscription.entities.SubscriptionResponse;
import org.gtc.kurento.orion.subscription.http.HTTPClient;
import org.gtc.kurento.orion.subscription.http.HTTPClientManager;
import org.kurento.orion.connector.OrionConnectorConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrionSubscriptionManager extends OrionSubscription {
    private static final Logger log = LoggerFactory.getLogger(OrionSubscriptionManager.class);

    private Gson gson;
    HTTPClient httpClient;

    public OrionSubscriptionManager(OrionConnectorConfiguration config) {
        super(config);
	    this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();
        httpClient = HTTPClientManager.getInstance();
    }

    @Override
    public SubscriptionResponse subscribe(SubscriptionRequest request) {
        if (request != null) {
            String jsonEntity = gson.toJson(request);
            log.info("Subscription: {}", jsonEntity);
            HttpResponse response = null;
            try {
                response = httpClient.post(getSubscriptionUri(), createHeaders(), jsonEntity).returnResponse();
                if (response.getStatusLine().getStatusCode() != HttpStatus.SC_CREATED) {
                    throw new OrionSubscriptionException("Orion could not create the subscrition. Orion responded with " + response.getStatusLine().getStatusCode() + " status code");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return getSubscriptionResponse(response, request);
        }

        return null;
    }

    private SubscriptionResponse getSubscriptionResponse(HttpResponse response, SubscriptionRequest request) {
        try {
            String location = response.getFirstHeader("Location").getValue();
            String fiwareCorrelator = response.getFirstHeader("Fiware-Correlator").getValue();

            return new SubscriptionResponse(request, location, fiwareCorrelator);

        } catch (UnsupportedOperationException e) {
            throw new OrionSubscriptionException("Unknown error on CheckResponse::" + e.getMessage(), e);
        }
    }

    @Override
    public boolean unsubscribe(String subscriptionId) {
        Response response = httpClient.delete(getSubscriptionUri() + "/" + subscriptionId, new ArrayList<>(), null);
        try {
            return response.returnResponse().getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT;
        } catch (IOException e) {
            throw new OrionSubscriptionException("Error while reading response::" + e.getMessage(), e);
        }
    }

    private List<Header> createHeaders() {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"));
        if (this.config.getFiwareService() != null && !"".equalsIgnoreCase(this.config.getFiwareService())) {
            headers.add(new BasicHeader("Fiware-Service", config.getFiwareService()));
        }
        return headers;
    }
    
}
