package ui.panels;

import java.awt.*;
import javax.swing.*;

import model.CalendarList;
import ui.CalendarGUI;
import ui.panels.calendarpanels.*;
import ui.panels.popup.*;

/**
 * Represents the calendar panel for the CalendarGUI where users can choose
 * options regarding calendars
 * 
 * Fields:
 * - CalendarList calendarList
 * - CalendarGUI gui
 * - 4 JButtons
 * 
 * imports:
 * - awt, swing, and panels to create the panel
 * - calendarlist and calendarGUI to interact with the reminders and calendars
 * - panels.popup to make it simpler and methods shorter
 */
public class CalendarPanel extends JPanel {
    private CalendarList calendarList;
    private CalendarGUI gui;

    private JButton addCalendarButton;
    private JButton removeCalendarButton;
    private JButton viewCalendarsButton;
    private JButton editCalendarNameButton;

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - instantiates a new CalendarList, calendarList
     * - instantiates a new CalendarGUI, gui
     * - sets layout to make the buttons go top to bottom instead of left to right
     * - adds option buttons
     * - Displays the options regarding calendars
     */
    public CalendarPanel(CalendarGUI gui, CalendarList calendarList) {
        this.gui = gui;
        this.calendarList = calendarList;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        initCalendarPanelButtons();
        formatComponents();
        addComponents();
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - creates a new button that adds a calendar
     */
    private JButton addCalendarButton() {
        addCalendarButton = new JButton("Add Calendar");
        addCalendarButton.addActionListener(e -> new AddCalendarPanel(gui, calendarList));
        return addCalendarButton;
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - creates a new button that removes a calendar
     */
    private JButton removeCalendarButton() {
        removeCalendarButton = new JButton("Remove Calendar");
        removeCalendarButton.addActionListener(e -> {
            if (calendarList.getCalendars().isEmpty()) {
                new CannotPanel("No calendars have been made.");
                gui.showScene("MENU");
                return;
            } else {
                new RemoveCalendarPanel(gui, calendarList);
            }
        });
        return removeCalendarButton;
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - creates a new button that shows all the calendars
     */
    private JButton viewCalendarsButton() {
        viewCalendarsButton = new JButton("View Calendars");
        viewCalendarsButton.addActionListener(e -> {
            if (calendarList.getCalendars().isEmpty()) {
                new CannotPanel("No calendars have been made.");
                gui.showScene("MENU");
                return;
            } else {
                new ViewCalendarsPanel(gui, calendarList);
            }
        });
        return viewCalendarsButton;
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - creates a new button that edits a calendar's name
     */
    private JButton editCalendarNameButton() {
        editCalendarNameButton = new JButton("Edit Name");
        editCalendarNameButton.addActionListener(e -> {
            if (calendarList.getCalendars().isEmpty()) {
                new CannotPanel("No calendars have been made.");
                gui.showScene("MENU");
                return;
            } else {
                new EditCalendarNamePanel(gui, calendarList);
            }
        });
        return editCalendarNameButton;
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - initiates the panel's buttons
     */
    public void initCalendarPanelButtons() {
        addCalendarButton = addCalendarButton();
        removeCalendarButton = removeCalendarButton();
        viewCalendarsButton = viewCalendarsButton();
        editCalendarNameButton = editCalendarNameButton();
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - formats the buttons to desire (location, font, bold, size, and dimensions)
     */
    public void formatComponents() {
        addCalendarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addCalendarButton.setFont(new Font("Arial", Font.BOLD, 20));
        addCalendarButton.setMaximumSize(new Dimension(200, 50));

        removeCalendarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeCalendarButton.setFont(new Font("Arial", Font.BOLD, 20));
        removeCalendarButton.setMaximumSize(new Dimension(200, 50));

        viewCalendarsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewCalendarsButton.setFont(new Font("Arial", Font.BOLD, 20));
        viewCalendarsButton.setMaximumSize(new Dimension(200, 50));

        editCalendarNameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        editCalendarNameButton.setFont(new Font("Arial", Font.BOLD, 20));
        editCalendarNameButton.setMaximumSize(new Dimension(200, 50));
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - creates spacing between the buttons and add thems
     */
    public void addComponents() {
        add(Box.createVerticalGlue());

        add(addCalendarButton);
        add(Box.createRigidArea(new Dimension(0, 20)));

        add(removeCalendarButton);
        add(Box.createRigidArea(new Dimension(0, 20)));

        add(viewCalendarsButton);
        add(Box.createRigidArea(new Dimension(0, 20)));

        add(editCalendarNameButton);
        add(Box.createRigidArea(new Dimension(0, 20)));

        add(Box.createVerticalGlue());
    }
}
