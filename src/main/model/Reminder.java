package model;

import java.time.LocalDate;
import java.time.LocalTime;

import org.json.JSONObject;

import persistence.Writable;

/**
 * Represents Events that can be added to a calendar
 * 
 * Fields:
 * - LocalDate date
 * - String name
 * - String info
 * - LocalDate time
 * 
 * Fields can be changed through edit()
 * 
 * Implements Schedulable interface for common behaviours and to be able to get
 * added into a calendar
 * Imports LocalDate and LocalTime to handle date and time
 */

public class Reminder implements Writable {
    private LocalDate date;
    private String name;
    private String info;
    private LocalTime time;

    /**
     * REQUIRES:
     * - Date is in the future
     * - Time is in the future if the date is today
     * - date, name, info, and time is not null
     * 
     * MODIFIES:
     * - This
     * 
     * EFFECTS:
     * - creates a Reminder object with date, name, info, and time
     */
    public Reminder(LocalDate date, String name, String info, LocalTime time) {
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Date must be in the future.");
        }
        if (date.equals(LocalDate.now())) {
            if (time.isBefore(LocalTime.now())) {
                throw new IllegalArgumentException("Time must be in the future");
            }
        }
        this.date = date;
        this.name = name;
        this.info = info;
        this.time = time;
        EventLog.getInstance().logEvent(new Event("A new Task: " + name + " with information has been created"));
    }

    public Reminder(LocalDate date, String name, LocalTime time) {
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Date must be in the future.");
        }
        if (date.equals(LocalDate.now())) {
            if (time.isBefore(LocalTime.now())) {
                throw new IllegalArgumentException("Time must be in the future");
            }
        }
        this.date = date;
        this.name = name;
        this.info = "";
        this.time = time;
        EventLog.getInstance().logEvent(new Event("A new Task: " + name + " without information has been created"));
    }

    public LocalDate getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    /**
     * REQUIRES:
     * - Field matches a valid field
     * - NewValue is the same type as field (cast)
     * - field and newValue is not null
     * 
     * MODIFIES:
     * - This
     * 
     * EFFECTS:
     * - Updates the field specified in the parameter to newValue
     * - Only changes the field specified, and no other
     * - Prints an error message if field is invalid or newValue is incorrect type
     */
    public void edit(String field, Object newValue) {
        String prevName = getName();
        try {
            if (field == null || newValue == null) {
                throw new IllegalArgumentException("Value can not be null.");
            }
            switch (field) {
                case "date":
                    this.date = (LocalDate) newValue;
                    break;
                case "name":
                    this.name = (String) newValue;
                    break;
                case "info":
                    this.info = (String) newValue;
                    break;
                case "time":
                    this.time = (LocalTime) newValue;
                    break;
                default:
                    throw new IllegalArgumentException(field + " is not a valid option.");
            }
            EventLog.getInstance().logEvent(new Event("Task: " + prevName + "'s field: " + field + "has been changed"));
        } catch (ClassCastException e) {
            throw new ClassCastException(newValue + " is not the same type as " + field + ".");
        }
    }

    /**
     * EFFECTS:
     * - returns a string containing date, name, info, startTime, and endTime in a
     * custom format
     */
    public String toString() {
        return name;
    }

    /**
     * EFFECTS:
     * - returns a string containing date, name, info, startTime, and endTime in a
     * custom format
     */
    public String display() {
        if (!info.isEmpty()) {
            EventLog.getInstance().logEvent(new Event("Task: " + getName() + " has been viewed"));
            return name
                    + "\n"
                    + info
                    + "\n"
                    + date.getMonth().toString().substring(0, 1)
                    + date.getMonth().toString().substring(1).toLowerCase() + " "
                    + date.getDayOfMonth() + ", " + date.getYear()
                    + "\n"
                    + time.toString();
        } else {
            EventLog.getInstance().logEvent(new Event("Task: " + getName() + " has been viewed"));
            return name
                    + "\n"
                    + date.getMonth().toString().substring(0, 1)
                    + date.getMonth().toString().substring(1).toLowerCase() + " "
                    + date.getDayOfMonth() + ", " + date.getYear()
                    + "\n"
                    + "At "
                    + time.toString();
        }
    }

    // Code adapted from the sample application provided in class
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("date", date.toString());
        json.put("info", info);
        json.put("time", time.toString());
        return json;
    }
}
