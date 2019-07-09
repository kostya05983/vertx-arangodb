package io.vertx.ext.arango.impl;

import com.arangodb.*;
import com.arangodb.entity.*;
import com.arangodb.model.*;
import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.arango.ArangoClient;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class ArangoClientImpl<T> implements ArangoClient<T> {

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

    @Override
    public ArangoDatabaseAsync db(String collection) {
        return db;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public void insertDocument(String collectionName, T document,
                               Handler<AsyncResult<DocumentCreateEntity<T>>> resultHandler) {
        final ArangoCollectionAsync collection = getCollection(collectionName);
        collection.insertDocument(document).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void insertDocument(String collectionName, T document, DocumentCreateOptions options,
                               Handler<AsyncResult<DocumentCreateEntity<T>>> resultHandler) {
        final ArangoCollectionAsync collection = getCollection(collectionName);
        collection.insertDocument(document, options).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void insertDocuments(String collectionName, Collection<T> values,
                                Handler<AsyncResult<MultiDocumentEntity<DocumentCreateEntity<T>>>> resultHandler) {
        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.insertDocuments(values).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void importDocuments(String collectionName, Collection<T> values,
                                Handler<AsyncResult<DocumentImportEntity>> resultHandler) {
        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.importDocuments(values).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void importDocuments(String collectionName, Collection<T> values, DocumentImportOptions options,
                                Handler<AsyncResult<DocumentImportEntity>> resultHandler) {
        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.importDocuments(values, options).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void getDocument(String collectionName, String key, Class<T> type, Handler<AsyncResult<T>> resultHandler) {
        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.getDocument(key, type).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void getDocument(String collectionName, String key, Class<T> type, DocumentReadOptions options,
                            Handler<AsyncResult<T>> resultHandler) {
        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.getDocument(key, type, options).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void getDocuments(String collectionName, Collection<String> keys, Class<T> type,
                             Handler<AsyncResult<MultiDocumentEntity<T>>> resultHandler) {

    }

    @Override
    public void getDocuments(String collectionName, Collection<String> keys, Class<T> type, DocumentReadOptions options,
                             Handler<AsyncResult<MultiDocumentEntity<T>>> resultHandler) {

    }

    @Override
    public void replaceDocument(String collectionName, String key, T value,
                                Handler<AsyncResult<DocumentUpdateEntity<T>>> resultHandler) {

    }

    @Override
    public void replaceDocument(String collectionName, String key, T value, DocumentReplaceOptions options,
                                Handler<AsyncResult<DocumentUpdateEntity>> resultHandler) {

    }

    @Override
    public void replaceDocuments(String collectionName, Collection<T> values,
                                 Handler<AsyncResult<MultiDocumentEntity<DocumentUpdateEntity>>> resultHandler) {

    }

    @Override
    public void replaceDocuments(String collectionName, Collection<T> values, DocumentReplaceOptions options,
                                 Handler<AsyncResult<MultiDocumentEntity<DocumentUpdateEntity<T>>>> resultHandler) {

    }

    @Override
    public void updateDocument(String collectionName, String key, T value,
                               Handler<AsyncResult<DocumentUpdateEntity<T>>> resultHandler) {

    }

    @Override
    public void updateDocument(String collectionName, String key, T value, DocumentUpdateOptions options,
                               Handler<AsyncResult<DocumentUpdateEntity<T>>> resultHandler) {

    }

    @Override
    public void updateDocuments(String collectionName, Collection<T> values,
                                Handler<AsyncResult<MultiDocumentEntity<DocumentUpdateEntity>>> resultHandler) {

    }

    @Override
    public void updateDocuments(String collectionName, Collection<T> values, DocumentUpdateOptions options,
                                Handler<AsyncResult<MultiDocumentEntity<DocumentUpdateEntity<T>>>> resultHandler) {

    }

    @Override
    public void deleteDocument(String collectionName, String key, Handler<AsyncResult<Void>> resultHandler) {

    }

    @Override
    public void deleteDocument(String collectionName, String key, Class<T> type, DocumentDeleteOptions options,
                               Handler<AsyncResult<DocumentDeleteEntity<T>>> resultHandler) {

    }

    @Override
    public void deleteDocuments(String collectionName, Collection<?> values,
                                Handler<MultiDocumentEntity<DocumentDeleteEntity<Void>>> resultHandler) {

    }

    @Override
    public void deleteDocuments(String collectionName, Collection<?> values, Class<T> type,
                                DocumentDeleteOptions options,
                                Handler<MultiDocumentEntity<DocumentDeleteEntity<T>>> resultHandler) {

    }

    @Override
    public void documentExists(String collectionName, String key, Handler<AsyncResult<Boolean>> resultHandler) {

    }

    @Override
    public void documentExists(String collectionName, String key, DocumentExistsOptions options,
                               Handler<AsyncResult<Boolean>> resultHandler) {

    }

    @Override
    public void getIndex(String collectionName, String id, Handler<AsyncResult<IndexEntity>> resultHandler) {

    }

    @Override
    public void deleteIndex(String collectionName, String id, Handler<AsyncResult<IndexEntity>> resultHandler) {

    }

    @Override
    public void ensureHashIndex(String collectionName, Iterable<String> fields, HashIndexOptions options,
                                Handler<AsyncResult<IndexEntity>> resultHandler) {

    }

    @Override
    public void ensureSkiplistIndex(String collectionName, Iterable<String> fields, SkiplistIndexOptions options,
                                    Handler<AsyncResult<IndexEntity>> resultHandler) {

    }

    @Override
    public void ensurePersistentIndex(String collectionName, Iterable<String> fields, PersistentIndexOptions options,
                                      Handler<AsyncResult<IndexEntity>> resultHandler) {

    }

    @Override
    public void ensureGeoIndex(String collectionName, Iterable<String> fields, GeoIndexOptions options,
                               Handler<AsyncResult<IndexEntity>> resultHandler) {

    }

    @Override
    public void ensureFulltextIndex(String collectionName, Iterable<String> fields, FulltextIndexOptions options,
                                    Handler<AsyncResult<IndexEntity>> resultHandler) {

    }

    @Override
    public void getIndexes(String collectionName, Handler<AsyncResult<IndexEntity>> resultHandler) {

    }

    @Override
    public void exists(String collectionName, Handler<AsyncResult<Boolean>> resultHandler) {

    }

    @Override
    public void truncate(String collectionName, Handler<AsyncResult<CollectionEntity>> resultHandler) {

    }

    @Override
    public void count(String collectionName, Handler<AsyncResult<CollectionPropertiesEntity>> resultHandler) {

    }

    @Override
    public void create(String collectionName, Handler<AsyncResult<CollectionEntity>> resultHandler) {

    }

    @Override
    public void create(String collectionName, CollectionCreateOptions options,
                       Handler<AsyncResult<CollectionEntity>> resultHandler) {

    }

    @Override
    public void drop(String collectionName, Handler<AsyncResult<Void>> resultHandler) {

    }

    @Override
    public void drop(String collectionName, boolean isSystem, Handler<AsyncResult<Void>> resultHandler) {

    }

    @Override
    public void load(String collectionName, Handler<AsyncResult<CollectionEntity>> resultHandler) {

    }

    @Override
    public void unload(String collectionName, Handler<AsyncResult<CollectionEntity>> resultHandler) {

    }

    @Override
    public void getInfo(String collectionName, Handler<AsyncResult<CollectionEntity>> resultHandler) {

    }

    @Override
    public void getProperties(String collectionName, Handler<AsyncResult<CollectionPropertiesEntity>> resultHandler) {

    }

    @Override
    public void changeProperties(String collectionName, CollectionPropertiesEntity options,
                                 Handler<AsyncResult<CollectionPropertiesEntity>> resultHandler) {

    }

    @Override
    public void rename(String collectionName, String newName, Handler<AsyncResult<CollectionEntity>> resultHandler) {

    }

    @Override
    public void getRevision(String collectionName, Handler<AsyncResult<CollectionRevisionEntity>> resultHandler) {

    }

    @Override
    public void grantAccess(String collectionName, String user, Permissions permissions,
                            Handler<AsyncResult<Void>> resultHandler) {

    }

    @Override
    public void revokeAccess(String collectionName, String user, Handler<AsyncResult<Void>> resultHandler) {

    }

    @Override
    public void resetAccess(String collectionName, String user, Handler<AsyncResult<Void>> resultHandler) {

    }
}
