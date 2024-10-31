package model.algorithms.classicEncryption;

import model.algorithms.IAlgorithms;
import model.key.CharacterKey;
import model.key.IKey;

import java.util.*;

import static model.common.Alphabet.*;

public class TranspositionAlgorithm implements IAlgorithms {
    IKey<Map<Integer, String>> keys;


    @Override
    public void genKey() {
        keys = new CharacterKey();
    }

    @Override
    public void loadKey(IKey key) {
        keys = key;
    }


    @Override
    public String encrypt(String input) {
        char[] textInput = input.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : textInput) {
            sb.append(encryptToken(c));
        }
        return "";
    }

    public String encryptToken(char token) {
        if (token >= UPPER_A && token <= UPPER_Z) {
            return keys.getKey().get(token - UPPER_A);
        }
        if (token >= LOWER_A && token <= LOWER_Z) {
            return String.valueOf((char) (keys.getKey().get(token - LOWER_A).charAt(0) - UPPER_A + LOWER_A));
        }
        return "";
    }

    @Override
    public String decrypt(String encryptInput) {
        char[] textInput = encryptInput.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : textInput) {
            sb.append(decryptToken(c));
        }
        return sb.toString();
    }

    public String decryptToken(char token) {
        if (token >= UPPER_A && token <= UPPER_Z) {
            return String.valueOf((char) (indexOf(token) + UPPER_A));
        }
        if (token >= LOWER_A && token <= LOWER_Z) {
            return String.valueOf((char) (indexOf(token) + LOWER_A));
        }
        return "";
    }

    private int indexOf(char token) {
        for (Map.Entry entry : keys.getKey().entrySet()) {
            if (entry.getValue().equals(String.valueOf(token).toUpperCase())) {
                return (int) entry.getKey();
            }
        }
        return -1;
    }
}
