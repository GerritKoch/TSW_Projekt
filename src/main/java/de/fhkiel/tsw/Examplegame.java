package de.fhkiel.tsw;

import de.fhkiel.tsw.armyoffrogs.Color;
import de.fhkiel.tsw.armyoffrogs.Game;
import de.fhkiel.tsw.armyoffrogs.Position;

import java.util.*;

public class Examplegame implements Game {
  private Color[] players;
  private Map<Color, List<Color>> frogsInHand;
  private Set<Position> board;
  private boolean gameStarted;
  private Color currentPlayer;
  private Color selectedFrog;
  private GamePhase currentPhase;
  private List<Color> frogBag;

  private enum GamePhase {
    ANLEGEN,
    NACHZIEHEN
  }

  public Examplegame() {
    frogsInHand = new HashMap<>();
    board = new HashSet<>();
    frogBag = new ArrayList<>();
    gameStarted = false;
  }

  @Override
  public boolean newGame(int numberOfPlayers) {
    if (numberOfPlayers < 2 || numberOfPlayers > 4) {
      players = new Color[0];
      return false;
    }

    // Reset game state
    players = new Color[numberOfPlayers];
    frogsInHand.clear();
    board.clear();
    frogBag.clear();
    gameStarted = false;

    // Initialize players
    Color[] colorOrder = {Color.Red, Color.Green, Color.Blue, Color.White};
    for (int i = 0; i < numberOfPlayers; i++) {
      players[i] = colorOrder[i];
      frogsInHand.put(players[i], new ArrayList<>());
    }

    // Add frogs to the bag
    for (int i = 0; i < numberOfPlayers; i++) {
      for (int j = 0; j < 10; j++) {
        frogBag.add(colorOrder[i]);
      }
    }

    // Shuffle the bag to randomize frog drawing
    Collections.shuffle(frogBag);

    // Distribute 2 frogs to each player
    for (Color player : players) {
      for (int i = 0; i < 2; i++) {
        frogsInHand.get(player).add(takeFrogFromBag());
      }
    }

    gameStarted = true;
    currentPlayer = players[0]; // Set the first player to start
    selectedFrog = null;
    currentPhase = GamePhase.ANLEGEN;
    System.out.println("newGame(" + numberOfPlayers + ") ausgeführt.");
    return true;
  }

  @Override
  public Color[] players() {
    System.out.println("players() ausgeführt.");
    return players;
  }

  @Override
  public String getInfo() {
    String info = gameStarted ? "Das Spiel läuft mit " + players.length + " Spielern. " + "Aktueller Spieler: " + currentPlayer + ", Phase: " + currentPhase : "Kein Spiel gestartet.";
    System.out.println("getInfo() ausgeführt.");
    return info;
  }

  @Override
  public int frogsInBag() {
    System.out.println("frogsInBag() ausgeführt.");
    return frogBag.size();
  }

  @Override
  public List<Color> getFrogsInHand(Color player) {
    System.out.println("getFrogsInHand(" + player + ") ausgeführt.");
    return frogsInHand.getOrDefault(player, new ArrayList<>());
  }

  @Override
  public Set<Position> getBoard() {
    System.out.println("getBoard() ausgeführt.");
    return board;
  }

  @Override
  public void clicked(Position position) {
    if (currentPhase == GamePhase.ANLEGEN && selectedFrog != null) {
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
    }
  }

  @Override
  public void selectedFrogInHand(Color player, Color frog) {
    if (currentPlayer == player && currentPhase == GamePhase.ANLEGEN && frogsInHand.containsKey(player) && frogsInHand.get(player).contains(frog)) {
      selectedFrog = frog;
      frogsInHand.get(player).remove(frog);
      System.out.println("selectedFrogInHand(" + player + ", " + frog + ") ausgeführt.");
    } else {
      System.out.println("Invalid frog selection.");
    }
  }

  @Override
  public Color winner() {
    System.out.println("winner() ausgeführt.");
    // Simulate no winner
    return Color.None;
  }

  @Override
  public boolean save(String filename) {
    System.out.println("save(" + filename + ") ausgeführt.");
    // Simulate save operation
    return true;
  }

  @Override
  public boolean load(String filename) {
    System.out.println("load(" + filename + ") ausgeführt.");
    // Simulate load operation
    return true;
  }

  private Color takeFrogFromBag() {
    if (!frogBag.isEmpty()) {
      return frogBag.remove(0);
    }
    return Color.None;
  }

  private void drawFrog() {
    if (currentPlayer != null && frogsInHand.get(currentPlayer).size() < 2 && !frogBag.isEmpty()) {
      frogsInHand.get(currentPlayer).add(takeFrogFromBag());
      System.out.println(currentPlayer + " drew a frog.");
    } else {
      System.out.println("No frogs left to draw or hand already has 2 frogs.");
    }
  }

  private boolean isPositionOccupied(Position position) {
    for (Position pos : board) {
      if (pos.x() == position.x() && pos.y() == position.y()) {
        return true;
      }
    }
    return false;
  }

  // Method to handle the end of the turn and move to the next player
  private void endTurn() {
    // Automatically proceed to the next player
    int currentIndex = Arrays.asList(players).indexOf(currentPlayer);
    currentPlayer = players[(currentIndex + 1) % players.length];
    currentPhase = GamePhase.ANLEGEN;
    System.out.println("Turn ended. Next player: " + currentPlayer);
  }
}
