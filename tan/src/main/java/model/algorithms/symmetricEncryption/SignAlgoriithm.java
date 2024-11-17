package model.algorithms.symmetricEncryption;

import model.algorithms.AAlgorithm;
import model.common.Cipher;

import javax.crypto.IllegalBlockSizeException;

public class SignAlgoriithm extends AAlgorithm {
    @Override
    public void genKey() {

    }

    @Override
    public String encrypt(String input) throws IllegalBlockSizeException {
        return "";
    }

    @Override
    public Cipher getCipher() {
        return null;
    }

    @Override
    public void updateKey(Object[] key) {

    }
}
