package io.vertx.ext.arango;

import com.arangodb.ArangoCollectionAsync;
import com.arangodb.ArangoDBAsync;
import com.arangodb.ArangoDatabaseAsync;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.CollectionEntity;
import com.arangodb.entity.DocumentCreateEntity;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.arango.impl.ArangoClientImpl;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.Rule;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.output.OutputFrame;
import org.testcontainers.containers.output.WaitingConsumer;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@ExtendWith(VertxExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Disabled("While changes to builder")
public class ArangoGetDocumentTest {

    private ArangoClient arangoClient;

    @Rule
    public FixedHostPortGenericContainer<?> arangoContainer =
            new FixedHostPortGenericContainer<>("arangodb/arangodb:3.4.4");

    private final static String COLLECTION_NAME = "collection";
    private final static String DB_NAME = "db";

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
        final ArangoCollectionAsync collection = db.collection(COLLECTION_NAME);
    }

    @AfterAll
    public void tearDown(VertxTestContext context) {
        arangoContainer.stop();
        context.completeNow();
        //TODO add close method
    }

    @Test
    public void getDocument(VertxTestContext context) {
        final BaseDocument document = new BaseDocument();
        document.addAttribute("param", "value");
        arangoClient.insertDocument(COLLECTION_NAME, document,
                (Handler<AsyncResult<DocumentCreateEntity>>) createEntity -> {
                    if (createEntity.succeeded()) {
                        arangoClient.getDocument(COLLECTION_NAME, createEntity.result().getKey(),
                                BaseDocument.class, (Handler<AsyncResult<BaseDocument>>) asyncResult -> {
                                    if (asyncResult.succeeded()) {
                                        final BaseDocument result = asyncResult.result();
                                        Assertions.assertEquals("value", result.getAttribute("param"));
                                        context.completeNow();
                                    }
                                });
                    } else {
                        context.failNow(createEntity.cause());
                    }
                });
    }
}
