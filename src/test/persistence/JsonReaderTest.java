package persistence;

import model.Reminder;
import model.CalendarList;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {
    CalendarList calendarList;

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            calendarList = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCalendar() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCalendar.json");
        try {
            CalendarList calendarList = reader.read();
            assertEquals("calendar 1", calendarList.getCalendar(0).getName());
            assertEquals(0, calendarList.getCalendar(0).getTasks().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCalendar.json");
        try {
            CalendarList calendarList = reader.read();
            assertEquals("calendar 1", calendarList.getCalendar(0).getName());
            List<Reminder> tasks = calendarList.getCalendar(0).getTasks();
            assertEquals(2, tasks.size());
            checkTask("reminder 1", "birthday", LocalDate.of(2025, 12, 23).toString(), LocalTime.of(0, 0).toString(), tasks.get(0));
            checkTask("reminder 2", "new year", LocalDate.of(2026, 1, 1).toString(), LocalTime.of(0, 0).toString(), tasks.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}