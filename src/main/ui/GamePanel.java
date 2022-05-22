package ui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Board;
import model.Checker;
import model.ConnectFourGame;
import model.exceptions.FullColumnException;
import model.exceptions.ImpossibleColumnException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

// This class references code from GitHub
// Link: https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase

/*
 *     The Connect 4 game panel
 */
public class GamePanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener {

    public static final int INTERVAL = 5;
    public static final Color BACKGROUND_COLOR = new Color(0,0,0);
    private static final String JSON_STORE = "./data/game.json";
    private ConnectFourGame game;
    private BoardDrawing board;
    private List<ColumnDrawing> columns;
    private List<List<CheckerDrawing>> checkerLabels;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JLabel playerTurn;

    // Constructs a game panel
    // EFFECTS: sets the size and background color
    //          updates the game
    public GamePanel(ConnectFourGame game) {
        setPreferredSize(new Dimension(ConnectFourGame.WIDTH, ConnectFourGame.HEIGHT));
        setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout());
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        addMouseListener(this);
        addMouseMotionListener(this);
        this.game = game;
        board = new BoardDrawing();
        Border border = BorderFactory.createLineBorder(Color.DARK_GRAY, 5);
        setBorder(border);
        createColumns();
        createCheckers();
//        add(Box.createRigidArea(new Dimension(400,200)), JLabel.CENTER);
        createPlayerTurn();
        createButtonPanel();
        addTimer();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        board.drawGraphics(g);
        for (ColumnDrawing column: columns) {
            column.fillGraphics(g);
        }
        for (List<CheckerDrawing> row: checkerLabels) {
            for (CheckerDrawing checker: row) {
                checker.drawGraphics(g);
                checker.fillGraphics(g);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: creates the player turn display
    public void createPlayerTurn() {
        playerTurn = new JLabel();
        playerTurn.setFont(new Font("Arial", Font.PLAIN, 40));
        setPlayerTurn();
        playerTurn.setHorizontalAlignment(JLabel.CENTER);
        playerTurn.setVerticalAlignment(JLabel.BOTTOM);
        add(playerTurn, BorderLayout.CENTER);
        //Border border = BorderFactory.createLineBorder(Color.BLUE, 5);
        //playerTurn.setBorder(border);
    }

    // MODIFIES: this
    // EFFECTS: changes the player's turn text
    public void setPlayerTurn() {
        playerTurn.setText(game.getPlayerTurn() + "'s Turn!");
        playerTurn.setForeground(game.getPlayerTurnColor());
    }

    // MODIFIES: this
    // EFFECTS: creates all column drawings
    public void createColumns() {
        columns = new ArrayList<>();
        for (int j = 0; j < Board.COLUMNS; j++) {
            columns.add(new ColumnDrawing(BoardDrawing.X_COORD + (BoardDrawing.CHECKER_H_SPACE / 2)
                    + (CheckerDrawing.WIDTH + BoardDrawing.CHECKER_H_SPACE) * j,
                    BoardDrawing.Y_COORD + (BoardDrawing.CHECKER_V_SPACE / 2),
                    CheckerDrawing.WIDTH + BoardDrawing.CHECKER_H_SPACE,
                    BoardDrawing.HEIGHT - BoardDrawing.CHECKER_V_SPACE, j));
        }
    }

    // MODIFIES: this
    // EFFECTS: creates all checker drawings
    public void createCheckers() {
        checkerLabels = new ArrayList<>();
        for (int i = 0; i < Board.ROWS; i++) {
            checkerLabels.add(new ArrayList<>());
            for (int j = 0; j < Board.COLUMNS; j++) {
                checkerLabels.get(i).add(new CheckerDrawing(BoardDrawing.X_COORD
                        + (BoardDrawing.CHECKER_H_SPACE * (j + 1))
                        + (CheckerDrawing.WIDTH * j),
                        BoardDrawing.Y_COORD + (BoardDrawing.CHECKER_V_SPACE * (i + 1)) + (CheckerDrawing.HEIGHT * i),
                        CheckerDrawing.WIDTH, CheckerDrawing.HEIGHT, i, j,
                        game));
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a panel for the quit and restart buttons
    public void createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JButton quit = new JButton("Quit");
        quit.setActionCommand("quit");
        quit.addActionListener(this);

        JButton restart = new JButton("Restart");
        restart.setActionCommand("restart");
        restart.addActionListener(this);

        JButton load = new JButton("Load");
        load.setActionCommand("load");
        load.addActionListener(this);

        buttonPanel.add(quit);
        buttonPanel.add(restart);
        buttonPanel.add(load);
        buttonPanel.setBackground(Color.gray);
        add(buttonPanel, BorderLayout.PAGE_END);

    }

    // MODIFIES: this, game
    // EFFECTS: if quit button is pressed, asks user if they want to save or not. Then prints all events.
    //          if restart is pressed, the game will restart
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("quit".equals(e.getActionCommand())) {
            int input = JOptionPane.showConfirmDialog(null, "Do you want to save?");
            if (input == 1) {
                game.printLogs();
                System.exit(0);
            } else if (input == 0) {
                saveGame();
                game.printLogs();
                System.exit(0);
            }
        } else if ("restart".equals(e.getActionCommand())) {
            game.restart();
            setPlayerTurn();
            saveGame();
            repaint();
        } else if ("load".equals(e.getActionCommand())) {
            loadGame();
            setPlayerTurn();
            repaint();
        }
    }

    // MODIFIES: this, column
    // EFFECTS: hovering over a column will change the color of the column to tell
    // the user that they can press on the column
    @Override
    public void mouseMoved(MouseEvent e) {
        for (ColumnDrawing column: columns) {
            if (mouseInBoundary(e, column)) {
                column.setColor(ColumnDrawing.HOVER_COLOR);
                repaint();
            } else {
                column.setColor(BoardDrawing.COLOR);
                repaint();
            }
        }
    }

    // MODIFIES: this, game, board, checker, checkerdrawing
    // EFFECTS: drops the checker when the user clicks on a column
    @Override
    public void mouseClicked(MouseEvent e) {
        for (ColumnDrawing column: columns) {
            if (mouseInBoundary(e, column)) {
                dropChecker(column);
            }
        }
    }

    // EFFECTS: returns true if the mouse is within a column
    public boolean mouseInBoundary(MouseEvent e, ColumnDrawing column) {
        return e.getX() >= column.getXcoord() && e.getX() <= column.getXcoord() + column.getWidth()
                    && e.getY() >= column.getYcoord() && e.getY() <= column.getYcoord() + column.getHeight();
    }

    // MODIFIES: this, game, board, checker, checkerdrawing
    // EFFECTS: drops the checker in the column, repaints, and checks to see if the game is over
    public void dropChecker(ColumnDrawing column) {
        if (!game.getGameOver()) {
            try {
                Checker checker = game.getBoard().placeChecker(column.getColumnNum() + 1);
                checker.setColor(game.getPlayerTurn());
                checker.setGraphicsColor(game.getPlayerTurnColor());
                game.changeTurns();
                setPlayerTurn();
                game.isGameOver();
            } catch (ImpossibleColumnException err) {
                System.out.println("Impossible Column!");
            } catch (FullColumnException err) {
                System.out.println("Full Column!");
            }
            repaint();
            isGameOver();
        }
    }

    // MODIFIES: this, game, board, checker
    // EFFECTS: if the game is over
    public void isGameOver() {
        if (game.getGameOver()) {
            playerTurn.setText("GAME OVER!");
            playerTurn.setForeground(Color.WHITE);
            int input = JOptionPane.showConfirmDialog(null,
                    game.getGameOverMessage() + " Do you want to play again?",
                    "Select an option...",
                    JOptionPane.YES_NO_OPTION);
            if (input == 0) {
                game.restart();
                restartCheckers();
                saveGame();
                setPlayerTurn();
                repaint();
            } else if (input == 1) {
                game.restart();
                restartCheckers();
                saveGame();
                game.printLogs();
                System.exit(0);
            }
        }
    }

    // EFFECTS: saves the game to file
    private void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
            System.out.println("Saved game to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads game from file
    private void loadGame() {
        try {
            game = jsonReader.read();
            System.out.println("Loaded previous game from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // Set up timer
    // modifies: none
    // effects:  initializes a timer that updates game each
    //           INTERVAL milliseconds
    private void addTimer() {
        Timer t = new Timer(INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                update();
                repaint();
            }
        });
        t.start();
    }

    public void update() {
        for (List<CheckerDrawing> row: checkerLabels) {
            for (CheckerDrawing checkerDrawing: row) {
                if (checkerDrawing.getOnBoard()) {
                    checkerDrawing.drop();
                }
            }
        }
    }

    public void restartCheckers() {
        for (List<CheckerDrawing> row: checkerLabels) {
            for (CheckerDrawing checkerDrawing: row) {
                checkerDrawing.reset();
            }
        }
    }

    // Not used methods
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
}