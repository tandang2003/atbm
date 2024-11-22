package model.common;

public enum Cipher implements ICipherEnum{
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
    //    MessageDigest
    MD2("MD2", "MD2"),
    MD5("MD5", "MD5"),
    SHA_1("SHA-1", "SHA-1"),
    SHA_224("SHA-224", "SHA-224"),
    SHA_256("SHA-256", "SHA-256"),
    SHA_384("SHA-384", "SHA-384"),
    SHA_512("SHA-512", "SHA-512"),
    SHA_512_224("SHA-512/224", "SHA-512/224"),
    SHA_512_256("SHA-512/256", "SHA-512/256"),
    SHA3_224("SHA3-224", "SHA3-224"),
    SHA3_256("SHA3-256", "SHA3-256"),
    SHA3_384("SHA3-384", "SHA3-384"),
    SHA3_512("SHA3-512", "SHA3-512"),
    //    Signature
    SHA1_WITH_DSA("SHA1withDSA", "SHA-1 with DSA"),
    SHA224_WITH_DSA("SHA224withDSA", "SHA-224 with DSA"),
    SHA256_WITH_DSA("SHA256withDSA", "SHA-256 with DSA"),
    SHA384_WITH_DSA("SHA384withDSA", "SHA-384 with DSA"),
    SHA512_WITH_DSA("SHA512withDSA", "SHA-512 with DSA"),
    NONE_WITH_DSA_IN_P1363_FORMAT("NONEwithDSAinP1363Format", "NONE with DSA in P1363 format"),
    SHA1_WITH_DSA_IN_P1363_FORMAT("SHA1withDSAinP1363Format", "SHA-1 with DSA in P1363 format"),
    SHA224_WITH_DSA_IN_P1363_FORMAT("SHA224withDSAinP1363Format", "SHA-224 with DSA in P1363 format"),
    SHA256_WITH_DSA_IN_P1363_FORMAT("SHA256withDSAinP1363Format", "SHA-256 with DSA in P1363 format"),
    SHA384_WITH_DSA_IN_P1363_FORMAT("SHA384withDSAinP1363Format", "SHA-384 with DSA in P1363 format"),
    SHA512_WITH_DSA_IN_P1363_FORMAT("SHA512withDSAinP1363Format", "SHA-512 with DSA in P1363 format"),
    SHA3_224withDSA("SHA3-224withDSA", "SHA3-224 with DSA"),
    SHA3_256withDSA("SHA3-256withDSA", "SHA3-256 with DSA"),
    SHA3_384withDSA("SHA3-384withDSA", "SHA3-384 with DSA"),
    SHA3_512withDSA("SHA3-512withDSA", "SHA3-512 with DSA"),

    ;

    public String name;
    public  String displayName;

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
