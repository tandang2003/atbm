package model.algorithms.classicEncryption;

import model.algorithms.AAlgorithm;
import model.common.Cipher;
import model.common.ICipherEnum;
import model.key.AffineKey;
import model.utils.MyMath;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AffineAlgorithm extends AAlgorithm {
    /**
     * Lớp {@code AffineAlgorithm} triển khai thuật toán mã hóa Affine.
     * Đây là một thuật toán mã hóa thay thế sử dụng hàm toán học để mã hóa và giải mã văn bản.
     * <p>
     * Lớp này cung cấp các chức năng:
     * <ul>
     *   <li>Tạo cặp khóa hợp lệ để mã hóa và giải mã</li>
     *   <li>Mã hóa chuỗi văn bản gốc (plaintext)</li>
     *   <li>Giải mã chuỗi đã mã hóa (ciphertext) (phương thức giả định có tồn tại)</li>
     *   <li>Kiểm tra tính hợp lệ của khóa mã hóa</li>
     * </ul>
     */

    /**
     * Biến kiểm tra ký tự không có trong bảng mã.
     */
    private boolean foreign=true;


    /**
     * Khởi tạo một đối tượng {@code AffineAlgorithm} với cặp khóa và tập ký tự được chỉ định.
     *
     * @param a       khóa nhân (phải nguyên tố cùng nhau với kích thước tập ký tự)
     * @param b       khóa dịch
     * @param arrchar danh sách các ký tự định nghĩa bảng chữ cái cho mã hóa
     */
    public AffineAlgorithm(int a, int b, List<String> arrchar) {
        super();
        // TODO: Cần kiểm tra tính hợp lệ của a và b
        key = new AffineKey(a, b);
        this.arrChar = arrchar;
    }

    /**
     * Khởi tạo một đối tượng {@code AffineAlgorithm} với cặp khóa mặc định và tập ký tự được chỉ định.
     *
     * @param arrchar danh sách các ký tự định nghĩa bảng chữ cái cho mã hóa
     */
    public AffineAlgorithm(List<String> arrchar) {
        super();
        // TODO: Cần kiểm tra tính hợp lệ của cặp khóa
        this.arrChar = arrchar;
        key = new AffineKey();
    }

    /**
     * Tạo một cặp khóa ngẫu nhiên hợp lệ cho mã hóa Affine.
     * Khóa nhân ('a') được chọn sao cho nguyên tố cùng nhau với kích thước bảng chữ cái,
     * và khóa dịch ('b') được chọn ngẫu nhiên.
     */
    @Override
    public void genKey() {
        int a = genA();
        int b = new Random().nextInt(this.arrChar.size() - 1);
        this.key = new AffineKey(a, b);
    }

    /**
     * Tạo khóa nhân ('a') hợp lệ, nguyên tố cùng nhau với kích thước bảng chữ cái.
     *
     * @return khóa nhân hợp lệ
     */
    private int genA() {
        int size = this.arrChar.size();
        int a = new Random().nextInt(size);
        while (MyMath.greatestCommonDivisor(a, size) != 1) {
            a = new Random().nextInt(size);
        }
        return a;
    }

    /**
     * Mã hóa một chuỗi văn bản gốc bằng thuật toán Affine.
     *
     * @param input chuỗi văn bản gốc cần mã hóa
     * @return chuỗi đã mã hóa
     */
    @Override
    public String encrypt(String input) {
        StringBuilder sb = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (!this.arrChar.contains(String.valueOf(c).toUpperCase())) {
                sb.append(c); // Giữ nguyên ký tự không thuộc bảng chữ cái
            } else {
                sb.append(encryptChar(c, Character.isUpperCase(c)));
            }
        }
        System.out.println(foreign);
        if (foreign) {
            return sb.toString();
        }
        return Arrays.stream(sb.toString().split("")).filter(e -> arrChar.contains(e.toUpperCase())).reduce("", String::concat);
    }

    /**
     * Mã hóa một ký tự đơn lẻ bằng thuật toán Affine.
     *
     * @param c         ký tự cần mã hóa
     * @param upperCase cho biết ký tự có phải chữ in hoa hay không
     * @return ký tự đã mã hóa dưới dạng chuỗi
     */
    private String encryptChar(char c, boolean upperCase) {
        int[] key = (int[]) (this.key.getKey());
        int a = key[0];
        int b = key[1];
        int crr = this.arrChar.indexOf(String.valueOf(c).toUpperCase());
        int e_index = (a * crr + b) % this.arrChar.size();
        String e_char = this.arrChar.get(e_index);
        if (!upperCase) {
            return e_char.toLowerCase();
        }
        return e_char;
    }
    /**
     * Giải mã một chuỗi văn bản đã mã hóa bằng thuật toán Affine.
     *
     * @param input chuỗi đã mã hóa cần giải mã
     * @return chuỗi văn bản gốc sau khi giải mã
     */
    @Override
    public String decrypt(String input) {
        StringBuilder sb = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (!this.arrChar.contains(String.valueOf(c).toUpperCase())) {
                sb.append(c); // Giữ nguyên ký tự không thuộc bảng chữ cái
            } else {
                sb.append(decryptChar(c, Character.isUpperCase(c)));
            }
        }
        return sb.toString();
    }

    /**
     * Lấy loại mã hóa được sử dụng trong lớp này.
     *
     * @return đối tượng đại diện cho thuật toán mã hóa Affine
     */
    @Override
    public ICipherEnum getCipher() {
        return Cipher.AFFINE;
    }

    /**
     * Cập nhật khóa mã hóa bằng một mảng đối tượng (key) mới.
     *
     * @param key mảng chứa hai phần tử: khóa nhân ('a') và khóa dịch ('b')
     */
    @Override
    public void updateKey(Object[] key) {
        this.key = new AffineKey((int) key[0], (int) key[1]);
        System.out.println((int) key[0]+" "+ (int) key[1]);
        foreign=(boolean) key[2];
        System.out.println(foreign);
    }

    /**
     * Giải mã một ký tự đơn lẻ bằng thuật toán Affine.
     *
     * @param c         ký tự cần giải mã
     * @param upperCase cho biết ký tự có phải chữ in hoa hay không
     * @return ký tự sau khi giải mã dưới dạng chuỗi
     */
    private String decryptChar(char c, boolean upperCase) {
        int size = this.arrChar.size();
        int[] key = (int[]) (this.key.getKey());
        int a = key[0];
        int b = key[1];
        // Tìm nghịch đảo modulo của a
        int inverseA = MyMath.findModularInverse(a, size);
        int crr = this.arrChar.indexOf(String.valueOf(c).toUpperCase());
        // Tính chỉ số giải mã
        int de_index = (inverseA * (crr - b + size)) % size;
        String de_char = this.arrChar.get(de_index);
        if (!upperCase) {
            return de_char.toLowerCase();
        }
        return de_char;
    }

    /**
     * Kiểm tra tính hợp lệ của cặp khóa hiện tại.
     *
     * @return {@code true} nếu khóa hợp lệ
     * @throws ClassNotFoundException nếu khóa không hợp lệ (khóa nhân và kích thước bảng chữ cái không nguyên tố cùng nhau)
     */
    @Override
    public boolean validation() throws ClassNotFoundException {
        if (!(MyMath.greatestCommonDivisor(((int[]) key.getKey())[0], this.arrChar.size()) == 1)) {
            throw new ClassNotFoundException("Invalid key. A and size of arrChar must be coprime");
        }
        return true;
    }


}