package de.fhkiel.tsw;

import de.fhkiel.tsw.armyoffrogs.Color;
public class Player {

    private Color playerColor;

    public Player (Color inputColor){
        this.playerColor = inputColor;
    }

    public Color getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(Color playerColor) {
        this.playerColor = playerColor;
    }
}
