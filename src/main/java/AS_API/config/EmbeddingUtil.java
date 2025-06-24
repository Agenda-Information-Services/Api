package AS_API.config;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class EmbeddingUtil {

    public static byte[] floatArrayToByteArray(float[] floats) {
        ByteBuffer buffer = ByteBuffer.allocate(floats.length * Float.BYTES);
        buffer.order(ByteOrder.LITTLE_ENDIAN); // 대부분 LITTLE_ENDIAN 사용
        for (float value : floats) {
            buffer.putFloat(value);
        }
        return buffer.array();
    }

    public static float[] byteArrayToFloatArray(byte[] bytes) {
        if (bytes == null || bytes.length == 0) return null;

        int floatCount = bytes.length / Float.BYTES;
        float[] floats = new float[floatCount];

        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.order(ByteOrder.LITTLE_ENDIAN); // 저장된 형식과 일치시켜야 함

        for (int i = 0; i < floatCount; i++) {
            floats[i] = buffer.getFloat(i * Float.BYTES);
        }

        return floats;
    }
}
