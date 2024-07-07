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
import java.util.logging.Logger;

/**
 * This class implements the game logic of the Army of Frogs game.
 * The game is played on a hexagonal board with a variable number of players.
 * Each player has a set of frogs of a specific color.
 * The goal of the game is to move all frogs of the same color into a single connected group.
 * The game has three phases: ANLEGEN, NACHZIEHEN, and BEWEGEN.
 * In the ANLEGEN phase, players take turns placing their frogs on the board.
 * In the NACHZIEHEN phase, players take turns drawing frogs from the bag.
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

  Logger logger = Logger.getLogger(getClass().getName());


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

  public void setCurrentDirection(Direction currentDirection) {
    this.currentDirection = currentDirection;
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

  /**
   * is the constructor for the Gamelogic class.
   * It initializes the game board, the list of players, the bag of frogs,
   * and the game state.
   */
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
      logger.info(() -> "No player found with color: " + color);
      return new ArrayList<>();
    }
  }

  /**
   * Returns the number of frogs of the specified color that are currently in the hands
   * of all players.
   *
   * @param color the color of the frogs to count
   * @return the number of frogs of the specified color in the hands of all players
   */
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

  /**
   * Returns the total number of frogs currently in the hands of all players.
   *
   * @return the total number of frogs in the hands of all players
   */
  public int getFrogInHandMapSize() {

    int count = 0;
    for (Map.Entry<Player, List<Color>> entry : frogsInHandMap.entrySet()) {

      count += entry.getValue().size();
    }
    return count;
  }

  /**
   * Returns the number of frogs of the specified color that are currently in the hands.
   *
   * @param player the player whose frogs are to be counted
   * @return the number of frogs of the specified color in the hands of the specified player
   */
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

  /**
   * Returns the player with the specified color.
   *
   * @param color the color of the player to find
   * @return the player with the specified color, or null if no player with the given color is found
   */
  public Player getPlayerByColor(Color color) {
    for (Player player : players) {
      if (player.getPlayerColor() == color) {
        return player;
      }
    }
    return null; // Return null if no player with the given color is found
  }

  /**
   * Returns the current state of the game board.
   *
   * @return the set of positions representing the current board state
   */
  @Override
  public Set<Position> getBoard() {

    if (board == null) {
      board = new HashSet<>();
      return board;
    }
    return board;
  }

  /**
   * Handles a click event on the specified position on the board.
   *
   * @param position the position that was clicked
   */
  @Override
  public void clicked(Position position) {
    if (spielLaueft) {
      if (currentGamePhase == GamePhase.ANLEGEN) {
        handleAnlegenPhase(position);
      } else if (currentGamePhase == GamePhase.BEWEGEN) {
        handleBewegenPhase(position);
      }
    }
  }

  private void handleAnlegenPhase(Position position) {
    if (selectedFrog != null) {
      Position newPos = new Position(selectedFrog, position.x(), position.y(), position.border());

      if (anlegen(newPos)) {
        nachziehen();
        selectedFrog = null;
        endTurn();

        if (!checkMovePossible()) {
          currentGamePhase = GamePhase.ANLEGEN;
        }
      }
    }
  }

  private void handleBewegenPhase(Position position) {
    if (selectedPosition == null) {
      selectFrogToMove(position);
      sampleMovePositions = possibleMovePositions();
    } else if (selectedPosition.x() == position.x() && selectedPosition.y() == position.y()) {
      endBewegenPhase();
    } else {
      bewegen(position);
    }
  }

  private void endBewegenPhase() {
    logger.info("End Bewegen phase");

    if (validateFinalMove(selectedPosition, board)) {
      selectedPosition = null;
      currentGamePhase = GamePhase.ANLEGEN;
    } else {
      logger.info("Invalid final move.");
    }
  }

  /*@Override
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
        logger.info("End Bewegen phase");

        if (validateFinalMove(selectedPosition, board)) {
          selectedPosition = null;
          currentGamePhase = GamePhase.ANLEGEN;
        } else {
          String message = "Invalid final move.";

          logger.info("Invalid final move.");
        }
      } else {
        bewegen(position);
      }
    }


  }*/

  /**
   * Checks if a move is possible and changes the game phase accordingly.
   *
   * @return true if a move is possible, false otherwise
   */
  public boolean jumpMove() {
    if (!checkMovePossible()) {
      currentGamePhase = GamePhase.ANLEGEN;
      return false;
    }
    return true;
  }

  /**
   * Changes the current game phase based on the current state of the game.
   */
  public void changePhase() {
    if (currentGamePhase == GamePhase.ANLEGEN) {
      currentGamePhase = GamePhase.BEWEGEN;
    } else if (currentGamePhase == GamePhase.BEWEGEN && checkMovePossible()) {
      currentGamePhase = GamePhase.ANLEGEN;
    }
  }

  /**
   * Checks if the specified position has a neighboring frog of the same color
   * as the current player.
   *
   * @param position the position to check
   * @return true if there is a neighboring frog of the same color, false otherwise
   */
  private boolean hasNeighbourOfSameColor(Position position) {
    for (Position pos : board) {
      if (areNeighbours(position.x(), position.y(), pos.x(), pos.y())
          && pos.frog() == currentPlayer.getPlayerColor()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks if the specified position is valid for placing a frog.
   *
   * @param pos the position to check
   * @return true if the position is valid for placement, false otherwise
   */
  private boolean isPositionValidForPlacement(Position pos) {
    if (pos.frog() == currentPlayer.getPlayerColor()) {
      return !hasNeighbourOfSameColor(pos);
    }
    return true;
  }

  /**
   * Checks if any frog can be placed on the board.
   *
   * @return true if any frog can be placed, false otherwise
   */
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

  /**
   * Selects a frog for movement from the specified position.
   *
   * @param position the position of the frog to select
   * @return true if the frog was successfully selected, false otherwise
   */
  private boolean selectFrogToMove(Position position) {
    for (Position pos : board) {
      if (pos.equals(position) && pos.frog() == currentPlayer.getPlayerColor()) {
        selectedPosition = pos;
        logger.info(() -> "Frog selected for movement: " + pos);
        return true;
      }
    }
    logger.info(() -> "Invalid frog selection: " + position);
    return false;
  }

  /**
   * Draws a frog from the bag for the current player if conditions are met.
   *
   * @return true if a frog was drawn successfully, false otherwise
   */
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

  /**
   * Sets the current game phase.
   *
   * @param currentGamePhase the game phase to set
   */
  public void setCurrentGamePhase(GamePhase currentGamePhase) {
    this.currentGamePhase = currentGamePhase;
  }

  /**
   * Checks if a frog can move from the specified starting position to the specified
   * destination position.
   *
   * @param from the starting position
   * @param to   the destination position
   * @return true if the frog can move to the destination, false otherwise
   */
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

  /**
   * Checks if a move is possible for the current player.
   *
   * @return true if a move is possible, false otherwise
   */
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

  /**
   * Checks if any frog can be moved by the current player.
   *
   * @return true if any frog can be moved, false otherwise
   */
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
      logger.info("No possible move positions.");

      return false;
    }

    if (!sampleMovePositions.contains(destinedPosition)) {
      logger.info("Invalid move.");
      return false;
    }


    Set<Position> sampleBoard = new HashSet<>(board);
    sampleBoard.remove(selectedPosition);
    var newPos = new Position(selectedPosition.frog(), destinedPosition.x(), destinedPosition.y(),
        destinedPosition.border());
    sampleBoard.add(newPos);
    if (!validateInitialMoves(selectedPosition, newPos, sampleBoard)) {
      logger.info("Invalid move.");
      return false;
    } else {
      board = sampleBoard;
      logger.info(() -> "Moving frog from " + selectedPosition + " to " + newPos);
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
      logger.info(() -> "Game ended. Winner: " + winner());
      spielLaueft = false;
    }
    gameRound++;
    currentGamePhase = GamePhase.BEWEGEN;
  }

  /**
   * Checks if the specified position on the board is occupied by a frog.
   *
   * @param position the position to check
   * @return true if the position is occupied, false otherwise
   */
  private boolean isPositionOccupied(Position position) {
    for (Position pos : board) {
      if (pos.x() == position.x() && pos.y() == position.y()) {
        return true;
      }
    }

    return false;
  }

  /**
   * Selects a frog of the specified color from the current player's hand if it is the player's turn
   * and the game is in the ANLEGEN phase.
   *
   * @param player the color of the player selecting the frog
   * @param frog   the color of the frog being selected
   */
  @Override
  public void selectedFrogInHand(Color player, Color frog) {

    if (currentPlayer.getPlayerColor() == player && currentGamePhase == GamePhase.ANLEGEN
        && frogsInHandMap.containsKey(currentPlayer)
        && frogsInHandMap.get(currentPlayer).contains(frog)) {
      selectedFrog = frog;
      logger.info(() -> "selectedFrogInHand(Player: " + player + ", Frog: " + frog + ")");
    } else {
      logger.info("Invalid frog selection.");
    }

  }

  /**
   * Calculates the possible move positions on the board where a frog can be moved.
   *
   * @return a list of possible move positions
   */
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

  /**
   * Validates the initial moves of a frog from one position to another on the sample board.
   *
   * @param from        the starting position
   * @param to          the destination position
   * @param sampleBoard the sample board to validate the move on
   * @return true if the move is valid, false otherwise
   */
  private boolean validateInitialMoves(Position from, Position to, Set<Position> sampleBoard) {


    if (isInStraightLine(from, to)
        &&
        isFrogBetweenUs(from, to, sampleBoard)
        &&
        hasNoChains(sampleBoard)) {
      logger.info("Valid move");
      return true;
    }

    return false;
  }

  /**
   * Validates the final move of a frog to a specified position on the sample board.
   *
   * @param to          the destination position
   * @param sampleBoard the sample board to validate the move on
   * @return true if the final move is valid, false otherwise
   */
  private boolean validateFinalMove(Position to, Set<Position> sampleBoard) {
    if (isZusammenhaengend(to, sampleBoard)
        &&
        hasNoChains(sampleBoard)) {
      logger.info("Valid  final move");
      return true;
    }

    return false;
  }

  /**
   * Calculates the greatest common divisor (GCD) of two integers using the Euclidean algorithm.
   *
   * @param a the first integer
   * @param b the second integer
   * @return the greatest common divisor of a and b
   */
  private int gcd(int a, int b) {
    while (b != 0) {
      int temp = b;
      b = a % b;
      a = temp;
    }
    return a;
  }

  /**
   * Checks if the board remains connected after a move to the specified position.
   *
   * @param to          the destination position
   * @param sampleBoard the sample board to check
   * @return true if the board remains connected, false otherwise
   */
  private boolean isZusammenhaengend(Position to, Set<Position> sampleBoard) {
    boolean verify = false;
    var listBoard = sampleBoard.stream().toList();
    var visited = new HashSet<Position>();
    var len = listBoard.size();
    logger.info("len: " + len);
    var len2 = bfs(to, listBoard, visited);
    logger.info("len2: " + len2);
    if (len == len2) {
      verify = true;
    }
    if (verify) {
      logger.info("Zusammenhängend");
    } else {
      logger.info("Nicht zusammenhängend");
    }
    return verify;
  }

//  /**
//   * Checks if there is a frog between two positions on the board in a specified direction.
//   *
//   * @param from         the starting position
//   * @param to           the destination position
//   * @param sampleBoard2 the sample board to check
//   * @return true if there is a frog between the two positions, false otherwise
//   */
//  public boolean isFrogBetweenUs(Position from, Position to, Set<Position> sampleBoard2) {
//    int[] direction = getDirectionDeltas(currentDirection, from.y());
//    if (direction == null) {
//      return false;
//    }
//
//    int q = from.x() + direction[0];
//    int r = from.y() + direction[1];
//
//    Set<Position> sampleBoard = initializeSampleBoard(sampleBoard2, from, to, direction);
//
//    return isFrogInPath(q, r, to, sampleBoard, direction);
//  }
//
//  private int[] getDirectionDeltas(Direction direction, int y) {
//    return switch (direction) {
//      case RIGHT -> new int[]{1, 0};
//      case LEFT -> new int[]{-1, 0};
//      case UPPER_RIGHT -> new int[]{(y % 2 != 0) ? 1 : 0, -1};
//      case UPPER_LEFT -> new int[]{(y % 2 == 0) ? -1 : 0, -1};
//      case LOWER_RIGHT -> new int[]{(y % 2 != 0) ? 1 : 0, 1};
//      case LOWER_LEFT -> new int[]{(y % 2 == 0) ? -1 : 0, 1};
//      default -> null;
//    };
//  }
//
//  private Set<Position> initializeSampleBoard(Set<Position> sampleBoard2, Position from, Position to, int[] direction) {
//    Set<Position> sampleBoard = new HashSet<>(sampleBoard2);
//    int q = from.x() + direction[0];
//    int r = from.y() + direction[1];
//    while (!(q == to.x() && r == to.y()) && isWithinBounds(q, r)) {
//      int finalQ = q;
//      int finalR = r;
//      if (sampleBoard.stream().noneMatch(p -> p.x() == finalQ && p.y() == finalR)) {
//        sampleBoard.add(new Position(Color.None, q, r, Color.None));
//      }
//      q += direction[0];
//      r += direction[1];
//    }
//    return sampleBoard;
//  }
//
//  private boolean isWithinBounds(int q, int r) {
//    return q >= -50 && r >= -50 && q <= 50 && r <= 50;
//  }
//
//  private boolean isFrogInPath(int q, int r, Position to, Set<Position> sampleBoard, int[] direction) {
//    while (!(q == to.x() && r == to.y())) {
//      int finalQ = q;
//      int finalR = r;
//      Position current = sampleBoard.stream()
//          .filter(p -> p.x() == finalQ && p.y() == finalR)
//          .findFirst()
//          .orElse(null);
//      if (current == null) {
//        logger.info("Current is null");
//        return false;
//      } else if (!current.frog().equals(Color.None)) {
//        logger.info("Frog in the path");
//        return true;
//      }
//      q += direction[0];
//      r += direction[1];
//    }
//    logger.info("No frog in the path");
//    return false;
//  }

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
      default -> {
        return false;
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
        logger.info("Current is null");
        return false; // Invalid move, position not part of the board

      } else if (!current.frog().equals(Color.None)) {
        logger.info("Frog in the path");
        return true; // Found a frog in the path

      }
      q += dq;
      r += dr;
    }


    logger.info("No frog in the path");
    return false; // No frog in the path
  }

  /**
   * Checks if two positions are in a straight line.
   *
   * @param from the starting position
   * @param to   the destination position
   * @return true if the positions are in a straight line, false otherwise
   */
  public boolean isInStraightLine(Position from, Position to) {

    if (checkDirection(from.x(), from.y(), to.x(), to.y())) {
      logger.info("Is in straight line");
      return true;
    }
    logger.info("Is not in straight line");
    return false;
  }

  /**
   * Checks the direction between two positions to determine if they are in a straight line.
   *
   * @param q1 the x-coordinate of the starting position
   * @param r1 the y-coordinate of the starting position
   * @param q2 the x-coordinate of the destination position
   * @param r2 the y-coordinate of the destination position
   * @return true if the positions are in a straight line, false otherwise
   */
