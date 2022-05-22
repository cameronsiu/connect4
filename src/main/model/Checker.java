package model;

// This class references code from github.com
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

import org.json.JSONObject;
import persistence.Writable;
import ui.GamePanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

// Represents a checker that the user drops into the Connect Four board
public class Checker implements Writable {

    private String color;
    private int row;
    private int column;
    private boolean onBoard;
    private Color graphicsColor;
    private EventLog eventLog;

    /*
     * EFFECTS: creates a checker, row is an integer that represents which list of checkers the checker is in,
     *          column is an integer that represents its position in the row list
     */
    public Checker(int row, int column) {
        this.row = row;
        this.column = column;
        onBoard = false;
        graphicsColor = GamePanel.BACKGROUND_COLOR;
        eventLog = EventLog.getInstance();
    }

    public String getColor() {
        return color;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public boolean getOnBoard() {
        return onBoard;
    }

    public Color getGraphicsColor() {
        return graphicsColor;
    }

    // REQUIRES: color.equals("Yellow") || color.equals("Red")
    // EFFECTS: sets the color of this checker and adds an event to the event log that the checker has placed.
    public void setColor(String color) {
        this.color = color;
        eventLog.logEvent(new Event("\t" + color + " checker added at row " + row + " and column "
                + column + "."));

    }

    public void setGraphicsColor(Color color) {
        graphicsColor = color;
    }

    // MODIFIES: this
    // EFFECTS: puts the checker on the board.
    public void putOnBoard() {
        onBoard = true;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("row", row);
        json.put("column", column);
        json.put("color", color);
        json.put("graphicsColor", Integer.toString(graphicsColor.getRGB()));
        json.put("onBoard", onBoard);
        return json;
    }
}
