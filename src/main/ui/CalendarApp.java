package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.*;
import java.util.*;

import org.json.JSONException;

import model.Calendar;
import model.Reminder;
import persistence.JsonReader;
import persistence.JsonWriter;
import model.CalendarList;

/**
 * Represents Console UI for Calendar that can interact with Reminders
 * 
 * Fields:
 * - CalendarList calendarList
 * - Scanner input
 * - LocalDate date
 * 
 * - Imports calendar to be able to create calendar class to use its methods
 */
public class CalendarApp {
    private CalendarList calendarList;
    private Scanner input;
    private LocalDate date;
    private Boolean running;

    /**
     * modifies:
     * - this
     * 
     * EFFECTS:
     * - instantiates a new CalendarList, calendarList
     * - instantiates a new scanner, input
     */
    public CalendarApp() {
        calendarList = new CalendarList();
        input = new Scanner(System.in);
        running = true;
    }

    /**
     * requires:
     * - input is int
     * - int is not out of bounds
     * 
     * modifies:
     * - input
     * 
     * effects:
     * - displays a message with a custom word, then returns what the user inputs
     */
    public int chooseCalendar(String option) {
        try {
            System.out.print("Which calendar would you like to " + option + "? (Number): ");
            int calendarOption = input.nextInt();
            if (calendarList.getCalendarsSize() < calendarOption) {
                input.nextLine();
                throw new IndexOutOfBoundsException();
            }
            input.nextLine();
            System.out.println();
            return calendarOption - 1;
        } catch (IllegalArgumentException e) {
            input.nextLine();
            throw new IllegalArgumentException("Option must be a number");
        } catch (IndexOutOfBoundsException e) {
            input.nextLine();
            throw new IndexOutOfBoundsException("Number is not valid");
        }
    }

    /**
     * requires:
     * - input is int
     * - int is not out of bounds
     * 
     * modifies:
     * - input
     * 
     * effects:
     * - displays a message with a custom word, then returns what the user inputs
     * 
     */
    public int chooseTask(String option, int calendarChoice) {
        try {
            System.out.print("Which task would you like to " + option + "? (Number): ");
            int taskOption = input.nextInt();
            if (calendarList.getCalendar(calendarChoice).getTasks().size() < taskOption) {
                throw new IndexOutOfBoundsException("Number is not valid");
            }
            input.nextLine();
            System.out.println();
            return taskOption - 1;
        } catch (InputMismatchException e) {
            input.nextLine();
            throw new InputMismatchException("Option must be a number");
        } catch (IndexOutOfBoundsException e) {
            input.nextLine();
            throw new IndexOutOfBoundsException("Number is not valid");
        }
    }

    /**
     * MODIFIES:
     * - This
     * 
     * modifies:
     * - input
     * 
     * EFFECTS:
     * - Adds calendar to cakendars list
     */
    public void addCalendar() {
        System.out.print("What would you like to name your calendar?: ");
        String name = input.nextLine();

        Calendar calendar = new Calendar(name);

        calendarList.addCalendar(calendar);
        System.out.println();
    }

    /**
     * requires:
     * - input is int
     * - int is in bound
     * 
     * MODIFIES:
     * - This
     * - input
     * 
     * EFFECTS:
     * - removes calendar to calendars list
     */
    public void removeCalendar() {
        displayCalendars();
        try {
            int option = chooseCalendar("remove");
            if (calendarList.getCalendarsSize() < option) {
                input.nextLine();
                throw new IndexOutOfBoundsException();
            }
            calendarList.removeCalendar(option - 1);
        } catch (IndexOutOfBoundsException e) {
            input.nextLine();
            throw new IndexOutOfBoundsException("Number is not valid");
        } catch (IllegalArgumentException e) {
            input.nextLine();
            throw new IllegalArgumentException("Option must be a number");
        }

    }

    /**
     * requires:
     * input is int
     * int is not out of bounds
     * inputs are not null
     * 
     * effects:
     * adds task to calendar
     */
    public void calendarAddTask() {
        int calendarChoice = chooseCalendar("choose");
        Calendar calendar = calendarList.getCalendar(calendarChoice);
        String name = promptName();
        LocalDate date = promptDate();
        LocalTime time = promptTime();
        boolean wantsInfo = promptWantsInfo();
        Reminder reminder;
        if (wantsInfo) {
            String info = promptInfo();
            reminder = new Reminder(date, name, info, time);
        } else {
            reminder = new Reminder(date, name, time);
        }
        calendar.addTask(reminder);
    }

