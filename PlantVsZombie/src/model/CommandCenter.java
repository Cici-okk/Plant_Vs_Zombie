package model;

import controller.GameController;
import jdk.internal.icu.text.UnicodeSet;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The CommandCenter class manages the state of the game, including
 * the player's resources, plants, zombies, bullets, and game levels.
 * It also controls game-related actions like playing music and managing
 * plant placement.
 */
public class CommandCenter {
    public static CopyOnWriteArrayList<Movable> movCandidate = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<Movable> movSun = new CopyOnWriteArrayList<Movable>();
    public static CopyOnWriteArrayList<Movable> movPlants = new CopyOnWriteArrayList<Movable>();
    public static CopyOnWriteArrayList<Movable> movTemp = new CopyOnWriteArrayList<Movable>();
    public static CopyOnWriteArrayList<Movable> movBullets = new CopyOnWriteArrayList<Movable>();
    public static CopyOnWriteArrayList<Movable> movFoes = new CopyOnWriteArrayList<Movable>();
    public static CopyOnWriteArrayList<Movable> movDebris = new CopyOnWriteArrayList<Movable>();

    private static long lSunCredit;
    public static String gameGuide = "Enjoy the game.";
    public static Peashooter plant;
    public static Boolean isPlanting = false;
    private static int nLevel;
    private static long lScore;
    public static int plantType = 0;

    private static boolean bPlaying;

    /**
     * Adds the given amount to the player's sun credits.
     *
     * @param lParam The amount of sun credits to add.
     */
    public static void addSunCredit(long lParam) {
        lSunCredit = lSunCredit + lParam;
    }

    /**
     * Returns the current amount of sun credits.
     *
     * @return The current sun credits.
     */
    public static long getSunCredit() {
        return lSunCredit;
    }

    /**
     * Sets the player's sun credits to the given amount.
     *
     * @param lParam The amount to set sun credits to.
     */
    public static void setSunCredit(long lParam) {
        lSunCredit = lParam;
    }

    public static CopyOnWriteArrayList<Movable> movLevelInstruction = new CopyOnWriteArrayList<Movable>();
    private static boolean bisGameOver;

    private CommandCenter(){}

    /**
     * Initializes the game by resetting the sun credits.
     */
    public static void initGame(){
        setSunCredit(0);
    }

    // controller to control the status of the game
    /**
     * Sets whether the game is over.
     *
     * @param b True if the game is over, false otherwise.
     */
    public static void setIsGameOver(boolean b){
        bisGameOver = b;
    }


    /**
     * Returns whether the game is over.
     *
     * @return True if the game is over, false otherwise.
     */
    public static boolean isGameOver() {
        return bisGameOver;
    }

    /**
     * Handles the event when zombies cross the yard.
     * Stops the current music and plays the game over sound.
     */
    public static void zombieCrossYard() {

        GameController.stopLoopingSounds(GameController.clpLevel1);
        // Game.clpLevel1 = Sound.clipForLoopFactory("level1.wav");  // I need a new sound effect here.

        // Game.clpLevel1.loop(Clip.LOOP_CONTINUOUSLY);
        Sound.playSound("music/gameover.wav");

        movLevelInstruction.clear();

        LevelInstruction tempLevleInstruction = new LevelInstruction(500,200, "Zombies Reach Your Front Door. Game Over.");
        tempLevleInstruction.setDeltaY(0);
        movLevelInstruction.add(tempLevleInstruction);

        new java.util.Timer().schedule(
            new java.util.TimerTask() {
                @Override
                public void run() {
                    // your code here
                    setIsGameOver(true);

                    // Game.stopLoopingSounds(Game.clpMusicBackground);


                    GameController.clpMusicBackground.loop(Clip.LOOP_CONTINUOUSLY);
                }
            },
            4000  // One second.....................
        );
    }

    // Game setting
    /**
     * Returns the current game level.
     *
     * @return The current level.
     */
    public static int getLevel() {
        return nLevel;
    }

    /**
     * Increases the game level by one.
     */
    public static void addLevel() {
        setLevel(nLevel+1);
    }

    /**
     * Sets the game level to the specified value.
     * Also changes the background music if the level changes.
     *
     * @param n The new level.
     */
    public static void setLevel(int n) {
        if (nLevel != n && n < 4){
            System.out.println("Change playing music.................");
            changePlayingMusic(n);
        }
        nLevel = n;
    }

    /**
     * Returns the player's current score.
     *
     * @return The player's score.
     */
    public static long getScore() {
        return lScore;
    }

    /**
     * Sets the player's score to the given value.
     *
     * @param lParam The new score.
     */
    public static void setScore(long lParam) {
        lScore = lParam;
    }

