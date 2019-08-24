package io.vertx.ext.arango;

import com.arangodb.entity.BaseDocument;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ArangoGetDocumentTest extends ArangoTestBase {

    @Test
    public void getDocument(VertxTestContext context) {
        final BaseDocument document = new BaseDocument();
        document.addAttribute("param", "value");
        arangoClient.insertDocument(COLLECTION_NAME, document, createEntity -> {
            if (createEntity.succeeded()) {
                arangoClient.getDocument(COLLECTION_NAME, createEntity.result().getKey(), BaseDocument.class, it -> {
                    if (it.succeeded()) {
                        final BaseDocument result = it.result();
                        Assertions.assertEquals("value", result.getAttribute("param"));
                        context.completeNow();
                    } else {
                        context.failNow(it.cause());
                    }
                });
            }
        });
    }
}
