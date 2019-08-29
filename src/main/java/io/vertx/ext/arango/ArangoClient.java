package io.vertx.ext.arango;

import com.arangodb.ArangoDatabaseAsync;
import com.arangodb.entity.DocumentCreateEntity;
import com.arangodb.entity.DocumentUpdateEntity;
import com.arangodb.entity.DocumentDeleteEntity;
import com.arangodb.entity.DocumentImportEntity;
import com.arangodb.entity.Permissions;
import com.arangodb.entity.CollectionPropertiesEntity;
import com.arangodb.entity.CollectionRevisionEntity;
import com.arangodb.entity.CollectionEntity;
import com.arangodb.entity.IndexEntity;
import com.arangodb.entity.MultiDocumentEntity;
import com.arangodb.model.DocumentCreateOptions;
import com.arangodb.model.DocumentDeleteOptions;
import com.arangodb.model.DocumentReadOptions;
import com.arangodb.model.DocumentReplaceOptions;
import com.arangodb.model.DocumentUpdateOptions;
import com.arangodb.model.DocumentExistsOptions;
import com.arangodb.model.DocumentImportOptions;
import com.arangodb.model.GeoIndexOptions;
import com.arangodb.model.SkiplistIndexOptions;
import com.arangodb.model.HashIndexOptions;
import com.arangodb.model.FulltextIndexOptions;
import com.arangodb.model.PersistentIndexOptions;
import com.arangodb.model.CollectionCreateOptions;
import com.arangodb.model.CollectionPropertiesOptions;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Closeable;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.arango.impl.ArangoClientImpl;
import io.vertx.ext.arango.impl.config.ArangoClientSettingsParser;

import java.util.Collection;

/**
 * @param <T> - the type of documents to work with
 * @author kostya05983
 */
public interface ArangoClient<T> extends Closeable {

    static ArangoClient createNonShared(Vertx vertx, JsonObject arangoBuilderDecorator, String dataSourceName) {
        final ArangoClientSettingsParser parser = new ArangoClientSettingsParser();

        return new ArangoClientImpl(vertx, parser.parse(arangoBuilderDecorator).build(), dataSourceName);
    }

    public ArangoDatabaseAsync db(String collection);

    public String name();

    /**
     * Creates a new document from the given document, unless there is already a document with the _key given. If no
     * _key is given, a new unique _key is generated automatically.
     *
     * @param document A representation of a single document (POJO, VPackSlice or String for Json)
     * @return information about the document
     */
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
                                Handler<AsyncResult<MultiDocumentEntity<DocumentUpdateEntity<T>>>> resultHandler);

    public void updateDocuments(String collectionName, Collection<T> values, DocumentUpdateOptions options,
                                Handler<AsyncResult<MultiDocumentEntity<DocumentUpdateEntity<T>>>> resultHandler);

    public void deleteDocument(String collectionName, String key, Handler<AsyncResult<DocumentDeleteEntity<Void>>> resultHandler);

    public void deleteDocument(String collectionName, String key, Class<T> type, DocumentDeleteOptions options,
                               Handler<AsyncResult<DocumentDeleteEntity<T>>> resultHandler);

    public void deleteDocuments(String collectionName, Collection<?> values,
                                Handler<AsyncResult<MultiDocumentEntity<DocumentDeleteEntity<Void>>>> resultHandler);

    public void deleteDocuments(String collectionName, Collection<?> values, Class<T> type,
                                DocumentDeleteOptions options,
                                Handler<AsyncResult<MultiDocumentEntity<DocumentDeleteEntity<T>>>> resultHandler);

    public void documentExists(String collectionName, String key, Handler<AsyncResult<Boolean>> resultHandler);

    public void documentExists(String collectionName, String key, DocumentExistsOptions options,
                               Handler<AsyncResult<Boolean>> resultHandler);

    public void getIndex(String collectionName, String id, Handler<AsyncResult<IndexEntity>> resultHandler);

    public void deleteIndex(String collectionName, String id, Handler<AsyncResult<String>> resultHandler);

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

    public void getIndexes(String collectionName, Handler<AsyncResult<Collection<IndexEntity>>> resultHandler);

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


    public void changeProperties(String collectionName, CollectionPropertiesOptions options,
                                 Handler<AsyncResult<CollectionPropertiesEntity>> resultHandler);


    public void rename(String collectionName, String newName, Handler<AsyncResult<CollectionEntity>> resultHandler);


    public void getRevision(String collectionName, Handler<AsyncResult<CollectionRevisionEntity>> resultHandler);


    public void grantAccess(String collectionName, String user, Permissions permissions,
                            Handler<AsyncResult<Void>> resultHandler);


    public void revokeAccess(String collectionName, String user, Handler<AsyncResult<Void>> resultHandler);


    public void resetAccess(String collectionName, String user, Handler<AsyncResult<Void>> resultHandler);
}
