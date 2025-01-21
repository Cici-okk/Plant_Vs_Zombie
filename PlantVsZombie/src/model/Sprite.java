package model;


import controller.GameController;
import view.GamePanel;

import java.awt.*;
import java.util.ArrayList;

/**
 * The Sprite class represents a movable and drawable object in the game.
 * It serves as a base class for other specific objects like bullets, zombies, or plants.
 */
public abstract class Sprite implements Movable {

    //every sprite needs to know about the size of the gaming environment
    //dimension (width and height) of the gaming environment
    public Dimension dim;
    public int radius;
    public Point pointCenter;
    public int orientation;
    //this causes movement; change in x and change in y
    private double dDeltaX, dDeltaY;

    //natural mortality (for short-living objects)
    private int nExpiry;

    //this game uses radial coordinates to render sprites
    public double[] dLengths;
    public double[] dDegrees;
    private ArrayList<Integer> nXCoords;
    private ArrayList<Integer> nYCoords;
    private ArrayList<Point> pntCoords; //an array of points used to draw polygon

    private Color col;

    /**
     * Constructor to initialize a Sprite with random position and default settings.
     */
    public Sprite() {

        //you can override this and many more in the subclasses
        setDimension(GamePanel.SCREEN_SIZE);
        setColor(Color.white);
        setPointCenter(new Point(GameController.randomNum.nextInt(GamePanel.SCREEN_SIZE.width),
                GameController.randomNum.nextInt(GamePanel.SCREEN_SIZE.height)));


    }



    public void setDimension(Dimension dim) {this.dim = dim;}
    public Dimension getDimension() {return this.dim;}
    public void setRadius(int radius) {this.radius = radius;}
    public int getRadius() {return this.radius;}
    public void setPointCenter(Point pointCenter) {this.pointCenter = pointCenter;}
    public Point getPointCenter() {return this.pointCenter;}
    public void setOrientation(int orientation) {this.orientation = orientation;}
    public int getOrientation() {return this.orientation;}
    public void setDeltaX(double deltaX) {this.dDeltaX = deltaX;}
    public double getDeltaX() {return this.dDeltaX;}
    public void setDeltaY(double deltaY) {this.dDeltaY = deltaY;}
    public double getDeltaY() {return this.dDeltaY;}
    public double[] getLengths() {
        return this.dLengths;
    }
    public void setLengths(double[] dLengths) {
        this.dLengths = dLengths;
    }
    public double[] getDegrees() {
        return this.dDegrees;
    }
    public void setDegrees(double[] dDegrees) {
        this.dDegrees = dDegrees;
    }

    public void setnExpiry(int nExpiry) {this.nExpiry = nExpiry;}
    public int getnExpiry() {
        return nExpiry;
    }

    public Color getColor() {return col;}
    public void setColor(Color col) {this.col = col;}

    public void setnXCoords(ArrayList<Integer> nXCoords) {this.nXCoords = nXCoords;}
    public ArrayList<Integer> getnXCoords() {return nXCoords;}
    public void setnYCoords(ArrayList<Integer> nYCoords) {this.nYCoords = nYCoords;}
    public ArrayList<Integer> getnYCoords() {return nYCoords;}
    public ArrayList<Point> getObjectPoints() {
        return pntCoords;
    }
    public void setObjectPoints(ArrayList<Point> pntPs) {
        pntCoords = pntPs;
    }

    /**
     * Converts a list of integers to an array.
     *
     * @param nCoords The list of integers.
     * @return The array of integers.
     */
    public int[] toArrayIntCoords(ArrayList<Integer> nCoords) {
        return nCoords.stream().mapToInt(Integer::intValue).toArray();
    }

    //utility function to convert from cartesian to polar
    //since it's much easier to describe a sprite as a list of cartesean points
    //sprites (except Asteroid) should use the cartesean technique to describe the coordinates
    //see Falcon or Bullet constructor for examples

