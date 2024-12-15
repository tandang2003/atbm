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


    public static SignatureSpecification DSA() {
        return new SignatureSpecification(
                KeyPairAlgorithm.DSA,
                Provider.SUN,
                List.of(
                        Signature.SHA1_WITH_DSA
                ),
                List.of(SecureRandom.SHA1PRNG),
                Set.of(Size.Size_128
                ));
    }

    public static SignatureSpecification findByKeyPairAlgorithm(KeyPairAlgorithm keyPairAlgorithm) {
        SignatureSpecification result = switch (keyPairAlgorithm) {
//            case RSA -> RSA();
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
//            case "RSA" -> RSA();
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
