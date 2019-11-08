package com.example.lazetactic;

public class Laze {
    short dir; //0hoch 1rechts usw
    boolean end;
    short color; //0: kein spieler ; 1:spieler 1; 2:spieler 2
    Lazeable isin;

    Laze(short dir, boolean end, short player,Lazeable in){
        this.dir=dir;
        this.end=end;
        this.color=player;
        isin=in;
    }
    Laze(Laze l){
        this.dir=l.dir;
        this.end=l.end;
        this.color=l.color;
        this.isin=l.isin;
    }
}
