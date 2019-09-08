package io.vertx.ext.arango.impl.codec.vpack;

import com.arangodb.velocypack.VPack;
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
 * Custom messageCodec for Vpack
 */
public class VPackCodec implements MessageCodec<VPack, VPack> {

    private final Logger logger = LoggerFactory.getLogger(VPackCodec.class);

    @Override
    public void encodeToWire(Buffer buffer, VPack vPack) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(oos);
            buffer.appendBytes(baos.toByteArray());
        } catch (IOException e) {
            logger.error("Encode vpack to bytes failed", e);
        }
    }

    @Override
    public VPack decodeFromWire(int i, Buffer buffer) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(buffer.getBytes())) {
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (VPack) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Decode vpack from bytes failed", e);
        }
        return null;
    }

    @Override
    public VPack transform(VPack vPack) {
        return vPack;
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
