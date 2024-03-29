package io.vertx.ext.arango;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.ext.arango.dataobjects.VertxDocumentCreateEntity;
import io.vertx.ext.arango.dataobjects.VertxDocumentCreateOptions;
import io.vertx.ext.arango.dataobjects.VertxDocumentImportEntity;
import io.vertx.ext.arango.dataobjects.VertxDocumentImportOptions;
import io.vertx.ext.arango.dataobjects.VertxDocumentReadOptions;
import io.vertx.ext.arango.dataobjects.VertxDocumentReplaceOptions;
import io.vertx.ext.arango.dataobjects.VertxDocumentUpdateEntity;
import io.vertx.ext.arango.dataobjects.VertxMultiDocumentEntity;
import io.vertx.ext.arango.dataobjects.VertxMultiUpdateEntity;


@VertxGen
public interface ArangoClientBaseDocument {

    public void insertDocument(String collectionName, VertxBaseDocument baseDocument,
                               Handler<AsyncResult<VertxDocumentCreateEntity>> resultHandler);

    public void insertDocument(String collectionName, VertxBaseDocument baseDocument, VertxDocumentCreateOptions createOptions,
                               Handler<AsyncResult<VertxDocumentCreateEntity>> resultHandler);

    public void insertDocuments(String collectionName, VertxCollection values,
                                Handler<AsyncResult<VertxMultiDocumentEntity>> resultHandler);

    public void importDocuments(String collectionName, VertxCollection values,
                                Handler<AsyncResult<VertxDocumentImportEntity>> resultHandler);

    public void importDocuments(String collectionName, VertxCollection values, VertxDocumentImportOptions options,
                                Handler<AsyncResult<VertxDocumentImportEntity>> resultHandler);

    public void getDocument(String collectionName, String key, Handler<VertxBaseDocument> resultHandler);

    public void getDocument(String collectionName, String key, VertxDocumentReadOptions options,
                            Handler<VertxBaseDocument> resultHandler);

    public void getDocuments(String collectionName, String key,
                             Handler<AsyncResult<VertxMultiDocumentEntity>> resultHandler);

    public void getDocuments(String collectionName, String key, VertxDocumentReadOptions options,
                             Handler<AsyncResult<VertxMultiDocumentEntity>> resultHandler);

    public void replaceDocument(String collectionName, String key, VertxBaseDocument document,
                                Handler<AsyncResult<VertxDocumentUpdateEntity>> resultHandler);

    public void replaceDocument(String collectionName, String key, VertxBaseDocument document,
                                VertxDocumentReplaceOptions options,
                                Handler<AsyncResult<VertxDocumentUpdateEntity>> resultHandler);

    public void replaceDocuments(String collectionName, VertxCollection collection,
                                 Handler<AsyncResult<VertxMultiUpdateEntity>> resultHandler);

    public void replaceDocuments(String collectionName, VertxCollection collection, VertxDocumentReplaceOptions
            options, Handler<AsyncResult<VertxMultiUpdateEntity>> resultHandler);
}
