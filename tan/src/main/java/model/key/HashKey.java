package model.key;

import model.common.Hash;

import java.io.DataInputStream;
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
        outputStream.writeUTF("HashKey");
        outputStream.writeUTF(key.getKey().getName());
        outputStream.writeUTF(key.getProvider());
        outputStream.writeUTF(key.getKeyHmac());
        outputStream.writeUTF(String.valueOf(key.isHex()));
        outputStream.writeUTF(String.valueOf(key.isHMAC()));

    }

    @Override
    public void loadFromFile(DataInputStream inputStream) throws IOException {
        String alg = inputStream.readUTF();
        if (!alg.equals("HashKey")) {
            throw new IOException("Invalid key file");
        }
        try {
            String keyName = inputStream.readUTF();
            if (keyName.equals(key.getKey().getName())) {
                throw new IOException("Invalid key file. This key is for " + keyName + " key");
            }
            String provider = inputStream.readUTF();
            String keyHmac = inputStream.readUTF();
            boolean hex = Boolean.parseBoolean(inputStream.readUTF());
            boolean hmac = Boolean.parseBoolean(inputStream.readUTF());
            key.setKeyHmac(keyHmac);
            key.setHex(hex);
            key.setHMAC(hmac);
            key.setProvider(provider);
            key.setKey(Hash.findHashWithName(keyName));
        } catch (Exception e) {
            throw new IOException("Invalid key file");
        }
    }
}
