package persistence;

import model.CalendarList;
import model.Reminder;
import model.Calendar;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExcludeFromJacocoGeneratedReport
class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyCalendar() {
        try {
            CalendarList calendarList = new CalendarList();
            Calendar calendar = new Calendar("test");
            calendarList.addCalendar(calendar);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCalendar.json");
            writer.open();
            writer.write(calendarList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCalendar.json");
            calendarList = reader.read();
            assertEquals("test", calendarList.getCalendar(0).getName());
            assertEquals(0, calendarList.getCalendar(0).getTasks().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralCalendar() {
        try {
            CalendarList calendarList = new CalendarList();
            Calendar calendar = new Calendar("test calendar");
            Reminder reminder1 = new Reminder(LocalDate.of(2025, 12, 23), "reminder1", "Test Reminder", LocalTime.of(12, 0));
            Reminder reminder2 = new Reminder(LocalDate.of(2025, 12, 23), "reminder2", LocalTime.of(12, 0));
            calendar.addTask(reminder1);
            calendar.addTask(reminder2);
            calendarList.addCalendar(calendar);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCalendar.json");
            writer.open();
            writer.write(calendarList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCalendar.json");
            calendarList = reader.read();
            assertEquals("test calendar", calendarList.getCalendar(0).getName());
            List<Reminder> tasks = calendarList.getCalendar(0).getTasks();
            assertEquals(2, calendarList.getCalendar(0).getTasks().size());
            checkTask("reminder1", "Test Reminder", LocalDate.of(2025, 12, 23).toString(), LocalTime.of(12, 0).toString(), tasks.get(0));
            checkTask("reminder2", LocalDate.of(2025, 12, 23).toString(), LocalTime.of(12, 0).toString(), tasks.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}