    /**
     * modifies:
     * - input
     * 
     * effects:
     * get user's input for a name and returns it
     */
    private String promptName() {
        System.out.print("What is the name of the reminder?: ");
        String name = input.nextLine();
        System.out.println();
        return name;
    }

    /**
     * requires:
     * - date is today or in the future
     * - input is int
     * - date is not null
     * 
     * modifies:
     * - this
     * - input
     * 
     * effects:
     * get user's input for a date and returns it
     */
    private LocalDate promptDate() {
        try {
            System.out.print("What day is the reminder on? (Year, Month, Day, in numbers separated by a space): ");
            int year = input.nextInt();
            int month = input.nextInt();
            int day = input.nextInt();
            input.nextLine();
            System.out.println();
            date = LocalDate.of(year, month, day);
            if (date.isBefore(LocalDate.now())) {
                input.nextLine();
                throw new IllegalArgumentException("Date must be in the future.");
            }
            if (date == null) {
                input.nextLine();
                throw new IllegalArgumentException("Day can not be null.");
            }
            return date;
        } catch (InputMismatchException e) {
            input.nextLine();
            throw new InputMismatchException("Inputs must be numbers");
        }
    }

    /**
     * modifies:
     * - input
     * 
     * effects:
     * get user's input on if they want to add info and returns it
     */
    private boolean promptWantsInfo() {
        System.out.print("Would you like to add extra info? (Y/N): ");
        String choice = input.nextLine();
        System.out.println();
        return choice.equalsIgnoreCase("Y");
    }

    /**
     * modifies:
     * - input
     * 
     * effects:
     * get user's input for the info and returns it
     */
    private String promptInfo() {
        System.out.print("What would you like to add?: ");
        String info = input.nextLine();
        System.out.println();
        return info;
    }

    /**
     * modifies:
     * - input
     * 
     * effects:
     * gets user's input for the time and returns it
     * 
     */
    private LocalTime promptTime() {
        try {
            System.out.print("At what time is the reminder? (Hr, Min, in 24 hour, numbers separated by a space): ");
            int hour = input.nextInt();
            int minute = input.nextInt();
            input.nextLine();
            System.out.println();
            LocalTime time = LocalTime.of(hour, minute);
            if (date.equals(LocalDate.now())) {
                if (time.isBefore(LocalTime.now())) {
                    input.nextLine();
                    throw new IllegalArgumentException("Time must be in the future");
                }
            }
            if (date == null || time == null) {
                input.nextLine();
                throw new IllegalArgumentException("Value can not be null.");
            }
            return time;
        } catch (InputMismatchException e) {
            input.nextLine();
            throw new InputMismatchException("Inputs must be numbers");
        }

    }

    /**
     * requires:
     * - input is an int
     * - int is not out of bounds
     * effects:
     * - removes a task from a calendar
     */
    public void calendarRemoveTask() {
        int calendarChoice = chooseCalendar("choose");
        Calendar calendar = calendarList.getCalendar(calendarChoice);
        displayCalendarTasks(calendarChoice);
        int taskChoice = chooseTask("choose", calendarChoice);
        calendar.removeTask(taskChoice);
    }

    /**
     * requires:
     * - input is int
     * - int is not out of bounds
     * 
     * MODIFIES:
     * - calendar
     * - input
     * 
     * EFFECTS:
     * - edits calendar's name
     */
    public void editCalendarName() {
        displayCalendars();
        int calendarChoice = chooseCalendar("choose");
        Calendar calendar = calendarList.getCalendar(calendarChoice);
        System.out.print("What would you like the new name to be?: ");
        String name = input.nextLine();
        calendar.setName(name);
    }

