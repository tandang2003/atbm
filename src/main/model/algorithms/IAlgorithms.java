package main.model.algorithms;

import main.model.common.Exception;
import main.model.key.IKey;

import java.util.List;

public interface IAlgorithms {
    void genKey();

    void loadKey(IKey key);

    String encrypt(String input);

    String decrypt(String encryptInput);

    void setArrChar(List<String> chars);

    default boolean encryptFile(String input, String output) {
        throw new RuntimeException(Exception.UNSUPPORTED_METHOD);
    }

    default boolean decryptFile(String input, String output) {
        throw new RuntimeException(Exception.UNSUPPORTED_METHOD);
    }
}
