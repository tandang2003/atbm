package model.common;

public enum Padding {
    NoPadding("NoPadding", "No Padding"),
    PKCS1Padding("PKCS7Padding", "PKCS1 Padding"),
    PKCS5Padding("PKCS5Padding", "PKCS5 Padding"),
    PKCS7Padding("PKCS7Padding", "PKCS7 Padding"),
    OAEPWithSHA_1AndMGF1Padding("OAEPWithSHA-1AndMGF1Padding", "OAEP with SHA-1 and MGF1 Padding"),
    OAEPWithSHA_256AndMGF1Padding("OAEPWithSHA-256AndMGF1Padding", "OAEP with SHA-256 and MGF1 Padding"),
    ISO10126Padding("ISO10126Padding", "ISO10126 Padding");
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
