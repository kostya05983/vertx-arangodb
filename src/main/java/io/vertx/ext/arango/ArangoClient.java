package io.vertx.ext.arango;

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
}
