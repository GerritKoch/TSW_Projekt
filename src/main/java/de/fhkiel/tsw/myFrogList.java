package de.fhkiel.tsw;

import java.util.ArrayList;

public class myFrogList extends ArrayList<Frog> {


@Override
public boolean add(Frog frog){
    if(this.size() < 3){
        return super.add(frog);
    }
    System.out.println("Froglist is full");
    return false;
}
}
