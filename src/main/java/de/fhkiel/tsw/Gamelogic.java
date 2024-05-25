package de.fhkiel.tsw;

import de.fhkiel.tsw.armyoffrogs.Color;
import de.fhkiel.tsw.armyoffrogs.Game;
import de.fhkiel.tsw.armyoffrogs.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Gamelogic implements Game {

    private Player[] players;
    private List<Player> playerList;
    private Bag gameBag;
    private boolean spielLaueft;
    private int numOfPlayers = 0;
    private int gameRound;
    private boolean frogonBoard = false;


    @Override
    public boolean newGame(int numberOfPlayers) {

        if(numberOfPlayers < 2 || numberOfPlayers > 4){

            spielLaueft = false;

            return false;

        }

        gameRound = 0;
        numOfPlayers = numberOfPlayers;
        players = new Player[numberOfPlayers];
        //playerList = new ArrayList<>();

        Color color[] = {Color.Red, Color.Blue, Color.Green, Color.Black,Color.White,Color.Black};
        List<Color> colorList = new ArrayList<>();
        colorList.add(Color.Red);
        colorList.add(Color.Blue);
        colorList.add(Color.Green);
        colorList.add(Color.Black);

        //Collections.shuffle(colorList);
        if (numberOfPlayers < colorList.size()) {
            // If num is smaller, remove elements to decrease the size
            for (int i = colorList.size() - 1; i >= numberOfPlayers; i--) {
                colorList.remove(i);
            }
        }



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

        gameBag = new Bag(numberOfPlayers*10,colorList);
       // startGame(numberOfPlayers,gameBag);
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
        //return players.length;
        return numOfPlayers;
    }

    public int numofFrogsPlayed(){
        int frogsPlayed = 0;
        for(Player player : players){
            frogsPlayed += gameBag.getFrogsInBag(player.getPlayerColor()).size();
        }
        return gameBag.getNumoffrogs();
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

    public int frogsInBag_withColor(Color color) {
        return gameBag.getFrogsInBag(color).size();
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

    public boolean endGame() {
        spielLaueft = false;
        return !spielLaueft;
    }

    public void setGameRound(int gameRound) {
        this.gameRound = gameRound;
    }

    private void updateBagContent(){
        gameBag.takeFrog();
    }


    public boolean isFrogonBoard() {
        return frogonBoard;
    }

    public Bag getGameBag() {
        return gameBag;
    }
}
