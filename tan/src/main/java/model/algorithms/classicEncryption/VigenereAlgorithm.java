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

    public VigenereAlgorithm(List<String> arrChar, String key) {
        super();
        this.arrChar = arrChar;
        this.key = new VigenereKey(key.split(""));
    }

    public VigenereAlgorithm(List<String> arrChar) {
        super();
        this.arrChar = arrChar;
    }

    @Override
    public void genKey() {
        List<String> arrCharShuffle = new ArrayList<>(arrChar);
        Collections.shuffle(arrCharShuffle);
        key = new VigenereKey(arrCharShuffle.toArray(new String[0]));
    }

    @Override
    public String encrypt(String input) {
//        if (!validation()) throw new RuntimeException("Key is not valid");
        int[] key = transformKey();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (sb.length() < input.length()) {
            i = Math.min(input.length() - sb.length(), key.length);
            sb.append(encryptArrChar(input.substring(sb.length(), sb.length() + i), key));
        }
        return sb.toString();
    }

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

    public String encryptChar(char c, int i, boolean upperCase) {
        int crr = this.arrChar.indexOf(String.valueOf(c).toUpperCase());
        int e_index = (crr + i) % this.arrChar.size();
        if (!upperCase)
            return this.arrChar.get(e_index).toLowerCase();
        return this.arrChar.get(e_index);
    }

    @Override
    public String decrypt(String input) {
        if (validation()) throw new RuntimeException("Key is not valid");
        int[] key = transformKey();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (sb.length() < input.length()) {
            i = Math.min(input.length() - sb.length(), key.length);
            sb.append(decryptArrChar(input.substring(sb.length(), sb.length() + i), key));
        }
        return sb.toString();
    }

    @Override
    public ICipherEnum getCipher() {
        return Cipher.VIGENERE;
    }

    @Override
    public void updateKey(Object[] key) {
        this.key = new VigenereKey(((String) key[0]).split(""));
    }


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

    public String decryptChar(char c, int i, boolean upperCase) {
        int crr = this.arrChar.indexOf(String.valueOf(c).toUpperCase());
        int e_index = (crr - i + this.arrChar.size()) % this.arrChar.size();
        if (!upperCase)
            return this.arrChar.get(e_index).toLowerCase();
        return this.arrChar.get(e_index);
    }

    private int[] transformKey() {
        String[] key = (String[]) this.key.getKey();
        int[] result = new int[key.length];
        for (int i = 0; i < key.length; i++) {
            result[i] = this.arrChar.indexOf(key[i]);
        }
        return result;
    }

    @Override
    protected boolean validation() {
        if (this.key == null) return false;
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
