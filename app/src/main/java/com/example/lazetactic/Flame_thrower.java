package com.example.lazetactic;

import android.graphics.Bitmap;

public class Flame_thrower extends Tower {
    private Bitmap img;
    Flame_thrower(int x, int y,boolean fp){
        super(x,y,fp);

        this.setImg(R.drawable.flame_thrower_tower);
    }
    @Override
    int get_class_number(){
        return 4;
    }
    @Override
    void rotate(){

    }
}
