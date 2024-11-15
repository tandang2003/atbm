package model.common;

public enum Cipher {
    TRANSPOSITION("Transposition", "Transposition"),
    SUBSTITUTION("Substitution", "Substitution"),
    AFFINE("Affine", "Affine"),
    VIGENERE("Vigenere", "Vigenere"),
    HILL("Hill", "Hill"),
    AES("AES", "AES"),
    DES("DES", "DES"),
    BLOWFISH("Blowfish", "Blowfish"),
    RSA("RSA", "RSA"),
    DSA("DSA", "DSA"),
    DESEDE("DESede", "DESede"),
    RC2("RC2", "RC2"),
    RC4("RC4", "RC4"),
    MD5("MD5", "MD5"),
    AES_128("AES_128", "AES 128"),
    AES_192("AES_192", "AES 192"),
    AES_256("AES_256", "AES 256"),
    SHA_1("SHA-1", "SHA-1"),
    SHA_256("SHA-256", "SHA-256"),
    SHA_512("SHA-512", "SHA-512"),
    ;
    private final String name;
    private final String displayName;

    Cipher(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
