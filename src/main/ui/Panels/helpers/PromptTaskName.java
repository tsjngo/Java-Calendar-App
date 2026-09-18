package ui.panels.helpers;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a panel that prompts the user to enter a task name.
 * 
 * Fields:
 * - taskName: the name entered by the user
 * - taskField: text field for entering the task name
 * - prompt: label prompting the user to enter the task name
 * - submitButton: button to submit the entered task name
 */
public class PromptTaskName extends JPanel {

    private String taskName;
    private JTextField taskField;
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
    public PromptTaskName() {
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
        prompt = new JLabel("Enter task name:");
        prompt.setAlignmentX(CENTER_ALIGNMENT);

        taskField = new JTextField(20);
        taskField.setMaximumSize(new Dimension(300, 30));
        taskField.setAlignmentX(CENTER_ALIGNMENT);

        submitButton = new JButton("Submit");
        submitButton.setAlignmentX(CENTER_ALIGNMENT);
        submitButton.addActionListener(e -> {
            taskName = taskField.getText().trim();
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
        add(taskField);
        add(Box.createVerticalStrut(20));
        add(submitButton);
    }

    /**
     * MODIFIES:
     * - creates a JDialog
     * 
     * EFFECTS:
     * - shows the panel inside a modal popup so the user can enter a task name
     */
    public void showPopup() {
        JOptionPane pane = new JOptionPane(
                this,
                JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null,
                new Object[] {},
                null);

        JDialog dialog = pane.createDialog("Task Name");
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    /**
     * EFFECTS:
     * - returns the task name entered by the user
     * - returns null if the user closes the popup without entering a name
     */
    public String getTaskName() {
        return taskName;
    }
}
