package de.fhkiel.tsw;

import de.fhkiel.tsw.armyoffrogs.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the Bag of Frogs that are used in the game.
 * It contains a list of frogs and a number of frogs
 */
public class Bag {
  private int numoffrogs;
  private boolean bagFilled;

  private List<Frog> frogsInBag;
  private List<Color> baseColorList = new ArrayList<>();

  /**
   * This is the default constructor for the Bag class.
   */
  public Bag() {

    this.baseColorList.add(Color.Blue);
    this.baseColorList.add(Color.Red);
    this.baseColorList.add(Color.Green);
    this.baseColorList.add(Color.Black);
    this.numoffrogs = 40;
    this.frogsInBag = new ArrayList<>();
  }


  /**
   * This is the constructor for the Bag class.
   *
   * @param numoffrogs  the number of frogs in the bag
   * @param playerCOlor the list of colors of the players
   */
  public Bag(int numoffrogs, List<Color> playerCOlor) {

    frogsInBag = new ArrayList<>();
    this.numoffrogs = numoffrogs;

    for (Color playerColor : playerCOlor) {
      for (int i = 0; i < 10; i++) {
        this.frogsInBag.add(new Frog(playerColor));
      }
    }
    bagFilled = true;
  }

  public Bag(int numoffrogs, Player[] players) {
    frogsInBag = new ArrayList<>();
    this.numoffrogs = numoffrogs;
    for (Player player : players) {
      this.frogsInBag.addAll(player.getFrogs());

    }
    bagFilled = true;
  }

  public int getNumoffrogs() {
    return numoffrogs;
  }

  public Frog takeFrog() {
    if (numoffrogs > 0) {
      numoffrogs = numoffrogs - 1;
      return frogsInBag.remove(0);
    }
    return null;
  }

  public void takeFrog(Color color) {
    if (numoffrogs > 0) {
      numoffrogs = numoffrogs - 1;
      frogsInBag.removeIf(frog -> frog.getFrogColor() == color && numoffrogs > 0);

    }

  }

  public List<Frog> getFrogsInBag() {
    return frogsInBag;
  }

  public List<Frog> getFrogsInBag(Color color) {
    return frogsInBag.stream().filter(frog -> frog.getFrogColor() == color).toList();
  }


  public boolean isBagFilled() {
    return bagFilled;
  }
}
