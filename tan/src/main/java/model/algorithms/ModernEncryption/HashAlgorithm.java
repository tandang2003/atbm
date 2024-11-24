package model.algorithms.ModernEncryption;

import model.algorithms.AAlgorithm;
import model.common.Hash;
import model.common.ICipherEnum;
import model.key.HashKey;
import model.key.HashKeyHelper;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class HashAlgorithm extends AAlgorithm {

    private MessageDigest messageDigests;
    private Mac mac;

    public HashAlgorithm(Hash cipher, boolean isHex, boolean isHMAC, String keyMac) {
        this.key = new HashKey(new HashKeyHelper(cipher, isHex, isHMAC, keyMac));
    }

    public HashAlgorithm(Hash cipher, boolean isHex, boolean isMac) {
        this.key = new HashKey(new HashKeyHelper(cipher, isHex, isMac));
    }

    public HashAlgorithm(Hash cipher, boolean isHex, String provider) {
        this.key = new HashKey(new HashKeyHelper(cipher, isHex, provider));
    }

    @Override
    protected boolean validation() {
        return false;
    }

    @Override
    public void genKey() {
        if (!((HashKeyHelper) key.getKey()).isHMAC()) {
            genMessageDigest();
        } else {
            genHMACMessageDigest();
        }
    }

    private void genMessageDigest() {
        try {
            messageDigests = MessageDigest.getInstance(((HashKeyHelper) key.getKey()).getKey().getName(), "SunJCE");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
    }


    private void genHMACMessageDigest() {
        HashKeyHelper key = (HashKeyHelper) this.key.getKey();
        try {
            SecretKeySpec secureRandom = new SecretKeySpec(key.getKeyHmac().getBytes(StandardCharsets.UTF_8), key.getHmac());
            mac = Mac.getInstance(key.getHmac(), "SunJCE");
            mac.init(secureRandom);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            mac = null;
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String encrypt(String input) {
        byte[] digest;
        if (!((HashKeyHelper) key.getKey()).isHMAC()) {
            digest = encryptNotHMAC(input);
        } else {
            digest = encryptHMAC(input);
        }
        if (!((HashKeyHelper) key.getKey()).isHex()) {
            return Base64.getEncoder().encodeToString(digest);
        } else {
            BigInteger no = new BigInteger(1, digest);
            return no.toString(16);
        }
    }

    private byte[] encryptHMAC(String input) {
        HashKeyHelper key = (HashKeyHelper) this.key.getKey();
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        return mac.doFinal(bytes);

    }

    private byte[] encryptNotHMAC(String input) {
        HashKeyHelper key = (HashKeyHelper) this.key.getKey();
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        return messageDigests.digest(bytes);
    }

    @Override
    public String signOrHashFile(String fileIn) throws IOException {
        byte[] digest;
        if (!((HashKeyHelper) key.getKey()).isHMAC()) {
            digest = signOrHashFileNonHMAC(fileIn);
        } else {
            digest = signOrHashFileHMAC(fileIn);
        }
        if (!((HashKeyHelper) key.getKey()).isHex()) {
            return Base64.getEncoder().encodeToString(digest);
        } else {
            BigInteger no = new BigInteger(1, digest);
            return no.toString(16);
        }
    }

    private byte[] signOrHashFileNonHMAC(String fileIn) throws FileNotFoundException {
        File f = new File(fileIn);
        if (!f.exists()) {
            throw new FileNotFoundException("File not found");
        }
        InputStream inputStream = new BufferedInputStream(new FileInputStream(f));
        DigestInputStream digestInputStream = new DigestInputStream(inputStream, messageDigests);
        byte[] buffer = new byte[1024];
        int read = 0;
        do {
            try {
                read = digestInputStream.read(buffer);
            } catch (IOException e) {
                throw new FileNotFoundException("Hashing process have error occur. Please check file");
            }
        } while (read != -1);
        return messageDigests.digest();
    }

    private byte[] signOrHashFileHMAC(String fileIn) throws FileNotFoundException {
        File f = new File(fileIn);
        if (!f.exists()) {
            throw new FileNotFoundException("File not found");
        }
        InputStream inputStream = new BufferedInputStream(new FileInputStream(f));
        int read = 0;
        byte[] buffer = new byte[1024];
        try {

            while ((read = inputStream.read(buffer)) != -1) {
                mac.update(buffer, 0, read);
            }
        } catch (IOException e) {
            throw new FileNotFoundException("Hashing process have error occur. Please check file");
        }
        return mac.doFinal();
    }

    @Override
    public ICipherEnum getCipher() {
        return ((HashKeyHelper) key.getKey()).getKey();
    }


    @Override
    public void updateKey(Object[] key) {
        HashKeyHelper hashKeyHelper = (HashKeyHelper) this.key.getKey();
        hashKeyHelper.setHex((boolean) key[0]);
        hashKeyHelper.setHMAC((boolean) key[1]);
        hashKeyHelper.setKeyHmac((String) key[2]);

        if (hashKeyHelper.isHMAC()) {
            genMessageDigest();
        } else {
            genHMACMessageDigest();
        }
    }

    public static void main(String[] args) {
        HashAlgorithm algorithm = new HashAlgorithm(Hash.SHA_512, true, true, "hello");
        algorithm.genKey();
        String input = "Nguyễn Văn Á";
        String encrypt = null;
        try {
            encrypt = algorithm.signOrHashFile("/home/tan/Documents/doanweb/atbm/tan/src/main/resources/Roboto.zip");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(encrypt);
//        for (Provider provider : Security.getProviders()) {
//            System.out.println("Provider: " + provider);
//            Security.getProvider(provider.getName()).getServices().forEach(service -> {
//                if (service.getType().equalsIgnoreCase("Mac")) {
//                    System.out.println("Algorithm: " + service.getAlgorithm());
//                }
//            });
//        }
    }
}
