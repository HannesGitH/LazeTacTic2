package com.example.lazetactic;

public class Laser_safe_prism extends Tower {
    Laser_safe_prism(int x, int y,boolean fp){
        super(x,y,fp);
        setImg(R.drawable.laser_safe_prism);
    }
    @Override
    int get_class_number() {
        return 10;
    }
}

