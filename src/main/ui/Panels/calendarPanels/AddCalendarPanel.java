package ui.panels.calendarpanels;

import javax.swing.*;
import java.awt.*;
import model.Calendar;
import model.CalendarList;
import ui.panels.popup.SuccessPanel;
import ui.CalendarGUI;
import ui.panels.popup.CannotPanel;

/**
 * Represents the add calendar panel for calendar panel
 * 
 * Fields:
 * - CalendarList calendarList
 * - JTextField name
 * - JLabel calendarNamePrompt
 * - JButton submitButton
 * - CalendarGUI gui
 * 
 * imports:
 * - awt, and swing to create the panel
 * - calendarlist to add calendars
 * - panels.popup to make it simpler and methods shorter
 */
public class AddCalendarPanel extends JPanel {
    private JTextField name;
    private JLabel calendarNamePrompt;
    private JButton submitButton;
    private CalendarList calendarList;
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
    public AddCalendarPanel(CalendarGUI gui, CalendarList calendarList) {
        this.calendarList = calendarList;
        this.gui = gui;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setPreferredSize(new Dimension(400, 200));

        initAddCalendarPanelComponents();
        formatComponents();
        addComponents();

        showPopup();
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - creates a new button that confirms the name entered by user
     */
    public JButton submitButton() {
        submitButton = new JButton("Add New Calendar");
        submitButton.addActionListener(e -> {
            String formattedName = name.getText().trim();

            if (formattedName.isEmpty()) {
                new CannotPanel("Calendar name cannot be empty.");
                gui.showScene("MENU");
            } else {
                calendarList.addCalendar(new Calendar(formattedName));
                new SuccessPanel("Calendar added!");
                gui.showScene("MENU");
            }

            SwingUtilities.getWindowAncestor(this).dispose();
        });
        return submitButton;
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - initiates the panel's buttons
     */
    public void initAddCalendarPanelComponents() {
        calendarNamePrompt = new JLabel("What would you like to name your calendar?");
        name = new JTextField(20);
        submitButton = submitButton();
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - formats the buttons to desire (location, size, and dimensions)
     */
    public void formatComponents() {
        calendarNamePrompt.setAlignmentX(CENTER_ALIGNMENT);

        name.setMaximumSize(new Dimension(300, 30));
        name.setAlignmentX(CENTER_ALIGNMENT);

        submitButton.setAlignmentX(CENTER_ALIGNMENT);
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - creates spacing between the components and adds them
     */
    public void addComponents() {
        add(calendarNamePrompt);
        add(Box.createRigidArea(new Dimension(0, 30)));

        add(name);
        add(Box.createRigidArea(new Dimension(0, 30)));

        add(submitButton);
    }

    /**
     * effects:
     * - creates a JOptionPane inside of a popup so that we can
     *   get rid of the OK button that comes with JDialog
     */
    private void showPopup() {
        JOptionPane pane = new JOptionPane(
                this,
                JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null,
                new Object[] {},
                null);

        JDialog dialog = pane.createDialog("Create New Calendar");
        dialog.setModal(true);
        dialog.setVisible(true);
    }
}