package model.algorithms.classicEncryption;


import model.algorithms.AAlgorithm;
import model.common.Cipher;
import model.common.ICipherEnum;
import model.key.MyCharacterKey;

import java.util.*;

public class SubstitutionAlgorithm extends AAlgorithm {
    /**
     * Constructor khởi tạo đối tượng SubstitutionAlgorithm với một khóa và danh sách các ký tự.
     * Phương thức này yêu cầu kiểm tra khóa hợp lệ (mã hóa và giải mã sẽ sử dụng khóa này).
     *
     * @param keys Chuỗi khóa được sử dụng trong quá trình mã hóa/giải mã.
     * @param chars Danh sách các ký tự có thể sử dụng trong mã hóa/giải mã.
     */

    /**
     * Biến kiểm tra ký tự không có trong bảng mã.
     */
    private boolean foreign=true;

    public SubstitutionAlgorithm(String keys, List<String> chars) {
        super();
        //TODO: Kiểm tra và xác thực khóa, đảm bảo rằng khóa đầy đủ và duy nhất
        this.arrChar = chars;
        //this.key = validationKey(keys);
    }

    /**
     * Constructor khởi tạo đối tượng SubstitutionAlgorithm với chỉ danh sách các ký tự.
     * Phương thức này sẽ tạo khóa mặc định.
     *
     * @param chars Danh sách các ký tự có thể sử dụng trong mã hóa/giải mã.
     */
    public SubstitutionAlgorithm(List<String> chars) {
        super();
        this.arrChar = chars;
        this.key = new MyCharacterKey(); // Khởi tạo khóa mặc định
    }

    /**
     * Phương thức này tạo một khóa mới bằng cách xáo trộn danh sách các ký tự và ánh xạ các ký tự đã xáo trộn
     * với các ký tự ban đầu trong arrChar. Sau khi tạo khóa mới, khóa sẽ được lưu trữ trong đối tượng key.
     */
    @Override
    public void genKey() {
        List<String> arrChar = new ArrayList<>(this.arrChar);
        Collections.shuffle(arrChar); // Xáo trộn danh sách các ký tự
        Map<String, String> keys = new HashMap<>(); // Khởi tạo một bảng ánh xạ khóa
        for (int i = 0; i < arrChar.size(); i++)
            keys.put(this.arrChar.get(i), arrChar.get(i)); // Ánh xạ mỗi ký tự ban đầu với ký tự đã xáo trộn
        this.key = new MyCharacterKey(keys); // Lưu khóa vào đối tượng key
    }

    /**
     * Phương thức này kiểm tra và tạo khóa hợp lệ từ chuỗi khóa đầu vào.
     * Nếu khóa hợp lệ, nó sẽ tạo một bảng ánh xạ ký tự từ khóa và lưu trữ nó trong MyCharacterKey.
     *
     * @param loadKey Chuỗi khóa cần được kiểm tra và sử dụng để tạo khóa mới.
     * @return Đối tượng MyCharacterKey chứa bảng ánh xạ khóa hợp lệ.
     */
    private MyCharacterKey validationKey(String loadKey) {
        String k = uniqueString(loadKey); // Kiểm tra tính duy nhất của chuỗi khóa
        List<String> arChar = new ArrayList<>(this.arrChar);
        Map<String, String> keys = new HashMap<>(); // Khởi tạo bảng ánh xạ khóa
        for (String s : k.split("")) {
            arChar.remove(s.toUpperCase()); // Loại bỏ các ký tự trong khóa khỏi danh sách ký tự ban đầu
        }
        if (arChar.isEmpty()) {
            for (int i = 0; i < k.length(); i++) {
                keys.put(String.valueOf(this.arrChar.get(i)).toUpperCase(), String.valueOf(k.charAt(i)).toUpperCase());
            }
            return new MyCharacterKey(keys); // Trả về khóa hợp lệ đã tạo
        }

        // Nếu vẫn còn ký tự trong arrChar, tiếp tục tạo khóa với các ký tự còn lại
        for (int i = 0; i < k.length(); i++) {
            keys.put(arChar.get(0), String.valueOf(k.charAt(i)).toUpperCase());
            arChar.remove(0);
        }
        for (String s : arChar) {
            keys.put(s, s); // Các ký tự còn lại trong arrChar sẽ ánh xạ với chính nó
        }
        return new MyCharacterKey(keys); // Trả về khóa hợp lệ đã tạo
    }

    /**
     * Phương thức này mã hóa văn bản đầu vào bằng cách thay thế mỗi ký tự trong văn bản bằng một ký tự
     * khác dựa trên bảng khóa. Nếu ký tự không có trong bảng khóa, nó sẽ giữ nguyên.
     *
     * @param input Chuỗi văn bản cần mã hóa.
     * @return Chuỗi văn bản đã mã hóa.
     */
    @Override
    public String encrypt(String input) {
        char[] textInput = input.toCharArray(); // Chuyển đổi chuỗi văn bản thành mảng ký tự
        StringBuilder sb = new StringBuilder(); // Khởi tạo StringBuilder để lưu trữ kết quả mã hóa
        for (char c : textInput)
            if (!this.arrChar.contains(String.valueOf(c).toUpperCase()))
                sb.append(c); // Giữ nguyên các ký tự không có trong arrChar
            else
                sb.append(encryptToken(c, Character.isUpperCase(c))); // Mã hóa ký tự nếu có trong arrChar
        if (foreign)
            return sb.toString();
        else
            return Arrays.stream(sb.toString().split("")).filter(e -> arrChar.contains(e.toUpperCase())).reduce("", String::concat);

    }