    /**
     * requires:
     * - input is int
     * - int is not out of bounds
     * - input is not null
     * 
     * modifies:
     * - reminder
     * 
     * effects:
     * - get user's input for calendar, task, field, and new value
     * - replaces the calendar's task's field's old value to new value
     */
    public void editCalendarTask() {
        displayCalendars();
        int calendarChoice = chooseCalendar("choose");
        Calendar calendar = calendarList.getCalendar(calendarChoice);
        displayCalendarTasks(calendarChoice);
        if (calendar.getTasks().isEmpty()) {
            return;
        }
        int taskChoice = chooseTask("choose", calendarChoice);
        Reminder task = calendar.getTasks().get(taskChoice);
        viewCalendarTasks(calendarChoice, taskChoice);
        String field = chooseField();
        Object newValue = chooseNewValue(field);
        calendar.editTask(task, field, newValue);
    }

    /**
     * requires:
     * - field matches an option
     * 
     * modifies:
     * - input
     * 
     * effects:
     * - using user's input on which field they want to edit,
     * match the field, prompt them for a new value, and replace the old with new
     */
    private Object chooseNewValue(String field) {
        switch (field) {
            case "date":
                return promptDate();
            case "name":
                return promptName();
            case "info":
                return promptInfo();
            case "time":
                return promptTime();
            default:
                input.nextLine();
                throw new IllegalArgumentException("Field does not match an option");
        }
    }

    /**
     * modifies:
     * - input
     * 
     * effects:
     * prompts the user which field they want to edit, and returns it
     */
    private String chooseField() {
        System.out.println("Fields for Reminder:");
        System.out.println("1. date");
        System.out.println("2. name");
        System.out.println("3. info");
        System.out.println("4. time");

        System.out.print("Which field would you like to edit? (String): ");
        String choice = input.nextLine();

        System.out.println();

        return choice;
    }

    /**
     * REQUIRES:
     * - calendar is of type Calendar
     * 
     * EFFECTS:
     * - prints out list of calendars in a nice format
     */
    public void displayCalendars() {
        if (calendarList.getCalendarsSize() == 0) {
            System.out.println("No calendars have been made.");
            System.out.println();
        } else {
            System.out.println("Calendars: ");
            for (int i = 1; i < calendarList.getCalendarsSize() + 1; i++) {
                System.out.println(i + ". " + calendarList.getCalendar(i - 1));
            }
            System.out.println();
        }
    }

    /**
     * REQUIRES:
     * - calendar is not empty
     * 
     * EFFECTS:
     * - prints out calendar's tasks in a nice format
     */
    public void viewCalendarTasks() {
        if (calendarList.getCalendars().isEmpty()) {
            displayCalendars();
        } else {
            displayCalendars();
            int calendarOption = chooseCalendar("view");
            if (calendarList.getCalendar(calendarOption).getTasks().isEmpty()) {
                displayCalendarTasks(calendarOption);
            } else {
                displayCalendarTasks(calendarOption);
                int taskOption = chooseTask("view", calendarOption);
                System.out.println(calendarList.getCalendar(calendarOption).viewTask(taskOption).display());
                System.out.println();
            }
        }
    }

    /**
     * effects:
     * prints out calendar's task's information
     */
    public void viewCalendarTasks(int calendarOption, int taskOption) {
        System.out.println(calendarList.getCalendar(calendarOption).viewTask(taskOption).display());
        System.out.println();
    }

    /**
     * EFFECTS:
     * - displays calendar's list of tasks
     */
    public void displayCalendarTasks(int calendarOption) {
        if (calendarList.getCalendar(calendarOption).getTasks().isEmpty()) {
            System.out.println("Calendar is empty.");
            System.out.println();
        } else {
            System.out.println("Tasks: ");
            for (int i = 0; i < calendarList.getCalendar(calendarOption).getTasks().size(); i++) {
                System.out.println(i + 1 + ". " + calendarList.getCalendar(calendarOption).viewTask(i));
            }
            System.out.println();
        }
    }

    /**
     * REQUIRES:
     * - input is an int
     * 
     * modifies:
     * - input
     * 
     * EFFECTS:
     * - runs the program
     * - Prompts user which option he wants to pick, then runs option he picked
     * - Prompts user which option he wants to pick then runs option he picked
     * - Prompts user for information regarding the option he picked (field, value,
     * name, remove, add, edit, etc)
     * - Prints out error message if option user picked is not valid
     */
    public void run() {
        while (running) {
            try {
                runOptions();
            } catch (InputMismatchException e) {
                input.nextLine();
                printPromptException();
            }
        }
    }

