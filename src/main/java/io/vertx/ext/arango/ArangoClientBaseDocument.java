package io.vertx.ext.arango;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;


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
}
