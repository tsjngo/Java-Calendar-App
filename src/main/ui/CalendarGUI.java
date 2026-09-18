package ui;

import java.awt.*;
import javax.swing.*;

import model.CalendarList;
import model.Event;
import model.EventLog;
import ui.panels.*;

/**
 * Represents Graphical UI for Calendar that can interact with Reminders
 * 
 * Fields:
 * - CalendarList calendarList
 * - JPanel panel
 * - CardLayout cardLayout
 * 
 * imports:
 * - awt, swing, and panels to create the GUI
 * - calendarlist to interact with the reminders and calendars
 */
public class CalendarGUI extends JFrame {
    private CalendarList calendarList;
    private JPanel panel;
    private CardLayout cardLayout;

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - instantiates a new CalendarList, calendarList
     * - instantiates a new Jpanel, panel
     * - instantiates a new CardLayout, cardLayout
     * - Sets size to 800 x 600
     * - sets window title to "Calendar App"
     * - sets close behaviour to exit
     * - sets window starting location to center of the screen
     * - add the main menu panel and displays it
     */
    public CalendarGUI() {
        calendarList = new CalendarList();

        cardLayout = new CardLayout();
        panel = new JPanel(cardLayout);

        panel.add(new MenuPanel(this, calendarList), "MENU");
        panel.add(new CalendarPanel(this, calendarList), "CALENDAR");
        panel.add(new TaskPanel(this, calendarList), "TASK");

        setTitle("Calendar App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        add(panel);

        setVisible(true);

        cardLayout.show(panel, "MENU");
    }

    /**
     * effects:
     * switches scene to the provided scene
     */
    public void showScene(String name) {
        cardLayout.show(panel, name);
    }

    /**
     * effects:
     * - overrides what happens when the panel closes
     */
    @Override
    public void dispose() {
        printLogs();
        super.dispose();
    }

    /**
     * Effects:
     * - prints out the event descriptions in the log
     */
    public void printLogs() {
        for (Event e : EventLog.getInstance()) {
            System.out.println(e.toString());
        }
    }

    /**
     * effects:
     * starts the gui app
     */
    public static void main(String[] args) {
        new CalendarGUI();
    }
}
