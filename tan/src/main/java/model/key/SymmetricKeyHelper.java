package model.key;

import model.common.Cipher;
import model.common.Size;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.util.Base64;

public class SymmetricKeyHelper implements Serializable {
    private Cipher cipher;
    private Size keySize;
    private String Transformation;
    private Size ivSize;
    private String secretKey;
    private String ivParameterSpec;

    //    TODO FIX SYNCHRONIZED THE MODE PADDING KEY AND IV IN VIEW
    public SymmetricKeyHelper() {
    }

    public SymmetricKeyHelper(Cipher cipher, Size keySize, String transformation, Size ivSize) {
        this.cipher = cipher;
        this.keySize = keySize;
        this.Transformation = transformation;
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
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public void setIvParameterSpec(IvParameterSpec ivParameterSpec) {
        this.ivParameterSpec = Base64.getEncoder().encodeToString(ivParameterSpec.getIV());
    }

    public SecretKey getSecretKey() {
        if (secretKey == null) {
            return null;
        }
        System.out.println(secretKey);
        return new SecretKeySpec(Base64.getDecoder().decode(secretKey), cipher.getName());
    }

    public IvParameterSpec getIvParameterSpec() {
        if (ivParameterSpec == null) {
            return null;
        }
        return new IvParameterSpec(Base64.getDecoder().decode(ivParameterSpec));
    }

    public void setCipher(Cipher cipher) {
        this.cipher = cipher;
    }

    public void setKeySize(Size keySize) {
        this.keySize = keySize;
        this.secretKey = null;
    }

    public void setTransformation(String transformation) {
        Transformation = transformation;
    }

    public void setIvSize(Size ivSize) {
        this.ivSize = ivSize;
        this.ivParameterSpec = null;
    }

    public String[] getStringKeyAndIv() {
        return new String[]{secretKey, ivParameterSpec};
    }

}
