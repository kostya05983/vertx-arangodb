package io.vertx.ext.arango;

import io.vertx.codegen.annotations.DataObject;

@DataObject
public class DocumentInsertResult {
    private VertxBaseDocument oldObject;
    private VertxBaseDocument newObject;

    public DocumentInsertResult() {

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
}
