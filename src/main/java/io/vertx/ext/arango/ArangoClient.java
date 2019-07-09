package io.vertx.ext.arango;

import com.arangodb.ArangoDatabase;
import com.arangodb.entity.*;
import com.arangodb.model.*;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.arango.impl.ArangoClientImpl;

import java.util.*;

public interface ArangoClient<T> {

    static ArangoClient createNonShared(Vertx vertx, JsonObject config, String dataSourceName) {
        return new ArangoClientImpl(vertx, config, dataSourceName);
    }

    public ArangoDatabase db();

    public String name();

    @Fluent
    public void insertDocument(String collectionName, T document,
                               Handler<AsyncResult<DocumentCreateEntity<T>>> resultHandler);

    @Fluent
    public void insertDocument(String collectionName, T document, DocumentCreateOptions options,
                               Handler<AsyncResult<DocumentCreateEntity<T>>> resultHandler);


    @Fluent
    public void insertDocuments(String collectionName, Collection<T> values,
                                Handler<AsyncResult<MultiDocumentEntity<DocumentCreateEntity<T>>>> resultHandler);

    @Fluent
    public void importDocuments(String collectionName, Collection<T> values,
                                Handler<AsyncResult<DocumentImportEntity>> resultHandler);

    @Fluent
    public void importDocuments(String collectionName, Collection<T> values, DocumentImportOptions options,
                                Handler<AsyncResult<DocumentImportEntity>> resultHandler);

    @Fluent
    public void getDocument(String collectionName, String key, Class<T> type, Handler<AsyncResult<T>> resultHandler);

    @Fluent
    public void getDocument(String collectionName, String key, Class<T> type, DocumentReadOptions options,
                            Handler<AsyncResult<T>> resultHandler);

    @Fluent
    public void getDocuments(String collectionName, Collection<String> keys, Class<T> type,
                             Handler<AsyncResult<MultiDocumentEntity<T>>> resultHandler);

    @Fluent
    public void getDocuments(String collectionName, Collection<String> keys, Class<T> type, DocumentReadOptions options,
                             Handler<AsyncResult<MultiDocumentEntity<T>>> resultHandler);

    @Fluent
    public void replaceDocument(String collectionName, String key, T value,
                                Handler<AsyncResult<DocumentUpdateEntity<T>>> resultHandler);

    @Fluent
    public void replaceDocument(String collectionName, String key, T value, DocumentReplaceOptions options,
                                Handler<AsyncResult<DocumentUpdateEntity>> resultHandler);

    @Fluent
    public void replaceDocuments(String collectionName, Collection<T> values,
                                 Handler<AsyncResult<MultiDocumentEntity<DocumentUpdateEntity>>> resultHandler);

    @Fluent
    public void replaceDocuments(String collectionName, Collection<T> values, DocumentReplaceOptions options,
                                 Handler<AsyncResult<MultiDocumentEntity<DocumentUpdateEntity<T>>>> resultHandler);

    @Fluent
    public void updateDocument(String collectionName, String key, T value,
                               Handler<AsyncResult<DocumentUpdateEntity<T>>> resultHandler);

    @Fluent
    public void updateDocument(String collectionName, String key, T value, DocumentUpdateOptions options,
                               Handler<AsyncResult<DocumentUpdateEntity<T>>> resultHandler);

    @Fluent
    public void updateDocuments(String collectionName, Collection<T> values,
                                Handler<AsyncResult<MultiDocumentEntity<DocumentUpdateEntity>>> resultHandler);

    @Fluent
    public void updateDocuments(String collectionName, Collection<T> values, DocumentUpdateOptions options,
                                Handler<AsyncResult<MultiDocumentEntity<DocumentUpdateEntity<T>>>> resultHandler);

    @Fluent
    public void deleteDocument(String collectionName, String key, Handler<AsyncResult<Void>> resultHandler);

    @Fluent
    public void deleteDocument(String collectionName, String key, Class<T> type, DocumentDeleteOptions options,
                               Handler<AsyncResult<DocumentDeleteEntity<T>>> resultHandler);

