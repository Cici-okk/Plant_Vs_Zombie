package controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import model.*;
import view.GamePanel;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * The GameController class handles the core game mechanics, including animation, event handling,
 * collision detection, and interaction between game entities such as zombies, peashooters, and suns.
 * It implements the Runnable interface to allow continuous updating of the game state in a separate thread,
 * and it also listens to mouse events for user interaction.
 */
public class GameController implements Runnable, MouseListener, MouseMotionListener {

    private Thread thread;
    private final GamePanel gamePanel;

    // track screen update
    private boolean isIntroScreen = true;

    public static final Random randomNum = new Random();
    private static int nTick = 0;
    public final static int ANI_DELAY = 45; // milliseconds between screen updates (animation)
    private ArrayList<Tuple> tupMarkForRemovals;

    private Clip clpThrust;
    public static Clip clpMusicBackground;
    public static Clip clpLevel1;

    /**
     * Starts the animation thread and begins the game loop.
     */
    @Override
    public void run() {

        try {
            System.out.println("Game Started");
            this.fireUpAnimThread();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Updating in Game.....");

        // lower this thread's priority; let the "main" aka 'Event Dispatch'
        // thread do what it needs to do first
        thread.setPriority(Thread.MIN_PRIORITY);

        // and get the current time
        long lStartTime = System.currentTimeMillis();

        // this thread animates the scene
        while (Thread.currentThread() == thread) {
            if (isIntroScreen) {
                drawIntroScreen(gamePanel.getGraphics());
            } else {
                tick();
                generateCandidatePlants(); // candidate plant always appear on screen
                checkCollisions();
                gamePanel.update(gamePanel.getGraphics()); // update takes the graphics context we must
            }

            try {
                // The total amount of time is guaranteed to be at least ANI_DELAY long. If processing (update)
                // between frames takes longer than ANI_DELAY, then the difference between lStartTime -
                // System.currentTimeMillis() will be negative, then zero will be the sleep time
                lStartTime += ANI_DELAY;
                Thread.sleep(Math.max(0, lStartTime - System.currentTimeMillis()));
            } catch (InterruptedException e) {
                // just skip this frame -- no big deal
                continue;
            }
        }
    }

    /**
     * Draws the introductory screen with game instructions.
     * @param g the Graphics object used to draw on the screen.
     */
    private void drawIntroScreen(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);

        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("PLANTS VS ZOMBIES", 300, 200);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("INSTRUCTION", 500, 300);
        g.drawString("A mob of zombies is about to invade your home.", 300, 350);
        g.drawString("Use the mouse to collect suns.", 300, 400);
        g.drawString("Use suns to plant peashooters for defense.", 300, 450);
        g.drawString("'S' to Start", 300, 500);
        g.drawString("'P' to Pause", 300, 550);
        g.drawString("'Q' to Quit", 300, 600);
    }

    /**
     * Initializes the animation thread to continuously update the game state.
     */
    private void fireUpAnimThread() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * Increments the tick count and generates new game entities (suns, zombies, etc.).
     */
    public static void tick() {
        if (nTick == Integer.MAX_VALUE) {
            nTick = 0;
        }
        nTick++;
        generateNewSun();
        generateNewZombie();

        if (CommandCenter.getLevel() >= 2) {
            generateNewCrazyZombie();
        }
    }

    /**
     * Returns the current tick count.
     * @return the current tick count.
     */
    public static int getTick() {
        return nTick;
    }

    /**
     * Generates a new sun at random positions on the screen.
     */
    private static void generateNewSun() {
        int currentTick = getTick();
        if (currentTick % 13 == 0) {
            int tempTick = (int) (Math.random() * 5);
            if (tempTick % 4 == 0) {
                int randomX = (int) (Math.random() * GamePanel.SCREEN_WIDTH);
                CommandCenter.movSun.add(new Sun(randomX));
            }
        }
    }

    /**
     * Generates a new zombie at random positions on the screen.
     */
    private static void generateNewZombie() {
        int tick = getTick();
        int n = CommandCenter.getLevel() <= 1 ? 50 : 20;

        if (tick % n == 0) {
            int tempTick = (int) (Math.random() * 10);
            if (tempTick % 7 == 0) {
                int randomY = (GameController.randomNum.nextInt() % 4) * 100 + 200;
                CommandCenter.movFoes.add(new Zombie(randomY));
            }
        }
    }

