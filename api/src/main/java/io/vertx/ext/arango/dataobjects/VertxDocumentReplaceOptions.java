package io.vertx.ext.arango.dataobjects;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.Objects;

/**
 * @author kostya05983
 */
@DataObject
public class VertxDocumentReplaceOptions {
    private final static String WAIT_FOR_SYNC = "wait_for_sync";
    private final static String IGNORE_REVS = "ignore_revs";
    private final static String IF_MATCH = "if_match";
    private final static String RETURN_NEW = "return_new";
    private final static String RETURN_OLD = "return_old";
    private final static String SILENT = "silent";

    private Boolean waitForSync;
    private Boolean ignoreRevs;
    private String ifMatch;
    private Boolean returnNew;
    private Boolean returnOld;
    private Boolean silent;

    public VertxDocumentReplaceOptions() {
    }

    public VertxDocumentReplaceOptions(JsonObject json) {
        waitForSync = json.getBoolean(WAIT_FOR_SYNC);
        ignoreRevs = json.getBoolean(IGNORE_REVS);
        ifMatch = json.getString(IF_MATCH);
        returnNew = json.getBoolean(RETURN_NEW);
        returnOld = json.getBoolean(RETURN_OLD);
        silent = json.getBoolean(SILENT);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.put(WAIT_FOR_SYNC, waitForSync);
        json.put(IGNORE_REVS, ignoreRevs);
        json.put(IF_MATCH, ifMatch);
        json.put(RETURN_NEW, returnNew);
        json.put(RETURN_OLD, returnOld);
        json.put(SILENT, silent);
        return json;
    }

    public Boolean getWaitForSync() {
        return waitForSync;
    }

    public void setWaitForSync(Boolean waitForSync) {
        this.waitForSync = waitForSync;
    }

    public Boolean getIgnoreRevs() {
        return ignoreRevs;
    }

    public void setIgnoreRevs(Boolean ignoreRevs) {
        this.ignoreRevs = ignoreRevs;
    }

    public String getIfMatch() {
        return ifMatch;
    }

    public void setIfMatch(String ifMatch) {
        this.ifMatch = ifMatch;
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

    public Boolean getSilent() {
        return silent;
    }

    public void setSilent(Boolean silent) {
        this.silent = silent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VertxDocumentReplaceOptions that = (VertxDocumentReplaceOptions) o;
        return Objects.equals(waitForSync, that.waitForSync) &&
                Objects.equals(ignoreRevs, that.ignoreRevs) &&
                Objects.equals(ifMatch, that.ifMatch) &&
                Objects.equals(returnNew, that.returnNew) &&
                Objects.equals(returnOld, that.returnOld) &&
                Objects.equals(silent, that.silent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(waitForSync, ignoreRevs, ifMatch, returnNew, returnOld, silent);
    }
}
