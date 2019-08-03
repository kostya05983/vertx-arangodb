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

    private static ArangoDBAsync arangoDB;
    private static ArangoDatabaseAsync db;

    public ArangoClientImpl(Vertx vertx, JsonObject config, String dataSourceName) throws Exception {
        Objects.requireNonNull(vertx);
        Objects.requireNonNull(config);
        Objects.requireNonNull(dataSourceName);
        this.vertx = vertx;
        arangoDB = new ArangoDBAsync.Builder().build();
        arangoDB.db(dataSourceName).drop().get();
        db = arangoDB.db(dataSourceName);
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
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(document);

        final ArangoCollectionAsync collection = getCollection(collectionName);
        collection.insertDocument(document).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void insertDocument(String collectionName, T document, DocumentCreateOptions options,
                               Handler<AsyncResult<DocumentCreateEntity<T>>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(document);
        Objects.requireNonNull(options);

        final ArangoCollectionAsync collection = getCollection(collectionName);
        collection.insertDocument(document, options).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void insertDocuments(String collectionName, Collection<T> values,
                                Handler<AsyncResult<MultiDocumentEntity<DocumentCreateEntity<T>>>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(values);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.insertDocuments(values).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void importDocuments(String collectionName, Collection<T> values,
                                Handler<AsyncResult<DocumentImportEntity>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(values);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.importDocuments(values).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void importDocuments(String collectionName, Collection<T> values, DocumentImportOptions options,
                                Handler<AsyncResult<DocumentImportEntity>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(values);
        Objects.requireNonNull(options);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.importDocuments(values, options).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void getDocument(String collectionName, String key, Class<T> type, Handler<AsyncResult<T>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(key);
        Objects.requireNonNull(type);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.getDocument(key, type).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void getDocument(String collectionName, String key, Class<T> type, DocumentReadOptions options,
                            Handler<AsyncResult<T>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(key);
        Objects.requireNonNull(type);
        Objects.requireNonNull(options);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.getDocument(key, type, options).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void getDocuments(String collectionName, Collection<String> keys, Class<T> type,
                             Handler<AsyncResult<MultiDocumentEntity<T>>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(keys);
        Objects.requireNonNull(type);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.getDocuments(keys, type).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void getDocuments(String collectionName, Collection<String> keys, Class<T> type, DocumentReadOptions options,
                             Handler<AsyncResult<MultiDocumentEntity<T>>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(keys);
        Objects.requireNonNull(type);
        Objects.requireNonNull(options);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.getDocuments(keys, type, options).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void replaceDocument(String collectionName, String key, T value,
                                Handler<AsyncResult<DocumentUpdateEntity<T>>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.replaceDocument(key, value).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void replaceDocument(String collectionName, String key, T value, DocumentReplaceOptions options,
                                Handler<AsyncResult<DocumentUpdateEntity>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        Objects.requireNonNull(options);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync
                .replaceDocument(key, value, options)
                .whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void replaceDocuments(String collectionName, Collection<T> values,
                                 Handler<AsyncResult<MultiDocumentEntity<DocumentUpdateEntity<T>>>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(values);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.replaceDocuments(values).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void replaceDocuments(String collectionName, Collection<T> values, DocumentReplaceOptions options,
                                 Handler<AsyncResult<MultiDocumentEntity<DocumentUpdateEntity<T>>>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(values);
        Objects.requireNonNull(options);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.replaceDocuments(values, options).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void updateDocument(String collectionName, String key, T value,
                               Handler<AsyncResult<DocumentUpdateEntity<T>>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.updateDocument(key, value).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void updateDocument(String collectionName, String key, T value, DocumentUpdateOptions options,
                               Handler<AsyncResult<DocumentUpdateEntity<T>>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        Objects.requireNonNull(options);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.updateDocument(key, value, options).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void updateDocuments(String collectionName, Collection<T> values,
                                Handler<AsyncResult<MultiDocumentEntity<DocumentUpdateEntity<T>>>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(values);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.updateDocuments(values).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void updateDocuments(String collectionName, Collection<T> values, DocumentUpdateOptions options,
                                Handler<AsyncResult<MultiDocumentEntity<DocumentUpdateEntity<T>>>> resultHandler) {
        Objects.requireNonNull(values);
        Objects.requireNonNull(options);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.updateDocuments(values, options).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void deleteDocument(String collectionName, String key,
                               Handler<AsyncResult<DocumentDeleteEntity<Void>>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(key);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.deleteDocument(key).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void deleteDocument(String collectionName, String key, Class<T> type, DocumentDeleteOptions options,
                               Handler<AsyncResult<DocumentDeleteEntity<T>>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(key);
        Objects.requireNonNull(type);
        Objects.requireNonNull(options);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.deleteDocument(key, type, options).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void deleteDocuments(String collectionName, Collection<?> values,
                                Handler<AsyncResult<MultiDocumentEntity<DocumentDeleteEntity<Void>>>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(values);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.deleteDocuments(values).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void deleteDocuments(String collectionName, Collection<?> values, Class<T> type,
                                DocumentDeleteOptions options,
                                Handler<AsyncResult<MultiDocumentEntity<DocumentDeleteEntity<T>>>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(values);
        Objects.requireNonNull(type);
        Objects.requireNonNull(options);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync
                .deleteDocuments(values, type, options)
                .whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void documentExists(String collectionName, String key, Handler<AsyncResult<Boolean>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(key);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.documentExists(key).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void documentExists(String collectionName, String key, DocumentExistsOptions options,
                               Handler<AsyncResult<Boolean>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(key);
        Objects.requireNonNull(options);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.documentExists(key, options).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void getIndex(String collectionName, String id, Handler<AsyncResult<IndexEntity>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(id);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.getIndex(id).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void deleteIndex(String collectionName, String id, Handler<AsyncResult<String>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(id);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.deleteIndex(id).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void ensureHashIndex(String collectionName, Iterable<String> fields, HashIndexOptions options,
                                Handler<AsyncResult<IndexEntity>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(fields);
        Objects.requireNonNull(options);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.ensureHashIndex(fields, options).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void ensureSkiplistIndex(String collectionName, Iterable<String> fields, SkiplistIndexOptions options,
                                    Handler<AsyncResult<IndexEntity>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(fields);
        Objects.requireNonNull(options);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync
                .ensureSkiplistIndex(fields, options)
                .whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void ensurePersistentIndex(String collectionName, Iterable<String> fields, PersistentIndexOptions options,
                                      Handler<AsyncResult<IndexEntity>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(fields);
        Objects.requireNonNull(options);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync
                .ensurePersistentIndex(fields, options)
                .whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void ensureGeoIndex(String collectionName, Iterable<String> fields, GeoIndexOptions options,
                               Handler<AsyncResult<IndexEntity>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(fields);
        Objects.requireNonNull(options);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.ensureGeoIndex(fields, options).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void ensureFulltextIndex(String collectionName, Iterable<String> fields, FulltextIndexOptions options,
                                    Handler<AsyncResult<IndexEntity>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(fields);
        Objects.requireNonNull(options);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync
                .ensureFulltextIndex(fields, options)
                .whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void getIndexes(String collectionName, Handler<AsyncResult<Collection<IndexEntity>>> resultHandler) {
        Objects.requireNonNull(collectionName);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.getIndexes().whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void exists(String collectionName, Handler<AsyncResult<Boolean>> resultHandler) {
        Objects.requireNonNull(collectionName);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.exists().whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void truncate(String collectionName, Handler<AsyncResult<CollectionEntity>> resultHandler) {
        Objects.requireNonNull(collectionName);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.truncate().whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void count(String collectionName, Handler<AsyncResult<CollectionPropertiesEntity>> resultHandler) {
        Objects.requireNonNull(collectionName);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.count().whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void create(String collectionName, Handler<AsyncResult<CollectionEntity>> resultHandler) {
        Objects.requireNonNull(collectionName);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.create().whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void create(String collectionName, CollectionCreateOptions options,
                       Handler<AsyncResult<CollectionEntity>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(options);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.create(options).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void drop(String collectionName, Handler<AsyncResult<Void>> resultHandler) {
        Objects.requireNonNull(collectionName);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.drop().whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void drop(String collectionName, boolean isSystem, Handler<AsyncResult<Void>> resultHandler) {
        Objects.requireNonNull(collectionName);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.drop(isSystem).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void load(String collectionName, Handler<AsyncResult<CollectionEntity>> resultHandler) {
        Objects.requireNonNull(collectionName);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.load().whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void unload(String collectionName, Handler<AsyncResult<CollectionEntity>> resultHandler) {
        Objects.requireNonNull(collectionName);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.unload().whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void getInfo(String collectionName, Handler<AsyncResult<CollectionEntity>> resultHandler) {
        Objects.requireNonNull(collectionName);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.getInfo().whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void getProperties(String collectionName, Handler<AsyncResult<CollectionPropertiesEntity>> resultHandler) {
        Objects.requireNonNull(collectionName);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.getProperties().whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void changeProperties(String collectionName, CollectionPropertiesOptions options,
                                 Handler<AsyncResult<CollectionPropertiesEntity>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(options);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.changeProperties(options).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void rename(String collectionName, String newName, Handler<AsyncResult<CollectionEntity>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(newName);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.rename(newName).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void getRevision(String collectionName, Handler<AsyncResult<CollectionRevisionEntity>> resultHandler) {
        Objects.requireNonNull(collectionName);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.getRevision().whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void grantAccess(String collectionName, String user, Permissions permissions,
                            Handler<AsyncResult<Void>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(user);
        Objects.requireNonNull(permissions);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.grantAccess(user, permissions).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void revokeAccess(String collectionName, String user, Handler<AsyncResult<Void>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(user);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.revokeAccess(user).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }

    @Override
    public void resetAccess(String collectionName, String user, Handler<AsyncResult<Void>> resultHandler) {
        Objects.requireNonNull(collectionName);
        Objects.requireNonNull(user);

        final ArangoCollectionAsync collectionAsync = getCollection(collectionName);
        collectionAsync.resetAccess(user).whenCompleteAsync(convertCallBack(resultHandler, wr -> wr));
    }
}
