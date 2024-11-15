package model.common;

public class Algorithms {
    //DEFAULT KEY LENGTH
    public static final int HILL_ALGORITHM_DEFAULT_KEY_LENGTH = 2;

    //DEFAULT KEY
    public static final String KEY_FILE_EXTENSION = ".tan.key";
    public static final String FILE_ENCRYPT_EXTENSION = ".tan";

    //TYPES ALGORITHMS
    public static final String SYMMETRIC = "Symmetric";
    public static final String CLASSICAL = "Classic";
    public static final String BLOCK= "Block";
    public static final String HASH= "Hash";
    public static final String ASYMMETRIC = "Asymmetric";


    //Algorithms
//    public static final String TRANSPOSITION = "Transposition";
//    public static final String SUBSTITUTION = "Substitution";
//    public static final String AFFINE = "Affine";
//    public static final String VIGENCE = "Vigence";
//    public static final String HILL = "Hill";
//    public static final String AES = "AES";
//    public static final String DES = "DES";
//    public static final String TRIPLE_DES = "3DES";
//    public static final String BLOWFISH = "Blowfish";
//    public static final String RC2 = "RC2";
//    public static final String RC4 = "RC4";
//        3DES
    public static final String RSA = "RSA";
    public static final String MD5= "MD5";
    public static final String DSA= "DSA";

    //MODES
    public static final String MODEL_NONE = "None";
    public static final String MODEL_ECB = "ECB";
    public static final String MODEL_CBC = "CBC";
    public static final String MODEL_CFB = "CFB";
    public static final String MODEL_CFB_X = "CFBx";
    public static final String MODEL_OFB = "OFB";
    public static final String MODEL_OFB_X = "OFBx";
    public static final String MODEL_CTR = "CTR";
    public static final String MODEL_CTS = "CTS";
    public static final String MODEL_GCM = "GCM";
    public static final String MODEL_PCBC = "PCBC";

    //PADDING
    public static final String PADDING_NO = "No";
    public static final String PADDING_PKCS5 = "PKCS5";
    public static final String PADDING_PKCS7 = "PKCS7";
    public static final String PADDING_ISO10126 = "ISO10126";
    public static final String PADDING_ISO7816 = "ISO7816";
    public static final String PADDING_X923 = "X923";
    public static final String PADDING_OAEP = "OAEP";
    public static final String PADDING_PKCS1 = "PKCS1";
}
