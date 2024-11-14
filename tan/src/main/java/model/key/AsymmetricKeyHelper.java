package model.key;

import model.common.Cipher;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class AsymmetricKeyHelper {
    private Cipher cipher;
    private int keySize;
    private String Transformation;
    private int ivSize;
    private SecretKey secretKey;
    private IvParameterSpec ivParameterSpec;

    public AsymmetricKeyHelper(Cipher cipher, int keySize, String transformation, int ivSize) {
        this.cipher = cipher;
        this.keySize = keySize;
        Transformation = transformation;
        this.ivSize = ivSize;
    }

    public Cipher getCipher() {
        return cipher;
    }

    public int getKeySize() {
        return keySize;
    }

    public String getTransformation() {
        return Transformation;
    }

    public int getIvSize() {
        return ivSize;
    }

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public void setIvParameterSpec(IvParameterSpec ivParameterSpec) {
        this.ivParameterSpec = ivParameterSpec;
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public IvParameterSpec getIvParameterSpec() {
        return ivParameterSpec;
    }
}
