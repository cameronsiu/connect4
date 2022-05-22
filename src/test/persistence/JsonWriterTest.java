package persistence;

import model.Checker;
import model.ConnectFourGame;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// This class references code from github.com
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            ConnectFourGame game = new ConnectFourGame();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testWriterNewGame() {
        try {
            ConnectFourGame game = new ConnectFourGame();
            JsonWriter writer = new JsonWriter("./data/testWriterNewGame.json");
            writer.open();
            writer.write(game);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNewGame.json");
            game = reader.read();
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
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGameFewTurns() {
        try {
            ConnectFourGame game = new ConnectFourGame();
            game.setPlayerTurn("RED");

            List<List<Checker>> checkerList = game.getBoard().getCheckerList();
            Checker checker1 = checkerList.get(5).get(3);
            checker1.setColor("YELLOW");
            checker1.putOnBoard();
            Checker checker2 = checkerList.get(5).get(4);
            checker2.setColor("RED");
            checker2.putOnBoard();
            Checker checker3 = checkerList.get(5).get(5);
            checker3.setColor("YELLOW");
            checker3.putOnBoard();

            JsonWriter writer = new JsonWriter("./data/testWriterGameFewTurns.json");
            writer.open();
            writer.write(game);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGameFewTurns.json");
            game = reader.read();
            checkerList = game.getBoard().getCheckerList();
            assertEquals("RED", game.getPlayerTurn());
            checkChecker(5,3, "YELLOW", true, checkerList.get(5).get(3));
            checkChecker(5,4, "RED", true, checkerList.get(5).get(4));
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
            fail("Exception should not have been thrown");
        }
    }
}