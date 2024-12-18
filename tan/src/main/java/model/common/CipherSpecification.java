package model.common;


import org.bouncycastle.jcajce.provider.asymmetric.GOST;

import java.security.Provider;
import java.security.Security;
import java.util.*;
/**
 * Lớp này đại diện cho đặc tả thuật toán mã hóa (Cipher Specification), cung cấp thông tin
 * về các chế độ (Mode), kiểu đệm (Padding), kích thước khóa (Key Size) được hỗ trợ
 * và kích thước vector khởi tạo (IV Size).
 */
public class CipherSpecification {

    private Cipher algorithm; // Thuật toán mã hóa
    private Map<Mode, List<Padding>> validModePaddingCombinations; // Kết hợp chế độ và kiểu đệm hợp lệ
    private Set<Size> supportedKeySizes;  // Tập hợp kích thước khóa được hỗ trợ
    private Map<Mode, Size> ivSizes;  // Bản đồ kích thước IV theo từng chế độ

    /**
     * Phương thức tìm kiếm đặc tả thuật toán mã hóa dựa trên tên thuật toán mã hóa.
     *
     * @param cipher Tên thuật toán mã hóa (ví dụ: "AES", "DES", "RSA", v.v.)
     * @return Đối tượng {@code CipherSpecification} tương ứng, hoặc {@code null} nếu không tìm thấy.
     */
    public static final CipherSpecification findCipherSpecificationWithCipherName(String cipher) {
        CipherSpecification result = switch (cipher) {
            case "AES" -> AES;
            case "DES" -> DES;
            case "DESede" -> TRIPLEDES;
            case "RSA" -> RSA;
            case "Blowfish" -> Blowfish;
            case "RC2" -> RC2;
            case "RC4" -> RC4;
            case "Camellia" -> CAMELLIA;
            case "Twofish" -> Twofish;
            case "LEA" -> Serpent;
            default -> null;
        };

        if (result != null) {
            // Khởi tạo các tập hợp dữ liệu được sắp xếp
            result.supportedKeySizes = new TreeSet<>(result.supportedKeySizes);
            result.ivSizes = new TreeMap<>(result.ivSizes);
            Map<Mode, List<Padding>> sortedValidModePaddingCombinations = new TreeMap<>(new Comparator<Mode>() {
                @Override
                public int compare(Mode o1, Mode o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            sortedValidModePaddingCombinations.putAll(result.validModePaddingCombinations);
            result.validModePaddingCombinations = sortedValidModePaddingCombinations;
        }
        return result;
    }

    /**
     * Phương thức tìm kiếm đặc tả thuật toán mã hóa dựa trên đối tượng {@code Cipher}.
     *
     * @param cipher Đối tượng thuật toán mã hóa {@code Cipher}.
     * @return Đối tượng {@code CipherSpecification} tương ứng, hoặc {@code null} nếu không tìm thấy.
     */
    public static final CipherSpecification findCipherSpecification(Cipher cipher) {
        CipherSpecification result = switch (cipher) {
            case AES -> AES;
            case DES -> DES;
            case DESEDE -> TRIPLEDES;
            case RSA -> RSA;
            case BLOWFISH -> Blowfish;
            case RC2 -> RC2;
            case RC4 -> RC4;
            case Camellia -> CAMELLIA;
            case Twofish -> Twofish;
            case Serpent -> Serpent;
            default -> null;
        };

        if (result != null) {
            // Khởi tạo các tập hợp dữ liệu được sắp xếp
            result.supportedKeySizes = new TreeSet<>(result.supportedKeySizes);
            result.ivSizes = new TreeMap<>(result.ivSizes);
            Map<Mode, List<Padding>> sortedValidModePaddingCombinations = new TreeMap<>(new Comparator<Mode>() {
                @Override
                public int compare(Mode o1, Mode o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            sortedValidModePaddingCombinations.putAll(result.validModePaddingCombinations);
            result.validModePaddingCombinations = sortedValidModePaddingCombinations;
        }
        return result;
    }


    private static final CipherSpecification CAMELLIA = new CipherSpecification(
            Cipher.Camellia,
            Map.ofEntries(
                    Map.entry(Mode.NONE, List.of(Padding.NoPadding)),
                    Map.entry(Mode.ECB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB128, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB128, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CTR, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CTS, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding))

            ),
            Set.of(Size.Size_16, Size.Size_24, Size.Size_32),
            Map.ofEntries(
                    Map.entry(Mode.NONE, Size.Size_0),
                    Map.entry(Mode.ECB, Size.Size_0),
                    Map.entry(Mode.CBC, Size.Size_16),
                    Map.entry(Mode.CFB, Size.Size_16),
                    Map.entry(Mode.CFB8, Size.Size_16),
                    Map.entry(Mode.CFB16, Size.Size_16),
                    Map.entry(Mode.CFB48, Size.Size_16),
                    Map.entry(Mode.CFB64, Size.Size_16),
                    Map.entry(Mode.CFB128, Size.Size_16),
                    Map.entry(Mode.OFB, Size.Size_16),
                    Map.entry(Mode.OFB8, Size.Size_16),
                    Map.entry(Mode.OFB16, Size.Size_16),
                    Map.entry(Mode.OFB48, Size.Size_16),
                    Map.entry(Mode.OFB64, Size.Size_16),
                    Map.entry(Mode.OFB128, Size.Size_16),
                    Map.entry(Mode.CTR, Size.Size_16),
                    Map.entry(Mode.CTS, Size.Size_16)
            )
    );
    private static final CipherSpecification Twofish = new CipherSpecification(
            Cipher.Twofish,
            Map.ofEntries(
                    Map.entry(Mode.NONE, List.of(Padding.NoPadding)),
                    Map.entry(Mode.ECB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB128, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB128, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CTR, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CTS, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding))

            ),
            Set.of(Size.Size_16, Size.Size_24, Size.Size_32),
            Map.ofEntries(
                    Map.entry(Mode.NONE, Size.Size_0),
                    Map.entry(Mode.ECB, Size.Size_0),
                    Map.entry(Mode.CBC, Size.Size_16),
                    Map.entry(Mode.CFB, Size.Size_16),
                    Map.entry(Mode.CFB8, Size.Size_16),
                    Map.entry(Mode.CFB16, Size.Size_16),
                    Map.entry(Mode.CFB48, Size.Size_16),
                    Map.entry(Mode.CFB64, Size.Size_16),
                    Map.entry(Mode.CFB128, Size.Size_16),
                    Map.entry(Mode.OFB, Size.Size_16),
                    Map.entry(Mode.OFB8, Size.Size_16),
                    Map.entry(Mode.OFB16, Size.Size_16),
                    Map.entry(Mode.OFB48, Size.Size_16),
                    Map.entry(Mode.OFB64, Size.Size_16),
                    Map.entry(Mode.OFB128, Size.Size_16),
                    Map.entry(Mode.CTR, Size.Size_16),
                    Map.entry(Mode.CTS, Size.Size_16)
            )
    );
    private static final CipherSpecification Serpent = new CipherSpecification(
            Cipher.Serpent,
            Map.ofEntries(
                    Map.entry(Mode.NONE, List.of(Padding.NoPadding)),
                    Map.entry(Mode.ECB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB128, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB128, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CTR, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CTS, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding))

            ),
            Set.of(Size.Size_16, Size.Size_24, Size.Size_32),
            Map.ofEntries(
                    Map.entry(Mode.NONE, Size.Size_0),
                    Map.entry(Mode.ECB, Size.Size_0),
                    Map.entry(Mode.CBC, Size.Size_16),
                    Map.entry(Mode.CFB, Size.Size_16),
                    Map.entry(Mode.CFB8, Size.Size_16),
                    Map.entry(Mode.CFB16, Size.Size_16),
                    Map.entry(Mode.CFB48, Size.Size_16),
                    Map.entry(Mode.CFB64, Size.Size_16),
                    Map.entry(Mode.CFB128, Size.Size_16),
                    Map.entry(Mode.OFB, Size.Size_16),
                    Map.entry(Mode.OFB8, Size.Size_16),
                    Map.entry(Mode.OFB16, Size.Size_16),
                    Map.entry(Mode.OFB48, Size.Size_16),
                    Map.entry(Mode.OFB64, Size.Size_16),
                    Map.entry(Mode.OFB128, Size.Size_16),
                    Map.entry(Mode.CTR, Size.Size_16),
                    Map.entry(Mode.CTS, Size.Size_16)
            )
    );

    private static final CipherSpecification AES = new CipherSpecification(
            Cipher.AES,
            Map.ofEntries(
                    Map.entry(Mode.NONE, List.of(Padding.NoPadding)),
                    Map.entry(Mode.ECB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.PCBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB128, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB128, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CTR, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CTS, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding))
//                    Map.entry(Mode.GCM, List.of(Padding.NoPadding))
            ),
            Set.of(Size.Size_16, Size.Size_24, Size.Size_32),  // Supported key sizes for AES
            Map.ofEntries(
                    Map.entry(Mode.NONE, Size.Size_0),
                    Map.entry(Mode.ECB, Size.Size_0),
                    Map.entry(Mode.CBC, Size.Size_16),
                    Map.entry(Mode.PCBC, Size.Size_16),
                    Map.entry(Mode.CFB, Size.Size_16),
                    Map.entry(Mode.CFB8, Size.Size_16),
                    Map.entry(Mode.CFB16, Size.Size_16),
                    Map.entry(Mode.CFB48, Size.Size_16),
                    Map.entry(Mode.CFB64, Size.Size_16),
                    Map.entry(Mode.CFB128, Size.Size_16),
                    Map.entry(Mode.OFB, Size.Size_16),
                    Map.entry(Mode.OFB8, Size.Size_16),
                    Map.entry(Mode.OFB16, Size.Size_16),
                    Map.entry(Mode.OFB48, Size.Size_16),
                    Map.entry(Mode.OFB64, Size.Size_16),
                    Map.entry(Mode.OFB128, Size.Size_16),
                    Map.entry(Mode.CTR, Size.Size_16),
                    Map.entry(Mode.CTS, Size.Size_16)
//                    Map.entry(Mode.GCM, Size.Size_12)
            )
    );

    private static final CipherSpecification DES = new CipherSpecification(
            Cipher.DES,
            Map.ofEntries(
                    Map.entry(Mode.NONE, List.of(Padding.NoPadding)),
                    Map.entry(Mode.ECB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.PCBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CTR, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CTS, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding))
//                    Map.entry(Mode.GCM, List.of(Padding.NoPadding))
            ),
            Set.of(Size.Size_7),  // Supported key size for DES (56-bit)
            Map.ofEntries(
                    Map.entry(Mode.NONE, Size.Size_0),
                    Map.entry(Mode.ECB, Size.Size_0),
                    Map.entry(Mode.CBC, Size.Size_8),
                    Map.entry(Mode.PCBC, Size.Size_8),
                    Map.entry(Mode.CFB, Size.Size_8),
                    Map.entry(Mode.CFB8, Size.Size_8),
                    Map.entry(Mode.CFB16, Size.Size_8),
                    Map.entry(Mode.CFB48, Size.Size_8),
                    Map.entry(Mode.CFB64, Size.Size_8),
                    Map.entry(Mode.OFB, Size.Size_8),
                    Map.entry(Mode.OFB8, Size.Size_8),
                    Map.entry(Mode.OFB16, Size.Size_8),
                    Map.entry(Mode.OFB48, Size.Size_8),
                    Map.entry(Mode.OFB64, Size.Size_8),
                    Map.entry(Mode.CTR, Size.Size_8),
                    Map.entry(Mode.CTS, Size.Size_8)
//                    Map.entry(Mode.GCM, Size.Size_12)
            )
    );

    private static final CipherSpecification TRIPLEDES = new CipherSpecification(
            Cipher.DESEDE,
            Map.ofEntries(
                    Map.entry(Mode.NONE, List.of(Padding.NoPadding)),
                    Map.entry(Mode.ECB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.PCBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CTR, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CTS, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding))
//                    Map.entry(Mode.GCM, List.of(Padding.NoPadding))
            ),
            Set.of(Size.Size_14, Size.Size_21),  // Supported key sizes for DESede (112-bit, 168-bit)
            Map.ofEntries(
                    Map.entry(Mode.NONE, Size.Size_0),
                    Map.entry(Mode.CBC, Size.Size_8),
                    Map.entry(Mode.PCBC, Size.Size_8),
                    Map.entry(Mode.CFB, Size.Size_8),
                    Map.entry(Mode.CFB8, Size.Size_8),
                    Map.entry(Mode.CFB16, Size.Size_8),
                    Map.entry(Mode.CFB48, Size.Size_8),
                    Map.entry(Mode.CFB64, Size.Size_8),
                    Map.entry(Mode.OFB, Size.Size_8),
                    Map.entry(Mode.OFB8, Size.Size_8),
                    Map.entry(Mode.OFB16, Size.Size_8),
                    Map.entry(Mode.OFB48, Size.Size_8),
                    Map.entry(Mode.OFB64, Size.Size_8),
                    Map.entry(Mode.CTR, Size.Size_8),
                    Map.entry(Mode.CTS, Size.Size_8)
//                    Map.entry(Mode.GCM, Size.Size_8)
            )
    );


