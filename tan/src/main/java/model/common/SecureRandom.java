package model.common;

public enum SecureRandom {
    SHA1PRNG("SHA1PRNG", "SHA1PRNG"),
    DRBG("DRBG", "DRBG"),
    ;

    private String name;
    private String displayName;
    SecureRandom(String name, String displayName) {
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
        return name;
    }
}
