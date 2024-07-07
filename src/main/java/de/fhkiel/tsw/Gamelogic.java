package de.fhkiel.tsw;

import static java.lang.Math.abs;

import de.fhkiel.tsw.armyoffrogs.Color;
import de.fhkiel.tsw.armyoffrogs.Game;
import de.fhkiel.tsw.armyoffrogs.Position;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


/**
 * @summary This class implements the game logic of the Army of Frogs game.
 */

public class Gamelogic implements Game {
  private Player[] players;
  private Bag gameBag;
  private boolean spielLaueft;
  private int numOfPlayers;
  private int gameRound;
  private final boolean frogonBoard;
  private List<Position> sampleMovePositions;

  private Set<Position> board;
  private Color selectedFrog;
  private Position selectedPosition;

  private List<Frog> originalFrogsList;
  private List<Frog> shuffledFrogsList;


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

  public Direction currentDirection;


  /**
   * is an enum class that represents the different phases of the game.
   * ANLEGEN: In this phase, players take turns placing their frogs on the board.
   * NACHZIEHEN: In this phase, players take turns drawing frogs from the bag.
   * BEWEGEN: In this phase, players take turns moving their frogs on the board.
   */
  public enum GamePhase {
    ANLEGEN,
    NACHZIEHEN,
    BEWEGEN
  }

  /**
   * is an enum class that represents the different directions in which a frog can move.
   *
   * <p>LEFT: Move to the left.
   * RIGHT: Move to the right.
   * UPPER_RIGHT: Move to the upper right.
   * UPPER_LEFT: Move to the upper left.
   * LOWER_LEFT: Move to the lower left.
   * LOWER_RIGHT: Move to the lower right.
   */
  public enum Direction {
    LEFT,
    RIGHT,
    UPPER_RIGHT,
    UPPER_LEFT,
    LOWER_LEFT,
    LOWER_RIGHT
  }

  public Gamelogic() {
    board = new HashSet<>();

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
    colorList.add(Color.White);


    Collections.shuffle(colorList);

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
    originalFrogsList = gameBag.getFrogsInBag();
    shuffledFrogsList = new ArrayList<>(originalFrogsList);
    Collections.shuffle(shuffledFrogsList);
    gameBag.setFrogsInBag(shuffledFrogsList);

    shuffledFrogsList = gameBag.getFrogsInBag();


    for (int i = 0; i < numberOfPlayers; i++) {
      takeFrogFromBag();
    }

    for (Player player : players) {
      for (Frog frog : player.getFrogsInHand()) {
        frogsInHandMap.get(player).add(frog.getFrogColor());
      }
    }

    selectedPosition = null;

    return true;
  }


  private boolean isShuffled(List<Frog> originalList, List<Frog> shuffledList) {
    // Check if both lists are of the same size
    if (originalList.size() != shuffledList.size()) {
      return true; // If sizes differ, it's considered shuffled
    }

    // Iterate through the lists and compare each element
    for (int i = 0; i < originalList.size(); i++) {
      if (!originalList.get(i).getFrogColor().equals(shuffledList.get(i).getFrogColor())) {
        return true; // Found an element that is out of place
      }
    }

    // If we reach here, every element was in its original place
    return false;

  }

  public boolean isShuffled() {
    return isShuffled(originalFrogsList, shuffledFrogsList);
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
    return "Das Spiel ist mit " + numOfPlayers + " Spielern gestartet. Aktueller Spieler: "
        +
        currentPlayer.getPlayerColor() + ", Phase: " + currentGamePhase + ". Turn: " + gameRound
        +
        ".";
  }

