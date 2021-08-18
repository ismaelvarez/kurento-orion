package org.gtc.kurento.orion.subscription.entities;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SubscriptionRequest {
    String description;
    Subject subject;
    Notification notification;

    Date expires;
    int throttling = 5;

    public class Notification {

        public class Http{
            String url;
        }

        Http http;
        List<String> attrs;

        public Notification() {
            this.attrs = new ArrayList<>();
            http = new Http();
        }

        public List<String> getAttrs() {
            return this.attrs;
        }
    }

    public class Subject {
        List<EntityPattern> entities;
    
        public Subject(List<EntityPattern> entities) {
            this.entities = entities;
        }
    
        public Subject() {
            this.entities = new ArrayList<>();
        }
    
        public void addEntity(EntityPattern entity) {
            if (!entities.contains(entity))
                entities.add(entity);
        }
    
    }

    public SubscriptionRequest() {
        this.subject = new Subject();
        this.notification = new Notification();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public List<EntityPattern> getAllEntityPatterns() {
        return subject.entities;
    }

    public void addEntity(EntityPattern entity) {
        subject.addEntity(entity);
    }

    public void setNotificationUrl(String url) {
        notification.http.url = url;
    }
 
    public String getNotificationUrl() {
        return notification.http.url;
    }

    public List<String> getNotificationAttrs() {
        return notification.getAttrs();
    }

    public void setExpiringDate(Date date) {
        this.expires = date;
    }

    public Date getExpiringDate() {
        return expires;
    }

    public void setThrottling(int throttling) {
        this.throttling = throttling;
    }
    
}
