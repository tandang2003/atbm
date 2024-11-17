package model.algorithms;


import model.common.Cipher;
import model.common.Exception;
import model.key.IKey;

import javax.crypto.IllegalBlockSizeException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IAlgorithms {
    void genKey();

    void loadKey(File selectedFile) throws ClassNotFoundException, IOException;

    String encrypt(String input) throws IllegalBlockSizeException;

    default String decrypt(String encryptInput) throws IllegalBlockSizeException {
        throw new RuntimeException(Exception.UNSUPPORTED_METHOD);
    }

    void setArrChar(List<String> chars);

    Cipher getCipher();

    default boolean encryptFile(String input, String output) throws IOException {
        throw new RuntimeException(Exception.UNSUPPORTED_METHOD);
    }

    default boolean decryptFile(String input, String output) {
        throw new RuntimeException(Exception.UNSUPPORTED_METHOD);
    }

    IKey getKey();

    void saveKey(File selectedFile) throws IOException;

    void updateKey(Object[] key);
//    void loadIKey(IKey key);
}
