package io.vertx.ext.arango;

import com.arangodb.ArangoDBAsync;
import com.arangodb.ArangoDatabaseAsync;
import com.arangodb.entity.DocumentCreateEntity;
import com.arangodb.entity.DocumentUpdateEntity;
import com.arangodb.entity.DocumentDeleteEntity;
import com.arangodb.entity.DocumentImportEntity;
import com.arangodb.entity.LoadBalancingStrategy;
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
import com.arangodb.util.ArangoDeserializer;
import com.arangodb.util.ArangoSerialization;
import com.arangodb.velocypack.VPackDeserializer;
import com.arangodb.velocypack.VPackSerializer;
import com.arangodb.velocypack.VPackInstanceCreator;
import com.arangodb.velocypack.VPackJsonDeserializer;
import com.arangodb.velocypack.VPackJsonSerializer;
import com.arangodb.velocypack.ValueType;
import com.arangodb.velocypack.VPackAnnotationFieldNaming;
import com.arangodb.velocypack.VPackAnnotationFieldFilter;
import com.arangodb.velocypack.VPackModule;
import com.arangodb.velocypack.VPackParserModule;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.arango.impl.ArangoClientImpl;
import io.vertx.ext.arango.impl.config.ArangoClientSettingsParser;

import javax.net.ssl.SSLContext;
import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * @author kostya05983
 * @param <T> - the type of documents to work with
 */
public interface ArangoClient<T> {

    static ArangoClient createNonShared(Vertx vertx, ArangoBuilderDecorator arangoBuilderDecorator, String dataSouceName) {
        return new ArangoClientImpl(vertx, arangoBuilderDecorator.build(), dataSouceName);
    }

    class ArangoBuilderDecorator {
        private final ArangoDBAsync.Builder builder;

        ArangoBuilderDecorator(JsonObject config) {
            final ArangoClientSettingsParser parser = new ArangoClientSettingsParser();
            builder = parser.parse(config);
        }

        /**
         * Sets the SSL context to be used.
         *
         * @param sslContext
         *            SSL context to be used
         * @return {@link ArangoClient.ArangoBuilderDecorator}
         */
        ArangoBuilderDecorator sslContext(final SSLContext sslContext) {
            builder.sslContext(sslContext);
            return this;
        }

        /**
         * Sets the load balancing strategy to be used in an ArangoDB cluster setup.
         *
         * @param loadBalancingStrategy the load balancing strategy to be used (default: {@link LoadBalancingStrategy#NONE}
         * @return {@link ArangoClient.ArangoBuilderDecorator}
         */
        ArangoBuilderDecorator loadBalancingStrategy(final LoadBalancingStrategy loadBalancingStrategy) {
            builder.loadBalancingStrategy(loadBalancingStrategy);
            return this;
        }

        /**
         * Register a custom {@link VPackSerializer} for a specific type to be used within the internal serialization
         * process.
         *
         * <p>
         * <strong>Attention:</strong>can not be used together with {@link #serializer(ArangoSerialization)}
         * </p>
         *
         * @param clazz      the type the serializer should be registered for
         * @param serializer serializer to register
         * @return {@link ArangoClient.ArangoBuilderDecorator}
         */
        <T> ArangoBuilderDecorator registerSerializer(final Class<T> clazz, final VPackSerializer<T> serializer) {
            builder.registerSerializer(clazz, serializer);
            return this;
        }

        /**
         * Register a special serializer for a member class which can only be identified by its enclosing class.
         *
         * <p>
         * <strong>Attention:</strong>can not be used together with {@link #serializer(ArangoSerialization)}
         * </p>
         *
         * @param clazz      the type of the enclosing class
         * @param serializer serializer to register
         * @return {@link ArangoClient.ArangoBuilderDecorator}
         */
        <T> ArangoBuilderDecorator registerEnclosingSerializer(final Class<T> clazz, final VPackSerializer<T> serializer) {
            builder.registerEnclosingSerializer(clazz, serializer);
            return this;
        }

