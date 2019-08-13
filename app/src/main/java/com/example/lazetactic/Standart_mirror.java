package com.example.lazetactic;

import android.graphics.Bitmap;

class Standart_mirror extends Tower{
    private Bitmap[] img=new Bitmap[4];
    Standart_mirror(int x, int y,boolean fp){
        super(x,y,fp);
        this.setImg(R.drawable.standart_mirror);
    }


    @Override
    int get_class_number(){
        return 2;
    }




}
