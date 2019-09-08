package io.vertx.ext.arango;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@DataObject
public class VertxCollection {
    private final static String DOCUMENTS = "documents";

    private Collection<VertxBaseDocument> documents;

    public VertxCollection() {
    }

    public VertxCollection(JsonObject json) {
        JsonArray array = json.getJsonArray(DOCUMENTS);
        documents = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            VertxBaseDocument document = new VertxBaseDocument(array.getJsonObject(i));
            documents.add(document);
        }
    }

    public Collection<VertxBaseDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(Collection<VertxBaseDocument> documents) {
        this.documents = documents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VertxCollection that = (VertxCollection) o;
        return Objects.equals(documents, that.documents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documents);
    }
}
