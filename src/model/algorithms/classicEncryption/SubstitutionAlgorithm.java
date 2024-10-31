package model.algorithms.classicEncryption;

import model.algorithms.IAlgorithms;
import model.key.IKey;
import model.key.NumberKey;

import java.util.function.IntConsumer;

import static model.common.Alphabet.*;

public class SubstitutionAlgorithm implements IAlgorithms {
    private IKey<Integer> key;


    @Override
    public void genKey() {
        key = new NumberKey();
    }

    @Override
    public void loadKey(IKey key) {
        this.key = key;
    }


    @Override
    public String encrypt(String input) {
        int move = key.getKey() > ENGLISH_ALPHABET ? key.getKey() % ENGLISH_ALPHABET : key.getKey();
        StringBuilder sb = new StringBuilder();
        input.chars().iterator().forEachRemaining(
                (IntConsumer) c -> {
                    sb.append(encodeChar((char) c, move));
                });
        return sb.toString();
    }

    @Override
    public String decrypt(String encryptInput) {
        int move = key.getKey() > ENGLISH_ALPHABET ? key.getKey() % ENGLISH_ALPHABET : key.getKey();
        StringBuilder sb = new StringBuilder();
        encryptInput.chars().iterator().forEachRemaining(
                (IntConsumer) c -> {
                    sb.append(decodeChar((char) c, move));
                });
        return sb.toString();
    }


    public char encodeChar(char c, int move) {
        if (c >= UPPER_A && c <= UPPER_Z) {
            return (char) ((c - UPPER_A + move) % ENGLISH_ALPHABET + UPPER_A);
        } else if (c >= LOWER_A && c <= LOWER_Z) {
            return (char) ((c - LOWER_A + move) % ENGLISH_ALPHABET + LOWER_A);
        }
        return c;
    }

    public char decodeChar(char c, int move) {
        if (c >= UPPER_A && c <= UPPER_Z) {
            return (char) ((c - UPPER_Z - move) % ENGLISH_ALPHABET + UPPER_Z);
        } else if (c >= LOWER_A && c <= LOWER_Z) {
            return (char) ((c - LOWER_Z - move) % ENGLISH_ALPHABET + LOWER_Z);
        }
        return c;
    }
}