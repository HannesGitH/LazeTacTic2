package com.example.lazetactic;

import android.graphics.Bitmap;

import java.util.Arrays;

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

    short[][] mirmat=new short[][]{
            {5,5,1,0},
            {1,5,5,2},
            {3,2,5,5},
            {5,0,3,5},
    };
    @Override Laze laze (Laze laze){
        short ndir=mirmat[this.imgrot][laze.dir];
        if (ndir==5){
            destroy();
            return laze;
        }
        short color=(short)(first_player?1:2);
        if(laze.color==(first_player?2:1))color=0;
        Laze l=new Laze(ndir,false,color,laze.isin);
        return l;
    }



}
