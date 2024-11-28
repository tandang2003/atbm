package model.algorithms.modernEncryption;

import model.algorithms.AAlgorithm;
import model.common.Hash;
import model.common.ICipherEnum;
import model.key.HashKey;
import model.key.HashKeyHelper;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
/**
 * Lớp `HashAlgorithm` đại diện cho các thuật toán băm (hashing algorithms) trong hệ thống.
 * Nó cung cấp các phương thức để băm chuỗi đầu vào bằng các thuật toán khác nhau như SHA, HMAC, v.v.
 * Lớp này có thể được sử dụng để mã hóa chuỗi hoặc tệp bằng cách sử dụng các thuật toán băm khác nhau.
 *
 * Các phương thức trong lớp này hỗ trợ băm không có HMAC (Hash-based Message Authentication Code)
 * và băm với HMAC, tùy thuộc vào cấu hình của khóa. Kết quả băm có thể được mã hóa dưới dạng Base64
 * hoặc chuỗi thập lục phân (hex), tùy thuộc vào cấu hình.
 *
 * Lớp này cũng hỗ trợ băm tệp bằng cách sử dụng các thuật toán băm và HMAC.
 *
 * @see HashKey
 * @see HashKeyHelper
 */
public class HashAlgorithm extends AAlgorithm {

    private MessageDigest messageDigests;
    private Mac mac;

    /**
     * Hàm khởi tạo mặc định.
     */
    public HashAlgorithm(){

    }

    /**
     * Hàm khởi tạo với các tham số cấu hình khóa băm, gồm: thuật toán băm, chế độ hex, HMAC và khóa HMAC.
     *
     * @param cipher Thuật toán băm được sử dụng.
     * @param isHex Chế độ mã hóa kết quả dưới dạng hex.
     * @param isHMAC Chế độ sử dụng HMAC.
     * @param keyMac Khóa HMAC (nếu có).
     */
    public HashAlgorithm(Hash cipher, boolean isHex, boolean isHMAC, String keyMac) {
        this.key = new HashKey(new HashKeyHelper(cipher, isHex, isHMAC, keyMac));
    }

    /**
     * Hàm khởi tạo với các tham số cấu hình khóa băm, gồm: thuật toán băm, chế độ hex và HMAC.
     *
     * @param cipher Thuật toán băm được sử dụng.
     * @param isHex Chế độ mã hóa kết quả dưới dạng hex.
     * @param isMac Chế độ sử dụng HMAC.
     */
    public HashAlgorithm(Hash cipher, boolean isHex, boolean isMac) {
        this.key = new HashKey(new HashKeyHelper(cipher, isHex, isMac));
    }

    /**
     * Hàm khởi tạo với các tham số cấu hình khóa băm, gồm: thuật toán băm, chế độ hex và provider.
     *
     * @param cipher Thuật toán băm được sử dụng.
     * @param isHex Chế độ mã hóa kết quả dưới dạng hex.
     * @param provider Provider cho thuật toán băm.
     */
    public HashAlgorithm(Hash cipher, boolean isHex, String provider) {
        this.key = new HashKey(new HashKeyHelper(cipher, isHex, provider));
    }

    /**
     * Kiểm tra tính hợp lệ của đối tượng `HashAlgorithm`.
     *
     * @return `true` nếu đối tượng hợp lệ, ngược lại trả về `false`.
     */
    @Override
    public boolean validation() throws ClassNotFoundException {
        return key != null;
    }

    /**
     * Phương thức tạo khóa (hiện chưa được triển khai).
     */
    @Override
    public void genKey() {

    }

