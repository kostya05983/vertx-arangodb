package io.vertx.ext.arango;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.Objects;

@DataObject
public class VertxDocumentCreateEntity {
    private final static String OLD_OBJECT = "old_object";
    private final static String NEW_OBJECT = "new_obejct";

    private VertxBaseDocument oldObject;
    private VertxBaseDocument newObject;

    public VertxDocumentCreateEntity() {

    }

    public VertxDocumentCreateEntity(JsonObject json) {
        oldObject = new VertxBaseDocument(json.getJsonObject(OLD_OBJECT));
        newObject = new VertxBaseDocument(json.getJsonObject(NEW_OBJECT));
    }

    public VertxBaseDocument getOldObject() {
        return oldObject;
    }

    public void setOldObject(VertxBaseDocument oldObject) {
        this.oldObject = oldObject;
    }

    public VertxBaseDocument getNewObject() {
        return newObject;
    }

    public void setNewObject(VertxBaseDocument newObject) {
        this.newObject = newObject;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.put(OLD_OBJECT, oldObject.toJson());
        json.put(NEW_OBJECT, newObject.toJson());
        return json;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        VertxDocumentCreateEntity vertxDocumentCreateEntity = (VertxDocumentCreateEntity) obj;

        if (!Objects.equals(oldObject, vertxDocumentCreateEntity.oldObject)) return false;
        return newObject.equals(vertxDocumentCreateEntity.newObject);
    }

    @Override
    public int hashCode() {
        int result = 31 + oldObject.hashCode();
        result = 31 * result + newObject.hashCode();
        return result;
    }
}
