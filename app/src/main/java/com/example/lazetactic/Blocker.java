package com.example.lazetactic;

public class Blocker extends Tower {
    Blocker(int x, int y,boolean fp){
        super(x,y,fp);
        setImg(R.drawable.blocker);
    }
    @Override
    int get_class_number() {
        return 7;
    }

    @Override Laze laze (Laze laze){
        return new Laze(laze.dir,true,laze.color,laze.isin);
    }
}
