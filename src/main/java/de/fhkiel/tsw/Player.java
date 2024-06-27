package de.fhkiel.tsw;

import de.fhkiel.tsw.armyoffrogs.Color;
import java.util.ArrayList;
import java.util.List;


/**
 * Player class.
 */
public class Player {

  private Color playerColor;
  private List<Frog> myFrogs;
  private MyFrogList frogsInHand;
  private Gamelogic currentGame;
  public boolean isMyTurn;

  /**
   * Constructor for the Player class.
   *
   * @param inputColor the color of the player
   */
  public Player(Color inputColor) {

    myFrogs = new ArrayList<>();
    frogsInHand = new MyFrogList();

    this.playerColor = inputColor;
    for (int i = 0; i < 10; i++) {
      myFrogs.add(new Frog(inputColor));
    }
  }

  private enum Actions {
    BEWEGEN,
    ANLEGEN,
    NACHZIEHEN
  }


  public Color getPlayerColor() {

    return playerColor;
  }

  public List<Frog> getFrogs() {

    return myFrogs;
  }

  public void setPlayerColor(Color playerColor) {

    this.playerColor = playerColor;
  }

  public List<Frog> getFrogsInHand() {

    return frogsInHand;
  }

  public void setFrogsInHand(Frog frogInHand) {

    this.frogsInHand.add(frogInHand);
  }

  public void setMyFrogs(List<Frog> myFrogs) {

    this.myFrogs = myFrogs;
  }

  public void setMyFrogs(Frog frog) {

    this.myFrogs.add(frog);
  }


}
