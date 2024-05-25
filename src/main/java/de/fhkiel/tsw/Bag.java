package de.fhkiel.tsw;

import de.fhkiel.tsw.armyoffrogs.Color;

import java.util.ArrayList;
import java.util.List;

public class Bag {
    private int numoffrogs;
    private boolean bagFilled;
    //private Frog frog;

    private List<Frog> frogsInBag;
    private List<Color> baseColorList= new ArrayList<>();

    public Bag(){

        this.baseColorList.add(Color.Blue);
        this.baseColorList.add(Color.Red);
        this.baseColorList.add(Color.Green);
        this.baseColorList.add(Color.Black);
        this.numoffrogs = 40;
        //this(40,baseColorList);
    }



    public Bag(int numoffrogs, List<Color> playerCOlor) {

        this.numoffrogs = numoffrogs;
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
}
