package ui.panels.calendarpanels;

import javax.swing.*;
import java.awt.*;

import model.Calendar;
import model.CalendarList;
import ui.CalendarGUI;
import ui.panels.popup.ErrorPanel;
import ui.panels.popup.SuccessPanel;

/**
 * Represents the remove calendar panel for calendar panel
 * 
 * Fields:
 * - CalendarList calendarList
 * - JComboBox of type Calendar selectCalendarnameBox
 * - JLabel calendarRemovePrompt
 * - JButton removeButton
 * - CalendarGUI gui
 * 
 * imports:
 * - awt, swing to create the panel
 * - calendarlist to add calendars
 * - calendarGUI to switch scenes
 * - panels.popup to make it simpler and methods shorter
 */
public class RemoveCalendarPanel extends JPanel {
    private CalendarList calendarList;
    private JComboBox<Calendar> selectCalendarNameBox;
    private JLabel calendarRemovePrompt;
    private JButton removeButton;
    private CalendarGUI gui;

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
     * - Displays the prompt popup
     * - goes back to the main menu when done
     */
    public RemoveCalendarPanel(CalendarGUI gui, CalendarList calendarList) {
        this.calendarList = calendarList;
        this.gui = gui;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setPreferredSize(new Dimension(400, 200));

        initRemoveCalendarPanelComponents();
        formatComponents();
        addComponents();

        showPopup();
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - initiates the panel's buttons
     */
    public void initRemoveCalendarPanelComponents() {
        selectCalendarNameBox = selectCalendarNameBox();
        calendarRemovePrompt = new JLabel("Select a calendar to remove.");
        removeButton = removeButton();
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - formats the buttons to desire (location, and dimensions)
     */
    public void formatComponents() {
        calendarRemovePrompt.setAlignmentX(CENTER_ALIGNMENT);

        selectCalendarNameBox.setMaximumSize(new Dimension(200, 30));
        selectCalendarNameBox.setAlignmentX(CENTER_ALIGNMENT);

        removeButton.setAlignmentX(CENTER_ALIGNMENT);
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - creates spacing between the buttons and adds them
     */
    public void addComponents() {
        add(calendarRemovePrompt);
        add(Box.createRigidArea(new Dimension(0, 30)));

        add(selectCalendarNameBox);
        add(Box.createRigidArea(new Dimension(0, 30)));

        add(removeButton);
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - creates a new button that removes the calendar chosen by the user
     */
    public JButton removeButton() {
        removeButton = new JButton("Remove Calendar");
        removeButton.addActionListener(e -> {
            int index = selectCalendarNameBox.getSelectedIndex();
            if (index >= 0 && index <= calendarList.getCalendars().size() - 1) {
                calendarList.removeCalendar(index);
                new SuccessPanel("Calendar removed!");
            } else {
                new ErrorPanel("Could not remove calendar.");
            }
            gui.showScene("MENU");

            SwingUtilities.getWindowAncestor(this).dispose();
        });
        return removeButton;
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - creates a ComboBox of calendars for the user to choose from
     */
    public JComboBox<Calendar> selectCalendarNameBox() {
        selectCalendarNameBox = new JComboBox<>();
        for (Calendar calendar : calendarList.getCalendars()) {
            selectCalendarNameBox.addItem(calendar);
        }
        return selectCalendarNameBox;
    }

    /**
     * effects:
     * - creates a JOptionPane inside of a popup so that we can
     * get rid of the OK button that comes with JDialog
     */
    private void showPopup() {
        JOptionPane pane = new JOptionPane(
                this,
                JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null,
                new Object[] {},
                null);

        JDialog dialog = pane.createDialog("Remove a Calendar");
        dialog.setModal(true);
        dialog.setVisible(true);
    }
}