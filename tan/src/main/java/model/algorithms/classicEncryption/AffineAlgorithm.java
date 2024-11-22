package model.algorithms.classicEncryption;

import model.algorithms.AAlgorithm;
import model.common.Cipher;
import model.common.ICipherEnum;
import model.key.AffineKey;
import model.utils.MyMath;

import java.util.List;
import java.util.Random;

public class AffineAlgorithm extends AAlgorithm {

    public AffineAlgorithm(int a, int b, List<String> arrchar) {
        super();
        //TODO:  validation it need checking a and b
        key = new AffineKey(a, b);
        this.arrChar = arrchar;
    }

    public AffineAlgorithm(List<String> arrchar) {
        super();
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
        while (MyMath.greatestCommonDivisor(a, size) != 1) {
            a = new Random().nextInt(size);
        }
        return a;
    }

    @Override
    public String encrypt(String input) {
        StringBuilder sb = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (!this.arrChar.contains(String.valueOf(c).toUpperCase())) sb.append(c);
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
            if (!this.arrChar.contains(String.valueOf(c).toUpperCase())) sb.append(c);
            else sb.append(decryptChar(c, Character.isUpperCase(c)));
        }
        return sb.toString();
    }

    @Override
    public ICipherEnum getCipher() {
        return Cipher.AFFINE;
    }

    @Override
    public void updateKey(Object[] key) {
        this.key = new AffineKey((int) key[0], (int) key[1]);
        System.out.println("Updated key for Affine Algorithm");
        System.out.println("a: " + key[0] + " b: " + key[1]);
    }


    private String decryptChar(char c, boolean upperCase) {
        int size = this.arrChar.size();
        int[] key = (int[]) (this.key.getKey());
        int a = key[0], b = key[1];
        int inverseA = MyMath.findModularInverse(a, size);
        int crr = this.arrChar.indexOf(String.valueOf(c).toUpperCase());
        int de_index = (inverseA * (crr - b + size)) % size;
        String de_char = this.arrChar.get(de_index);
        if (!upperCase) {
            return de_char.toLowerCase();
        }
        return de_char;
    }

    @Override
    protected boolean validation() {
        return false;
    }

    public static void main(String[] args) {

    }
}