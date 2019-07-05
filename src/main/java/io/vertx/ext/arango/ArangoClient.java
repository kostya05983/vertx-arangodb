package io.vertx.ext.arango;

import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.DocumentCreateEntity;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.arango.impl.ArangoClientImpl;

public interface ArangoClient {

    static ArangoClient createNonShared(Vertx vertx, JsonObject config, String dataSourceName) {
        return new ArangoClientImpl(vertx, config, dataSourceName);
    }

    @Fluent
    public void insertDocument(String collectionName, BaseDocument document, Handler<AsyncResult<DocumentCreateEntity<BaseDocument>>> resultHandler);
}
