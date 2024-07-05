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
 * This class represents the game logic of the Army of Frogs game.
 */
public class Gamelogic implements Game {
  private Player[] players;
  private Bag gameBag;
  private boolean spielLaueft;
  private int numOfPlayers;
  private int gameRound;
  private final boolean frogonBoard;

  private final List<Frog> frogsOnBoard;
  private Set<Position> board;
  private Position selectedFrogPosition;
  private Position destinationPosition;
  private Set<Position> viableJumpLocations = new HashSet<>();
  private Color selectedFrog;
  private Position lastClickedPosition;
  private long lastClickTime;
  private static final long DOUBLE_CLICK_THRESHOLD = 500; // milliseconds


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

  /**
   * Enum representing the different phases of the game.
   */
  public enum GamePhase {
    ANLEGEN,
    NACHZIEHEN,
    BEWEGEN
  }

  /**
   * Constructor for the game logic.
   */
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

    List<Color> colorList =
        new ArrayList<>(Arrays.asList(Color.Red, Color.Blue, Color.Green, Color.White));

    if (numberOfPlayers < colorList.size()) {
      colorList.subList(numberOfPlayers, colorList.size()).clear();
    }

    for (int i = 0; i < numberOfPlayers; ++i) {
      players[i] = new Player(colorList.get(i));
      frogsInHandMap.put(players[i], new ArrayList<>());
    }

    currentPlayer = players[0];
    currentGamePhase = GamePhase.BEWEGEN;
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

