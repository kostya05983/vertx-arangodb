package io.vertx.ext.arango.impl;

import com.arangodb.ArangoCollectionAsync;
import com.arangodb.ArangoDBAsync;
import com.arangodb.ArangoDatabaseAsync;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.DocumentCreateEntity;
import com.arangodb.entity.MultiDocumentEntity;
import com.arangodb.model.DocumentCreateOptions;
import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.arango.ArangoClient;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class ArangoClientImpl implements ArangoClient {

    private static final Logger log = LoggerFactory.getLogger(ArangoClientImpl.class);

    private final Vertx vertx;

    protected static ArangoDBAsync arangoDB;
    protected static ArangoDatabaseAsync db;
    protected ArangoCollectionAsync collection;

    public ArangoClientImpl(Vertx vertx, JsonObject config, String dataSourceName) {
        Objects.requireNonNull(vertx);
        Objects.requireNonNull(config);
        Objects.requireNonNull(dataSourceName);
        this.vertx = vertx;
        arangoDB = new ArangoDBAsync.Builder().build();
        try {//for Test
            arangoDB.db(dataSourceName).drop().get();
        } catch (final Exception e) {

        }

        arangoDB.createDatabase(dataSourceName);
        db = arangoDB.db(dataSourceName);
        collection = db.collection("test");
    }


    private ArangoCollectionAsync getCollection(String name) {
        return db.collection(name);
    }

    @Override
    public void insertDocument(String collectionName, BaseDocument document,
                               Handler<AsyncResult<DocumentCreateEntity<BaseDocument>>> resultHandler) {
        final ArangoCollectionAsync collection = getCollection(collectionName);
        collection.insertDocument(document).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void insertDocument(String collectionName, BaseDocument document, DocumentCreateOptions options,
                               Handler<AsyncResult<DocumentCreateEntity<BaseDocument>>> resultHandler) {
        final ArangoCollectionAsync collection = getCollection(collectionName);
        collection.insertDocument(document, options).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void insertDocuments(String collectionName, Collection<BaseDocument> values,
                                Handler<AsyncResult<MultiDocumentEntity<DocumentCreateEntity<BaseDocument>>>> resultHandler) {
        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.insertDocuments(values).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    private <T, R> BiConsumer<? super T, ? super Throwable> convertCallBack(Handler<AsyncResult<R>> resultHandler,
                                                                            Function<T, R> converter) {
        Context context = vertx.getOrCreateContext();
        return (result, error) -> {
            context.runOnContext(v -> {
                if (error != null) {
                    resultHandler.handle(Future.failedFuture(error));
                } else {
                    resultHandler.handle(Future.succeededFuture(converter.apply(result)));
                }
            });
        };
    }
}