    /**
     * modifies:
     * this
     * 
     * effects:
     * - prompts user which option he wants to pick, then runs option he picked
     * - prints error message is option is not valid
     */
    public void runOptions() {
        int option = promptOption();
        if (option == 1) {
            int calendarOption = promptCalendarOption();
            calendarRun(calendarOption);
        } else if (option == 2) {
            int taskOption = promptTaskOption();
            taskRun(taskOption);
        } else if (option == 3) {
            System.out.println("Quitting");
            running = false;
        } else if (option == 4) {
            System.out.println("Saving");
            System.out.println();
            save();
        } else if (option == 5) {
            System.out.println("Loading");
            System.out.println();
            load();
        } else {
            System.out.println("Number is not valid.");
            System.out.println();
        }
    }

    /**
     * MODIFIES:
     * - save.json file
     * 
     * modifies:
     * - input
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
        } catch (FileNotFoundException e) {
            input.nextLine();
            System.out.println();
            System.out.println("File not found");
            System.out.println();
        }
    }

    /**
     * MODIFIES:
     * - this
     * - input
     * 
     * EFFECTS:
     * - parses save.json and adds that data into calendarList
     */
    public void load() {
        try {
            JsonReader reader = new JsonReader("./data/save.json");
            calendarList = reader.read();
        } catch (IOException e) {
            input.nextLine();
            System.out.println();
            System.out.println("Error reading file");
            System.out.println();
        } catch (JSONException e) {
            input.nextLine();
            System.out.println();
            System.out.println("Error parsing JSON");
            System.out.println();
        }
    }

    /**
     * modifies:
     * - input
     * 
     * EFFECTS:
     * - prints out exception message
     */
    public void printPromptException() {
        System.out.println();
        System.out.println("Input must be a number.");
        System.out.println();
        input.nextLine();
    }

    /**
     * REQUIRES:
     * - input is an int
     * 
     * modifies:
     * - input
     * 
     * EFFECTS:
     * - Prompts user which option he wants to pick
     */
    public int promptOption() {
        printMenuOptions();
        int taskOption = input.nextInt();
        input.nextLine();
        System.out.println();
        return taskOption;
    }

    /**
     * REQUIRES:
     * - input is an int
     * 
     * modifies:
     * - input
     * 
     * EFFECTS:
     * - Prompts user which calendar option he wants to pick
     */
    public int promptCalendarOption() {
        printCalendarsOptions();
        int calendarOption = input.nextInt();
        input.nextLine();
        System.out.println();
        return calendarOption;
    }

    /**
     * modifies:
     * - input
     * 
     * EFFECTS:
     * - Prompts user which option he wants to pick
     */
    public void calendarAddCalendar() {
        System.out.print("What would you like to name your calendar?: ");
        String name = input.nextLine();
        Calendar calendar = new Calendar(name);
        calendarList.addCalendar(calendar);
        System.out.println();
        System.out.println("Calendar added successfully");
        System.out.println();
    }

    /**
     * REQUIRES:
     * - atleast one calendar exists
     * - input must be int
     * - int must be in range
     * 
     * modifies:
     * - input
     * 
     * EFFECTS:
     * - Prompts user which option he wants to pick
     */
    public void calendarRemoveCalendar() {
        if (calendarList.getCalendarsSize() == 0) {
            displayCalendars();
        } else {
            displayCalendars();
            try {
                int removeOption = chooseCalendar("remove");
                calendarList.removeCalendar(removeOption);
                System.out.println("Calendar removed successfully");
                System.out.println();
            } catch (IndexOutOfBoundsException e) {
                input.nextLine();
                System.out.println(e.getMessage());
                System.out.println();
            } catch (IllegalArgumentException e) {
                input.nextLine();
                System.out.println(e.getMessage());
                System.out.println();
            }
        }
    }

    /**
     * REQUIRES:
     * - atleast one calendar exists
     * 
     * modifies:
     * - input
     * 
     * EFFECTS:
     * - Prints list of calendars
     */
    public void calendarViewCalendars() {
        if (calendarList.getCalendarsSize() == 0) {
            displayCalendars();
        } else {
            displayCalendars();
            System.out.print("Press \"Enter\" to continue.");
            input.nextLine();
            System.out.println();
            clearScreen();
        }
    }

