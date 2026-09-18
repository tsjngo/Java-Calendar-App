package ui.panels.calendarpanels;

import java.awt.*;
import javax.swing.*;

import model.Calendar;
import model.CalendarList;
import ui.CalendarGUI;
import ui.panels.popup.CannotPanel;
import ui.panels.popup.ErrorPanel;
import ui.panels.popup.SuccessPanel;

/**
 * Represents the add calendar panel for calendar panel
 * 
 * Fields:
 * - CalendarGUI gui
 * - CalendarList calendarList
 * - JPanel selectPanel
 * - JPanel editPanel
 * 
 * - JComboBox of type Calendar selectNameBox
 * - JButton calendarSelectButton
 * - JLabel calendarSelectPrompt
 * - int calendarSelectIndex
 * - JButton calendarEditButton
 * - JTextField newName
 * - JLabel calendarEditPrompt
 * 
 * imports:
 * - awt, and swing to create the panels
 * - calendarlist to edit calendars
 * - panels.popup to make it simpler and methods shorter
 */
public class EditCalendarNamePanel extends JPanel {
    private CalendarGUI gui;
    private CalendarList calendarList;
    private JPanel selectPanel;
    private JPanel editPanel;

    private JComboBox<Calendar> selectNameBox;
    private JButton calendarSelectButton;
    private JLabel calendarSelectPrompt;
    private int calendarSelectIndex;

    private JButton calendarEditButton;
    private JTextField newName;
    private JLabel calendarEditPrompt;


    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - instantiates a CalendarList, calendarList
     * - instantiates a CalendarGUI, gui
     * - sets the layout of the window
     * - displays the popup for user to select calendar
     */
    public EditCalendarNamePanel(CalendarGUI gui, CalendarList calendarList) {
        this.gui = gui;
        this.calendarList = calendarList;

        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setPreferredSize(new Dimension(400, 200));

        showSelectPopup();
    }

    /**
     * effects:
     * - instantiates selectPanel and sets its layouts
     * - instantiates its components
     * - formats the components
     * - adds the components
     * - creates a JOptionPane inside of a popup so that we can
     * get rid of the OK button that comes with JDialog
     */
    private void showSelectPopup() {
        selectPanel = new JPanel();

        initSelectComponents();
        formatSelectComponents();
        addSelectComponents();

        JOptionPane pane = new JOptionPane(
                selectPanel,
                JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null,
                new Object[] {},
                null);

        JDialog dialog = pane.createDialog("Edit Calendar Name");
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - initiates the panel's buttons
     */
    public void initSelectComponents() {
        selectNameBox = selectNameBox();
        calendarSelectButton = calendarSelectButton();
        calendarSelectPrompt = new JLabel("Select a calendar to edit");
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - formats the buttons to desire (location, and dimensions)
     */
    public void formatSelectComponents() {
        calendarSelectPrompt.setAlignmentX(CENTER_ALIGNMENT);

        selectNameBox.setMaximumSize(new Dimension(200, 30));
        selectNameBox.setAlignmentX(CENTER_ALIGNMENT);

        calendarSelectButton.setAlignmentX(CENTER_ALIGNMENT);
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - creates spacing between the components and adds them
     */
    public void addSelectComponents() {
        selectPanel.add(calendarSelectPrompt);
        selectPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        selectPanel.add(selectNameBox);
        selectPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        selectPanel.add(calendarSelectButton);
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - creates a ComboBox of calendars for the user to choose from
     */
    public JComboBox<Calendar> selectNameBox() {
        selectNameBox = new JComboBox<>();
        for (Calendar calendar : calendarList.getCalendars()) {
            selectNameBox.addItem(calendar);
        }
        return selectNameBox;
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - creates a new button that validates the option picked by user
     * - sets calendarSelectIndex to the index of the chosen calendar
     * - calls showEditPopup
     */
    public JButton calendarSelectButton() {
        calendarSelectButton = new JButton("Change Name");
        calendarSelectButton.addActionListener(e -> {
            calendarSelectIndex = selectNameBox.getSelectedIndex();
            if (calendarSelectIndex >= 0 && calendarSelectIndex <= calendarList.getCalendars().size() - 1) {
                showEditPopup();
            } else {
                new ErrorPanel("Could not change calendar's name.");
            }
            gui.showScene("MENU");

            SwingUtilities.getWindowAncestor(selectPanel).dispose();
        });
        return calendarSelectButton;
    }

    /**
     * effects:
     * - instantiates editPanel and sets its layouts
     * - instantiates its components
     * - formats the components
     * - adds the components
     * - creates a JOptionPane inside of a popup so that we can
     * get rid of the OK button that comes with JDialog
     */
    public void showEditPopup() {
        editPanel = new JPanel();
        editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.Y_AXIS));

        initEditComponents();
        formatEditComponents();
        addEditComponents();

        JOptionPane pane = new JOptionPane(
                editPanel,
                JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null,
                new Object[] {},
                null);

        JDialog dialog = pane.createDialog("Edit Calendar Name");
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - initiates the panel's buttons
     */
    public void initEditComponents() {
        calendarEditButton = calendarEditButton();
        newName = new JTextField(20);
        calendarEditPrompt = new JLabel("What is the new name?");
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - formats the buttons to desire (location, and dimensions)
     */
    public void formatEditComponents() {
        calendarEditPrompt.setAlignmentX(CENTER_ALIGNMENT);

        newName.setMaximumSize(new Dimension(300, 30));
        newName.setAlignmentX(CENTER_ALIGNMENT);

        calendarEditPrompt.setAlignmentX(CENTER_ALIGNMENT);
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - creates spacing between the buttons and adds them
     */
    public void addEditComponents() {
        editPanel.add(calendarEditPrompt);
        editPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        editPanel.add(newName);
        editPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        editPanel.add(calendarEditButton);
    }

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - creates a new button that edits the calendar's name where
     *      - the calendar is chosen by the user
     *      - the name is given by the user
     */
    public JButton calendarEditButton() {
        calendarEditButton = new JButton("Confirm Edit");
        calendarEditButton.addActionListener(e -> {
            String newFormattedName = newName.getText().trim();

            if (newFormattedName.isEmpty()) {
                new CannotPanel("Calendar name cannot be empty.");
                gui.showScene("MENU");
            } else {
                calendarList.getCalendar(calendarSelectIndex).setName(newFormattedName);
                new SuccessPanel("Name successfully changed!");
                gui.showScene("MENU");
            }

            SwingUtilities.getWindowAncestor(editPanel).dispose();
        });
        return calendarEditButton;
    }
}
