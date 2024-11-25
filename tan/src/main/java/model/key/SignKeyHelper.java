package model.key;

import model.common.*;

import java.io.Serializable;
import java.util.Base64;

public class SignKeyHelper implements Serializable {
    private SecureRandom secureRandom;
    private Signature signature;
    private Size keySize;
    private KeyPairAlgorithm keyPairAlgorithm;
    private Provider provider;

    private byte[] publicKey, privateKey;

    public SignKeyHelper(KeyPairAlgorithm keyPairAlgorithm, Signature signature, Provider provider, SecureRandom secureRandom, Size size) {
        this.signature = signature;
        this.provider = provider;
        this.keyPairAlgorithm = keyPairAlgorithm;
        this.secureRandom = secureRandom;
        this.keySize = size;
    }

    public String getSecureRandom() {
        return secureRandom.getName();
    }

    public void setSecureRandom(SecureRandom secureRandom) {
        this.secureRandom = secureRandom;
    }


    public String getSignature() {
        return signature.getName();
    }

    public void setSignature(model.common.Signature signature) {
        this.signature = signature;
    }

    public int getKeySize() {
        return keySize.getBit();
    }

    public void setKeySize(Size keySize) {
        this.keySize = keySize;
    }

    public KeyPairAlgorithm getKeyPairAlgorithm() {
        return keyPairAlgorithm;
    }

    public void setKeyPairAlgorithm(KeyPairAlgorithm keyPairAlgorithm) {
        this.keyPairAlgorithm = keyPairAlgorithm;
    }

    public String getProvider() {
        return provider.getName();
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = Base64.getDecoder().decode(publicKey);
    }

    public void setPrivateKey(byte[] privateKey) {
        this.privateKey = privateKey;
    }
    public void setPrivateKey(String privateKey) {
        this.privateKey = Base64.getDecoder().decode(privateKey);
    }
    public String[] getKeys() {
        return new String[]{Base64.getEncoder().encodeToString(publicKey), Base64.getEncoder().encodeToString(privateKey)};
    }
}
