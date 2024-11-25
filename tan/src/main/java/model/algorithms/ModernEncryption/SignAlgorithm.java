package model.algorithms.ModernEncryption;

import model.algorithms.AAlgorithm;
import model.common.Provider;
import model.common.SecureRandom;
import model.common.*;
import model.key.SignKey;
import model.key.SignKeyHelper;

import javax.crypto.IllegalBlockSizeException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Signature;
import java.security.*;

public class SignAlgorithm extends AAlgorithm {
    Signature signatureObj;
    KeyPair keyPair;
    PublicKey publicKey;
    PrivateKey privateKey;
    java.security.SecureRandom secureRandomObj;

    public SignAlgorithm(KeyPairAlgorithm keyPairAlgorithm, model.common.Signature signature, Provider provider, SecureRandom secureRandom, Size size) {
        SignKeyHelper signKeyHelper = new SignKeyHelper(keyPairAlgorithm, signature, provider, secureRandom, size);
        this.key = new SignKey(signKeyHelper);
    }

    @Override
    public void genKey() {
        genKeyPair();
        genSignature();
    }

    private void genKeyPair() {
        SignKeyHelper signKeyHelper = (SignKeyHelper) this.key.getKey();
        try {

            KeyPairGenerator generator = KeyPairGenerator.getInstance(signKeyHelper.getKeyPairAlgorithm().getName(), signKeyHelper.getProvider());
            if (Provider.SUN_RSA_SIGN.getName().equals(signKeyHelper.getProvider())) {
                secureRandomObj = java.security.SecureRandom.getInstance(signKeyHelper.getSecureRandom(), Provider.SUN.getName());
            } else
                secureRandomObj = java.security.SecureRandom.getInstance(signKeyHelper.getSecureRandom(), signKeyHelper.getProvider());

            generator.initialize(signKeyHelper.getKeySize(), secureRandomObj);
            keyPair = generator.genKeyPair();
            publicKey = keyPair.getPublic();
            signKeyHelper.setPublicKey(publicKey.getEncoded());
            privateKey = keyPair.getPrivate();
            signKeyHelper.setPrivateKey(privateKey.getEncoded());
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
    }

    private void genSignature() {
        SignKeyHelper signKeyHelper = (SignKeyHelper) this.key.getKey();
        try {
            signatureObj = Signature.getInstance(signKeyHelper.getSignature(), signKeyHelper.getProvider());
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
            signatureObj.initSign(privateKey);
            signatureObj.update(bytes);
            sign = signatureObj.sign();
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
            signatureObj.initSign(privateKey);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(fileIn));
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = bufferedInputStream.read(buffer)) != -1) {
                signatureObj.update(buffer, 0, read);
            }
            bufferedInputStream.close();
            byte[] sign = signatureObj.sign();
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
            signatureObj.initVerify(publicKey);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(fileIn));
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = bufferedInputStream.read(buffer)) != -1) {
                signatureObj.update(buffer, 0, read);
            }
            bufferedInputStream.close();
            return signatureObj.verify(java.util.Base64.getDecoder().decode(sign));
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
            signatureObj.initVerify(publicKey);
            byte[] data = input.getBytes();
            signatureObj.update(data);
            byte[] signByte = java.util.Base64.getDecoder().decode(sign);
            return signatureObj.verify(signByte);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ICipherEnum getCipher() {
        return ((SignKeyHelper) this.key.getKey()).getKeyPairAlgorithm();
    }

    @Override
    public void updateKey(Object[] key) {
        SignKeyHelper signKeyHelper = (SignKeyHelper) this.key.getKey();
        signKeyHelper.setKeySize((Size) key[0]);
        signKeyHelper.setSignature((model.common.Signature) key[1]);
        signKeyHelper.setSecureRandom((SecureRandom) key[2]);

        genKeyPair();
        genSignature();
    }

    @Override
    protected boolean validation() {
        return false;
    }

    public static void main(String[] args) throws IllegalBlockSizeException {
        SignAlgorithm signAlgoriithm = new SignAlgorithm(KeyPairAlgorithm.RSA, model.common.Signature.SHA224withRSA, Provider.SUN_RSA_SIGN, SecureRandom.DRBG, Size.Size_128);
        signAlgoriithm.genKey();
        String encrypt = signAlgoriithm.encrypt("Hello World");
        System.out.println(encrypt);
//        System.out.println(encrypt1);
//        String encrypt = signAlgoriithm.encrypt("Hello World");
//        System.out.println(encrypt);
        SignAlgorithm signAlgorithm = new SignAlgorithm(KeyPairAlgorithm.RSA, model.common.Signature.SHA224withRSA, Provider.SUN_RSA_SIGN, SecureRandom.DRBG, Size.Size_128);
        signAlgorithm.genKey();
//        signAlgorithm.publicKey=signAlgoriithm.publicKey;
//        signAlgorithm.privateKey=signAlgoriithm.privateKey;
        System.out.println(signAlgorithm.verify("Hello World", encrypt));
    }
}
