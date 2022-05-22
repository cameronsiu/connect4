package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CheckerTest {

    private Checker checker;

    @BeforeEach
    void setup() {
        checker = new Checker(1,1);
        checker.setColor("YELLOW");
        checker.setGraphicsColor(new Color(249, 255, 0));
    }

    @Test
    void testGetColor() {
        assertEquals("YELLOW", checker.getColor());
    }

    @Test
    void testGetGraphicsColor() {
        assertEquals(new Color(249, 255, 0), checker.getGraphicsColor());
    }

    @Test
    void testGetColumn() {
        assertEquals(1, checker.getColumn());
    }

    @Test
    void testGetRow() {
        assertEquals(1, checker.getRow());
    }

    @Test
    void testGetOnBoard() {
        assertFalse(checker.getOnBoard());
    }

    @Test
    void testSetColor() {
        checker.setColor("RED");
        assertEquals("RED", checker.getColor());
    }

    @Test
    void testPutOnBoard() {
        checker.putOnBoard();
        assertTrue(checker.getOnBoard());
    }

}
