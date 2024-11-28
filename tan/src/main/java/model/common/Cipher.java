package model.common;

public enum Cipher implements ICipherEnum {
    TRANSPOSITION("Transposition", "Transposition", Provider.SUN_JCE),
    SUBSTITUTION("Substitution", "Substitution", Provider.SUN_JCE),
    AFFINE("Affine", "Affine", Provider.SUN_JCE),
    VIGENERE("Vigenere", "Vigenere", Provider.SUN_JCE),
    HILL("Hill", "Hill", Provider.SUN_JCE),
    AES("AES", "AES", Provider.SUN_JCE),
    DES("DES", "DES", Provider.SUN_JCE),
    BLOWFISH("Blowfish", "Blowfish", Provider.SUN_JCE),
    RSA("RSA", "RSA", Provider.SUN_JCE),
    DSA("DSA", "DSA", Provider.SUN_JCE),
    DESEDE("DESede", "DESede", Provider.SUN_JCE),
    RC2("RC2", "RC2", Provider.SUN_JCE),
    RC4("RC4", "RC4", Provider.SUN_JCE),
    Camellia("Camellia", "Camellia", Provider.BC),
//    //    MessageDigest
//    MD2("MD2", "MD2"),
//    MD5("MD5", "MD5"),
//    SHA_1("SHA-1", "SHA-1"),
//    SHA_224("SHA-224", "SHA-224"),
//    SHA_256("SHA-256", "SHA-256"),
//    SHA_384("SHA-384", "SHA-384"),
//    SHA_512("SHA-512", "SHA-512"),
//    SHA_512_224("SHA-512/224", "SHA-512/224"),
//    SHA_512_256("SHA-512/256", "SHA-512/256"),
//    SHA3_224("SHA3-224", "SHA3-224"),
//    SHA3_256("SHA3-256", "SHA3-256"),
//    SHA3_384("SHA3-384", "SHA3-384"),
//    SHA3_512("SHA3-512", "SHA3-512"),
//    //    Signature
//    SHA1_WITH_DSA("SHA1withDSA", "SHA-1 with DSA"),
//    SHA224_WITH_DSA("SHA224withDSA", "SHA-224 with DSA"),
//    SHA256_WITH_DSA("SHA256withDSA", "SHA-256 with DSA"),
//    SHA384_WITH_DSA("SHA384withDSA", "SHA-384 with DSA"),
//    SHA512_WITH_DSA("SHA512withDSA", "SHA-512 with DSA"),
//    NONE_WITH_DSA_IN_P1363_FORMAT("NONEwithDSAinP1363Format", "NONE with DSA in P1363 format"),
//    SHA1_WITH_DSA_IN_P1363_FORMAT("SHA1withDSAinP1363Format", "SHA-1 with DSA in P1363 format"),
//    SHA224_WITH_DSA_IN_P1363_FORMAT("SHA224withDSAinP1363Format", "SHA-224 with DSA in P1363 format"),
//    SHA256_WITH_DSA_IN_P1363_FORMAT("SHA256withDSAinP1363Format", "SHA-256 with DSA in P1363 format"),
//    SHA384_WITH_DSA_IN_P1363_FORMAT("SHA384withDSAinP1363Format", "SHA-384 with DSA in P1363 format"),
//    SHA512_WITH_DSA_IN_P1363_FORMAT("SHA512withDSAinP1363Format", "SHA-512 with DSA in P1363 format"),
//    SHA3_224withDSA("SHA3-224withDSA", "SHA3-224 with DSA"),
//    SHA3_256withDSA("SHA3-256withDSA", "SHA3-256 with DSA"),
//    SHA3_384withDSA("SHA3-384withDSA", "SHA3-384 with DSA"),
//    SHA3_512withDSA("SHA3-512withDSA", "SHA3-512 with DSA"),

    ;

    public String name;
    public String displayName;
    public Provider provider;

    public Provider getProvider() {
        return provider;
    }

    Cipher(String name, String displayName, Provider provider) {
        this.name = name;
        this.displayName = displayName;
        this.provider = provider;
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
