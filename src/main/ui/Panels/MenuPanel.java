package ui.panels;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;

import org.json.JSONException;

import model.CalendarList;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.CalendarGUI;
import ui.panels.popup.*;

/**
 * Represents the main menu panel for the CalendarGUI
 * 
 * Fields:
 * - CalendarList calendarList
 * - CalendarGUI gui
 * - 5 JButtons
 * 
 * imports:
 * - awt, swing, and panels to create the panel
 * - calendarlist and calendarGUI to interact with the reminders and calendars
 * - panels.popup to make it simpler and methods shorter
 */
public class MenuPanel extends JPanel {
    private CalendarList calendarList;
    private CalendarGUI gui;

    private JButton calendarButton;
    private JButton taskButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton exitButton;

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - instantiates a new CalendarList, calendarList
     * - instantiates a new CalendarGUI, gui
     * - sets layout to make the buttons go top to bottom instead of left to right
     * - adds option buttons
     * - Displays the main menu
     */
    public MenuPanel(CalendarGUI gui, CalendarList calendarList) {
        this.gui = gui;
        this.calendarList = calendarList;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        initMenuPanelButtons();
        formatComponents();
        addComponents();
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - creates a new button that switches to CalendarPanel
     */
    private JButton calendarPanelButton() {
        calendarButton = new JButton("Calendar Options");
        calendarButton.addActionListener(e -> gui.showScene("CALENDAR"));
        return calendarButton;
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - creates a new button that switches to TaskPanel
     */
    private JButton taskPanelButton() {
        taskButton = new JButton("Task Options");
        taskButton.addActionListener(e -> {
            if (calendarList.getCalendars().isEmpty()) {
                new CannotPanel("No calendars have been made.");
                return;
            } else {
                gui.showScene("TASK");
            }
        });
        return taskButton;
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - creates a new button that saves the Calendars and Reminders
     */
    private JButton saveButton() {
        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> save());
        return saveButton;
    }

    /**
     * MODIFIES:
     * - save.json file
     * 
     * EFFECTS:
     * - writes calendarList to save.json
     */
    public void save() {
        try {
            JsonWriter writer = new JsonWriter("./data/save.json");
            writer.open();
            writer.write(calendarList);
            writer.close();
            new SuccessPanel("Successfully Saved!");
        } catch (FileNotFoundException e) {
            new ErrorPanel("Could not save successfully.");
        }
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - loads the saved calendars and reminders
     */
    private JButton loadButton() {
        loadButton = new JButton("Load");
        loadButton.addActionListener(e -> load());
        return loadButton;
    }

    /**
     * MODIFIES:
     * - this
     * 
     * EFFECTS:
     * - parses save.json and adds that data into calendarList
     */
    public void load() {
        try {
            JsonReader reader = new JsonReader("./data/save.json");
            CalendarList loaded = reader.read();
            calendarList.getCalendars().clear();
            calendarList.getCalendars().addAll(loaded.getCalendars());
            new SuccessPanel("Successfully Loaded!");
        } catch (IOException | JSONException e) {
            new ErrorPanel("Could not load successfully.");
        }
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - creates a new button that exits the GUI
     */
    private JButton exitButton() {
        exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> gui.dispose());
        return exitButton;
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - initiates the panel's buttons
     */
    public void initMenuPanelButtons() {
        calendarButton = calendarPanelButton();
        taskButton = taskPanelButton();
        saveButton = saveButton();
        loadButton = loadButton();
        exitButton = exitButton();
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - formats the buttons to desire (location, font, bold, size, and dimensions)
     */
    public void formatComponents() {
        calendarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        calendarButton.setFont(new Font("Arial", Font.BOLD, 20));
        calendarButton.setMaximumSize(new Dimension(200, 50));

        taskButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        taskButton.setFont(new Font("Arial", Font.BOLD, 20));
        taskButton.setMaximumSize(new Dimension(200, 50));

        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setFont(new Font("Arial", Font.BOLD, 20));
        saveButton.setMaximumSize(new Dimension(200, 50));

        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadButton.setFont(new Font("Arial", Font.BOLD, 20));
        loadButton.setMaximumSize(new Dimension(200, 50));

        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setFont(new Font("Arial", Font.BOLD, 20));
        exitButton.setMaximumSize(new Dimension(200, 50));
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - creates spacing between the buttons and adds them
     */
    public void addComponents() {
        add(Box.createVerticalGlue());

        add(calendarButton);
        add(Box.createRigidArea(new Dimension(0, 20)));

        add(taskButton);
        add(Box.createRigidArea(new Dimension(0, 20)));

        add(saveButton);
        add(Box.createRigidArea(new Dimension(0, 20)));

        add(loadButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(exitButton);

        add(Box.createVerticalGlue());
    }
}
