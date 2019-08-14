package com.example.lazetactic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

public class Ice_thrower extends Tower {
    Bitmap flame= BitmapFactory.decodeResource(Constants.context.getResources(), R.drawable.ice_flame);

    double heightfac;

    int deg=21;

    Matrix rot = new Matrix();
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
    int degc=0;
    @Override
    void draw_onfield(Canvas canvas){
        int size=Constants.tower_resized;
        canvas.drawBitmap(this.img[imgrot],null, new Rect(this.x-size/2,this.y-size/2,this.x+size/2,this.y+size/2),paint);

        degc=(deg+degc)%360;

        rot.preRotate(deg);

        heightfac=Math.abs(Math.sin(Math.toRadians(degc%90))-Math.sin(Math.toRadians(degc%90-90)));
        Bitmap flame2= Bitmap.createBitmap(flame,0,0,flame.getWidth(),flame.getHeight(),rot,true);
        int width=(int)(size*3/2*heightfac);
        canvas.drawBitmap(flame2,null, new Rect(this.x-width,this.y-width,this.x+width,this.y+width),paint);
    }
}
