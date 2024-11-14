package model.common;

public enum Mode {
    NONE("", "None", false),
    CBC("CBC", "Cipher Block Chaining", true),
    ECB("ECB", "Electronic Codebook", false),
    CFB("CFB", "Cipher Feedback", true),
    OFB("OFB", "Output Feedback", true),
    CTR("CTR", "Counter", true),
    GCM("GCM", "Galois/Counter Mode", true),
    ;

    private final String name;
    private final String displayName;
    private final boolean requireIV;

    Mode(String name, String displayName, boolean requireIV) {
        this.name = name;
        this.displayName = displayName;
        this.requireIV = requireIV;
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
