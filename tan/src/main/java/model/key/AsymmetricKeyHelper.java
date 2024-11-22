package model.key;

import model.common.Cipher;
import model.common.Mode;
import model.common.Padding;
import model.common.Size;

import java.io.Serializable;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class AsymmetricKeyHelper implements Serializable {
    private Cipher cipher;
    //    private Mode mode;
//    private Padding padding;
    private String transformation;
    private Size keySize;
    private String publicKey;
    private String privateKey;

    public AsymmetricKeyHelper() {

    }

    public AsymmetricKeyHelper(Cipher cipher, String transformation, Size keySize) {
        this.cipher = cipher;
        this.transformation = transformation;
        this.keySize = keySize;
    }

    public Cipher getCipher() {
        return cipher;
    }

    public PublicKey getPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (publicKey == null) {
            return null;
        }
        KeyFactory kf = KeyFactory.getInstance(cipher.getName());
        X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
        return kf.generatePublic(encodedKeySpec);
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public PrivateKey getPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (privateKey == null) {
            return null;
        }
        KeyFactory kf = KeyFactory.getInstance(cipher.getName());
        PKCS8EncodedKeySpec encodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
        return kf.generatePrivate(encodedKeySpec);
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    public String[] getKeys() {
        return new String[]{publicKey, privateKey};
    }

    public Size getKeySize() {
        return keySize;
    }

    public String getTransformation() {
        return transformation;
    }

    public void setTransformation(String transformation) {

        this.transformation = transformation;
    }

    public void setKeySize(Size keySize) {
        this.keySize = keySize;
        this.privateKey = null;
        this.publicKey = null;
    }
}