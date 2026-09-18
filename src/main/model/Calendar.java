package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

/**
 * Represents Calendars that can add Reminders
 * 
 * Fields:
 * - tasks
 * - String name
 */

public class Calendar implements Writable {
    private List<Reminder> tasks;
    private String name;

    /**
     * EFFECTS:
     * - instantiates a new calendar with specified name
     * - instantiates new empty arraylist, tasks
     */
    public Calendar(String name) {
        this.name = name;
        tasks = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("New calendar: " + name + " has been created"));
    }

    public LocalDate getToday() {
        return LocalDate.now();
    }

    public LocalTime getCurrentTime() {
        return LocalTime.now();
    }

    public List<Reminder> getTasks() {
        return tasks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * REQUIRES:
     * - Task is not null
     * - Task is of type Reminder or a subtype of it
     * 
     * MODIFIES:
     * - This
     * 
     * EFFECTS:
     * - Adds task to tasks list
     */
    public void addTask(Reminder task) {
        try {
            if (task == null) {
                throw new IllegalArgumentException("Task can not be null.");
            }
            tasks.add(task);
            EventLog.getInstance().logEvent(new Event("Task: " + task.getName() + " has been added to calendar: " + getName()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Task must be a reminder.");
        }
    }

    /**
     * REQUIRES:
     * - index is not of out bounds
     * 
     * MODIFIES:
     * - This
     * 
     * EFFECTS:
     * - Removes task from tasks list
     */
    public void removeTask(int index) {
        try {
            tasks.remove(index);
            EventLog.getInstance().logEvent(new Event("Task: " + tasks.get(index).getName() + " has been removed from calendar: " + getName()));
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException(index + 1 + " is not valid.");
        }
    }

    /**
     * MODIFIES:
     * - This
     * 
     * EFFECTS:
     * - Removes task from tasks list
     */
    public void removeTask(Reminder task) {
        tasks.remove(task);
        EventLog.getInstance().logEvent(new Event("Task: " + task.getName() + " has been removed from calendar: " + getName()));
    }

    /**
     * REQUIRES:
     * - index is not out of bounds
     * 
     * EFFECTS:
     * - Shows all of tasks's field's values
     */
    public Reminder viewTask(int index) {
        try {
            EventLog.getInstance().logEvent(new Event("Calendar: " + getName() + " was viewed"));
            return tasks.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException(index + 1 + " is not valid.");
        }
    }

    /**
     * REQUIRES:
     * - Task is not null
     * - Field is a valid field
     * - Value is the same type as the field
     * 
     * MODIFIES:
     * - task
     * 
     * EFFECTS:
     * - Edits one of task's fields
     */
    public void editTask(Reminder task, String field, Object newValue) {
        task.edit(field, newValue);
        EventLog.getInstance().logEvent(new Event("Calendar: " + getName() + "'s task: " + task.getName() + "'s field: " + field + " was changed"));
    }

    /**
     * EFFECTS:
     * - returns a string containing name
     */
    public String toString() {
        return this.name;
    }

    // Code adapted from the sample application provided in class
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("tasks", tasksToJson());
        json.put("name", name);
        return json;
    }

    // Code adapted from the sample application provided in class
    // EFFECTS: returns Reminders in this calendar as a JSON array
    private JSONArray tasksToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Reminder task : tasks) {
            jsonArray.put(task.toJson());
        }
        return jsonArray;
    }
}
