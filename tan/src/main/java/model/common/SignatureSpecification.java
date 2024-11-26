package model.common;

import java.util.*;
import java.util.stream.Collectors;

public class SignatureSpecification {
    private KeyPairAlgorithm keyPairAlgorithm;
    private Provider provider;
    private List<Signature> signatures;
    private List<SecureRandom> algRandoms;
    private Set<Size> sizes;

    public KeyPairAlgorithm getKeyPairAlgorithm() {
        return keyPairAlgorithm;
    }

    public Provider getProvider() {
        return provider;
    }

    public List<Signature> getSignatures() {
        return signatures;
    }

    public List<SecureRandom> getAlgRandoms() {
        return algRandoms;
    }

    public Set<Size> getSizes() {
        return sizes;
    }

    private SignatureSpecification(KeyPairAlgorithm keyPairAlgorithm, Provider provider, List<Signature> signatures, List<SecureRandom> algRandoms, Set<Size> sizes) {
        this.keyPairAlgorithm = keyPairAlgorithm;
        this.provider = provider;
        this.signatures = signatures;
        this.algRandoms = algRandoms;
        this.sizes = sizes;
    }


    public static SignatureSpecification RSA() {
        return new SignatureSpecification(
                KeyPairAlgorithm.RSA,
                Provider.SUN_RSA_SIGN,
                List.of(
                        Signature.MD2withRSA,
                        Signature.MD5withRSA,
                        Signature.SHA1withRSA,
                        Signature.SHA256withRSA,
                        Signature.SHA384withRSA,
                        Signature.SHA512withRSA,
                        Signature.SHA512_224withRSA,
                        Signature.SHA512_256withRSA,
                        Signature.SHA3_224withRSA,
                        Signature.SHA3_256withRSA,
                        Signature.SHA3_384withRSA,
                        Signature.SHA3_512withRSA),
                List.of(SecureRandom.SHA1PRNG, SecureRandom.DRBG),
                Set.of(Size.Size_128
                        ,Size.Size_256,
                        Size.Size_384
                ));
    }

    public static SignatureSpecification DSA() {
        return new SignatureSpecification(
                KeyPairAlgorithm.RSA,
                Provider.SUN,
                List.of(
                        Signature.SHA1_WITH_DSA,
                        Signature.SHA224withDSA,
                        Signature.SHA256withDSA,
                        Signature.SHA384withDSA,
                        Signature.SHA512withDSA,
                        Signature.SHA3_224withDSA,
                        Signature.SHA3_256withDSA,
                        Signature.SHA3_384withDSA,
                        Signature.SHA3_512withDSA
                ),
                List.of(SecureRandom.SHA1PRNG, SecureRandom.DRBG),
                Set.of(Size.Size_64, Size.Size_128
//                        , Size.Size_256, Size.Size_384, Size.Size_512
                ));
    }

    public static SignatureSpecification findByKeyPairAlgorithm(KeyPairAlgorithm keyPairAlgorithm) {
        SignatureSpecification result = switch (keyPairAlgorithm) {
            case RSA -> RSA();
            case DSA -> DSA();
            default -> null;
        };
        if (result != null) {
            result.sizes = new TreeSet<>(result.sizes);
            result.signatures = result.signatures.stream().sorted(Comparator.comparing(Signature::getName)).toList();
            result.algRandoms = result.algRandoms.stream().sorted(Comparator.comparing(SecureRandom::getName)).toList();
        }
        return result;
    }

    public static SignatureSpecification findByKeyPairAlgorithm(String keyPairAlgorithm) {
        SignatureSpecification result = switch (keyPairAlgorithm) {
            case "RSA" -> RSA();
            case "DSA" -> DSA();
            default -> null;
        };
        if (result != null) {
            result.sizes = new TreeSet<>(result.sizes);
            result.signatures = result.signatures.stream().sorted(Comparator.comparing(Signature::getName)).toList();
            result.algRandoms = result.algRandoms.stream().sorted(Comparator.comparing(SecureRandom::getName)).toList();
        }
        return result;
    }

}
