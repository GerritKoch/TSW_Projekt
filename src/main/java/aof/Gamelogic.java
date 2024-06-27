package aof;

import de.fhkiel.tsw.armyoffrogs.Color;
import de.fhkiel.tsw.armyoffrogs.Game;
import de.fhkiel.tsw.armyoffrogs.Position;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Represents the game logic.
 */
public class Gamelogic implements Game {

  private Color[] player;

  @Override
  public boolean newGame(int numberOfPlayers) {
    if (numberOfPlayers >= 2 && numberOfPlayers <= 4) {
      player = new Color[numberOfPlayers];

      Color[] colorOrder = {Color.Red, Color.Green, Color.Blue, Color.White};
      for (int i = 0; i < numberOfPlayers; ++i) {
        player[i] = colorOrder[i];
      }

      startGame(numberOfPlayers);

      return true;
    } else {
      player = new Color[0];
      return false;
    }
  }

  @Override
  public Color[] players() {
    return player;
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

  private void startGame(int spieler) {
    bag = new Bag(players());
    for (int i = 0; i < 2 * spieler; ++i) {
      bag.takeFrog();
    }
  }

  public void takeFrogFromBag() {
    bag.takeFrog();
  }

}
