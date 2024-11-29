package app;

import controller.MainController;
import view.VFrame;

import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class main {
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        new MainController();
    }
}
