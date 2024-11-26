package model.key;

import model.common.SignatureSpecification;

import java.io.DataInputStream;
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
        outputStream.writeUTF("SignKey");
        outputStream.writeUTF(key.getKeyPairAlgorithm().getName());
        outputStream.writeUTF(key.getSecureRandom());
        outputStream.writeUTF(key.getProvider());
        outputStream.writeUTF(key.getSignature());
        outputStream.writeUTF(String.valueOf(key.getKeySize()));
        for (int i = 0; i < key.getKeys().length; i++) {
            outputStream.writeUTF(key.getKeys()[i]);
        }
    }

    @Override
    public void loadFromFile(DataInputStream inputStream) throws IOException {
        String alg = inputStream.readUTF();
        if (!alg.equals("SignKey")) {
            throw new IOException("Invalid key file");
        }
        String keyPairAlgorithm = inputStream.readUTF();
        if (!keyPairAlgorithm.equals(key.getKeyPairAlgorithm().getName())) {
            throw new IOException("Invalid key file. This key is for " + keyPairAlgorithm + " key");
        }
        try {

            String secureRandom = inputStream.readUTF();
            String provider = inputStream.readUTF();
            String signature = inputStream.readUTF();
            int keySize = Integer.parseInt(inputStream.readUTF());
            String publicKey = inputStream.readUTF();
            String privateKey = inputStream.readUTF();
            SignatureSpecification signatureSpecification = SignatureSpecification.findByKeyPairAlgorithm(keyPairAlgorithm);
            key.setKeyPairAlgorithm(signatureSpecification.getKeyPairAlgorithm());
            key.setProvider(signatureSpecification.getProvider());
            signatureSpecification.getAlgRandoms().forEach((k) -> {
                if (k.getName().equals(secureRandom)) {
                    key.setSecureRandom(k);
                }
            });
            signatureSpecification.getSignatures().forEach((k) -> {
                if (k.getName().equals(signature)) {
                    key.setSignature(k);
                }
            });
            signatureSpecification.getSizes().forEach((k) -> {
                if (k.getBit() == keySize) {
                    key.setKeySize(k);
                }
            });
            key.setPublicKey(publicKey);
            key.setPrivateKey(privateKey);
        } catch (Exception e) {
            throw new IOException("Invalid key file");
        }
    }
}
