package ui.panels.helpers;

import javax.swing.*;
import java.awt.*;
import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * Represents a panel that prompts the user to enter a task date.
 * 
 * Fields:
 * - selectedDate: the LocalDate chosen by the user
 * - yearField: text field for entering the year
 * - monthField: text field for entering the month
 * - dayField: text field for entering the day
 * - prompt: label prompting the user to enter a date
 * - submitButton: button to submit the date
 */
public class PromptTaskDate extends JPanel {

    private LocalDate selectedDate;
    private JTextField yearField;
    private JTextField monthField;
    private JTextField dayField;
    private JLabel prompt;
    private JButton submitButton;

    /**
     * MODIFIES:
     * - this
     * 
     * EFFECTS:
     * - sets the layout and border of the panel
     * - initializes components (prompt, fields, submit button)
     * - adds components to the panel
     * - shows the popup to the user
     */
    public PromptTaskDate() {
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
     * - initializes the prompt label, input fields, and submit button
     */
    public void initComponents() {
        initPrompt();
        initFields();
        initSubmitButton();
    }

    /**
     * MODIFIES:
     * - this
     * 
     * EFFECTS:
     * - creates the label prompting the user for the date
     */
    private void initPrompt() {
        prompt = new JLabel("Enter task date (Year, Month, Day):");
        prompt.setAlignmentX(CENTER_ALIGNMENT);
    }

    /**
     * MODIFIES:
     * - this
     * 
     * EFFECTS:
     * - creates the year, month, and day input text fields
     */
    private void initFields() {
        yearField = createTextField(4, 60);
        monthField = createTextField(2, 40);
        dayField = createTextField(2, 40);
    }

    /**
     * REQUIRES:
     * - columns > 0
     * - width > 0
     * 
     * MODIFIES:
     * - this
     * 
     * EFFECTS:
     * - creates a JTextField of specified columns and width, aligned to the center
     */
    private JTextField createTextField(int columns, int width) {
        JTextField field = new JTextField(columns);
        field.setMaximumSize(new Dimension(width, 30));
        field.setAlignmentX(CENTER_ALIGNMENT);
        return field;
    }

    /**
     * MODIFIES:
     * - this
     * 
     * EFFECTS:
     * - creates the submit button
     * - adds an action listener to process the input date when clicked
     */
    private void initSubmitButton() {
        submitButton = new JButton("Submit");
        submitButton.setAlignmentX(CENTER_ALIGNMENT);
        submitButton.addActionListener(e -> handleSubmit());
    }

    /**
     * MODIFIES:
     * - this
     * 
     * EFFECTS:
     * - parses the year, month, and day fields into a LocalDate
     * - sets selectedDate to null if parsing fails
     * - closes the popup
     */
    private void handleSubmit() {
        try {
            int year = Integer.parseInt(yearField.getText().trim());
            int month = Integer.parseInt(monthField.getText().trim());
            int day = Integer.parseInt(dayField.getText().trim());
            selectedDate = LocalDate.of(year, month, day);
        } catch (NumberFormatException | DateTimeException ex) {
            selectedDate = null;
        } finally {
            SwingUtilities.getWindowAncestor(this).dispose();
        }
    }

    /**
     * MODIFIES:
     * - this
     * 
     * EFFECTS:
     * - adds the prompt, input fields, and submit button to the panel
     * - arranges fields in a sub-panel with labels for year, month, and day
     */
    public void addComponents() {
        add(prompt);
        add(Box.createVerticalStrut(20));

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.add(new JLabel("Year:"));
        fieldsPanel.add(yearField);
        fieldsPanel.add(new JLabel("Month:"));
        fieldsPanel.add(monthField);
        fieldsPanel.add(new JLabel("Day:"));
        fieldsPanel.add(dayField);
        add(fieldsPanel);

        add(Box.createVerticalStrut(20));
        add(submitButton);
    }

    /**
     * MODIFIES:
     * - creates a JDialog
     * 
     * EFFECTS:
     * - shows the panel inside a modal popup so the user can input a date
     */
    public void showPopup() {
        JOptionPane pane = new JOptionPane(
                this,
                JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null,
                new Object[] {},
                null);

        JDialog dialog = pane.createDialog("Task Date");
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    /**
     * EFFECTS:
     * - returns the LocalDate entered by the user
     * - returns null if the user closes the popup or enters invalid input
     */
    public LocalDate getSelectedDate() {
        return selectedDate;
    }
}
