package de.fhkiel.tsw;

import de.fhkiel.tsw.armyoffrogs.Color;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private Color playerColor;
    private List<Frog> myFrogs;
    private List<Frog> frogsInHand;
    public Player (Color inputColor){

        myFrogs = new ArrayList<>();
        frogsInHand = new ArrayList<>();

        this.playerColor = inputColor;
        for(int i = 0; i < 10; i++){
            myFrogs.add(new Frog(inputColor));
        }
    }

    public Color getPlayerColor() {
        return playerColor;
    }

    public List<Frog> getFrogs() {
        return myFrogs;
    }

    public void setPlayerColor(Color playerColor) {
        this.playerColor = playerColor;
    }

    public List<Frog> getFrogsInHand() {
        return frogsInHand;
    }

    public void setFrogsInHand(Frog frogInHand) {
        this.frogsInHand.add(frogInHand);
    }

    public void setMyFrogs(List<Frog> myFrogs) {
        this.myFrogs = myFrogs;
    }


    public void performActions(Frog frog, int x, int y, Gamelogic gameLogic) {
        try {
            gameLogic.bewegen(frog, x, y);
        } catch (Exception e) {
            System.out.println("Bewegen action failed: " + e.getMessage());
        }

        try {
            gameLogic.anlegen(frog, x, y);
        } catch (Exception e) {
            System.out.println("Anlegen action failed: " + e.getMessage());
        }

        try {
            gameLogic.nachzeihen(this.getPlayerColor());
        } catch (Exception e) {
            System.out.println("Nachziehen action failed: " + e.getMessage());
        }
    }
}
