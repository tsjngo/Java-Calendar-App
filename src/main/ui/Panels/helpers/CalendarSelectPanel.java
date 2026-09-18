package ui.panels.helpers;

import javax.swing.*;
import java.awt.*;
import model.Calendar;
import model.CalendarList;

/**
 * Represents a panel that allows the user to select a calendar from a list.
 * 
 * Fields:
 * - selectedCalendar: the calendar chosen by the user
 * - calendarBox: the JComboBox containing available calendars
 * - calendarList: the CalendarList used to populate the combo box
 * - prompt: the label prompting the user
 * - selectButton: the button used to confirm selection
 * 
 * Imports:
 * - javax.swing.* for GUI components
 * - java.awt.* for layout and dimensions
 * - model.Calendar and model.CalendarList to provide calendars for selection
 */
public class CalendarSelectPanel extends JPanel {
    private Calendar selectedCalendar;
    private JComboBox<Calendar> calendarBox;
    private CalendarList calendarList;
    private JLabel prompt;
    private JButton selectButton;

    /**
     * REQUIRES:
     * - calendarList != null
     * 
     * MODIFIES:
     * - this
     * 
     * EFFECTS:
     * - sets layout and border
     * - initializes components
     * - adds components to the panel
     * - displays the selection popup for the user
     */
    public CalendarSelectPanel(CalendarList calendarList) {
        this.calendarList = calendarList;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        initComponents();
        addComponents();
        showPopup();
    }

    /**
     * MODIFIES:
     * - this
     * 
     * EFFECTS:
     * - creates the label, combo box of calendars, and select button
     * - adds action listener to select button to save the chosen calendar and close
     * popup
     */
    public void initComponents() {
        prompt = new JLabel("Select a calendar:");
        prompt.setAlignmentX(CENTER_ALIGNMENT);

        calendarBox = new JComboBox<>();
        for (Calendar c : calendarList.getCalendars()) {
            calendarBox.addItem(c);
        }
        calendarBox.setMaximumSize(new Dimension(200, 30));
        calendarBox.setAlignmentX(CENTER_ALIGNMENT);

        selectButton = new JButton("Select");
        selectButton.setAlignmentX(CENTER_ALIGNMENT);
        selectButton.addActionListener(e -> {
            selectedCalendar = (Calendar) calendarBox.getSelectedItem();
            SwingUtilities.getWindowAncestor(this).dispose();
        });
    }

    /**
     * MODIFIES:
     * - this
     * 
     * EFFECTS:
     * - adds the label, combo box, and button to the panel with spacing
     */
    public void addComponents() {
        add(prompt);
        add(Box.createVerticalStrut(20));
        add(calendarBox);
        add(Box.createVerticalStrut(20));
        add(selectButton);
    }

    /**
     * MODIFIES:
     * - creates a JDialog
     * 
     * EFFECTS:
     * - displays the panel inside a modal popup so the user can select a calendar
     */
    public void showPopup() {
        JOptionPane pane = new JOptionPane(
                this,
                JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null,
                new Object[] {},
                null);
        JDialog dialog = pane.createDialog("Select Calendar");
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    /**
     * EFFECTS:
     * - returns the calendar selected by the user
     * - returns null if the user closes the popup without selecting
     */
    public Calendar getSelectedCalendar() {
        return selectedCalendar;
    }
}
