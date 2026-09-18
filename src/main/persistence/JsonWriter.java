package persistence;

import model.CalendarList;

import org.json.JSONObject;

import java.io.*;


// Code adapted from the sample application provided in class
// Represents a writer that writes JSON representation of CalendarList to file
public class JsonWriter {
    private static final int TAB = 4; // For pretty printing
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file
    // cannot be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of CalendarList to file
    public void write(CalendarList calendarList) {
        JSONObject json = calendarList.toJson(); // Convert CalendarList to JSONObject
        saveToFile(json.toString(TAB)); // Pretty print with indentation
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json); // Write the JSON data to the file
    }
}
