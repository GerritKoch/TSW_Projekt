package de.fhkiel.tsw;

import de.fhkiel.tsw.armyoffrogs.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Player {

    private Color playerColor;
    private List<Frog> myFrogs;
    private List<Frog> frogsInHand;
    private Gamelogic currentGame;
    public boolean isMyTurn;
    public Player (Color inputColor){

        myFrogs = new ArrayList<>();
        frogsInHand = new ArrayList<>();

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

//        Color[] players = gameLogic.players();
//        for (Color player : players) {
//            if (player == this.getPlayerColor()) {
//                this.isMyTurn = false;
//                //I want to change the isMyTurn to true for the next playe
//        }

            Player[] players = gameLogic.getPlayers();
            for (int i = 0; i < players.length; i++) {
                if (players[i].getPlayerColor() == this.getPlayerColor()) {
                    this.isMyTurn = false;
                    // Set isMyTurn to true for the next player
                    if (i + 1 < players.length) {
                        players[i + 1].isMyTurn = true;
                    } else {
                        // If current player is the last one, set isMyTurn to true for the first player
                        players[0].isMyTurn = true;
                    }
                    break;
                }
            }

    }
}
