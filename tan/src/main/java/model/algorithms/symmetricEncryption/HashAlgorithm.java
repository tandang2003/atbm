package model.algorithms.symmetricEncryption;

import model.algorithms.AAlgorithm;
import model.common.Cipher;

import javax.crypto.IllegalBlockSizeException;
import java.io.*;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashAlgorithm extends AAlgorithm {
    private MessageDigest messageDigests;
    private String cipher;

    public HashAlgorithm() {
        super();
    }

    @Override
    protected boolean validation() {
        return false;
    }

    @Override
    public void genKey() {
        try {
            messageDigests = MessageDigest.getInstance(cipher);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String encrypt(String input) throws IllegalBlockSizeException {
        byte[] bytes = input.getBytes();
        byte[] digest = messageDigests.digest(bytes);
        BigInteger no = new BigInteger(1, digest);
        return no.toString(16);
    }

    @Override
    public boolean encryptFile(String fileIn, String fileOut) throws IOException {
        File f = new File(fileIn);
        if (!f.exists()) {
            throw new IOException("File not found");
        }
        InputStream inputStream = new BufferedInputStream(new FileInputStream(f));
        DigestInputStream digestInputStream = new DigestInputStream(inputStream, messageDigests);
        return false;
//        return super.encryptFile(fileIn, fileOut);

    }

    @Override
    public Cipher getCipher() {
        return null;
    }

    @Override
    public void updateKey(Object[] key) {

    }
}
