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
