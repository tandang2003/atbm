package model.algorithms.classicEncryption;


import model.algorithms.AAlgorithm;
import model.algorithms.IAlgorithms;
import model.common.Alphabet;
import model.common.Cipher;
import model.common.ICipherEnum;
import model.key.NumberKey;

import javax.crypto.IllegalBlockSizeException;
import java.util.List;
import java.util.Random;

public class TranspositionAlgorithm extends AAlgorithm {

    public TranspositionAlgorithm(int key, List<String> chars) {
        super();
        this.key = new NumberKey(key);
        this.arrChar = chars;
    }

    public TranspositionAlgorithm(List<String> chars) {
        super();
        this.arrChar = chars;
        this.key = new NumberKey();
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

    @Override
    public ICipherEnum getCipher() {
        return Cipher.TRANSPOSITION;
    }

    @Override
    public void updateKey(Object[] key) {
        this.key = new NumberKey((int) key[0]);
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
            int r = ((crr - move - this.arrChar.size())
                    % this.arrChar.size() + this.arrChar.size());

            return this.arrChar.get(r == this.arrChar.size() ? this.arrChar.size() - 1 : r).toLowerCase();
        }
        int r = ((crr - move - this.arrChar.size())
                % this.arrChar.size() + this.arrChar.size());
        return this.arrChar.get(r == this.arrChar.size() ? this.arrChar.size() - 1 : r);

    }

    @Override
    protected boolean validation() {
        return false;
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