    /**
     * Generates a new crazy zombie at random positions on the screen.
     */
    private static void generateNewCrazyZombie() {
        int tick = getTick();
        if (tick % 15 == 0) {
            int tempTick = (int) (Math.random() * 10);
            if (tempTick % 7 == 0) {
                int randomY = (GameController.randomNum.nextInt() % 4) * 100 + 200;
                CommandCenter.movFoes.add(new CrazyZombie(randomY));
            }
        }
    }

    /**
     * Generates a new Peashooter at the specified location.
     * @param newPoint the point where the Peashooter will be placed.
     */
    private void generateNewPeashooter(Point newPoint) {
        if (CommandCenter.plantType == 0) {
            CommandCenter.movPlants.add(new Peashooter(newPoint));
        } else if (CommandCenter.plantType == 1) {
            CommandCenter.movPlants.add(new IcePeashooter(newPoint));
        }
        Sound.playSound("music/plantingpeashooter.wav");
    }

    /**
     * Generates candidate plants for the player to select.
     */
    private void generateCandidatePlants() {
        CommandCenter.movCandidate.add(new CandidatePeashooter(250 - 20, 600 + 50));
        CommandCenter.movCandidate.add(new CandidateIcePeashooter(500 - 20 - 10, 600 + 50));
    }

    /**
     * Checks if a new Peashooter would overlap with an existing one at the specified position.
     * @param candidatePos the position of the new Peashooter.
     * @return true if the position is valid, false otherwise.
     */
    private boolean checkNoDuplicatePeashooter(Point candidatePos) {
        Point pntPlantCenter, pntFoeCenter;
        int x = (int) candidatePos.getX();
        int y = (int) candidatePos.getY();

        int modX = x % 100;
        int modY = y % 100;

        x = modX < 50 ? x - modX : x + 100 - modX;
        y = modY < 50 ? y - modY : y + 100 - modY;

        pntFoeCenter = new Point(x, y);

        for (Movable movPlant : CommandCenter.movPlants) {
            pntPlantCenter = movPlant.getPointCenter();
            if (pntPlantCenter.distance(pntFoeCenter) == 0) {
                CommandCenter.gameGuide = "A peashooter has been there. Each slot can only have one peashooter.";
                return false;
            }
        }

        return true;
    }

    /**
     * Checks for collisions between bullets, plants, and foes, and handles the removal of entities if necessary.
     */
    private void checkCollisions() {
        tupMarkForRemovals = new ArrayList<Tuple>();

        Point pntBulletCenter, pntFoeCenter, pntPlantCenter;
        int nBulletRadius, nFoeRadius, nPlantRadius;

        for (Movable movBullet : CommandCenter.movBullets) {
            int offset = 0;
            for (Movable movFoe : CommandCenter.movFoes) {
                pntBulletCenter = movBullet.getPointCenter();
                pntFoeCenter = movFoe.getPointCenter();
                nBulletRadius = movBullet.getRadius();
                nFoeRadius = movFoe.getRadius();

                if (pntBulletCenter.distance(pntFoeCenter) < (nBulletRadius + nFoeRadius - 20 + offset)) {
                    if ((movBullet instanceof Bullet)) {
                        offset = 15;
                        tupMarkForRemovals.add(new Tuple(CommandCenter.movBullets, movBullet));
                        Bullet.bulletSoundEffect();
                        hitFoe(movBullet, movFoe);
                    }
                }
            }
        }

        for (Movable movPlant : CommandCenter.movPlants) {
            for (Movable movFoe : CommandCenter.movFoes) {
                pntPlantCenter = movPlant.getPointCenter();
                pntFoeCenter = movFoe.getPointCenter();
                nPlantRadius = movPlant.getRadius();
                nFoeRadius = movFoe.getRadius();

                if (pntPlantCenter.distance(pntFoeCenter) < (nPlantRadius + nFoeRadius - 80)) {
                    if ((movPlant instanceof Peashooter)) {
                        CommandCenter.gameGuide = "It's very dangerous. More and more zombies are coming.";
                        tupMarkForRemovals.add(new Tuple(CommandCenter.movPlants, movPlant));
                    }
                }
            }
        }

        for (Tuple tup : tupMarkForRemovals) {
            tup.removeMovable();
        }

        if (nTick % 300 == 0) {
            System.gc();
        }
    }

