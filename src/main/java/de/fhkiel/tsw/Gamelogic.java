package de.fhkiel.tsw;

import de.fhkiel.tsw.armyoffrogs.Color;
import de.fhkiel.tsw.armyoffrogs.Game;
import de.fhkiel.tsw.armyoffrogs.Position;

import java.util.*;

import static java.lang.Math.abs;

///
public class Gamelogic implements Game {
    private Player[] players;
    private Color[] playersColor;
    private List<Player> playerList;
    private Bag gameBag;
    private boolean spielLaueft;
    private int numOfPlayers;
    private int gameRound;
    private boolean frogonBoard;
    private int x;
    private int y;
    private List<Frog> frogsOnBoard;
    private Set<Position> board;
    private Color selectedFrog;
    private GamePhase currentGamePhase;
    private Player currentPlayer;
    List<Color> frogsInHand;
    int turncount;

    private enum GamePhase {
        ANLEGEN,
        NACHZIEHEN
    }

    public Gamelogic(){
        board = new HashSet<>();
        frogsOnBoard = new ArrayList<>();
        numOfPlayers =0;
        frogonBoard = false;
        gameBag = new Bag();
        spielLaueft = false;
        frogsInHand = new ArrayList<>();
        players =new Player[4];
        turncount = 0;
    }

    @Override
    public boolean newGame(int numberOfPlayers) {

        if (numberOfPlayers < 2 || numberOfPlayers > 4){
            spielLaueft = false;
            return false;
        }

        frogsInHand.clear();
        board.clear();
        spielLaueft = false;


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



        for(int i = 0; i < numberOfPlayers; ++i){
            players[i] = new Player(colorList.get(i));
        }


//        int j =0;
//        for(Color singleColor : colorList){
//
//            if( j < numberOfPlayers){
//                players[j]= new Player(singleColor);
//                j++;
//            }
//
//        }

        currentPlayer = players[0];
        currentGamePhase = GamePhase.ANLEGEN;
        selectedFrog = null;
        spielLaueft = true;
        gameBag = new Bag(numberOfPlayers*10,colorList);
        Collections.shuffle(gameBag.getFrogsInBag());

        for(int i = 0; i < numberOfPlayers; i++){
            takeFrogFromBag();
        }

       // startGame(numberOfPlayers,gameBag);
        return true;
    }


    @Override
    public Color[] players() {




        playersColor = new Color[players.length];
        if (spielLaueft){

            List<Color> test = new ArrayList<>();
            for(Player playCol : this.players){
                test.add(playCol.getPlayerColor());
            }
            int i = 0;
            for(Color oneColor: test){
                playersColor[i]= oneColor;
                i++;
            }

            return playersColor;
        }else

            return new Color[0];

    }

    public int numberOfPlayers() {
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
        return "Das Spiel ist mit " + numOfPlayers + " Spielern gestartet. Aktueller Spieler: " + currentPlayer.getPlayerColor() + ", Phase: " + currentGamePhase + ". Turn: " + turncount + ".";
    }

    @Override
    public List<Color> getFrogsInHand(Color player) {

        frogsInHand.clear();
        for(Player play : players){
            if(play.getPlayerColor() == player){
                for(Frog frog : play.getFrogsInHand()){
                    frogsInHand.add(frog.getFrogColor());
                }
                break;
            }
        }
        return frogsInHand;
    }

    @Override
    public Set<Position> getBoard() {

        if(board == null)
        {
            board = new HashSet<>();
            return board;
        }
        return board;
    }

    @Override
    public void clicked(Position position) {
        /*if (currentPhase == GamePhase.ANLEGEN && selectedFrog != null) {
            if (isPositionOccupied(position)) {
                System.out.println("Position " + position + " ist bereits besetzt.");
            } else {
                board.add(new Position(selectedFrog, position.x(), position.y(), position.border()));
                selectedFrog = null;
                System.out.println("clicked(" + position + ") ausgeführt.");
                // Automatically proceed to the next phase (Nachziehen)
                currentPhase = GamePhase.NACHZIEHEN;
                drawFrog();
                endTurn();
            }
        } else {
            System.out.println("Invalid action for current phase or no frog selected.");
        }*/

        //board.add()

        //board.add( )
        if (selectedFrog != null && currentGamePhase == GamePhase.ANLEGEN) {
            Position newPos = new Position(selectedFrog, position.x(), position.y(), position.border());
            if(anlegen(newPos)){
                takeFrogFromBag(currentPlayer);
                selectedFrog = null;
                endTurn();

            }
        }


    }

    private void endTurn() {
        // Automatically proceed to the next player
        int currentIndex = Arrays.asList(players).indexOf(currentPlayer);
        currentPlayer = players[(currentIndex + 1) % players.length];
        currentGamePhase = Gamelogic.GamePhase.ANLEGEN;
        turncount++;
        System.out.println("Turn" + turncount+" ended. Next player: " + currentPlayer);
    }
    private boolean isPositionOccupied(Position position) {
        for (Position pos : board) {
            if (pos.x() == position.x() && pos.y() == position.y()) {
                System.out.println("Position " + position + " ist bereits besetzt.");
                return true;
            }
        }

        return false;
    }
    @Override
    public void selectedFrogInHand(Color player, Color frog) {

    if (currentPlayer.getPlayerColor() == player && currentGamePhase == GamePhase.ANLEGEN) {
        selectedFrog = frog;
        frogsInHand.remove(frog);
        currentPlayer.getFrogsInHand().removeIf(frog1 -> frog1.getFrogColor() == frog);
        System.out.println("selectedFrogInHand(" + player + ", " + frog + ") ausgeführt.");
    } else {
        System.out.println("Invalid frog selection.");
    }


    }

