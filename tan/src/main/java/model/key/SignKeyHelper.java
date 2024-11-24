package model.key;

import model.common.*;

import java.io.Serializable;

public class SignKeyHelper implements Serializable {
    SecureRandom secureRandom;
    Signature signature;
    Size keySize;
    KeyPairAlgorithm keyPairAlgorithm;
    Provider provider;

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
}
