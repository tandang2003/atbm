package model.key;

import model.common.Alphabet;
import model.common.Hash;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;

public class HashKeyHelper implements Serializable {
    private Hash key;
    private byte[] state;
    private boolean isHMAC;
    private boolean isHex;
    private String provider;
    private String keyHmac;

    public HashKeyHelper(Hash cipher, boolean isHex, boolean isHMAC, String keyMac) {
        this.key = cipher;
        this.isHMAC = isHMAC;
        this.isHex = isHex;
        this.keyHmac = keyMac;
    }

    public HashKeyHelper(Hash cipher, boolean isHex, boolean isMac) {
        this.key = cipher;
        this.isHex = isHex;
        this.isHMAC = isMac;
    }

    public HashKeyHelper(Hash cipher, boolean isHex, String provider) {
        this.key = cipher;
        this.isHex = isHex;
        this.provider = provider;

    }


    public Hash getKey() {
        return key;
    }


    public String getHmac() {
        return key.getHmacFormat();
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setKeyHmac(String keyHmac) {
        this.keyHmac = keyHmac;
    }

    public void setKey(Hash key) {
        this.key = key;
    }


    public void setHMAC(boolean HMAC) {
        isHMAC = HMAC;
    }

    public void setHex(boolean hex) {
        isHex = hex;
    }

    public boolean isHex() {
        return isHex;
    }

    public boolean isHMAC() {
        return isHMAC;
    }

    public String getProvider() {
        return provider == null || provider.isEmpty() ? "" : provider;
    }

    public String getKeyHmac() {
        return keyHmac;
    }

}
