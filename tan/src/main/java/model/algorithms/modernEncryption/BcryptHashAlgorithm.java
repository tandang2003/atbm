package model.algorithms.modernEncryption;

import at.favre.lib.crypto.bcrypt.BCrypt;
import model.algorithms.AAlgorithm;
import model.common.Hash;
import model.common.ICipherEnum;
import model.key.HashKeyHelper;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Base64;

public class BcryptHashAlgorithm extends HashAlgorithm {
    public BcryptHashAlgorithm() {
    }

    @Override
    public void genKey() {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String encrypt(String input) {
        byte[] digest = BCrypt.withDefaults().hash(12, input.toCharArray());
        if (!((HashKeyHelper) key.getKey()).isHex()) {
            return Base64.getEncoder().encodeToString(digest);
        } else {
            BigInteger no = new BigInteger(1, digest);
            return no.toString(16);
        }
    }

    @Override
    public String signOrHashFile(String fileIn) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ICipherEnum getCipher() {
        return Hash.BCrypt;
    }

    @Override
    public boolean verify(String input, String sign) {
        return BCrypt.verifyer().verify(input.toCharArray(), sign).verified;
    }

    @Override
    public void updateKey(Object[] key) {
        HashKeyHelper hashKeyHelper = (HashKeyHelper) this.key.getKey();
        hashKeyHelper.setHex((boolean) key[0]);
        hashKeyHelper.setHMAC((boolean) key[1]);
        hashKeyHelper.setKeyHmac((String) key[2]);
    }

    @Override
    public boolean validation() {
        return true;
    }

    public static void main(String[] args) {
        BcryptHashAlgorithm bcryptHashAlgorithm = new BcryptHashAlgorithm();
        String input = "/home/tan/Documents/doanweb/atbm/tan/src/main/resources/Roboto.zip";
        String encrypted = null;
        encrypted = bcryptHashAlgorithm.encrypt(input);
        System.out.println(bcryptHashAlgorithm.verify(input, encrypted));
    }
}
