package io.vertx.ext.arango;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

@VertxGen
public interface ArangoClientBaseDocument {

    public void insertDocument(String collectionName, VertxBaseDocument test);
}
