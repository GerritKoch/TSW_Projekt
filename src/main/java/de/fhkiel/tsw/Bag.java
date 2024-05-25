package de.fhkiel.tsw;

import de.fhkiel.tsw.armyoffrogs.Color;

import java.util.ArrayList;
import java.util.List;

public class Bag {
    private int numoffrogs;
    private boolean bagFilled;

    private List<Frog> frogsInBag;
    private List<Color> baseColorList= new ArrayList<>();

    //This is the basic constructor for the Bag class
    public Bag(){

        this.baseColorList.add(Color.Blue);
        this.baseColorList.add(Color.Red);
        this.baseColorList.add(Color.Green);
        this.baseColorList.add(Color.Black);
        this.numoffrogs = 40;
        //this(40,baseColorList);
    }


    //This is the special     constructor for the Bag class
    public Bag(int numoffrogs, List<Color> playerCOlor) {

        frogsInBag = new ArrayList<>();
        this.numoffrogs = numoffrogs;

        for (Color playerColor : playerCOlor) {
            for (int i = 0; i < 10; i++) {
                this.frogsInBag.add(new Frog(playerColor));
            }
        }
        bagFilled = true;
    }

    public Bag(int numoffrogs, Player[] players) {
        frogsInBag = new ArrayList<>();
        this.numoffrogs = numoffrogs;
        for (Player player : players) {
            this.frogsInBag.addAll(player.getFrogs());

        }
        bagFilled = true;
    }

    public int getNumoffrogs() {
        return numoffrogs;
    }

    public void takeFrog() {
        if(numoffrogs > 0) {
            numoffrogs = numoffrogs - 1;
        }
    }

    public void takeFrog(Color color) {
        if(numoffrogs > 0) {
            numoffrogs = numoffrogs - 1;
            frogsInBag.removeIf(frog -> frog.getFrogColor() == color);
        }
    }

    public List<Frog> getFrogsInBag() {
        return frogsInBag;
    }

    public List<Frog> getFrogsInBag(Color color) {
        List<Frog> frogs = new ArrayList<>();
        for (Frog frog : frogsInBag) {
            if (frog.getFrogColor() == color) {
                frogs.add(frog);
            }
        }
        return frogs;
    }


    public boolean isBagFilled() {
        return bagFilled;
    }
}