//  private boolean checkDirection(int q1, int r1, int q2, int r2) {
//    int size = board.size() + 1;
//
//    for (int i = 0; i < size; i++) {
//      if (checkRight(q1, r1, q2, r2) || checkLeft(q1, r1, q2, r2) || checkUpperRight(q1, r1, q2, r2)
//          || checkLowerRight(q1, r1, q2, r2) || checkUpperLeft(q1, r1, q2, r2) ||
//          checkLowerLeft(q1, r1, q2, r2)) {
//        return true;
//      }
//    }
//    return false;
//  }
//
//  private boolean checkRight(int q1, int r1, int q2, int r2) {
//    if (r1 == r2 && q1 + 1 == q2) {
//      currentDirection = Direction.RIGHT;
//      logger.info("Is Right");
//      return true;
//    }
//    return false;
//  }
//
//  private boolean checkLeft(int q1, int r1, int q2, int r2) {
//    if (r1 == r2 && q1 - 1 == q2) {
//      currentDirection = Direction.LEFT;
//      logger.info("Is Left");
//      return true;
//    }
//    return false;
//  }
//
//  private boolean checkUpperRight(int q1, int r1, int q2, int r2) {
//    if ((r1 == 0 || Math.abs(r1) % 2 == 0)) {
//      if (--r1 == r2 && q1 == q2) {
//        currentDirection = Direction.UPPER_RIGHT;
//        logger.info("Is Upper Right");
//        return true;
//      }
//    } else {
//      if (--r1 == r2 && ++q1 == q2) {
//        currentDirection = Direction.UPPER_RIGHT;
//        logger.info("Is Upper Right");
//        return true;
//      }
//    }
//    return false;
//  }
//
//  private boolean checkLowerRight(int q1, int r1, int q2, int r2) {
//    if ((r1 == 0 || Math.abs(r1) % 2 == 0)) {
//      if (++r1 == r2 && q1 == q2) {
//        currentDirection = Direction.LOWER_RIGHT;
//        logger.info("Is Lower Right");
//        return true;
//      }
//    } else {
//      if (++r1 == r2 && ++q1 == q2) {
//        currentDirection = Direction.LOWER_RIGHT;
//        logger.info("Is Lower Right");
//        return true;
//      }
//    }
//    return false;
//  }
//
//  private boolean checkUpperLeft(int q1, int r1, int q2, int r2) {
//    if ((r1 == 0 || Math.abs(r1) % 2 == 0)) {
//      if (--r1 == r2 && --q1 == q2) {
//        currentDirection = Direction.UPPER_LEFT;
//        logger.info("Is Upper Left");
//        return true;
//      }
//    } else {
//      if (--r1 == r2 && q1 == q2) {
//        currentDirection = Direction.UPPER_LEFT;
//        logger.info("Is Upper Left");
//        return true;
//      }
//    }
//    return false;
//  }
//
//  private boolean checkLowerLeft(int q1, int r1, int q2, int r2) {
//    if ((r1 == 0 || Math.abs(r1) % 2 == 0)) {
//      if (++r1 == r2 && --q1 == q2) {
//        currentDirection = Direction.LOWER_LEFT;
//        logger.info("Is Lower Left");
//        return true;
//      }
//    } else {
//      if (++r1 == r2 && q1 == q2) {
//        currentDirection = Direction.LOWER_LEFT;
//        logger.info("Is Lower Left");
//        return true;
//      }
//    }
//    return false;
//  }
  private boolean checkDirection(int q1, int r1, int q2, int r2) {
    int size = board.size() + 1;

    int leftQ1 = q1;
    int rightQ1 = q1;
    int upperLeftQ1 = q1;
    int upperRightQ1 = q1;
    int lowerLeftQ1 = q1;
    int lowerRightQ1 = q1;

    int leftR1 = r1;
    int rightR1 = r1;
    int upperLeftR1 = r1;
    int upperRightR1 = r1;
    int lowerLeftR1 = r1;
    int lowerRightR1 = r1;

    int leftQ2 = q2;
    int rightQ2 = q2;
    int upperLeftQ2 = q2;
    int upperRightQ2 = q2;
    int lowerLeftQ2 = q2;
    int lowerRightQ2 = q2;

    int leftR2 = r2;
    int rightR2 = r2;
    int upperLeftR2 = r2;
    int upperRightR2 = r2;
    int lowerLeftR2 = r2;
    int lowerRightR2 = r2;

    for (int i = 0; i < size; i++) {
      // Check right
      if (rightR1 == rightR2 && ++rightQ1 == rightQ2) {
        currentDirection = Direction.RIGHT;
        System.out.println("Is Right");
        return true;
      }

      // Check left
      if (leftR1 == leftR2 && --leftQ1 == leftQ2) {
        currentDirection = Direction.LEFT;
        System.out.println("Is Left");
        return true;
      }

      // Check upper right
      if (upperRightR1 == 0 || (Math.abs(upperRightR1) % 2) == 0) {
        --upperRightR1;
        if (upperRightQ1 == upperRightQ2 && upperRightR1 == upperRightR2) {
          currentDirection = Direction.UPPER_RIGHT;
          System.out.println("Is Upper Right");
          return true;
        }
      } else {
        ++upperRightQ1;
        --upperRightR1;
        if (upperRightQ1 == upperRightQ2 && upperRightR1 == upperRightR2) {
          currentDirection = Direction.UPPER_RIGHT;
          System.out.println("Is Upper Right");
          return true;
        }
      }

      // Check lower right
      if (lowerRightR1 == 0 || (Math.abs(lowerRightR1) % 2) == 0) {
        ++lowerRightR1;
        if (lowerRightQ1 == lowerRightQ2 && lowerRightR1 == lowerRightR2) {
          currentDirection = Direction.LOWER_RIGHT;
          System.out.println("Is Lower Right");
          return true;
        }
      } else {
        ++lowerRightQ1;
        ++lowerRightR1;
        if (lowerRightQ1 == lowerRightQ2 && lowerRightR1 == lowerRightR2) {
          currentDirection = Direction.LOWER_RIGHT;
          System.out.println("Is Lower Right");
          return true;
        }
      }

      // Check upper left
      if (upperLeftR1 == 0 || (Math.abs(upperLeftR1) % 2) == 0) {
        --upperLeftR1;
        --upperLeftQ1;
        if (upperLeftQ1 == upperLeftQ2 && upperLeftR1 == upperLeftR2) {
          currentDirection = Direction.UPPER_LEFT;
          System.out.println("Is Upper Left");
          return true;
        }
      } else {
        upperLeftR1--;
        if (upperLeftQ1 == upperLeftQ2 && upperLeftR1 == upperLeftR2) {
          currentDirection = Direction.UPPER_LEFT;
          System.out.println("Is Upper Left");
          return true;
        }
      }

      // Check lower left
      if (lowerLeftR1 == 0 || (Math.abs(lowerLeftR1) % 2) == 0) {
        lowerLeftQ1--;
        lowerLeftR1++;
        if (lowerLeftQ1 == lowerLeftQ2 && lowerLeftR1 == lowerLeftR2) {
          currentDirection = Direction.LOWER_LEFT;
          System.out.println("Is Lower Left");
          return true;
        }
      } else {
        lowerLeftR1++;
        if (lowerLeftQ1 == lowerLeftQ2 && lowerLeftR1 == lowerLeftR2) {
          currentDirection = Direction.LOWER_LEFT;
          System.out.println("Is Lower Left");
          return true;
        }
      }
    }

    return false;
  }


  /**
   * Performs a depth-first search (DFS) to determine if a valid chain of the specified
   * length exists.
   *
   * @param frog        the starting position of the frog
   * @param visitedOld  the set of already visited positions
   * @param chainOld    the current chain of positions
   * @param chainLength the desired length of the chain
   * @param aufRuf      the current recursion depth
   * @param sampleBoard the sample board to search on
   * @return true if a valid chain of the specified length is found, false otherwise
   */
  private boolean dfs(Position frog, Set<Position> visitedOld, Set<Position> chainOld,
                      int chainLength, int aufRuf, Set<Position> sampleBoard) {

    logger.info("Frog: " + frog.frog() + " at " + frog.x() + " " + frog.y());

    var visited = new HashSet<>(visitedOld);
    var chain = new HashSet<>(chainOld);

    visited.add(frog);
    chain.add(frog);

    logger.info("\nAufRuf: " + ++aufRuf);
    logger.info("Chain size: " + chain.size() + "\n");


    List<Position> neighbors = getNeighbours(frog, sampleBoard);
    logger.info("Neighbors in dfs: " + neighbors.size());
    if (neighbors.size() > 2) {
      return false;
    }

    for (Position neighbor : neighbors) {
      if (!visited.contains(neighbor)) {
        logger.info(
            "Current neighbor: " + neighbor.frog() + " at (" + neighbor.x() + "," + neighbor.y()
                + ") for aufRuf:" + aufRuf
                + "\n");
        if (dfs(neighbor, visited, chain, chainLength, aufRuf, sampleBoard)) {

          return true;
        }
      } else if (chain.size() - 1 >= chainLength && chain.contains(neighbor)) {
        logger.info(
            "\nCurrent neighbor: " + neighbor.frog() + " at (" + neighbor.x() + "," + neighbor.y()
                + ") for aufRuf:" + aufRuf);
        logger.info("Chain size_786: " + chain.size());
        logger.info("Return true from line 786 for aufRuf:" + aufRuf + "\n");
        return true;
      }
    }

    chain.remove(frog);
    logger.info("Chain size after remove frog: " + chain.size() + " for aufRuf:" + aufRuf);

    // Check if there's an element in the chain that has only one neighbor
    for (Position position : chain) {
      if (getNeighbours(position, sampleBoard).size() == 1 && chain.size() >= 3) {
        logger.info("\nChain size_797: " + chain.size());
        logger.info("Return true from line 797 for aufRuf:" + aufRuf + "\n");
        return true;
      }
    }

    logger.info("Return false from line 802 for aufRuf:" + aufRuf + "\n");
    return false;
  }

  /**
   * Performs a depth-first search (DFS) to determine if a valid chain of the specified
   * length exists.
   *
   * @param current     the current position in the search
   * @param visited     the set of already visited positions
   * @param kette       the current chain of positions
   * @param chainLength the desired length of the chain
   * @param sampleBoard the sample board to search on
   * @return true if a valid chain of the specified length is found, false otherwise
   */
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
        if (!visited.contains(neighbor)
            && dfs(neighbor, visited, kette, chainLength, sampleBoard)) {
          return true; // Successful chain found
        }
      }
    }
    // Backtrack if the path doesn't lead to a solution
    visited.remove(current);
    kette.removeLast(); // Efficiently remove the last element
    return false;
  }

  /**
   * Checks if the specified chain of positions is valid.
   *
   * @param kette       the chain of positions to check
   * @param chainLength the desired length of the chain
   * @param sampleBoard the sample board to check on
   * @return true if the chain is valid, false otherwise
   */
  private boolean istGueltigeKette(LinkedList<Position> kette, int chainLength,
                                   Set<Position> sampleBoard) {
    if (kette.size() < chainLength) {
      return false; // Ensure the chain has the desired length
    }

    Position first = kette.getFirst();
    Position last = kette.getLast();

    boolean istValidesStartEnde = istGueltigeStartEnde(first, sampleBoard)
        && istGueltigeStartEnde(last, sampleBoard)
        && !(getNeighbours(first, sampleBoard).size() > 2
        && getNeighbours(last, sampleBoard).size() > 2);

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

  /**
   * Checks if the specified position is a valid start or end point for a chain.
   *
   * @param pos         the position to check
   * @param sampleBoard the sample board to check on
   * @return true if the position is a valid start or end point, false otherwise
   */
  private boolean istGueltigeStartEnde(Position pos, Set<Position> sampleBoard) {
    int neighborCount = getNeighbours(pos, sampleBoard).size();
    return neighborCount == 1 || neighborCount <= 4;
  }


  /**
   * Gets the neighboring positions of the specified position on the board.
   *
   * @param pos   the position to get the neighbors of
   * @param board the board to get the neighbors from
   * @return a list of neighboring positions
   */
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

    return actualNeighbors;
  }

  /**
   * Returns a list of neighboring positions around a given position.
   *
   * @param pos the position for which to get the neighboring positions
   * @return a list of neighboring positions
   */
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

  /**
   * Determines the winner of the game.
   *
   * @return the color of the winning player, or Color.None if there is no winner
   */
  @Override
  public Color winner() {
    for (Player player : players) {
      List<Position> positionsOfSinglePlayer = getPlayerPositions(player);
      if (positionsOfSinglePlayer.size() >= 7
          && hasWinningCluster(positionsOfSinglePlayer)) {
        return player.getPlayerColor();
      }
    }
    return Color.None;
  }

  private List<Position> getPlayerPositions(Player player) {
    List<Position> positionsOfSinglePlayer = new ArrayList<>();
    for (Position pos : getBoard()) {
      if (pos.frog() == player.getPlayerColor()) {
        positionsOfSinglePlayer.add(pos);
      }
    }
    return positionsOfSinglePlayer;
  }

  private boolean hasWinningCluster(List<Position> positions) {
    for (Position start : positions) {
      Set<Position> visited = new HashSet<>();
      int count = bfs(start, positions, visited);
      int size = positions.size();
      if (isWinningSize(size, count)) {
        return true;
      }
    }
    return false;
  }

  private boolean isWinningSize(int size, int count) {
    return (size == 7 && count == 7) || (size > 7 && count == size);
  }

  /**
   * Saves the game state to a file.
   *
   * @param filename the name of the file to save the game state
   * @return true if the game state was successfully saved, false otherwise
   */
  @Override
  public boolean save(String filename) {
    return false;
  }

  /**
   * Loads the game state from a file.
   *
   * @param filename the name of the file to load the game state from
   * @return true if the game state was successfully loaded, false otherwise
   */
  @Override
  public boolean load(String filename) {


    return false;
  }

  /**
   * Returns the number of frogs remaining in the bag.
   *
   * @return the number of frogs in the bag
   */
  @Override
  public int frogsInBag() {
    return gameBag.getNumoffrogs();
  }

  /**
   * Returns the number of frogs of a specific color remaining in the bag.
   *
   * @param color the color of the frogs to count
   * @return the number of frogs of the specified color in the bag
   */
  public int frogsInBagWithColor(Color color) {
    return gameBag.getFrogsInBag(color).size();
  }

  /**
   * Allows each player to take a frog from the bag if they have less than 2 frogs in hand.
   */
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

  /**
   * Allows a player to take a frog from the bag if conditions are met.
   *
   * @param player the player taking the frog
   */
  public void takeFrogFromBag(Player player) {

    if (gameBag.getNumoffrogs() > 0 && player.getFrogsInHand().size() < 2
        && frogsInHandMap.get(currentPlayer).size() < 2) {

      Frog takenFrog = gameBag.takeFrog();
      player.setMyFrogs(takenFrog);
      player.setFrogsInHand(takenFrog);
      frogsInHandMap.get(player).add(takenFrog.getFrogColor());

    }
  }

  /**
   * Performs a breadth-first search (BFS) from a starting position to determine the size
   * of a connected component.
   *
   * @param start                   the starting position
   * @param positionsOfSinglePlayer the list of positions of a single player
   * @param visited                 a set of visited positions
   * @return the size of the connected component
   */
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

  /**
   * Checks if the board does not contain any chains starting from a given position.
   *
   * @param sampleboard the set of positions on the board to check
   * @param start       the starting position for the chain check
   * @return true if there are no chains of length 3 or more, false otherwise
   */
  public boolean hatkeineKetten(Set<Position> sampleboard, Position start) {

    Set<Position> visited = new HashSet<>();
    List<Position> positionsOfSinglePlayer = sampleboard.stream().toList();
    int chainLength = 0;

    if (!visited.contains(start)) {
      chainLength = bfs(start, positionsOfSinglePlayer, visited);
      if (chainLength >= 3) {
        logger.info("Kettenlänge: " + chainLength);
        logger.info("Hat Ketten ");
        return false;

      }

    }
    logger.info("Hat keine Ketten ");
    logger.info("Kettenlänge: " + chainLength);
    return true;
  }

  /**
   * Sets the board to the given set of positions.
   *
   * @param board the new set of positions to be set as the board
   */
  public void setBoard(Set<Position> board) {
    this.board = board;
  }

  /**
   * Checks if there are no chains of length 3 or more on the board.
   *
   * @param sampleBoard the set of positions representing the board
   * @return true if there are no chains, false otherwise
   */
  private boolean hasNoChains(Set<Position> sampleBoard) {
    boolean hatKetten = true;
    Set<Position> visited = new HashSet<>();
    LinkedList<Position> chain = new LinkedList<>();
    for (Position frog : sampleBoard) {
      if (!visited.contains(frog)) {
        chain.clear();
        if (dfs(frog, visited, chain, 4, sampleBoard)) {
          logger.info("Hat Ketten ");
          hatKetten = false;
          break;
        }
      }
    }

    if (hatKetten) {
      logger.info("Hat keine Ketten ");
    }
    return hatKetten;
  }

  /**
   * Ends the game.
   *
   * @return true if the game has ended, false otherwise
   */
  public boolean endGame() {
    return !spielLaueft;
  }

  /**
   * Sets the current game round.
   *
   * @param gameRound the current game round
   */
  public void setGameRound(int gameRound) {
    this.gameRound = gameRound;
  }

  /**
   * Checks if there is a frog on the board.
   *
   * @return true if there is a frog on the board, false otherwise
   */
  public boolean isFrogonBoard() {
    return frogonBoard;
  }

  /**
   * Returns the game bag.
   *
   * @return the game bag
   */
  public Bag getGameBag() {
    return gameBag;
  }

  /**
   * Returns the players in the game.
   *
   * @return an array of players
   */
  public Player[] getPlayers() {
    return players;
  }

  /**
   * Places a frog on the board.
   *
   * @param pos the position to place the frog
   * @return true if the frog was successfully placed, false otherwise
   */
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


  /**
   * Checks if two positions are neighbors on the board.
   *
   * @param q1 the x-coordinate of the first position
   * @param r1 the y-coordinate of the first position
   * @param q2 the x-coordinate of the second position
   * @param r2 the y-coordinate of the second position
   * @return true if the positions are neighbors, false otherwise
   */
  public boolean areNeighbours(int q1, int r1, int q2, int r2) {
    int[][] evenOffsets = {
        {1, 0}, {-1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}
    };
    int[][] oddOffsets = {
        {1, 0}, {-1, 0}, {1, -1}, {1, 1}, {0, -1}, {0, 1}
    };

    int[][] offsets = (r1 == 0 || (Math.abs(r1) % 2) == 0) ? evenOffsets : oddOffsets;

    for (int[] offset : offsets) {
      if (q2 == q1 + offset[0] && r2 == r1 + offset[1]) {
        return true;
      }
    }

    return false;
  }

  /*public boolean areNeighbours(int q1, int r1, int q2, int r2) {

    if (r1 == 0 || (abs(r1) % 2) == 0) {

      return (q2 == q1 + 1 && r2 == r1)  // Right neighbor
          || (q2 == q1 - 1 && r2 == r1)  // Left neighbor
          || (q2 == q1 && r2 == r1 - 1)  // Upper-right neighbor
          || (q2 == q1 && r2 == r1 + 1)  // Lower-right neighbor
          || (q2 == q1 - 1 && r2 == r1 - 1)  // Upper-left neighbor
          || (q2 == q1 - 1 && r2 == r1 + 1); // Lower-left neighbor

    } else {
      return (q2 == q1 + 1 && r2 == r1)  // Right neighbor
          || (q2 == q1 - 1 && r2 == r1)  // Left neighbor
          || (q2 == q1 + 1 && r2 == r1 - 1)  // Upper-right neighbor
          || (q2 == q1 + 1 && r2 == r1 + 1)  // Lower-right neighbor
          || (q2 == q1 && r2 == r1 - 1)  // Upper-left neighbor
          || (q2 == q1 && r2 == r1 + 1); // Lower-left neighbor
    }

  }*/

  /**
   * Checks if a position has a neighboring position on the board.
   *
   * @param pos1 the position to check
   * @return true if the position has a neighbor, false otherwise
   */
  public boolean hasNeighbour(Position pos1) {
    for (Position pos2 : board) {
      if (areNeighbours(pos1.x(), pos1.y(), pos2.x(), pos2.y())) {
        return true;
      }
    }
    return false;
  }

}
