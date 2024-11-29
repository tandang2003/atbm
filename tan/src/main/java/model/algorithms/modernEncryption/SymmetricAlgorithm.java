package model.algorithms.modernEncryption;

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
import java.security.*;
import java.security.Provider;
import java.security.SecureRandom;
import java.util.Base64;
/**
 * Đại diện cho một thuật toán mã hóa đối xứng.
 * Lớp này chịu trách nhiệm tạo ra khóa, vector khởi tạo (IV), và các cipher cho mã hóa đối xứng,
 * cũng như xử lý quá trình mã hóa và giải mã sử dụng các tham số đã cho như cipher, chế độ, padding, kích thước khóa và kích thước IV.
 */
public class SymmetricAlgorithm extends AAlgorithm {
    private javax.crypto.Cipher cipherIn;
    private javax.crypto.Cipher cipherOut;

    /**
     * Constructor để khởi tạo thuật toán đối xứng với các cipher, chế độ, padding, kích thước khóa và kích thước IV cụ thể.
     *
     * @param cipher  Thuật toán cipher dùng để mã hóa và giải mã.
     * @param mode    Chế độ hoạt động của cipher (ví dụ: CBC, ECB).
     * @param padding Chế độ padding sử dụng trong cipher (ví dụ: PKCS5Padding).
     * @param keySize Kích thước của khóa mã hóa.
     * @param ivSize  Kích thước của vector khởi tạo (IV).
     */
    public SymmetricAlgorithm(Cipher cipher, Mode mode, Padding padding, Size keySize, Size ivSize) {
        super();
        this.key = new SymmetricKey(new SymmetricKeyHelper(cipher, keySize, mode, padding, ivSize));
        try {
            cipherIn = javax.crypto.Cipher.getInstance(((SymmetricKeyHelper)this.key.getKey()).getTransformation());
            cipherOut =javax.crypto.Cipher.getInstance(((SymmetricKeyHelper)this.key.getKey()).getTransformation());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Tạo ra khóa, vector khởi tạo (IV) và các cipher cho thuật toán đối xứng.
     * Phương thức này xử lý các ngoại lệ cần thiết và đảm bảo rằng các tham số của thuật toán là hợp lệ cho hoạt động.
     *
     * @throws NoSuchPaddingException   Nếu padding không hợp lệ.
     * @throws IllegalBlockSizeException Nếu kích thước khối không hợp lệ.
     * @throws InvalidKeyException       Nếu khóa không hợp lệ.
     * @throws NoSuchProviderException  Nếu nhà cung cấp không hợp lệ.
     */
    @Override
    public void genKey() throws NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchProviderException {
        SymmetricKeyHelper symmetricKeyHelper = (SymmetricKeyHelper) this.key.getKey();
        try {
            genKeySize(symmetricKeyHelper);
            genIv(symmetricKeyHelper);
            genCipher(symmetricKeyHelper);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            // Ném thông báo cụ thể cho padding không hợp lệ
            throw new NoSuchPaddingException("Padding không hợp lệ. Vui lòng thay đổi kích thước khối hoặc thêm chế độ padding.");
        } catch (InvalidAlgorithmParameterException e) {
            // Ném thông báo cụ thể cho chế độ hoặc padding không hợp lệ
            throw new IllegalBlockSizeException("Chế độ hoặc padding không hợp lệ. Vui lòng thay đổi kích thước khối hoặc thêm chế độ padding.");
        } catch (InvalidKeyException e) {
            // Ném thông báo cụ thể cho khóa không hợp lệ
            throw new InvalidKeyException("Khóa không hợp lệ. Vui lòng thay đổi kích thước khối hoặc thêm chế độ padding.");
        } catch (NoSuchProviderException e) {
            // Ném thông báo cụ thể cho nhà cung cấp không hợp lệ
            throw new NoSuchProviderException("Nhà cung cấp không hợp lệ. Vui lòng thay đổi kích thước khối hoặc thêm chế độ padding.");
        }
    }

    /**
     * Tạo vector khởi tạo (IV) cho thuật toán đối xứng.
     * IV được tạo ngẫu nhiên dựa trên kích thước IV đã cho.
     *
     * @param symmetricKeyHelper Lớp trợ giúp chứa các tham số tạo khóa và IV.
     */
    private void genIv(SymmetricKeyHelper symmetricKeyHelper) {
        byte[] b = new byte[symmetricKeyHelper.getIvSize()];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(b);
        symmetricKeyHelper.setIvParameterSpec(new IvParameterSpec(b));
    }

    /**
     * Tạo khóa bí mật cho thuật toán đối xứng.
     * Khóa được tạo ra bằng cách sử dụng đối tượng {@link KeyGenerator} dựa trên thuật toán cipher và kích thước khóa.
     *
     * @param symmetricKeyHelper Lớp trợ giúp chứa thông tin về cipher và kích thước khóa.
     * @throws NoSuchAlgorithmException Nếu thuật toán cipher không hợp lệ.
     * @throws NoSuchProviderException Nếu nhà cung cấp cipher không hợp lệ.
     */
    private void genKeySize(SymmetricKeyHelper symmetricKeyHelper) throws NoSuchAlgorithmException, NoSuchProviderException {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance(symmetricKeyHelper.getCipher().getName(), symmetricKeyHelper.getCipher().getProvider().getName());
        KeyGenerator keyGenerator = KeyGenerator.getInstance(symmetricKeyHelper.getCipher().getName());
        keyGenerator.init(symmetricKeyHelper.getKeySize());
        symmetricKeyHelper.setSecretKey(keyGenerator.generateKey());
    }

    /**
     * Tạo các cipher cho mã hóa và giải mã dựa trên các tham số đã cấu hình.
     * Khởi tạo cipher cho cả chế độ mã hóa và giải mã sử dụng khóa bí mật và IV, nếu có.
     *
     * @param symmetricKeyHelper Lớp trợ giúp chứa cấu hình cipher, khóa và IV.
     * @throws NoSuchPaddingException Nếu padding không hợp lệ.
     * @throws NoSuchAlgorithmException Nếu thuật toán cipher không hợp lệ.
     * @throws InvalidAlgorithmParameterException Nếu các tham số thuật toán không hợp lệ.
     * @throws InvalidKeyException Nếu khóa không hợp lệ.
     */
    private void genCipher(SymmetricKeyHelper symmetricKeyHelper) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException {
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
    /**
     * Mã hóa chuỗi đầu vào và trả về chuỗi đã mã hóa dưới dạng Base64.
     * Nếu có lỗi xảy ra trong quá trình mã hóa (ví dụ: kích thước khối không phù hợp), sẽ ném ra ngoại lệ {@link IllegalBlockSizeException}.
     *
     * @param input Dữ liệu đầu vào cần mã hóa.
     * @return Chuỗi đã mã hóa dưới dạng Base64.
     * @throws IllegalBlockSizeException Nếu kích thước khối không phù hợp, cần thay đổi kích thước khối hoặc thêm chế độ padding.
     */
    @Override
    public String encrypt(String input) throws IllegalBlockSizeException {
        byte[] dataEncrypt = input.getBytes(StandardCharsets.UTF_8);
        byte[] encrypted = null;
        try {
            encrypted = cipherIn.doFinal(dataEncrypt);
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            throw new IllegalBlockSizeException("The block size is not suitable. Please change block size or add padding mode.");
        }
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * Mở rộng dữ liệu byte bằng cách kết hợp dữ liệu hiện tại với mảng byte mở rộng.
     * Phương thức này được sử dụng để đảm bảo dữ liệu đủ kích thước theo yêu cầu.
     *
     * @param data Dữ liệu cần mở rộng.
     * @param expand Mảng byte mở rộng sẽ được nối vào dữ liệu.
     * @param limit Giới hạn số byte mở rộng sẽ được thêm vào.
     * @return Mảng byte đã mở rộng.
     */
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

    /**
     * Giải mã chuỗi đã mã hóa (dưới dạng Base64) và trả về dữ liệu gốc.
     * Nếu có lỗi trong quá trình giải mã (ví dụ: kích thước khối không phù hợp), sẽ ném ra ngoại lệ {@link IllegalBlockSizeException}.
     *
     * @param encryptInput Chuỗi mã hóa cần giải mã.
     * @return Chuỗi dữ liệu gốc sau khi giải mã.
     * @throws IllegalBlockSizeException Nếu kích thước khối không phù hợp, cần thay đổi kích thước khối hoặc thêm chế độ padding.
     */
    public String decrypt(String encryptInput) throws IllegalBlockSizeException {
        byte[] encrypted = Base64.getDecoder().decode(encryptInput);
        byte[] decrypted = null;
        try {
            decrypted = cipherOut.doFinal(encrypted);
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            throw new IllegalBlockSizeException("The block size is not suitable. Please change block size or add padding mode.");
        }
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    /**
     * Trả về đối tượng {@link ICipherEnum} đại diện cho cipher đang được sử dụng trong lớp này.
     *
     * @return Đối tượng {@link ICipherEnum} tương ứng với cipher hiện tại.
     */
    @Override
    public ICipherEnum getCipher() {
        return ((SymmetricKeyHelper) this.getKey().getKey()).getCipher();
    }

    /**
     * Mã hóa tệp tin đầu vào và lưu kết quả vào tệp tin đã chỉ định.
     * Phương thức này sử dụng {@link CipherOutputStream} để mã hóa dữ liệu và lưu vào tệp.
     *
     * @param fileIn Đường dẫn tệp đầu vào cần mã hóa.
     * @param fileEncrypt Đường dẫn tệp đầu ra để lưu dữ liệu đã mã hóa.
     * @return Trả về {@code true} nếu mã hóa thành công, {@code false} nếu gặp lỗi.
     * @throws IOException Nếu có lỗi trong quá trình đọc hoặc ghi tệp.
     */
    @Override
    public boolean encryptFile(String fileIn, String fileEncrypt) throws IOException {
        try {
            String nameFile = fileIn.substring(fileIn.lastIndexOf(File.separator) + 1);
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileIn));
            File file = new File(fileEncrypt + File.separator + nameFile.substring(0, nameFile.indexOf(".")) + ".tan");
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            byte[] name = nameFile.getBytes(StandardCharsets.UTF_8);
            bos.write(name.length);
            bos.write(name);
            bos.flush();
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
            throw new IOException("Error when encrypting the file");
        }
    }

    /**
     * Giải mã tệp tin đã mã hóa và lưu kết quả vào tệp tin đã chỉ định.
     * Phương thức này sử dụng {@link CipherInputStream} để giải mã dữ liệu và lưu vào tệp.
     *
     * @param fileEncrypt Đường dẫn tệp đầu vào chứa dữ liệu đã mã hóa.
     * @param fileDecrypted Đường dẫn tệp đầu ra để lưu dữ liệu đã giải mã.
     * @return Trả về {@code true} nếu giải mã thành công, {@code false} nếu gặp lỗi.
     */
    @Override
    public boolean decryptFile(String fileEncrypt, String fileDecrypted) {
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileEncrypt));
            int nameLength = bis.read();
            byte[] name = new byte[nameLength];
            bis.read(name);
            String nameFile = new String(name, StandardCharsets.UTF_8);
            CipherInputStream cis = new CipherInputStream(bis, cipherOut);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileDecrypted + File.separator + "decrypted_" + nameFile));
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

