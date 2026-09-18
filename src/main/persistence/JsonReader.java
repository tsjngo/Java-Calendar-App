package persistence;

import model.Reminder;
import model.Calendar;
import model.CalendarList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

import org.json.*;

// Code adapted from the sample application provided in class
// Represents a reader that reads calendarList from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Calendarlist from file and returns it;
    // throws IOException if an error occurs reading data from file
    public CalendarList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCalendarList(jsonObject);
    }

    // EFFECTS: parses calendarList from JSON object and returns it
    private CalendarList parseCalendarList(JSONObject jsonObject) {
        CalendarList list = new CalendarList();
        JSONArray jsonCalendars = jsonObject.getJSONArray("calendars");
        for (Object obj : jsonCalendars) {
            JSONObject nextCalendar = (JSONObject) obj;
            list.addCalendar(parseCalendar(nextCalendar));
        }
        return list;
    }

    // EFFECTS: parses calendar from JSON object and returns it
    private Calendar parseCalendar(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Calendar calendar = new Calendar(name);
        addTasks(calendar, jsonObject);
        return calendar;
    }

    /**
     * MODIFIES:
     * - calendar
     * 
     * EFFECTS:
     * - parses tasks from JSON object and adds them to calendar
     */
    private void addTasks(Calendar calendar, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("tasks");
        for (Object obj : jsonArray) {
            JSONObject nextTask = (JSONObject) obj;
            String taskName = nextTask.getString("name");
            String info = nextTask.getString("info");
            LocalDate date = LocalDate.parse(nextTask.getString("date"));
            LocalTime time = LocalTime.parse(nextTask.getString("time"));
            Reminder task = new Reminder(date, taskName, info, time);
            calendar.addTask(task);
        }
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }
}
