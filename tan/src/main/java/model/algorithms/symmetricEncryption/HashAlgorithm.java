package model.algorithms.symmetricEncryption;

import model.algorithms.AAlgorithm;
import model.common.Cipher;
import model.key.AsymmetricKeyHelper;
import model.key.HashKey;
import model.key.HashKeyHelper;
import model.key.SymmetricKeyHelper;

import javax.crypto.IllegalBlockSizeException;
import java.io.*;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class HashAlgorithm extends AAlgorithm {

    private MessageDigest messageDigests;

    public HashAlgorithm(Cipher cipher) {
        this.key = new HashKey(new HashKeyHelper(cipher));
    }

    public HashAlgorithm(Cipher cipher, String provider) {
        this.key = new HashKey(new HashKeyHelper(cipher));
    }

    @Override
    protected boolean validation() {
        return false;
    }

    @Override
    public void genKey() {
        try {
            messageDigests = MessageDigest.getInstance(((HashKeyHelper) key.getKey()).getKey().getName());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String encrypt(String input)  {
        byte[] bytes = input.getBytes();
        byte[] digest = messageDigests.digest(bytes);
        BigInteger no = new BigInteger(1, digest);
        return no.toString(16);
    }

    @Override
    public String signOrHashFile(String fileIn) throws IOException {
        File f = new File(fileIn);
        if (!f.exists()) {
            throw new IOException("File not found");
        }
        InputStream inputStream = new BufferedInputStream(new FileInputStream(f));
        DigestInputStream digestInputStream = new DigestInputStream(inputStream, messageDigests);
        byte[] buffer = new byte[1024];
        int read = 0;
        do {
            read = digestInputStream.read(buffer);
        } while (read != -1);
        BigInteger bigInteger = new BigInteger(1, messageDigests.digest());
        return bigInteger.toString(16);
    }

    @Override
    public Cipher getCipher() {
        return ((HashKeyHelper) key.getKey()).getKey();
    }


    @Override
    public void updateKey(Object[] key) {

    }
}
