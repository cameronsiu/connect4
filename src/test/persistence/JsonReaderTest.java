package persistence;

import model.Checker;
import model.ConnectFourGame;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// This class references code from github.com
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ConnectFourGame game = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testReaderNewGame() {
        JsonReader reader = new JsonReader("./data/testReaderNewGame.json");
        try {
            ConnectFourGame game = reader.read();
            assertEquals("RED", game.getPlayerTurn());
            int numOnBoard = 0;
            for (List<Checker> row: game.getBoard().getCheckerList()) {
                for (Checker checker: row) {
                    if (checker.getOnBoard()) {
                        numOnBoard++;
                    }
                }
            }
            assertEquals(0, numOnBoard);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGameFewTurns() {
        JsonReader reader = new JsonReader("./data/testReaderGameFewTurns.json");
        try {
            ConnectFourGame game = reader.read();
            assertEquals("YELLOW", game.getPlayerTurn());
            List<List<Checker>> checkerList = game.getBoard().getCheckerList();
            checkChecker(4,6, "RED", true, checkerList.get(4).get(6));
            checkChecker(5,6, "RED", true, checkerList.get(5).get(6));
            checkChecker(5,5, "YELLOW", true, checkerList.get(5).get(5));

            int numOnBoard = 0;
            for (List<Checker> row: game.getBoard().getCheckerList()) {
                for (Checker checker: row) {
                    if (checker.getOnBoard()) {
                        numOnBoard++;
                    }
                }
            }
            assertEquals(3, numOnBoard);

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}