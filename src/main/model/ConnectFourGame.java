package model;

import org.json.JSONObject;
import persistence.Writable;

import java.awt.*;
import java.util.List;

// Represents the connect four game that checks to see that the rules are applied.
public class ConnectFourGame implements Writable {

    public static final String PLAYER_ONE_COLOR = "RED";
    public static final String PLAYER_TWO_COLOR = "YELLOW";
    public static final Color PLAYER_ONE_GRAPHIC_COLOR = new Color(255, 0, 0);
    public static final Color PLAYER_TWO_GRAPHIC_COLOR = new Color(249, 255, 0);
    public static final int WIDTH = 800;
    public static final int HEIGHT = 700;
    private Board board;
    private String playerTurn;
    private Color playerTurnColor;
    private String winningPlayer;
    private String gameOverMessage;
    private boolean quitGame;
    private boolean gameOver;
    private EventLog eventLog;

    // EFFECTS: creates a new board with checkers, and player Blue goes first
    public ConnectFourGame() {
        board = new Board();
        playerTurn = PLAYER_ONE_COLOR;
        playerTurnColor = PLAYER_ONE_GRAPHIC_COLOR;
        quitGame = false;
        gameOver = false;
        eventLog = EventLog.getInstance();
    }

    public String getPlayerTurn() {
        return playerTurn;
    }

    public Color getPlayerTurnColor() {
        return playerTurnColor;
    }

    // REQUIRES: color.equals(PLAYER_ONE_COLOR) || color.equals(PLAYER_TWO_COLOR)
    // MODIFIES: this
    // EFFECTS: sets the player's turn
    public void setPlayerTurn(String color) {
        playerTurn = color;
    }

    // REQUIRES: color.equals(PLAYER_ONE_GRAPHIC_COLOR) || color.equals(PLAYER_TWO_GRAPHIC_COLOR)
    // MODIFIES: this
    // EFFECTS: sets the player's turn graphically
    public void setPlayerTurnColor(Color color) {
        playerTurnColor = color;
    }

    public Board getBoard() {
        return board;
    }

    public String getWinningPlayer() {
        return winningPlayer;
    }

    public void setWinningPlayer(String color) {
        winningPlayer = color;
    }

    public String getGameOverMessage() {
        return gameOverMessage;
    }

    public boolean getQuitGame() {
        return quitGame;
    }

    public void setQuitGame(boolean quitGame) {
        this.quitGame = quitGame;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    // MODIFIES: this
    // EFFECTS: changes turns of the players
    public void changeTurns() {
        if (playerTurn.equals(PLAYER_ONE_COLOR)) {
            playerTurn = PLAYER_TWO_COLOR;
            playerTurnColor = PLAYER_TWO_GRAPHIC_COLOR;
        } else {
            playerTurn = PLAYER_ONE_COLOR;
            playerTurnColor = PLAYER_ONE_GRAPHIC_COLOR;
        }
    }

    // MODIFIES: this
    // EFFECTS: if someone wins or there is nowhere else to place checkers and logs the event.
    public void isGameOver() {
        if (playerWins()) {
            gameOverMessage = getWinningPlayer() + " Wins!";
            gameOver = true;
            eventLog.logEvent(new Event("\t" + getWinningPlayer() + " wins the game."));
        } else if (tieGame()) {
            gameOverMessage = "Tie Game!";
            gameOver = true;
            eventLog.logEvent(new Event("\t Tie game."));

        }
    }

    // EFFECTS: returns true if a player win, otherwise false
    public boolean playerWins() {
        for (List<Checker> row: board.getCheckerList()) {
            for (Checker checker: row) {
                if (checker.getOnBoard()) {
                    if (connectFour(checker)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // EFFECTS: returns true if a checker is in a four consecutive row, otherwise false
    public boolean connectFour(Checker checker) {
        if (fourInARow(checker,  0, 1)) {
            return true;
        } else if (fourInARow(checker,  1, 0)) {
            return true;
        } else if (fourInARow(checker,  1, 1)) {
            return true;
        } else if (fourInARow(checker,  -1, 1)) {
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: returns true if the checker is in a four consecutive row, otherwise false
    public boolean fourInARow(Checker checker, int xdirection, int ydirection) {
        String color = checker.getColor();
        int row = checker.getRow();
        int column = checker.getColumn();

        for (int i = 1; i < 4; i++) {
            if (outOfBounds(checker, xdirection * i, ydirection * i)) {
                return false;
            }
            Checker nextChecker = board.getCheckerList().get(row + (ydirection * i)).get(column + (xdirection * i));
            if (!nextChecker.getOnBoard()) {
                return false;
            } else if (!nextChecker.getColor().equals(color)) {
                return false;
            }
        }
        setWinningPlayer(checker.getColor());
        return true;
    }

    // EFFECTS: returns true if the next checker to look at is out of bounds, otherwise false
    public boolean outOfBounds(Checker checker, int horizontal, int vertical) {
        int row = checker.getRow();
        int column = checker.getColumn();
        int nextRow = row + vertical;
        int nextColumn = column + horizontal;

        if (nextRow > Board.ROWS - 1) {
            return true;
        } else if (nextColumn < 0 || nextColumn > Board.COLUMNS - 1) {
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: returns true if board is full and no player wins, otherwise false
    public boolean tieGame() {
        return fullBoard();
    }

    // EFFECTS: returns true if all checkers are on the board, otherwise false
    public boolean fullBoard() {
        for (List<Checker> row: board.getCheckerList()) {
            for (Checker checker: row) {
                if (!checker.getOnBoard()) {
                    return false;
                }
            }
        }
        return true;
    }

    // MODIFIES: this
    // EFFECTS: restarts the game and logs the event
    public void restart() {
        board = new Board();
        playerTurn = PLAYER_ONE_COLOR;
        playerTurnColor = PLAYER_ONE_GRAPHIC_COLOR;
        quitGame = false;
        gameOver = false;
        eventLog.logEvent(new Event("\tRestarted game."));
    }

    // EFFECTS: adds playerTurn, playerTurnColor and board to json file
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("playerTurn", playerTurn);
        json.put("playerTurnColor", Integer.toString(playerTurnColor.getRGB()));
        json.put("board", board.checkerListToJson());
        return json;
    }

    // EFFECTS: prints out all events in the event log
    public void printLogs() {
        for (Event event: eventLog) {
            System.out.println(event.toString() + "\n");
        }
    }

}
