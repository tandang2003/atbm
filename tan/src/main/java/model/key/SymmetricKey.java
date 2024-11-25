package model.key;

import java.io.DataOutputStream;
import java.io.IOException;

public class SymmetricKey implements IKey<SymmetricKeyHelper> {
    private SymmetricKeyHelper key;

    public SymmetricKey(SymmetricKeyHelper key) {
        this.key = key;
    }

    public SymmetricKey() {
        this.key = new SymmetricKeyHelper();
    }

    @Override
    public SymmetricKeyHelper getKey() {
        if (key == null) {
            key = new SymmetricKeyHelper();
        }
        return key;
    }

    @Override
    public void saveToFile(DataOutputStream outputStream) throws IOException {
        outputStream.writeUTF(key.getCipher().getName());
        outputStream.writeUTF(String.valueOf(key.getKeySize()));
        outputStream.writeUTF(String.valueOf(key.getIvSize()));
        outputStream.writeUTF(key.getMode().getName());
        outputStream.writeUTF(key.getPadding().getName());
        for (String s : key.getStringKeyAndIv())
            outputStream.writeUTF(s);

    }

}
