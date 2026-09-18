package ui.panels.helpers;

import javax.swing.*;
import java.awt.*;
import java.time.DateTimeException;
import java.time.LocalTime;

/**
 * Represents a panel that prompts the user to enter a task time.
 * 
 * Fields:
 * - selectedTime: the time entered by the user
 * - hourField: text field for entering the hour
 * - minuteField: text field for entering the minute
 * - prompt: label prompting the user to enter the time
 * - submitButton: button to submit the entered time
 */
public class PromptTaskTime extends JPanel {

    private LocalTime selectedTime;
    private JTextField hourField;
    private JTextField minuteField;
    private JLabel prompt;
    private JButton submitButton;

    /**
     * MODIFIES:
     * - this
     * 
     * EFFECTS:
     * - sets the layout and border of the panel
     * - initializes components (prompt, input fields, submit button)
     * - adds components to the panel
     * - shows the popup to the user
     */
    public PromptTaskTime() {
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
     * - creates the prompt label, input fields for hour and minute, and submit button
     * - adds an action listener to the submit button to save the input and close the popup
     */
    public void initComponents() {
        prompt = new JLabel("Enter task time (Hour and Minute, 24-hour format):");
        prompt.setAlignmentX(CENTER_ALIGNMENT);

        hourField = new JTextField(2);
        hourField.setMaximumSize(new Dimension(40, 30));
        hourField.setAlignmentX(CENTER_ALIGNMENT);

        minuteField = new JTextField(2);
        minuteField.setMaximumSize(new Dimension(40, 30));
        minuteField.setAlignmentX(CENTER_ALIGNMENT);

        submitButton = new JButton("Submit");
        submitButton.setAlignmentX(CENTER_ALIGNMENT);
        submitButton.addActionListener(e -> {
            try {
                int hour = Integer.parseInt(hourField.getText().trim());
                int minute = Integer.parseInt(minuteField.getText().trim());
                selectedTime = LocalTime.of(hour, minute);
            } catch (NumberFormatException | DateTimeException ex) {
                selectedTime = null;
            } finally {
                SwingUtilities.getWindowAncestor(this).dispose();
            }
        });
    }

    /**
     * MODIFIES:
     * - this
     * 
     * EFFECTS:
     * - adds the prompt label, input fields, and submit button to the panel
     * - adds spacing between components
     */
    public void addComponents() {
        add(prompt);
        add(Box.createVerticalStrut(20));

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.add(new JLabel("Hour:"));
        fieldsPanel.add(hourField);
        fieldsPanel.add(new JLabel("Minute:"));
        fieldsPanel.add(minuteField);
        add(fieldsPanel);

        add(Box.createVerticalStrut(20));
        add(submitButton);
    }

    /**
     * MODIFIES:
     * - creates a JDialog
     * 
     * EFFECTS:
     * - shows the panel inside a modal popup so the user can enter the task time
     */
    public void showPopup() {
        JOptionPane pane = new JOptionPane(
                this,
                JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null,
                new Object[] {},
                null
        );

        JDialog dialog = pane.createDialog("Task Time");
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    /**
     * EFFECTS:
     * - returns the time entered by the user as a LocalTime
     * - returns null if the user closes the popup or enters an invalid time
     */
    public LocalTime getSelectedTime() {
        return selectedTime;
    }
}
