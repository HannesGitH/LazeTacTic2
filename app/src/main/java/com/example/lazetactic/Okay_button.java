package com.example.lazetactic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Okay_button {
    private Bitmap img;
    Paint paint=new Paint();
    int size=Constants.tower_size;
    Okay_button(){
        img= BitmapFactory.decodeResource(Constants.context.getResources(),R.drawable.okay_button);

        x=Constants.s_width-size;
        y=Constants.s_height-size;
    }
    int x,y;
    boolean izzda=false;

    void draw(Canvas canvas){
        if(izzda){

            canvas.drawBitmap(img,null, new Rect(x-size/2,y-size/2,x+size/2,y+size/2),paint);

        }
    }

}
