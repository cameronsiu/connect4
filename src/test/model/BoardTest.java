package model;

import model.exceptions.FullColumnException;
import model.exceptions.ImpossibleColumnException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;

    @BeforeEach
    void setup() {
        board = new Board();
    }

    @Test
    void testGetBoard() {
        List<List<Checker>> checkerList = board.getCheckerList();
        assertEquals(Board.ROWS, checkerList.size());

        for (int i = 0; i < Board.ROWS; i++) {
            assertEquals(Board.COLUMNS, checkerList.get(i).size());
        }
    }

    @Test
    void testPlaceChecker() {
        try {
            Checker checker1 = board.placeChecker(7);
            assertTrue(checker1.getOnBoard());
            Checker checker2 = board.placeChecker(1);
            assertTrue(checker2.getOnBoard());
            // expected
        } catch (ImpossibleColumnException e) {
            fail("Caught ImpossibleColumnException");
        } catch (FullColumnException e) {
            fail("Caught FullColumnException");
        }
    }

    @Test
    void testPlaceCheckerOutOfBoundsRightSide() {
        try {
            board.placeChecker(8);
            fail("ImpossibleColumnException was not thrown");
        } catch (ImpossibleColumnException e) {
            // expected
        } catch (FullColumnException e) {
            fail("FullColumnException Caught - ImpossibleColumnException was not thrown");
        }
    }

    @Test
    void testPlaceCheckerOutOfBoundsLeftSide() {
        try {
            board.placeChecker(0);
            fail("ImpossibleColumnException was not thrown");
        } catch (ImpossibleColumnException e) {
            // expected
        } catch (FullColumnException e) {
            fail("FullColumnException Caught - ImpossibleColumnException was not thrown");
        }
    }

    @Test
    void testPlaceCheckerFullColumn() {
        try {
            for (int i = 0; i < 7; i++) {
                board.placeChecker(1);
            }
            board.placeChecker(1);
            fail("FullColumnException was not thrown");
        } catch (ImpossibleColumnException e) {
            fail("ImpossibleColumnException Caught - FullColumnException was not thrown");
        } catch (FullColumnException e) {
            // expected
        }

    }

}