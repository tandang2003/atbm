package model.common;
/**
 * Enum đại diện cho các thuật toán mã hóa khác nhau.
 * Mỗi thuật toán được định nghĩa với tên, tên hiển thị và provider cung cấp.
 */
public enum Cipher implements ICipherEnum {

    /**
     * Thuật toán mã hóa hoán vị (Transposition).
     */
    TRANSPOSITION("Transposition", "Transposition", Provider.SUN_JCE),

    /**
     * Thuật toán mã hóa thay thế (Substitution).
     */
    SUBSTITUTION("Substitution", "Substitution", Provider.SUN_JCE),

    /**
     * Thuật toán mã hóa Affine.
     */
    AFFINE("Affine", "Affine", Provider.SUN_JCE),

    /**
     * Thuật toán mã hóa Vigenere.
     */
    VIGENERE("Vigenere", "Vigenere", Provider.SUN_JCE),

    /**
     * Thuật toán mã hóa Hill.
     */
    HILL("Hill", "Hill", Provider.SUN_JCE),

    /**
     * Thuật toán mã hóa AES (Advanced Encryption Standard).
     */
    AES("AES", "AES", Provider.SUN_JCE),

    /**
     * Thuật toán mã hóa DES (Data Encryption Standard).
     */
    DES("DES", "DES", Provider.SUN_JCE),

    /**
     * Thuật toán mã hóa Blowfish.
     */
    BLOWFISH("Blowfish", "Blowfish", Provider.SUN_JCE),

    /**
     * Thuật toán mã hóa RSA.
     */
    RSA("RSA", "RSA", Provider.SUN_JCE),

    /**
     * Thuật toán chữ ký số DSA (Digital Signature Algorithm).
     */
    DSA("DSA", "DSA", Provider.SUN_JCE),

    /**
     * Thuật toán mã hóa DESede (Triple DES).
     */
    DESEDE("DESede", "DESede", Provider.SUN_JCE),

    /**
     * Thuật toán mã hóa RC2.
     */
    RC2("RC2", "RC2", Provider.SUN_JCE),

    /**
     * Thuật toán mã hóa RC4.
     */
    RC4("RC4", "RC4", Provider.SUN_JCE),

    /**
     * Thuật toán mã hóa Camellia.
     */
    Camellia("Camellia", "Camellia", Provider.BC),

    /**
     * Thuật toán mã hóa Twofish.
     */
    Twofish("Twofish", "Twofish", Provider.BC),

    /**
     * Thuật toán mã hóa Serpent.
     */
    Serpent("Serpent", "Serpent", Provider.BC);

    /**
     * Tên thuật toán.
     */
    public String name;

    /**
     * Tên hiển thị của thuật toán.
     */
    public String displayName;

    /**
     * Provider cung cấp thuật toán mã hóa.
     */
    public Provider provider;

    /**
     * Lấy thông tin provider cung cấp thuật toán.
     *
     * @return provider của thuật toán.
     */
    public Provider getProvider() {
        return provider;
    }

    /**
     * Khởi tạo enum với tên, tên hiển thị và provider.
     *
     * @param name        tên thuật toán.
     * @param displayName tên hiển thị của thuật toán.
     * @param provider    provider cung cấp thuật toán.
     */
    Cipher(String name, String displayName, Provider provider) {
        this.name = name;
        this.displayName = displayName;
        this.provider = provider;
    }

    /**
     * Lấy tên thuật toán.
     *
     * @return tên thuật toán.
     */
    public String getName() {
        return name;
    }

    /**
     * Lấy tên hiển thị của thuật toán.
     *
     * @return tên hiển thị của thuật toán.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Trả về chuỗi hiển thị của thuật toán.
     *
     * @return tên hiển thị của thuật toán.
     */
    @Override
    public String toString() {
        return displayName;
    }
}

