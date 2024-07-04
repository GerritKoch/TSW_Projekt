package de.fhkiel.tsw;

import de.fhkiel.tsw.armyoffrogs.Color;
import de.fhkiel.tsw.armyoffrogs.Game;
import de.fhkiel.tsw.armyoffrogs.Position;

import java.util.*;

import static java.lang.Math.abs;

/**
 *
 */
public class Gamelogic implements Game {
  private Player[] players;
  private Bag gameBag;
  private boolean spielLaueft;
  private int numOfPlayers;
  private int gameRound;
  private final boolean frogonBoard;
  private List<Position> sampleMovePositions;

  private final List<Frog> frogsOnBoard;
  private Set<Position> board;
  private Color selectedFrog;
  private Position selectedPosition;

  public GamePhase getCurrentGamePhase() {
    return currentGamePhase;
  }

  private GamePhase currentGamePhase;


  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  private Player currentPlayer;
  private Map<Player, List<Color>> frogsInHandMap;

  public int getGameRound() {
    return gameRound;
  }


  public enum GamePhase {
    ANLEGEN,
    NACHZIEHEN,
    BEWEGEN
  }

  public Gamelogic() {
    board = new HashSet<>();
    frogsOnBoard = new ArrayList<>();
    numOfPlayers = 0;
    frogonBoard = false;
    gameBag = new Bag();
    spielLaueft = false;
    frogsInHandMap = new HashMap<>();
    players = new Player[4];
    gameRound = 0;
  }

