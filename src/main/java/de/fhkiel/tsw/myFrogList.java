package de.fhkiel.tsw;

import java.util.ArrayList;

/**
 * This class represents a list of frogs. It extends the ArrayList class and has a method to add a frog to the list.
 */
public class myFrogList extends ArrayList<Frog> {


  @Override
  public boolean add(Frog frog) {
    if (this.size() < 3) {
      return super.add(frog);
    }
    System.out.println("Froglist is full");
    return false;
  }
}