    /**
     * Tạo đối tượng `MessageDigest` dựa trên thuật toán băm đã chỉ định.
     */
    private void genMessageDigest() {
        try {
            messageDigests = MessageDigest.getInstance(((HashKeyHelper) key.getKey()).getKey().getName());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tạo đối tượng `Mac` (HMAC) dựa trên cấu hình khóa.
     */
    private void genHMACMessageDigest() {
        HashKeyHelper key = (HashKeyHelper) this.key.getKey();
        if (key.getKeyHmac().isEmpty()) {
            return;
        }
        try {
            SecretKeySpec secureRandom = new SecretKeySpec(key.getKeyHmac().getBytes(StandardCharsets.UTF_8), key.getHmac());
            mac = Mac.getInstance(key.getHmac());
            mac.init(secureRandom);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            mac = null;
            throw new RuntimeException(e);
        }
    }

    /**
     * Mã hóa chuỗi đầu vào bằng thuật toán băm và trả về kết quả dưới dạng Base64 hoặc hex.
     *
     * @param input Chuỗi đầu vào cần mã hóa.
     * @return Kết quả mã hóa dưới dạng chuỗi (Base64 hoặc hex).
     */
    @Override
    public String encrypt(String input) {
        byte[] digest;
        if (!((HashKeyHelper) key.getKey()).isHMAC()) {
            digest = encryptNotHMAC(input);
        } else {
            digest = encryptHMAC(input);
        }
        if (!((HashKeyHelper) key.getKey()).isHex()) {
            return Base64.getEncoder().encodeToString(digest);
        } else {
            BigInteger no = new BigInteger(1, digest);
            return no.toString(16);
        }
    }

    /**
     * Mã hóa chuỗi đầu vào với HMAC.
     *
     * @param input Chuỗi đầu vào cần mã hóa.
     * @return Kết quả mã hóa HMAC.
     */
    private byte[] encryptHMAC(String input) {
        genHMACMessageDigest();
        HashKeyHelper key = (HashKeyHelper) this.key.getKey();
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        return mac.doFinal(bytes);
    }

    /**
     * Mã hóa chuỗi đầu vào mà không sử dụng HMAC.
     *
     * @param input Chuỗi đầu vào cần mã hóa.
     * @return Kết quả mã hóa không sử dụng HMAC.
     */
    private byte[] encryptNotHMAC(String input) {
        genMessageDigest();
        HashKeyHelper key = (HashKeyHelper) this.key.getKey();
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        return messageDigests.digest(bytes);
    }

    /**
     * Băm hoặc ký tệp đầu vào và trả về kết quả dưới dạng Base64 hoặc hex.
     *
     * @param fileIn Đường dẫn tệp cần băm.
     * @return Kết quả băm tệp (Base64 hoặc hex).
     * @throws IOException Nếu có lỗi trong quá trình đọc tệp.
     */
    @Override
    public String signOrHashFile(String fileIn) throws IOException {
        if (!((HashKeyHelper) key.getKey()).isHMAC()) {
            genMessageDigest();
        } else {
            genHMACMessageDigest();
        }
        byte[] digest;
        if (!((HashKeyHelper) key.getKey()).isHMAC()) {
            digest = signOrHashFileNonHMAC(fileIn);
        } else {
            digest = signOrHashFileHMAC(fileIn);
        }
        if (!((HashKeyHelper) key.getKey()).isHex()) {
            return Base64.getEncoder().encodeToString(digest);
        } else {
            BigInteger no = new BigInteger(1, digest);
            return no.toString(16);
        }
    }

    /**
     * Băm tệp đầu vào mà không sử dụng HMAC.
     *
     * @param fileIn Đường dẫn tệp cần băm.
     * @return Kết quả băm tệp không sử dụng HMAC.
     * @throws FileNotFoundException Nếu tệp không tồn tại.
     */
    private byte[] signOrHashFileNonHMAC(String fileIn) throws FileNotFoundException {
        genMessageDigest();
        File f = new File(fileIn);
        if (!f.exists()) {
            throw new FileNotFoundException("File not found");
        }
        InputStream inputStream = new BufferedInputStream(new FileInputStream(f));
        DigestInputStream digestInputStream = new DigestInputStream(inputStream, messageDigests);
        byte[] buffer = new byte[1024];
        int read = 0;
        do {
            try {
                read = digestInputStream.read(buffer);
            } catch (IOException e) {
                throw new FileNotFoundException("Hashing process have error occur. Please check file");
            }
        } while (read != -1);
        return messageDigests.digest();
    }

    /**
     * Băm tệp đầu vào với HMAC.
     *
     * @param fileIn Đường dẫn tệp cần băm.
     * @return Kết quả băm tệp với HMAC.
     * @throws FileNotFoundException Nếu tệp không tồn tại.
     */
    private byte[] signOrHashFileHMAC(String fileIn) throws FileNotFoundException {
        genHMACMessageDigest();
        File f = new File(fileIn);
        if (!f.exists()) {
            throw new FileNotFoundException("File not found");
        }
        InputStream inputStream = new BufferedInputStream(new FileInputStream(f));
        int read = 0;
        byte[] buffer = new byte[1024];
        try {

            while ((read = inputStream.read(buffer)) != -1) {
                mac.update(buffer, 0, read);
            }
        } catch (IOException e) {
            throw new FileNotFoundException("Hashing process have error occur. Please check file");
        }
        return mac.doFinal();
    }
    /**
     * Lấy đối tượng `ICipherEnum` tương ứng từ khóa đã cấu hình.
     * Phương thức này trả về thuật toán mã hóa hoặc băm được sử dụng từ đối tượng `HashKeyHelper`.
     *
     * @return Đối tượng `ICipherEnum` đại diện cho thuật toán hiện tại.
     */
    @Override
    public ICipherEnum getCipher() {
        return ((HashKeyHelper) key.getKey()).getKey();
    }

    /**
     * Cập nhật khóa và cấu hình lại các tham số như chế độ hex, chế độ HMAC, và khóa HMAC.
     * Phương thức này sẽ thay đổi các tham số trong đối tượng `HashKeyHelper` và khởi tạo lại
     * các đối tượng `MessageDigest` hoặc `Mac` tùy thuộc vào việc có sử dụng HMAC hay không.
     *
     * @param key Mảng đối tượng chứa các tham số cần cập nhật:
     *            key[0]: chế độ hex (boolean),
     *            key[1]: chế độ HMAC (boolean),
     *            key[2]: khóa HMAC (String).
     */
    @Override
    public void updateKey(Object[] key) {
        HashKeyHelper hashKeyHelper = (HashKeyHelper) this.key.getKey();
        hashKeyHelper.setHex((boolean) key[0]);
        hashKeyHelper.setHMAC((boolean) key[1]);
        hashKeyHelper.setKeyHmac((String) key[2]);

        System.out.println(hashKeyHelper.toString());

        // Nếu không sử dụng HMAC, tạo MessageDigest mới, nếu có sử dụng HMAC, tạo Mac mới
        if (!hashKeyHelper.isHMAC()) {
            genMessageDigest();
        } else {
            genHMACMessageDigest();
        }
    }


    public static void main(String[] args) {
        HashAlgorithm algorithm = new HashAlgorithm(Hash.SHA_512_224, true, false, "hello");
//        algorithm.genKey();
        String input = "Nguyễn Văn Á";
        String encrypt = algorithm.encrypt(input);

        algorithm = new HashAlgorithm(Hash.SHA_512_224, true, false, "hello");
        System.out.println(algorithm.encrypt(input).equals(encrypt));

    }
}
