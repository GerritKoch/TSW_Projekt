package de.fhkiel.tsw;

import de.fhkiel.tsw.armyoffrogs.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class Player {

    private Color playerColor;
    private List<Frog> myFrogs;
    private myFrogList frogsInHand;
    private Gamelogic currentGame;
    public boolean isMyTurn;
    public Player (Color inputColor){

        myFrogs = new ArrayList<>();
        frogsInHand = new myFrogList();

        this.playerColor = inputColor;
        for(int i = 0; i < 10; i++){
            myFrogs.add(new Frog(inputColor));
        }
    }

    private enum Actions {
        BEWEGEN,
        ANLEGEN,
        NACHZIEHEN
    }

//    private Map<Actions, Runnable> actions = Map.of(
//        Actions.BEWEGEN, () -> {
//            currentGame.bewegen(null, 0, 0);
//            System.out.println("Bewegen action failed: ");
//        },
//        Actions.ANLEGEN, () -> {
//            currentGame.anlegen(null, 0, 0);
//            System.out.println("Anlegen action failed: ");
//        },
//        Actions.NACHZIEHEN, () -> {
//            currentGame.nachzeihen(this.getPlayerColor());
//            System.out.println("Nachziehen action failed: ");
//        }
//    );

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

    public void setMyFrogs(Frog frog) {

        this.myFrogs.add(frog);
    }



}
