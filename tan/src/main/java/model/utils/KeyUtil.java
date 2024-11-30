package model.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class KeyUtil {
    private static final String KEY_PATH = System.getProperty("user.home") + File.separator + ".tan";
    private static final String KEY_PATH_WINDOWS = System.getenv("USERPROFILE") + File.separator + ".tan";
//    private static final String KEY_PATH = "/home/tan/Documents/doanweb/atbm/tan/src/main/resources/test.tan";

    public static String getPrivateKey(String publicKey) {
        String path = KEY_PATH;
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                path = KEY_PATH_WINDOWS;
            }
            File file = new File(path);
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
        String path = KEY_PATH;
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                path = KEY_PATH_WINDOWS;
            }
            File file = new File(path);
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

    }
}
