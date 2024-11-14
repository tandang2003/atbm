package view.font;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MyFont {
    public static String ROBOTO_REGULAR = MyFont.class.getResource("/Roboto/Roboto-Regular.ttf").getPath();
    public static String ROBOTO_BOLD = MyFont.class.getResource("/Roboto/Roboto-Bold.ttf").getPath();
    public static String ROBOTO_ITALIC = MyFont.class.getResource("/Roboto/Roboto-Italic.ttf").getPath();


    private MyFont() {
    }


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
