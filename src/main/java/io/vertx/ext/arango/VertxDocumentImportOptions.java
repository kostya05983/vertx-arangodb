package io.vertx.ext.arango;

import com.sun.org.apache.xpath.internal.operations.Bool;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.Objects;

@DataObject
public class VertxDocumentImportOptions {
    private final static String FROM_PREFIX = "from_prefix";
    private final static String TO_PREFIX = "to_prefix";
    private final static String OVER_WRITE = "over_write";
    private final static String WAIT_FOR_SYNC = "wait_for_sync";
    private final static String ON_DUPLICATE = "on_duplicate";
    private final static String COMPLETE = "complete";
    private final static String DETAILS = "details";

    private String fromPrefix;
    private String toPrefix;
    private Boolean overwrite;
    private Boolean waitForSync;
    private String onDuplicate;
    private Boolean complete;
    private Boolean details;

    public VertxDocumentImportOptions() {

    }

    public VertxDocumentImportOptions(JsonObject jsonObject) {
        fromPrefix = jsonObject.getString(FROM_PREFIX);
        toPrefix = jsonObject.getString(TO_PREFIX);
        overwrite = jsonObject.getBoolean(OVER_WRITE);
        waitForSync = jsonObject.getBoolean(WAIT_FOR_SYNC);
        onDuplicate = jsonObject.getString(ON_DUPLICATE);
        complete = jsonObject.getBoolean(COMPLETE);
        details = jsonObject.getBoolean(DETAILS);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.put(FROM_PREFIX, fromPrefix);
        json.put(TO_PREFIX, toPrefix);
        json.put(OVER_WRITE, overwrite);
        json.put(WAIT_FOR_SYNC, waitForSync);
        json.put(ON_DUPLICATE, onDuplicate);
        json.put(COMPLETE, complete);
        json.put(DETAILS, details);
        return json;
    }

    public String getFromPrefix() {
        return fromPrefix;
    }

    public void setFromPrefix(String fromPrefix) {
        this.fromPrefix = fromPrefix;
    }

    public String getToPrefix() {
        return toPrefix;
    }

    public void setToPrefix(String toPrefix) {
        this.toPrefix = toPrefix;
    }

    public Boolean getOverwrite() {
        return overwrite;
    }

    public void setOverwrite(Boolean overwrite) {
        this.overwrite = overwrite;
    }

    public Boolean getWaitForSync() {
        return waitForSync;
    }

    public void setWaitForSync(Boolean waitForSync) {
        this.waitForSync = waitForSync;
    }

    public String getOnDuplicate() {
        return onDuplicate;
    }

    public void setOnDuplicate(String onDuplicate) {
        this.onDuplicate = onDuplicate;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public Boolean getDetails() {
        return details;
    }

    public void setDetails(Boolean details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VertxDocumentImportOptions that = (VertxDocumentImportOptions) o;
        return Objects.equals(fromPrefix, that.fromPrefix) &&
                Objects.equals(toPrefix, that.toPrefix) &&
                Objects.equals(overwrite, that.overwrite) &&
                Objects.equals(waitForSync, that.waitForSync) &&
                Objects.equals(onDuplicate, that.onDuplicate) &&
                Objects.equals(complete, that.complete) &&
                Objects.equals(details, that.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromPrefix, toPrefix, overwrite, waitForSync, onDuplicate, complete, details);
    }
}
