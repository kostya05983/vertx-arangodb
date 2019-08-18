package io.vertx.ext.arango;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBAsync;
import com.arangodb.ArangoDatabaseAsync;
import com.arangodb.entity.*;
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
import com.arangodb.util.ArangoDeserializer;
import com.arangodb.util.ArangoSerialization;
import com.arangodb.velocypack.*;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.arango.impl.config.ArangoClientSettingsParser;

import javax.net.ssl.SSLContext;
import java.lang.annotation.Annotation;
import java.util.Collection;

public interface ArangoClient<T> {

    static ArangoBuilderDecorator createNonShared(Vertx vertx, JsonObject config, String dataSouceName) {
        return null;
    }


    public static class ArangoBuilderDecorator {
        private final ArangoDBAsync.Builder builder;

        public ArangoBuilderDecorator(Vertx vertx, JsonObject config, String dataSourceName) {
            final ArangoClientSettingsParser parser = new ArangoClientSettingsParser();
            builder = parser.parse(config);
        }

        ArangoBuilderDecorator sshContext(final SSLContext sslContext) {
            builder.sslContext(sslContext);
            return this;
        }

        ArangoBuilderDecorator loadBalancingStrategy(final LoadBalancingStrategy loadBalancingStrategy) {
            builder.loadBalancingStrategy(loadBalancingStrategy);
            return this;
        }

        <T> ArangoBuilderDecorator registerSerializer(final Class<T> clazz, final VPackSerializer<T> serializer) {
            builder.registerSerializer(clazz, serializer);
            return this;
        }

        <T> ArangoBuilderDecorator registerEnclosingSerializer(final Class<T> clazz, final VPackSerializer<T> serializer) {
            builder.registerEnclosingSerializer(clazz, serializer);
            return this;
        }

        <T> ArangoBuilderDecorator registerDeserializer(final Class<T> clazz, final VPackDeserializer<T> deserializer) {
            builder.registerDeserializer(clazz, deserializer);
            return this;
        }

        <T> ArangoBuilderDecorator registerInstanceCreator(final Class<T> clazz, final VPackInstanceCreator<T> creator) {
            builder.registerInstanceCreator(clazz, creator);
            return this;
        }

        ArangoBuilderDecorator registerJsonDeserializer(final ValueType type, final VPackJsonDeserializer deserializer) {
            builder.registerJsonDeserializer(type, deserializer);
            return this;
        }

        ArangoBuilderDecorator registerJsonDeserializer(final String atytribute, final ValueType type, final VPackJsonDeserializer deserializer) {
            builder.registerJsonDeserializer(atytribute, type, deserializer);
            return this;
        }

        <T> ArangoBuilderDecorator registerJsonSerializer(final Class<T> clazz, final VPackJsonSerializer<T> serializer) {
            builder.registerJsonSerializer(clazz, serializer);
            return this;
        }

        <A extends Annotation> ArangoBuilderDecorator annotationFieldFilter(final Class<A> type, final VPackAnnotationFieldFilter<A> fieldFilter) {
            builder.annotationFieldFilter(type, fieldFilter);
            return this;
        }

        <A extends Annotation> ArangoBuilderDecorator annotationFieldNaming(final Class<A> type, final VPackAnnotationFieldNaming<A> fieldNaming) {
            builder.annotationFieldNaming(type, fieldNaming);
            return this;
        }

        ArangoBuilderDecorator registerModule(final VPackModule module) {
            builder.registerModule(module);
            return this;
        }

        ArangoBuilderDecorator registerModules(final VPackModule... modules) {
            builder.registerModules(modules);
            return this;
        }

        ArangoBuilderDecorator registerJsonModule(final VPackParserModule module) {
            builder.registerJsonModule(module);
            return this;
        }

        ArangoBuilderDecorator registerJsonModules(final VPackParserModule... modules) {
            builder.registerJsonModules(modules);
            return this;
        }

        ArangoBuilderDecorator serializer(final ArangoSerialization serialization) {
            builder.serializer(serialization);
            return this;
        }

        ArangoBuilderDecorator deserializer(final ArangoDeserializer deserializer) {
            builder.setDeserializer(deserializer);
            return this;
        }
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
