package model.key;

import java.io.DataOutputStream;
import java.io.IOException;

public class AsymmetricKey implements IKey<AsymmetricKeyHelper> {
    private AsymmetricKeyHelper key;

    public AsymmetricKey(AsymmetricKeyHelper key) {
        this.key = key;
    }

    public AsymmetricKey() {
        this.key = new AsymmetricKeyHelper();
    }

    @Override
    public AsymmetricKeyHelper getKey() {
        if (key == null) {
            key = new AsymmetricKeyHelper();
        }
        return key;
    }

    @Override
    public void saveToFile(DataOutputStream outputStream) throws IOException {
        outputStream.writeUTF(key.getCipher().getName());
        outputStream.writeUTF(key.getMode().getName());
        outputStream.writeUTF(key.getPadding().getName());
        outputStream.writeUTF(String.valueOf(key.getKeySize().getByteFormat()));
        for (int i = 0; i < key.getKeys().length; i++) {
            outputStream.writeUTF(key.getKeys()[i]);
        }
//        outputStream.writeUTF(key.getKeys());
    }
}
