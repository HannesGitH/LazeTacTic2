package com.example.lazetactic;

public class Laser extends Tower {
    Laser(int x, int y,boolean fp){
        super(x,y,fp);
        setImg(R.drawable.laser);
    }
    @Override
    int get_class_number() {
        return 5;
    }
}
