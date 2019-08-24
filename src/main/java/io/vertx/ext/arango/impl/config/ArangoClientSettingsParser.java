package io.vertx.ext.arango.impl.config;

import com.arangodb.ArangoDBAsync;
import io.vertx.core.json.JsonObject;


/**
 * @author kostya05983
 * Class is responsible for parse settings from json to arangoSettings module
 */
public class ArangoClientSettingsParser {

    public ArangoDBAsync.Builder parse(
            final JsonObject json) {
        final ArangoDBAsync.Builder builder = new ArangoDBAsync.Builder();
        final String host = json.getString("host");

        final Integer port = json.getInteger("port");
        builder.host(host, port);

        final Integer timeout = json.getInteger("timeout");
        builder.timeout(timeout);

        final String user = json.getString("user");
        builder.user(user);

        final String password = json.getString("password");
        builder.password(password);

        final Boolean useSsl = json.getBoolean("useSsl");
        builder.useSsl(useSsl);

        final Integer chunkSize = json.getInteger("chunkSize");
        builder.chunksize(chunkSize);

        final Integer maxConnections = json.getInteger("maxConnections");
        builder.maxConnections(maxConnections);

        final Long connectionTtl = json.getLong("connectionTtl");
        builder.connectionTtl(connectionTtl);

        final Boolean acquireHostList = json.getBoolean("acquireHostList");
        builder.acquireHostList(acquireHostList);

        return builder;
    }

}
