package io.vertx.ext.arango;

import com.arangodb.ArangoDatabaseAsync;
import com.arangodb.entity.*;
import com.arangodb.model.*;
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

    public ArangoDatabaseAsync db(String collection);

    public String name();

    public void insertDocument(String collectionName, T document,
                               Handler<AsyncResult<DocumentCreateEntity<T>>> resultHandler);
    public void insertDocument(String collectionName, T document, DocumentCreateOptions options,
                               Handler<AsyncResult<DocumentCreateEntity<T>>> resultHandler);

    public void insertDocuments(String collectionName, Collection<T> values,
                                Handler<AsyncResult<MultiDocumentEntity<DocumentCreateEntity<T>>>> resultHandler);

    public void importDocuments(String collectionName, Collection<T> values,
                                Handler<AsyncResult<DocumentImportEntity>> resultHandler);

    public void importDocuments(String collectionName, Collection<T> values, DocumentImportOptions options,
                                Handler<AsyncResult<DocumentImportEntity>> resultHandler);

    public void getDocument(String collectionName, String key, Class<T> type, Handler<AsyncResult<T>> resultHandler);

    public void getDocument(String collectionName, String key, Class<T> type, DocumentReadOptions options,
                            Handler<AsyncResult<T>> resultHandler);

    public void getDocuments(String collectionName, Collection<String> keys, Class<T> type,
                             Handler<AsyncResult<MultiDocumentEntity<T>>> resultHandler);

    public void getDocuments(String collectionName, Collection<String> keys, Class<T> type, DocumentReadOptions options,
                             Handler<AsyncResult<MultiDocumentEntity<T>>> resultHandler);

    public void replaceDocument(String collectionName, String key, T value,
                                Handler<AsyncResult<DocumentUpdateEntity<T>>> resultHandler);

    public void replaceDocument(String collectionName, String key, T value, DocumentReplaceOptions options,
                                Handler<AsyncResult<DocumentUpdateEntity>> resultHandler);

    public void replaceDocuments(String collectionName, Collection<T> values,
                                 Handler<AsyncResult<MultiDocumentEntity<DocumentUpdateEntity<T>>>> resultHandler);

    public void replaceDocuments(String collectionName, Collection<T> values, DocumentReplaceOptions options,
                                 Handler<AsyncResult<MultiDocumentEntity<DocumentUpdateEntity<T>>>> resultHandler);

    public void updateDocument(String collectionName, String key, T value,
                               Handler<AsyncResult<DocumentUpdateEntity<T>>> resultHandler);

    public void updateDocument(String collectionName, String key, T value, DocumentUpdateOptions options,
                               Handler<AsyncResult<DocumentUpdateEntity<T>>> resultHandler);

    public void updateDocuments(String collectionName, Collection<T> values,
                                Handler<AsyncResult<MultiDocumentEntity<DocumentUpdateEntity>>> resultHandler);

    public void updateDocuments(String collectionName, Collection<T> values, DocumentUpdateOptions options,
                                Handler<AsyncResult<MultiDocumentEntity<DocumentUpdateEntity<T>>>> resultHandler);

    public void deleteDocument(String collectionName, String key, Handler<AsyncResult<Void>> resultHandler);

    public void deleteDocument(String collectionName, String key, Class<T> type, DocumentDeleteOptions options,
                               Handler<AsyncResult<DocumentDeleteEntity<T>>> resultHandler);

    public void deleteDocuments(String collectionName, Collection<?> values,
                                Handler<MultiDocumentEntity<DocumentDeleteEntity<Void>>> resultHandler);

    public void deleteDocuments(String collectionName, Collection<?> values, Class<T> type,
                                DocumentDeleteOptions options,
                                Handler<MultiDocumentEntity<DocumentDeleteEntity<T>>> resultHandler);

    public void documentExists(String collectionName, String key, Handler<AsyncResult<Boolean>> resultHandler);

    public void documentExists(String collectionName, String key, DocumentExistsOptions options,
                               Handler<AsyncResult<Boolean>> resultHandler);

    public void getIndex(String collectionName, String id, Handler<AsyncResult<IndexEntity>> resultHandler);

    public void deleteIndex(String collectionName, String id, Handler<AsyncResult<IndexEntity>> resultHandler);

    public void ensureHashIndex(String collectionName, Iterable<String> fields, HashIndexOptions options,
                                Handler<AsyncResult<IndexEntity>> resultHandler);

    public void ensureSkiplistIndex(String collectionName, Iterable<String> fields, SkiplistIndexOptions options,
                                    Handler<AsyncResult<IndexEntity>> resultHandler);

    public void ensurePersistentIndex(String collectionName, Iterable<String> fields, PersistentIndexOptions options,
                                      Handler<AsyncResult<IndexEntity>> resultHandler);

    public void ensureGeoIndex(String collectionName, Iterable<String> fields, GeoIndexOptions options,
                               Handler<AsyncResult<IndexEntity>> resultHandler);

    public void ensureFulltextIndex(String collectionName, Iterable<String> fields, FulltextIndexOptions options,
                                    Handler<AsyncResult<IndexEntity>> resultHandler);

    public void getIndexes(String collectionName, Handler<AsyncResult<IndexEntity>> resultHandler);

    public void exists(String collectionName, Handler<AsyncResult<Boolean>> resultHandler);

    public void truncate(String collectionName, Handler<AsyncResult<CollectionEntity>> resultHandler);

    public void count(String collectionName, Handler<AsyncResult<CollectionPropertiesEntity>> resultHandler);

    public void create(String collectionName, Handler<AsyncResult<CollectionEntity>> resultHandler);

    public void create(String collectionName, CollectionCreateOptions options,
                       Handler<AsyncResult<CollectionEntity>> resultHandler);

    
    public void drop(String collectionName, Handler<AsyncResult<Void>> resultHandler);

    
    public void drop(String collectionName, boolean isSystem, Handler<AsyncResult<Void>> resultHandler);

    
    public void load(String collectionName, Handler<AsyncResult<CollectionEntity>> resultHandler);

    
    public void unload(String collectionName, Handler<AsyncResult<CollectionEntity>> resultHandler);

    
    public void getInfo(String collectionName, Handler<AsyncResult<CollectionEntity>> resultHandler);

    
    public void getProperties(String collectionName, Handler<AsyncResult<CollectionPropertiesEntity>> resultHandler);

    
    public void changeProperties(String collectionName, CollectionPropertiesEntity options,
                                 Handler<AsyncResult<CollectionPropertiesEntity>> resultHandler);

    
    public void rename(String collectionName, String newName, Handler<AsyncResult<CollectionEntity>> resultHandler);

    
    public void getRevision(String collectionName, Handler<AsyncResult<CollectionRevisionEntity>> resultHandler);

    
    public void grantAccess(String collectionName, String user, Permissions permissions,
                            Handler<AsyncResult<Void>> resultHandler);

    
    public void revokeAccess(String collectionName, String user, Handler<AsyncResult<Void>> resultHandler);

    
    public void resetAccess(String collectionName, String user, Handler<AsyncResult<Void>> resultHandler);
}