  @Override
  public boolean newGame(int numberOfPlayers) {

    if (numberOfPlayers < 2 || numberOfPlayers > 4) {
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


    for (int i = 0; i < numberOfPlayers; ++i) {
      players[i] = new Player(colorList.get(i));
      frogsInHandMap.put(players[i], new ArrayList<>());
    }


    currentPlayer = players[0];
    currentGamePhase = GamePhase.ANLEGEN;
    selectedFrog = null;
    spielLaueft = true;
    gameBag = new Bag(numberOfPlayers * 10, colorList);
    Collections.shuffle(gameBag.getFrogsInBag());

    for (int i = 0; i < numberOfPlayers; i++) {
      takeFrogFromBag();
    }

    for (Player player : players) {
      for (Frog frog : player.getFrogsInHand()) {
        frogsInHandMap.get(player).add(frog.getFrogColor());
      }
    }


    return true;
  }


  @Override
  public Color[] players() {


    Color[] playersColor;
    playersColor = new Color[players.length];
    if (spielLaueft) {

      List<Color> test = new ArrayList<>();
      for (Player playCol : this.players) {
        test.add(playCol.getPlayerColor());
      }
      int i = 0;
      for (Color oneColor : test) {
        playersColor[i] = oneColor;
        i++;
      }

      return playersColor;
    } else {
      return new Color[0];
    }

  }

  public int numberOfPlayers() {
    return numOfPlayers;
  }


  @Override
  public String getInfo() {
    if (!spielLaueft) {
      return "Kein Spiel gestartet.";
    }
    return "Das Spiel ist mit " + numOfPlayers + " Spielern gestartet. Aktueller Spieler: " +
        currentPlayer.getPlayerColor() + ", Phase: " + currentGamePhase + ". Turn: " + gameRound +
        ".";
  }

  @Override
  public List<Color> getFrogsInHand(Color color) {
    Player player = getPlayerByColor(color);
    if (player != null) {
      //System.out.println("getFrogsInHand(" + color + ") ausgeführt.");
      return frogsInHandMap.getOrDefault(player, new ArrayList<>());
    } else {
      System.out.println("No player found with color: " + color);
      return new ArrayList<>();
    }
  }

  public int getFrogInHandMapSize(Color color) {
    int count = 0;
    for (Map.Entry<Player, List<Color>> entry : frogsInHandMap.entrySet()) {

      for (Color color1 : entry.getValue()) {
        if (color1 == color) {
          count++;
        }
      }
    }
    return count;
  }

  public int getFrogInHandMapSize() {

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

    if (board == null) {
      board = new HashSet<>();
      return board;
    }
    return board;
  }

  @Override
  public void clicked(Position position) {
    if (selectedFrog != null && currentGamePhase == GamePhase.ANLEGEN && spielLaueft) {
      Position newPos = new Position(selectedFrog, position.x(), position.y(), position.border());
      if (anlegen(newPos)) {
        nachziehen();
        //bewegen(null);
        selectedFrog = null;
        endTurn();
        System.out.println("Change to  BEWEGEN");

        if (canMoveAnyFrog()) {
          currentGamePhase = GamePhase.BEWEGEN;
        } else {
          currentGamePhase = GamePhase.ANLEGEN;
        }


      }
    } else if (currentGamePhase == GamePhase.BEWEGEN && spielLaueft) {
      if (selectedPosition == null) {

        selectFrogToMove(position);
        sampleMovePositions = possibleMovePositions();

      } else {
        bewegen(position);
      }
    }


  }

  private boolean hasNeighbourOfSameColor(Position position) {
    for (Position pos : board) {
      if (areNeighbours(position.x(), position.y(), pos.x(), pos.y())
          && pos.frog() == currentPlayer.getPlayerColor()) {
        return true;
      }
    }
    return false;
  }

  private boolean isPositionValidForPlacement(Position pos) {
    if (isPositionOccupied(pos) || !hasNeighbour(pos)) {
      return false;
    }
    if (pos.frog() == currentPlayer.getPlayerColor()) {
      return !hasNeighbourOfSameColor(pos);
    }
    return true;
  }

//  private void checkPhases() {
//    if (currentGamePhase == GamePhase.BEWEGEN && !canMoveAnyFrog()) {
//      currentGamePhase = GamePhase.ANLEGEN;
//    }
//    if (currentGamePhase == GamePhase.ANLEGEN && !canPlaceAnyFrog()) {
//      currentGamePhase = GamePhase.NACHZIEHEN;
//    }
//    if (currentGamePhase == GamePhase.NACHZIEHEN) {
//      nachziehen();
//      bewegen(null);
//    }
//  }

  private boolean canPlaceAnyFrog() {
    if (board.isEmpty()) {
      return true; // Always possible to place the first frog
    }

    for (Color frogColor : frogsInHandMap.get(currentPlayer)) {
      for (int x = -50; x <= 50; x++) {
        for (int y = -50; y <= 50; y++) {
          Position pos = new Position(frogColor, x, y, Color.None);
          if (isPositionValidForPlacement(pos)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  private boolean selectFrogToMove(Position position) {
    for (Position pos : board) {
      if (pos.equals(position) && pos.frog() == currentPlayer.getPlayerColor()) {
        selectedPosition = pos;
        System.out.println("Frog selected for movement: " + pos);
        return true;
      }
    }
    System.out.println("Invalid frog selection: " + position);
    return false;
  }

  public void nachziehen() {
    if (currentGamePhase == GamePhase.NACHZIEHEN) {
      takeFrogFromBag(currentPlayer);

    }
  }

  private boolean canMoveFrog(Position from, Position to) {
    int dx = to.x() - from.x();
    int dy = to.y() - from.y();

    if (dx != 0 && dy != 0) {
      return false; // Move must be in a straight line
    }

    int stepX = Integer.signum(dx);
    int stepY = Integer.signum(dy);

    int currentX = from.x() + stepX;
    int currentY = from.y() + stepY;

    while (currentX != to.x() || currentY != to.y()) {
      if (!isPositionOccupied(new Position(Color.None, currentX, currentY, Color.None))) {
        return false;
      }
      currentX += stepX;
      currentY += stepY;
    }

    return true;
  }

  private boolean canMoveAnyFrog() {
    for (Position pos : board) {
      if (pos.frog() == currentPlayer.getPlayerColor()) {
        for (Position neighbor : getNeigbours(pos)) {
          if (canMoveFrog(pos, neighbor)) {
            return true;
          }
        }
      }
    }
    return false;
  }


  /**
   * Moves the selected frog to the destined position.
   *
   * @param destinedPosition The position where the frog is to be moved.
   */
  public void bewegen(Position destinedPosition) {


    //List<Position> possibleMovePositions = possibleMovePositions();

    if (sampleMovePositions == null || sampleMovePositions.isEmpty()) {
      System.out.println("No possible move positions.");
      return;
    }

    if (!sampleMovePositions.contains(destinedPosition)) {
      System.out.println("Invalid move.");
      return;
    }


    Set<Position> sampleBoard = new HashSet<>(board);
    sampleBoard.remove(selectedPosition);
    var newPos = new Position(selectedPosition.frog(), destinedPosition.x(), destinedPosition.y(),
        destinedPosition.border());
    sampleBoard.add(newPos);
    if (!validateMove(selectedPosition, newPos, sampleBoard)) {
      System.out.println("Invalid move.");
      return;
    } else {
      board = sampleBoard;
      //board.remove(selectedPosition);
      System.out.println("Moving frog from " + selectedPosition + " to " + newPos);
      //board.add(newPos);
      selectedPosition = null;
    }

    if (currentGamePhase == GamePhase.BEWEGEN && !canMoveAnyFrog()) {
      currentGamePhase = GamePhase.ANLEGEN;
    }
  }

  /**
   * Ends the current turn and proceeds to the next player.
   */
  public void endTurn() {
    // Automatically proceed to the next player

    int currentIndex = Arrays.asList(players).indexOf(currentPlayer);
    currentPlayer = players[(currentIndex + 1) % players.length];
    if (winner() != Color.None) {
      System.out.println("Game ended. Winner: " + winner());
      spielLaueft = false;
    }
    gameRound++;
    System.out.println("Turn" + gameRound + " ended. Next player: " + currentPlayer);
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

    if (currentPlayer.getPlayerColor() == player && currentGamePhase == GamePhase.ANLEGEN &&
        frogsInHandMap.containsKey(currentPlayer) &&
        frogsInHandMap.get(currentPlayer).contains(frog)) {
      selectedFrog = frog;
      frogsInHandMap.get(currentPlayer).remove(frog);
      currentPlayer.getFrogsInHand().removeIf(frog1 -> frog1.getFrogColor() == frog);
      System.out.println("selectedFrogInHand(" + player + ", " + frog + ") ausgeführt.");
    } else {
      System.out.println("Invalid frog selection.");
    }


  }

  public List<Position> possibleMovePositions() {
    List<Position> possibleMovePositions = new ArrayList<>();
    for (Position pos : board) {
      if (isPositionOccupied(pos)) {
        for (Position neighbor : getBounderingNeighbours(pos)) {
          if (!isPositionOccupied(neighbor)) {
            possibleMovePositions.add(neighbor);
          }
        }
      }
    }
    for (Position pos : possibleMovePositions) {
      System.out.println("Possible move position is : " + pos);
    }


    return possibleMovePositions;
  }

  private boolean validateMove(Position from, Position to, Set<Position> sampleBoard) {
    if (!isInStraightLine(from, to, sampleBoard)) {
      return false;
    }
    if (!isFrogBetweenUs(from, to, sampleBoard)) {
      return false;
    }
    return isZusammenhaengend(from, to, sampleBoard);
  }

  private boolean isZusammenhaengend(Position from, Position to, Set<Position> sampleBoard) {
    boolean verify = false;
    var listBoard = sampleBoard.stream().toList();
    var visited = new HashSet<Position>();
    var len = listBoard.size();
    System.out.println("len: " + len);
    var len2 = bfs(to, listBoard, visited);
    System.out.println("len2: " + len2);
    if (len == len2) {
      verify = true;
    }
    if (verify) {
      System.out.println("Zusammenhängend");
    } else {
      System.out.println("Nicht zusammenhängend");
    }
    return verify;
  }

  private boolean isFrogBetweenUs(Position from, Position to, Set<Position> sampleBoard) {
    return true;
  }

  private boolean isInStraightLine(Position from, Position to, Set<Position> sampleBoard) {
    return true;
  }

  private boolean dfs(Position frog, Set<Position> visited, Set<Position> chain, int chainLength) {
    visited.add(frog);
    chain.add(frog);

    List<Position> neighbors = getNeigbours(frog);
    if (neighbors.size() > 2) {
      return false;
    }

    for (Position neighbor : neighbors) {
      if (!visited.contains(neighbor)) {
        if (dfs(neighbor, visited, chain, chainLength)) {
          return true;
        }
      } else if (chain.size() >= chainLength && chain.contains(neighbor)) {
        return true;
      }
    }

    chain.remove(frog);

    // Check if there's an element in the chain that has only one neighbor
    for (Position position : chain) {
      if (getNeigbours(position).size() == 1) {
        return true;
      }
    }

    return false;
  }

  private boolean hatKetten(Set<Position> sampleboard) {
    Set<Position> visited = new HashSet<>();
    Set<Position> chain = new HashSet<>();
    for (Position frog : sampleboard) {
      if (!visited.contains(frog)) {
        chain.clear();
        if (dfs(frog, visited, chain, 3)) {
          return true;
        }
      }
    }
    return false;
  }


  private List<Position> getNeigbours(Position pos) {
    List<Position> neigborsByPosition = getBounderingNeighbours(pos);

    List<Position> actualNeighbors = new ArrayList<>();
    for (Position neighbor : neigborsByPosition) {
      for (Position realFrog : board) {
        if (realFrog.x() == neighbor.x() && realFrog.y() == neighbor.y()) {
          actualNeighbors.add(realFrog);
        }
      }
    }

    System.out.println("Possible neighbors for " + pos + " are:\n");


    return actualNeighbors;
  }

  List<Position> getBounderingNeighbours(Position pos) {

    List<Position> neigbors = new ArrayList<>();
    int x = pos.x();
    int y = pos.y();

    if (y == 0 || (Math.abs(y) % 2) == 0) {
      neigbors.add(new Position(Color.None, x + 1, y, pos.border())); // Right neighbor
      neigbors.add(new Position(Color.None, x - 1, y, pos.border())); // Left neighbor
      neigbors.add(
          new Position(Color.None, x, y - 1, pos.border())); // Upper-right neighbor
      neigbors.add(
          new Position(Color.None, x, y + 1, pos.border())); // Lower-right neighbor
      neigbors.add(
          new Position(Color.None, x - 1, y - 1, pos.border())); // Upper-left neighbor
      neigbors.add(
          new Position(Color.None, x - 1, y + 1, pos.border())); // Lower-left neighbor
    } else {
      neigbors.add(new Position(Color.None, x + 1, y, pos.border())); // Right neighbor
      neigbors.add(new Position(Color.None, x - 1, y, pos.border())); // Left neighbor
      neigbors.add(
          new Position(Color.None, x + 1, y - 1, pos.border())); // Upper-right neighbor
      neigbors.add(
          new Position(Color.None, x + 1, y + 1, pos.border())); // Lower-right neighbor
      neigbors.add(
          new Position(Color.None, x, y - 1, pos.border())); // Upper-left neighbor
      neigbors.add(
          new Position(Color.None, x, y + 1, pos.border())); // Lower-left neighbor
    }

    return neigbors;

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

          if (size == 7 && count == 7) {
            return player.getPlayerColor();
          }

          if (size > 7 && count == size) {
            return player.getPlayerColor();
          }


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
    if (gameBag.getNumoffrogs() > 0) {
      for (Player player : players) {
        if (player.getFrogsInHand().size() < 2) {
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

    if (gameBag.getNumoffrogs() > 0 && player.getFrogsInHand().size() < 2 &&
        frogsInHandMap.get(currentPlayer).size() < 2) {

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

    if (getBoard().isEmpty()) {
      board.add(pos);
      System.out.println("Erstes Anlegen ist erfolgreich.");
      currentGamePhase = GamePhase.NACHZIEHEN;
      return true;
    }
    if (!isPositionOccupied(pos) && hasNeighbour(pos)) {

      board.add(pos);
//      sampleBoard = new HashSet<>(board);
//      sampleBoard.add(pos);
//      if (hatKetten(sampleBoard)) {
//        System.out.println("Kette gebildet.");
//        return false;
//      } else {
//        board.add(pos);
//        System.out.println("Anlegen erfolgreich.");
//        currentGamePhase = GamePhase.NACHZIEHEN;
      return true;
    }


    return false;
  }


  public boolean areNeighbours(int q1, int r1, int q2, int r2) {

    if (r1 == 0 || (abs(r1) % 2) == 0) {

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
          (q2 == q1 && r2 == r1 - 1) || // Upper-left neighbor
          (q2 == q1 && r2 == r1 + 1); // Lower-left neighbor
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
