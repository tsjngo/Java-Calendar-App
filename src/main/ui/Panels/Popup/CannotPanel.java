package ui.panels.popup;

import java.awt.Image;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Represents the success popup
 * 
 * imports:
 * - awt, swing, and panels to create the popup
 */
public class CannotPanel extends JPanel {

    /**
     * EFFECTS:
     * - instantiates an image and scales it to 2x smaller ( /2) and adds it to the popup OptionPane
     * - creates a label with the text provided and adds it to the popup
     * - creates the popup using the image and text and displays it
     */
    public CannotPanel(String message) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        ImageIcon image = new ImageIcon("./data/cannot.png");

        Image scaledImage = image.getImage().getScaledInstance(256, 256, Image.SCALE_SMOOTH);

        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

        JLabel cannotImage = new JLabel(scaledImageIcon);

        cannotImage.setAlignmentX(CENTER_ALIGNMENT);
        add(cannotImage);

        JLabel cannotMessage = new JLabel(message);
        cannotMessage.setAlignmentX(CENTER_ALIGNMENT);
        add(cannotMessage);

        JOptionPane.showMessageDialog(
                null,
                this,
                "Status",
                JOptionPane.PLAIN_MESSAGE);
    }
}
