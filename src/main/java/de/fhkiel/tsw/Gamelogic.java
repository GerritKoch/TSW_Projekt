package de.fhkiel.tsw;

import de.fhkiel.tsw.armyoffrogs.Color;
import de.fhkiel.tsw.armyoffrogs.Game;
import de.fhkiel.tsw.armyoffrogs.Position;

import java.util.*;

import static java.lang.Math.abs;

///
public class Gamelogic implements Game {
    private Player[] players;
    private Bag gameBag;
    private boolean spielLaueft;
    private int numOfPlayers;
    private int gameRound;
    private final boolean frogonBoard;

    private final List<Frog> frogsOnBoard;
    private Set<Position> board;
    private Color selectedFrog;

    public GamePhase getCurrentGamePhase() {
        return currentGamePhase;
    }

    private GamePhase currentGamePhase;

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    private Player currentPlayer;
    private Map<Player,List<Color>> frogsInHandMap;

    public int getGameRound() {
        return gameRound ;
    }


    public enum GamePhase {
        ANLEGEN,
        NACHZIEHEN,
        BEWEGEN
    }

    public Gamelogic(){
        board = new HashSet<>();
        frogsOnBoard = new ArrayList<>();
        numOfPlayers =0;
        frogonBoard = false;
        gameBag = new Bag();
        spielLaueft = false;
        frogsInHandMap = new HashMap<>();
        players =new Player[4];
        gameRound = 0;
    }

    @Override
    public boolean newGame(int numberOfPlayers) {

        if (numberOfPlayers < 2 || numberOfPlayers > 4){
            spielLaueft = false;
            return false;
        }

        frogsInHandMap.clear();
        board.clear();
        spielLaueft = false;
        gameBag = null;


        gameRound = 0;
        numOfPlayers = numberOfPlayers;
        players = new Player[numberOfPlayers];



        List<Color> colorList = new ArrayList<>();
        colorList.add(Color.Red);
        colorList.add(Color.Blue);
        colorList.add(Color.Green);
        colorList.add(Color.Black);


        if (numberOfPlayers < colorList.size()) {
            // If num is smaller, remove elements to decrease the size
            colorList.subList(numberOfPlayers, colorList.size()).clear();
        }



        for(int i = 0; i < numberOfPlayers; ++i){
            players[i] = new Player(colorList.get(i));
            frogsInHandMap.put(players[i], new ArrayList<>());
        }


        currentPlayer = players[0];
        currentGamePhase = GamePhase.ANLEGEN;
        selectedFrog = null;
        spielLaueft = true;
        gameBag = new Bag(numberOfPlayers*10,colorList);
        Collections.shuffle(gameBag.getFrogsInBag());

        for(int i = 0; i < numberOfPlayers; i++){
            takeFrogFromBag();
        }

    for (Player player : players){
        for (Frog frog : player.getFrogsInHand()){
            frogsInHandMap.get(player).add(frog.getFrogColor());
        }
    }




        return true;
    }


