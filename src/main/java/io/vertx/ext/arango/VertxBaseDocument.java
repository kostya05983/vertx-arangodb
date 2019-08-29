package io.vertx.ext.arango;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author kostya05983
 */
@DataObject
public class VertxBaseDocument {
    private final static String ID = "id";
    private final static String KEY = "key";
    private final static String REVISION = "revision";
    private final static String PROPERTIES = "properties";

    private String id;
    private String key;
    private String revision;

    private Map<String, Object> properties;

    /**
     * Copy constructor
     *
     * @param other the one to copy
     */
    public VertxBaseDocument(VertxBaseDocument other) {
        this.id = other.id;
        this.key = other.key;
        this.revision = other.revision;
        this.properties = other.properties;
    }

    /**
     * Constructor from JSON
     *
     * @param json the json
     */
    public VertxBaseDocument(JsonObject json) {
        id = json.getString(ID);
        key = json.getString(KEY);
        revision = json.getString(REVISION);
        final JsonObject entries = json.getJsonObject(PROPERTIES);
        properties = new HashMap<>();
        for (Map.Entry<String, Object> entry : entries) {
            properties.put(entry.getKey(), entry.getValue());
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.put(ID, id);
        json.put(KEY, key);
        json.put(REVISION, revision);
        JsonObject properties = new JsonObject();
        for (Map.Entry<String, Object> entry : properties) {
            properties.put(entry.getKey(), entry.getValue());
        }
        json.put(PROPERTIES, properties);
        return json;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VertxBaseDocument options = (VertxBaseDocument) o;

        if (!Objects.equals(id, options.id)) return false;
        if (!Objects.equals(key, options.key)) return false;
        if (!Objects.equals(revision, options.revision)) return false;
        return properties == options.properties;
    }

    @Override
    public int hashCode() {
        int result = 31 + id.hashCode();
        result = 31 * result + key.hashCode();
        result = 31 * result + revision.hashCode();
        result = 31 * result + properties.hashCode();
        return result;
    }
}
