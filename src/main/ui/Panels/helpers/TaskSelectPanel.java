package ui.panels.helpers;

import javax.swing.*;
import java.awt.*;
import model.Reminder;
import model.Calendar;

/**
 * Represents a panel that prompts the user to select a task from a given
 * calendar.
 *
 * Fields:
 * - selectedTask: Reminder selected by the user
 * - taskBox: JComboBox containing the calendar's tasks
 * - calendar: Calendar to get tasks from
 * - prompt: JLabel prompting the user
 * - selectButton: JButton to confirm selection
 */
public class TaskSelectPanel extends JPanel {
    private Reminder selectedTask;
    private JComboBox<Reminder> taskBox;
    private Calendar calendar;
    private JLabel prompt;
    private JButton selectButton;

    /**
     * REQUIRES:
     * - calendar is not null
     * 
     * MODIFIES:
     * - this
     * 
     * EFFECTS:
     * - sets the layout and border of the panel
     * - initializes components (prompt, task box, select button)
     * - adds components to the panel
     * - shows the popup to the user
     */
    public TaskSelectPanel(Calendar calendar) {
        this.calendar = calendar;

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
     * - creates the prompt label, task combo box, and select button
     * - populates taskBox with the tasks from the calendar
     * - adds an action listener to the select button to store the selected task and
     * close the popup
     */
    private void initComponents() {
        prompt = new JLabel("Select a task:");
        prompt.setAlignmentX(CENTER_ALIGNMENT);

        taskBox = new JComboBox<>();
        for (Reminder t : calendar.getTasks()) {
            taskBox.addItem(t);
        }
        taskBox.setMaximumSize(new Dimension(250, 30));
        taskBox.setAlignmentX(CENTER_ALIGNMENT);

        selectButton = new JButton("Select");
        selectButton.setAlignmentX(CENTER_ALIGNMENT);
        selectButton.addActionListener(e -> {
            selectedTask = (Reminder) taskBox.getSelectedItem();
            SwingUtilities.getWindowAncestor(this).dispose();
        });
    }

    /**
     * MODIFIES:
     * - this
     * 
     * EFFECTS:
     * - adds the prompt, task combo box, and select button to the panel
     * - creates spacing between components
     */
    private void addComponents() {
        add(prompt);
        add(Box.createVerticalStrut(20));
        add(taskBox);
        add(Box.createVerticalStrut(20));
        add(selectButton);
    }

    /**
     * MODIFIES:
     * - creates a JDialog
     * 
     * EFFECTS:
     * - displays the panel inside a modal popup so the user can select a task
     */
    private void showPopup() {
        JOptionPane pane = new JOptionPane(
                this,
                JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null,
                new Object[] {},
                null);
        JDialog dialog = pane.createDialog("Select Task");
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    /**
     * EFFECTS:
     * - returns the task selected by the user
     * - returns null if the user closes the popup without selecting a task
     */
    public Reminder getSelectedTask() {
        return selectedTask;
    }
}