    @Override
    public Color[] players() {



        Color[] playersColor;
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


    @Override
    public String getInfo() {
        if(!spielLaueft){
            return "Kein Spiel gestartet.";
        }
        return "Das Spiel ist mit " + numOfPlayers + " Spielern gestartet. Aktueller Spieler: " + currentPlayer.getPlayerColor() + ", Phase: " + currentGamePhase + ". Turn: " + gameRound + ".";
    }

    @Override
    public List<Color> getFrogsInHand(Color color) {
        Player player = getPlayerByColor(color);
        if (player != null) {
            System.out.println("getFrogsInHand(" + color + ") ausgeführt.");
            return frogsInHandMap.getOrDefault(player, new ArrayList<>());
        } else {
            System.out.println("No player found with color: " + color);
            return new ArrayList<>();
        }
    }

    public int getFrogInHandMapSize( Color color){
        int count = 0;
        for (Map.Entry<Player, List<Color>> entry : frogsInHandMap.entrySet()) {

            for (Color color1 : entry.getValue()){
                if(color1 == color){
                    count++;
                }
            }
        }
        return count;
    }

    public int getFrogInHandMapSize(){

        int count = 0;
        for (Map.Entry<Player, List<Color>> entry : frogsInHandMap.entrySet()) {

            count += entry.getValue().size();
        }
        return count;
    }

    public Player getPlayerByColor(Color color) {
        for (Player player : players) {
            if (player.getPlayerColor() == color) {
                return player;
            }
        }
        return null; // Return null if no player with the given color is found
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
        if (selectedFrog != null && currentGamePhase == GamePhase.ANLEGEN && spielLaueft) {
            Position newPos = new Position(selectedFrog, position.x(), position.y(), position.border());
            if(anlegen(newPos)){
                nachziehen();
                bewegen();;
                selectedFrog = null;
                endTurn();

            }
        }


    }

    public void nachziehen(){
        if(currentGamePhase == GamePhase.NACHZIEHEN){
            takeFrogFromBag(currentPlayer);
           currentGamePhase = GamePhase.BEWEGEN;
        }
    }

    public void bewegen(){
        if(currentGamePhase == GamePhase.BEWEGEN){
            currentGamePhase = GamePhase.ANLEGEN;
        }
    }


    public void endTurn() {
        // Automatically proceed to the next player

        int currentIndex = Arrays.asList(players).indexOf(currentPlayer);
        currentPlayer = players[(currentIndex + 1) % players.length];
        if (winner() != Color.None) {
            System.out.println("Game ended. Winner: " + winner());
            spielLaueft = false;
        }
        gameRound++;
        System.out.println("Turn" + gameRound+" ended. Next player: " + currentPlayer);
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

    if (currentPlayer.getPlayerColor() == player && currentGamePhase == GamePhase.ANLEGEN && frogsInHandMap.containsKey(currentPlayer) &&frogsInHandMap.get(currentPlayer).contains(frog) ) {
        selectedFrog = frog;
        frogsInHandMap.get(currentPlayer).remove(frog);
        currentPlayer.getFrogsInHand().removeIf(frog1 -> frog1.getFrogColor() == frog);
        System.out.println("selectedFrogInHand(" + player + ", " + frog + ") ausgeführt.");
    } else {
        System.out.println("Invalid frog selection.");
    }


    }

    @Override
    public Color winner() {
        System.out.println("winner() ausgeführt.");

        for (Player player : players) {
            List<Position> positionsOfSinglePlayer = new ArrayList<>();
            for (Position pos : getBoard()) {
                if (pos.frog() == player.getPlayerColor()) {
                    positionsOfSinglePlayer.add(pos);
                }
            }
            if (positionsOfSinglePlayer.size() >= 7) {
                for (Position start : positionsOfSinglePlayer) {
                    Set<Position> visited = new HashSet<>();
                    int count = bfs(start, positionsOfSinglePlayer, visited);
                    int size = positionsOfSinglePlayer.size();

                    if(size == 7 && count == 7){
                        return player.getPlayerColor();
                    } else if (size > 7 && count == size)
                        return player.getPlayerColor();



                }
            }
        }

        return Color.None;
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

    public int frogsInBagWithColor(Color color) {
        return gameBag.getFrogsInBag(color).size();
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

    public int bfs(Position start, List<Position> positionsOfSinglePlayer, Set<Position> visited) {
        Queue<Position> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);
        int count = 1;

        while (!queue.isEmpty()) {
            Position current = queue.poll();
            int q1 = current.x();
            int r1 = current.y();

            for (Position next : positionsOfSinglePlayer) {
                int q2 = next.x();
                int r2 = next.y();
                if (!visited.contains(next) && areNeighbours(q1, r1, q2, r2)) {
                    queue.add(next);
                    visited.add(next);
                    count++;
                }
            }
        }

        return count;
    }

    public void takeFrogFromBag(Player player) {

        if(gameBag.getNumoffrogs() > 0 && player.getFrogsInHand().size() < 2 &&frogsInHandMap.get(currentPlayer).size() < 2){

                Frog takenFrog = gameBag.takeFrog();
                player.setMyFrogs(takenFrog);
                player.setFrogsInHand(takenFrog);
                frogsInHandMap.get(player).add(takenFrog.getFrogColor());

        }
    }


    public boolean endGame() {
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

        if(getBoard().isEmpty()){
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
