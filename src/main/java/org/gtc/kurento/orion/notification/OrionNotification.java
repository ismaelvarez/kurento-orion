package org.gtc.kurento.orion.notification;

import java.util.List;

/**
 * Orion Notification Model
 */
public class OrionNotification<T> {
    List<T> entities;
    String id;

    public OrionNotification(String id, List<T> entities) {
        this.id = id;
        this.entities = entities;
    }

    public List<T> getEntities() {
        return entities;
    }

    public void setEntities(List<T> entities) {
        this.entities = entities;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}