package com.example.lazetactic;
public class Changer extends Tower {
    Changer(int x, int y,boolean fp){
        super(x,y,fp);
        setImg(R.drawable.changer);
    }
    @Override
    int get_class_number() {
        return 8;
    }
}
