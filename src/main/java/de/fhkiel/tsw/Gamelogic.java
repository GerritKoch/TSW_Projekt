package de.fhkiel.tsw;

import de.fhkiel.tsw.armyoffrogs.Color;
import de.fhkiel.tsw.armyoffrogs.Game;
import de.fhkiel.tsw.armyoffrogs.Position;
import java.util.List;
import java.util.Set;

public class Gamelogic implements Game {
    @Override
    public boolean newGame(int numberOfPlayers) {
        return false;
    }

    @Override
    public Color[] players() {
        return new Color[0];
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public List<Color> getFrogsInHand(Color player) {
        return null;
    }

    @Override
    public Set<Position> getBoard() {
        return null;
    }

    @Override
    public void clicked(Position position) {

    }

    @Override
    public void selectedFrogInHand(Color player, Color frog) {

    }

    @Override
    public Color winner() {
        return null;
    }

    @Override
    public boolean save(String filename) {
        return false;
    }

    @Override
    public boolean load(String filename) {
        return false;
    }

    private Bag bag = new Bag();

    @Override
    public int frogsInBag() {
        return bag.getFrogs();
    }

    public void startGame(int spieler) {
        bag = new Bag(spieler*10);
        for (int i = 0; i < 2*spieler; ++i){
            bag.takeFrog();
        }
    }

    public void takeFrogFromBag() {
        bag.takeFrog();
    }
}
