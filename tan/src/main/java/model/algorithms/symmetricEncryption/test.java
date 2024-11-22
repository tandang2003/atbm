package model.algorithms.symmetricEncryption;

import java.security.*;

public class test {
    KeyPair generator;
    SecureRandom secureRandom;
    Signature signature;
    PublicKey publicKey;
    PrivateKey privateKey;

    public test() {

    }

    public test(String alg, String algRandom, String prov) throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance(alg, prov);
        secureRandom = SecureRandom.getInstance(algRandom, prov);
        generator.initialize(1024, secureRandom);
        KeyPair keyPair = generator.genKeyPair();
        signature = Signature.getInstance(alg, prov);
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }

    public String encrypt(String input) {
        byte[] bytes = input.getBytes();
        byte[] sign = null;
        try {
            signature.initSign(privateKey);
            signature.update(bytes);
            sign = signature.sign();
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        }
        return java.util.Base64.getEncoder().encodeToString(sign);
    }

    public static void main(String[] args) {
        try {
            test test = new test("DSA", "SHA1PRNG", "SUN");
            System.out.println(test.encrypt("Hello World"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
    }
}