    /**
     * Adds the specified amount to the player's score.
     * Increases the game level if the score reaches a multiple of 300.
     *
     * @param lParam The amount to add to the score.
     */
    public static void addScore(long lParam) {
        lScore = lScore+lParam;

        if(lScore%300==0){
            addLevel();
        }
    }

    private static void changePlayingMusic(int level_){
        GameController.stopLoopingSounds(GameController.clpLevel1);
        if (level_ == 1){
            GameController.clpLevel1 = Sound.clipForLoopFactory("music/level1.wav");
            playMusic();
            movLevelInstruction.add(new LevelInstruction(500,50, "Zombies are Coming!"));
        }
        else if(level_ == 2){
            GameController.clpLevel1 = Sound.clipForLoopFactory("music/level2.wav");
            playMusic();
            movLevelInstruction.add(new LevelInstruction(500,50, "Level Two"));
        }
        else if(level_==3){
            GameController.clpLevel1 = Sound.clipForLoopFactory("music/level3.wav");
            playMusic();
            movLevelInstruction.add(new LevelInstruction(500,50, "Level Three"));
        }
    }

    /**
     * Plays background music after a delay.
     */
    public static void playMusic(){
        // When the background music is changed, always wait for 50000
        new java.util.Timer().schedule(
            new java.util.TimerTask() {
                @Override
                public void run() {
                    // your code here
                    GameController.clpLevel1.loop(Clip.LOOP_CONTINUOUSLY);
                }
            },
            3000
        );
    }

    /**
     * Clears the temporary plant list and resets planting status.
     */
    public static void clearMovTemp() {
        movTemp.clear();
        isPlanting = false;
    }

    /**
     * Deducts the specified amount from the player's sun credits.
     * Ensures the sun credits do not fall below zero.
     *
     * @param lParam The amount to deduct.
     */
    public static void minusSunCredit(long lParam) {
        lSunCredit = lSunCredit-lParam;
        if(lSunCredit < 0){
            lSunCredit = 0;
        }
        System.out.println(lSunCredit);
    }

    /**
     * Sets the type of plant to be placed and updates the plant object.
     *
     * @param type The type of plant (0 for regular, 1 for ice).
     */
    public static void setPlant(int type){

        long currentSunCredit = getSunCredit();

        // original code when only regular peashooter is available
//            if (currentSunCredit < 100) {
//                gameGuide = "No enough credits. Collect more suns.";
//                plant = null; // Explicitly clear the plant
//                isPlanting = false; // Explicitly reset isPlanting
//                return;
//            }

        if(type == 0){
            plantType = 0;
            if(currentSunCredit < 100){
                // System.out.println("No enough credits");
                gameGuide = "No enough credits. Collect more suns.";
                plant = null; // Explicitly clear the plant
                isPlanting = false; // Explicitly reset isPlanting
                return;
            }
            else{
                plant = new Peashooter(-200,-200);
            }
        }
        else if(type == 1){
            plantType = 1;
            if(currentSunCredit<200){
                gameGuide = "No enough credits. Collect more suns.";
                plant = null; // Explicitly clear the plant
                isPlanting = false; // Explicitly reset isPlanting
                return;
            }
            else{
                plant = new IcePeashooter(-200,-200);
            }
        }
        gameGuide = "Drop the peashooter in one slot";
        movTemp.clear();
        movTemp.add(plant);
        isPlanting = true;
        System.out.println(gameGuide);
    }

    /**
     * Updates the position of the plant being placed.
     *
     * @param newPoint The new position of the plant.
     */
    public static void setPlantPosition(Point newPoint){
        if(isPlanting){
            plant.setPointCenter(newPoint);
            if(plantType == 0){
                plant.setColor(Color.green);
            }
            else if(plantType == 1){
                plant.setColor(Color.cyan);
            }
        }
    }

    private static boolean paused = false;

    /**
     * Pauses or resumes the game.
     *
     * @param isPaused True to pause the game, false to resume.
     */
    public static void setPaused(boolean isPaused) {
        paused = isPaused;
    }

    /**
     * Returns whether the game is currently paused.
     *
     * @return True if the game is paused, false otherwise.
     */
    public static boolean isPaused() {
        return paused;
    }

    /**
     * Returns whether the game is currently playing.
     *
     * @return True if the game is playing, false otherwise.
     */
    public static boolean isPlaying() {
        return bPlaying;
    }

    /**
     * Sets whether the game is currently playing.
     *
     * @param bPlaying True if the game is playing, false otherwise.
     */
    public static void setPlaying(boolean bPlaying) {
        CommandCenter.bPlaying = bPlaying;
    }

}
