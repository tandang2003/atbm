package model.algorithms.modernEncryption;

import at.favre.lib.crypto.bcrypt.BCrypt;
import model.algorithms.AAlgorithm;
import model.common.Hash;
import model.common.ICipherEnum;
import model.key.HashKey;
import model.key.HashKeyHelper;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Base64;
/**
 * Lớp này đại diện cho thuật toán băm Bcrypt, sử dụng để mã hóa và xác minh dữ liệu đầu vào.
 * Đây là một thuật toán băm mật khẩu mạnh mẽ với khả năng bảo mật cao, giúp bảo vệ thông tin nhạy cảm.
 */
public class BcryptHashAlgorithm extends HashAlgorithm {

    /**
     * Hàm tạo khởi tạo đối tượng `BcryptHashAlgorithm`.
     * Nó tạo ra một đối tượng `HashKey` với các thông số mặc định cho thuật toán Bcrypt.
     */
    public BcryptHashAlgorithm() {
        this.key = new HashKey(new HashKeyHelper(Hash.BCrypt, false, false));
    }

    /**
     * Phương thức này không được hỗ trợ trong lớp này.
     * Phương thức `genKey` được ghi đè nhưng không thực hiện bất kỳ thao tác nào.
     *
     * @throws UnsupportedOperationException Nếu được gọi, phương thức sẽ ném ra ngoại lệ.
     */
    @Override
    public void genKey() {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Phương thức mã hóa chuỗi đầu vào sử dụng thuật toán Bcrypt.
     * Bcrypt sử dụng một hàm băm để mã hóa chuỗi đầu vào và trả về giá trị đã mã hóa.
     * Nếu yêu cầu, giá trị sẽ được trả về dưới dạng Base64 hoặc Hex.
     *
     * @param input Chuỗi đầu vào cần mã hóa.
     * @return Chuỗi đã mã hóa dưới dạng Base64 hoặc Hex.
     */
    @Override
    public String encrypt(String input) {
        byte[] digest = BCrypt.withDefaults().hash(12, input.toCharArray());
        if (!((HashKeyHelper) key.getKey()).isHex()) {
            return Base64.getEncoder().encodeToString(digest);
        } else {
            BigInteger no = new BigInteger(1, digest);
            return no.toString(16);
        }
    }

    /**
     * Phương thức này không được hỗ trợ trong lớp này.
     * Phương thức `signOrHashFile` không thực hiện bất kỳ thao tác nào đối với tệp tin đầu vào.
     *
     * @param fileIn Đường dẫn đến tệp tin cần mã hóa hoặc ký.
     * @throws UnsupportedOperationException Nếu được gọi, phương thức sẽ ném ra ngoại lệ.
     */
    @Override
    public String signOrHashFile(String fileIn) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Phương thức trả về thuật toán băm đang sử dụng, trong trường hợp này là Bcrypt.
     *
     * @return Đối tượng `ICipherEnum` đại diện cho thuật toán băm Bcrypt.
     */
    @Override
    public ICipherEnum getCipher() {
        return Hash.BCrypt;
    }

    /**
     * Phương thức xác minh tính hợp lệ của chuỗi đầu vào so với giá trị đã băm.
     *
     * @param input Chuỗi đầu vào cần xác minh.
     * @param sign Giá trị đã băm cần kiểm tra.
     * @return true nếu chuỗi đầu vào khớp với giá trị đã băm, false nếu không.
     */
    @Override
    public boolean verify(String input, String sign) {
        byte[] digest = Base64.getDecoder().decode(sign);
        return BCrypt.verifyer().verify(input.toCharArray(), digest).verified;
    }

    /**
     * Phương thức cập nhật các thông số khóa và cấu hình liên quan đến thuật toán băm.
     * Các tham số được cập nhật sẽ thay đổi cách thức mã hóa hoặc băm.
     *
     * @param key Mảng chứa các tham số cấu hình mới cho thuật toán, bao gồm các tùy chọn như chế độ Hex, HMAC và khóa HMAC.
     */
    @Override
    public void updateKey(Object[] key) {
        HashKeyHelper hashKeyHelper = (HashKeyHelper) this.key.getKey();
        hashKeyHelper.setHex((boolean) key[0]);
        hashKeyHelper.setHMAC((boolean) key[1]);
        hashKeyHelper.setKeyHmac((String) key[2]);
    }

    /**
     * Phương thức xác minh tính hợp lệ của thuật toán băm.
     *
     * @return true nếu thuật toán và khóa băm hợp lệ, false nếu không.
     */
    @Override
    public boolean validation() throws ClassNotFoundException {
        if (key == null) {
            throw new ClassNotFoundException("Key is not initialized");
        }
        return true;
    }

    /**
     * Phương thức chính để kiểm thử lớp `BcryptHashAlgorithm`.
     * Mã hóa một chuỗi đầu vào và sau đó kiểm tra tính hợp lệ của giá trị băm so với chuỗi đầu vào.
     *
     * @param args Tham số dòng lệnh, trong trường hợp này là không cần thiết.
     */
    public static void main(String[] args) {
        BcryptHashAlgorithm bcryptHashAlgorithm = new BcryptHashAlgorithm();
        String input = "/home/tan/Documents/doanweb/atbm/tan/src/main/resources/Roboto.zip";
        String encrypted = null;
        encrypted = bcryptHashAlgorithm.encrypt(input);
        System.out.println(bcryptHashAlgorithm.verify(input, encrypted));
    }
}

