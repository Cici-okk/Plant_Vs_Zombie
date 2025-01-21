package model;

import java.awt.*;
import java.util.ArrayList;


/**
 * The CrazyZombie class represents a special type of zombie with unique
 * characteristics such as swinging legs, variable speed, and the ability
 * to be frozen by ice bullets. CrazyZombies move across the game screen
 * and can trigger a game-over event if they cross the yard.
 */
public class CrazyZombie extends Sprite {

    private final static int ZOMBIE_RADIUS = 30;
    private final static int SCALER = 1;

    private boolean isSwingToLeft = true;
    public int stepLength = 10;
    private int leftFootX = -stepLength;
    private int rightFootX = stepLength;

    public int speed = 1;
    private int newSpeed = 1;

    public int speedRatio = 1;

    private boolean isFozen = false;

    public int nSize = 3;

    private int iceTime = 0;


    public Color mainColor = Color.red;



    public Point handPoint;

    /**
     * Constructs a CrazyZombie at the given y-coordinate.
     * The zombie is initialized with default properties such as size,
     * speed, and position.
     *
     * @param y The y-coordinate where the zombie is created.
     */
    public CrazyZombie(int y){

        super();

        stepLength = 6;

        nSize = 2;
        speedRatio = 5;

        // setCenter(new Point((int)getCenter().getX(), (int)getCenter().getY()));


        ArrayList<Point> pntCs = new ArrayList<Point>();

        setRadius(ZOMBIE_RADIUS);


        if (y<100){
            y = 300;
        }

        setPointCenter(new Point(1190, y));

        setDeltaX(-speed*0.5);

        pntCs.add(new Point(0*SCALER, 18*SCALER-10));

        //right points
        pntCs.add(new Point(10*SCALER, 18*SCALER-10));
        pntCs.add(new Point(12*SCALER, 20*SCALER-10));
        pntCs.add(new Point(12*SCALER, 18*SCALER-10));
        pntCs.add(new Point(12*SCALER, 0*SCALER-10));
        pntCs.add(new Point(11*SCALER, -2*SCALER-10));
        pntCs.add(new Point(4*SCALER, -3*SCALER-10));
        pntCs.add(new Point(2*SCALER, -10*SCALER-10));


        //left points
        pntCs.add(new Point(-2*SCALER, -10*SCALER-10));
        pntCs.add(new Point(-4*SCALER, -3*SCALER-10));
        pntCs.add(new Point(-11*SCALER, -2*SCALER-10));
        pntCs.add(new Point(-12*SCALER, 0*SCALER-10));
        pntCs.add(new Point(-12*SCALER, 18*SCALER-10));

        assignPolarPoints(pntCs);
        setOrientation(-100);
    }


    /**
     * Moves the zombie across the screen. If the zombie crosses the yard,
     * it triggers a game-over event.
     */
    public void move(){
        super.move();

        int x = getPointCenter().x;
        int y = getPointCenter().y;

        int xUpdate = x+(int)getDeltaX();
        setPointCenter(new Point(xUpdate, y));

        if(xUpdate<50){
            System.out.println("Game Over....................");
            CommandCenter.zombieCrossYard();
        }
    }

    /**
     * Draws the zombie on the screen, including its body, legs, and frozen effect if applicable.
     *
     * @param g The Graphics object used to draw the zombie.
     */
    @Override
    public void draw(Graphics g) {
        updateSpeed();
        if(speed==0){
            g.setColor(Color.cyan);
            g.fillRect(getPointCenter().x-30, getPointCenter().y-20, 61, 62);
            iceTime++;
        }

        if(iceTime==200){
            recover();
        }

        super.draw(g);
        // 1
        g.setColor(mainColor);
        g.fillPolygon(toArrayIntCoords(getnXCoords()), toArrayIntCoords(getnYCoords()), getnXCoords().size());
        // 2
        g.setColor(Color.lightGray);
        g.drawPolygon(toArrayIntCoords(getnXCoords()), toArrayIntCoords(getnYCoords()), getnXCoords().size());
        // 4
        g.setColor(Color.darkGray);
        g.fillOval(getPointCenter().x-15, getPointCenter().y-5, 12, 10);




        g.setColor(mainColor);
        if(leftFootX < -stepLength){
            isSwingToLeft = false;
        }
        else if(leftFootX > stepLength){
            isSwingToLeft = true;
        }

        if (isSwingToLeft){
            leftFootX=leftFootX-speed;
            rightFootX=rightFootX+speed;
        }
        else {
            leftFootX=leftFootX+speed;
            rightFootX=rightFootX-speed;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(4));
        g2.drawLine(getPointCenter().x+6,getPointCenter().y+20,getPointCenter().x-leftFootX, getPointCenter().y+40);
        g2.drawLine(getPointCenter().x+6,getPointCenter().y+20,getPointCenter().x-rightFootX, getPointCenter().y+40);

        g2.setStroke(new BasicStroke(2));
        handPoint = new Point(getPointCenter().x-15, getPointCenter().y+20+10);
        g2.drawLine(getPointCenter().x,getPointCenter().y+10+15, (int)handPoint.getX(), (int)handPoint.getY());
        g2.drawLine(getPointCenter().x,getPointCenter().y+10+10, getPointCenter().x+12, getPointCenter().y+18+10);

        g.setColor(Color.black);
        g.drawLine(getPointCenter().x-13,getPointCenter().y+14,getPointCenter().x, getPointCenter().y+9);

        g2.setStroke(new BasicStroke(1));
    }

    /**
     * Returns the current size of the zombie, indicating its health.
     *
     * @return The size of the zombie.
     */
    public int getSize(){
        return nSize;
    }

    /**
     * Reduces the zombie's size when hit by a bullet.
     * If the bullet is an ice type, the zombie is frozen.
     *
     * @param bulletType The type of the bullet hitting the zombie.
     */
    public void isHit(int bulletType){
        nSize--;

        if (bulletType==1){
            frozen();

        }
        else {


        }
    }

    /**
     * Freezes the zombie, stopping its movement for a duration.
     */
    public void frozen(){
        speed = 0;
        iceTime = 0;
        setDeltaX(-speed*0.25);
        isFozen = true;
    }

    /**
     * Recovers the zombie from a frozen state, restoring its speed.
     */
    public void recover(){
        speed =checkSpeed();
        iceTime = 0;
        setDeltaX(-speed*0.5);
    }

    /**
     * Checks and updates the zombie's speed based on its state and level.
     *
     * @return The updated speed of the zombie.
     */
    public int checkSpeed(){
        if(isFozen){
            newSpeed = 0;
            return newSpeed;
        }

        if(CommandCenter.getLevel()>2){
            newSpeed = 2;
            setDeltaX(-speed*0.5);
            stepLength = 8;
        }
        else {
            newSpeed = 1; // double size?
        }
        return newSpeed;
    }

    /**
     * Updates the zombie's speed based on its current speed ratio.
     */
    public void updateSpeed(){
        speed = checkSpeed()*speedRatio;
        setDeltaX(-speed*0.5);
    }


}
