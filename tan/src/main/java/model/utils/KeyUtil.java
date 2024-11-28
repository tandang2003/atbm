package model.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class KeyUtil {
    //    private static final String KEY_PATH = System.getProperty("user.home") + File.separator + "key.tan";
    private static final String KEY_PATH = "/home/tan/Documents/doanweb/atbm/tan/src/main/resources/test.tan";

    public static String getPrivateKey(String publicKey) {
        try {
            File file = new File(KEY_PATH);
            if (!file.exists()) {
                return null;
            }
            DataInputStream dis = new DataInputStream(new FileInputStream(file));
            String line = "";
            while ((line = dis.readLine()) != null) {
                if (line.trim().equals(publicKey)) {
                    return dis.readLine();
                }
            }
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static boolean saveKey(String publicKey, String privateKey) {
        try {
            File file = new File(KEY_PATH);
            if (!file.exists()) {
                file.createNewFile();
            }
            DataInputStream dis = new DataInputStream(new FileInputStream(file));
            String line = "";
            while ((line = dis.readLine()) != null) {
                if (line.equals(publicKey)) {
                    return false;
                }
            }
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file, true));
            dataOutputStream.write((publicKey + "\n").getBytes());
            dataOutputStream.write((privateKey + "\n").getBytes());
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
//        saveKey("public1", "private1");
//        saveKey("public2", "private2");
//        saveKey("public3", "private3");
//        saveKey("public4", "private4");
//        saveKey("public5", "như thế ");
        System.out.println(getPrivateKey("public5"));
    }
}
