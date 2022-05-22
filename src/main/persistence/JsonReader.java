package persistence;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Board;
import model.Checker;
import model.ConnectFourGame;
import org.json.*;

// This class references code from GitHub
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads board from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ConnectFourGame read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseConnectFourGame(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses board from JSON object and returns it
    private ConnectFourGame parseConnectFourGame(JSONObject jsonObject) {
        ConnectFourGame game = new ConnectFourGame();
        String playerTurn = jsonObject.getString("playerTurn");
        game.setPlayerTurn(playerTurn);
        Color playerTurnColor = new Color(Integer.parseInt(jsonObject.getString("playerTurnColor")));
        game.setPlayerTurnColor(playerTurnColor);
        Board board = game.getBoard();
        loadBoard(board, jsonObject);
        return game;
    }

    // MODIFIES: board
    // EFFECTS: parses board from JSON object and adds modifies each checker in board
    private void loadBoard(Board board, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("board");
        for (Object json : jsonArray) {
            JSONObject nextChecker = (JSONObject) json;
            loadChecker(board, nextChecker);
        }
    }

    // MODIFIES: board
    // EFFECTS: parses checker from JSON object and modifies it on board
    private void loadChecker(Board board, JSONObject jsonObject) {
        int row = jsonObject.getInt("row");
        int column = jsonObject.getInt("column");
        Checker checker = board.getCheckerList().get(row).get(column);

        boolean onBoard = jsonObject.getBoolean("onBoard");
        if (onBoard) {
            checker.putOnBoard();
            String color = jsonObject.getString("color");
            Color graphicsColor = new Color(Integer.parseInt(jsonObject.getString("graphicsColor")));
            checker.setColor(color);
            checker.setGraphicsColor(graphicsColor);
        }
    }
}
