package ui.panels.helpers;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a panel that prompts the user to select a task field.
 * 
 * Fields:
 * - selectedField: the field chosen by the user
 * - fieldBox: JComboBox containing the field options
 * - prompt: label prompting the user to select a field
 * - submitButton: button to submit the selected field
 */
public class PromptTaskField extends JPanel {

    private String selectedField;
    private JComboBox<String> fieldBox;
    private JLabel prompt;
    private JButton submitButton;

    private static final String[] OPTIONS = { "Name", "Date", "Time", "Info" };

    /**
     * MODIFIES:
     * - this
     * 
     * EFFECTS:
     * - sets the layout and border of the panel
     * - initializes components (prompt, combo box, submit button)
     * - adds components to the panel
     * - shows the popup to the user
     */
    public PromptTaskField() {
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
     * - creates the prompt label, combo box, and submit button
     * - adds an action listener to the submit button to save the selection and
     * close the popup
     */
    public void initComponents() {
        prompt = new JLabel("Select a task field:");
        prompt.setAlignmentX(CENTER_ALIGNMENT);

        fieldBox = new JComboBox<>(OPTIONS);
        fieldBox.setMaximumSize(new Dimension(200, 30));
        fieldBox.setAlignmentX(CENTER_ALIGNMENT);

        submitButton = new JButton("Submit");
        submitButton.setAlignmentX(CENTER_ALIGNMENT);
        submitButton.addActionListener(e -> {
            selectedField = (String) fieldBox.getSelectedItem();
            SwingUtilities.getWindowAncestor(this).dispose();
        });
    }

    /**
     * MODIFIES:
     * - this
     * 
     * EFFECTS:
     * - adds the prompt label, combo box, and submit button to the panel
     * - adds spacing between components
     */
    public void addComponents() {
        add(prompt);
        add(Box.createVerticalStrut(20));
        add(fieldBox);
        add(Box.createVerticalStrut(20));
        add(submitButton);
    }

    /**
     * MODIFIES:
     * - creates a JDialog
     * 
     * EFFECTS:
     * - shows the panel inside a modal popup so the user can select a task field
     */
    public void showPopup() {
        JOptionPane pane = new JOptionPane(
                this,
                JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null,
                new Object[] {},
                null);

        JDialog dialog = pane.createDialog("Select Task Field");
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    /**
     * EFFECTS:
     * - returns the field selected by the user
     * - returns null if the user closes the popup without selecting
     */
    public String getSelectedField() {
        return selectedField;
    }
}
