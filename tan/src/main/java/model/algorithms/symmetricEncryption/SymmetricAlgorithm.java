package model.algorithms.symmetricEncryption;

import model.algorithms.AAlgorithm;
import model.algorithms.IAlgorithms;
import model.common.*;
import model.common.Cipher;
import model.key.AsymmetricKey;
import model.key.AsymmetricKeyHelper;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.lang.Exception;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

public class SymmetricAlgorithm extends AAlgorithm {
    private javax.crypto.Cipher cipherIn;
    private javax.crypto.Cipher cipherOut;

    public SymmetricAlgorithm(Cipher cipher, Mode mode, Padding padding, Size keySize, Size ivSize) {
        super();
        String transformation = cipher.getName() + "/" + mode.getName() + "/" + padding.getName();

        if (mode == Mode.NONE) {
            transformation = cipher.getName();

        }
        this.key = new AsymmetricKey(new AsymmetricKeyHelper(cipher, keySize, transformation, ivSize));
    }

    @Override
    public void genKey() {
        AsymmetricKeyHelper asymmetricKeyHelper = (AsymmetricKeyHelper) this.key.getKey();
        try {
            genKeySize(asymmetricKeyHelper);
            genIv(asymmetricKeyHelper);
            genCipher(asymmetricKeyHelper);
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

    private void genIv(AsymmetricKeyHelper asymmetricKeyHelper) {
        if (asymmetricKeyHelper.getIvSize() == 0) {
            return;
        }
        byte[] b = new byte[asymmetricKeyHelper.getIvSize()];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(b);
        asymmetricKeyHelper.setIvParameterSpec(new IvParameterSpec(b));
    }

    private void genKeySize(AsymmetricKeyHelper asymmetricKeyHelper) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(asymmetricKeyHelper.getCipher().getName());
        keyGenerator.init(asymmetricKeyHelper.getKeySize());
        asymmetricKeyHelper.setSecretKey(keyGenerator.generateKey());
    }

    private void genCipher(AsymmetricKeyHelper asymmetricKeyHelper) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException {
        cipherIn = javax.crypto.Cipher.getInstance(asymmetricKeyHelper.getTransformation());
        cipherOut = javax.crypto.Cipher.getInstance(asymmetricKeyHelper.getTransformation());
        if (asymmetricKeyHelper.getIvParameterSpec() == null) {
            cipherIn.init(javax.crypto.Cipher.ENCRYPT_MODE, asymmetricKeyHelper.getSecretKey());
            cipherOut.init(javax.crypto.Cipher.DECRYPT_MODE, asymmetricKeyHelper.getSecretKey());
            return;
        }
        cipherIn.init(javax.crypto.Cipher.ENCRYPT_MODE, asymmetricKeyHelper.getSecretKey(), asymmetricKeyHelper.getIvParameterSpec());
        cipherOut.init(javax.crypto.Cipher.DECRYPT_MODE, asymmetricKeyHelper.getSecretKey(), asymmetricKeyHelper.getIvParameterSpec());

    }

    @Override
    public String encrypt(String input) throws IllegalBlockSizeException {
        byte[] dataEncrypt = input.getBytes();
//        byte[] encrypted = null;
//        try {
//            encrypted = cipherIn.doFinal(dataEncrypt);
//        } catch (IllegalBlockSizeException e) {
////            throw new RuntimeException(e);
//            throw new IllegalBlockSizeException("The block size is not suitable.Please change block size or adding padding mode");
//        } catch (BadPaddingException e) {
//            throw new RuntimeException(e);
//        }
        BufferedInputStream bis = new BufferedInputStream(new CipherInputStream(new ByteArrayInputStream(dataEncrypt), cipherIn));
        int read = 0;
        byte[] encrypted = null;
        byte[] buffered = new byte[1024];
        try {
            while ((read = bis.read(buffered)) != -1) {
                encrypted = expand(encrypted, buffered, read);
            }
        } catch (IOException e) {
            throw new IllegalBlockSizeException("The block size is not suitable.Please change block size or adding padding mode");
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

    public String decrypt(String encryptInput) throws IllegalBlockSizeException {
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
            throw new IllegalBlockSizeException("The block size is not suitable.Please change block size or adding padding mode");
        }
        return new String(decrypted);
    }

    @Override
    public Cipher getCipher() {
        return ((AsymmetricKeyHelper) this.getKey()).getCipher();
    }

    @Override
    public boolean encryptFile(String fileIn, String fileEncrypt) {
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileIn));
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileEncrypt));
            CipherOutputStream cos = new CipherOutputStream(bos, cipherIn);
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = bis.read(buffer)) != -1) {
                cos.write(buffer, 0, read);
            }
            bis.close();
            cos.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean decryptFile(String fileEncrypt, String fileDecrypted) {
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileEncrypt));
            CipherInputStream cis = new CipherInputStream(bis, cipherOut);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileDecrypted));
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = cis.read(buffer)) != -1) {
                bos.write(buffer, 0, read);
            }
            cis.close();
            bos.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        IAlgorithms algorithms = new SymmetricAlgorithm(Cipher.BLOWFISH, Mode.ECB, Padding.PKCS5Padding, Size.Size_8, Size.Size_0);
        algorithms.genKey();
        String encrypt = null;
        try {
            encrypt = algorithms.encrypt("Hello World");
        } catch (IllegalBlockSizeException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(encrypt);
//        System.out.println(algorithms.decrypt(encrypt));


    }

}
