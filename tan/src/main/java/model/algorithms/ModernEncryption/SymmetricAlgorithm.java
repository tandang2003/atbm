package model.algorithms.ModernEncryption;

import model.algorithms.AAlgorithm;
import model.common.*;
import model.common.Cipher;
import model.key.SymmetricKey;
import model.key.SymmetricKeyHelper;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.lang.Exception;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SymmetricAlgorithm extends AAlgorithm {
    private javax.crypto.Cipher cipherIn;
    private javax.crypto.Cipher cipherOut;

    public SymmetricAlgorithm(Cipher cipher, Mode mode, Padding padding, Size keySize, Size ivSize) {
        super();

        this.key = new SymmetricKey(new SymmetricKeyHelper(cipher, keySize, mode, padding, ivSize));
    }

    @Override
    public void genKey() throws NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException {
        SymmetricKeyHelper symmetricKeyHelper = (SymmetricKeyHelper) this.key.getKey();
        try {
            genKeySize(symmetricKeyHelper);
            genIv(symmetricKeyHelper);
            genCipher(symmetricKeyHelper);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new NoSuchPaddingException("The padding is not suitable.Please change block size or adding padding mode");
//            throw new RuntimeException(e);

        } catch (InvalidAlgorithmParameterException e) {
            throw new IllegalBlockSizeException("The mode or padding is not suitable.Please change block size or adding padding mode");
//            throw new RuntimeException(e);

        } catch (InvalidKeyException e) {
//            throw new RuntimeException(e);
            throw new InvalidKeyException("The key is not suitable.Please change block size or adding padding mode");
        }
    }

    private void genIv(SymmetricKeyHelper symmetricKeyHelper) {
        byte[] b = new byte[symmetricKeyHelper.getIvSize()];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(b);
        symmetricKeyHelper.setIvParameterSpec(new IvParameterSpec(b));
    }

    private void genKeySize(SymmetricKeyHelper symmetricKeyHelper) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(symmetricKeyHelper.getCipher().getName());
        keyGenerator.init(symmetricKeyHelper.getKeySize());
        symmetricKeyHelper.setSecretKey(keyGenerator.generateKey());
    }

    private void genCipher(SymmetricKeyHelper symmetricKeyHelper) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException {
        cipherIn = javax.crypto.Cipher.getInstance(symmetricKeyHelper.getTransformation());
        cipherOut = javax.crypto.Cipher.getInstance(symmetricKeyHelper.getTransformation());

        if (symmetricKeyHelper.getSecretKey() == null || symmetricKeyHelper.getIvParameterSpec() == null) {
            return;
        }
        if (symmetricKeyHelper.getIvSize() == 0) {
            cipherIn.init(javax.crypto.Cipher.ENCRYPT_MODE, symmetricKeyHelper.getSecretKey());
            cipherOut.init(javax.crypto.Cipher.DECRYPT_MODE, symmetricKeyHelper.getSecretKey());
            return;
        }
        cipherIn.init(javax.crypto.Cipher.ENCRYPT_MODE, symmetricKeyHelper.getSecretKey(), symmetricKeyHelper.getIvParameterSpec());
        cipherOut.init(javax.crypto.Cipher.DECRYPT_MODE, symmetricKeyHelper.getSecretKey(), symmetricKeyHelper.getIvParameterSpec());

    }

    @Override
    public String encrypt(String input) throws IllegalBlockSizeException {
        byte[] dataEncrypt = input.getBytes(StandardCharsets.UTF_8);
        byte[] encrypted = null;
        try {
            encrypted = cipherIn.doFinal(dataEncrypt);
        } catch (BadPaddingException | IllegalBlockSizeException e) {
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
        byte[] decrypted = null;
        try {
            decrypted = cipherOut.doFinal(encrypted);
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            throw new IllegalBlockSizeException("The block size is not suitable.Please change block size or adding padding mode");
        }
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    @Override
    public ICipherEnum getCipher() {
        return ((SymmetricKeyHelper) this.getKey().getKey()).getCipher();
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

    @Override
    public void updateKey(Object[] objects) {


        SymmetricKeyHelper symmetricKeyHelper = (SymmetricKeyHelper) this.key.getKey();

        if (objects.length == 5) {
            try {
                genCipher(symmetricKeyHelper);
            } catch (NoSuchPaddingException e) {
                throw new RuntimeException(e);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (InvalidAlgorithmParameterException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeyException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        symmetricKeyHelper.setKeySize((Size) objects[0]);
        symmetricKeyHelper.setMode((Mode) objects[1]);
        symmetricKeyHelper.setPadding((Padding) objects[2]);
        symmetricKeyHelper.setIvSize((Size) objects[3]);
        try {
            genKeySize(symmetricKeyHelper);
            genIv(symmetricKeyHelper);
//            genCipher(symmetricKeyHelper);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected boolean validation() {
        return false;
    }

}
