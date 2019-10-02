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

    @Override
    short[]get_damagedirs(){
        return new short[]{1,2,3,4,11,22,33,44};
    }
}