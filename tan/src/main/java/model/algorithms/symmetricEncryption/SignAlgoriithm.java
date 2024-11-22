package model.algorithms.symmetricEncryption;

import model.algorithms.AAlgorithm;
import model.common.Cipher;
import model.common.Size;

import javax.crypto.IllegalBlockSizeException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;

public class SignAlgoriithm extends AAlgorithm {
    SecureRandom secureRandom;
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
            generator.initialize(1024, secureRandom);
            KeyPair keyPair = generator.genKeyPair();
            signature = Signature.getInstance(cipher.getName(), prov);
            publicKey = keyPair.getPublic();
            privateKey = keyPair.getPrivate();
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
    public Cipher getCipher() {
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
        try {
            System.out.println(signAlgoriithm.signOrHashFile("/home/tan/Documents/doanweb/atbm/tan/src/main/resources/Roboto.zip"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
