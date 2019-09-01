package io.vertx.ext.arango.dataobjects;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@DataObject
public class VertxMultiDocumentEntity {
    private final static String DOCUMENTS = "documents";
    private final static String ERRORS = "errors";

    private Collection<VertxDocumentCreateEntity> documents;
    private Collection<VertxErrorEntity> errors;

    public VertxMultiDocumentEntity() {
    }

    public VertxMultiDocumentEntity(JsonObject json) {
        JsonArray documentsArray = json.getJsonArray(DOCUMENTS);
        documents = new ArrayList<>();
        for (int i = 0; i < documentsArray.size(); i++) {
            VertxDocumentCreateEntity insertResult = new VertxDocumentCreateEntity(documentsArray.getJsonObject(i));
            documents.add(insertResult);
        }

        JsonArray errorsArray = json.getJsonArray(ERRORS);
        errors = new ArrayList<>();
        for (int i = 0; i < errorsArray.size(); i++) {
            VertxErrorEntity entity = new VertxErrorEntity(errorsArray.getJsonObject(i));
            errors.add(entity);
        }
    }


    public Collection<VertxDocumentCreateEntity> getDocuments() {
        return documents;
    }

    public void setDocuments(Collection<VertxDocumentCreateEntity> documents) {
        this.documents = documents;
    }

    public Collection<VertxErrorEntity> getErrors() {
        return errors;
    }

    public void setErrors(Collection<VertxErrorEntity> errors) {
        this.errors = errors;
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        JsonArray documentsArray = new JsonArray();
        for (VertxDocumentCreateEntity result : documents) {
            documentsArray.add(result.toJson());
        }
        jsonObject.put(DOCUMENTS, documentsArray);

        JsonArray errorsArray = new JsonArray();
        for (VertxErrorEntity entity : errors) {
            errorsArray.add(entity.toJson());
        }
        jsonObject.put(ERRORS, errorsArray);
        return jsonObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VertxMultiDocumentEntity that = (VertxMultiDocumentEntity) o;
        return Objects.equals(documents, that.documents) &&
                Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documents, errors);
    }
}
