package de.fhkiel.tsw;

import de.fhkiel.tsw.armyoffrogs.Color;
import de.fhkiel.tsw.armyoffrogs.Game;
import de.fhkiel.tsw.armyoffrogs.Position;
import java.util.List;
import java.util.Set;

public class Gamelogic implements Game {

    private Color[] players;
    private Bag gameBag;
    @Override
    public boolean newGame(int numberOfPlayers) {

        if(numberOfPlayers < 2 || numberOfPlayers > 4)
            return false;

        players = new Color[numberOfPlayers];

        Color color[] = {Color.Red, Color.Blue, Color.Green, Color.Black, Color.None,Color.White,Color.Black};

        gameBag = new Bag(numberOfPlayers*10);

        for(int i = 0; i < numberOfPlayers; ++i){
            players[i] = color[i];
        }
        startGame(numberOfPlayers);
        return true;
    }

    @Override
    public Color[] players() {


        return this.players;
    }

    public int numberOfPlayers() {
        return players.length;
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

        Color currentplayers[]=players();


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
