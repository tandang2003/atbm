package app;

import controller.MainController;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.swing.*;
import java.security.Security;

public class main {
    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());
        try {
            // Check if Twofish is available
            Cipher cipher = Cipher.getInstance("Twofish", "BC");
            System.out.println("Twofish is supported!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        new MainController();
    }
}
