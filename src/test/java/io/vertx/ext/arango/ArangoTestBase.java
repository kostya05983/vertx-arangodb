package io.vertx.ext.arango;

import com.arangodb.ArangoDBAsync;
import com.arangodb.ArangoDatabaseAsync;
import com.arangodb.entity.CollectionEntity;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.output.OutputFrame;
import org.testcontainers.containers.output.WaitingConsumer;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@ExtendWith(VertxExtension.class)
public class ArangoTestBase {

    ArangoClient arangoClient;
    final static String COLLECTION_NAME = "collection";

    private final static String DB_NAME = "db";

    @Rule
    public FixedHostPortGenericContainer<?> arangoContainer =
            new FixedHostPortGenericContainer<>("arangodb/arangodb:3.4.4");

    @BeforeAll
    public void setUp(Vertx vertx, VertxTestContext context) throws Exception {
        HashMap<String, String> envs = new HashMap<>();
        envs.put("ARANGO_NO_AUTH", "0");
        arangoContainer.withEnv(envs);
        arangoContainer.withFixedExposedPort(8529, 8529);
        arangoContainer.start();
        WaitingConsumer consumer = new WaitingConsumer();
        arangoContainer.followOutput(consumer, OutputFrame.OutputType.STDOUT);
        consumer.waitUntil(
                it -> it.getUtf8String().contains("ArangoDB (version 3.4.4 [linux]) is ready for business. Have fun!"));

        JsonObject config = new JsonObject();
        config.put("host", "localhost");
        config.put("port", 8529);
        final ArangoClient.ArangoBuilderDecorator arangoBuilderDecorator = new ArangoClient.ArangoBuilderDecorator(config);
        arangoClient = ArangoClient.createNonShared(vertx, arangoBuilderDecorator, DB_NAME);
        try {
            createCollection();
            context.completeNow();
        } catch (ExecutionException | InterruptedException e) {
            context.failNow(e);
        }
    }

    private void createCollection() throws ExecutionException, InterruptedException {
        ArangoDBAsync arangoDBAsync = new ArangoDBAsync.Builder().host("localhost", 8529).build();
        if (!arangoDBAsync.getDatabases().get().contains(DB_NAME)) {
            arangoDBAsync.createDatabase(DB_NAME);
        }
        final ArangoDatabaseAsync db = arangoDBAsync.db(DB_NAME);
        final List<String> list =
                db.getCollections().get().stream().map(CollectionEntity::getName).collect(Collectors.toList());
        if (!list.contains(COLLECTION_NAME)) {
            db.createCollection(COLLECTION_NAME);
        }
        db.collection(COLLECTION_NAME);
    }

    @AfterAll
    public void tearDown(VertxTestContext context) {
        arangoContainer.stop();
        context.completeNow();
        //TODO add close method
    }
}
