package aof;

import de.fhkiel.tsw.armyoffrogs.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Represents the bag of frogs.
 */
public class Bag {
  private List<Color> frogs;

  public Bag() {

    this(new Color[] {Color.Red, Color.Green, Color.Blue, Color.White});
  }

  /**
   * Creates a new bag with the given players.
   *
   * @param players The players to create the bag for.
   */
  public Bag(Color[] players) {
    List<Color> newFrogList = new ArrayList<>();
    for (Color player : players) {
      for (int i = 0; i < 10; ++i) {
        newFrogList.add(player);
      }
      frogs = newFrogList;
    }
  }

  public int getFrogs() {
    return frogs.size();
  }

  /**
   * Takes a frog from the bag.
   *
   * @return The color of the frog.
   */
  public Color takeFrog() {
    if (!frogs.isEmpty()) {

      var sample = new Random().nextInt(frogs.size());
      return frogs.remove(sample);
    } else {
      return Color.None;
    }
  }
}
