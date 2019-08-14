package com.example.lazetactic;

public class Wall implements Lazeable {
    Wall(){
    }
    public Laze laze(Laze l){
        return new Laze(l.dir,true);
    }
}
