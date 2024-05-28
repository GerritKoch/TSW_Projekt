package aof;

import de.fhkiel.tsw.armyoffrogs.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bag {
    private List<Color> frogs;

    public Bag(){

        this(new Color[]{Color.Red, Color.Green, Color.Blue, Color.White});
    }

    public Bag(Color[] players) {
        List<Color> newFrogList = new ArrayList<>();
        for(Color player : players){
            for(int i = 0; i < 10; ++i){
                newFrogList.add(player);
            }
            frogs = newFrogList;
        }
    }

    public int getFrogs() {
        return frogs.size();
    }

    public Color takeFrog() {
        if(!frogs.isEmpty()) {
            return frogs.remove(new Random().nextInt(frogs.size()));
        } else {
            return Color.None;
        }
    }
}
