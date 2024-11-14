package view;

import controller.MainController;
import view.font.MyFont;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

import static model.common.Button.FONTSIZE_NORMAL;
import static view.font.MyFont.ROBOTO_REGULAR;

public class VMenuBar extends JMenuBar {

    private Font font;
    private JMenu languageMenu;
    private JCheckBoxMenuItem englishItem, vietnameseItem;
    private MainController controller;

    public VMenuBar(MainController controller) {
        this.controller = controller;
        font = MyFont.loadCustomFont(ROBOTO_REGULAR, FONTSIZE_NORMAL);
        setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK));
        init();
        event();
    }

    private void init() {
        // Create the "Language" menu
        languageMenu = new JMenu("Language");
        // Create menu items for English and Vietnamese with checkmarks
        englishItem = new JCheckBoxMenuItem("English");
        vietnameseItem = new JCheckBoxMenuItem("Vietnamese");


        // Add the items to the language menu
        languageMenu.add(englishItem);
        languageMenu.add(vietnameseItem);

        // Add the language menu to the menu bar
        add(languageMenu);
        controller.notifyObservers();

        // Set the initial selection
        if (controller.isEnglish()) {
            englishItem.setSelected(true);
            vietnameseItem.setSelected(false);
        } else {
            vietnameseItem.setSelected(true);
            englishItem.setSelected(false);
        }
    }

    private void event() {
        englishItem.addActionListener(e -> {
            englishItem.setSelected(true);
            vietnameseItem.setSelected(false);
            controller.setLanguage(true);
            controller.notifyObservers();

        });
        vietnameseItem.addActionListener(e -> {
            vietnameseItem.setSelected(true);
            englishItem.setSelected(false);
            controller.setLanguage(false);
            controller.notifyObservers();

        });
    }
}