    /**
     * Cập nhật thông tin về khóa, chế độ, padding, kích thước khóa và IV của thuật toán đối xứng.
     * Phương thức này thay đổi các tham số của khóa và cipher nếu có sự thay đổi.
     *
     * @param objects Mảng đối tượng chứa các tham số mới để cập nhật.
     */
    @Override
    public void updateKey(Object[] objects) {
        SymmetricKeyHelper symmetricKeyHelper = (SymmetricKeyHelper) this.key.getKey();
//        if (objects.length == 5) {
//            try {
//                genCipher(symmetricKeyHelper);
//            } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeyException e) {
//                throw new RuntimeException(e);
//            }
//            return;
//        }
        symmetricKeyHelper.setKeySize((Size) objects[0]);
        symmetricKeyHelper.setMode((Mode) objects[1]);
        symmetricKeyHelper.setPadding((Padding) objects[2]);
        symmetricKeyHelper.setIvSize((Size) objects[3]);
        try {
            genKeySize(symmetricKeyHelper);
            genIv(symmetricKeyHelper);
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Kiểm tra tính hợp lệ của khóa hiện tại.
     * Phương thức này trả về {@code true} nếu khóa không rỗng và hợp lệ.
     *
     * @return {@code true} nếu khóa hợp lệ, {@code false} nếu khóa không hợp lệ.
     */
    @Override
    public boolean validation() throws ClassNotFoundException {
        if (key == null) {
            throw new ClassNotFoundException("Key is not initialized");
        }
        return true;
    }

}
