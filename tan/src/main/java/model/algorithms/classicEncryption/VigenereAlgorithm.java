package model.algorithms.classicEncryption;


import model.algorithms.AAlgorithm;
import model.algorithms.IAlgorithms;
import model.common.Alphabet;
import model.common.Cipher;
import model.common.ICipherEnum;
import model.key.VigenereKey;

import javax.crypto.IllegalBlockSizeException;
import java.util.*;

public class VigenereAlgorithm extends AAlgorithm {

    /**
     * Khởi tạo đối tượng VigenereAlgorithm với bảng ký tự và khóa.
     *
     * @param arrChar danh sách các ký tự để mã hóa và giải mã
     * @param key     chuỗi khóa sử dụng trong thuật toán
     */
    /**
     * Biến kiểm tra ký tự không có trong bảng mã.
     */
    private boolean foreign=true;


    public VigenereAlgorithm(List<String> arrChar, String key) {
        super();
        this.arrChar = arrChar;
        this.key = new VigenereKey(key.split(""));
    }

    /**
     * Khởi tạo đối tượng VigenereAlgorithm với bảng ký tự.
     * Khóa mặc định sẽ được tạo ra.
     *
     * @param arrChar danh sách các ký tự để mã hóa và giải mã
     */
    public VigenereAlgorithm(List<String> arrChar) {
        super();
        this.arrChar = arrChar;
        this.key = new VigenereKey();
    }

    /**
     * Sinh khóa ngẫu nhiên cho thuật toán mã hóa Vigenère.
     */
    @Override
    public void genKey() {
        List<String> arrCharShuffle = new ArrayList<>(arrChar);
        Collections.shuffle(arrCharShuffle);
        key = new VigenereKey(arrCharShuffle.toArray(new String[0]));
    }

    /**
     * Mã hóa văn bản đầu vào sử dụng thuật toán Vigenère.
     *
     * @param input văn bản cần mã hóa
     * @return chuỗi đã được mã hóa
     */
    @Override
    public String encrypt(String input) {
        int[] key = transformKey();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (sb.length() < input.length()) {
            i = Math.min(input.length() - sb.length(), key.length);
            sb.append(encryptArrChar(input.substring(sb.length(), sb.length() + i), key));
        }
        if (foreign)
            return sb.toString();
        else
            return Arrays.stream(sb.toString().split("")).filter(e -> arrChar.contains(e.toUpperCase())).reduce("", String::concat);

    }

    /**
     * Mã hóa các ký tự của văn bản đầu vào dựa trên khóa.
     *
     * @param input văn bản cần mã hóa
     * @param key   mảng khóa chuyển đổi
     * @return chuỗi đã được mã hóa
     */
    public String encryptArrChar(String input, int[] key) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (!this.arrChar.contains(String.valueOf(input.charAt(i)).toUpperCase()))
                sb.append(input.charAt(i));
            else
                sb.append(encryptChar(input.charAt(i), key[i], Character.isUpperCase(input.charAt(i))));
        }
        return sb.toString();
    }

    /**
     * Mã hóa một ký tự đơn lẻ dựa trên khóa.
     *
     * @param c         ký tự cần mã hóa
     * @param i         chỉ số khóa
     * @param upperCase chỉ ra liệu ký tự ban đầu có phải là chữ hoa không
     * @return ký tự đã được mã hóa
     */
    public String encryptChar(char c, int i, boolean upperCase) {
        int crr = this.arrChar.indexOf(String.valueOf(c).toUpperCase());
        int e_index = (crr + i) % this.arrChar.size();
        if (!upperCase)
            return this.arrChar.get(e_index).toLowerCase();
        return this.arrChar.get(e_index);
    }

    /**
     * Giải mã văn bản đã được mã hóa bằng thuật toán Vigenère.
     *
     * @param input văn bản đã mã hóa
     * @return chuỗi đã được giải mã
     */
    @Override
    public String decrypt(String input) {
        int[] key = transformKey();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (sb.length() < input.length()) {
            i = Math.min(input.length() - sb.length(), key.length);
            sb.append(decryptArrChar(input.substring(sb.length(), sb.length() + i), key));
        }
        return sb.toString();
    }

    /**
     * Cung cấp loại mã hóa mà thuật toán sử dụng.
     *
     * @return loại mã hóa (Vigenère)
     */
    @Override
    public ICipherEnum getCipher() {
        return Cipher.VIGENERE;
    }

    /**
     * Cập nhật khóa cho thuật toán Vigenère.
     *
     * @param key mảng đối tượng chứa khóa
     */
    @Override
    public void updateKey(Object[] key) {
        this.key = new VigenereKey(((String) key[0]).split(""));
        foreign = (boolean) key[1];
    }

    /**
     * Giải mã các ký tự của văn bản đã mã hóa.
     *
     * @param input văn bản đã mã hóa
     * @param key   mảng khóa chuyển đổi
     * @return chuỗi đã được giải mã
     */
    public String decryptArrChar(String input, int[] key) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (!this.arrChar.contains(String.valueOf(input.charAt(i)).toUpperCase()))
                sb.append(input.charAt(i));
            else
                sb.append(decryptChar(input.charAt(i), key[i], Character.isUpperCase(input.charAt(i))));
        }
        return sb.toString();
    }

    /**
     * Giải mã một ký tự đơn lẻ dựa trên khóa.
     *
     * @param c         ký tự cần giải mã
     * @param i         chỉ số khóa
     * @param upperCase chỉ ra liệu ký tự ban đầu có phải là chữ hoa không
     * @return ký tự đã được giải mã
     */
    public String decryptChar(char c, int i, boolean upperCase) {
        int crr = this.arrChar.indexOf(String.valueOf(c).toUpperCase());
        int e_index = (crr - i + this.arrChar.size()) % this.arrChar.size();
        if (!upperCase)
            return this.arrChar.get(e_index).toLowerCase();
        return this.arrChar.get(e_index);
    }

    /**
     * Chuyển đổi khóa từ chuỗi sang mảng các chỉ số tương ứng với bảng ký tự.
     *
     * @return mảng các chỉ số tương ứng với khóa
     */
    private int[] transformKey() {
        String[] key = (String[]) this.key.getKey();
        int[] result = new int[key.length];
        for (int i = 0; i < key.length; i++) {
            result[i] = this.arrChar.indexOf(key[i]);
        }
        return result;
    }

    /**
     * Kiểm tra tính hợp lệ của khóa.
     *
     * @return true nếu khóa hợp lệ, false nếu khóa không hợp lệ
     * @throws ClassNotFoundException nếu khóa không hợp lệ
     */
    @Override
    public boolean validation() throws ClassNotFoundException {
        String[] k = (String[]) key.getKey();
        if (k == null || k.length == 0)
            throw new ClassNotFoundException("Key is not valid");
        for (String s : k) {
            if (!arrChar.contains(s))
                throw new ClassNotFoundException("Key is not valid. Key must be in alphabet");
        }
        return true;
    }

    public static void main(String[] args) {
        IAlgorithms algorithm = new VigenereAlgorithm(Alphabet.VIETNAMESE_CHAR_SET, "dasdadvxc");
//        algorithm.genKey();
//        String input = "Tai ptit";
        String input = "Nguyễn Văn Á";
        String encrypt = null;
        try {
            encrypt = algorithm.encrypt(input);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }
        System.out.println(encrypt);
//        String decrypt = algorithm.decrypt(encrypt);
//        System.out.println(decrypt);

    }
}
