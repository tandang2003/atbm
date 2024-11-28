package model.algorithms;


import model.common.Exception;
import model.common.ICipherEnum;
import model.key.IKey;

import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchProviderException;
import java.util.List;
/**
 * Giao diện cho các thuật toán mã hóa.
 * Định nghĩa các phương thức để tạo khóa, mã hóa, giải mã, và thao tác trên tệp.
 */
public interface IAlgorithms {

    /**
     * Tạo khóa mật mã cho thuật toán.
     *
     * @throws NoSuchPaddingException       nếu chế độ padding không hợp lệ.
     * @throws IllegalBlockSizeException    nếu kích thước khối không hợp lệ.
     * @throws InvalidKeyException          nếu khóa không hợp lệ.
     * @throws NoSuchProviderException      nếu không tìm thấy provider tương ứng.
     */
    void genKey() throws NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchProviderException;

    /**
     * Tải khóa từ một tệp được chỉ định.
     *
     * @param selectedFile tệp chứa khóa cần tải.
     * @throws IOException nếu tệp không tồn tại hoặc có lỗi trong quá trình đọc.
     */
    void loadKey(File selectedFile) throws IOException;

    /**
     * Mã hóa một chuỗi văn bản và trả về kết quả mã hóa.
     *
     * @param input chuỗi văn bản cần mã hóa.
     * @return chuỗi văn bản sau khi mã hóa.
     * @throws IllegalBlockSizeException nếu kích thước chuỗi đầu vào không hợp lệ.
     */
    String encrypt(String input) throws IllegalBlockSizeException;

    /**
     * Giải mã một chuỗi đã được mã hóa và trả về văn bản gốc.
     * Phương thức mặc định ném ra ngoại lệ không hỗ trợ.
     *
     * @param encryptInput chuỗi văn bản đã được mã hóa.
     * @return chuỗi văn bản sau khi giải mã.
     * @throws IllegalBlockSizeException nếu kích thước chuỗi mã hóa không hợp lệ.
     */
    default String decrypt(String encryptInput) throws IllegalBlockSizeException {
        throw new RuntimeException(Exception.UNSUPPORTED_METHOD);
    }

    /**
     * Đặt danh sách các ký tự sử dụng trong thuật toán.
     *
     * @param chars danh sách các ký tự.
     */
    void setArrChar(List<String> chars);

    /**
     * Lấy kiểu mã hóa được sử dụng bởi thuật toán.
     *
     * @return đối tượng kiểu mã hóa.
     */
    ICipherEnum getCipher();

    /**
     * Mã hóa một tệp và lưu kết quả vào vị trí được chỉ định.
     * Phương thức mặc định ném ra ngoại lệ không hỗ trợ.
     *
     * @param input  đường dẫn tệp đầu vào.
     * @param output đường dẫn lưu tệp mã hóa.
     * @return {@code true} nếu mã hóa thành công, ngược lại {@code false}.
     * @throws IOException nếu xảy ra lỗi trong quá trình mã hóa tệp.
     */
    default boolean encryptFile(String input, String output) throws IOException {
        throw new RuntimeException(Exception.UNSUPPORTED_METHOD);
    }

    /**
     * Giải mã một tệp và lưu kết quả vào vị trí được chỉ định.
     * Phương thức mặc định ném ra ngoại lệ không hỗ trợ.
     *
     * @param input  đường dẫn tệp mã hóa.
     * @param output đường dẫn lưu tệp giải mã.
     * @return {@code true} nếu giải mã thành công, ngược lại {@code false}.
     */
    default boolean decryptFile(String input, String output) {
        throw new RuntimeException(Exception.UNSUPPORTED_METHOD);
    }

    /**
     * Ký hoặc băm một tệp và trả về kết quả.
     * Phương thức mặc định ném ra ngoại lệ không hỗ trợ.
     *
     * @param fileIn đường dẫn tệp đầu vào.
     * @return chữ ký hoặc chuỗi băm của tệp.
     * @throws IOException nếu xảy ra lỗi trong quá trình xử lý tệp.
     */
    default String signOrHashFile(String fileIn) throws IOException {
        throw new RuntimeException(Exception.UNSUPPORTED_METHOD);
    }

    /**
     * Xác minh một tệp dựa trên chữ ký hoặc chuỗi băm cung cấp.
     * Phương thức mặc định ném ra ngoại lệ không hỗ trợ.
     *
     * @param fileIn đường dẫn tệp đầu vào.
     * @param sign   chữ ký hoặc chuỗi băm để xác minh.
     * @return {@code true} nếu xác minh thành công, ngược lại {@code false}.
     * @throws IOException nếu xảy ra lỗi trong quá trình xác minh.
     */
    default boolean verifyFile(String fileIn, String sign) throws IOException {
        throw new RuntimeException(Exception.UNSUPPORTED_METHOD);
    }

    /**
     * Xác minh một chuỗi văn bản dựa trên chữ ký cung cấp.
     * Phương thức mặc định ném ra ngoại lệ không hỗ trợ.
     *
     * @param input chuỗi văn bản cần xác minh.
     * @param sign  chữ ký để xác minh.
     * @return {@code true} nếu xác minh thành công, ngược lại {@code false}.
     */
    default boolean verify(String input, String sign) {
        throw new RuntimeException(Exception.UNSUPPORTED_METHOD);
    }

    /**
     * Lấy khóa mật mã được sử dụng bởi thuật toán.
     *
     * @return đối tượng khóa mật mã.
     */
    IKey getKey();

    /**
     * Lưu khóa mật mã vào một tệp chỉ định.
     *
     * @param selectedFile tệp lưu khóa.
     * @throws IOException nếu xảy ra lỗi trong quá trình lưu hoặc khóa chưa được tạo.
     */
    void saveKey(File selectedFile) throws IOException;

    /**
     * Kiểm tra trạng thái của thuật toán, đảm bảo các thành phần cần thiết đã được khởi tạo và hoạt động bình thường.
     *
     * @return {@code true} nếu thuật toán hợp lệ, ngược lại {@code false}.
     * @throws ClassNotFoundException nếu không tìm thấy các lớp định nghĩa cần thiết.
     */
    boolean validation() throws ClassNotFoundException;

    /**
     * Cập nhật khóa mật mã với các tham số mới.
     *
     * @param key mảng các tham số đại diện cho khóa.
     */
    void updateKey(Object[] key);
}

