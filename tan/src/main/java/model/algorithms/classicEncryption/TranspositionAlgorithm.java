package model.algorithms.classicEncryption;


import model.algorithms.AAlgorithm;
import model.algorithms.IAlgorithms;
import model.common.Alphabet;
import model.common.Cipher;
import model.common.ICipherEnum;
import model.key.NumberKey;

import javax.crypto.IllegalBlockSizeException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TranspositionAlgorithm extends AAlgorithm {

    /**
     * Khởi tạo thuật toán với khóa số và danh sách các ký tự.
     *
     * @param key Khóa số được sử dụng cho thuật toán chuyển vị.
     * @param chars Danh sách các ký tự được sử dụng trong mã hóa/giải mã.
     */
    /**
     * Biến kiểm tra ký tự không có trong bảng mã.
     */
    private boolean foreign=true;


    public TranspositionAlgorithm(int key, List<String> chars) {
        super(); // Gọi constructor của lớp cha (CipherAlgorithm)
        this.key = new NumberKey(key); // Khởi tạo khóa với giá trị được cung cấp
        this.arrChar = chars; // Khởi tạo danh sách các ký tự
    }

    /**
     * Khởi tạo thuật toán với danh sách các ký tự. Khóa được tạo ngẫu nhiên.
     *
     * @param chars Danh sách các ký tự được sử dụng trong mã hóa/giải mã.
     */
    public TranspositionAlgorithm(List<String> chars) {
        super(); // Gọi constructor của lớp cha (CipherAlgorithm)
        this.arrChar = chars; // Khởi tạo danh sách các ký tự
        this.key = new NumberKey(); // Khởi tạo khóa với một giá trị ngẫu nhiên
    }

    /**
     * Tạo một khóa ngẫu nhiên dựa trên kích thước của danh sách các ký tự.
     * Khóa được tạo ngẫu nhiên trong phạm vi kích thước của danh sách ký tự.
     */
    public void genKey() {
        this.key = new NumberKey(new Random().nextInt(arrChar.size())); // Khóa ngẫu nhiên trong phạm vi kích thước của danh sách ký tự
    }

    /**
     * Mã hóa chuỗi văn bản đầu vào bằng thuật toán chuyển vị.
     *
     * @param input Chuỗi văn bản cần mã hóa.
     * @return Chuỗi văn bản đã được mã hóa.
     */
    @Override
    public String encrypt(String input) {
        StringBuilder sb = new StringBuilder(); // StringBuilder để chứa kết quả mã hóa
        // Lặp qua từng ký tự trong chuỗi đầu vào
        for (char c : input.toCharArray()) {
            // Nếu ký tự không có trong danh sách ký tự, nó sẽ được giữ nguyên
            if (!this.arrChar.contains(String.valueOf(c).toUpperCase()))
                sb.append(c);
            else
                // Nếu có trong danh sách, nó sẽ được mã hóa
                sb.append(encodeChar(String.valueOf(c), Character.isUpperCase(c)));
        }
        if (foreign)
            return sb.toString();
        else
            return Arrays.stream(sb.toString().split("")).filter(e -> arrChar.contains(e.toUpperCase())).reduce("", String::concat);

    }

    /**
     * Giải mã chuỗi văn bản đầu vào bằng thuật toán chuyển vị.
     *
     * @param input Chuỗi văn bản cần giải mã.
     * @return Chuỗi văn bản đã được giải mã.
     */
    @Override
    public String decrypt(String input) {
        StringBuilder sb = new StringBuilder(); // StringBuilder để chứa kết quả giải mã
        // Lặp qua từng ký tự trong chuỗi đầu vào
        for (char c : input.toCharArray()) {
            // Nếu ký tự không có trong danh sách ký tự, nó sẽ được giữ nguyên
            if (!this.arrChar.contains(String.valueOf(c).toUpperCase()))
                sb.append(c);
            else
                // Nếu có trong danh sách, nó sẽ được giải mã
                sb.append(decodeChar(String.valueOf(c), Character.isUpperCase(c)));
        }
        return sb.toString(); // Trả về chuỗi đã giải mã
    }

    /**
     * Trả về kiểu của thuật toán mã hóa (chuyển vị).
     *
     * @return Kiểu cipher là TRANSPOSITION.
     */
    @Override
    public ICipherEnum getCipher() {
        return Cipher.TRANSPOSITION; // Trả về kiểu thuật toán chuyển vị
    }

    /**
     * Cập nhật khóa cho thuật toán.
     *
     * @param key Mảng đối tượng chứa khóa mới.
     */
    @Override
    public void updateKey(Object[] key) {
        this.key = new NumberKey((int) key[0]); // Cập nhật khóa với giá trị mới
        foreign = (boolean) key[1];
    }

    /**
     * Mã hóa một ký tự sử dụng thuật toán chuyển vị.
     *
     * @param c           Ký tự cần mã hóa.
     * @param isUpperCase Kiểm tra xem ký tự là chữ hoa hay chữ thường.
     * @return Ký tự đã mã hóa.
     */
    public String encodeChar(String c, boolean isUpperCase) {
        int key = (int) this.key.getKey(); // Lấy khóa
        int move = key > this.arrChar.size() ? key % this.arrChar.size() : key; // Tính toán di chuyển ký tự
        int crr = this.arrChar.indexOf(c); // Lấy chỉ số của ký tự trong danh sách ký tự
        // Kiểm tra xem ký tự là chữ hoa hay chữ thường để mã hóa đúng
        if (!isUpperCase) {
            crr = this.arrChar.indexOf(c.toUpperCase());
            return this.arrChar.get((crr + move) % this.arrChar.size()).toLowerCase();
        }
        return this.arrChar.get((crr + move) % this.arrChar.size()); // Trả về ký tự mã hóa
    }

    /**
     * Giải mã một ký tự sử dụng thuật toán chuyển vị.
     *
     * @param c           Ký tự cần giải mã.
     * @param isUpperCase Kiểm tra xem ký tự là chữ hoa hay chữ thường.
     * @return Ký tự đã giải mã.
     */
    public String decodeChar(String c, boolean isUpperCase) {
        int key = (int) this.key.getKey(); // Lấy khóa
        int move = key > this.arrChar.size() ? key % this.arrChar.size() : key; // Tính toán di chuyển ký tự
        int crr = this.arrChar.indexOf(c); // Lấy chỉ số của ký tự trong danh sách ký tự
        // Kiểm tra xem ký tự là chữ hoa hay chữ thường để giải mã đúng
        if (!isUpperCase) {
            crr = this.arrChar.indexOf(c.toUpperCase());
            int r = ((crr - move - this.arrChar.size()) % this.arrChar.size() + this.arrChar.size());
            return this.arrChar.get(r == this.arrChar.size() ? 0 : r).toLowerCase(); // Trả về ký tự giải mã
        }
        int r = ((crr - move - this.arrChar.size()) % this.arrChar.size() + this.arrChar.size());
        return this.arrChar.get(r == this.arrChar.size() ? 0 : r); // Trả về ký tự giải mã
    }

    /**
     * Kiểm tra tính hợp lệ của khóa. Khóa không được là 0.
     *
     * @return true nếu khóa hợp lệ, false nếu không hợp lệ.
     */
    @Override
    public boolean validation() throws ClassNotFoundException {
        if ((int) this.key.getKey() == 0)
            throw new ClassNotFoundException("Key is not set"); // Ném ngoại lệ nếu khóa không hợp lệ
        return true; // Kiểm tra xem khóa có hợp lệ không
    }

    public static void main(String[] args) {
//        System.out.println((-26) % 26);
//        hiwxxhdlgsihcasknaobsfoihsdasd
        IAlgorithms algorithms = new TranspositionAlgorithm(Alphabet.ENGLISH_CHAR_SET);
        ((NumberKey) algorithms.getKey()).setKey(20);
//
        String input = "ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz";
        String encrypt = null;
        try {
            encrypt = algorithms.encrypt(input);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }
        System.out.println(encrypt);
        String decrypt = null;
        try {
            decrypt = algorithms.decrypt(encrypt);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }
        System.out.println(decrypt);

    }
}