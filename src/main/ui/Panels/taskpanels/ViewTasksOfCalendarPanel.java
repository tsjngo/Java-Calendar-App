package ui.panels.taskpanels;

import java.awt.*;
import javax.swing.*;

import model.CalendarList;
import model.Reminder;
import model.Calendar;
import ui.CalendarGUI;
import ui.panels.helpers.*;
import ui.panels.popup.CannotPanel;

/**
 * Represents the view tasks (list) panel.
 * 
 * Fields:
 * - CalendarGUI gui
 * - CalendarList calendarList
 * - Calendar selectedCalendar
 * - JLabel viewTasks
 * - JTextArea listOfTasks
 * - JScrollPane scrollPane
 * - JButton doneButton
 */
public class ViewTasksOfCalendarPanel extends JPanel {
    private CalendarGUI gui;
    private Calendar selectedCalendar;
    private JLabel viewTasks;
    private JTextArea listOfTasks;
    private JScrollPane scrollPane;
    private JButton doneButton;

    /**
     * modifies:
     * - this
     *
     * EFFECTS:
     * - prompts user for a calendar
     * - checks if the calendar has any tasks
     * - if invalid or empty, shows popup and exits immediately
     * - otherwise builds UI and displays the popup window
     */
    public ViewTasksOfCalendarPanel(CalendarGUI gui, CalendarList calendarList) {
        this.gui = gui;

        selectedCalendar = new CalendarSelectPanel(calendarList).getSelectedCalendar();

        if (selectedCalendar == null) {
            new CannotPanel("Calendar is invalid.");
            gui.showScene("MENU");
            return;
        }

        if (selectedCalendar.getTasks().isEmpty()) {
            new CannotPanel("No tasks have been added to the calendar.");
            gui.showScene("MENU");
            return;
        }

        initComponents();

        showPopup();
    }

    /**
     * modifies:
     * - this
     *
     * EFFECTS:
     * - builds UI components since validation has passed
     */
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        viewTasks = new JLabel("Tasks:");
        viewTasks.setHorizontalAlignment(SwingConstants.CENTER);
        add(viewTasks, BorderLayout.NORTH);

        listOfTasks = new JTextArea();
        listOfTasks.setEditable(false);
        listOfTasks.setLineWrap(true);
        listOfTasks.setWrapStyleWord(true);
        updateTasksList();

        scrollPane = new JScrollPane(listOfTasks);
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
     * - this
     *
     * EFFECTS:
     * - updates listOfTasks with all task names
     */
    private void updateTasksList() {
        String text = selectedCalendar.getTasks()
                .stream()
                .map(Reminder::getName)
                .reduce("", (a, b) -> a + b + "\n");

        listOfTasks.setText(text);
    }

    /**
     * EFFECTS:
     * - creates a modal popup containing this panel
     * - same behavior as AddCalendarPanel.showPopup()
     */
    private void showPopup() {
        JOptionPane pane = new JOptionPane(
                this,
                JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null,
                new Object[] {},
                null);

        JDialog dialog = pane.createDialog("View Tasks (List)");
        dialog.setModal(true);
        dialog.setVisible(true);
    }
}
