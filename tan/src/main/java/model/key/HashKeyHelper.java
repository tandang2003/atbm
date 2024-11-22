package model.key;

import model.common.Cipher;

public class HashKeyHelper {
    private Cipher key;

    public HashKeyHelper(Cipher cipher) {
        this.key= cipher;
    }

    public Cipher getKey() {
        return key;
    }
}
