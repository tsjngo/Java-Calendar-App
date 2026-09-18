package ui.panels.popup;

import java.awt.Image;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Represents the Cannot popup
 * 
 * imports:
 * - awt, swing, and panels to create the popup
 */
public class SuccessPanel extends JPanel {

    /**
     * EFFECTS:
     * - instantiates an image and scales it to 2x smaller ( /2) and adds it to the popup OptionPane
     * - creates a label with the text provided and adds it to the popup
     * - creates the popup using the image and text and displays it
     */
    public SuccessPanel(String message) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        ImageIcon image = new ImageIcon("./data/success.png");

        Image scaledImage = image.getImage().getScaledInstance(256, 256, Image.SCALE_SMOOTH);

        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

        JLabel successImage = new JLabel(scaledImageIcon);

        successImage.setAlignmentX(CENTER_ALIGNMENT);
        add(successImage);

        JLabel successMessage = new JLabel(message);
        successMessage.setAlignmentX(CENTER_ALIGNMENT);
        add(successMessage);

        JOptionPane.showMessageDialog(
                null,
                this,
                "Status",
                JOptionPane.PLAIN_MESSAGE);
    }
}
