package model.key;

import model.common.CipherSpecification;

import java.io.DataInputStream;
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
        outputStream.writeUTF("AsymmetricKey");
        outputStream.writeUTF(key.getCipher().getName());
        outputStream.writeUTF(key.getMode().getName());
        outputStream.writeUTF(key.getPadding().getName());
        outputStream.writeUTF(String.valueOf(key.getKeySize().getByteFormat()));
        for (int i = 0; i < key.getKeys().length; i++) {
            outputStream.writeUTF(key.getKeys()[i]);
        }
    }

    @Override
    public void loadFromFile(DataInputStream inputStream) throws IOException {
        String alg = inputStream.readUTF();
        if (!alg.equals("AsymmetricKey")) {
            throw new IOException("Invalid key file");
        }
        try {
            String cipher = inputStream.readUTF();
            if (!cipher.equals(key.getCipher().getName())) {
                throw new IOException("Invalid key file. This key is for " + cipher + " cipher");
            }
            String mode = inputStream.readUTF();
            String padding = inputStream.readUTF();
            int keySize = Integer.parseInt(inputStream.readUTF());
            String publicKey = inputStream.readUTF();
            String privateKey = inputStream.readUTF();
            CipherSpecification cipherSpecification = CipherSpecification.findCipherSpecificationWithCipherName(cipher);

            key.setCipher(cipherSpecification.getAlgorithm());
            cipherSpecification.getValidModePaddingCombinations().forEach((k, v) -> {
                if (k.getName().equals(mode)) {
                    key.setMode(k);
                    v.forEach(p -> {
                        if (p.getName().equals(padding)) {
                            key.setPadding(p);
                        }
                    });
                }
            });
            cipherSpecification.getSupportedKeySizes().forEach(keySize1 -> {
                if (keySize1.getByteFormat() == keySize) {
                    key.setKeySize(keySize1);
                }
            });
            key.setPublicKey(publicKey);
            key.setPrivateKey(privateKey);
        } catch (Exception e) {
            throw new IOException("Invalid key file");
        }
    }
}
