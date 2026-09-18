package model;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReminderTest {
    private Reminder reminder1;
    private Reminder reminder2;

    @BeforeEach
    void runBefore() {
        reminder1 = new Reminder(LocalDate.of(2025, 12, 23), "reminder1", "Test Reminder", LocalTime.of(12, 0));
        reminder2 = new Reminder(LocalDate.of(2025, 12, 23), "reminder2", LocalTime.of(12, 0));
    }

    @Test
    void testConstructor1() {
        assertEquals(LocalDate.of(2025, 12, 23), reminder1.getDate());
        assertEquals("reminder1", reminder1.getName());
        assertEquals("Test Reminder", reminder1.getInfo());
        assertEquals(LocalTime.of(12, 0), reminder1.getTime());
    }

    @Test
    void testConstructor2() {
        assertEquals(LocalDate.of(2025, 12, 23), reminder2.getDate());
        assertEquals("reminder2", reminder2.getName());
        assertEquals("", reminder2.getInfo());
        assertEquals(LocalTime.of(12, 0), reminder2.getTime());
    }

    @Test
    void testSetDate() {
        assertEquals(LocalDate.of(2025, 12, 23), reminder1.getDate());
        reminder1.setDate(LocalDate.of(2000, 1, 1));
        assertEquals(LocalDate.of(2000, 1, 1), reminder1.getDate());
    }

    @Test
    void testSetName() {
        assertEquals("reminder1", reminder1.getName());
        reminder1.setName("newName");
        assertEquals("newName", reminder1.getName());
    }

    @Test
    void testSetInfo() {
        assertEquals("Test Reminder", reminder1.getInfo());
        reminder1.setInfo("new Info");
        assertEquals("new Info", reminder1.getInfo());
    }

    @Test
    void testSetTime() {
        assertEquals(LocalTime.of(12, 0), reminder1.getTime());
        reminder1.setTime(LocalTime.of(0, 0));
        assertEquals(LocalTime.of(0, 0), reminder1.getTime());
    }

    @Test
    void testTimeBeforeNowTodayThrows() {
        LocalDate today = LocalDate.now();
        LocalTime pastTime = LocalTime.now().minusMinutes(1);

        try {
            new Reminder(today, "reminder", pastTime);
            fail("Expected IllegalArgumentException for past time today");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    void testTimeAfterNowTodaySucceeds() {
        LocalDate today = LocalDate.now();
        LocalTime futureTime = LocalTime.now().plusMinutes(1);

        try {
            new Reminder(today, "reminder", futureTime);
        } catch (IllegalArgumentException e) {
            fail("Did not expect IllegalArgumentException for future time today");
        }
    }

    @Test
    void testFutureDateAnyTimeSucceeds() {
        LocalDate futureDate = LocalDate.now().plusDays(1);
        LocalTime anyTime = LocalTime.of(0, 0);

        try {
            new Reminder(futureDate, "reminder", anyTime);
        } catch (IllegalArgumentException e) {
            fail("Did not expect IllegalArgumentException for future date");
        }
    }

    @Test
    void testPastDateThrows() {
        LocalDate pastDate = LocalDate.now().minusDays(1);
        LocalTime anyTime = LocalTime.of(12, 0);

        try {
            new Reminder(pastDate, "reminder", anyTime);
            fail("Expected IllegalArgumentException for past date");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    void testEditName() {
        reminder1.edit("name", "new name");
        assertEquals("new name", reminder1.getName());
    }

    @Test
    void testEditDate() {
        LocalDate newDate = LocalDate.of(2030, 1, 1);
        reminder1.edit("date", newDate);
        assertEquals(newDate, reminder1.getDate());
    }

    @Test
    void testEditInfo() {
        reminder1.edit("info", "new info");
        assertEquals("new info", reminder1.getInfo());
    }

    @Test
    void testEditTime() {
        LocalTime newTime = LocalTime.of(5, 0);
        reminder1.edit("time", newTime);
        assertEquals(newTime, reminder1.getTime());
    }

    @Test
    void testEditInvalidField() {
        try {
            reminder1.edit("invalid", "value");
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    void testEditWrongType() {
        try {
            reminder1.edit("date", "wrongType");
            fail("Expected ClassCastException to be thrown");
        } catch (ClassCastException e) {

        }
    }

    @Test
    void testEditNulls() {
        try {
            reminder1.edit(null, "hi");
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {

        }
        try {
            reminder1.edit("name", null);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    void testToString() {
        assertEquals("reminder1", reminder1.toString());
    }

    @Test
    void testDisplayWithInfo() {
        String expected = "reminder1\nTest Reminder\nDecember 23, 2025\n12:00";
        assertEquals(expected, reminder1.display());
    }

    @Test
    void testDisplayWithoutInfo() {
        String expected = "reminder2\nDecember 23, 2025\nAt 12:00";
        assertEquals(expected, reminder2.display());
    }

    @Test
    void testInvalidDateThrows() {
        LocalDate pastDate = LocalDate.of(2000, 1, 1);
        try {
            new Reminder(pastDate, "bad", "x", LocalTime.of(10, 0));
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    void testInvalidTimeThrows() {
        LocalDate today = LocalDate.now();
        LocalTime pastTime = LocalTime.now().minusMinutes(1);
        try {
            new Reminder(today, "bad", "x", pastTime);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    void testToJson() {
        JSONObject json = reminder1.toJson();
        assertEquals(LocalDate.of(2025, 12, 23).toString(), json.get("date"));
        assertEquals("reminder1", json.get("name"));
        assertEquals("Test Reminder", json.get("info"));
        assertEquals(LocalTime.of(12, 0).toString(), json.get("time"));
    }
}
