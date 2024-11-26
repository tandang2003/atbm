package model.key;

import model.common.Cipher;
import model.common.Mode;
import model.common.Padding;
import model.common.Size;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.util.Base64;

public class SymmetricKeyHelper implements Serializable {
    private Cipher cipher;
    private Size keySize;
    private Mode mode;
    private Padding padding;

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void setPadding(Padding padding) {
        this.padding = padding;
    }

    //    private String Transformation;
    private Size ivSize;

    private String secretKey;
    private String ivParameterSpec;

    //    TODO FIX SYNCHRONIZED THE MODE PADDING KEY AND IV IN VIEW
    public SymmetricKeyHelper() {
    }

    public SymmetricKeyHelper(Cipher cipher, Size keySize, String transformation, Size ivSize) {
        this.cipher = cipher;
        this.keySize = keySize;
//        this.Transformation = transformation;
        this.ivSize = ivSize;
    }

    public Mode getMode() {
        return mode;
    }

    public Padding getPadding() {
        return padding;
    }

    public SymmetricKeyHelper(Cipher cipher, Size keySize, Mode mode, Padding padding, Size ivSize) {
        this.cipher = cipher;
        this.keySize = keySize;
        this.mode = mode;
        this.padding = padding;
        this.ivSize = ivSize;
    }

    public Cipher getCipher() {
        return cipher;
    }

    public int getKeySize() {
        return keySize.getBit();
    }

    public String getTransformation() {
        String transformation = cipher.getName() + "/" + mode.getName() + "/" + padding.getName();
        if (mode == Mode.NONE) {
            transformation = cipher.getName();
        }
        return transformation;
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
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setIvParameterSpec(String ivParameterSpec) {
        this.ivParameterSpec = ivParameterSpec;
    }

    public SecretKey getSecretKey() {
        if (secretKey == null) {
            return null;
        }
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

    public void setIvSize(Size ivSize) {
        this.ivSize = ivSize;
        this.ivParameterSpec = null;
    }

    public String[] getStringKeyAndIv() {
        return new String[]{secretKey, ivParameterSpec};
    }

}
