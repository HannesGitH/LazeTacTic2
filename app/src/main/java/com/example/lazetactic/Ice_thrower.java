package com.example.lazetactic;

import android.graphics.Bitmap;

public class Ice_thrower extends Tower {
    private Bitmap img;
    Ice_thrower(int x, int y,boolean fp) {
        super(x, y,fp);
        this.setImg(R.drawable.ice_tower);
    }
    @Override
    int get_class_number(){
        return 3;
    }
    @Override
    void rotate(){

    }
}
