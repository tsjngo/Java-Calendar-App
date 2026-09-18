package model;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

public class CalendarTest {
    private Calendar calendar;
    private Reminder reminder;

    @BeforeEach
    void runBefore() {
        calendar = new Calendar("Test Calendar");
        reminder = new Reminder(LocalDate.of(2025, 12, 23), "reminder", LocalTime.of(12, 0));
    }

    @Test
    void testConstructor() {
        assertEquals("Test Calendar", calendar.getName());
        assertTrue(calendar.getTasks().isEmpty());
    }

    @Test
    void testGetToday() {
        assertEquals(LocalDate.now(), calendar.getToday());
    }

    @Test
    void testGetCurrentTime() {
        LocalTime before = LocalTime.now();
        LocalTime current = calendar.getCurrentTime();
        LocalTime after = LocalTime.now();
        assertTrue(!current.isBefore(before) && !current.isAfter(after));
    }

    @Test
    void testSetName() {
        assertEquals("Test Calendar", calendar.getName());
        calendar.setName("New Calendar");
        assertEquals("New Calendar", calendar.getName());
    }

    @Test
    void testAddTask() {
        calendar.addTask(reminder);
        assertEquals(1, calendar.getTasks().size());
        assertEquals(reminder, calendar.getTasks().get(0));
    }

    @Test
    void testAddTaskNull() {
        try {
            calendar.addTask(null);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    void testRemoveTask() {
        calendar.addTask(reminder);
        calendar.removeTask(0);
        assertTrue(calendar.getTasks().isEmpty());
    }

    @Test
    void testRemoveTaskInvalidIndex() {
        try {
            calendar.removeTask(5);
            fail("Expected IndexOutOfBoundsException to be thrown");
        } catch (IndexOutOfBoundsException e) {

        }
    }

    @Test
    void testViewTask() {
        calendar.addTask(reminder);
        assertEquals(reminder, calendar.viewTask(0));
    }

    @Test
    void testViewTaskInvalidIndex() {
        try {
            calendar.viewTask(1);
            fail("Expected IndexOutOfBoundsException to be thrown");
        } catch (IndexOutOfBoundsException e) {

        }
    }

    @Test
    void testEditTaskName() {
        calendar.addTask(reminder);
        calendar.editTask(reminder, "name", "new name");
        assertEquals("new name", reminder.getName());
    }

    @Test
    void testEditTaskDate() {
        calendar.addTask(reminder);
        LocalDate newDate = LocalDate.of(2025, 12, 25);
        calendar.editTask(reminder, "date", newDate);
        assertEquals(newDate, reminder.getDate());
    }

    @Test
    void testEditTaskTime() {
        calendar.addTask(reminder);
        LocalTime newTime = LocalTime.of(10, 30);
        calendar.editTask(reminder, "time", newTime);
        assertEquals(newTime, reminder.getTime());
    }

    @Test
    void testEditTaskInfo() {
        calendar.addTask(reminder);
        calendar.editTask(reminder, "info", "updated info");
        assertEquals("updated info", reminder.getInfo());
    }

    @Test
    void testEditTaskInvalidField() {
        calendar.addTask(reminder);
        try {
            calendar.editTask(reminder, "invalid", "wrong");
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    void testEditTaskNullInputs() {
        calendar.addTask(reminder);
        try {
            calendar.editTask(reminder, null, "hi");
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {

        }
        try {
            calendar.editTask(reminder, "name", null);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    void testToString() {
        assertEquals("Test Calendar", calendar.toString());
    }

    @Test
    void testToJSON() {
        calendar.addTask(reminder);
        JSONObject json = calendar.toJson();
        assertEquals("Test Calendar", json.get("name"));
    }
    
    @Test
    void testTaskToJson() {
        calendar.addTask(reminder);
        JSONObject json = calendar.toJson();
        JSONArray jsonTasks = json.getJSONArray("tasks");
        assertEquals(jsonTasks, json.get("tasks"));
    }
}
