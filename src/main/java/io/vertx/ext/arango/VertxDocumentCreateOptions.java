package io.vertx.ext.arango;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.Objects;

@DataObject
public class VertxDocumentCreateOptions {
    private final static String WAIT_FOR_SYNC = "wait_fot_sync";
    private final static String RETURN_NEW = "return_new";
    private final static String RETURN_OLD = "return_old";
    private final static String OVER_WRITE = "overwrite";
    private final static String SILENT = "silent";

    private Boolean waitForSync;
    private Boolean returnNew;
    private Boolean returnOld;
    private Boolean overwrite;
    private Boolean silent;

    public VertxDocumentCreateOptions() {
    }

    public VertxDocumentCreateOptions(VertxDocumentCreateOptions other) {
        this.waitForSync = other.waitForSync;
        this.returnNew = other.returnNew;
        this.returnOld = other.returnOld;
        this.overwrite = other.overwrite;
        this.silent = other.silent;
    }

    public VertxDocumentCreateOptions(JsonObject json) {
        waitForSync = json.getBoolean(WAIT_FOR_SYNC);
        returnNew = json.getBoolean(RETURN_NEW);
        returnOld = json.getBoolean(RETURN_OLD);
        overwrite = json.getBoolean(OVER_WRITE);
        silent = json.getBoolean(SILENT);
    }

    public Boolean getWaitForSync() {
        return waitForSync;
    }

    public void setWaitForSync(Boolean waitForSync) {
        this.waitForSync = waitForSync;
    }

    public Boolean getReturnNew() {
        return returnNew;
    }

    public void setReturnNew(Boolean returnNew) {
        this.returnNew = returnNew;
    }

    public Boolean getReturnOld() {
        return returnOld;
    }

    public void setReturnOld(Boolean returnOld) {
        this.returnOld = returnOld;
    }

    public Boolean getOverwrite() {
        return overwrite;
    }

    public void setOverwrite(Boolean overwrite) {
        this.overwrite = overwrite;
    }

    public Boolean getSilent() {
        return silent;
    }

    public void setSilent(Boolean silent) {
        this.silent = silent;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.put(WAIT_FOR_SYNC, waitForSync);
        json.put(RETURN_NEW, returnNew);
        json.put(RETURN_OLD, returnOld);
        json.put(OVER_WRITE, overwrite);
        json.put(SILENT, silent);
        return json;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VertxDocumentCreateOptions that = (VertxDocumentCreateOptions) o;
        return Objects.equals(waitForSync, that.waitForSync) &&
                Objects.equals(returnNew, that.returnNew) &&
                Objects.equals(returnOld, that.returnOld) &&
                Objects.equals(overwrite, that.overwrite) &&
                Objects.equals(silent, that.silent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(waitForSync, returnNew, returnOld, overwrite, silent);
    }
}
