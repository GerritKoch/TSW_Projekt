package de.fhkiel.tsw;

import de.fhkiel.tsw.armyoffrogs.Color;
import de.fhkiel.tsw.armyoffrogs.Game;
import de.fhkiel.tsw.armyoffrogs.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Gamelogic implements Game {

    private Player[] players;
    private Bag gameBag;
    private boolean spielLaueft;
    @Override
    public boolean newGame(int numberOfPlayers) {

        if(numberOfPlayers < 2 || numberOfPlayers > 4){

            spielLaueft = false;

            return false;

        }

        players = new Player[numberOfPlayers];

        Color color[] = {Color.Red, Color.Blue, Color.Green, Color.Black,Color.White,Color.Black};
        List<Color> colorList = new ArrayList<>();
        colorList.add(Color.Red);
        colorList.add(Color.Blue);
        colorList.add(Color.Green);
        colorList.add(Color.Black);
        gameBag = new Bag(numberOfPlayers*10,colorList);

        //for(int i = 0; i < numberOfPlayers; ++i){
        //    players[i] = color[i];
       // }

        int j =0;
        for(Color singleColor : colorList){

            if( j < numberOfPlayers){
                players[j]= new Player(singleColor);
                j++;
            }

        }
        startGame(numberOfPlayers,gameBag);
        return true;
    }

    @Override
    public Color[] players() {

        List<Color> test = new ArrayList<>();
        for(Player playCol : this.players){
            test.add(playCol.getPlayerColor());
        }

        Color[] test2 = new Color[test.size()];
        int i = 0;
        for(Color oneColor: test){
            test2[i]= oneColor;
            i++;
        }

        return test2;
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

    //private Bag bag = new Bag();

    @Override
    public int frogsInBag() {
        return gameBag.getNumoffrogs();
    }

    public void startGame(int spieler, Bag gamebag) {
        //bag = new Bag(spieler*10);
        for (int i = 0; i < 2*spieler; ++i){
            gamebag.takeFrog();
        }
    }

    public void takeFrogFromBag() {
        gameBag.takeFrog();
    }
}
