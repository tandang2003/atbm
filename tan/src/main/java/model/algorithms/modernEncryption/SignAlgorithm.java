package model.algorithms.modernEncryption;

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
import java.security.spec.InvalidKeySpecException;
/**
 * Lớp này cung cấp các thuật toán ký số (digital signature) sử dụng các thuật toán mã hóa khóa công khai (public key encryption).
 * Nó hỗ trợ việc tạo cặp khóa (public/private key), tạo chữ ký số và ký chuỗi hoặc tệp.
 */
public class SignAlgorithm extends AAlgorithm {
    Signature signatureObj;
    KeyPair keyPair;
    PublicKey publicKey;
    PrivateKey privateKey;
    java.security.SecureRandom secureRandomObj;

    /**
     * Khởi tạo đối tượng `SignAlgorithm` với các tham số liên quan đến thuật toán cặp khóa, chữ ký và các tham số khác.
     *
     * @param keyPairAlgorithm Thuật toán tạo cặp khóa.
     * @param signature Thuật toán chữ ký.
     * @param provider Nhà cung cấp (provider) sử dụng cho các thuật toán.
     * @param secureRandom Đối tượng SecureRandom để sinh các số ngẫu nhiên.
     * @param size Kích thước khóa.
     */
    public SignAlgorithm(KeyPairAlgorithm keyPairAlgorithm, model.common.Signature signature, Provider provider, SecureRandom secureRandom, Size size) {
        SignKeyHelper signKeyHelper = new SignKeyHelper(keyPairAlgorithm, signature, provider, secureRandom, size);
        this.key = new SignKey(signKeyHelper);
    }

    /**
     * Tạo cặp khóa và chữ ký mới, sau đó cập nhật thông tin cặp khóa.
     */
    @Override
    public void genKey() {
        genKeyPair();
        genSignature();
        genKeyPair((SignKeyHelper) this.key.getKey());
//        System.out.println(((SignKeyHelper) this.key.getKey()).toString());

    }

    /**
     * Tạo cặp khóa (public key và private key) sử dụng các thông số trong `SignKeyHelper`.
     * Nếu sử dụng thuật toán khác với `SUN_RSA_SIGN`, SecureRandom sẽ được khởi tạo.
     */
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

    /**
     * Tạo đối tượng `Signature` sử dụng thuật toán và nhà cung cấp đã được cấu hình trong `SignKeyHelper`.
     */
    private void genSignature() {
        SignKeyHelper signKeyHelper = (SignKeyHelper) this.key.getKey();
        try {
            signatureObj = Signature.getInstance(model.common.Signature.SHA1_WITH_DSA.getName(), Provider.SUN.getName());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Cập nhật khóa công khai và khóa riêng từ thông tin trong `SignKeyHelper`.
     *
     * @param signKeyHelper Đối tượng chứa các thông tin khóa công khai và khóa riêng.
     */
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

    /**
     * Mã hóa (ký số) chuỗi đầu vào bằng khóa riêng và trả về chữ ký số dưới dạng Base64.
     *
     * @param input Chuỗi cần được ký.
     * @return Chữ ký số của chuỗi đầu vào dưới dạng Base64.
     * @throws IllegalBlockSizeException Nếu kích thước khối không hợp lệ khi ký.
     */
    @Override
    public String encrypt(String input) throws IllegalBlockSizeException {
        try {
            genSignature();
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

    /**
     * Ký số tệp đầu vào và trả về chữ ký số dưới dạng Base64.
     *
     * @param fileIn Đường dẫn tới tệp cần ký.
     * @return Chữ ký số của tệp dưới dạng Base64.
     * @throws IOException Nếu có lỗi khi đọc tệp.
     */
    @Override
    public String signOrHashFile(String fileIn) throws IOException {
        try {
            genSignature();
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
    /**
     * Verifies the signature of a file against the provided signature.
     *
     * @param fileIn The path to the file to be verified.
     * @param sign   The signature to verify against, encoded in Base64 format.
     * @return {@code true} if the signature is valid, {@code false} otherwise.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    @Override
    public boolean verifyFile(String fileIn, String sign) throws IOException {
        try {
            signatureObj.initVerify(publicKey);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(fileIn));
            byte[] buffer = new byte[1024];
            int read = 0;
            // Reading the file and updating the signature verification object
            while ((read = bufferedInputStream.read(buffer)) != -1) {
                signatureObj.update(buffer, 0, read);
            }
            bufferedInputStream.close();
            // Verifying the signature using the provided Base64-encoded signature
            return signatureObj.verify(java.util.Base64.getDecoder().decode(sign));
        } catch (InvalidKeyException | SignatureException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Verifies the signature of an input string against the provided signature.
     *
     * @param input The input string to be verified.
     * @param sign  The signature to verify against, encoded in Base64 format.
     * @return {@code true} if the signature is valid, {@code false} otherwise.
     */
    @Override
    public boolean verify(String input, String sign) {
        try {
            signatureObj.initVerify(publicKey);
            byte[] data = input.getBytes();
            signatureObj.update(data);
            byte[] signByte = java.util.Base64.getDecoder().decode(sign);
            // Verifying the signature using the provided Base64-encoded signature
            return signatureObj.verify(signByte);
        } catch (InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the cipher algorithm associated with this signing operation.
     *
     * @return The cipher algorithm used for key pair generation.
     */
    @Override
    public ICipherEnum getCipher() {
        return ((SignKeyHelper) this.key.getKey()).getKeyPairAlgorithm();
    }

    /**
     * Updates the key parameters for this signing algorithm.
     *
     * If the key length is 4, it regenerates the key pair using the existing key helper.
     * Otherwise, it updates the key size, signature algorithm, and secure random object, and regenerates the key pair and signature.
     *
     * @param key An array of objects containing the new key parameters. The expected values are:
     *            <ul>
     *            <li>0: Size</li>
     *            <li>1: Signature</li>
     *            <li>2: SecureRandom</li>
     *            </ul>
     */
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

        System.out.println("Key updated");
        System.out.println(signKeyHelper.toString());


    }

    /**
     * Validates if the key is correctly initialized.
     *
     * @return {@code true} if the key is valid (not null), {@code false} otherwise.
     */
    @Override
    public boolean validation() throws ClassNotFoundException {
        if (key == null) {
            throw new ClassNotFoundException("Key is not initialized");
        }
        SignKeyHelper signKeyHelper = (SignKeyHelper) key.getKey();
        if  ((signKeyHelper.getPublicKeyString()) == null|| (signKeyHelper.getPrivateKeyString()) == null) {
            throw new ClassNotFoundException("Key is not initialized");
        }
        if  ((signKeyHelper.getPublicKeyString()).isEmpty() || (signKeyHelper.getPrivateKeyString()).isEmpty()) {
            throw new ClassNotFoundException("Key is not load enought key");
        }
        return true;
    }

}
