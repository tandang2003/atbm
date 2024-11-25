package model.key;

import java.io.DataOutputStream;
import java.io.IOException;

public class HashKey implements IKey<HashKeyHelper> {
    private HashKeyHelper key;

    public HashKey(HashKeyHelper key) {
        this.key = key;
    }

    @Override
    public HashKeyHelper getKey() {
        return key;
    }

    @Override
    public void saveToFile(DataOutputStream outputStream) throws IOException {
        outputStream.writeUTF(key.getKey().getName());
        outputStream.writeUTF(key.getProvider());
        outputStream.writeUTF(key.getKeyHmac());
        outputStream.writeUTF(String.valueOf(key.isHex()));
        outputStream.writeUTF(String.valueOf(key.isHMAC()));

    }
}