    @Override
    public Color winner() {

        boolean testWinner = false;
        List<Color> currentPlayerColors = new ArrayList<>();
        List<Position> positionsOfSinglePlayer = new ArrayList<>();
        for(Player play : players){
            currentPlayerColors.add(play.getPlayerColor());
        }
        for (Color playerColor: currentPlayerColors){

            for(Position pos : getBoard()){
                if(pos.frog() == playerColor){
                    positionsOfSinglePlayer.add(pos);
                }
            }
            if(positionsOfSinglePlayer.size() >= 7){

                for(int i= 0; i < positionsOfSinglePlayer.size() - 1; i++){

                    int q1 = positionsOfSinglePlayer.get(i).x();
                    int r1 = positionsOfSinglePlayer.get(i).y();
                    int q2 = positionsOfSinglePlayer.get(i+1).x();
                    int r2 = positionsOfSinglePlayer.get(i+1).y();
                    if(areNeighbours(q1,r1,q2,r2)){
                        testWinner = true;
                    }
                    else{
                        testWinner = false;
                    }
                }

                if(testWinner){
                    return playerColor;
                }

            }

        }


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
        if(gameBag.getNumoffrogs() > 0){
            for(Player player : players){
                if(player.getFrogsInHand().size() < 2)  {
                    Frog takenFrog = gameBag.takeFrog();
                    player.setMyFrogs(takenFrog);
                    player.setFrogsInHand(takenFrog);
                }
            }
        }
    }

    public void takeFrogFromBag(Player player) {

        if(gameBag.getNumoffrogs() > 0){
            if(player.getFrogsInHand().size() < 2)  {
                Frog takenFrog = gameBag.takeFrog();
                player.setMyFrogs(takenFrog);
                player.setFrogsInHand(takenFrog);
            }
        }
    }


    public boolean endGame() {
        spielLaueft = false;
        return !spielLaueft;
    }

    public void setGameRound(int gameRound) {
        this.gameRound = gameRound;
    }

    public boolean isFrogonBoard() {
        return frogonBoard;
    }

    public Bag getGameBag() {
        return gameBag;
    }

    public Player[] getPlayers() {
        return players;
    }

    public boolean anlegen(Position pos) {

        if(turncount == 0){
            board.add(pos);
            System.out.println("Erstes Anlegen ist erfolgreich.");
            currentGamePhase = GamePhase.NACHZIEHEN;
            return true;
        }
        if( !isPositionOccupied(pos) && hasNeighbour(pos)){
               board.add(pos);
                System.out.println("Anlegen erfolgreich.");
                currentGamePhase = GamePhase.NACHZIEHEN;
                return true;
           }

        return false;
    }

    public void bewegen(Frog frog, int x, int y) {

        try{
            if(!frog.isFrogInGame()){
                return;
            }

            for (Position pos : board){
                if(pos.x() == frog.getPosition().x() && pos.y() == frog.getPosition().y()){
                    board.remove(pos);
                    break;
                }
            }
            frog.setPosition(null);
            Color borderColor = Color.None;

            for(Frog frog1 : frogsOnBoard){
                if(frog1.getPosition().x() == x && frog1.getPosition().y() == y){
                    frog1.setPosition(null);
                    return;
                }

                if(frog1.getPosition().x() == x || frog1.getPosition().y() == y){
                    borderColor = frog1.getPosition().border();
                }

            }
            Position currentFrogPosition = new Position(frog.getFrogColor(),x,y,borderColor);
            frog.setPosition(currentFrogPosition);

        }
        catch (Exception e){
            e.printStackTrace();}


    }

    public void nachzeihen(Color color) {
        takeFrogFromBag();
    }

    public boolean areNeighbours(int q1,int r1, int q2, int r2) {

        if(r1 == 0 || (abs(r1) % 2) == 0){

            return (q2 == q1 + 1 && r2 == r1) || // Right neighbor
                    (q2 == q1 - 1 && r2 == r1) || // Left neighbor
                    (q2 == q1 && r2 == r1 - 1) || // Upper-right neighbor
                    (q2 == q1 && r2 == r1 + 1) || // Lower-right neighbor
                    (q2 == q1 - 1 && r2 == r1 - 1) || // Upper-left neighbor
                    (q2 == q1 - 1 && r2 == r1 + 1); // Lower-left neighbor

        } else {
            return (q2 == q1 + 1 && r2 == r1) || // Right neighbor
                    (q2 == q1 - 1 && r2 == r1) || // Left neighbor
                    (q2 == q1 + 1 && r2 == r1 - 1) || // Upper-right neighbor
                    (q2 == q1 + 1 && r2 == r1 + 1) || // Lower-right neighbor
                    (q2 == q1  && r2 == r1 - 1) || // Upper-left neighbor
                    (q2 == q1  && r2 == r1 + 1); // Lower-left neighbor
        }


    }

    public boolean hasNeighbour(Position pos1) {
        for (Position pos2 : board) {
            if (areNeighbours(pos1.x(), pos1.y(), pos2.x(), pos2.y())) {
                return true;
            }
        }
        return false;
    }

}
