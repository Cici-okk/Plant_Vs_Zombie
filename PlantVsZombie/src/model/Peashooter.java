package model;

import controller.GameController;

import java.awt.*;

/**
 * The Peashooter class represents a plant that shoots bullets at zombies.
 * It can be drawn, placed on the grid, and create bullets over time.
 */
public class Peashooter extends Sprite {

    private final static int PEASHOOTER_RADIUS = 100;
    private int plantScore = 100;
    // Cici added typeIndicator for regular and ice peashooter
    public int typeIndicator = 0;
    private int initBullet;

    /**
     * Constructor to create a Peashooter at a specific position.
     *
     * @param x The x-coordinate of the Peashooter.
     * @param y The y-coordinate of the Peashooter.
     */
    public Peashooter(int x, int y){
        super();
        setColor(Color.green);
        setRadius(PEASHOOTER_RADIUS);
        setPointCenter(new Point(x, y));
    }

    /**
     * Constructor to create a Peashooter at a specific point,
     * adjusting its position to fit into the grid.
     *
     * @param newPoint The point where the Peashooter is placed.
     */
    public Peashooter(Point newPoint) {
        super();
        setColor(Color.green);
        setRadius(PEASHOOTER_RADIUS);
        initBullet =  (int)(Math.random()*130);

        CommandCenter.minusSunCredit(plantScore);

        // set to grid center
        int x = (int)newPoint.getX();
        int y = (int)newPoint.getY();

        int modX = x%100;
        int modY = y%100;

        if(modX<50){
            x = x-modX;
        }
        else {
            x = x+100-modX;
        }

        if(modY<50){
            y = y-modY;
        }
        else {
            y = y+100-modY;
        }

        setPointCenter(new Point(x, y));
    }

    /**
     * Draws the Peashooter on the screen.
     *
     * @param g The Graphics object used for drawing.
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        g.fillOval(getPointCenter().x-40, getPointCenter().y-30, 55, 45);
        g.fillOval(getPointCenter().x-20+2, getPointCenter().y+10, 15, 10);
        g.fillArc(getPointCenter().x-40-10, getPointCenter().y+30, 80, 20, 240, 340);
        g.fillRect(getPointCenter().x-10, getPointCenter().y-10, 50, 20);

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(8));
        g2.drawLine(getPointCenter().x-14,getPointCenter().y+10,getPointCenter().x, getPointCenter().y+40);
        g2.setStroke(new BasicStroke(1));

        g.setColor(Color.white);
        g.fillOval(getPointCenter().x, getPointCenter().y-15, 8, 12);

        g.setColor(Color.black);
        g.fillOval(getPointCenter().x+4, getPointCenter().y-12, 4, 6);

        g.setColor(Color.darkGray);
        g.fillOval(getPointCenter().x+20, getPointCenter().y-18, 30, 36);

        g2.setStroke(new BasicStroke(2));
        g2.setColor(getColor());
        g2.drawOval(getPointCenter().x+20, getPointCenter().y-18, 30, 36);
        g2.setStroke(new BasicStroke(1));
    }

    /**
     * Moves the Peashooter by shooting bullets at regular intervals.
     */
    @Override
    public void move() {

        Bullet bullet = new Bullet(this);

        int initTime = GameController.getTick()+initBullet;
        if(initTime%130 == 0){
            CommandCenter.movBullets.add(bullet);
        }

    }

}
