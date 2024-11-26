package model.algorithms.ModernEncryption;

import model.algorithms.AAlgorithm;
import model.common.*;
import model.key.AsymmetricKey;
import model.key.AsymmetricKeyHelper;
import org.bouncycastle.math.raw.Mod;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class AsymmetricAlgorithm extends AAlgorithm {
    private javax.crypto.Cipher cipherPublic;
    private javax.crypto.Cipher cipherPrivate;

    public AsymmetricAlgorithm(Cipher cipher, Mode mode, Padding padding, Size keySize) {
        super();
        this.key = new AsymmetricKey(new AsymmetricKeyHelper(cipher, mode, padding, keySize));

    }

    @Override
    public void genKey() {
        AsymmetricKeyHelper asymmetricKeyHelper = (AsymmetricKeyHelper) this.key.getKey();
        try {
            genkey(asymmetricKeyHelper);
            genCipher(asymmetricKeyHelper);
        } catch (NoSuchAlgorithmException e) {
//            throw new NoSuchPaddingException("The padding is not suitable.Please change block size or adding padding mode");
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
//            throw new IllegalBlockSizeException("The mode or padding is not suitable.Please change block size or adding padding mode");
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
//            throw new InvalidKeyException("The key is not suitable.Please change block size or adding padding mode");
            throw new RuntimeException(e);
        }
    }

    private void genkey(AsymmetricKeyHelper asymmetricKeyHelper) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(asymmetricKeyHelper.getCipher().getName());
        keyPairGenerator.initialize(asymmetricKeyHelper.getKeySize().getBit());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        asymmetricKeyHelper.setPrivateKey(keyPair.getPrivate());
        asymmetricKeyHelper.setPublicKey(keyPair.getPublic());
    }

    private void genCipher(AsymmetricKeyHelper asymmetricKeyHelper) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        System.out.println(asymmetricKeyHelper.getTransformation());
        cipherPublic = javax.crypto.Cipher.getInstance(asymmetricKeyHelper.getTransformation());
        cipherPrivate = javax.crypto.Cipher.getInstance(asymmetricKeyHelper.getTransformation());
        if (asymmetricKeyHelper.getPublicKey() == null || asymmetricKeyHelper.getPrivateKey() == null) {
            return;
        }

        cipherPublic.init(javax.crypto.Cipher.ENCRYPT_MODE, asymmetricKeyHelper.getPublicKey());
        cipherPrivate.init(javax.crypto.Cipher.DECRYPT_MODE, asymmetricKeyHelper.getPrivateKey());
    }

    @Override
    public String encrypt(String input) throws IllegalBlockSizeException {
        byte[] encrypted;
        try {
            encrypted = cipherPublic.doFinal(input.getBytes(StandardCharsets.UTF_8));
        } catch (BadPaddingException e) {
            throw new IllegalBlockSizeException("The input is not suitable for the key size. Please using padding mode");
        }
        return Base64.getEncoder().encodeToString(encrypted);

    }

    @Override
    public String decrypt(String encryptInput) throws IllegalBlockSizeException {
        byte[] decrypted;
        try {
            decrypted = cipherPrivate.doFinal(Base64.getDecoder().decode(encryptInput));
        } catch (BadPaddingException e) {
            throw new IllegalBlockSizeException("The input is not suitable for the key size. Please using padding mode");
        }

        return new String(decrypted, StandardCharsets.UTF_8);
    }

    @Override
    public ICipherEnum getCipher() {
        return ((AsymmetricKeyHelper) this.key.getKey()).getCipher();
    }

    @Override
    public void updateKey(Object[] key) {
        AsymmetricKeyHelper asymmetricKeyHelper = (AsymmetricKeyHelper) this.key.getKey();
        asymmetricKeyHelper.setKeySize((Size) key[0]);
        asymmetricKeyHelper.setMode((Mode) key[1]);
        asymmetricKeyHelper.setPadding((Padding) key[2]);

        try {
            genkey(asymmetricKeyHelper);
            genCipher(asymmetricKeyHelper);
        } catch (NoSuchAlgorithmException e) {
//            throw new NoSuchPaddingException("The padding is not suitable.Please change block size or adding padding mode");
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
//            throw new IllegalBlockSizeException("The mode or padding is not suitable.Please change block size or adding padding mode");
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
//            throw new InvalidKeyException("The key is not suitable.Please change block size or adding padding mode");
            throw new RuntimeException(e);
        }
    }

    @Override
    protected boolean validation() {
        return false;
    }

    public static void main(String[] args) {
        AsymmetricAlgorithm asymmetricAlgorithm = new AsymmetricAlgorithm(Cipher.RSA, Mode.ECB, Padding.PKCS1Padding, Size.Size_256);
        asymmetricAlgorithm.genKey();
        String input = "Hello";
        String encrypt = null;
        try {
            encrypt = asymmetricAlgorithm.encrypt(input);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }
        System.out.println(encrypt);
        try {
            System.out.println(asymmetricAlgorithm.decrypt(encrypt));
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }

    }
}
