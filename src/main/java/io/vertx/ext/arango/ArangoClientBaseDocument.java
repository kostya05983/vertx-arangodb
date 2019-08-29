package io.vertx.ext.arango;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

import java.util.Collection;

@VertxGen
public interface ArangoClientBaseDocument {

    public void insertDocument(String collectionName, VertxBaseDocument baseDocument,
                               Handler<AsyncResult<DocumentInsertResult>> resultHandler);

    public void insertDocument(String collectionName, VertxBaseDocument baseDocument, VertxDocumentCreateOptions createOptions,
                               Handler<AsyncResult<DocumentInsertResult>> resultHandler);

//    public void insertDocuments(String collectionName, Collection<VertxBaseDocument> values);
}
