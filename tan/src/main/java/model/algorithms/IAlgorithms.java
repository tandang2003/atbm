package model.algorithms;


import model.common.Cipher;
import model.common.Exception;
import model.key.IKey;
import observer.algorithmObserver.SubjectAlgorithm;

import java.io.File;
import java.util.List;

public interface IAlgorithms  {
    void genKey();

    void loadKey(IKey key);

    String encrypt(String input);

    String decrypt(String encryptInput);

    void setArrChar(List<String> chars);
    Cipher getCipher();
    default boolean encryptFile(String input, String output) {
        throw new RuntimeException(Exception.UNSUPPORTED_METHOD);
    }

    default boolean decryptFile(String input, String output) {
        throw new RuntimeException(Exception.UNSUPPORTED_METHOD);
    }

    IKey getKey();

    void saveKey(File selectedFile);

}
