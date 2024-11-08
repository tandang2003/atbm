package main.model.algorithms.classicEncryption;

import main.model.algorithms.AAlgorithm;
import main.model.algorithms.IAlgorithms;
import main.model.common.Alphabet;
import main.model.key.NumberKey;

import java.util.List;
import java.util.Random;

public class SubstitutionAlgorithm extends AAlgorithm {

    public SubstitutionAlgorithm(int key, List<String> chars) {
        this.key = new NumberKey(key);
        this.arrChar = chars;
    }

    public SubstitutionAlgorithm(List<String> chars) {
        this.arrChar = chars;
    }

    public void genKey() {
        this.key = new NumberKey(new Random().nextInt(arrChar.size()));
    }


    @Override
    public String encrypt(String input) {

        StringBuilder sb = new StringBuilder();
        for (char c : input.toCharArray())
            if (!this.arrChar.contains(String.valueOf(c).toUpperCase()))
                sb.append(c);
            else
                sb.append(encodeChar(String.valueOf(c), Character.isUpperCase(c)));

        return sb.toString();
    }

    @Override
    public String decrypt(String input) {
        StringBuilder sb = new StringBuilder();
        for (char c : input.toCharArray())
            if (!this.arrChar.contains(String.valueOf(c).toUpperCase()))
                sb.append(c);
            else
                sb.append(decodeChar(String.valueOf(c), Character.isUpperCase(c)));
        return sb.toString();
    }


    public String encodeChar(String c, boolean isUpperCase) {
        int key = (int) this.key.getKey();
        int move = key > this.arrChar.size() ? key % this.arrChar.size() : key;
        int crr = this.arrChar.indexOf(c);
        if (!isUpperCase) {
            crr = this.arrChar.indexOf(c.toUpperCase());
            return this.arrChar.get((crr + move) % this.arrChar.size()).toLowerCase();
        }
        return this.arrChar.get((crr + move) % this.arrChar.size());
    }

    public String decodeChar(String c, boolean isUpperCase) {
        int key = (int) this.key.getKey();
        int move = key > this.arrChar.size() ? key % this.arrChar.size() : key;
        int crr = this.arrChar.indexOf(c);
        if (!isUpperCase) {
            crr = this.arrChar.indexOf(c.toUpperCase());
            return this.arrChar.get((crr - move - this.arrChar.size()) % this.arrChar.size() + this.arrChar.size()).toLowerCase();
        }
        return this.arrChar.get((crr - move - this.arrChar.size()) % this.arrChar.size() + this.arrChar.size());
    }

    public static void main(String[] args) {
        IAlgorithms algorithms = new SubstitutionAlgorithm(Alphabet.VIETNAMESE_CHAR_SET);
        algorithms.genKey();

        String input = "Nguyễn Văn Á";
        String encrypt = algorithms.encrypt(input);
        System.out.println(encrypt);
        String decrypt = algorithms.decrypt(encrypt);
        System.out.println(decrypt);

    }
}