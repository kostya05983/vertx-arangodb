package io.vertx.ext.arango;

import com.arangodb.ArangoDBAsync;
import com.arangodb.entity.LoadBalancingStrategy;
import com.arangodb.util.ArangoDeserializer;
import com.arangodb.util.ArangoSerialization;
import com.arangodb.velocypack.VPackAnnotationFieldFilter;
import com.arangodb.velocypack.VPackAnnotationFieldNaming;
import com.arangodb.velocypack.VPackDeserializer;
import com.arangodb.velocypack.VPackInstanceCreator;
import com.arangodb.velocypack.VPackJsonDeserializer;
import com.arangodb.velocypack.VPackJsonSerializer;
import com.arangodb.velocypack.VPackModule;
import com.arangodb.velocypack.VPackParserModule;
import com.arangodb.velocypack.VPackSerializer;
import com.arangodb.velocypack.ValueType;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.arango.impl.config.ArangoClientSettingsParser;

import javax.net.ssl.SSLContext;
import java.lang.annotation.Annotation;

public class ArangoBuilderDecorator {

    private final ArangoDBAsync.Builder builder;

    ArangoBuilderDecorator(JsonObject config) {
        final ArangoClientSettingsParser parser = new ArangoClientSettingsParser();
        builder = parser.parse(config);
    }

    /**
     * Sets the SSL context to be used.
     *
     * @param sslContext SSL context to be used
     * @return {@link ArangoBuilderDecorator}
     */
    ArangoBuilderDecorator sslContext(final SSLContext sslContext) {
        builder.sslContext(sslContext);
        return this;
    }

    /**
     * Sets the load balancing strategy to be used in an ArangoDB cluster setup.
     *
     * @param loadBalancingStrategy the load balancing strategy to be used (default: {@link LoadBalancingStrategy#NONE}
     * @return {@link ArangoBuilderDecorator}
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
     * @return {@link ArangoBuilderDecorator}
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
     * @return {@link ArangoBuilderDecorator}
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
     * @return {@link ArangoBuilderDecorator}
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
     * @return {@link ArangoBuilderDecorator}
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
     * @return {@link ArangoBuilderDecorator}
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
     * @return {@link ArangoBuilderDecorator}
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
     * @param clazz      the type the serializer should be registered for
     * @param serializer
     * @return {@link ArangoBuilderDecorator}
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
     * @return {@link ArangoBuilderDecorator}
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
     * @return {@link ArangoBuilderDecorator}
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
     * @return {@link ArangoBuilderDecorator}
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
     * @return {@link ArangoBuilderDecorator}
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
     * @return {@link ArangoBuilderDecorator}
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
     * @return {@link ArangoBuilderDecorator}
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
     * @return {@link ArangoBuilderDecorator}
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
     * @return {@link ArangoBuilderDecorator}
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
     * @return {@link ArangoBuilderDecorator}
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