    /**
     * Calculates the hypotenuse for two values.
     *
     * @param dX The x value.
     * @param dY The y value.
     * @return The hypotenuse.
     */
    protected double hypot(double dX, double dY) {
        return Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));
    }

    /**
     * Converts Cartesian points to polar angles in radians.
     *
     * @param pntPoints The list of points.
     * @return The array of angles in radians.
     */
    protected double[] convertToPolarDegs(ArrayList<Point> pntPoints) {

        //ArrayList<Tuple<Double,Double>> dblCoords = new ArrayList<Tuple<Double,Double>>();
        double[] dDegs = new double[pntPoints.size()];

        int nC = 0;
        for (Point pnt : pntPoints) {
            dDegs[nC++]=(Math.toDegrees(Math.atan2(pnt.y, pnt.x))) * Math.PI / 180 ;
        }
        return dDegs;
    }

    //utility function to convert to polar
    /**
     * Converts Cartesian points to polar lengths.
     *
     * @param pntPoints The list of points.
     * @return The array of lengths.
     */
    protected double[] convertToPolarLens(ArrayList<Point> pntPoints) {

        double[] dLens = new double[pntPoints.size()];

        //determine the largest hypotenuse
        double dL = 0;
        for (Point pnt : pntPoints)
            if (hypot(pnt.x, pnt.y) > dL)
                dL = hypot(pnt.x, pnt.y);

        int nC = 0;
        for (Point pnt : pntPoints) {
            if (pnt.x == 0 && pnt.y > 0) {
                dLens[nC] = hypot(pnt.x, pnt.y) / dL;
            } else if (pnt.x < 0 && pnt.y > 0) {
                dLens[nC] = hypot(pnt.x, pnt.y) / dL;
            } else {
                dLens[nC] = hypot(pnt.x, pnt.y) / dL;
            }
            nC++;
        }

        // holds <thetas, lens>
        return dLens;
    }

    /**
     * Assigns polar points to the sprite based on Cartesian points.
     *
     * @param pntCs The list of Cartesian points.
     */
    protected void assignPolarPoints(ArrayList<Point> pntCs) {
        setDegrees(convertToPolarDegs(pntCs));
        setLengths(convertToPolarLens(pntCs));
    }

    /**
     * Updates the sprite's position based on its velocity and keeps it within bounds.
     */
    public void move() {

        Point pnt = getPointCenter();
        double dX = pnt.x + getDeltaX();
        double dY = pnt.y + getDeltaY();

        //this just keeps the sprite inside the bounds of the frame
        if (pnt.x > getDimension().width) {
            setPointCenter(new Point(1, pnt.y));

        } else if (pnt.x < 0) {
            setPointCenter(new Point(getDimension().width - 1, pnt.y));
        } else if (pnt.y > getDimension().height) {
            setPointCenter(new Point(pnt.x, 1));

        } else if (pnt.y < 0) {
            setPointCenter(new Point(pnt.x, getDimension().height - 1));
        } else {

            setPointCenter(new Point((int) dX, (int) dY));
        }
    }

    // this is a super class, need to add more drawing functions in subclasses.
    /**
     * Draws the sprite. This is an abstract method and should be overridden by subclasses.
     *
     * @param g The graphics object for drawing.
     */
    public void draw(Graphics g) {

        nXCoords = new ArrayList<>();
        nYCoords = new ArrayList<>();
        //need this as well
        pntCoords = new ArrayList<>();

        for (int nC = 0; nC < dDegrees.length; nC++) {
            nXCoords.add((int) (getPointCenter().x + getRadius()
                    * dLengths[nC]
                    * Math.sin(Math.toRadians(getOrientation()) + dDegrees[nC])));
            nYCoords.add((int) (getPointCenter().y - getRadius()
                    * dLengths[nC]
                    * Math.cos(Math.toRadians(getOrientation()) + dDegrees[nC])));


            //need this line of code to create the points which we will need for debris
            pntCoords.add(new Point(nXCoords.get(nC), nYCoords.get(nC)));
        }

//
//        nXCoords = new ArrayList<>();
//        nYCoords = new ArrayList<>();
//        for (Point p : pntCoords) {
//            nXCoords.add(p.x+ pointCenter.x);  // Add the x-coordinate to the list
//            nYCoords.add(p.y+pointCenter.y);
//        }

//        g.setColor(getColor());
//        g.drawPolygon(toArrayIntCoords(nXCoords), toArrayIntCoords(nYCoords), nXCoords.size());
    }

    /**
     * Handles the sprite's expiry logic. Override in subclasses for custom behavior.
     */
    public void expire() {
    }


}
