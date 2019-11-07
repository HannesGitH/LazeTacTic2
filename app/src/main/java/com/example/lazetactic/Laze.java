package com.example.lazetactic;

public class Laze {
    short dir; //0hoch 1rechts usw
    boolean end;
    short color;
    Casket isin;

    Laze(short dir, boolean end, short player,Casket in){
        this.dir=dir;
        this.end=end;
        this.color=player;
        isin=in;
    }
}