    // RSA doesn't require IVs but supports different key sizes
    private static final CipherSpecification RSA = new CipherSpecification(
            Cipher.RSA,
            Map.of(
//                    Mode.NONE, List.of(Padding.NoPadding),
                    Mode.ECB, List.of(Padding.NoPadding, Padding.PKCS1Padding, Padding.OAEPPadding, Padding.OAEPWithMD5AndMGF1Padding)
            ),
            Set.of(Size.Size_64, Size.Size_128, Size.Size_256, Size.Size_512),  // RSA supports these key sizes
            Map.of()  // RSA does not require IVs
    );

    // Blowfish cipher specification
    private static final CipherSpecification Blowfish = new CipherSpecification(
            Cipher.BLOWFISH,
            Map.ofEntries(
                    Map.entry(Mode.NONE, List.of(Padding.NoPadding)),
                    Map.entry(Mode.ECB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.PCBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CTR, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CTS, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding))
//                    Map.entry(Mode.GCM, List.of(Padding.NoPadding))
            ),
            Set.of(Size.Size_4, Size.Size_12, Size.Size_24, Size.Size_32, Size.Size_56),  // Blowfish supports key sizes of 128-bit, 192-bit, and 256-bit
            Map.ofEntries(
                    Map.entry(Mode.NONE, Size.Size_0),
                    Map.entry(Mode.CBC, Size.Size_8),
                    Map.entry(Mode.PCBC, Size.Size_8),
                    Map.entry(Mode.CFB, Size.Size_8),
                    Map.entry(Mode.CFB8, Size.Size_8),
                    Map.entry(Mode.CFB16, Size.Size_8),
                    Map.entry(Mode.CFB48, Size.Size_8),
                    Map.entry(Mode.CFB64, Size.Size_8),
                    Map.entry(Mode.OFB, Size.Size_8),
                    Map.entry(Mode.OFB8, Size.Size_8),
                    Map.entry(Mode.OFB16, Size.Size_8),
                    Map.entry(Mode.OFB48, Size.Size_8),
                    Map.entry(Mode.OFB64, Size.Size_8),
                    Map.entry(Mode.CTR, Size.Size_8),
                    Map.entry(Mode.CTS, Size.Size_8)
//                    Map.entry(Mode.GCM, Size.Size_8)
            )

    );

    // RC2 cipher specification
    private static final CipherSpecification RC2 = new CipherSpecification(
            Cipher.RC2,
            Map.ofEntries(
                    Map.entry(Mode.NONE, List.of(Padding.NoPadding)),
                    Map.entry(Mode.ECB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.PCBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.OFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CTR, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding)),
                    Map.entry(Mode.CTS, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.ISO10126Padding))
