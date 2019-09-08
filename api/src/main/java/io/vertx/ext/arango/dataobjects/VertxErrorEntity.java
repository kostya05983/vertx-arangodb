package io.vertx.ext.arango.dataobjects;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.Objects;

/**
 * @author kostya05983
 */
@DataObject
public class VertxErrorEntity {
    private final static String ERROR_MESSAGE = "error_message";
    private final static String EXCEPTION = "exception";
    private final static String CODE = "code";
    private final static String ERROR_NUM = "errorNum";

    private String errorMessage;
    private String exception;
    private int code;
    private int errorNum;

    public VertxErrorEntity() {

    }

    public VertxErrorEntity(JsonObject json) {
        errorMessage = json.getString(ERROR_MESSAGE);
        exception = json.getString(EXCEPTION);
        code = json.getInteger(CODE);
        errorNum = json.getInteger(ERROR_NUM);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(int errorNum) {
        this.errorNum = errorNum;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.put(ERROR_MESSAGE, errorMessage);
        json.put(EXCEPTION, exception);
        json.put(CODE, code);
        json.put(ERROR_NUM, errorNum);
        return json;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VertxErrorEntity that = (VertxErrorEntity) o;
        return code == that.code &&
                errorNum == that.errorNum &&
                Objects.equals(errorMessage, that.errorMessage) &&
                Objects.equals(exception, that.exception);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorMessage, exception, code, errorNum);
    }
}
