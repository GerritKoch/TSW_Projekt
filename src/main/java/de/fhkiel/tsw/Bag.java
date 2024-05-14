package de.fhkiel.tsw;

public class Bag {
    private int frogs;

    public Bag(){
        this(40);
    }

    public Bag(int frogs) {
        this.frogs = frogs;
    }

    public int getFrogs() {
        return frogs;
    }

    public void takeFrog() {
        if(frogs > 0) {
            frogs = frogs - 1;
        }
    }
}
