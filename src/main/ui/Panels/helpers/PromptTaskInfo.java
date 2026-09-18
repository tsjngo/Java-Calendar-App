package ui.panels.helpers;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a panel that prompts the user to enter additional information for
 * a task.
 * 
 * Fields:
 * - taskInfo: the information entered by the user
 * - infoField: text field for entering information
 * - prompt: label prompting the user to enter information
 * - submitButton: button to submit the information
 */
public class PromptTaskInfo extends JPanel {

    private String taskInfo;
    private JTextField infoField;
    private JLabel prompt;
    private JButton submitButton;

    /**
     * MODIFIES:
     * - this
     * 
     * EFFECTS:
     * - sets the layout and border of the panel
     * - initializes components (prompt, input field, submit button)
     * - adds components to the panel
     * - shows the popup to the user
     */
    public PromptTaskInfo() {
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
     * - creates the prompt label, input field, and submit button
     * - adds an action listener to the submit button to save the input and close
     * the popup
     */
    public void initComponents() {
        prompt = new JLabel("Enter additional information for the task:");
        prompt.setAlignmentX(CENTER_ALIGNMENT);

        infoField = new JTextField(20);
        infoField.setMaximumSize(new Dimension(300, 30));
        infoField.setAlignmentX(CENTER_ALIGNMENT);

        submitButton = new JButton("Submit");
        submitButton.setAlignmentX(CENTER_ALIGNMENT);
        submitButton.addActionListener(e -> {
            taskInfo = infoField.getText().trim();
            SwingUtilities.getWindowAncestor(this).dispose();
        });
    }

    /**
     * MODIFIES:
     * - this
     * 
     * EFFECTS:
     * - adds the prompt label, input field, and submit button to the panel
     * - adds spacing between components
     */
    public void addComponents() {
        add(prompt);
        add(Box.createVerticalStrut(20));
        add(infoField);
        add(Box.createVerticalStrut(20));
        add(submitButton);
    }

    /**
     * MODIFIES:
     * - creates a JDialog
     * 
     * EFFECTS:
     * - shows the panel inside a modal popup so the user can enter task information
     */
    public void showPopup() {
        JOptionPane pane = new JOptionPane(
                this,
                JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null,
                new Object[] {},
                null);

        JDialog dialog = pane.createDialog("Task Information");
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    /**
     * EFFECTS:
     * - returns the information entered by the user
     * - returns null if the user closes the popup without entering anything
     */
    public String getTaskInfo() {
        return taskInfo;
    }
}
