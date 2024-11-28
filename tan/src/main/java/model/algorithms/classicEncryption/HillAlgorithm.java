package model.algorithms.classicEncryption;


import model.algorithms.AAlgorithm;
import model.common.Algorithms;
import model.common.Alphabet;
import model.common.Cipher;
import model.common.ICipherEnum;
import model.key.HillKey;
import model.utils.MyMath;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static model.common.Algorithms.HILL_DEFAULT_PADDING;

public class HillAlgorithm extends AAlgorithm {
    /**
     * Biến kiểm tra ký tự không có trong bảng mã.
     */
    private boolean foreign=true;


    /**
     * Khởi tạo đối tượng `HillAlgorithm` với bảng ký tự và ma trận khóa cho trước.
     *
     * @param arrChar danh sách các ký tự được hỗ trợ
     * @param key     ma trận khóa dùng để mã hóa
     */
    public HillAlgorithm(List<String> arrChar, double[][] key) {
        super();
        // TODO: Kiểm tra xem ma trận khóa có thể nghịch đảo được hay không
        this.arrChar = arrChar;
        this.key = new HillKey(key);
    }

    /**
     * Khởi tạo đối tượng `HillAlgorithm` với bảng ký tự và ma trận khóa mặc định.
     *
     * @param arrChar danh sách các ký tự được hỗ trợ
     */
    public HillAlgorithm(List<String> arrChar) {
        super();
        // TODO: Kiểm tra xem ma trận khóa có thể nghịch đảo được hay không
        this.arrChar = arrChar;
        this.key = new HillKey(new double[2][2]); // Ma trận khóa mặc định kích thước 2x2
    }

    /**
     * Tự động sinh khóa ngẫu nhiên cho thuật toán Hill.
     * Đảm bảo khóa là ma trận nghịch đảo được và kích thước phù hợp với bảng ký tự.
     */
    @Override
    public void genKey() {
        double[][] key = (double[][]) this.key.getKey();
        int defaultKeyLength = key.length;
        key = new double[defaultKeyLength][defaultKeyLength];
        Random random = new Random();
        // Sinh khóa ngẫu nhiên cho đến khi tìm được ma trận nghịch đảo được
        while (!MyMath.isInvertibleMatrix(key, arrChar.size())) {
            for (int i = 0; i < defaultKeyLength; i++) {
                for (int j = 0; j < defaultKeyLength; j++) {
                    key[i][j] = random.nextInt(arrChar.size());
                }
            }
        }
        this.key = new HillKey(key);
    }

    /**
     * Mã hóa một chuỗi đầu vào bằng thuật toán Hill.
     *
     * @param input chuỗi cần mã hóa
     * @return chuỗi sau khi mã hóa
     */
    @Override
    public String encrypt(String input) {
        double[][] key = (double[][]) this.key.getKey();
        StringBuilder sb = new StringBuilder();

        // Tính số ký tự đệm cần thêm để đảm bảo chuỗi đầu vào chia hết cho kích thước ma trận khóa
        int padding = 0;
        for (int i = 0; i < input.length(); i++) {
            if (this.arrChar.contains(String.valueOf(input.charAt(i)).toUpperCase())) {
                padding++;
            }
        }
        padding = (key.length - padding % key.length) % key.length;

        int i = 0;
        String[] arr = input.split("");
        StringBuilder encrypted = new StringBuilder();

        // Mã hóa từng khối ký tự
        for (String s : arr) {
            if (this.arrChar.contains(s.toUpperCase())) i++;
            encrypted.append(s);

            if (i == key.length) {
                sb.append(encryptArrChar(encrypted.toString(), key));
                i = 0;
                encrypted.setLength(0);
            }
        }

        // Xử lý các ký tự dư cuối chuỗi
        if (i > 0) {
            sb.append(encryptArrChar(encrypted.toString(), key));
            for (int j = 0; j < padding; j++) {
                sb.append(HILL_DEFAULT_PADDING);
            }
        }
        if (foreign)
            return sb.toString();
        else
            return Arrays.stream(sb.toString().split("")).filter(e -> arrChar.contains(e.toUpperCase())).reduce("", String::concat);
    }

