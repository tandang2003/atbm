package view.font;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class MyFont {
    public static String ROBOTO_REGULAR = "/Roboto/Roboto-Regular.ttf";
    public static String ROBOTO_BOLD = "/Roboto/Roboto-Bold.ttf";
//    public static String ROBOTO_ITALIC = "/Roboto/Roboto-Italic.ttf";


    private MyFont() {
    }


    public static Font loadCustomFont(String path, float size) {
        try {
            InputStream
                    is = MyFont.class.getResourceAsStream(path);
            // Load font from file
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            is.close();
            return font.deriveFont(size); // Set font size
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return null; // Return null if font loading fails
        }
    }
}