//                    Map.entry(Mode.GCM, List.of(Padding.NoPadding))
            ),
            Set.of(Size.Size_5, Size.Size_8, Size.Size_16),  // RC2 supports key sizes from 40 bits to 128 bits
            Map.ofEntries(
                    Map.entry(Mode.NONE, Size.Size_0),
                    Map.entry(Mode.CBC, Size.Size_8),
                    Map.entry(Mode.PCBC, Size.Size_8),
                    Map.entry(Mode.CFB, Size.Size_8),
                    Map.entry(Mode.CFB8, Size.Size_8),
                    Map.entry(Mode.CFB16, Size.Size_8),
                    Map.entry(Mode.CFB48, Size.Size_8),
                    Map.entry(Mode.CFB64, Size.Size_8),
                    Map.entry(Mode.OFB, Size.Size_8),
                    Map.entry(Mode.OFB8, Size.Size_8),
                    Map.entry(Mode.OFB16, Size.Size_8),
                    Map.entry(Mode.OFB48, Size.Size_8),
                    Map.entry(Mode.OFB64, Size.Size_8),
                    Map.entry(Mode.CTR, Size.Size_8),
                    Map.entry(Mode.CTS, Size.Size_8)
//                    Map.entry(Mode.GCM, Size.Size_8)
            )
    );

    // RC4 cipher specification (stream cipher, no IV)
    private static final CipherSpecification RC4 = new CipherSpecification(
            Cipher.RC4,
            Map.of(
                    Mode.NONE, List.of(Padding.NoPadding),
                    Mode.ECB, List.of(Padding.NoPadding)
            ),
            Set.of(Size.Size_5, Size.Size_7, Size.Size_16),  // RC4 supports key sizes of 40, 56, and 128 bits
            Map.of()  // RC4 does not require an IV
    );

    private static final CipherSpecification DSA = new CipherSpecification(
            Cipher.DSA,
            Map.of(
                    Mode.NONE, List.of(Padding.NoPadding)
            ),
            Set.of(Size.Size_128, Size.Size_256, Size.Size_512),  // DSA supports key sizes of 512, 1024, and 2048 bits
            Map.of()  // DSA does not require an IV
    );

    public CipherSpecification(Cipher algorithm, Map<Mode, List<Padding>> validModePaddingCombinations, Set<Size> supportedKeySizes, Map<Mode, Size> ivSizes) {
        this.algorithm = algorithm;
        this.validModePaddingCombinations = validModePaddingCombinations;
        this.supportedKeySizes = supportedKeySizes;
        this.ivSizes = ivSizes;
    }


    public Map<Mode, List<Padding>> getValidModePaddingCombinations() {
        return validModePaddingCombinations;
    }

    public Set<Size> getSupportedKeySizes() {
        return supportedKeySizes;
    }

    public Map<Mode, Size> getIvSizes() {
        return ivSizes;
    }

    public static void main(String[] args) {
        Set<String> ciphers = new HashSet<>();

        // Get all installed providers
        Provider[] providers = Security.getProviders();

        for (Provider provider : providers) {
            // For each provider, get the services related to "Cipher"
            provider.getServices().stream()
                    .filter(service -> "Cipher".equals(service.getType()))
                    .forEach(service -> ciphers.add(service.getAlgorithm()));
        }


        ciphers.stream().sorted();
        for (String cipher : ciphers) {
            System.out.println(cipher);
        }

    }

    public Cipher getAlgorithm() {
        return algorithm;
    }
}
