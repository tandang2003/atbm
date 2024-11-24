package model.key;

import model.common.Alphabet;
import model.common.Hash;

import java.util.Arrays;
import java.util.stream.Collectors;

public class HashKeyHelper {
    private Hash key;
    private byte[] state;
    private byte[] hmac;
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
    }
    public HashKeyHelper(Hash cipher, boolean isHex, String provider) {
        this.key = cipher;
        this.isHex = isHex;
        this.provider = provider;

    }


    public Hash getKey() {
        return key;
    }

    public void setState(byte[] state) {
        this.state = state;
    }


    public String getHmac() {
        String name = Arrays.stream(key.getName().split("")).filter(s -> Alphabet.ENGLISH_CHAR_SET.contains(s) || Alphabet.NUMBER_CHAR_SET.contains(s)).collect(Collectors.joining());
        return "Hmac" + name;
    }

    public void setKeyHmac(String keyHmac) {
        this.keyHmac = keyHmac;
    }

    public void setKey(Hash key) {
        this.key = key;
    }

    public void setHmac(byte[] hmac) {
        this.hmac = hmac;
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

    public byte[] getState() {
        return state;
    }

    public boolean isHMAC() {
        return isHMAC;
    }

    public String getProvider() {
        return provider;
    }

    public String getKeyHmac() {
        return keyHmac;
    }
}
