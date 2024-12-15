package model.key;

import model.common.SignatureSpecification;

import java.io.*;

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
    public void saveToFile(File fileOutputStream, File priDestination) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(fileOutputStream));
        outputStream.writeUTF(key.getKeyPairAlgorithm().getName());
        outputStream.writeUTF(key.getSecureRandom());
        outputStream.writeUTF(key.getProvider());
        outputStream.writeUTF(key.getSignature());
        outputStream.writeUTF(String.valueOf(key.getKeySize()));
        outputStream.writeUTF(key.getPublicKeyString());
        DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(priDestination));
        dataOutputStream.writeUTF(key.getPrivateKeyString());
        dataOutputStream.close();
        outputStream.close();
    }

    @Override
    public void loadFromFile(File file, boolean isPublicKey) throws IOException {
        DataInputStream inputStream = new DataInputStream(new FileInputStream(file));
        if (isPublicKey) {
            try {
                String keyPairAlgorithm = inputStream.readUTF();
                String secureRandom = inputStream.readUTF();
                String provider = inputStream.readUTF();
                String signature = inputStream.readUTF();
                int keySize = Integer.parseInt(inputStream.readUTF());
                String publicKey = inputStream.readUTF();
//            String privateKey = inputStream.readUTF();
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
//            key.setPrivateKey(privateKey);
            } catch (Exception e) {
                throw new IOException("Invalid key file");
            }
        }
        else
            try {
                String privateKey = inputStream.readUTF();
                key.setPrivateKey(privateKey);
            } catch (Exception e) {
                throw new IOException("Invalid key file");
            }
    }
}
