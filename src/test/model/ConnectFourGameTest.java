package model;

import model.exceptions.FullColumnException;
import model.exceptions.ImpossibleColumnException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectFourGameTest {

    private ConnectFourGame game;
    private Board board;

    @BeforeEach
    void setup() {
        game = new ConnectFourGame();
        board = game.getBoard();
    }

    @Test
    void testGetPlayerTurn() {
        assertEquals("RED", game.getPlayerTurn());
    }

    @Test
    void testGetPlayerTurnColor() {
        assertEquals(new Color(255, 0, 0), game.getPlayerTurnColor());
    }

    @Test
    void testSetPlayerTurn() {
        game.setPlayerTurn("RED");
        assertEquals("RED", game.getPlayerTurn());
    }

    @Test
    void testGetBoard() {
        Board board = game.getBoard();
        List<List<Checker>> checkerList = board.getCheckerList();
        assertEquals(Board.ROWS, checkerList.size());

        for (int i = 0; i < Board.ROWS; i++) {
            assertEquals(Board.COLUMNS, checkerList.get(i).size());
        }
    }

    @Test
    void testGetWinningPlayer() {
        assertEquals(null, game.getWinningPlayer());
    }

    @Test
    void testGetGameOverMessage() {
        assertEquals(null, game.getGameOverMessage());
    }

    @Test
    void testGetQuitGame() {
        assertFalse(game.getQuitGame());
    }

    @Test
    void testSetQuitGame() {
        assertFalse(game.getQuitGame());
        game.setQuitGame(true);
        assertTrue(game.getQuitGame());
    }

    @Test
    void testChangeTurns() {
        game.changeTurns();
        assertEquals("YELLOW", game.getPlayerTurn());
        game.changeTurns();
        assertEquals("RED", game.getPlayerTurn());
    }

    @Test
    void testIsGameOver() {
       game.isGameOver();
       assertFalse(game.getGameOver());
    }

    @Test
    void testIsGameOverPlayerWins() {
        try {
            for (int i = 0; i<4; i++) {
                board.placeChecker(1).setColor("RED");
            }
            game.isGameOver();
            assertTrue(game.getGameOver());
        } catch (ImpossibleColumnException e) {
            fail("Caught ImpossibleColumnException");
        } catch (FullColumnException e) {
            fail("Caught FullColumnException");
        }
    }

    @Test
    void testIsGameOverTieGame() {
        try {
            for (int i = 1; i <= 7; i++) {
                if (i % 2 != 0) {
                    board.placeChecker(i).setColor("RED");
                    board.placeChecker(i).setColor("RED");
                    board.placeChecker(i).setColor("YELLOW");
                    board.placeChecker(i).setColor("RED");
                    board.placeChecker(i).setColor("YELLOW");
                    board.placeChecker(i).setColor("YELLOW");
                } else if (i == 2 || i == 6) {
                    board.placeChecker(i).setColor("YELLOW");
                    board.placeChecker(i).setColor("YELLOW");
                    board.placeChecker(i).setColor("RED");
                    board.placeChecker(i).setColor("YELLOW");
                    board.placeChecker(i).setColor("RED");
                    board.placeChecker(i).setColor("RED");
                } else {
                    for (int j = 0; j < 6; j++) {
                        if (j % 2 == 0) {
                            board.placeChecker(i).setColor("YELLOW");
                        } else {
                            board.placeChecker(i).setColor("RED");
                        }
                    }
                }
            }
            game.isGameOver();
            assertTrue(game.getGameOver());
            assertTrue(game.tieGame());
            assertTrue(game.fullBoard());
        } catch (ImpossibleColumnException e) {
            fail("Caught ImpossibleColumnException");
        } catch (FullColumnException e) {
            fail("Caught FullColumnException");
        }
    }

    @Test
    void testIsGameOverPlayerWinsVertical() {
        try {
            for (int i = 0; i<4; i++) {
                board.placeChecker(1).setColor("RED");
            }
            assertTrue(game.playerWins());
        } catch (ImpossibleColumnException e) {
            fail("Caught ImpossibleColumnException");
        } catch (FullColumnException e) {
            fail("Caught FullColumnException");
        }
    }

    @Test
    void testIsGameOverPlayerWinsHorizontal() {
        try {
            for (int i = 0; i < 4; i++) {
                board.placeChecker(i+1).setColor("RED");
            }
            assertTrue(game.playerWins());
        } catch (ImpossibleColumnException e) {
            fail("Caught ImpossibleColumnException");
        } catch (FullColumnException e) {
            fail("Caught FullColumnException");
        }
    }

    @Test
    void testIsGameOverPlayerWinsPositiveDiagonal() {
        try {
            for (int i = 4; i > 0; i--) {
                for (int j = 1; j < i; j++) {
                    board.placeChecker(j).setColor("RED");
                }
                board.placeChecker(i).setColor("YELLOW");
            }
            assertTrue(game.playerWins());
        } catch (ImpossibleColumnException e) {
            fail("Caught ImpossibleColumnException");
        } catch (FullColumnException e) {
            fail("Caught FullColumnException");
        }
    }

    @Test
    void testIsGameOverPlayerWinsNegativeDiagonal() {
        try {
            for (int i = 1; i < 5; i++) {
                for (int j = 1; j < i; j++) {
                    board.placeChecker(i).setColor("RED");
                }
                board.placeChecker(i).setColor("YELLOW");
            }
            assertTrue(game.playerWins());
        } catch (ImpossibleColumnException e) {
            fail("Caught ImpossibleColumnException");
        } catch (FullColumnException e) {
            fail("Caught FullColumnException");
        }
    }

    @Test
    void testConnectFourNoWinner() {
        Checker checker = new Checker(5,0);
        assertFalse(game.connectFour(checker));
    }

    @Test
    void testOutOfBounds() {
        Checker checker = new Checker(5,0);
        assertTrue(game.outOfBounds(checker, 0, 1));
    }

    @Test
    void testRestart() {
        try {
            game.getBoard().placeChecker(1);
            assertTrue(game.getBoard().getCheckerList().get(Board.ROWS-1).get(0).getOnBoard());
        } catch (ImpossibleColumnException e) {
            fail("Caught ImpossibleColumnException");
        } catch (FullColumnException e) {
            fail("Caught FullColumnException");
        }
        game.restart();
        assertFalse(game.getBoard().getCheckerList().get(0).get(0).getOnBoard());
    }




}
