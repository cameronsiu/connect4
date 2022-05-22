package ui;

import java.awt.*;

/*
 *     Board column drawing
 */
public class ColumnDrawing {

    public static final Color HOVER_COLOR = new Color(5, 77, 117);

    private int xcoord;
    private int ycoord;
    private int width;
    private int height;
    private Color color;
    private int columnNum;

    /*
     * EFFECTS: creates a column drawing
     *          x is the x-coordinate, y is the y-coordinate
     *          w is the width, h is the height,
     *          colNum is the column number
     */
    public ColumnDrawing(int x, int y, int w, int h, int colNum) {
        xcoord = x;
        ycoord = y;
        width = w;
        height = h;
        color = BoardDrawing.COLOR;
        columnNum = colNum;
    }

    public int getXcoord() {
        return xcoord;
    }

    public int getYcoord() {
        return ycoord;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getColumnNum() {
        return columnNum;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    //EFFECTS: fills the shape
    public void fillGraphics(Graphics g) {
        g.setColor(color);
        g.fillRoundRect(xcoord, ycoord, width, height,25,25);
    }

}
