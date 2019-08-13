package com.example.lazetactic;
public class Shooter extends Tower {
    Shooter(int x, int y,boolean fp){
        super(x,y,fp);
        setImg(R.drawable.shooter);
    }
    @Override
    int get_class_number() {
        return 11;
    }
}