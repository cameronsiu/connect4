package persistence;


import model.Checker;

import static org.junit.jupiter.api.Assertions.assertEquals;

// This class references code from github.com
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonTest {

    protected void checkChecker(int row, int column, String color, boolean onBoard, Checker checker) {
        assertEquals(row, checker.getRow());
        assertEquals(column, checker.getColumn());
        assertEquals(color, checker.getColor());
        assertEquals(onBoard, checker.getOnBoard());
    }
}