    /**
     * Handles the interaction between a bullet and a foe, including updating the game state and removing the foe if needed.
     * @param movBullet the bullet that hits the foe.
     * @param movFoe the foe that is hit by the bullet.
     */
    private void hitFoe(Movable movBullet, Movable movFoe) {
        if (movFoe instanceof Zombie) {
            Zombie astExploded = (Zombie) movFoe;
            Bullet bullet = (Bullet) movBullet;

            if (astExploded.getSize() == 1) {
                tupMarkForRemovals.add(new Tuple(CommandCenter.movFoes, movFoe));
                CommandCenter.addScore(100);
            } else {
                astExploded.isHit(bullet.bulletType);
                if (astExploded.getSize() == 1) {
                    CommandCenter.movDebris.add(new ExplodingHead(new Point(astExploded.getPointCenter()), astExploded.mainColor));
                }
            }
        } else if (movFoe instanceof CrazyZombie) {
            CrazyZombie astExploded = (CrazyZombie) movFoe;
            Bullet bullet = (Bullet) movBullet;

            if (astExploded.getSize() == 1) {
                tupMarkForRemovals.add(new Tuple(CommandCenter.movFoes, movFoe));
                CommandCenter.addScore(100);
            } else {
                astExploded.isHit(bullet.bulletType);
            }
        }
    }

    /**
     * Stops the specified looping sound clips.
     * @param clpClips the sound clips to stop.
     */
    public static void stopLoopingSounds(Clip... clpClips) {
        for (Clip clp : clpClips) {
            clp.stop();
        }
    }

    /**
     * Handles the click event to collect suns and add sun credits to the player's total.
     * @param e the MouseEvent that triggered the click.
     */
    private void checkValidClick(MouseEvent e) {
        Point pntFoeCenter = e.getPoint();
        int nFoeRadius = 20;

        for (Movable movSun : CommandCenter.movSun) {
            if (movSun instanceof Sun &&
                    pntFoeCenter.distance(movSun.getPointCenter()) < movSun.getRadius() + nFoeRadius) {
                CommandCenter.addSunCredit(((Sun) movSun).getCredit());
                Sound.playSound("music/select.wav");
                CommandCenter.movSun.remove(movSun);
                break;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        checkValidClick(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (CommandCenter.isPlanting && e.getY() < 550 && e.getY() > 51 && e.getX() > 51 && e.getX() < 1150) {
            if (checkNoDuplicatePeashooter(new Point(e.getX(), e.getY()))) {
                generateNewPeashooter(new Point(e.getX(), e.getY()));
            }
        }
        CommandCenter.clearMovTemp();
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    /**
     * Handles the mouse press event to select a plant type based on the candidate's position.
     * @param e the MouseEvent that triggered the press.
     */
    private void checkValidPress(MouseEvent e) {
        Point pntFoeCenter = e.getPoint();
        int nFoeRadius = 10;

        for (Movable movCandidate : CommandCenter.movCandidate) {
            if (pntFoeCenter.distance(movCandidate.getPointCenter()) < movCandidate.getRadius() + nFoeRadius &&
                    movCandidate instanceof Peashooter) {
                CommandCenter.setPlant(((Peashooter) movCandidate).typeIndicator);
                System.out.println("Plant type is: " + ((Peashooter) movCandidate).typeIndicator);
                break;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        checkValidPress(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        CommandCenter.setPlantPosition(e.getPoint());
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    /**
     * Constructs a new GameController to initialize game assets and event listeners.
     * @throws IOException if there is an error loading sound files.
     */
    public GameController() throws IOException {

        clpMusicBackground = Sound.clipForLoopFactory("music/plants_vs_zombies.wav");
        clpLevel1 = Sound.clipForLoopFactory("music/level1.wav");

        System.out.println("Start game");
        gamePanel = new GamePanel();
        gamePanel.addMouseListener(this);
        gamePanel.addMouseMotionListener(this);

        clpMusicBackground.loop(Clip.LOOP_CONTINUOUSLY);

        // Add a keyboard listener
        gamePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int nKey = e.getKeyCode();

                if (isIntroScreen && nKey == KeyEvent.VK_S) {
                    isIntroScreen = false; // Exit the intro screen
                } else if (!isIntroScreen) {
                    switch (nKey) {
                        case KeyEvent.VK_P:
                            CommandCenter.setPaused(!CommandCenter.isPaused());
                            break;
                        case KeyEvent.VK_Q:
                            System.exit(0);
                            break;
                        default:
                            break;
                    }
                }
            }
        });

        gamePanel.setFocusable(true); // Ensure the panel can receive keyboard events
    }

    /**
     * Sets the static variable nTick for testing purposes.
     * Allows controlled modification to validate dependent methods.
     */
    public static void setTick(int tick) {
        nTick = tick;
    }
}
