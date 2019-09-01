package io.vertx.ext.arango.dataobjects;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.arango.VertxBaseDocument;

import java.util.Objects;

@DataObject
public class VertxDocumentUpdateEntity {
    private final static String OLD_NEW = "old_new";
    private final static String NEW_DOCUMENT = "new_document";
    private final static String OLD_DOCUMENT = "old_document";

    private String oldNew;
    private VertxBaseDocument newDocument;
    private VertxBaseDocument oldDocument;

    public VertxDocumentUpdateEntity() {

    }

    public VertxDocumentUpdateEntity(JsonObject json) {
        oldNew = json.getString(OLD_NEW);
        newDocument = new VertxBaseDocument(json.getJsonObject(NEW_DOCUMENT));
        oldDocument = new VertxBaseDocument(json.getJsonObject(OLD_DOCUMENT));
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.put(OLD_NEW, oldNew);
        json.put(NEW_DOCUMENT, newDocument.toJson());
        json.put(OLD_DOCUMENT, oldDocument.toJson());
        return json;
    }

    public String getOldNew() {
        return oldNew;
    }

    public void setOldNew(String oldNew) {
        this.oldNew = oldNew;
    }

    public VertxBaseDocument getNewDocument() {
        return newDocument;
    }

    public void setNewDocument(VertxBaseDocument newDocument) {
        this.newDocument = newDocument;
    }

    public VertxBaseDocument getOldDocument() {
        return oldDocument;
    }

    public void setOldDocument(VertxBaseDocument oldDocument) {
        this.oldDocument = oldDocument;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VertxDocumentUpdateEntity that = (VertxDocumentUpdateEntity) o;
        return Objects.equals(oldNew, that.oldNew) &&
                Objects.equals(newDocument, that.newDocument) &&
                Objects.equals(oldDocument, that.oldDocument);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oldNew, newDocument, oldDocument);
    }
}
