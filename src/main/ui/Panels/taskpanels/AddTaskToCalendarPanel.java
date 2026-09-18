package ui.panels.taskpanels;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

import model.Calendar;
import model.CalendarList;
import model.Reminder;
import ui.CalendarGUI;
import ui.panels.helpers.*;
import ui.panels.popup.*;

/**
 * Represents the add task panel for the task panel.
 * 
 * Fields:
 * - CalendarGUI gui
 * - CalendarList calendarList
 * - Calendar selectedCalendar
 * - LocalDate date
 * - LocalTime time
 * - String taskName
 * - String taskInfo
 * - boolean wantsInfo
 * - boolean noErrors
 * 
 * imports:
 * - awt and swing to create and display the panel
 * - calendar, calendarlist, and reminder to interact with backend data
 * - panels.popup and panels.helpers to reuse popup prompts
 */
public class AddTaskToCalendarPanel extends JPanel {
    private CalendarGUI gui;
    private CalendarList calendarList;
    private Calendar selectedCalendar;
    private LocalDate date;
    private LocalTime time;
    private String taskName;
    private String taskInfo;
    private boolean wantsInfo;
    private boolean noErrors;

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - instantiates gui and calendarList
     * - sets the panel layout and size
     * - prompts the user for all required information
     * - attempts to add a new task to the selected calendar
     * - returns back to the main menu afterwards
     */
    public AddTaskToCalendarPanel(CalendarGUI gui, CalendarList calendarList) {
        this.gui = gui;
        this.calendarList = calendarList;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setPreferredSize(new Dimension(400, 200));

        promptInformation();
        addTaskToCalendar();
    }

    /**
     * modifies:
     * - this
     * 
     * effects:
     * - sequentially prompts the user for all task information
     * - stops prompting early if an error occurs
     */
    public void promptInformation() {
        if (!selectCalendar()) {
            return;
        }
        if (!promptTaskName()) {
            return;
        }
        if (!promptTaskDate()) {
            return;
        }
        if (!promptTaskTime()) {
            return;
        }
        promptTaskInfo();
        noErrors = true;
    }

    /**
     * modifies:
     * - this
     * 
     * effects:
     * - opens a popup asking the user which calendar they want to add the task to
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
        return true;
    }

    /**
     * modifies:
     * - this
     * 
     * effects:
     * - prompts the user to enter the task name
     * - if the name is empty, sets noErrors to false and displays a popup
     * - returns true if a valid name was entered, false otherwise
     */
    private boolean promptTaskName() {
        taskName = new PromptTaskName().getTaskName();
        if (taskName == null || taskName.trim().isEmpty()) {
            taskName = null;
            noErrors = false;
            new CannotPanel("Name cannot be empty.");
            return false;
        }
        return true;
    }

    /**
     * modifies:
     * - this
     * 
     * effects:
     * - prompts the user for the task's date
     * - checks if the date exists and is not before today
     * - if invalid, sets noErrors to false and displays a popup
     * - returns true if a valid date was entered, false otherwise
     */
    private boolean promptTaskDate() {
        date = new PromptTaskDate().getSelectedDate();
        if (date == null) {
            noErrors = false;
            new ErrorPanel("Date is invalid.");
            return false;
        }

        if (date.isBefore(LocalDate.now())) {
            noErrors = false;
            new CannotPanel("Date must be in the future.");
            return false;
        }

        return true;
    }

    /**
     * modifies:
     * - this
     * 
     * effects:
     * - prompts the user for the task's time
     * - checks if the time exists and is not in the past when the date is today
     * - if invalid, sets noErrors to false and displays a popup
     * - returns true if a valid time was entered, false otherwise
     */
    private boolean promptTaskTime() {
        time = new PromptTaskTime().getSelectedTime();
        if (time == null) {
            noErrors = false;
            new ErrorPanel("Time is invalid.");
            return false;
        }

        if (date.equals(LocalDate.now())) {
            if (time.isBefore(LocalTime.now())) {
                noErrors = false;
                new CannotPanel("Time must be in the future.");
                return false;
            }
        }

        return true;
    }

    /**
     * modifies:
     * - this
     * 
     * effects:
     * - prompts the user if they want to add additional task information
     * - if yes, prompts for the info and stores it
     * - if no, sets taskInfo to an empty string
     */
    private void promptTaskInfo() {
        wantsInfo = new PromptTaskWantsInfo().getWantsInfo();
        if (wantsInfo) {
            taskInfo = new PromptTaskInfo().getTaskInfo();
            if (taskInfo == null || taskInfo.trim().isEmpty()) {
                taskInfo = "";
            } else {
                taskInfo = taskInfo.trim();
            }
        } else {
            taskInfo = "";
        }
    }

    /**
     * modifies:
     * - this
     * - selectedCalendar
     * 
     * effects:
     * - if noErrors is true, creates a new Reminder and adds it to the calendar
     * - displays a success popup
     * - otherwise, displays a failure popup
     * - always returns back to the main menu afterwards
     */
    public void addTaskToCalendar() {
        if (noErrors) {
            selectedCalendar.addTask(new Reminder(date, taskName, taskInfo, time));
            new SuccessPanel(taskName + " successfully added to " + selectedCalendar.getName() + "!");
        } else {
            String calendarName = (selectedCalendar != null ? selectedCalendar.getName() : "any calendar");
            new CannotPanel((taskName != null ? taskName : "Task") + " was not added to " + calendarName + ".");
        }
        gui.showScene("MENU");
    }
}
