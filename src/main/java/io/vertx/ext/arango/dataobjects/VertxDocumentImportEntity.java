package io.vertx.ext.arango.dataobjects;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@DataObject
public class VertxDocumentImportEntity {
    private final static String CREATED = "created";
    private final static String ERRORS = "erros";
    private final static String EMPTY = "empty";
    private final static String UPDATED = "updated";
    private final static String IGNORED = "ignored";
    private final static String DETAILS = "details";

    private Integer created;
    private Integer errors;
    private Integer empty;
    private Integer updated;
    private Integer ignored;
    private Collection<String> details;

    public VertxDocumentImportEntity() {
    }

    public VertxDocumentImportEntity(JsonObject json) {
        created = json.getInteger(CREATED);
        errors = json.getInteger(ERRORS);
        empty = json.getInteger(EMPTY);
        updated = json.getInteger(UPDATED);
        ignored = json.getInteger(IGNORED);
        details = new ArrayList<>();
        JsonArray array = json.getJsonArray(DETAILS);
        for (int i = 0; i < array.size(); i++) {
            details.add(array.getString(i));
        }
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.put(CREATED, created);
        json.put(ERRORS, errors);
        json.put(EMPTY, empty);
        json.put(UPDATED, updated);
        json.put(IGNORED, ignored);
        JsonArray array = new JsonArray();
        for (String detail : details) {
            array.add(detail);
        }
        json.put(DETAILS, array);
        return json;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public Integer getErrors() {
        return errors;
    }

    public void setErrors(Integer errors) {
        this.errors = errors;
    }

    public Integer getEmpty() {
        return empty;
    }

    public void setEmpty(Integer empty) {
        this.empty = empty;
    }

    public Integer getUpdated() {
        return updated;
    }

    public void setUpdated(Integer updated) {
        this.updated = updated;
    }

    public Integer getIgnored() {
        return ignored;
    }

    public void setIgnored(Integer ignored) {
        this.ignored = ignored;
    }

    public Collection<String> getDetails() {
        return details;
    }

    public void setDetails(Collection<String> details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VertxDocumentImportEntity that = (VertxDocumentImportEntity) o;
        return Objects.equals(created, that.created) &&
                Objects.equals(errors, that.errors) &&
                Objects.equals(empty, that.empty) &&
                Objects.equals(updated, that.updated) &&
                Objects.equals(ignored, that.ignored) &&
                Objects.equals(details, that.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(created, errors, empty, updated, ignored, details);
    }
}
