package model.common;

public enum Padding {
    NoPadding("NoPadding", "No Padding"),
    PKCS1Padding("PKCS1Padding", "PKCS1 padding"),
    PKCS5Padding("PKCS5Padding", "PKCS5 Padding"),
    ISO10126Padding("ISO10126Padding", "ISO10126 Padding"),
    OAEPPadding("OAEPPadding", "Optimal Asymmetric Encryption Padding (OAEP)"),
    OAEPWithMD5AndMGF1Padding("OAEPWithMD5AndMGF1Padding", "OAEP with MD5 and MGF1"),
    OAEPWithSHA1AndMGF1Padding("OAEPWithSHA‑1AndMGF1Padding", "OAEP with SHA-1 and MGF1"),
    OAEPWithSHA224AndMGF1Padding("OAEPWithSHA‑224AndMGF1Padding", "OAEP with SHA-224 and MGF1"),
    OAEPWithSHA256AndMGF1Padding("OAEPWithSHA-256AndMGF1Padding", "OAEP with SHA-256 and MGF1"),
    OAEPWithSHA384AndMGF1Padding("OAEPWithSHA-384AndMGF1Padding", "OAEP with SHA-384 and MGF1"),
    OAEPWithSHA512AndMGF1Padding("OAEPWithSHA-512AndMGF1Padding", "OAEP with SHA-512 and MGF1"),
    OAEPWithSHA512_224AndMGF1Padding("OAEPWithSHA-512/224AndMGF1Padding", "OAEP with SHA-512/224 and MGF1"),
    OAEPWithSHA512_256AndMGF1Padding("OAEPWithSHA-512/256AndMGF1Padding", "OAEP with SHA-512/256 and MGF1");

    private final String name;
    private final String displayName;

    Padding(String name, String displayName) {
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
