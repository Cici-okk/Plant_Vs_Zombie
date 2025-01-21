package model;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represents a tuple that holds a list of movable objects and a specific target object.
 * Provides methods to add or remove the target object from the list.
 */
public class Tuple{
    //this can be any one of several CopyOnWriteArrayList<Movable>
    private CopyOnWriteArrayList<Movable> movMovs;
    //this is the target movable object to remove
    private Movable movTarget;

    /**
     * Constructor to create a Tuple with a list of movable objects and a target object.
     *
     * @param movMovs   The list of movable objects.
     * @param movTarget The target movable object.
     */
    public Tuple(CopyOnWriteArrayList<Movable> movMovs, Movable movTarget) {
        this.movMovs = movMovs;
        this.movTarget = movTarget;
    }

    /**
     * Removes the target movable object from the list.
     */
    public void removeMovable(){
        movMovs.remove(movTarget);
    }

    /**
     * Adds the target movable object to the list.
     */
    public void addMovable(){
        movMovs.add(movTarget);
    }

}
