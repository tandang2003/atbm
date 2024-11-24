package model.common;

// secure random: DRBG, SHA1PRNG
public enum Signature {
    //DSA
    SHA1_WITH_DSA("SHA1withDSA", "SHA-1 with DSA"),
    SHA224withDSA("SHA224withDSA", "SHA-224 with DSA"),
    SHA256withDSA("SHA256withDSA", "SHA-256 with DSA"),
    SHA384withDSA("SHA384withDSA", "SHA-384 with DSA"),
    SHA512withDSA("SHA512withDSA", "SHA-512 with DSA"),
    SHA3_224withDSA("SHA3-224withDSA", "SHA3-224 with DSA"),
    SHA3_256withDSA("SHA3-256withDSA", "SHA3-256 with DSA"),
    SHA3_384withDSA("SHA3-384withDSA", "SHA3-384 with DSA"),
    SHA3_512withDSA("SHA3-512withDSA", "SHA3-512 with DSA"),

    //    RSA
    MD2withRSA("MD2withRSA", "MD2 with RSA"),
    MD5withRSA("MD5withRSA", "MD5 with RSA"),
    SHA1withRSA("SHA1withRSA", "SHA-1 with RSA"),
    SHA224withRSA("SHA224withRSA", "SHA-224 with RSA"),
    SHA256withRSA("SHA256withRSA", "SHA-256 with RSA"),
    SHA384withRSA("SHA384withRSA", "SHA-384 with RSA"),
    SHA512withRSA("SHA512withRSA", "SHA-512 with RSA"),
    SHA512_224withRSA("SHA512/224withRSA", "SHA-512/224 with RSA"),
    SHA512_256withRSA("SHA512/256withRSA", "SHA-512/256 with RSA"),
    SHA3_224withRSA("SHA3-224withRSA", "SHA3-224 with RSA"),
    SHA3_256withRSA("SHA3-256withRSA", "SHA3-256 with RSA"),
    SHA3_384withRSA("SHA3-384withRSA", "SHA3-384 with RSA"),
    SHA3_512withRSA("SHA3-512withRSA", "SHA3-512 with RSA"),
    RSASSA_PSS("RSASSA-PSS", "RSASSA-PSS"),
    ;
    private final String name;
    private final String displayName;

    Signature(String name, String displayName) {
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
