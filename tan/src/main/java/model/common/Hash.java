package model.common;

public enum Hash implements ICipherEnum {
    //    MessageDigest
    MD2("MD2", "MD2",""),
    MD5("MD5", "MD5","HmacMD5"),
    SHA_1("SHA-1", "SHA-1","HmacSHA1"),
    SHA_224("SHA-224", "SHA-224","HmacSHA224"),
    SHA_256("SHA-256", "SHA-256","HmacSHA256"),
    SHA_384("SHA-384", "SHA-384","HmacSHA384"),
    SHA_512("SHA-512", "SHA-512","HmacSHA512"),
    SHA_512_224("SHA-512/224", "SHA-512/224", "HmacSHA512/224"),
    SHA_512_256("SHA-512/256", "SHA-512/256"),
    SHA3_224("SHA3-224", "SHA3-224"),
    SHA3_256("SHA3-256", "SHA3-256"),
    SHA3_384("SHA3-384", "SHA3-384"),
    SHA3_512("SHA3-512", "SHA3-512"),
    ;
    final String name;
    final String displayName;
    final String HmacFormat;
    Hash(String name, String displayName,String HmacFormat) {
        this.name = name;
        this.displayName = displayName;
        this.HmacFormat = HmacFormat;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

//    public boolean isHmac() {
//        return isHmac;
//    }

    @Override
    public String toString() {
        return displayName;
    }
}