    /**
     * Mã hóa một khối ký tự bằng ma trận khóa.
     *
     * @param input chuỗi cần mã hóa
     * @param key   ma trận khóa
     * @return chuỗi đã mã hóa
     */
    public String encryptArrChar(String input, double[][] key) {
        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        // Lọc các ký tự thuộc bảng ký tự
        for (String s : input.split("")) {
            if (this.arrChar.contains(s.toUpperCase())) {
                temp.append(s);
            }
        }

        // Mã hóa từng khối ký tự
        while (sb.length() < temp.length()) {
            int remaining = temp.length() - sb.length();
            int blockSize = Math.min(remaining, key.length);
            String cut = temp.substring(sb.length(), sb.length() + blockSize);
            double[] inputArr = transformInput(cut, key.length);
            double[] multiplied = MyMath.multiplyVectorWithMatrix(inputArr, key);

            // Tính modulo để giới hạn giá trị trong phạm vi ký tự
            double[] result = new double[multiplied.length];
            for (int i = 0; i < result.length; i++) {
                result[i] = (multiplied[i] % arrChar.size() + arrChar.size()) % arrChar.size();
            }

            String transformed = reverseTransformInput(result, multiplied.length);

            // Ghép các ký tự đã mã hóa và ký tự không mã hóa
            for (int i = 0; i < input.length(); i++) {
                if (!this.arrChar.contains(String.valueOf(input.charAt(i)).toUpperCase())) {
                    sb.append(input.charAt(i));
                } else {
                    if (Character.isUpperCase(input.charAt(i)))
                        sb.append(transformed.charAt(0));
                    else sb.append(String.valueOf(transformed.charAt(0)).toLowerCase());
                    transformed = transformed.substring(1);
                }
            }

            if (!transformed.isEmpty()) {
                sb.append(transformed);
            }
        }
        return sb.toString();
    }

    @Override
    public String decrypt(String input) {
        // Lấy độ dài ban đầu của chuỗi đầu vào để cắt bỏ phần padding ở cuối sau
        int inputLength = input.length();
        int padding = 0;

        // Kiểm tra và loại bỏ phần padding (giả sử padding luôn ở cuối chuỗi)
        // Kiểm tra 3 ký tự cuối cùng của chuỗi đầu vào để tìm padding
        for (int i = input.length() - 1; i > input.length() - 4; i--) {
            if (input.charAt(i) == HILL_DEFAULT_PADDING.charAt(0)) {
                padding++;
            } else {
                break;
            }
        }

        // Nếu có padding, cắt bỏ nó
        if (padding > 0) {
            input = input.substring(0, input.length() - padding);
        }

        // Lấy ma trận khóa giải mã từ đối tượng HillKey
        double[][] key = (double[][]) this.key.getKey();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        String[] arr = input.split(""); // Tách chuỗi đầu vào thành một mảng các ký tự
        StringBuilder encrypted = new StringBuilder();

        // Xử lý chuỗi đầu vào theo từng khối có kích thước bằng chiều dài ma trận khóa
        for (String s : arr) {
            if (this.arrChar.contains(s.toUpperCase())) i++; // Tăng bộ đếm khi gặp ký tự hợp lệ
            encrypted.append(s);

            // Khi khối đã đủ kích thước (bằng chiều dài ma trận khóa), tiến hành giải mã
            if (i == key.length) {
                sb.append(decryptArrChar(encrypted.toString(), key)); // Giải mã khối
                i = 0; // Đặt lại bộ đếm cho khối tiếp theo
                encrypted = new StringBuilder(); // Làm mới StringBuilder tạm thời
            }
        }

        // Nếu khối cuối cùng nhỏ hơn kích thước ma trận khóa, xử lý khối đó
        if (i > 0) {
            sb.append(decryptArrChar(encrypted.toString(), key));
        }

        // Trả về chuỗi đã giải mã, loại bỏ phần padding
        return sb.substring(0, inputLength - padding - padding);
    }

    @Override
    public ICipherEnum getCipher() {
        return Cipher.HILL; // Trả về loại cipher (Hill cipher)
    }

    @Override
    public void updateKey(Object[] key) {
        this.key = new HillKey((double[][]) key[0]); // Cập nhật khóa cho cipher
        System.out.println(key[1]);
        foreign = (boolean) key[1];
    }

