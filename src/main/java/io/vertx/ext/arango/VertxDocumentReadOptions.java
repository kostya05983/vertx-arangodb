package io.vertx.ext.arango;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.Objects;

@DataObject
public class VertxDocumentReadOptions {
    private final static String IF_NONE_MATCH = "if_none_match";
    private final static String IF_MATCH = "if_match";
    private final static String CATCH_EXCEPTION = "catch_exception";
    private final static String ALLOW_DIRTY_READ = "allow_dirty_read";

    private String ifNoneMatch;
    private String ifMatch;
    private Boolean catchException;
    private Boolean allowDirtyRead;

    public VertxDocumentReadOptions() {
    }

    public VertxDocumentReadOptions(JsonObject json) {
        ifNoneMatch = json.getString(IF_NONE_MATCH);
        ifMatch = json.getString(IF_MATCH);
        catchException = json.getBoolean(CATCH_EXCEPTION);
        allowDirtyRead = json.getBoolean(ALLOW_DIRTY_READ);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.put(IF_NONE_MATCH, ifNoneMatch);
        json.put(IF_MATCH, ifMatch);
        json.put(CATCH_EXCEPTION, catchException);
        json.put(ALLOW_DIRTY_READ, allowDirtyRead);
        return json;
    }

    public String getIfNoneMatch() {
        return ifNoneMatch;
    }

    public void setIfNoneMatch(String ifNoneMatch) {
        this.ifNoneMatch = ifNoneMatch;
    }

    public String getIfMatch() {
        return ifMatch;
    }

    public void setIfMatch(String ifMatch) {
        this.ifMatch = ifMatch;
    }

    public Boolean getCatchException() {
        return catchException;
    }

    public void setCatchException(Boolean catchException) {
        this.catchException = catchException;
    }

    public Boolean getAllowDirtyRead() {
        return allowDirtyRead;
    }

    public void setAllowDirtyRead(Boolean allowDirtyRead) {
        this.allowDirtyRead = allowDirtyRead;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VertxDocumentReadOptions that = (VertxDocumentReadOptions) o;
        return Objects.equals(ifNoneMatch, that.ifNoneMatch) &&
                Objects.equals(ifMatch, that.ifMatch) &&
                Objects.equals(catchException, that.catchException) &&
                Objects.equals(allowDirtyRead, that.allowDirtyRead);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ifNoneMatch, ifMatch, catchException, allowDirtyRead);
    }
}
