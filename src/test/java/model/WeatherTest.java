package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.bot.tree.*;

class WeatherTest {

    Weather w = new Weather(true);

    @Test
    void northTranslation() {
        assertEquals("N", w.degreesToDirection(0));
        assertEquals("N", w.degreesToDirection(22.4));
        assertNotEquals("N", w.degreesToDirection(22.5));
        assertEquals("N", w.degreesToDirection(360));
        assertEquals("N", w.degreesToDirection(360-22.5));
        assertNotEquals("N", w.degreesToDirection(360-22.6));
    }

    @Test
    void southTranslation() {
        assertEquals("S", w.degreesToDirection(180));
    }

    @Test
    void eastTranslation() {
        assertEquals("E", w.degreesToDirection(90));
        assertEquals("NE", w.degreesToDirection(45));
    }
}

