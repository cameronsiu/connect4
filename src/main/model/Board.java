package model;

import model.exceptions.FullColumnException;
import model.exceptions.ImpossibleColumnException;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

// This class references code from github.com
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class Board {

    public static final int ROWS = 6;
    public static final int COLUMNS = 7;
    private List<List<Checker>> checkerList;

    // EFFECTS: Creates a Connect Four board
    public Board() {
        checkerList = new ArrayList<>();
        for (int i = 0; i < ROWS; i++) {
            checkerList.add(new ArrayList<>());
            for (int j = 0; j < COLUMNS; j++) {
                checkerList.get(i).add(new Checker(i, j));
            }
        }
    }

    public List<List<Checker>> getCheckerList() {
        return checkerList;
    }

    // MODIFIES: this
    // EFFECTS: adds a checker to the board
    public Checker placeChecker(int column) throws ImpossibleColumnException, FullColumnException {
        if (column > COLUMNS || column < 1) {
            throw new ImpossibleColumnException();
        } else {
            int columnIndex = column - 1;
            for (int i = ROWS - 1; i >= 0; i--) {
                Checker checker = checkerList.get(i).get(columnIndex);
                if (!checker.getOnBoard()) {
                    checker.putOnBoard();
                    return checker;
                }
            }
            throw new FullColumnException();
        }
    }

    // EFFECTS: returns checkers in this board as a JSON array
    public JSONArray checkerListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (List<Checker> row : checkerList) {
            for (Checker checker: row) {
                jsonArray.put(checker.toJson());
            }
        }
        return jsonArray;
    }

}
