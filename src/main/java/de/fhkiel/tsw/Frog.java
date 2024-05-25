package de.fhkiel.tsw;

import de.fhkiel.tsw.armyoffrogs.Color;

public class Frog {


    private Color frogColor;

    public Frog (){
        this.frogColor = Color.None;
    }

    public Frog(Color inputColor){
        this.frogColor = inputColor;
    }

    public Color getFrogColor() {
        return frogColor;
    }

    public void setFrogColor(Color frogColor) {
        this.frogColor = frogColor;
    }
}
