package model;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalendarListTest {
    private CalendarList cl;
    private Calendar c1;
    private Calendar c2;

    @BeforeEach
    void runBefore() {
        cl = new CalendarList();
        c1 = new Calendar("c1");
        c2 = new Calendar("c2");
    }

    @Test
    void testAddCalendar() {
        cl.addCalendar(c1);
        assertEquals(1, cl.getCalendarsSize());

        cl.addCalendar(c2);
        assertEquals(2, cl.getCalendarsSize());
    }

    @Test
    void testGetCalendarsSize() {
        assertEquals(0, cl.getCalendarsSize());

        cl.addCalendar(c1);
        assertEquals(1, cl.getCalendarsSize());

        cl.addCalendar(c2);
        assertEquals(2, cl.getCalendarsSize());
    }

    @Test
    void testGetCalendarValidIndex() {
        cl.addCalendar(c1);
        cl.addCalendar(c2);

        assertSame(c1, cl.getCalendar(0));
        assertSame(c2, cl.getCalendar(1));
    }

    @Test
    void testGetCalendarInvalidIndex() {
        cl.addCalendar(c1);
        try {
            cl.getCalendar(1);
            fail("Expected IndexOutOfBoundsException to be thrown");
        } catch (IndexOutOfBoundsException e) {
            assertEquals("2 is not valid.", e.getMessage());
        }
    }

    @Test
    void testRemoveCalendarValidIndex() {
        cl.addCalendar(c1);
        cl.addCalendar(c2);

        cl.removeCalendar(0);
        assertEquals(1, cl.getCalendarsSize());
        assertSame(c2, cl.getCalendar(0));
    }

    @Test
    void testRemoveCalendarInvalidIndex() {
        cl.addCalendar(c1);
        try {
            cl.removeCalendar(1);
            fail("Expected IndexOutOfBoundsException to be thrown");
        } catch (IndexOutOfBoundsException e) {
            assertEquals("2 is not valid.", e.getMessage());
        }
    }

    @Test
    void testGetCalendars() {
        assertTrue(cl.getCalendars().isEmpty());

        cl.addCalendar(c1);
        cl.addCalendar(c2);

        assertEquals(2, cl.getCalendars().size());
        assertTrue(cl.getCalendars().contains(c1));
        assertTrue(cl.getCalendars().contains(c2));
    }

    @Test
    void testToJSON() {
        cl.addCalendar(c1);
        cl.addCalendar(c2);
        JSONObject json = cl.toJson();
        JSONArray jsonArray = json.getJSONArray("calendars");
        assertEquals(jsonArray, json.get("calendars"));
    }

    @Test
    void testCalendarToJson() {
        cl.addCalendar(c1);
        cl.addCalendar(c2);
        JSONObject json = cl.toJson();
        JSONArray jsonArray = json.getJSONArray("calendars");
        assertEquals(jsonArray, json.get("calendars"));
    }
}
