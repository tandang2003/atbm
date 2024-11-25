package model.key;

import java.io.DataOutputStream;
import java.io.IOException;

public class SignKey implements IKey {
    private SignKeyHelper key;

    public SignKey(SignKeyHelper key) {
        this.key = key;
    }

    @Override
    public SignKeyHelper getKey() {
        return key;
    }

    @Override
    public void saveToFile(DataOutputStream outputStream) throws IOException {
        outputStream.writeUTF(key.getKeyPairAlgorithm().getName());
        outputStream.writeUTF(key.getSecureRandom());
        outputStream.writeUTF(key.getProvider());
        outputStream.writeUTF(key.getSignature());
        outputStream.writeUTF(String.valueOf(key.getKeySize()));
        for (int i = 0; i < key.getKeys().length; i++) {
            outputStream.writeUTF(key.getKeys()[i]);
        }
    }
}
