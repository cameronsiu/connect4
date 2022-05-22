package ui;

import model.Board;
import model.ConnectFourGame;

import java.awt.*;


public class BoardDrawing {

    public static final Color COLOR = new Color(47, 215, 255);
    public static final int HEIGHT = 420;
    public static final int WIDTH = 490;
    public static final int CHECKER_H_SPACE = (WIDTH - (CheckerDrawing.WIDTH * Board.COLUMNS)) / (Board.COLUMNS + 1);
    public static final int CHECKER_V_SPACE = (HEIGHT - (CheckerDrawing.HEIGHT * Board.ROWS)) / (Board.ROWS + 1);
    // Coordinates of the top left board
    public static final int X_COORD = (ConnectFourGame.WIDTH - WIDTH) / 2;
    public static final int Y_COORD = 50;

    // EFFECTS: Creates a Connect Four board
    public BoardDrawing() {
    }

    //EFFECTS: draws the shape
    public void drawGraphics(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRoundRect(X_COORD - 3, Y_COORD - 3, WIDTH + 6, HEIGHT + 6 + CHECKER_V_SPACE,
                50, 50);
        g.setColor(COLOR);
        g.fillRoundRect(X_COORD, Y_COORD, WIDTH, HEIGHT + CHECKER_V_SPACE, 50, 50);

        // Draws the board stand
        g.setColor(Color.BLACK);
        g.fillRoundRect(100 - 3, Y_COORD + HEIGHT - 3, 600 + 6, 50 + 6, 50, 50);
        g.setColor(COLOR);
        g.fillRoundRect(100, Y_COORD + HEIGHT, 600, 50, 50, 50);
    }

}
