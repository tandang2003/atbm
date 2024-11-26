package view.custom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class TextAreaCus extends JTextArea {

    private String placeholder;

    public TextAreaCus(String placeholder) {
        this.placeholder = placeholder;
        initPlaceholderListener();
    }

    private void initPlaceholderListener() {
        // Add focus listener to hide/show placeholder
        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                repaint(); // Redraw to hide the placeholder
            }

            @Override
            public void focusLost(FocusEvent e) {
                repaint(); // Redraw to show the placeholder
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (getText().isEmpty() && !isFocusOwner() && placeholder != null) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(new Color(150, 150, 150)); // Set a clear, visible gray color
//            g2.setFont(getFont().deriveFont(Font.ITALIC)); // Italicize the font for the placeholder
                    // Calculate text positioning respecting insets
            Insets insets = getInsets();
            int x = insets.left + 2; // Slight padding from the left
            int y = insets.top + g.getFontMetrics().getAscent(); // Align with the text baseline

            g2.drawString(placeholder, x, y);
            g2.dispose();
        }
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        repaint();
    }

    public static void main(String[] args) {
        // Test the TextAreaCus
        JFrame frame = new JFrame("Placeholder JTextArea Example");
        TextAreaCus textArea = new TextAreaCus("Type something here...");
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        textArea.setRows(5);
        textArea.setColumns(30);
        TextAreaCus textArea1 = new TextAreaCus("Type something here...");
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        textArea.setRows(5);
        textArea.setColumns(30);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(2,1));
        frame.add((textArea));
        frame.add((textArea1));
        frame.pack();
        frame.setVisible(true);
    }

}