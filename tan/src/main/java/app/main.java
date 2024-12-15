package app;

import controller.MainController;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.swing.*;
import java.security.Security;

public class main {
    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());
        new MainController();
    }
}
