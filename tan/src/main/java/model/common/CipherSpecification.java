package model.common;


import java.security.Provider;
import java.security.Security;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CipherSpecification {
    private Cipher algorithm;
    private Map<Mode, List<Padding>> validModePaddingCombinations;
    private Set<Size> supportedKeySizes;  // Set to hold supported key sizes
    private Map<Mode, Size> ivSizes;  // Map to hold IV sizes for each mode

    public static final CipherSpecification findCipherSpecification(Cipher cipher) {
        return switch (cipher) {
            case AES -> AES;
            case DES -> DES;
            case DESEDE -> TRIPLEDES;
            case RSA -> RSA;
            case BLOWFISH -> Blowfish;
            case RC2 -> RC2;
            case RC4 -> RC4;
            default -> null;
        };
    }

    private static final CipherSpecification AES = new CipherSpecification(
            Cipher.AES,
            Map.ofEntries(
                    Map.entry(Mode.NONE, List.of(Padding.NoPadding)),
                    Map.entry(Mode.ECB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.PCBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CFB128, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.OFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.OFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.OFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.OFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.OFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.OFB128, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CTR, List.of(Padding.NoPadding)),
                    Map.entry(Mode.CTS, List.of(Padding.NoPadding)),
                    Map.entry(Mode.GCM, List.of(Padding.NoPadding))
            ),
            Set.of(Size.Size_16, Size.Size_24, Size.Size_32),  // Supported key sizes for AES
            Map.of(
                    Mode.ECB, Size.Size_0,  // AES CBC mode requires 128-bit IV
                    Mode.CFB, Size.Size_16,  // AES CFB mode requires 128-bit IV
                    Mode.OFB, Size.Size_16,  // AES OFB mode requires 128-bit IV
                    Mode.CTR, Size.Size_16,  // AES CTR mode requires 128-bit IV
                    Mode.GCM, Size.Size_12   // AES GCM mode typically uses 96-bit IV
            )
    );

    private static final CipherSpecification DES = new CipherSpecification(
            Cipher.DES,
            Map.ofEntries(
                    Map.entry(Mode.NONE, List.of(Padding.NoPadding)),
                    Map.entry(Mode.ECB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.PCBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.OFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.OFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.OFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.OFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.OFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CTR, List.of(Padding.NoPadding)),
                    Map.entry(Mode.CTS, List.of(Padding.NoPadding)),
                    Map.entry(Mode.GCM, List.of(Padding.NoPadding))
            ),
            Set.of(Size.Size_7),  // Supported key size for DES (56-bit)
            Map.of(
                    Mode.CBC, Size.Size_8,  // DES CBC mode requires 64-bit IV
                    Mode.ECB, Size.Size_8   // DES ECB mode requires 64-bit IV
            )
    );

    private static final CipherSpecification TRIPLEDES = new CipherSpecification(
            Cipher.DESEDE,
            Map.ofEntries(
                    Map.entry(Mode.NONE, List.of(Padding.NoPadding)),
                    Map.entry(Mode.ECB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.PCBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.OFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.OFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.OFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.OFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.OFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CTR, List.of(Padding.NoPadding)),
                    Map.entry(Mode.CTS, List.of(Padding.NoPadding)),
                    Map.entry(Mode.GCM, List.of(Padding.NoPadding))
            ),
            Set.of(Size.Size_14, Size.Size_21),  // Supported key sizes for DESede (112-bit, 168-bit)
            Map.of(
                    Mode.CBC, Size.Size_8,  // DESede CBC mode requires 64-bit IV
                    Mode.ECB, Size.Size_0   // DESede ECB mode requires 64-bit IV
            )
    );


    // RSA doesn't require IVs but supports different key sizes
    private static final CipherSpecification RSA = new CipherSpecification(
            Cipher.RSA,
            Map.of(Mode.NONE, List.of(Padding.NoPadding),
                    Mode.ECB, List.of(Padding.NoPadding, Padding.PKCS1Padding, Padding.OAEPPadding, Padding.OAEPWithMD5AndMGF1Padding,
                            Padding.OAEPWithSHA1AndMGF1Padding, Padding.OAEPWithSHA224AndMGF1Padding, Padding.OAEPWithSHA256AndMGF1Padding,
                            Padding.OAEPWithSHA384AndMGF1Padding, Padding.OAEPWithSHA512AndMGF1Padding, Padding.OAEPWithSHA512_224AndMGF1Padding,
                            Padding.OAEPWithSHA512_256AndMGF1Padding)
            ),
            Set.of(Size.Size_64, Size.Size_128, Size.Size_256, Size.Size_512),  // RSA supports these key sizes
            Map.of(Mode.ECB, Size.Size_0)  // RSA does not require IVs
    );

    // Blowfish cipher specification
    private static final CipherSpecification Blowfish = new CipherSpecification(
            Cipher.BLOWFISH,
            Map.ofEntries(
                    Map.entry(Mode.NONE, List.of(Padding.NoPadding)),
                    Map.entry(Mode.ECB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.PCBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.OFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.OFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.OFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.OFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.OFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CTR, List.of(Padding.NoPadding)),
                    Map.entry(Mode.CTS, List.of(Padding.NoPadding)),
                    Map.entry(Mode.GCM, List.of(Padding.NoPadding))
            ),
            Set.of(Size.Size_4, Size.Size_12, Size.Size_24, Size.Size_32, Size.Size_56),  // Blowfish supports key sizes of 128-bit, 192-bit, and 256-bit
            Map.of(
                    Mode.CBC, Size.Size_8,  // Blowfish CBC mode requires 64-bit IV
                    Mode.ECB, Size.Size_8,  // Blowfish ECB mode requires 64-bit IV
                    Mode.CFB, Size.Size_8   // Blowfish CFB mode requires 64-bit IV
            )
    );

    // RC2 cipher specification
    private static final CipherSpecification RC2 = new CipherSpecification(
            Cipher.RC2,
            Map.ofEntries(
                    Map.entry(Mode.NONE, List.of(Padding.NoPadding)),
                    Map.entry(Mode.ECB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.PCBC, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.OFB, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.OFB8, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.OFB16, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.OFB48, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.OFB64, List.of(Padding.NoPadding, Padding.PKCS5Padding, Padding.PKCS7Padding)),
                    Map.entry(Mode.CTR, List.of(Padding.NoPadding)),
                    Map.entry(Mode.CTS, List.of(Padding.NoPadding)),
                    Map.entry(Mode.GCM, List.of(Padding.NoPadding))
            ),
            Set.of(Size.Size_5, Size.Size_8, Size.Size_16),  // RC2 supports key sizes from 40 bits to 128 bits
            Map.of(
                    Mode.CBC, Size.Size_8,  // RC2 CBC mode requires 64-bit IV
                    Mode.ECB, Size.Size_0   // RC2 ECB mode requires 64-bit IV
            )
    );

    // RC4 cipher specification (stream cipher, no IV)
    private static final CipherSpecification RC4 = new CipherSpecification(
            Cipher.RC4,
            Map.of(
                    Mode.NONE, List.of(Padding.NoPadding)
            ),
            Set.of(Size.Size_5, Size.Size_7, Size.Size_16),  // RC4 supports key sizes of 40, 56, and 128 bits
            Map.of()  // RC4 does not require an IV
    );


    public CipherSpecification(Cipher algorithm, Map<Mode, List<Padding>> validModePaddingCombinations, Set<Size> supportedKeySizes, Map<Mode, Size> ivSizes) {
        this.algorithm = algorithm;
        this.validModePaddingCombinations = validModePaddingCombinations;
        this.supportedKeySizes = supportedKeySizes;
        this.ivSizes = ivSizes;
    }

    public Cipher getAlgorithm() {
        return algorithm;
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


        System.out.println("Supported Ciphers, Modes, and Paddings:");
        ciphers.stream().sorted();
        for (String cipher : ciphers) {
            System.out.println(cipher);
        }

    }
}
