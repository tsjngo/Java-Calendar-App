package ui.panels.taskpanels;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalTime;

import model.Calendar;
import model.CalendarList;
import model.Reminder;
import ui.CalendarGUI;
import ui.panels.helpers.*;
import ui.panels.popup.CannotPanel;
import ui.panels.popup.SuccessPanel;

public class EditTaskPanel extends JPanel {
    private CalendarGUI gui;
    private CalendarList calendarList;
    private Calendar selectedCalendar;
    private Reminder selectedTask;
    private String selectedField;

    /**
     * MODIFIES:
     * - this
     *
     * EFFECTS:
     * - prompts the user to select a calendar, task, and field to edit
     * - calls the appropriate helper panel to edit the selected field
     * - updates the task and shows a success popup
     */
    public EditTaskPanel(CalendarGUI gui, CalendarList calendarList) {
        this.gui = gui;
        this.calendarList = calendarList;

        promptInformation();
        editSelectedField();
    }

    /**
     * MODIFIES:
     * - this
     *
     * EFFECTS:
     * - prompts the user to select a calendar and task
     * - displays error popups if selection fails
     */
    private void promptInformation() {
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
            return;
        }

        selectedField = new PromptTaskField().getSelectedField();
        if (selectedField == null) {
            new CannotPanel("No field selected.");
        }
    }

    /**
     * MODIFIES:
     * - selectedTask
     *
     * EFFECTS:
     * - calls the appropriate helper method to edit the selected field
     */
    private void editSelectedField() {
        if (selectedCalendar == null || selectedTask == null || selectedField == null) {
            return;
        }

        switch (selectedField) {
            case "Name":
                editName();
                break;

            case "Date":
                editDate();
                break;

            case "Time":
                editTime();
                break;

            case "Info":
                editInfo();
                break;

            default:
                new CannotPanel("Unknown field.");
                break;
        }

        gui.showScene("MENU");
    }

    /**
     * MODIFIES:
     * - selectedTask
     *
     * EFFECTS:
     * - prompts for a new task name and updates it
     */
    private void editName() {
        String newName = new PromptTaskName().getTaskName();
        if (newName == null || newName.trim().isEmpty()) {
            new CannotPanel("Task name cannot be empty.");
            return;
        }
        selectedTask.setName(newName.trim());
        new SuccessPanel("Task name updated!");
    }

    /**
     * MODIFIES:
     * - selectedTask
     *
     * EFFECTS:
     * - prompts for a new task date and updates it
     */
    private void editDate() {
        LocalDate newDate = new PromptTaskDate().getSelectedDate();
        if (newDate == null) {
            new CannotPanel("Invalid date.");
            return;
        }
        selectedTask.setDate(newDate);
        new SuccessPanel("Task date updated!");
    }

    /**
     * MODIFIES:
     * - selectedTask
     *
     * EFFECTS:
     * - prompts for a new task time and updates it
     */
    private void editTime() {
        LocalTime newTime = new PromptTaskTime().getSelectedTime();
        if (newTime == null) {
            new CannotPanel("Invalid time.");
            return;
        }
        selectedTask.setTime(newTime);
        new SuccessPanel("Task time updated!");
    }

    /**
     * MODIFIES:
     * - selectedTask
     *
     * EFFECTS:
     * - prompts for new task info and updates it
     */
    private void editInfo() {
        String newInfo = new PromptTaskInfo().getTaskInfo();
        if (newInfo == null) {
            newInfo = "";
        }
        selectedTask.setInfo(newInfo.trim());
        new SuccessPanel("Task info updated!");
    }
}
