package model.algorithms.symmetricEncryption;

import model.algorithms.AAlgorithm;
import model.algorithms.IAlgorithms;
import model.common.Cipher;
import model.common.Mode;
import model.common.Padding;
import model.common.Size;
import model.key.SymmetricKey;
import model.key.SymmetricKeyHelper;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
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
        String transformation = cipher.getName() + "/" + mode.getName() + "/" + padding.getName();

        if (mode == Mode.NONE) {
            transformation = cipher.getName();
        }
        this.key = new SymmetricKey(new SymmetricKeyHelper(cipher, keySize, transformation, ivSize));
    }

    @Override
    public void genKey() {
        SymmetricKeyHelper symmetricKeyHelper = (SymmetricKeyHelper) this.key.getKey();
        try {
            genKeySize(symmetricKeyHelper);
            genIv(symmetricKeyHelper);
            genCipher(symmetricKeyHelper);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
//            throw new NoSuchPaddingException("The padding is not suitable.Please change block size or adding padding mode");
            throw new RuntimeException(e);

        } catch (InvalidAlgorithmParameterException e) {
//            throw new IllegalBlockSizeException("The mode or padding is not suitable.Please change block size or adding padding mode");
            throw new RuntimeException(e);

        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
//            throw new InvalidKeyException("The key is not suitable.Please change block size or adding padding mode");
        }
    }

    private void genIv(SymmetricKeyHelper symmetricKeyHelper) {
//        if (symmetricKeyHelper.getIvSize() == 0) {
//            return;
//        }
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
            System.out.println("Key or IV is null");
            return;
        }
        if (symmetricKeyHelper.getIvSize() == 0) {
            cipherIn.init(javax.crypto.Cipher.ENCRYPT_MODE, symmetricKeyHelper.getSecretKey());
            cipherOut.init(javax.crypto.Cipher.DECRYPT_MODE, symmetricKeyHelper.getSecretKey());
            return;
        }
        System.out.println(symmetricKeyHelper.getIvSize());
        cipherIn.init(javax.crypto.Cipher.ENCRYPT_MODE, symmetricKeyHelper.getSecretKey(), symmetricKeyHelper.getIvParameterSpec());
        cipherOut.init(javax.crypto.Cipher.DECRYPT_MODE, symmetricKeyHelper.getSecretKey(), symmetricKeyHelper.getIvParameterSpec());

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
//        if (objects.length != 3) {
//            return;
//        }
        SymmetricKeyHelper symmetricKeyHelper = (SymmetricKeyHelper) this.key.getKey();
        if (objects.length >= 1) {
            System.out.println(objects[0]);
            symmetricKeyHelper.setKeySize((Size) objects[0]);
        }
        if (objects.length >= 2) {
            System.out.println(objects[1]);
            symmetricKeyHelper.setTransformation(((String) objects[1]).isEmpty() ? symmetricKeyHelper.getCipher().getName() : symmetricKeyHelper.getCipher().getName() + "/" + ((String) objects[1]));
        }
//        symmetricKeyHelper.setTransformation(symmetricKeyHelper.getCipher().getName() + "/" + ((String) objects[1]));
        if (objects.length >= 3) {
            System.out.println(objects[2]);
            symmetricKeyHelper.setIvSize((Size) objects[2]);
        }
        try {
//            genKeySize(symmetricKeyHelper);
//            genIv(symmetricKeyHelper);
            genKeySize(symmetricKeyHelper);
            genCipher(symmetricKeyHelper);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
//            throw new NoSuchPaddingException("The padding is not suitable.Please change block size or adding padding mode");
            throw new RuntimeException(e);

        } catch (InvalidAlgorithmParameterException e) {
//            throw new IllegalBlockSizeException("The mode or padding is not suitable.Please change block size or adding padding mode");
            throw new RuntimeException(e);

        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
//            throw new InvalidKeyException("The key is not suitable.Please change block size or adding padding mode");
        }
    }

    @Override
    protected boolean validation() {
        return false;
    }

    public static void main(String[] args) {
        IAlgorithms algorithms = new SymmetricAlgorithm(Cipher.AES, Mode.CTR, Padding.NoPadding, Size.Size_16, Size.Size_16);
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