  @Override
  public List<Color> getFrogsInHand(Color color) {
    Player player = getPlayerByColor(color);
    if (player != null) {
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

  public int getFrogInPlayerHandMapSize(Player player) {
    int count = 0;
    for (Map.Entry<Player, List<Color>> entry : frogsInHandMap.entrySet()) {

      for (Color color1 : entry.getValue()) {
        if (entry.getKey() == player) {
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
        selectedFrog = null;
        endTurn();

        if (!checkMovePossible()) {
          currentGamePhase = GamePhase.ANLEGEN;
        }

      }
    } else if (currentGamePhase == GamePhase.BEWEGEN && spielLaueft) {
      if (selectedPosition == null) {

        selectFrogToMove(position);
        sampleMovePositions = possibleMovePositions();

        //wenn ich auf mich selbst klicke, dann soll ich die Bewegung beenden
      } else if (selectedPosition.x() == position.x() && selectedPosition.y() == position.y()) {
        System.out.println("End Bewegen phase");

        if (validateFinalMove(selectedPosition, board)) {
          selectedPosition = null;
          currentGamePhase = GamePhase.ANLEGEN;
        } else {
          String message = "Invalid final move.";

          System.out.println("Invalid final move.");
        }
      } else {
        bewegen(position);
      }
    }


  }

  public boolean jumpMove() {
    if (!checkMovePossible()) {
      currentGamePhase = GamePhase.ANLEGEN;
      return false;
    }
    return true;
  }

  public void changePhase() {
    if (currentGamePhase == GamePhase.ANLEGEN) {
      currentGamePhase = GamePhase.BEWEGEN;
    } else if (currentGamePhase == GamePhase.BEWEGEN && checkMovePossible()) {
      currentGamePhase = GamePhase.ANLEGEN;
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
    if (pos.frog() == currentPlayer.getPlayerColor()) {
      return !hasNeighbourOfSameColor(pos);
    }
    return true;
  }


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

  public boolean nachziehen() {

    if (getFrogInHandMapSize(currentPlayer.getPlayerColor()) >= 2) {
      return false;
    }

    if (currentGamePhase == GamePhase.NACHZIEHEN
        &&
        !gameBag.getFrogsInBag(currentPlayer.getPlayerColor()).isEmpty()) {
      takeFrogFromBag(currentPlayer);
      return true;
    }

    return false;
  }

  public void setCurrentGamePhase(GamePhase currentGamePhase) {
    this.currentGamePhase = currentGamePhase;
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

  public boolean checkMovePossible() {

    boolean movePossible = false;
    var color = currentPlayer.getPlayerColor();
    var frogs = board.stream().filter(p -> p.frog() == color).toList();
    var boardsize = board.size();

    if (boardsize == 0) {
      return movePossible;
    }


    if (boardsize > 1 && !frogs.isEmpty()) {
      movePossible = true;
    }


    return movePossible;
  }

  private boolean canMoveAnyFrog() {
    for (Position pos : board) {
      if (pos.frog() == currentPlayer.getPlayerColor()) {
        for (Position neighbor : getNeighbours(pos, board)) {
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
  public boolean bewegen(Position destinedPosition) {


    //List<Position> possibleMovePositions = possibleMovePositions();

    if (sampleMovePositions == null || sampleMovePositions.isEmpty()) {
      System.out.println("No possible move positions.");

      return false;
    }

    if (!sampleMovePositions.contains(destinedPosition)) {
      System.out.println("Invalid move.");
      return false;
    }


    Set<Position> sampleBoard = new HashSet<>(board);
    sampleBoard.remove(selectedPosition);
    var newPos = new Position(selectedPosition.frog(), destinedPosition.x(), destinedPosition.y(),
        destinedPosition.border());
    sampleBoard.add(newPos);
    if (!validateInitialMoves(selectedPosition, newPos, sampleBoard)) {
      System.out.println("Invalid move.");
      return false;
    } else {
      board = sampleBoard;
      System.out.println("Moving frog from " + selectedPosition + " to " + newPos);
      selectedPosition = newPos;
      sampleMovePositions = possibleMovePositions();
    }

    if (currentGamePhase == GamePhase.BEWEGEN && !checkMovePossible()) {
      selectedPosition = null;
      currentGamePhase = GamePhase.ANLEGEN;
    }

    return true;
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
    currentGamePhase = GamePhase.BEWEGEN;
  }

  private boolean isPositionOccupied(Position position) {
    for (Position pos : board) {
      if (pos.x() == position.x() && pos.y() == position.y()) {
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
      System.out.println("selectedFrogInHand(Player: " + player + ", Frog: " + frog + ")");
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
//    for (Position pos : possibleMovePositions) {
//      System.out.println("Possible move position is : " + pos);
//    }


    return possibleMovePositions;
  }

  private boolean validateInitialMoves(Position from, Position to, Set<Position> sampleBoard) {


    if (isInStraightLine(from, to)
        &&
        isFrogBetweenUs(from, to, sampleBoard)
        &&
        hasNoChains(sampleBoard)) {
      System.out.println("Valid move");
      return true;
    }

    return false;
  }

  private boolean validateFinalMove(Position to, Set<Position> sampleBoard) {
    if (isZusammenhaengend(to, sampleBoard)
        &&
        hasNoChains(sampleBoard)) {
      System.out.println("Valid  final move");
      return true;
    }

    return false;
  }

  private int gcd(int a, int b) {
    while (b != 0) {
      int temp = b;
      b = a % b;
      a = temp;
    }
    return a;
  }

  private boolean isZusammenhaengend(Position to, Set<Position> sampleBoard) {
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


  public boolean isFrogBetweenUs(Position from, Position to, Set<Position> sampleBoard2) {
    int q = from.x();
    int r = from.y();
    int dq = 0;
    int dr = 0;

    switch (currentDirection) {
      case RIGHT -> dq = 1;
      case LEFT -> dq = -1;
      case UPPER_RIGHT -> {
        dr = -1;
        if (r % 2 != 0) {
          dq = 1;
        }
      }
      case UPPER_LEFT -> {
        dr = -1;
        if (r % 2 == 0) {
          dq = -1;
        }
      }
      case LOWER_RIGHT -> {
        dr = 1;
        if (r % 2 != 0) {
          dq = 1;
        }
      }
      case LOWER_LEFT -> {
        dr = 1;
        if (r % 2 == 0) {
          dq = -1;
        }
      }
    }

    var sampleBoard = new HashSet<>(sampleBoard2);

    q += dq;
    r += dr;

    int aq = q;
    int ar = r;
    int adq = dq;
    int adr = dr;

    while (!(aq == to.x() && ar == to.y()) && aq >= -50 && ar >= -50 && aq <= 50 && ar <= 50) {

      int finalAq = aq;
      int finalAr = ar;
      //System.out.println("Fill path");
      if (sampleBoard.stream().noneMatch(p -> p.x() == finalAq && p.y() == finalAr)) {
        sampleBoard.add(new Position(Color.None, aq, ar, Color.None));
      }

      aq += adq;
      ar += adr;
    }


    while (!(q == to.x() && r == to.y())) {
      int finalQ = q;
      int finalR = r;
      Position current = sampleBoard.stream()
          .filter(p -> p.x() == finalQ && p.y() == finalR)
          .findFirst()
          .orElse(null);
      if (current == null) {
        System.out.println("Current is null");
        return false; // Invalid move, position not part of the board

      } else if (!current.frog().equals(Color.None)) {
        System.out.println("Frog in the path");
        return true; // Found a frog in the path

      }
      q += dq;
      r += dr;
    }


    System.out.println("No frog in the path");
    return false; // No frog in the path
  }


  private boolean isInStraightLine(Position from, Position to) {

    if (checkDirection(from.x(), from.y(), to.x(), to.y())) {
      System.out.println("Is in straight line");
      return true;
    }
    System.out.println("Is not in straight line");
    return false;
  }

  private boolean checkDirection(int q1, int r1, int q2, int r2) {

    int size = board.size() + 1;


    int lQ1 = q1, rQ1 = q1, ulQ1 = q1, urQ1 = q1, llQ1 = q1, lrQ1 = q1;
    int lR1 = r1, rR1 = r1, ulR1 = r1, urR1 = r1, llR1 = r1, lrR1 = r1;
    int lQ2 = q2, rQ2 = q2, ulQ2 = q2, urQ2 = q2, llQ2 = q2, lrQ2 = q2;
    int lR2 = r2, rR2 = r2, ulR2 = r2, urR2 = r2, llR2 = r2, lrR2 = r2;

    for (int i = 0; i < size; i++) {


      //check right
      if (rR1 == rR2 && ++rQ1 == rQ2) {
        currentDirection = Direction.RIGHT;
        System.out.println("Is Right");
        return true;
      }

      //check left
      if (lR1 == lR2 && --lQ1 == lQ2) {
        currentDirection = Direction.LEFT;
        System.out.println("Is Left");
        return true;
      }

      //check upper right
      if (urR1 == 0 || (Math.abs(urR1) % 2) == 0) {
        --urR1;
        if (urQ1 == urQ2 && urR1 == urR2) {
          currentDirection = Direction.UPPER_RIGHT;
          System.out.println("Is Upper Right");
          return true;
        }
      } else {
        ++urQ1;
        --urR1;
        if (urQ1 == urQ2 && urR1 == urR2) {
          currentDirection = Direction.UPPER_RIGHT;
          System.out.println("Is Upper Right");
          return true;
        }
      }

      //check lower right
      if (lrR1 == 0 || (Math.abs(lrR1) % 2) == 0) {
        ++lrR1;
        if (lrQ1 == lrQ2 && lrR1 == lrR2) {
          currentDirection = Direction.LOWER_RIGHT;
          System.out.println("Is Lower Right");
          return true;
        }
      } else {
        ++lrQ1;
        ++lrR1;
        if (lrQ1 == lrQ2 && lrR1 == lrR2) {
          currentDirection = Direction.LOWER_RIGHT;
          System.out.println("Is Lower Right");
          return true;
        }
      }

      //check upper left
      if (ulR1 == 0 || (Math.abs(ulR1) % 2) == 0) {
        --ulR1;
        --ulQ1;
        if (ulQ1 == ulQ2 && ulR1 == ulR2) {
          currentDirection = Direction.UPPER_LEFT;
          System.out.println("Is Upper Left");
          return true;
        }
      } else {
        ulR1--;
        if (ulQ1 == ulQ2 && ulR1 == ulR2) {
          currentDirection = Direction.UPPER_LEFT;
          System.out.println("Is Upper Left");
          return true;
        }
      }

      //check lower left
      if (llR1 == 0 || (Math.abs(llR1) % 2) == 0) {
        llQ1--;
        llR1++;
        if (llQ1 == llQ2 && llR1 == llR2) {
          currentDirection = Direction.LOWER_LEFT;
          System.out.println("Is Lower Left");
          return true;
        }
      } else {
        llR1++;
        if (llQ1 == llQ2 && llR1 == llR2) {
          currentDirection = Direction.LOWER_LEFT;
          System.out.println("Is Lower Left");
          return true;
        }
      }

    }


    return false;
  }


  // DFS-Methode zur Zählung der Größe der verbundenen Komponente

  private boolean dfs(Position frog, Set<Position> visitedOld, Set<Position> chainOld,
                      int chainLength, int aufRuf, Set<Position> sampleBoard) {

    System.out.println("Frog: " + frog.frog() + " at " + frog.x() + " " + frog.y());

    var visited = new HashSet<>(visitedOld);
    var chain = new HashSet<>(chainOld);

    visited.add(frog);
    chain.add(frog);

    System.out.println("\nAufRuf: " + ++aufRuf);
    System.out.println("Chain size: " + chain.size() + "\n");


    List<Position> neighbors = getNeighbours(frog, sampleBoard);
    System.out.println("Neighbors in dfs: " + neighbors.size());
    if (neighbors.size() > 2) {
      return false;
    }

    for (Position neighbor : neighbors) {
      if (!visited.contains(neighbor)) {
        System.out.println(
            "Current neighbor: " + neighbor.frog() + " at (" + neighbor.x() + "," + neighbor.y() +
                ") for aufRuf:" + aufRuf +
                "\n");
        if (dfs(neighbor, visited, chain, chainLength, aufRuf, sampleBoard)) {

          return true;
        }
      } else if (chain.size() - 1 >= chainLength && chain.contains(neighbor)) {
        System.out.println(
            "\nCurrent neighbor: " + neighbor.frog() + " at (" + neighbor.x() + "," + neighbor.y() +
                ") for aufRuf:" + aufRuf);
        System.out.println("Chain size_786: " + chain.size());
        System.out.println("Return true from line 786 for aufRuf:" + aufRuf + "\n");
        return true;
      }
    }

    chain.remove(frog);
    System.out.println("Chain size after remove frog: " + chain.size() + " for aufRuf:" + aufRuf);

    // Check if there's an element in the chain that has only one neighbor
    for (Position position : chain) {
      if (getNeighbours(position, sampleBoard).size() == 1 && chain.size() >= 3) {
        System.out.println("\nChain size_797: " + chain.size());
        System.out.println("Return true from line 797 for aufRuf:" + aufRuf + "\n");
        return true;
      }
    }

    System.out.println("Return false from line 802 for aufRuf:" + aufRuf + "\n");
    return false;
  }

  public boolean dfs(Position current, Set<Position> visited, LinkedList<Position> kette,
                     final int chainLength, Set<Position> sampleBoard) {
    visited.add(current);
    kette.add(current);

    // Check if the chain is valid up to this point
    if (kette.size() >= chainLength && istGueltigeKette(kette, chainLength, sampleBoard)) {
      return true; // Valid chain found
    }

    // Continue searching only if we haven't reached the desired length
    if (kette.size() < chainLength) {
      for (Position neighbor : getNeighbours(current, sampleBoard)) {
        if (!visited.contains(neighbor) &&
            dfs(neighbor, visited, kette, chainLength, sampleBoard)) {
          return true; // Successful chain found
        }
      }
    }
    // Backtrack if the path doesn't lead to a solution
    visited.remove(current);
    kette.removeLast(); // Efficiently remove the last element
    return false;
  }

  private boolean istGueltigeKette(LinkedList<Position> kette, int chainLength,
                                   Set<Position> sampleBoard) {
    if (kette.size() < chainLength) {
      return false; // Ensure the chain has the desired length
    }

    Position first = kette.getFirst();
    Position last = kette.getLast();

    boolean istValidesStartEnde = istGueltigeStartEnde(first, sampleBoard) &&
        istGueltigeStartEnde(last, sampleBoard)
        && !(getNeighbours(first, sampleBoard).size() > 2 &&
        getNeighbours(last, sampleBoard).size() > 2);

    if (!istValidesStartEnde) {
      return false; // Start or end position doesn't meet criteria
    }

    // Check middle positions
    for (int i = 1; i < kette.size() - 1; i++) {
      Position mitte = kette.get(i);
      if (getNeighbours(mitte, sampleBoard).size() != 2) {
        return false; // Middle elements must have exactly 2 neighbors
      }
    }

    return true; // Chain meets all criteria
  }

  private boolean istGueltigeStartEnde(Position pos, Set<Position> sampleBoard) {
    int neighborCount = getNeighbours(pos, sampleBoard).size();
    return neighborCount == 1 || neighborCount <= 4;
  }


  private List<Position> getNeighbours(Position pos, Set<Position> board) {
    List<Position> neigborsByPosition = getBounderingNeighbours(pos);

    List<Position> actualNeighbors = new ArrayList<>();
    for (Position neighbor : neigborsByPosition) {
      for (Position realFrog : board) {
        if (realFrog.x() == neighbor.x() && realFrog.y() == neighbor.y()) {
          actualNeighbors.add(realFrog);
        }
      }
    }

//    System.out.println("Possible neighbors for " + pos + " are:\n");


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

  public boolean hatkeineKetten(Set<Position> sampleboard, Position start) {

    Set<Position> visited = new HashSet<>();
    List<Position> positionsOfSinglePlayer = sampleboard.stream().toList();
    int chainLength = 0;

    if (!visited.contains(start)) {
      chainLength = bfs(start, positionsOfSinglePlayer, visited);
      if (chainLength >= 3) {
        System.out.println("Kettenlänge: " + chainLength);
        System.out.println("Hat Ketten ");
        return false;

      }

    }
    System.out.println("Hat keine Ketten ");
    System.out.println("Kettenlänge: " + chainLength);
    return true;
  }

  public void setBoard(Set<Position> board) {
    this.board = board;
  }

  // Check if there are chains of 3 individual connections between frogs
  private boolean hasNoChains(Set<Position> sampleBoard) {
    boolean hatKetten = true;
    Set<Position> visited = new HashSet<>();
    LinkedList<Position> chain = new LinkedList<>();
    for (Position frog : sampleBoard) {
      if (!visited.contains(frog)) {
        chain.clear();
        if (dfs(frog, visited, chain, 4, sampleBoard)) {
          System.out.println("Hat Ketten ");
          hatKetten = false;
          break;
        }
      }
    }

    if (hatKetten) {
      System.out.println("Hat keine Ketten ");
    }
    return hatKetten;
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

    if (pos.x() < -50 || pos.y() > 50) {
      return false;
    }

    if (getBoard().isEmpty()) {
      frogsInHandMap.get(currentPlayer).remove(selectedFrog);
      currentPlayer.getFrogsInHand().removeIf(frog1 -> frog1.getFrogColor() == selectedFrog);
      board.add(pos);
      currentGamePhase = GamePhase.NACHZIEHEN;
      return true;
    }
    if (!isPositionOccupied(pos) && hasNeighbour(pos) && isPositionValidForPlacement(pos)) {
      var sampleBoard = new HashSet<>(board);
      sampleBoard.add(pos);
      if (!hasNoChains(sampleBoard)) {
        return false;
      } else {
        frogsInHandMap.get(currentPlayer).remove(selectedFrog);
        currentPlayer.getFrogsInHand().removeIf(frog1 -> frog1.getFrogColor() == selectedFrog);
        board.add(pos);
        currentGamePhase = GamePhase.NACHZIEHEN;

      }

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
