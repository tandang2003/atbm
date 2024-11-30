package model.common;

import java.util.Comparator;

public enum Mode  {
    NONE("", "None", false),
    CBC("CBC", "Cipher Block Chaining", true),
    PCBC("PCBC", "Propagating Cipher Block Chaining", true),
    ECB("ECB", "Electronic Codebook", false),
    CFB("CFB", "Cipher Feedback", true),
    CFB8("CFB8", "Cipher Feedback 8", true),
    CFB16("CFB16", "Cipher Feedback 16", true),
    CFB48("CFB48", "Cipher Feedback 48", true),
    CFB64("CFB64", "Cipher Feedback 64", true),
    CFB128("CFB128", "Cipher Feedback 128", true),
    OFB("OFB", "Output Feedback", true),
    OFB8("OFB8", "Output Feedback 8", true),
    OFB16("OFB16", "Output Feedback 16", true),
    OFB48("OFB48", "Output Feedback 48", true),
    OFB64("OFB64", "Output Feedback 64", true),
    OFB128("OFB128", "Output Feedback 128", true),
    CTR("CTR", "Counter", true),
    CTS("CTS", "Cipher text stealing", true),
    GCM("GCM", "Galois/Counter Mode", true),
    KW("KW", "Key Wrap", true),
    WRAP("WRAP", "AES Key Wrap", true),
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
        if (this == Mode.NONE
        || this == Mode.CBC)
            return "None";
        return name;
    }

}
