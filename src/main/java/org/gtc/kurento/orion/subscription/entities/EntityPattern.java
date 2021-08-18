package org.gtc.kurento.orion.subscription.entities;

public class EntityPattern {
    private String id;
    private String idPattern;
    private String type;
    private String typePattern;

    public String getIdPattern() {
        return idPattern;
    }

    public void setIdPattern(String idPattern) {
        this.idPattern = idPattern;
        this.id = null;
    }

    public String getTypePattern() {
        return typePattern;
    }

    public void setTypePattern(String typePattern) {
        this.typePattern = typePattern;
        this.type = null;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
        this.idPattern = null;
    }

    public void setType(String type) {
        this.type = type;
        this.typePattern = null;
    }
}