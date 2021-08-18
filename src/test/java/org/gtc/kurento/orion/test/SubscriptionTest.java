package org.gtc.kurento.orion.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.gtc.kurento.orion.subscription.OrionSubscriptionException;
import org.gtc.kurento.orion.subscription.OrionSubscriptionManager;
import org.gtc.kurento.orion.subscription.entities.EntityPattern;
import org.gtc.kurento.orion.subscription.entities.SubscriptionRequest;
import org.gtc.kurento.orion.subscription.entities.SubscriptionResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.kurento.orion.connector.OrionConnectorConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit test for simple SubscriptionRequest.
 */
class SubscriptionTest {
    private static final Logger log	= LoggerFactory.getLogger(SubscriptionTest.class);

    private static SubscriptionRequest req;
    private static final OrionConnectorConfiguration config = new OrionConnectorConfiguration();
    private static final String expectedJsonBody = "{\"description\":\"This is a test\"," 
    + "\"subject\":{\"entities\":[{\"id\":\"Room1\",\"type\":\"Room\"}]" 
    + "},\"notification\":{\"http\":{\"url\":\"http://127.0.0.0\"},\"attrs\":[\"temperature\"]},\"expires\":\"2022-07-30T00:00:00Z\"," 
    + "\"throttling\":5}";
    private static final List<String> subscriptions = new ArrayList<>();
    private static final OrionSubscriptionManager subscriptionManager = new OrionSubscriptionManager(config);


    @BeforeAll
    static void init_Test() {
        req = new SubscriptionRequest();
        req.setDescription("This is a test");
        req.setExpiringDate(java.sql.Date.valueOf("2022-07-30"));
        req.setThrottling(5);
        req.setNotificationUrl("http://127.0.0.0");
        EntityPattern a = new EntityPattern();
        a.setId("Room1");
        a.setType("Room");
        req.addEntity(a);
        req.getNotificationAttrs().add("temperature");
    }

    @AfterAll
    static void delete_data() {
        for (String id : subscriptions) {
            subscriptionManager.unsubscribe(id);
        }
    }

    @Test
    void to_json_entity_with_pattern_Test() {
        log.info("[ENTITY WITH PATTERN JSON PARSER] ");
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();
        String json = gson.toJson(req);
        assertEquals(expectedJsonBody, json);
    }

    @Test
    void subscribe_test() {
        log.info("[SUBSCRIBE TO ORION] ");
        SubscriptionResponse response = subscriptionManager.subscribe(req);
        assertNotNull(response.getSubscriptionId());
        subscriptions.add(response.getSubscriptionId());
    }

    @Test
    void subscribe_with_invalid_data_test() {
        log.info("[SUBSCRIBE TO ORION] ");
        req.setNotificationUrl(null);
        Exception exception = assertThrows(OrionSubscriptionException.class, () -> {
            SubscriptionResponse response = subscriptionManager.subscribe(req);
        });
        req.setNotificationUrl("http://127.0.0.0");
    
        assertTrue(exception.getMessage().equals("Orion could not create the subscrition. Orion responded with 400 status code"));
    }

}
