package de.fhkiel.tsw;

import de.fhkiel.tsw.armyoffrogs.Color;

public class Frog {


    private Color frogColor;
    private boolean frogInGame;
    private boolean frogInHand = false;


    public Frog (){
        this.frogColor = Color.None;
    }

    public Frog(Color inputColor){
        this.frogColor = inputColor;
    }

    public Color getFrogColor() {
        return frogColor;
    }

    public boolean isFrogInGame() {
        return frogInGame;
    }

    public void setFrogInGame(boolean frogInGame) {
        this.frogInGame = frogInGame;
    }

    public void setFrogColor(Color frogColor) {
        this.frogColor = frogColor;
    }

    public boolean isFrogInHand() {
        return frogInHand;
    }

    public void setFrogInHand(boolean frogInHand) {
        this.frogInHand = frogInHand;
    }
}