        /**
         * Register a custom {@link VPackDeserializer} for a specific type to be used within the internal serialization
         * process.
         *
         * <p>
         * <strong>Attention:</strong>can not be used together with {@link #serializer(ArangoSerialization)}
         * </p>
         *
         * @param clazz        the type the serializer should be registered for
         * @param deserializer
         * @return {@link ArangoClient.ArangoBuilderDecorator}
         */
        <T> ArangoBuilderDecorator registerDeserializer(final Class<T> clazz, final VPackDeserializer<T> deserializer) {
            builder.registerDeserializer(clazz, deserializer);
            return this;
        }

        /**
         * Register a custom {@link VPackInstanceCreator} for a specific type to be used within the internal
         * serialization process.
         *
         * <p>
         * <strong>Attention:</strong>can not be used together with {@link #serializer(ArangoSerialization)}
         * </p>
         *
         * @param clazz   the type the instance creator should be registered for
         * @param creator
         * @return {@link ArangoClient.ArangoBuilderDecorator}
         */
        <T> ArangoBuilderDecorator registerInstanceCreator(final Class<T> clazz, final VPackInstanceCreator<T> creator) {
            builder.registerInstanceCreator(clazz, creator);
            return this;
        }

        /**
         * Register a custom {@link VPackJsonDeserializer} for a specific type to be used within the internal
         * serialization process.
         *
         * <p>
         * <strong>Attention:</strong>can not be used together with {@link #serializer(ArangoSerialization)}
         * </p>
         *
         * @param type         the type the serializer should be registered for
         * @param deserializer
         * @return {@link ArangoClient.ArangoBuilderDecorator}
         */
        ArangoBuilderDecorator registerJsonDeserializer(final ValueType type, final VPackJsonDeserializer deserializer) {
            builder.registerJsonDeserializer(type, deserializer);
            return this;
        }

        /**
         * Register a custom {@link VPackJsonDeserializer} for a specific type and attribute name to be used within the
         * internal serialization process.
         *
         * <p>
         * <strong>Attention:</strong>can not be used together with {@link #serializer(ArangoSerialization)}
         * </p>
         *
         * @param attribute
         * @param type         the type the serializer should be registered for
         * @param deserializer
         * @return {@link ArangoClient.ArangoBuilderDecorator}
         */
        ArangoBuilderDecorator registerJsonDeserializer(final String attribute, final ValueType type, final VPackJsonDeserializer deserializer) {
            builder.registerJsonDeserializer(attribute, type, deserializer);
            return this;
        }

        /**
         * Register a custom {@link VPackJsonSerializer} for a specific type to be used within the internal
         * serialization process.
         *
         * <p>
         * <strong>Attention:</strong>can not be used together with {@link #serializer(ArangoSerialization)}
         * </p>
         *
         * @param clazz
         *            the type the serializer should be registered for
         * @param serializer
         * @return {@link ArangoClient.ArangoBuilderDecorator}
         */
        <T> ArangoBuilderDecorator registerJsonSerializer(final Class<T> clazz, final VPackJsonSerializer<T> serializer) {
            builder.registerJsonSerializer(clazz, serializer);
            return this;
        }

        /**
         * Register a custom {@link VPackJsonSerializer} for a specific type and attribute name to be used within the
         * internal serialization process.
         *
         * <p>
         * <strong>Attention:</strong>can not be used together with {@link #serializer(ArangoSerialization)}
         * </p>
         *
         * @param attribute
         * @param clazz      the type the serializer should be registered for
         * @param serializer
         * @return {@link ArangoClient.ArangoBuilderDecorator}
         */
        <T> ArangoBuilderDecorator registerJsonSerializer(final String attribute, final Class<T> clazz, final VPackJsonSerializer<T> serializer) {
            builder.registerJsonSerializer(attribute, clazz, serializer);
            return this;
        }

