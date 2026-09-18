package model;

import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Represents a list of calendars
 * 
 * Fields:
 * - List of Calendars, calendarList
 * 
 * - imports java.util to add and interact with Calendar
 */

public class CalendarList {
    private List<Calendar> calendarList;

    /**
     * EFFECTS:
     * - creates a new empty arraylist, calendarList
     */
    public CalendarList() {
        calendarList = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("New CalendarList has been created"));
    }

    public List<Calendar> getCalendars() {
        return calendarList;
    }

    /**
     * MODIFIES:
     * - This
     * 
     * EFFECTS:
     * - Adds calendar to cakendars list
     */
    public void addCalendar(Calendar calendar) {
        calendarList.add(calendar);
        EventLog.getInstance().logEvent(new Event("CalendarList added a new calendar: " + calendar.getName()));
    }

    /**
     * EFFECTS:
     * returns number of calendars made
     */
    public int getCalendarsSize() {
        return calendarList.size();
    }

    /**
     * REQUIRES:
     * - Int is not out of bounds
     * 
     * EFFECTS:
     * - returns calendar at the index provided
     */
    public Calendar getCalendar(int calendar) {
        try {
            return calendarList.get(calendar);
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException(calendar + 1 + " is not valid.");
        }
    }

    /**
     * REQUIRES:
     * - Int is not out of bounds
     * 
     * EFFECTS:
     * - removes calendar at the index provided
     */
    public void removeCalendar(int calendar) {
        try {
            EventLog.getInstance().logEvent(new Event("CalendarList has removed calendar: " + calendarList.get(calendar).getName()));
            calendarList.remove(calendar);
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException(calendar + 1 + " is not valid.");
        }
    }

    // Code adapted from the sample application provided in class
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("calendars", calendarsToJson());
        return json;
    }

    // Code adapted from the sample application provided in class
    /**
     * EFFECTS:
     * - returns calendars in this CalendarList as a JSON Array
     */
    private JSONArray calendarsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Calendar calendar : calendarList) {
            jsonArray.put(calendar.toJson());
        }
        return jsonArray;
    }
}