    /**
     * Phương thức này mã hóa một ký tự duy nhất theo bảng khóa.
     *
     * @param token       Ký tự cần mã hóa.
     * @param isUpperCase Chỉ ra liệu ký tự là chữ hoa hay chữ thường.
     * @return Ký tự đã mã hóa.
     */
    public String encryptToken(char token, boolean isUpperCase) {
        Map<String, String> key = (Map<String, String>) ((this.key).getKey());
        if (isUpperCase)
            return key.get(String.valueOf(token)).toUpperCase(); // Mã hóa với chữ hoa
        return key.get(String.valueOf(token).toUpperCase()).toLowerCase(); // Mã hóa với chữ thường
    }

    /**
     * Phương thức này giải mã chuỗi văn bản đã mã hóa bằng cách thay thế mỗi ký tự trong văn bản mã hóa
     * bằng một ký tự gốc dựa trên bảng khóa. Nếu ký tự không có trong bảng khóa, nó sẽ giữ nguyên.
     *
     * @param encryptInput Chuỗi văn bản đã mã hóa cần giải mã.
     * @return Chuỗi văn bản đã được giải mã.
     */
    @Override
    public String decrypt(String encryptInput) {
        char[] textInput = encryptInput.toCharArray(); // Chuyển đổi chuỗi văn bản mã hóa thành mảng ký tự
        StringBuilder sb = new StringBuilder(); // Khởi tạo StringBuilder để lưu trữ kết quả giải mã
        for (char c : textInput)
            if (!this.arrChar.contains(String.valueOf(c).toUpperCase()))
                sb.append(c); // Giữ nguyên các ký tự không có trong arrChar
            else
                sb.append(decryptToken(c, Character.isUpperCase(c))); // Giải mã ký tự nếu có trong arrChar
        return sb.toString(); // Trả về chuỗi văn bản đã giải mã
    }

    /**
     * Phương thức trả về kiểu mã hóa được sử dụng trong thuật toán này.
     *
     * @return Kiểu mã hóa hiện tại, trong trường hợp này là Cipher.SUBSTITUTION.
     */
    @Override
    public ICipherEnum getCipher() {
        return Cipher.SUBSTITUTION; // Trả về kiểu mã hóa Substitution
    }

    /**
     * Phương thức cập nhật khóa cho thuật toán mã hóa.
     * Khóa được cập nhật từ đối tượng key, thông qua phương thức xác thực khóa.
     *
     * @param key Mảng đối tượng chứa khóa (chỉ có một phần tử là chuỗi khóa).
     */
    @Override
    public void updateKey(Object[] key) {
        this.key = validationKey((String) key[0]); // Cập nhật khóa sau khi xác thực
        foreign = (boolean) key[1]; // Cập nhật biến kiểm tra ký tự không có trong bảng mã
    }

    /**
     * Phương thức giải mã một ký tự duy nhất, dựa trên bảng khóa.
     * Nếu ký tự là chữ hoa hoặc chữ thường, sẽ tìm kiếm trong bảng khóa tương ứng và trả về ký tự gốc.
     *
     * @param token       Ký tự cần giải mã.
     * @param isUpperCase Biến boolean chỉ ra liệu ký tự là chữ hoa hay chữ thường.
     * @return Ký tự đã được giải mã (hoặc ký tự gốc nếu không có trong bảng khóa).
     */
    public String decryptToken(char token, boolean isUpperCase) {
        Map<String, String> key = (Map<String, String>) this.key.getKey();
        for (Map.Entry<String, String> entry : key.entrySet()) {
            if (isUpperCase)
                if (entry.getValue().equals(String.valueOf(token)))
                    return entry.getKey(); // Trả về ký tự gốc nếu tìm thấy trong bảng khóa và là chữ hoa
            if (entry.getValue().equals(String.valueOf(token).toUpperCase()))
                return entry.getKey().toLowerCase(); // Trả về ký tự gốc nếu tìm thấy trong bảng khóa và là chữ thường
        }
        return String.valueOf(token); // Trả về ký tự không thay đổi nếu không tìm thấy trong bảng khóa
    }

    /**
     * Phương thức này đảm bảo rằng chuỗi khóa được đưa vào chỉ chứa các ký tự duy nhất,
     * loại bỏ các ký tự trùng lặp.
     *
     * @param key Chuỗi khóa cần được kiểm tra và đảm bảo tính duy nhất.
     * @return Chuỗi khóa đã được loại bỏ các ký tự trùng lặp.
     */
    private String uniqueString(String key) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : key.toCharArray()) {
            if (stringBuilder.indexOf(String.valueOf(c)) != -1) continue; // Bỏ qua ký tự đã xuất hiện
            stringBuilder.append(c); // Thêm ký tự nếu chưa có trong chuỗi kết quả
        }
        return stringBuilder.toString(); // Trả về chuỗi khóa không chứa ký tự trùng lặp
    }

    /**
     * Phương thức kiểm tra tính hợp lệ của khóa.
     * Kiểm tra xem khóa có được thiết lập và không rỗng hay không. Nếu khóa không hợp lệ, ném ra ngoại lệ.
     *
     * @return true nếu khóa hợp lệ.
     * @throws ClassNotFoundException Nếu khóa không được thiết lập hoặc rỗng.
     */
    @Override
    public boolean validation() throws ClassNotFoundException {
        if (key.getKey() == null || ((Map<String, String>) key.getKey()).isEmpty())
            throw new ClassNotFoundException("Key is not set"); // Ném ngoại lệ nếu khóa không hợp lệ
        return true; // Trả về true nếu khóa hợp lệ
    }


}
