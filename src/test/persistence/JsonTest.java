package persistence;

import model.Reminder;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkTask(String name, String info, String date, String time, Reminder task) {
        assertEquals(name, task.getName());
        assertEquals(info, task.getInfo());
        assertEquals(date, task.getDate().toString());
        assertEquals(time, task.getTime().toString());
    }

    protected void checkTask(String name, String date, String time, Reminder task) {
        assertEquals(name, task.getName());
        assertEquals("", task.getInfo());
        assertEquals(date, task.getDate().toString());
        assertEquals(time, task.getTime().toString());
    }
}
