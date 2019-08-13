package com.example.lazetactic;

public class Laser_safe_mirror extends Tower {
    Laser_safe_mirror(int x, int y,boolean fp){
        super(x,y,fp);
        setImg(R.drawable.laser_safe_mirror);
    }
    @Override
    int get_class_number() {
        return 9;
    }
}
