package ui;

import model.ConnectFourGame;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

// This class references code from GitHub
// Link: https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase

/*
 *     The Connect 4 game frame
 */
public class Connect4 extends JFrame {

    private static final String JSON_STORE = "./data/game.json";

    private ConnectFourGame game;
    private GamePanel gamePanel;
    private BufferedImage appIcon;
    private JsonReader jsonReader;

    // EFFECTS: creates the main application frame
    public Connect4() {
        super("Play Connect 4!");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(ConnectFourGame.WIDTH, ConnectFourGame.HEIGHT));
        setResizable(false);
        try {
            appIcon = ImageIO.read(getClass().getResource("images/Connect4Icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setIconImage(appIcon);
        jsonReader = new JsonReader(JSON_STORE);
        loadGame();
        gamePanel = new GamePanel(game);
        add(gamePanel);
        pack();
        centreOnScreen();
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                game.printLogs();
                System.exit(0);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
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

    public static void main(String[] args) {
        new Connect4();
    }

}
