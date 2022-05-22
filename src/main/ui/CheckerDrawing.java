package ui;

import model.Board;
import model.ConnectFourGame;

import java.awt.*;

// Represents a checker drawing that the user drops into the Connect Four board
public class CheckerDrawing {

    public static final int WIDTH = (int) (0.6 * (BoardDrawing.WIDTH / Board.COLUMNS));
    public static final int HEIGHT = (int) (0.6 * (BoardDrawing.WIDTH / Board.COLUMNS));
    public static final int BORDER_THICKNESS = 6;

    // Graphics Info
    private int xcoord;
    private int ycoord;
    private int width;
    private int height;
    private int column;
    private int row;

    private int finalYCoord;
    private ConnectFourGame game;

    /*
     * EFFECTS: creates a checker drawing
     *          x is the x-coordinate of the checker, y is the y-coordinate
     *          w is the width, h is the height,
     *          row is an integer that represents which list of checkers the checker is in,
     *          column is an integer that represents its position in the row list,
     *          and game is the
     */
    public CheckerDrawing(int x, int y, int w, int h, int row, int column, ConnectFourGame game) {
        this.xcoord = x;
        this.ycoord = BoardDrawing.Y_COORD - (BoardDrawing.CHECKER_V_SPACE * 2);
        width = w;
        height = h;
        this.row = row;
        this.column = column;
        this.game = game;
        finalYCoord = y;
    }

    //EFFECTS: draws the shape
    public void drawGraphics(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval(xcoord - (BORDER_THICKNESS / 2), finalYCoord - (BORDER_THICKNESS / 2),
                width + BORDER_THICKNESS, height + BORDER_THICKNESS);
    }

    //EFFECTS: fills the shape
    public void fillGraphics(Graphics g) {
        g.setColor(game.getBoard().getCheckerList().get(row).get(column).getGraphicsColor());
        g.fillOval(xcoord, ycoord, width, height);
    }

    public void drop() {
        if (ycoord < finalYCoord) {
            ycoord += finalYCoord / Board.ROWS;
        }
    }

    public void reset() {
        ycoord = BoardDrawing.Y_COORD - (BoardDrawing.CHECKER_V_SPACE * 2);
    }

    public boolean getOnBoard() {
        return game.getBoard().getCheckerList().get(row).get(column).getOnBoard();
    }
}
