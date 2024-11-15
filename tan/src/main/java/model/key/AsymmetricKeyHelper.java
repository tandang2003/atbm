package model.key;

import model.common.Cipher;
import model.common.Size;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class AsymmetricKeyHelper {
    private Cipher cipher;
    private Size keySize;
    private String Transformation;
    private Size ivSize;
    private SecretKey secretKey;
    private IvParameterSpec ivParameterSpec;

    public AsymmetricKeyHelper(Cipher cipher, Size keySize, String transformation, Size ivSize) {
        this.cipher = cipher;
        this.keySize = keySize;
        Transformation = transformation;
        this.ivSize = ivSize;
    }

    public Cipher getCipher() {
        return cipher;
    }

    public int getKeySize() {
        return keySize.getBit();
    }

    public String getTransformation() {
        return Transformation;
    }

    public int getIvSize() {
        return ivSize.getByteFormat();
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