        /**
         * Register a custom {@link VPackAnnotationFieldFilter} for a specific type to be used within the internal
         * serialization process.
         *
         * <p>
         * <strong>Attention:</strong>can not be used together with {@link #serializer(ArangoSerialization)}
         * </p>
         *
         * @param type        the type the serializer should be registered for
         * @param fieldFilter
         * @return {@link ArangoClient.ArangoBuilderDecorator}
         */
        <A extends Annotation> ArangoBuilderDecorator annotationFieldFilter(final Class<A> type, final VPackAnnotationFieldFilter<A> fieldFilter) {
            builder.annotationFieldFilter(type, fieldFilter);
            return this;
        }

        /**
         * Register a custom {@link VPackAnnotationFieldNaming} for a specific type to be used within the internal
         * serialization process.
         *
         * <p>
         * <strong>Attention:</strong>can not be used together with {@link #serializer(ArangoSerialization)}
         * </p>
         *
         * @param type        the type the serializer should be registered for
         * @param fieldNaming
         * @return {@link ArangoClient.ArangoBuilderDecorator}
         */
        <A extends Annotation> ArangoBuilderDecorator annotationFieldNaming(final Class<A> type, final VPackAnnotationFieldNaming<A> fieldNaming) {
            builder.annotationFieldNaming(type, fieldNaming);
            return this;
        }

        /**
         * Register a {@link VPackModule} to be used within the internal serialization process.
         *
         * <p>
         * <strong>Attention:</strong>can not be used together with {@link #serializer(ArangoSerialization)}
         * </p>
         *
         * @param module module to register
         * @return {@link ArangoClient.ArangoBuilderDecorator}
         */
        ArangoBuilderDecorator registerModule(final VPackModule module) {
            builder.registerModule(module);
            return this;
        }

        /**
         * Register a list of {@link VPackModule} to be used within the internal serialization process.
         *
         * <p>
         * <strong>Attention:</strong>can not be used together with {@link #serializer(ArangoSerialization)}
         * </p>
         *
         * @param modules modules to register
         * @return {@link ArangoClient.ArangoBuilderDecorator}
         */
        ArangoBuilderDecorator registerModules(final VPackModule... modules) {
            builder.registerModules(modules);
            return this;
        }

        /**
         * Register a {@link VPackParserModule} to be used within the internal serialization process.
         *
         * <p>
         * <strong>Attention:</strong>can not be used together with {@link #serializer(ArangoSerialization)}
         * </p>
         *
         * @param module module to register
         * @return {@link ArangoClient.ArangoBuilderDecorator}
         */
        ArangoBuilderDecorator registerJsonModule(final VPackParserModule module) {
            builder.registerJsonModule(module);
            return this;
        }

        /**
         * Register a list of {@link VPackParserModule} to be used within the internal serialization process.
         *
         * <p>
         * <strong>Attention:</strong>can not be used together with {@link #serializer(ArangoSerialization)}
         * </p>
         *
         * @param modules modules to register
         * @return {@link ArangoClient.ArangoBuilderDecorator}
         */
        ArangoBuilderDecorator registerJsonModules(final VPackParserModule... modules) {
            builder.registerJsonModules(modules);
            return this;
        }

        /**
         * Replace the built-in serializer/deserializer with the given one.
         * <p>
         * <br />
         * <b>ATTENTION!:</b> Any registered custom serializer/deserializer or module will be ignored.
         *
         * @param serialization custom serializer/deserializer
         * @return {@link ArangoClient.ArangoBuilderDecorator}
         */
        ArangoBuilderDecorator serializer(final ArangoSerialization serialization) {
            builder.serializer(serialization);
            return this;
        }

        /**
         * Replace the built-in deserializer with the given deserializer.
         * <p>
         * <br />
         * <b>ATTENTION!:</b> Use at your own risk
         *
         * @param deserializer custom deserializer
         * @return {@link ArangoClient.ArangoBuilderDecorator}
         * @deprecated use {@link #serializer(ArangoSerialization)} instead
         */
        ArangoBuilderDecorator deserializer(final ArangoDeserializer deserializer) {
            builder.setDeserializer(deserializer);
            return this;
        }

        /**
         * Build an instance of arangoDbAsync
         *
         * @return {@link ArangoDBAsync}
         */
        ArangoDBAsync build() {
            return builder.build();
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
