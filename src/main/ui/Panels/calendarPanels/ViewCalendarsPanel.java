package ui.panels.calendarpanels;

import javax.swing.*;
import java.awt.*;

import model.Calendar;
import model.CalendarList;
import ui.CalendarGUI;

/**
 * Represents the view calendar panel for calendar panel
 * 
 * Fields:
 * - CalendarList calendarList
 * - JComboBox of type Calendar selectCalendarnameBox
 * - JLabel viewCalendars
 * - JTextArea listOfCalendars
 * - JScrollPane scrollPane
 * - JButton doneButton
 * 
 * imports:
 * - awt, swing to create the panel
 * - calendarlist to remove calendars
 * - calendarGUI to switch scenes
 */
public class ViewCalendarsPanel extends JPanel {
    private CalendarList calendarList;
    private CalendarGUI gui;
    private JLabel viewCalendars;
    private JTextArea listOfCalendars;
    private JScrollPane scrollPane;
    private JButton doneButton;

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - instantiates a CalendarList, calendarList
     * - instantiates a CalendarGUI, gui
     * - instantiates the buttons
     * - formats the buttons
     * - adds buttons
     * - Displays the list popup
     * - goes back to the main menu when done
     */
    public ViewCalendarsPanel(CalendarGUI gui, CalendarList calendarList) {
        this.calendarList = calendarList;
        this.gui = gui;

        initViewCalendarsPanelComponents();
        showPopup();
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - initiates the panel's buttons
     */
    private void initViewCalendarsPanelComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        viewCalendars = new JLabel("Calendars:");
        viewCalendars.setHorizontalAlignment(SwingConstants.CENTER);
        add(viewCalendars, BorderLayout.NORTH);

        listOfCalendars = new JTextArea();
        listOfCalendars.setEditable(false);
        listOfCalendars.setLineWrap(true);
        listOfCalendars.setWrapStyleWord(true);
        updateCalendarList();

        scrollPane = new JScrollPane(listOfCalendars);
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
     * - updates the list of calendars
     */
    private void updateCalendarList() {
        String text = calendarList.getCalendars()
                .stream()
                .map(Calendar::getName)
                .reduce("", (a, b) -> a + b + "\n");
        listOfCalendars.setText(text);
    }

   /**
     * effects:
     * - creates a JOptionPane inside of a popup so that we can
     * get rid of the OK button that comes with JDialog
     */
    private void showPopup() {
        JDialog dialog = new JDialog();
        dialog.setTitle("View Calendars");
        dialog.setModal(true);
        dialog.setContentPane(this);
        dialog.setSize(450, 250);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}