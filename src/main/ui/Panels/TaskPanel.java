package ui.panels;

import java.awt.*;
import javax.swing.*;

import model.CalendarList;
import ui.CalendarGUI;
import ui.panels.taskpanels.*;

/**
 * Represents the task panel for the task menu.
 * 
 * Fields:
 * - CalendarList calendarList
 * - CalendarGUI gui
 * - JButton addTaskToCalendarButton
 * - JButton removeTaskFromCalendarButton
 * - JButton viewTasksOfCalendarButton
 * - JButton viewSpecificTaskButton
 * - JButton editTaskButton
 * 
 * imports:
 * - awt and swing to format and display the UI panel
 * - CalendarList to access calendars
 * - CalendarGUI to switch between scenes
 * - taskpanels to navigate to their respective task operations
 */
public class TaskPanel extends JPanel {
    private CalendarList calendarList;
    private CalendarGUI gui;

    private JButton addTaskToCalendarButton;
    private JButton removeTaskFromCalendarButton;
    private JButton viewTasksOfCalendarButton;
    private JButton viewSpecificTaskButton;
    private JButton editTaskButton;

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - instantiates gui and calendarList
     * - sets the panel layout
     * - initializes all task panel buttons
     * - formats the components
     * - adds the components to the panel
     */
    public TaskPanel(CalendarGUI gui, CalendarList calendarList) {
        this.gui = gui;
        this.calendarList = calendarList;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        initTaskPanelButtons();
        formatComponents();
        addComponents();
    }

    /**
     * EFFECTS:
     * - creates and returns a button that, when pressed, opens the
     * AddTaskToCalendarPanel
     */
    private JButton addTaskToCalendarButton() {
        addTaskToCalendarButton = new JButton("Add Task");
        addTaskToCalendarButton.addActionListener(e -> new AddTaskToCalendarPanel(gui, calendarList));
        return addTaskToCalendarButton;
    }

    /**
     * EFFECTS:
     * - creates and returns a button that opens the RemoveTaskFromCalendarPanel
     */
    private JButton removeTaskFromCalendarButton() {
        removeTaskFromCalendarButton = new JButton("Remove Task");
        removeTaskFromCalendarButton.addActionListener(e -> new RemoveTaskFromCalendarPanel(gui, calendarList));
        return removeTaskFromCalendarButton;
    }

    /**
     * EFFECTS:
     * - creates and returns a button that displays all tasks of a selected calendar
     * in list form
     */
    private JButton viewTasksOfCalendarButton() {
        viewTasksOfCalendarButton = new JButton("View Tasks (List)");
        viewTasksOfCalendarButton.addActionListener(e -> new ViewTasksOfCalendarPanel(gui, calendarList));
        return viewTasksOfCalendarButton;
    }

    /**
     * EFFECTS:
     * - creates and returns a button that displays detailed information for a
     * specific task
     */
    private JButton viewSpecificTaskButton() {
        viewSpecificTaskButton = new JButton("View Task (Info)");
        viewSpecificTaskButton.addActionListener(e -> new ViewSpecificTaskPanel(gui, calendarList));
        return viewSpecificTaskButton;
    }

    /**
     * EFFECTS:
     * - creates and returns a button that opens the EditTaskPanel for modifying an
     * existing task
     */
    private JButton editTaskButton() {
        editTaskButton = new JButton("Edit Task");
        editTaskButton.addActionListener(e -> new EditTaskPanel(gui, calendarList));
        return editTaskButton;
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - initializes all task-related buttons
     * - assigns each button its corresponding action listener
     */
    public void initTaskPanelButtons() {
        addTaskToCalendarButton = addTaskToCalendarButton();
        removeTaskFromCalendarButton = removeTaskFromCalendarButton();
        viewTasksOfCalendarButton = viewTasksOfCalendarButton();
        viewSpecificTaskButton = viewSpecificTaskButton();
        editTaskButton = editTaskButton();
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - formats each button's appearance, including:
     * - alignment
     * - font style
     * - font size
     * - maximum dimensions
     */
    public void formatComponents() {
        addTaskToCalendarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addTaskToCalendarButton.setFont(new Font("Arial", Font.BOLD, 20));
        addTaskToCalendarButton.setMaximumSize(new Dimension(200, 50));

        removeTaskFromCalendarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeTaskFromCalendarButton.setFont(new Font("Arial", Font.BOLD, 20));
        removeTaskFromCalendarButton.setMaximumSize(new Dimension(200, 50));

        viewTasksOfCalendarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewTasksOfCalendarButton.setFont(new Font("Arial", Font.BOLD, 20));
        viewTasksOfCalendarButton.setMaximumSize(new Dimension(200, 50));

        viewSpecificTaskButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewSpecificTaskButton.setFont(new Font("Arial", Font.BOLD, 20));
        viewSpecificTaskButton.setMaximumSize(new Dimension(200, 50));

        editTaskButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        editTaskButton.setFont(new Font("Arial", Font.BOLD, 20));
        editTaskButton.setMaximumSize(new Dimension(200, 50));
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - adds spacing between components for cleaner presentation
     * - adds each button to the panel in vertical order
     * - centers the content using vertical glue
     */
    public void addComponents() {
        add(Box.createVerticalGlue());

        add(addTaskToCalendarButton);
        add(Box.createRigidArea(new Dimension(0, 20)));

        add(removeTaskFromCalendarButton);
        add(Box.createRigidArea(new Dimension(0, 20)));

        add(viewTasksOfCalendarButton);
        add(Box.createRigidArea(new Dimension(0, 20)));

        add(viewSpecificTaskButton);
        add(Box.createRigidArea(new Dimension(0, 20)));

        add(editTaskButton);
        add(Box.createRigidArea(new Dimension(0, 20)));

        add(Box.createVerticalGlue());
    }
}
