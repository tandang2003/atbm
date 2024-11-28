package model.algorithms.ModernEncryption;

import model.algorithms.AAlgorithm;
import model.common.Provider;
import model.common.SecureRandom;
import model.common.*;
import model.key.SignKey;
import model.key.SignKeyHelper;

import javax.crypto.IllegalBlockSizeException;
import javax.imageio.metadata.IIOMetadataFormat;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Exception;
import java.net.SocketTimeoutException;
import java.security.Signature;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

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
        genKeyPair((SignKeyHelper) this.key.getKey());
    }

    private void genKeyPair() {
        SignKeyHelper signKeyHelper = (SignKeyHelper) this.key.getKey();
        try {

            KeyPairGenerator generator = KeyPairGenerator.getInstance(signKeyHelper.getKeyPairAlgorithm().getName(), signKeyHelper.getProvider());
            if (!Provider.SUN_RSA_SIGN.getName().equals(signKeyHelper.getProvider())) {
                secureRandomObj = java.security.SecureRandom.getInstance(signKeyHelper.getSecureRandom(), signKeyHelper.getProvider());
                generator.initialize(signKeyHelper.getKeySize(), secureRandomObj);
            } else {
                generator.initialize(signKeyHelper.getKeySize());
            }
            keyPair = generator.genKeyPair();
            signKeyHelper.setPublicKey(keyPair.getPublic().getEncoded());
            signKeyHelper.setPrivateKey(keyPair.getPrivate().getEncoded());
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

    private void genKeyPair(SignKeyHelper signKeyHelper) {
        try {
            if (signKeyHelper.getPublicKey() == null || signKeyHelper.getPrivateKey() == null)
                return;
            publicKey = signKeyHelper.getPublicKey();
            privateKey = signKeyHelper.getPrivateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
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
            String result = java.util.Base64.getEncoder().encodeToString(sign);
            System.out.println(result);
            return result;
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
        if (key.length == 4) {
            genKeyPair(signKeyHelper);
            return;
        }


        signKeyHelper.setKeySize((Size) key[0]);
        signKeyHelper.setSignature((model.common.Signature) key[1]);
        signKeyHelper.setSecureRandom((SecureRandom) key[2]);

        genKeyPair();
        genSignature();
    }

    @Override
    public boolean validation() {
        return key != null;

    }

    public static void main(String[] args) throws IllegalBlockSizeException {
        SignAlgorithm signAlgoriithm = new SignAlgorithm(KeyPairAlgorithm.RSA, model.common.Signature.MD5withRSA, Provider.SUN_RSA_SIGN, SecureRandom.DRBG, Size.Size_384);
        signAlgoriithm.genKey();
        String encrypt = signAlgoriithm.encrypt("Hello World");
        System.out.println(encrypt);
//        SignAlgorithm signAlgorithm = new SignAlgorithm(KeyPairAlgorithm.RSA, model.common.Signature.SHA224withRSA, Provider.SUN_RSA_SIGN, SecureRandom.DRBG, Size.Size_128);
//        signAlgorithm.genKey();
        encrypt = signAlgoriithm.encrypt("Hello World");
        System.out.println(encrypt);

//        System.out.println(signAlgoriithm.verify("Hello World", encrypt));
    }
}
