package model.key;

import model.common.CipherSpecification;
import model.utils.KeyUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.FileSystemException;

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
        String[] keys = key.getKeys();
        outputStream.writeUTF(keys[0]);
        KeyUtil.saveKey(keys[0], keys[1]);

//        outputStream.writeUTF(key.getKeys()[1]);
    }

    @Override
    public void loadFromFile(DataInputStream inputStream) throws IOException {
        String alg = inputStream.readUTF();
        if (!alg.equals("AsymmetricKey")) {
            throw new IOException("Invalid key file");
        }
        String cipher = inputStream.readUTF();
        if (!cipher.equals(key.getCipher().getName())) {
            throw new IOException("Invalid key file. This key is for " + cipher + " cipher");
        }
        String mode, padding, publicKey, privateKey;
        int keySize;
        try {

            mode = inputStream.readUTF();
            padding = inputStream.readUTF();
            keySize = Integer.parseInt(inputStream.readUTF());
            publicKey = inputStream.readUTF();
//            privateKey = inputStream.readUTF();
        } catch (Exception e) {
            throw new IOException("Invalid key file");
        }
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
        String pKey = KeyUtil.getPrivateKey(publicKey);
        if (pKey == null)
            throw new FileSystemException("System not found private key");
        key.setPrivateKey(pKey);

    }

    private void savePrivateKey() {

    }
}
