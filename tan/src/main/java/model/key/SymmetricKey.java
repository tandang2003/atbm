package model.key;

import model.common.CipherSpecification;

import java.io.DataInputStream;
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
        outputStream.writeUTF("SymmetricKey");
        outputStream.writeUTF(key.getCipher().getName());
        outputStream.writeUTF(String.valueOf(key.getKeySize()));
//        outputStream.writeUTF(String.valueOf(key.getIvSize()));
        outputStream.writeUTF(key.getMode().getName());
        outputStream.writeUTF(key.getPadding().getName());
        for (String s : key.getStringKeyAndIv())
            outputStream.writeUTF(s);

    }

    @Override
    public void loadFromFile(DataInputStream inputStream) throws IOException {
        String alg = inputStream.readUTF();
        if (!alg.equals("SymmetricKey")) {
            throw new IOException("Invalid key file");
        }
        try {
            String cipher = inputStream.readUTF();
            if (!cipher.equals(key.getCipher().getName())) {
                throw new IOException("Invalid key file. This key is for " + cipher + " cipher");
            }
            int keySize = Integer.parseInt(inputStream.readUTF());
            String mode = inputStream.readUTF();
            String padding = inputStream.readUTF();
            String[] keyAndIv = new String[2];
            for (int i = 0; i < 2; i++) {
                keyAndIv[i] = inputStream.readUTF();
            }
            CipherSpecification cipherSpecification = CipherSpecification.findCipherSpecificationWithCipherName(cipher);
            key.setCipher(cipherSpecification.getAlgorithm());
            cipherSpecification.getSupportedKeySizes().forEach((k) -> {
                if (k.getBit() == keySize) {
                    key.setKeySize(k);
                }
            });
            cipherSpecification.getIvSizes().forEach((m, s) -> {
                if (m.getName().equals(mode)) {
                    key.setMode(m);
                    key.setIvSize(s);
                }
            });
            cipherSpecification.getValidModePaddingCombinations().forEach((m, p) -> {
                if (m.getName().equals(mode)) {
                    p.forEach((k) -> {
                        if (k.getName().equals(padding)) {
                            key.setPadding(k);
                        }
                    });
                }
            });
            key.setSecretKey(keyAndIv[0]);
            key.setIvParameterSpec(keyAndIv[1]);
        } catch (Exception e) {
            throw new IOException("Invalid key file");
        }
    }

}