    // Phương thức hỗ trợ để giải mã một khối ký tự
    private String decryptArrChar(String input, double[][] key) {
        // Tính toán ma trận nghịch đảo của khóa để thực hiện giải mã
        double[][] inverse = MyMath.calculateInverseMatrix(key, arrChar.size());
        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        // Lọc ra các ký tự hợp lệ từ chuỗi đầu vào
        for (String s : input.split("")) {
            if (this.arrChar.contains(s.toUpperCase())) {
                temp.append(s);
            }
        }

        int j = 0;
        while (sb.length() < temp.length()) {
            j = Math.min(temp.length() - sb.length(), key.length); // Xác định kích thước khối
            String cut = temp.substring(sb.length(), sb.length() + j); // Lấy ra khối hiện tại

            // Chuyển khối đầu vào thành dạng số
            double[] inputArr = transformInput(cut, key.length);

            // Nhân véc-tơ đầu vào với ma trận nghịch đảo
            double[] multiplyMatrices = MyMath.multiplyVectorWithMatrix(inputArr, inverse);
            double[] result = new double[cut.length()];

            // Dùng phép modulo để đảm bảo kết quả nằm trong phạm vi ký tự hợp lệ
            for (int i = 0; i < cut.length(); i++) {
                result[i] = (multiplyMatrices[i] % arrChar.size() + arrChar.size()) % arrChar.size();
            }

            // Chuyển kết quả về lại ký tự
            String s = reverseTransformInput(result, cut.length());

            // Tạo lại chuỗi đã giải mã với việc giữ nguyên trường hợp chữ cái
            for (int i = 0; i < input.length(); i++) {
                if (!this.arrChar.contains(String.valueOf(input.charAt(i)).toUpperCase())) {
                    sb.append(input.charAt(i)); // Giữ nguyên các ký tự không phải chữ cái
                } else {
                    // Với các ký tự chữ cái, khôi phục lại chữ hoa hay chữ thường từ chuỗi đầu vào
                    if (Character.isUpperCase(input.charAt(i)))
                        sb.append(s.charAt(0)); // Chữ hoa
                    else sb.append(String.valueOf(s.charAt(0)).toLowerCase()); // Chữ thường
                    s = s.substring(1); // Xóa ký tự đã giải mã
                }
            }
        }

        return sb.toString();
    }

    // Phương thức chuyển đổi đầu vào số (từ ký tự) trở lại chuỗi ký tự thực tế
    private String reverseTransformInput(double[] input, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(arrChar.get((int) input[i])); // Chuyển từng số thành ký tự tương ứng
        }
        return sb.toString();
    }

    // Chuyển đổi ký tự thành chỉ số tương ứng trong danh sách arrChar
    private double[] transformInput(String input, int length) {
        double[] result = new double[length];
        for (int i = 0; i < input.length(); i++) {
            result[i] = arrChar.indexOf(String.valueOf(input.charAt(i)).toUpperCase()); // Tìm chỉ số của từng ký tự
        }
        return result;
    }

    @Override
    public boolean validation() throws ClassNotFoundException {
        // Kiểm tra xem ma trận khóa có khả năng nghịch đảo không, nếu không sẽ ném ngoại lệ
        if (!MyMath.isInvertibleMatrix((double[][]) key.getKey(), arrChar.size())) {
            throw new ClassNotFoundException("Key is not valid. Key must be an invertible matrix");
        }
        return true;
    }

    public static void main(String[] args) {
        // Kiểm tra việc mã hóa và giải mã bằng Hill cipher
        HillAlgorithm algorithm = new HillAlgorithm(Alphabet.ENGLISH_CHAR_SET);
        algorithm.genKey(); // Tạo khóa ngẫu nhiên để mã hóa
        String input = "hEllo.Hi"; // Chuỗi đầu vào mẫu
        String encrypt = algorithm.encrypt(input); // Mã hóa chuỗi đầu vào
        System.out.println(encrypt); // In ra chuỗi đã mã hóa
        String decrypt = algorithm.decrypt(encrypt); // Giải mã chuỗi đã mã hóa
        System.out.println(decrypt); // In ra chuỗi đã giải mã
    }

}
