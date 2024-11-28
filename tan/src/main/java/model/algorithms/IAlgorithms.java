package model.algorithms;


import model.common.Exception;
import model.common.ICipherEnum;
import model.key.IKey;

import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchProviderException;
import java.util.List;

public interface IAlgorithms {
    void genKey() throws NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchProviderException;

    void loadKey(File selectedFile) throws IOException;

    String encrypt(String input) throws IllegalBlockSizeException;

    default String decrypt(String encryptInput) throws IllegalBlockSizeException {
        throw new RuntimeException(Exception.UNSUPPORTED_METHOD);
    }

    void setArrChar(List<String> chars);

    ICipherEnum getCipher();

    default boolean encryptFile(String input, String output) throws IOException {
        throw new RuntimeException(Exception.UNSUPPORTED_METHOD);
    }

    default boolean decryptFile(String input, String output) {
        throw new RuntimeException(Exception.UNSUPPORTED_METHOD);
    }

    default String signOrHashFile(String fileIn) throws IOException {
        throw new RuntimeException(Exception.UNSUPPORTED_METHOD);
    }

    default boolean verifyFile(String fileIn, String sign) throws IOException {
        throw new RuntimeException(Exception.UNSUPPORTED_METHOD);
    }

    default boolean verify(String input, String sign) {
        throw new RuntimeException(Exception.UNSUPPORTED_METHOD);
    }

    IKey getKey();

    void saveKey(File selectedFile) throws IOException;

    boolean validation() throws ClassNotFoundException;

    void updateKey(Object[] key);
//    void loadIKey(IKey key);
}
