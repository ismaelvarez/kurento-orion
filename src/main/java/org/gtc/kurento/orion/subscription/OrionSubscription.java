package org.gtc.kurento.orion.subscription;

import org.gtc.kurento.orion.subscription.entities.SubscriptionRequest;
import org.gtc.kurento.orion.subscription.entities.SubscriptionResponse;
import org.kurento.orion.connector.OrionConnectorConfiguration;

public abstract class OrionSubscription {

    private static final String SUBSCRIPTION_PATH = "/v2/subscriptions";
    protected OrionConnectorConfiguration config;

    public OrionSubscription(OrionConnectorConfiguration config) {
        this.config = config;
    }  

    protected String getSubscriptionUri() {
        return config.getOrionScheme() + "://" + config.getOrionHost() + ":" + config.getOrionPort() + SUBSCRIPTION_PATH;
    }

    public abstract SubscriptionResponse subscribe(SubscriptionRequest subscription);
    public abstract boolean unsubscribe(String subscriptionId);
}
