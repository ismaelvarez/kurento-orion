package org.gtc.kurento.orion.subscription.entities;

public class SubscriptionResponse {
    private final String location;
    private final String fiwareCorrelator;
    private final SubscriptionRequest request;

    public SubscriptionResponse(SubscriptionRequest request, String location, String fiwareCorrelator) {
        this.request = request;
        this.location = location;
        this.fiwareCorrelator = fiwareCorrelator;
    }

    public String getLocation() {
        return location;
    }

    public String getSubscriptionId() {
        int index = location.indexOf("subscriptions") + "subscriptions".length() + 1;
        return location.substring(index);
    }

    public String getFiwareCorrelator() {
        return fiwareCorrelator;
    }

    public SubscriptionRequest getRequest() {
        return request;
    }
    
}
