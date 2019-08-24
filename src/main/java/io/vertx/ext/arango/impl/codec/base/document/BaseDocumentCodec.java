package io.vertx.ext.arango.impl.codec.base.document;

import com.arangodb.entity.BaseDocument;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author kostya05983
 * Custom messageCodec for baseDocument from arangodb
 */
public class BaseDocumentCodec implements MessageCodec<BaseDocument, BaseDocument> {
    private Logger logger = LoggerFactory.getLogger(BaseDocumentCodec.class);

    @Override
    public void encodeToWire(Buffer buffer, BaseDocument baseDocument) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(baseDocument);
            buffer.appendBytes(baos.toByteArray());
        } catch (IOException io) {
            logger.error("Encode BaseDocument to buffer error", io);
        }
    }

    @Override
    public BaseDocument decodeFromWire(int i, Buffer buffer) {
        try (ByteArrayInputStream byteInputStream = new ByteArrayInputStream(buffer.getBytes())) {
            ObjectInputStream ois = new ObjectInputStream(byteInputStream);
            return (BaseDocument) ois.readObject();
        } catch (IOException | ClassNotFoundException io) {
            logger.error("Decode BaseDocument to buffer error", io);
        }
        return null;
    }

    @Override
    public BaseDocument transform(BaseDocument baseDocument) {
        return baseDocument;
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}
