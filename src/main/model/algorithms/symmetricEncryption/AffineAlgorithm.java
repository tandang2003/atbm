package main.model.algorithms.symmetricEncryption;

import main.model.algorithms.AAlgorithm;
import main.model.algorithms.IAlgorithms;
import main.model.common.Alphabet;
import main.model.key.AffineKey;
import main.model.utils.MyMath;

import java.util.List;
import java.util.Random;

public class AffineAlgorithm extends AAlgorithm {

    public AffineAlgorithm(int a, int b, List<String> arrchar) {
        //TODO:  validation it need checking a and b
        key = new AffineKey(a, b);
        this.arrChar = arrchar;
    }

    public AffineAlgorithm(List<String> arrchar) {
        //TODO:  validation it need checking a and b
        this.arrChar = arrchar;
    }

    @Override
    public void genKey() {
        int a = genA(), b = new Random().nextInt(this.arrChar.size() - 1);
        this.key = new AffineKey(a, b);
    }

    private int genA() {
        int size = this.arrChar.size();
        int a = new Random().nextInt(size);
        while (MyMath.gcd(a, size) != 1) {
            a = new Random().nextInt(size);
        }
        return a;
    }

    @Override
    public String encrypt(String input) {
        StringBuilder sb = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (!this.arrChar.contains(String.valueOf(c).toUpperCase()))
                sb.append(c);
            else sb.append(encryptChar(c, Character.isUpperCase(c)));
        }
        return sb.toString();
    }

    private String encryptChar(char c, boolean upperCase) {
        int[] key = (int[]) (this.key.getKey());
        int a = key[0], b = key[1];
        int crr = this.arrChar.indexOf(String.valueOf(c).toUpperCase());
        int e_index = (a * crr + b) % this.arrChar.size();
        String e_char = this.arrChar.get(e_index);
        if (!upperCase) {
            return e_char.toLowerCase();
        }
        return e_char;
    }

    @Override
    public String decrypt(String input) {
        StringBuilder sb = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (!this.arrChar.contains(String.valueOf(c).toUpperCase()))
                sb.append(c);
            else sb.append(decryptChar(c, Character.isUpperCase(c)));
        }
        return sb.toString();
    }

    private String decryptChar(char c, boolean upperCase) {
        int size = this.arrChar.size();
        int[] key = (int[]) (this.key.getKey());
        int a = key[0], b = key[1];
        int inverseA = MyMath.inverseInZm(a, size);
        int crr = this.arrChar.indexOf(String.valueOf(c).toUpperCase());
        int de_index = (inverseA * (crr - b + size)) % size;
        String de_char = this.arrChar.get(de_index);
        if (!upperCase) {
            return de_char.toLowerCase();
        }
        return de_char;
    }

    public static void main(String[] args) {
        IAlgorithms algorithm = new AffineAlgorithm(Alphabet.VIETNAMESE_CHAR_SET);
        algorithm.genKey();
//        String input = "Tai ptit";
        String input = "Nguyễn Văn Á";
        String encrypt = algorithm.encrypt(input);
        System.out.println(encrypt);
        String decrypt = algorithm.decrypt(encrypt);
        System.out.println(decrypt);

    }
}