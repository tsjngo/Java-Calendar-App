package ui.panels.helpers;

import javax.swing.*;

/**
 * Represents a panel that prompts the user whether they want to add extra
 * information for a task.
 * 
 * Fields:
 * - wantsInfo: Boolean indicating if the user wants to add info
 * - prompt: JLabel prompting the user
 * - yesButton: JButton to select "Yes"
 * - noButton: JButton to select "No"
 */
public class PromptTaskWantsInfo extends JPanel {

    private Boolean wantsInfo;
    private JLabel prompt;
    private JButton yesButton;
    private JButton noButton;

    /**
     * MODIFIES:
     * - this
     * 
     * EFFECTS:
     * - sets the layout and border of the panel
     * - initializes components (prompt, Yes/No buttons)
     * - adds components to the panel
     * - shows the popup to the user
     */
    public PromptTaskWantsInfo() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        initComponents();
        addComponents();
        showPopup();
    }

    /**
     * MODIFIES:
     * - this
     * 
     * EFFECTS:
     * - creates the prompt label, Yes button, and No button
     * - adds action listeners to the buttons to set wantsInfo and close the popup
     */
    public void initComponents() {
        prompt = new JLabel("Do you want to add extra info?");
        prompt.setAlignmentX(CENTER_ALIGNMENT);

        yesButton = new JButton("Yes");
        yesButton.setAlignmentX(CENTER_ALIGNMENT);
        yesButton.addActionListener(e -> {
            wantsInfo = true;
            SwingUtilities.getWindowAncestor(this).dispose();
        });

        noButton = new JButton("No");
        noButton.setAlignmentX(CENTER_ALIGNMENT);
        noButton.addActionListener(e -> {
            wantsInfo = false;
            SwingUtilities.getWindowAncestor(this).dispose();
        });
    }

    /**
     * MODIFIES:
     * - this
     * 
     * EFFECTS:
     * - adds the prompt label and buttons to the panel
     * - adds spacing between components
     */
    public void addComponents() {
        add(prompt);
        add(Box.createVerticalStrut(20));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(yesButton);
        buttonsPanel.add(noButton);
        add(buttonsPanel);
    }

    /**
     * MODIFIES:
     * - creates a JDialog
     * 
     * EFFECTS:
     * - shows the panel inside a modal popup so the user can select Yes or No
     */
    public void showPopup() {
        JOptionPane pane = new JOptionPane(
                this,
                JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null,
                new Object[] {},
                null);

        JDialog dialog = pane.createDialog("Task Requires Info");
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    /**
     * EFFECTS:
     * - returns true if the user selected Yes, false if No
     * - returns null if the user closed the popup without selecting
     */
    public Boolean getWantsInfo() {
        return wantsInfo;
    }
}
