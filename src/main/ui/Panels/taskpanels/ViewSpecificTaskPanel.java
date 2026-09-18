package ui.panels.taskpanels;

import javax.swing.*;
import java.awt.*;

import model.Calendar;
import model.CalendarList;
import model.Reminder;
import ui.CalendarGUI;
import ui.panels.helpers.*;
import ui.panels.popup.CannotPanel;

/**
 * Represents the view specific task panel for task panel
 * 
 * Fields:
 * - CalendarList calendarList
 * - Calendar selectedCalendar
 * - Reminder selectedTask
 * - JLabel viewTaskLabel
 * - JTextArea taskInfoArea
 * - JScrollPane scrollPane
 * - JButton doneButton
 * 
 * imports:
 * - awt, swing to create the panel
 * - calendarlist and calendar to interact with backend
 * - panels.helpers to select calendar and task
 * - panels.popup to show errors
 */
public class ViewSpecificTaskPanel extends JPanel {
    private CalendarList calendarList;
    private Calendar selectedCalendar;
    private Reminder selectedTask;
    private CalendarGUI gui;

    private JLabel viewTaskLabel;
    private JTextArea taskInfoArea;
    private JScrollPane scrollPane;
    private JButton doneButton;

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - instantiates gui and calendarList
     * - prompts the user to select a calendar and task
     * - displays the task info in a scrollable popup
     * - provides a done button to return to main menu
     */
    public ViewSpecificTaskPanel(CalendarGUI gui, CalendarList calendarList) {
        this.gui = gui;
        this.calendarList = calendarList;

        promptInformation();
        if (selectedCalendar != null && selectedTask != null) {
            initViewSpecificTaskPanelComponents();
            showPopup();
        }
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - prompts user to select a calendar and a task
     * - displays error popups if either selection is invalid
     */
    public void promptInformation() {
        selectedCalendar = new CalendarSelectPanel(calendarList).getSelectedCalendar();

        if (selectedCalendar == null) {
            new CannotPanel("Calendar is invalid.");
            return;
        }

        if (selectedCalendar.getTasks().isEmpty()) {
            new CannotPanel("No tasks have been added to the calendar.");
            return;
        }

        selectedTask = new TaskSelectPanel(selectedCalendar).getSelectedTask();
        if (selectedTask == null) {
            new CannotPanel("Task is invalid.");
        }
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - creates labels, text area, scroll pane, and done button
     * - sets layout to BorderLayout
     * - fills text area with selected task information
     */
    private void initViewSpecificTaskPanelComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        viewTaskLabel = new JLabel("Task Info:");
        viewTaskLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(viewTaskLabel, BorderLayout.NORTH);

        taskInfoArea = new JTextArea();
        taskInfoArea.setEditable(false);
        taskInfoArea.setLineWrap(true);
        taskInfoArea.setWrapStyleWord(true);
        taskInfoArea.setText(formatTaskInfo(selectedTask));

        scrollPane = new JScrollPane(taskInfoArea);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        add(scrollPane, BorderLayout.CENTER);

        doneButton = new JButton("Done");
        doneButton.addActionListener(e -> {
            gui.showScene("MENU");
            SwingUtilities.getWindowAncestor(this).dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(doneButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * modifies:
     * - none
     * 
     * EFFECTS:
     * - formats the selected task's information into a readable string
     */
    private String formatTaskInfo(Reminder task) {
        String month = selectedTask.getDate().getMonth().toString();
        month = month.substring(0, 1).toUpperCase() + month.substring(1).toLowerCase();

        String details = "Name: " + selectedTask.getName() + "\n"
                + "Date: " + month + " "
                + selectedTask.getDate().getDayOfMonth() + ", "
                + selectedTask.getDate().getYear() + "\n"
                + "Time: " + selectedTask.getTime() + "\n" + "\n"
                + "Info: " + (selectedTask.getInfo().isEmpty() ? "No additional info" : selectedTask.getInfo());

        return details;
    }

    /**
     * effects:
     * - creates a JDialog popup to display the panel
     */
    private void showPopup() {
        JDialog dialog = new JDialog();
        dialog.setTitle("View Task (Info)");
        dialog.setModal(true);
        dialog.setContentPane(this);
        dialog.setSize(450, 250);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
