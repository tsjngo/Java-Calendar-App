package ui.panels.taskpanels;

import java.awt.*;
import javax.swing.*;

import model.Calendar;
import model.CalendarList;
import model.Reminder;
import ui.CalendarGUI;
import ui.panels.helpers.CalendarSelectPanel;
import ui.panels.helpers.TaskSelectPanel;
import ui.panels.popup.CannotPanel;
import ui.panels.popup.SuccessPanel;

/**
 * Represents the remove task panel for the task panel.
 *
 * Fields:
 * - CalendarGUI gui
 * - CalendarList calendarList
 * - Calendar selectedCalendar
 * - Reminder selectedTask
 * - boolean noErrors
 *
 * imports:
 * - awt and swing to create and display the panel
 * - calendar, calendarlist, and reminder to interact with backend data
 * - panels.popup and panels.helpers to reuse popup prompts
 */
public class RemoveTaskFromCalendarPanel extends JPanel {
    private CalendarGUI gui;
    private CalendarList calendarList;
    private Calendar selectedCalendar;
    private Reminder selectedTask;
    private boolean noErrors;

    /**
     * MODIFIES:
     * - this
     *
     * EFFECTS:
     * - instantiates gui and calendarList
     * - sets the panel layout and size
     * - prompts the user for all required information
     * - attempts to remove a task from the selected calendar
     * - returns back to the main menu afterwards
     */
    public RemoveTaskFromCalendarPanel(CalendarGUI gui, CalendarList calendarList) {
        this.gui = gui;
        this.calendarList = calendarList;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setPreferredSize(new Dimension(400, 200));

        promptInformation();
        removeTaskFromCalendar();
    }

    /**
     * MODIFIES:
     * - this
     *
     * EFFECTS:
     * - sequentially prompts the user for a calendar and task to remove
     * - stops prompting early if an error occurs
     */
    public void promptInformation() {
        if (!selectCalendar()) {
            return;
        }
        if (!selectTask()) {
            return;
        }

        noErrors = true;
    }

    /**
     * MODIFIES:
     * - this
     *
     * EFFECTS:
     * - opens a popup asking the user which calendar they want to remove a task
     * from
     * - if no calendar is selected, sets noErrors to false and displays a popup
     * - returns true if a calendar was selected, false otherwise
     */
    private boolean selectCalendar() {
        selectedCalendar = new CalendarSelectPanel(calendarList).getSelectedCalendar();

        if (selectedCalendar == null) {
            noErrors = false;
            new CannotPanel("Calendar is invalid.");
            return false;
        }

        if (selectedCalendar.getTasks().isEmpty()) {
            noErrors = false;
            new CannotPanel("No tasks have been added to the calendar.");
            return false;
        }

        return true;
    }

    /**
     * MODIFIES:
     * - this
     *
     * EFFECTS:
     * - prompts the user to select a task from the chosen calendar
     * - if invalid or none selected, sets noErrors to false and displays a popup
     * - returns true if a valid task was selected, false otherwise
     */
    private boolean selectTask() {
        selectedTask = new TaskSelectPanel(selectedCalendar).getSelectedTask();

        if (selectedTask == null) {
            noErrors = false;
            new CannotPanel("Task is invalid.");
            return false;
        }

        return true;
    }

    /**
     * MODIFIES:
     * - this
     * - selectedCalendar
     *
     * EFFECTS:
     * - if noErrors is true, removes the selected task from the calendar
     * - displays a success popup
     * - otherwise, displays a failure popup
     * - always returns back to the main menu afterwards
     */
    public void removeTaskFromCalendar() {
        if (noErrors) {
            selectedCalendar.removeTask(selectedTask);
            new SuccessPanel(selectedTask.getName()
                    + " successfully removed from "
                    + selectedCalendar.getName() + "!");
        } else {
            String calendarName = (selectedCalendar != null
                    ? selectedCalendar.getName()
                    : "any calendar");

            String taskName = (selectedTask != null
                    ? selectedTask.getName()
                    : "Task");

            new CannotPanel(taskName + " was not removed from " + calendarName + ".");
        }

        gui.showScene("MENU");
    }
}
