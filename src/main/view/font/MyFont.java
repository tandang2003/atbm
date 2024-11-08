package main.view.font;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MyFont {
    public static String ROBOTO_REGULAR = "src/main/lib/Roboto/Roboto-Regular.ttf";
    public static String ROBOTO_BOLD = "src/main/lib/Roboto/Roboto-Bold.ttf";
    public static String ROBOTO_ITALIC = "src/main/lib/Roboto/Roboto-Italic.ttf";


    private MyFont() {
    }

//    public static Font getRoboto(String type, float size) {
//        return loadCustomFont(type, size);
//    }
//    private void init(){
//        Font customFont = loadCustomFont("src/main/lib/Roboto/Roboto-Regular.ttf", 18f);
//
//    }

    public static Font loadCustomFont(String path, float size) {
        try {
            // Load font from file
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File(path));
            return font.deriveFont(size); // Set font size
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return null; // Return null if font loading fails
        }
    }
}
