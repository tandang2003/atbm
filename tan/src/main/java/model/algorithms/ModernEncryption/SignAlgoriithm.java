package model.algorithms.ModernEncryption;

import model.algorithms.AAlgorithm;
import model.common.Cipher;
import model.common.ICipherEnum;
import model.common.Size;

import javax.crypto.IllegalBlockSizeException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;

public class SignAlgoriithm extends AAlgorithm {
    SecureRandom secureRandom;
    KeyPair keyPair;
    Signature signature;
    PublicKey publicKey;
    PrivateKey privateKey;
    Cipher cipher;
    Size keySize;
    String algRandom;
    String prov;

    public SignAlgoriithm(Cipher cipher, Size keySize, String algRandom, String prov) {
        this.cipher = cipher;
        this.algRandom = algRandom;
        this.prov = prov;
        this.keySize = keySize;

    }

    @Override
    public void genKey() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(cipher.getName(), prov);
            secureRandom = SecureRandom.getInstance(algRandom, prov);
            generator.initialize(keySize.getBit(), secureRandom);
            keyPair = generator.genKeyPair();
            signature = Signature.getInstance(cipher.getName(), prov);
            publicKey = keyPair.getPublic();
            privateKey = keyPair.getPrivate();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
    }

    private void genKeyPair() {
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }

    private void genSignature() {
        try {
            signature = Signature.getInstance(cipher.getName(), prov);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String encrypt(String input) throws IllegalBlockSizeException {
        try {
            byte[] bytes = input.getBytes();
            byte[] sign = null;
            signature.initSign(privateKey);
            signature.update(bytes);
            sign = signature.sign();
            return java.util.Base64.getEncoder().encodeToString(sign);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String signOrHashFile(String fileIn) throws IOException {
        try {
            signature.initSign(privateKey);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(fileIn));
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = bufferedInputStream.read(buffer)) != -1) {
                signature.update(buffer, 0, read);
            }
            bufferedInputStream.close();
            byte[] sign = signature.sign();
            return java.util.Base64.getEncoder().encodeToString(sign);

        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean verifyFile(String fileIn, String sign) throws IOException {
        try {

            signature.initVerify(publicKey);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(fileIn));
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = bufferedInputStream.read(buffer)) != -1) {
                signature.update(buffer, 0, read);
            }
            bufferedInputStream.close();
            return signature.verify(java.util.Base64.getDecoder().decode(sign));
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean verify(String input, String sign) {
        try {
            signature.initVerify(publicKey);
            byte[] data = input.getBytes();
            signature.update(data);
            byte[] signByte = java.util.Base64.getDecoder().decode(sign);
            return signature.verify(signByte);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ICipherEnum getCipher() {
        return null;
    }

    @Override
    public void updateKey(Object[] key) {

    }

    @Override
    protected boolean validation() {
        return false;
    }

    public static void main(String[] args) throws IllegalBlockSizeException {
        SignAlgoriithm signAlgoriithm = new SignAlgoriithm(Cipher.DSA, Size.Size_256, "SHA1PRNG", "SUN");
        signAlgoriithm.genKey();
        String encrypt = signAlgoriithm.encrypt("Hello World");
        System.out.println(encrypt);
        System.out.println(signAlgoriithm.verify("Hello World", encrypt));
    }
}
