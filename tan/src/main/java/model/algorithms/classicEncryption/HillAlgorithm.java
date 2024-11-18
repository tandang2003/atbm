package model.algorithms.classicEncryption;


import model.algorithms.AAlgorithm;
import model.common.Algorithms;
import model.common.Alphabet;
import model.common.Cipher;
import model.key.HillKey;
import model.utils.MyMath;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Random;

import static model.common.Algorithms.HILL_DEFAULT_PADDING;

public class HillAlgorithm extends AAlgorithm {
    private String encrypt;

    public HillAlgorithm(List<String> arrChar, double[][] key) {
        super();
        //TODO check key can inverse
        this.arrChar = arrChar;
        this.key = new HillKey(key);
    }

    public HillAlgorithm(List<String> arrChar) {
        super();
        //TODO check key can inverse
        this.arrChar = arrChar;
    }

    @Override
    public void genKey() {
        int defaultKeyLength = Algorithms.HILL_ALGORITHM_DEFAULT_KEY_LENGTH;
        double[][] key = new double[defaultKeyLength][defaultKeyLength];
        Random random = new Random();
        while (!MyMath.isInverseMatrix(key, arrChar.size())) {
            for (int i = 0; i < defaultKeyLength; i++) {
                for (int j = 0; j < defaultKeyLength; j++) {
                    key[i][j] = random.nextInt(arrChar.size());
                }
            }
        }
        this.key = new HillKey(key);
    }

    @Override
    public String encrypt(String input) {
        double[][] key = (double[][]) this.key.getKey();
        StringBuilder sb = new StringBuilder();
        int padding = 0;
        for (int i = 0; i < input.length(); i++) {
            if (this.arrChar.contains(String.valueOf(input.charAt(i)).toUpperCase()))
                padding++;
        }
        padding = (key.length - padding % key.length) % key.length;
        int i = 0;
        String[] arr = input.split("");
        StringBuilder encrypted = new StringBuilder();
        for (String s : arr) {
            if (this.arrChar.contains(s.toUpperCase())) i++;
            encrypted.append(s);
            if (i == key.length) {
                sb.append(encryptArrChar(encrypted.toString(), key));
                i = 0;
                encrypted.setLength(0);
            }
        }
        if (i > 0) {
            sb.append(encryptArrChar(encrypted.toString(), key));
            for (int j = 0; j < padding; j++) {
                sb.append(HILL_DEFAULT_PADDING);
            }
        }
        return sb.toString();
    }


    public String encryptArrChar(String input, double[][] key) {
        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        for (String s : input.split("")) {
            if (this.arrChar.contains(s.toUpperCase())) {
                temp.append(s);
            }
        }

        int j = 0;
        while (sb.length() < temp.length()) {
            j = Math.min(temp.length() - sb.length(), key.length);
            String cut = temp.substring(sb.length(), sb.length() + j);
            double[] inputArr = transformInput(cut, key.length);
            double[] multiplyMatrices = MyMath.multiplyMatrices(inputArr, key);
            double[] result = new double[multiplyMatrices.length];
            for (int i = 0; i < result.length; i++) {
                result[i] = (multiplyMatrices[i] % arrChar.size() + arrChar.size()) % arrChar.size();
            }
            String s = reverseTransformInput(result, multiplyMatrices.length);
            for (int i = 0; i < input.length(); i++) {
                if (!this.arrChar.contains(String.valueOf(input.charAt(i)).toUpperCase())) {
                    sb.append(input.charAt(i));
                } else {
                    sb.append(s.charAt(0));
                    s = s.substring(1);
                }
            }
            if (!s.isEmpty()) {
                sb.append(s);
            }
        }
        return sb.toString();
    }

