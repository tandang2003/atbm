package model.algorithms;

import model.common.Exception;
import model.key.IKey;

public interface IAlgorithms {
    void genKey();

    void loadKey(IKey key);

    String encrypt(String input);

    String decrypt(String encryptInput);

   default boolean encryptFile(String input, String output){
       throw new RuntimeException(Exception.UNSUPPORTED_METHOD);
   }

    default boolean decryptFile(String input, String output){
        throw new RuntimeException(Exception.UNSUPPORTED_METHOD);

    }
}