    /**
     * REQUIRES:
     * - atleast one calendar exists
     * - input must be int
     * - int must be in range
     * 
     * modifies:
     * - input
     * 
     * EFFECTS:
     * - Prompts user which calendar he wants to edit
     * - prompts user what he wants the new name to be
     * - changes that calendar's name to the new value user inputted
     */
    public void calendarEditCalendarName() {
        if (calendarList.getCalendarsSize() == 0) {
            displayCalendars();
        } else {
            try {
                displayCalendars();
                int calendarRename = chooseCalendar("edit");
                System.out.print("What would you like to name your calendar?: ");
                String newName = input.nextLine();
                calendarList.getCalendar(calendarRename).setName(newName);
                System.out.println();
                clearScreen();
            } catch (IndexOutOfBoundsException e) {
                input.nextLine();
                System.out.println(e.getMessage());
                System.out.println();
            } catch (IllegalArgumentException e) {
                input.nextLine();
                System.out.println(e.getMessage());
                System.out.println();
            }
        }
    }

    /**
     * REQUIRES:
     * - int must be in range
     * 
     * modifies:
     * - input
     * 
     * EFFECTS:
     * - Runs the option that is given by the method
     */
    public void calendarRun(int calendarOption) {
        if (calendarOption == 1) {
            calendarAddCalendar();
        } else if (calendarOption == 2) {
            calendarRemoveCalendar();
        } else if (calendarOption == 3) {
            calendarViewCalendars();
        } else if (calendarOption == 4) {
            calendarEditCalendarName();
        } else {
            System.out.println();
            input.nextLine();
            throw new IllegalArgumentException("Number is not valid");
        }
    }

    /**
     * REQUIRES:
     * - input is an int
     * 
     * modifies:
     * - input
     * 
     * EFFECTS:
     * - Prompts user which task option he wants to pick
     */
    public int promptTaskOption() {
        printTasksOptions();
        int taskOption = input.nextInt();
        input.nextLine();
        System.out.println();
        return taskOption;
    }

    /**
     * REQUIRES:
     * - atleast one calendar exists
     * - input must be int
     * - int must be in range
     * 
     * modifies:
     * - input
     * 
     * EFFECTS:
     * - Prompts user which calendar he wants to add to
     * - prompts user questions needed to make a new reminder
     * - adds the reminder to the calendar
     */
    public void taskAddTask() {
        if (calendarList.getCalendarsSize() == 0) {
            displayCalendars();
        } else {
            try {
                displayCalendars();
                calendarAddTask();
                System.out.println("Task added successfully");
                System.out.println();
            } catch (IndexOutOfBoundsException e) {
                input.nextLine();
                System.out.println(e.getMessage());
                System.out.println();
            } catch (IllegalArgumentException e) {
                input.nextLine();
                System.out.println(e.getMessage());
                System.out.println();
            } catch (InputMismatchException e) {
                input.nextLine();
                System.out.println(e.getMessage());
                System.out.println();
            }
        }
    }

    /**
     * REQUIRES:
     * - atleast one calendar exists
     * - input must be int
     * - int must be in range
     * 
     * modifies:
     * - input
     * 
     * EFFECTS:
     * - Prompts user which calendar he wants to remove from
     * - prompts user which task he wants to remove
     * - removes the reminder from the calendar
     */
    public void taskRemoveTask() {
        if (calendarList.getCalendarsSize() == 0) {
            displayCalendars();
        } else {
            try {
                displayCalendars();
                calendarRemoveTask();
                System.out.println("Task removed successfully");
                System.out.println();
            } catch (IndexOutOfBoundsException e) {
                input.nextLine();
                System.out.println(e.getMessage());
                System.out.println();
            } catch (IllegalArgumentException e) {
                input.nextLine();
                System.out.println(e.getMessage());
                System.out.println();
            }
        }
    }