    checkPhases();
    return true;
  }

  @Override
  public Color[] players() {
    Color[] playersColor = new Color[players.length];
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
        + currentPlayer.getPlayerColor() + ", Phase: " + currentGamePhase + ". Turn: " + gameRound
        + ".";
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

  /**
   * Returns the number of frogs in hand of a specific color.
   *
   * @param color The color of the frog.
   * @return The number of frogs in hand of the specified color.
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
   * Returns the number of frogs in hand of all players.
   *
   * @return The number of frogs in hand of all players.
   */
  public int getFrogInHandMapSize() {
    int count = 0;
    for (Map.Entry<Player, List<Color>> entry : frogsInHandMap.entrySet()) {
      count += entry.getValue().size();
    }
    return count;
  }

  /**
   * Returns the player with the specified color.
   *
   * @param color The color of the player.
   * @return The player with the specified color.
   */
  public Player getPlayerByColor(Color color) {
    for (Player player : players) {
      if (player.getPlayerColor() == color) {
        return player;
      }
    }
    return null;
  }

  @Override
  public Set<Position> getBoard() {
    if (board == null) {
      board = new HashSet<>();
      return board;
    }
    return board;
  }


//  private void calculateViableJumpLocations(Position frogPosition) {
//    viableJumpLocations.clear();
//    int[] dxEven = {1, -1, 0, 0, -1, -1};
//    int[] dyEven = {0, 0, -1, 1, -1, 1};
//    int[] dxOdd = {1, -1, 0, 0, 1, 1};
//    int[] dyOdd = {0, 0, -1, 1, -1, 1};
//
//    int[] dx = (frogPosition.x() % 2 == 0) ? dxEven : dxOdd;
//  //  int[] dx = (frogPosition.x() % 2 == 0) ? dxEven : dxOdd;
//    int[] dy = (frogPosition.y() % 2 == 0) ? dyEven : dyOdd;
//
//    // print all dx and dy
//    for (int i = 0; i < dx.length; i++) {
//      System.out.println("dx[" + i + "] = " + dx[i]);
//      System.out.println("dy[" + i + "] = " + dy[i]);
//    }
//
//    for (int i = 0; i < dx.length; i++) {
//      int x = frogPosition.x() + dx[i];
//      int y = frogPosition.y() + dy[i];
//      Position neighbor = new Position(Color.None, x, y, Color.None);
//      if (isPositionOccupied(neighbor)) {
//        // Search for the first unoccupied position in the same direction
//        while (isPositionOccupied(neighbor)) {
//          x += dx[i];
//          y += dy[i];
//          neighbor = new Position(Color.None, x, y, Color.None);
//        }
//        /* Check if the first unoccupied position is directly behind the
//         sequence of occupied positions */
//        if (!isPositionOccupied(neighbor)) {
//          viableJumpLocations.add(neighbor);
//          System.out.println("Viable jump location added: " + neighbor);
//        }
//      }
//    }
//  }

  private void calculateViableJumpLocations(Position frogPosition) {
    viableJumpLocations.clear();
    int[][] evenOffsets = {{1, 0}, {-1, 0}, {0, -1}, {0, 1}, {-1, -1}, {1, -1}};
    int[][] oddOffsets = {{1, 0}, {-1, 0}, {0, -1}, {0, 1}, {-1, 1}, {1, 1}};

    int[][] offsets = (frogPosition.y() % 2 == 0) ? evenOffsets : oddOffsets;

    for (int[] offset : offsets) {
      int x = frogPosition.x() + offset[0];
      int y = frogPosition.y() + offset[1];
      Position neighbor = new Position(Color.None, x, y, Color.None);
      boolean hasJumpedOver = false;

      // Check if the initial position is occupied
      if (isPositionOccupied(neighbor)) {
        hasJumpedOver = true;

        // Continue moving in the same direction
        while (isPositionOccupied(neighbor)) {
          x += offset[0];
          y += offset[1];
          neighbor = new Position(Color.None, x, y, Color.None);
        }

        // Add the first unoccupied position in the direction if a jump occurred
        if (!isPositionOccupied(neighbor) && hasJumpedOver) {
          viableJumpLocations.add(neighbor);
          System.out.println("Viable jump location added: " + neighbor);
        }
      }
    }
  }






  @Override
  public void clicked(Position position) {
    if (currentGamePhase == GamePhase.ANLEGEN && selectedFrog != null && spielLaueft) {
      Position newPos = new Position(selectedFrog, position.x(), position.y(), position.border());
      if (anlegen(newPos)) {
        selectedFrog = null;
        nachziehen();
        checkPhases();
        endTurn();
      }
    } else if (currentGamePhase == GamePhase.BEWEGEN && spielLaueft) {
      if (selectedFrogPosition == null) {
        // Select a frog to move
        if (selectFrogToMove(position)) {
          calculateViableJumpLocations(position);
        }
      } else {
        // Check if the position is a valid jump location
        if (viableJumpLocations.contains(position)) {
          // Move the selected frog
          moveFrog(selectedFrogPosition, position);
          selectedFrogPosition = position; // Update selected frog position
          calculateViableJumpLocations(selectedFrogPosition); // Recalculate viable jumps

          // Check for double-click to end the move
          if (position.equals(lastClickedPosition) && (System.currentTimeMillis() - lastClickTime) < DOUBLE_CLICK_THRESHOLD) {
            selectedFrogPosition = null;
            viableJumpLocations.clear();
            checkPhases();
            endTurn();
            System.out.println("Bewegen phase ended.");
          }
          lastClickedPosition = position;
          lastClickTime = System.currentTimeMillis();
        } else {
          System.out.println("Invalid move position: " + position);
        }
      }
    }
  }





  private boolean selectFrogToMove(Position position) {
    for (Position pos : board) {
      if (pos.equals(position) && pos.frog() == currentPlayer.getPlayerColor()) {
        selectedFrogPosition = pos;
        System.out.println("Frog selected for movement: " + pos);
        return true;
      }
    }
    System.out.println("Invalid frog selection: " + position);
    return false;
  }

  /**
   * Method to draw a frog from the bag.
   */
  public void nachziehen() {
    if (currentGamePhase == GamePhase.NACHZIEHEN && currentPlayer.getFrogsInHand().size() < 2) {
      takeFrogFromBag(currentPlayer);
      currentGamePhase = GamePhase.BEWEGEN;
      checkPhases();
    }
  }

  /**
   * Method to draw a frog from the bag.
   */
  public void bewegen() {
    if (currentGamePhase == GamePhase.BEWEGEN) {
      currentGamePhase = GamePhase.ANLEGEN;
      checkPhases();
    }
  }

  /**
   * Method to move a frog from one position to another.
   *
   * @param from The position of the frog to move
   * @param to   The position to move the frog to
   */
//  public void moveFrog(Position from, Position to) {
//    if (currentGamePhase == GamePhase.BEWEGEN && isPositionOccupied(from)
//        &&
//        viableJumpLocations.contains(to)) {
//        destinationPosition = new Position(from.frog(), to.x(), to.y(), from.border());
//        board.remove(from);
//        board.add(destinationPosition);
//        System.out.println("Frog moved from " + from + " to " + destinationPosition);
//        //selectedFrogPosition = to;
//        calculateViableJumpLocations(destinationPosition); // Recalculate viable jumps after moving
//
//    }
//  }

  public void moveFrog(Position from, Position to) {
    if (currentGamePhase == GamePhase.BEWEGEN && isPositionOccupied(from) && viableJumpLocations.contains(to)) {
      // Create the new destination position with the frog's color from the original position
      Position destinationPosition = new Position(from.frog(), to.x(), to.y(), from.border());

      // Remove the frog from the current position
      board.remove(from);

      // Add the frog to the new destination position
      board.add(destinationPosition);

      // Output the move for debugging
      System.out.println("Frog moved from " + from + " to " + destinationPosition);

      // Recalculate viable jump locations after moving the frog
      calculateViableJumpLocations(destinationPosition);
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

  /**
   * Method to end the current turn and switch to the next player.
   */
  public void endTurn() {
    int currentIndex = Arrays.asList(players).indexOf(currentPlayer);
    currentPlayer = players[(currentIndex + 1) % players.length];
    if (winner() != Color.None) {
      System.out.println("Game ended. Winner: " + winner());
      spielLaueft = false;
    }
    gameRound++;
    System.out.println("Turn " + gameRound + " ended. Next player: " + currentPlayer);
    currentGamePhase = GamePhase.BEWEGEN;
    checkPhases();
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
    if (currentPlayer.getPlayerColor() == player && currentGamePhase == GamePhase.ANLEGEN
        && frogsInHandMap.containsKey(currentPlayer)
        && frogsInHandMap.get(currentPlayer).contains(frog)) {
      selectedFrog = frog;
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

          if (size == 7 && count == 7) {
            return player.getPlayerColor();
          } else if (size > 7 && count == size) {
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

  /**
   * Method to take a frog from the bag and give it to the players.
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
   * Method to take a frog from the bag and give it to a specific player.
   *
   * @param player The player to give the frog to.
   */
  public void takeFrogFromBag(Player player) {
    if (gameBag.getNumoffrogs() > 0 && player.getFrogsInHand().size() < 2) {
      Frog takenFrog = gameBag.takeFrog();
      player.setMyFrogs(takenFrog);
      player.setFrogsInHand(takenFrog);
      frogsInHandMap.get(player).add(takenFrog.getFrogColor());
    }
  }

  /**
   * Method to calculate the number of connected frogs of a single player.
   *
   * @param start                   The starting position of the player.
   * @param positionsOfSinglePlayer The positions of the player.
   * @param visited                 The visited positions.
   * @return The number of connected frogs of a single player.
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

  /**
   * Method to place a frog on the board.
   *
   * @param pos The position to place the frog.
   * @return True if the frog was placed successfully, false otherwise.
   */
  public boolean anlegen(Position pos) {
    if (getBoard().isEmpty()) {
      board.add(pos);
      removeFrogFromHand(currentPlayer, selectedFrog);
      currentGamePhase = GamePhase.NACHZIEHEN;
      return true;
    }
    if (!isPositionOccupied(pos) && hasNeighbour(pos)) {
      if (pos.frog() == currentPlayer.getPlayerColor()) {
        if (!hasNeighbourOfSameColor(pos)) {
          board.add(pos);
          removeFrogFromHand(currentPlayer, selectedFrog);
          currentGamePhase = GamePhase.NACHZIEHEN;
          return true;
        }
      } else {
        board.add(pos);
        removeFrogFromHand(currentPlayer, selectedFrog);
        currentGamePhase = GamePhase.NACHZIEHEN;
        return true;
      }
    }
    return false;
  }

  /**
   * Method to remove a frog from a player's hand.
   *
   * @param player    The player to remove the frog from.
   * @param frogColor The color of the frog to remove.
   */
  private void removeFrogFromHand(Player player, Color frogColor) {
    frogsInHandMap.get(player).remove(frogColor);
    player.getFrogsInHand().removeIf(frog -> frog.getFrogColor() == frogColor);
  }

  /**
   * Method to check if two positions are neighbours.
   *
   * @param q1 The x-coordinate of the first position.
   * @param r1 The y-coordinate of the first position.
   * @param q2 The x-coordinate of the second position.
   * @param r2 The y-coordinate of the second position.
   * @return True if the positions are neighbours, false otherwise.
   */
  public boolean areNeighbours(int q1, int r1, int q2, int r2) {
    if (r1 == 0 || (abs(r1) % 2) == 0) {
      return (q2 == q1 + 1 && r2 == r1)  // Right neighbor
          || (q2 == q1 - 1 && r2 == r1)  // Left neighbor
          || (q2 == q1 && r2 == r1 - 1)  // Upper-right neighbor
          || (q2 == q1 && r2 == r1 + 1)  // Lower-right neighbor
          || (q2 == q1 - 1 && r2 == r1 - 1)  // Upper-left neighbor
          || (q2 == q1 - 1 && r2 == r1 + 1); // Lower-left neighbor
    } else {
      return (q2 == q1 + 1 && r2 == r1)  // Right neighbor
          || (q2 == q1 - 1 && r2 == r1) // Left neighbor
          || (q2 == q1 + 1 && r2 == r1 - 1)  // Upper-right neighbor
          || (q2 == q1 + 1 && r2 == r1 + 1)  // Lower-right neighbor
          || (q2 == q1 && r2 == r1 - 1)  // Upper-left neighbor
          || (q2 == q1 && r2 == r1 + 1); // Lower-left neighbor
    }
  }

  private boolean hasNeighbour(Position pos1) {
    for (Position pos2 : board) {
      if (areNeighbours(pos1.x(), pos1.y(), pos2.x(), pos2.y())) {
        return true;
      }
    }
    return false;
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

  private void checkPhases() {
    if (currentGamePhase == GamePhase.BEWEGEN && !canMoveAnyFrog()) {
      currentGamePhase = GamePhase.ANLEGEN;
    }
    if (currentGamePhase == GamePhase.ANLEGEN && !canPlaceAnyFrog()) {
      currentGamePhase = GamePhase.NACHZIEHEN;
    }
    if (currentGamePhase == GamePhase.NACHZIEHEN) {
      nachziehen();
      bewegen();
    }
  }

  private boolean canMoveAnyFrog() {
    for (Position pos : board) {
      if (pos.frog() == currentPlayer.getPlayerColor()) {
        for (Position neighbor : getNeighbors(pos)) {
          if (canMoveFrog(pos, neighbor)) {
            return true;
          }
        }
      }
    }
    return false;
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

  private boolean isPositionValidForPlacement(Position pos) {
    if (isPositionOccupied(pos) || !hasNeighbour(pos)) {
      return false;
    }
    if (pos.frog() == currentPlayer.getPlayerColor()) {
      return !hasNeighbourOfSameColor(pos);
    }
    return true;
  }

  private List<Position> getNeighbors(Position pos) {
    List<Position> neighbors = new ArrayList<>();
    int x = pos.x();
    int y = pos.y();

    int[] dxEven = {1, -1, 0, 0, -1, -1};
    int[] dyEven = {0, 0, -1, 1, -1, 1};
    int[] dxOdd = {1, -1, 0, 0, 1, 1};
    int[] dyOdd = {0, 0, -1, 1, -1, 1};

    int[] dx = (y % 2 == 0) ? dxEven : dxOdd;
    int[] dy = (y % 2 == 0) ? dyEven : dyOdd;

    for (int i = 0; i < dx.length; i++) {
      Position neighbor = new Position(Color.None, x + dx[i], y + dy[i], Color.None);
      if (isPositionOccupied(neighbor)) {
        neighbors.add(neighbor);
      }
    }

    return neighbors;
  }
}
