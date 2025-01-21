package view;

import controller.GameController;
import model.CommandCenter;
import model.Movable;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * GamePanel is the graphical interface of the game that renders the game screen, handles user input,
 * and updates the display. It includes the introduction screen and the main game screen.
 * The panel handles drawing the game components and managing game states (such as playing, intro screen, etc.).
 */
public class GamePanel extends Panel {

    public final static int SCREEN_WIDTH = 1200;
    public final static int SCREEN_HEIGHT = 800;
    public final static Dimension SCREEN_SIZE = new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);
    private GameFrame gameFrame;
    public static Font fnt = new Font("Times", Font.BOLD, 20);  // set font size.
    public static Font fntBig = new Font("Times", Font.BOLD + Font.ITALIC, 36);
    private int nFontWidth;
    private int nFontHeight;
    private FontMetrics fmt;

    // The following "off" vars are used for the off-screen double-buffered image.
    private Dimension dimOff;
    private Image imgOff;
    private Graphics grpOff;

    /**
     * Constructor for the GamePanel class.
     * Initializes the game frame, sets up the game view, and adds key listeners for user input.
     */
    public GamePanel() {
        System.out.println("Create GamePanel.");

        gameFrame = new GameFrame();
        gameFrame.getContentPane().add(this);
        gameFrame.pack();
        initView();

        gameFrame.setSize(SCREEN_SIZE);
        gameFrame.setTitle("Plant vs. Zombie");
        gameFrame.setVisible(true);
        gameFrame.setResizable(false);
        this.setFocusable(true); // Ensure the panel can receive focus
        this.requestFocus(); // Request focus to ensure keyboard input is captured

        // Add KeyListener for handling intro screen interaction
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!CommandCenter.isPlaying() && e.getKeyCode() == KeyEvent.VK_S) {
                    System.out.println("S key pressed, starting the game...");
                    CommandCenter.setPlaying(true); // Exit intro screen and start the game
                    repaint(); // Trigger repaint to update the screen
                }
            }
        });
    }

    /**
     * Paints the game panel by updating the graphics context.
     * It delegates the painting process to the `update` method.
     * @param g the Graphics object to paint the panel.
     */
    @Override
    public void paint(Graphics g) {
        update(g);
    }

    /**
     * Updates the game screen by rendering the current state.
     * This method handles the double-buffering process and draws either the intro screen
     * or the game screen depending on the game state.
     * @param g the Graphics object used to draw the panel.
     */
    public void update(Graphics g) {
        if (grpOff == null || SCREEN_SIZE.width != dimOff.width
                || SCREEN_SIZE.height != dimOff.height) {
            dimOff = SCREEN_SIZE;
            imgOff = createImage(SCREEN_SIZE.width, SCREEN_SIZE.height);
            grpOff = imgOff.getGraphics();
        }
        grpOff.setColor(Color.black);
        grpOff.fillRect(0, 0, SCREEN_SIZE.width, SCREEN_SIZE.height);

        if (CommandCenter.isGameOver()) {
            CommandCenter.setPlaying(false);
        }

        if (!CommandCenter.isPlaying()) {
            // Intro screen
            drawIntroScreen(grpOff);
        } else {
            // Game screen
            drawGameScreen(grpOff);
        }

        g.drawImage(imgOff, 0, 0, this);
    }

    /**
     * Draws the main game screen, including game objects, score, sun credits, and other necessary details.
     * @param g the Graphics object used to draw the game screen.
     */
    private void drawGameScreen(Graphics g) {
        grpOff.setColor(new Color(66, 72, 70));
        grpOff.fillRect(0, SCREEN_SIZE.height - 200, SCREEN_SIZE.width, 200);

        grpOff.setColor(Color.white);
        grpOff.setFont(fnt);
        grpOff.drawString(CommandCenter.gameGuide, 590, SCREEN_SIZE.height - 150);

        iterateMovables(grpOff,
                CommandCenter.movPlants,
                CommandCenter.movBullets,
                CommandCenter.movFoes,
                CommandCenter.movDebris,
                CommandCenter.movCandidate,
                CommandCenter.movTemp,
                CommandCenter.movSun);

        drawSunCredit(grpOff); // Display current sun credits
        drawScore(grpOff); // Display current score
    }

    /**
     * Draws the introductory screen that includes game instructions and key commands.
     * @param g the Graphics object used to draw the intro screen.
     */
    private void drawIntroScreen(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("PLANTS VS ZOMBIES", 300, 200);

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("INSTRUCTION", 500, 300);
        g.drawString("A mob of zombies is about to invade your home.", 300, 350);
        g.drawString("Use the mouse to collect suns.", 300, 400);
        g.drawString("Use suns to plant peashooters for defense.", 300, 450);
        g.drawString("'S' to Start", 300, 500);
        g.drawString("'P' to Pause", 300, 550);
        g.drawString("'Q' to Quit", 300, 600);

        drawIntroIcons(g);
    }

    /**
     * Draws the icons for plants, suns, and zombies on the intro screen.
     * These icons visually represent the game elements for the player.
     * @param g the Graphics object used to draw the icons.
     */
    private void drawIntroIcons(Graphics g) {
        // Draw regular peashooter (green circle)
        g.setColor(Color.green);
        g.fillOval(150, SCREEN_HEIGHT - 150, 50, 50);

        // Draw ice peashooter (cyan circle)
        g.setColor(Color.cyan);
        g.fillOval(400, SCREEN_HEIGHT - 150, 50, 50);

        // Draw sun (yellow circle)
        g.setColor(Color.yellow);
        g.fillOval(650, SCREEN_HEIGHT - 150, 50, 50);

        // Draw red zombie (red rectangle)
        g.setColor(Color.red);
        g.fillRect(850, SCREEN_HEIGHT - 150, 50, 50);

        // Draw orange zombie (orange rectangle)
        g.setColor(Color.orange);
        g.fillRect(950, SCREEN_HEIGHT - 150, 50, 50);
    }

    /**
     * Draws the current score on the game screen.
     * @param g the Graphics object used to draw the score.
     */
    private void drawScore(Graphics g) {
        g.setColor(Color.white);
        g.setFont(fnt);
        if (CommandCenter.getScore() != 0) {
            g.drawString("SCORE :  " + CommandCenter.getScore(), nFontWidth, nFontHeight);
        } else {
            g.drawString("NO SCORE", nFontWidth, nFontHeight);
        }
    }

    /**
     * Draws the current sun credits on the game screen.
     * @param g the Graphics object used to draw the sun credits.
     */
    private void drawSunCredit(Graphics g) {
        g.setColor(Color.white);
        g.setFont(fnt);
        g.drawString("SUN CREDITS : " + CommandCenter.getSunCredit(), nFontWidth, nFontHeight * 2 + 5);
    }

    /**
     * Initializes the font metrics for drawing text on the screen.
     */
    private void initView() {
        Graphics g = getGraphics();
        g.setFont(fnt);
        fmt = g.getFontMetrics();
        nFontWidth = fmt.getMaxAdvance();
        nFontHeight = fmt.getHeight();
    }

    /**
     * Iterates over all movable game entities and calls their `move`, `draw`, and `expire` methods.
     * @param g the Graphics object used to draw the movable entities.
     * @param movMovz one or more lists of movable entities to iterate through.
     */
    private void iterateMovables(Graphics g, CopyOnWriteArrayList<Movable>... movMovz) {
        for (CopyOnWriteArrayList<Movable> movMovs : movMovz) {
            for (Movable mov : movMovs) {
                mov.move();
                mov.draw(g);
                mov.expire();
            }
        }
    }
}