    /**
     * REQUIRES:
     * - atleast one calendar exists
     * - input must be int
     * - int must be in range
     * 
     * modifies:
     * - input
     * 
     * EFFECTS:
     * - Prompts user which calendar he wants to view
     * - prints that calendar's list of tasks
     */
    public void taskViewTasks() {
        if (calendarList.getCalendarsSize() == 0) {
            displayCalendars();
        } else {
            try {
                displayCalendars();
                int calendarViewTasks = chooseCalendar("view");
                displayCalendarTasks(calendarViewTasks);

                System.out.print("Press \"Enter\" to continue.");
                input.nextLine();
                clearScreen();
            } catch (IndexOutOfBoundsException e) {
                input.nextLine();
                System.out.println(e.getMessage());
                System.out.println();
            } catch (IllegalArgumentException e) {
                input.nextLine();
                System.out.println(e.getMessage());
                System.out.println();
            }
        }
    }

    /**
     * REQUIRES:
     * - atleast one calendar exists
     * - input must be int
     * - int must be in range
     * 
     * modifies:
     * - input
     * 
     * EFFECTS:
     * - Prompts user which calendar he wants to view
     * - prompts user which task he wants to view
     * - prints that calendar's task's information
     */
    public void taskViewSpecificTask() {
        if (calendarList.getCalendarsSize() == 0) {
            displayCalendars();
        } else {
            try {
                viewCalendarTasks();
                System.out.println("Press \"Enter\" to continue.");
                input.nextLine();
                clearScreen();
            } catch (IndexOutOfBoundsException e) {
                input.nextLine();
                System.out.println(e.getMessage());
                System.out.println();
            } catch (IllegalArgumentException e) {
                input.nextLine();
                System.out.println(e.getMessage());
                System.out.println();
            }
        }
    }

    /**
     * REQUIRES:
     * - atleast one calendar exists
     * - input must be int
     * - int must be in range
     * - new value must be the same type as the field
     * 
     * modifies:
     * - input
     * 
     * EFFECTS:
     * - Prompts user which calendar he wants to pick
     * - prompts user which task he wants to edit
     * - prompts user which field he wants to change
     * - prompts user questions needed to change the field
     * - changes that calendar's task's field's value to the new value
     */
    public void taskEditTask() {
        if (calendarList.getCalendarsSize() == 0) {
            displayCalendars();
        } else {
            try {
                editCalendarTask();
                System.out.println();
                clearScreen();
            } catch (IllegalArgumentException e) {
                input.nextLine();
                System.out.println(e.getMessage());
            } catch (IndexOutOfBoundsException e) {
                input.nextLine();
                System.out.println(e.getMessage());
                System.out.println();
            } catch (ClassCastException e) {
                input.nextLine();
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * REQUIRES:
     * - int must be in range
     * 
     * EFFECTS:
     * - Runs the option that is given by the method
     */
    public void taskRun(int taskOption) {
        if (taskOption == 1) {
            taskAddTask();
        } else if (taskOption == 2) {
            taskRemoveTask();
        } else if (taskOption == 3) {
            taskViewTasks();
        } else if (taskOption == 4) {
            taskViewSpecificTask();
        } else if (taskOption == 5) {
            taskEditTask();
        } else {
            System.out.println("Number is not valid.");
            System.out.println();
        }
    }

    /**
     * EFFECTS:
     * - prints possible options
     */
    public void printMenuOptions() {
        System.out.println("Options:");
        System.out.println("1. Calendars");
        System.out.println("2. Tasks");
        System.out.println("3. Quit");
        System.out.println("4. Save");
        System.out.println("5. Load");
        System.out.print("Select an option (1-5): ");
    }

    /**
     * EFFECTS:
     * - prints possible calendar options
     */
    public void printCalendarsOptions() {
        System.out.println("Options:");
        System.out.println("1. Add a calendar");
        System.out.println("2. Remove a calendar");
        System.out.println("3. View calendars");
        System.out.println("4. Edit calendar name");
        System.out.print("Select an option (1-4): ");
    }

    /**
     * EFFECTS:
     * - prints possible task options
     */
    public void printTasksOptions() {
        System.out.println("Options:");
        System.out.println("1. Add task to a calendar");
        System.out.println("2. Remove task from a calendar");
        System.out.println("3. View tasks from a calendar");
        System.out.println("4. View a specific task");
        System.out.println("5. Edit a task");
        System.out.print("Select an option (1-5): ");
    }

    /**
     * EFFECTS:
     * - clears the terminal screen just to look better
     */
    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * effects:
     * starts the console app
     */
    public static void main(String[] args) {
        CalendarApp test = new CalendarApp();
        test.run();
    }
}