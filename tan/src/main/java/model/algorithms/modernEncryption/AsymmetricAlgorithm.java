package model.algorithms.modernEncryption;

import model.algorithms.AAlgorithm;
import model.common.*;
import model.key.AsymmetricKey;
import model.key.AsymmetricKeyHelper;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class AsymmetricAlgorithm extends AAlgorithm {
    private javax.crypto.Cipher cipherPublic; // Cipher cho mã hóa với khóa công khai
    private javax.crypto.Cipher cipherPrivate; // Cipher cho giải mã với khóa riêng

    /**
     * Khởi tạo thuật toán mã hóa bất đối xứng với các tham số bao gồm cipher, mode, padding và kích thước khóa.
     *
     * @param cipher Loại cipher (mã hóa) được sử dụng (ví dụ: RSA, DSA,...).
     * @param mode Chế độ hoạt động của thuật toán mã hóa (ví dụ: ECB, CBC,...).
     * @param padding Phương thức padding được sử dụng (ví dụ: PKCS1, NoPadding,...).
     * @param keySize Kích thước khóa (ví dụ: 2048 bit).
     */
    public AsymmetricAlgorithm(Cipher cipher, Mode mode, Padding padding, Size keySize) {
        super(); // Gọi constructor của lớp cha (CipherAlgorithm)
        this.key = new AsymmetricKey(new AsymmetricKeyHelper(cipher, mode, padding, keySize));
    }

    /**
     * Tạo khóa mới cho thuật toán mã hóa bất đối xứng, bao gồm cả việc tạo cặp khóa công khai và khóa riêng.
     * Nếu có lỗi xảy ra trong quá trình tạo khóa, sẽ ném ra các ngoại lệ liên quan.
     */
    @Override
    public void genKey() {
        AsymmetricKeyHelper asymmetricKeyHelper = (AsymmetricKeyHelper) this.key.getKey();
        try {
            genkey(asymmetricKeyHelper); // Tạo cặp khóa công khai và khóa riêng
            genCipher(asymmetricKeyHelper); // Khởi tạo các Cipher cho mã hóa và giải mã
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidKeySpecException e) {
            throw new RuntimeException(e); // Ném ngoại lệ nếu có lỗi trong quá trình tạo khóa hoặc cipher
        }
    }

    /**
     * Tạo cặp khóa công khai và khóa riêng sử dụng KeyPairGenerator.
     *
     * @param asymmetricKeyHelper Tham chiếu đến đối tượng chứa thông tin về cipher và khóa.
     * @throws NoSuchAlgorithmException Nếu thuật toán khóa không tồn tại.
     */
    private void genkey(AsymmetricKeyHelper asymmetricKeyHelper) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(asymmetricKeyHelper.getCipher().getName());
        keyPairGenerator.initialize(asymmetricKeyHelper.getKeySize().getBit());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        asymmetricKeyHelper.setPrivateKey(keyPair.getPrivate());
        asymmetricKeyHelper.setPublicKey(keyPair.getPublic());
    }

    /**
     * Khởi tạo cipher cho mã hóa và giải mã dựa trên khóa công khai và khóa riêng.
     *
     * @param asymmetricKeyHelper Tham chiếu đến đối tượng chứa thông tin về cipher và khóa.
     * @throws NoSuchAlgorithmException Nếu thuật toán không tồn tại.
     * @throws NoSuchPaddingException Nếu padding không hợp lệ.
     * @throws InvalidKeyException Nếu khóa không hợp lệ.
     * @throws InvalidKeySpecException Nếu thông số khóa không hợp lệ.
     */
    private void genCipher(AsymmetricKeyHelper asymmetricKeyHelper) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        cipherPublic = javax.crypto.Cipher.getInstance(asymmetricKeyHelper.getTransformation());
        cipherPrivate = javax.crypto.Cipher.getInstance(asymmetricKeyHelper.getTransformation());
        if (asymmetricKeyHelper.getPublicKey() == null || asymmetricKeyHelper.getPrivateKey() == null) {
            return; // Nếu không có khóa công khai hoặc khóa riêng thì không khởi tạo cipher
        }

        cipherPublic.init(javax.crypto.Cipher.ENCRYPT_MODE, asymmetricKeyHelper.getPublicKey()); // Khởi tạo cipher cho mã hóa với khóa công khai
        cipherPrivate.init(javax.crypto.Cipher.DECRYPT_MODE, asymmetricKeyHelper.getPrivateKey()); // Khởi tạo cipher cho giải mã với khóa riêng
    }

    /**
     * Mã hóa chuỗi văn bản sử dụng thuật toán mã hóa bất đối xứng.
     *
     * @param input Chuỗi văn bản cần mã hóa.
     * @return Chuỗi đã mã hóa ở dạng Base64.
     * @throws IllegalBlockSizeException Nếu kích thước khối không phù hợp với kích thước khóa.
     */
    @Override
    public String encrypt(String input) throws IllegalBlockSizeException {
        AsymmetricKeyHelper asymmetricKeyHelper = (AsymmetricKeyHelper) this.key.getKey();
        String[] keys = asymmetricKeyHelper.getKeys();
        byte[] encrypted;
        try {
            encrypted = cipherPublic.doFinal(input.getBytes(StandardCharsets.UTF_8)); // Mã hóa chuỗi văn bản
        } catch (BadPaddingException e) {
            throw new IllegalBlockSizeException("The input is not suitable for the key size. Please using padding mode"); // Nếu có lỗi về padding
        }
        return Base64.getEncoder().encodeToString(encrypted); // Trả về chuỗi mã hóa ở định dạng Base64
    }

    /**
     * Giải mã chuỗi đã mã hóa bằng thuật toán mã hóa bất đối xứng.
     *
     * @param encryptInput Chuỗi đã mã hóa (Base64).
     * @return Chuỗi đã giải mã.
     * @throws IllegalBlockSizeException Nếu kích thước khối không phù hợp với kích thước khóa.
     */
    @Override
    public String decrypt(String encryptInput) throws IllegalBlockSizeException {
        byte[] decrypted;
        try {
            decrypted = cipherPrivate.doFinal(Base64.getDecoder().decode(encryptInput)); // Giải mã chuỗi đã mã hóa
        } catch (BadPaddingException e) {
            throw new IllegalBlockSizeException("The input is not suitable for the key size. Please using padding mode"); // Nếu có lỗi về padding
        }

        return new String(decrypted, StandardCharsets.UTF_8); // Trả về chuỗi đã giải mã
    }

    /**
     * Lấy loại cipher (thuật toán mã hóa) đang sử dụng.
     *
     * @return Loại cipher (Asymmetric Cipher).
     */
    @Override
    public ICipherEnum getCipher() {
        return ((AsymmetricKeyHelper) this.key.getKey()).getCipher(); // Trả về cipher đang sử dụng
    }

    /**
     * Cập nhật khóa cho thuật toán mã hóa bất đối xứng.
     *
     * @param key Mảng các đối tượng mới để cập nhật khóa.
     */
    @Override
    public void updateKey(Object[] key) {
        AsymmetricKeyHelper asymmetricKeyHelper = (AsymmetricKeyHelper) this.key.getKey();

        if (key.length == 4) {
            try {
                genCipher(asymmetricKeyHelper); // Cập nhật cipher với khóa mới
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidKeySpecException e) {
                throw new RuntimeException(e); // Ném ngoại lệ nếu có lỗi
            }
            return;
        }

        asymmetricKeyHelper.setKeySize((Size) key[0]);
        asymmetricKeyHelper.setMode((Mode) key[1]);
        asymmetricKeyHelper.setPadding((Padding) key[2]);

        try {
            genkey(asymmetricKeyHelper); // Tạo lại cặp khóa với cài đặt mới
            genCipher(asymmetricKeyHelper); // Cập nhật lại cipher với khóa mới
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidKeySpecException e) {
            throw new RuntimeException(e); // Ném ngoại lệ nếu có lỗi
        }
    }

    /**
     * Kiểm tra tính hợp lệ của khóa.
     *
     * @return true nếu khóa hợp lệ, false nếu khóa không hợp lệ.
     */
    @Override
    public boolean validation() throws ClassNotFoundException {
        if (key == null) {
            throw new ClassNotFoundException("Key is not initialized");
        }
        return true; // Kiểm tra xem khóa có tồn tại không
    }

    public static void main(String[] args) {
        AsymmetricAlgorithm asymmetricAlgorithm = new AsymmetricAlgorithm(Cipher.RSA, Mode.ECB, Padding.PKCS1Padding, Size.Size_256);
        asymmetricAlgorithm.genKey();
        String input = "Hello";
        String encrypt = null;
        try {
            encrypt = asymmetricAlgorithm.encrypt(input);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }
        System.out.println(encrypt);
        try {
            System.out.println(asymmetricAlgorithm.decrypt(encrypt));
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }

    }
}