    @Fluent
    public void deleteDocuments(String collectionName, Collection<?> values,
                                Handler<MultiDocumentEntity<DocumentDeleteEntity<Void>>> resultHandler);

    @Fluent
    public void deleteDocuments(String collectionName, Collection<?> values, Class<T> type,
                                DocumentDeleteOptions options,
                                Handler<MultiDocumentEntity<DocumentDeleteEntity<T>>> resultHandler);

    @Fluent
    public void documentExists(String collectionName, String key, Handler<AsyncResult<Boolean>> resultHandler);

    @Fluent
    public void documentExists(String collectionName, String key, DocumentExistsOptions options,
                               Handler<AsyncResult<Boolean>> resultHandler);

    @Fluent
    public void getIndex(String collectionName, String id, Handler<AsyncResult<IndexEntity>> resultHandler);

    @Fluent
    public void deleteIndex(String collectionName, String id, Handler<AsyncResult<IndexEntity>> resultHandler);

    @Fluent
    public void ensureHashIndex(String collectionName, Iterable<String> fields, HashIndexOptions options,
                                Handler<AsyncResult<IndexEntity>> resultHandler);

    @Fluent
    public void ensureSkiplistIndex(String collectionName, Iterable<String> fields, SkiplistIndexOptions options,
                                    Handler<AsyncResult<IndexEntity>> resultHandler);

    @Fluent
    public void ensurePersistentIndex(String collectionName, Iterable<String> fields, PersistentIndexOptions options,
                                      Handler<AsyncResult<IndexEntity>> resultHandler);

    @Fluent
    public void ensureGeoIndex(String collectionName, Iterable<String> fields, GeoIndexOptions options,
                               Handler<AsyncResult<IndexEntity>> resultHandler);

    @Fluent
    public void ensureFulltextIndex(String collectionName, Iterable<String> fields, FulltextIndexOptions options,
                                    Handler<AsyncResult<IndexEntity>> resultHandler);

    @Fluent
    public void getIndexes(String collectionName, Handler<AsyncResult<IndexEntity>> resultHandler);

    @Fluent
    public void exists(String collectionName, Handler<AsyncResult<Boolean>> resultHandler);

    @Fluent
    public void truncate(String collectionName, Handler<AsyncResult<CollectionEntity>> resultHandler);

    @Fluent
    public void count(String collectionName, Handler<AsyncResult<CollectionPropertiesEntity>> resultHandler);

    @Fluent
    public void create(String collectionName, Handler<AsyncResult<CollectionEntity>> resultHandler);

    @Fluent
    public void create(String collectionName, CollectionCreateOptions options,
                       Handler<AsyncResult<CollectionEntity>> resultHandler);

    @Fluent
    public void drop(String collectionName, Handler<AsyncResult<Void>> resultHandler);

    @Fluent
    public void drop(String collectionName, boolean isSystem, Handler<AsyncResult<Void>> resultHandler);

    @Fluent
    public void load(String collectionName, Handler<AsyncResult<CollectionEntity>> resultHandler);

    @Fluent
    public void unload(String collectionName, Handler<AsyncResult<CollectionEntity>> resultHandler);

    @Fluent
    public void getInfo(String collectionName, Handler<AsyncResult<CollectionEntity>> resultHandler);

    @Fluent
    public void getProperties(String collectionName, Handler<AsyncResult<CollectionPropertiesEntity>> resultHandler);

    @Fluent
    public void changeProperties(String collectionName, CollectionPropertiesEntity options,
                                 Handler<AsyncResult<CollectionPropertiesEntity>> resultHandler);

    @Fluent
    public void rename(String collectionName, String newName, Handler<AsyncResult<CollectionEntity>> resultHandler);

    @Fluent
    public void getRevision(String collectionName, Handler<AsyncResult<CollectionRevisionEntity>> resultHandler);

    @Fluent
    public void grantAccess(String collectionName, String user, Permissions permissions,
                            Handler<AsyncResult<Void>> resultHandler);

    @Fluent
    public void revokeAccess(String collectionName, String user, Handler<AsyncResult<Void>> resultHandler);

    @Fluent
    public void resetAccess(String collectionName, String user, Handler<AsyncResult<Void>> resultHandler);
}
