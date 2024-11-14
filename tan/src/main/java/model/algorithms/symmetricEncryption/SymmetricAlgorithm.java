package model.algorithms.symmetricEncryption;

import model.algorithms.AAlgorithm;
import model.common.Cipher;
import model.common.Mode;
import model.common.Padding;
import model.key.AsymmetricKey;
import model.key.AsymmetricKeyHelper;

import javax.crypto.CipherInputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

public class SymmetricAlgorithm extends AAlgorithm {
    private javax.crypto.Cipher cipherIn;
    private javax.crypto.Cipher cipherOut;

    public SymmetricAlgorithm(List<String> arrChar, Cipher cipher, Mode mode, Padding padding, int keySize, int ivSize) {
        super();
        this.arrChar = arrChar;
        this.key = new AsymmetricKey(new AsymmetricKeyHelper(cipher, keySize, cipher.getName() + File.separator + mode.toString() + File.separator + padding.getName(), ivSize));
    }

    @Override
    public void genKey() {
        AsymmetricKeyHelper asymmetricKeyHelper = (AsymmetricKeyHelper) this.getKey();
        KeyGenerator keyGenerator = null;
        try {
            String cipher = asymmetricKeyHelper.getCipher().getName();
            keyGenerator = KeyGenerator.getInstance(cipher);
            keyGenerator.init(asymmetricKeyHelper.getKeySize());
            asymmetricKeyHelper.setSecretKey(keyGenerator.generateKey());
            byte[] b = new byte[asymmetricKeyHelper.getIvSize()];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(b);
            asymmetricKeyHelper.setIvParameterSpec(new IvParameterSpec(b));
            cipherIn = javax.crypto.Cipher.getInstance(asymmetricKeyHelper.getTransformation());
            cipherIn.init(javax.crypto.Cipher.ENCRYPT_MODE, asymmetricKeyHelper.getSecretKey(), asymmetricKeyHelper.getIvParameterSpec());
            cipherOut = javax.crypto.Cipher.getInstance(asymmetricKeyHelper.getTransformation());
            cipherOut.init(javax.crypto.Cipher.DECRYPT_MODE, asymmetricKeyHelper.getSecretKey(), asymmetricKeyHelper.getIvParameterSpec());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String encrypt(String input) {
        byte[] dataEncrypt = input.getBytes();
        BufferedInputStream bis = new BufferedInputStream(new CipherInputStream(new ByteArrayInputStream(dataEncrypt), cipherIn));
        int read = 0;
        byte[] encrypted = null;
        byte[] buffered = new byte[1024];
        try {
            while ((read = bis.read(buffered)) != -1) {
                encrypted = expand(encrypted, buffered, read);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Base64.getEncoder().encodeToString(encrypted);
    }


    private byte[] expand(byte[] data, byte[] expand, int limit) {
        if (data == null) {
            byte[] out = new byte[limit];
            System.arraycopy(expand, 0, out, 0, limit);
            return out;
        }
        byte[] out = new byte[data.length + limit];
        System.arraycopy(data, 0, out, 0, data.length);
        System.arraycopy(expand, 0, out, data.length, limit);
        return out;
    }

    public String decrypt(String encryptInput) {
        byte[] encrypted = Base64.getDecoder().decode(encryptInput);
        BufferedInputStream bis = new BufferedInputStream(new CipherInputStream(new ByteArrayInputStream(encrypted), cipherOut));
        int read = 0;
        byte[] decrypted = null;
        byte[] buffered = new byte[1024];
        try {
            while ((read = bis.read(buffered)) != -1) {
                decrypted = expand(decrypted, buffered, read);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new String(decrypted);
    }

    @Override
    public Cipher getCipher() {
        return ((AsymmetricKeyHelper) this.getKey()).getCipher();
    }

    @Override
    public boolean encryptFile(String fileIn, String fileOut) {

//        return super.encryptFile(input, output);
        return false;
    }

    @Override
    public boolean decryptFile(String input, String output) {
        return super.decryptFile(input, output);
    }
}
