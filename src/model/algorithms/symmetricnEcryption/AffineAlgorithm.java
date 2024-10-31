package model.algorithms.symmetricnEcryption;

import model.algorithms.IAlgorithms;
import model.key.AffineKey;
import model.key.IKey;

import java.security.Provider;
import java.security.Security;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static model.common.Alphabet.*;

public class AffineAlgorithm implements IAlgorithms {
    private IKey<int[]> key;

    @Override
    public void genKey() {
        key = new AffineKey();
    }

    @Override
    public void loadKey(IKey key) {
        this.key = key;
    }

    @Override
    public String encrypt(String input) {
        int a = key.getKey()[0];
        int b = key.getKey()[1];
        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (c >= UPPER_A && c <= UPPER_Z) {
                result.append((char) ((a * (c - UPPER_A) + b) % ENGLISH_ALPHABET + UPPER_A));
            } else if (c >= LOWER_A && c <= LOWER_Z) {
                result.append((char) ((a * (c - LOWER_A) + b) % ENGLISH_ALPHABET + LOWER_A));
            }
        }
        return result.toString();
    }

    @Override
    public String decrypt(String encryptInput) {
        int a = key.getKey()[0];
        int b = key.getKey()[1];
        StringBuilder result = new StringBuilder();
        for (char c : encryptInput.toCharArray()) {
            if (c >= UPPER_A && c <= UPPER_Z) {
//                result.append((char) ((MyMath.modInverse(a, ENGLISH_ALPHABET) * (c - UPPER_A - b + ENGLISH_ALPHABET) % ENGLISH_ALPHABET) + UPPER_A));
            } else if (c >= LOWER_A && c <= LOWER_Z) {
//                result.append((char) ((MyMath.modInverse(a, ENGLISH_ALPHABET) * (c - LOWER_A - b + ENGLISH_ALPHABET) % ENGLISH_ALPHABET) + LOWER_A));
            }
        }
        return "";
    }

    public static void main(String[] args) {
        List<String> algorithms = Arrays.stream(Security.getProviders())
                .flatMap(provider -> provider.getServices().stream())
                .filter(service -> "Cipher".equals(service.getType()))
                .map(Provider.Service::getAlgorithm)
                .collect(Collectors.toList());
        algorithms.forEach(System.out::println);
    }
}