package model.algorithms.symmetricEncryption;

import javax.crypto.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class DESAlgorithm {
    private Cipher cipher;
    private SecretKey key;

    public SecretKey genKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        keyGenerator.init(56);
        key = keyGenerator.generateKey();
        return key;
    }

    public void loadKey(SecretKey inputKey) {
        key = inputKey;
    }

    public byte[] encrypt(String text) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] dt = text.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream bais = new ByteArrayInputStream(dt);
        CipherInputStream cis = new CipherInputStream(bais, cipher);
        byte[] buffer = new byte[1024];
        byte[] encrypted = null;
        int i = 0;
        while ((i = cis.read(buffer)) != -1) {
            encrypted = expand(encrypted, buffer, i);
        }
        cis.close();
        bais.close();
        return encrypted;
    }

    public byte[] expand(byte[] data, byte[] expand, int limit) {
        if (data == null) {
            byte[] out = new byte[limit];
            System.arraycopy(expand, 0, out, 0, limit);
            return out;
        }
        byte[] out = new byte[data.length + limit];
        System.arraycopy(data, 0, out, 0, data.length);
        System.arraycopy(expand, 0, out, data.length, limit);

        return out;

    }

    public String decrypt(byte[] encryptInput) throws InvalidKeyException, IOException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] dt = encryptInput;
        ByteArrayInputStream bais = new ByteArrayInputStream(dt);
        CipherInputStream cis = new CipherInputStream(bais, cipher);
        byte[] buffer = new byte[1024];
        byte[] decrypted = null;
        int i = 0;
        while ((i = cis.read(buffer)) != -1) {
            decrypted = expand(decrypted, buffer, i);
        }
        cis.close();
        bais.close();
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    public String encryptBase64(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes());
    }

    public String decryptBase64(String encryptInput) {
        return new String(Base64.getDecoder().decode(encryptInput.getBytes()));

    }

    public boolean encryptFile(String input, String output) {
        File fileout = new File(output);
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        ByteArrayInputStream bais = null;
        CipherInputStream cis = null;

        try {
            if (!fileout.exists()) {
                fileout.createNewFile();
            }
            bis = new BufferedInputStream(new FileInputStream(input));
            bos = new BufferedOutputStream(new FileOutputStream(fileout));
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] dt = bis.readAllBytes();
            bais = new ByteArrayInputStream(dt);
            cis = new CipherInputStream(bais, cipher);
            byte[] buffer = new byte[1024];
            byte[] decrypted = null;
            int i = 0;
            while ((i = cis.read(buffer)) != -1) {
                bos.write(buffer, 0, i);
            }
            return true;

        } catch (IOException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } finally {

            try {
                if (bis != null)
                    bis.close();
                if (cis != null)
                    cis.close();
                if (bais != null)
                    bais.close();
                if (bos != null)
                    bos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }

    public boolean decryptFile(String input, String output) {
        File fileout = new File(output);
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        ByteArrayInputStream bais = null;
        CipherInputStream cis = null;

        try {
            if (!fileout.exists()) {
                fileout.createNewFile();
            }
            bis = new BufferedInputStream(new FileInputStream(input));
            bos = new BufferedOutputStream(new FileOutputStream(fileout));
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] dt = bis.readAllBytes();
            bais = new ByteArrayInputStream(dt);
            cis = new CipherInputStream(bais, cipher);
            byte[] buffer = new byte[1024];
            byte[] decrypted = null;
            int i = 0;
            while ((i = cis.read(buffer)) != -1) {
                bos.write(buffer, 0, i);
            }
            return true;

        } catch (IOException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } finally {

            try {
                if (bis != null)
                    bis.close();
                if (cis != null)
                    cis.close();
                if (bais != null)
                    bais.close();
                if (bos != null)
                    bos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, IOException, InvalidKeyException {
        String str = "The arraycopy method definitely does not change the size of its argument arrays.\n" +
                "\n" +
                "What is going on here is that you misunderstand how (supposedly) multi-dimensional arrays work in Java.\n" +
                "\n" +
                "The reality is that Java arrays are essentially one dimensional. For instance, a String[][] is actually an array of arrays of Strings. When you use arraycopy to copy from one String[][] to another, you are not copying the references to the Strings. Rather you are actually performing a shallow copy of the references to the String[] objects. In your case, that is changing the number of columns ... according to your mental app.main.model of the array.\n" +
                "\n" +
                "Remember, the arraycopy method simply does a shallow copy of the top level array. It is simply treating the String[][] as an array of String[] objects ... and copying their references from the source array to the destination array.\n" +
                "\n" +
                "If you want to keep the number of columns the same, the simplest way is to copy the elements with nested for loops. I expect that if you profiled the code, you would find that the time you are saving with your use of arraycopy is small compared with the overheads of 1) performing the database query, 2) transferring the data through the resultset, and 3) copying it into a List, and 4) the toArray(...) call.";
        DESAlgorithm des = new DESAlgorithm();
        des.genKey();
        System.out.println(des.encryptFile("src/testData/CV.pdf", "src/testData/encrypt.txt"));
        System.out.println(des.decryptFile("src/testData/encrypt.txt", "src/testData/decrypt.pdf"));
//       byte[] b= des.encrypt(str);
//        System.out.println(Base64.getEncoder().encodeToString(b));
//        System.out.println( des.decrypt(b));
    }
}