    @Override
    public String decrypt(String input) {
        int inputLength = input.length();
        //count padding and remove padding
        int padding = 0;
        for (int i = input.length() - 1; i > input.length() - 4; i--) {
            if (input.charAt(i) == HILL_DEFAULT_PADDING.charAt(0)) {
                padding++;
            } else {
                break;
            }
        }
        if (padding > 0) {
            input = input.substring(0, input.length() - padding);
        }

//        if (input.endsWith(HILL_DEFAULT_PADDING)) {
//            input = input.substring(0, input.indexOf(HILL_DEFAULT_PADDING));
//        }

//        if (this.encrypt != null && this.encrypt.startsWith(input)) {
//            input = this.encrypt;
//        }
        double[][] key = (double[][]) this.key.getKey();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        String[] arr = input.split("");
        StringBuilder encrypted = new StringBuilder();
        for (String s : arr) {
            if (this.arrChar.contains(s.toUpperCase())) i++;
            encrypted.append(s);
            if (i == key.length) {
                sb.append(decryptArrChar(encrypted.toString(), key));
                i = 0;
                encrypted = new StringBuilder();
            }
        }
        if (i > 0) {
            sb.append(decryptArrChar(encrypted.toString(), key));
        }

        return sb.substring(0, inputLength - padding - padding);
    }

    @Override
    public Cipher getCipher() {
        return Cipher.HILL;
    }

    @Override
    public void updateKey(Object[] key) {
        this.key = new HillKey((double[][]) key);
    }

    private String decryptArrChar(String input, double[][] key) {
        double[][] inverse = MyMath.inverseMatrix(key, arrChar.size());
        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        for (String s : input.split("")) {
            if (this.arrChar.contains(s.toUpperCase())) {
                temp.append(s);
            }
        }
        int j = 0;
        while (sb.length() < temp.length()) {
            j = Math.min(temp.length() - sb.length(), key.length);
            String cut = temp.substring(sb.length(), sb.length() + j);
            double[] inputArr = transformInput(cut, key.length);
            double[] multiplyMatrices = MyMath.multiplyMatrices(inputArr, inverse);
            double[] result = new double[cut.length()];
            for (int i = 0; i < cut.length(); i++) {
                result[i] = (multiplyMatrices[i] % arrChar.size() + arrChar.size()) % arrChar.size();
            }
            String s = reverseTransformInput(result, cut.length());
//            sb.append(reverseTransformInput(result, cut.length()));
            for (int i = 0; i < input.length(); i++) {
                if (!this.arrChar.contains(String.valueOf(input.charAt(i)).toUpperCase())) {
                    sb.append(input.charAt(i));
                } else {
                    sb.append(s.charAt(0));
                    s = s.substring(1);
                }
            }
        }
        return sb.toString();
    }

    private String reverseTransformInput(double[] input, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(arrChar.get((int) input[i]));
        }
        return sb.toString();
    }

    private double[] transformInput(String input, int length) {
        double[] result = new double[length];
        for (int i = 0; i < input.length(); i++) {
            result[i] = arrChar.indexOf(String.valueOf(input.charAt(i)).toUpperCase());
        }
        return result;
    }

//    private int[][] transformKey() {
//        int[][] key = ((HillKey) this.key).getKey();
//        int[][] result = new int[key.length][key.length];
//        for (int i = 0; i < key.length; i++) {
//            for (int j = 0; j < key.length; j++) {
//                result[i][j] = Integer.parseInt(key[i][j]);
//            }
//        }
//        return result;
//    }

    @Override
    protected boolean validation() {
        return false;
    }

    public static void main(String[] args) {
        HillAlgorithm algorithm = new HillAlgorithm(Alphabet.VIETNAMESE_CHAR_SET);
        algorithm.genKey();
        String input = "Nguyễn Đình Lam sinh ngày 01 tháng 01 năm 1999 1";
        System.out.println(input.length());
//        String input = "DHNONGLAM";
        String encrypt = algorithm.encrypt(input);
        System.out.println(encrypt);
        String decrypt = algorithm.decrypt(encrypt);
        System.out.println(decrypt);

    }
}
