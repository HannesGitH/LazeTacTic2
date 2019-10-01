package com.example.lazetactic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import static android.graphics.Bitmap.createScaledBitmap;

public class Flame_thrower extends Tower {
    Bitmap flame= BitmapFactory.decodeResource(Constants.context.getResources(), R.drawable.flame_thrower_flame);


    double heightfac;

    int deg=21;

    Matrix rot = new Matrix();
    Flame_thrower(int x, int y,boolean fp){
        super(x,y,fp);

        this.setImg(R.drawable.flame_thrower_tower);
        if (null==BitmapStore.fireRotated){
            BitmapStore.fireRotated=new Bitmap[360/deg+1];
        }
    }
    @Override
    int get_class_number(){
        return 4;
    }
    @Override
    void rotate(){

    }
    int step=0;
    @Override
    void draw_onfield(Canvas canvas){
        int size=Constants.tower_resized;
        canvas.drawBitmap(this.img[imgrot],null, new Rect(this.x-size/2,this.y-size/2,this.x+size/2,this.y+size/2),paint);

        Bitmap flame2;

        if(null==BitmapStore.fireRotated[step]){
            int degc=(deg*step)%360;
            rot=new Matrix();
            rot.preRotate(degc);
            heightfac=Math.abs(Math.sin(Math.toRadians(degc%90))-Math.sin(Math.toRadians(degc%90-90)));
            flame2= Bitmap.createBitmap(flame,0,0,flame.getWidth(),flame.getHeight(),rot,true);
            int width=(int)(size*3/2*heightfac);
            Bitmap newfl=createScaledBitmap(flame2,width,width,true);
            BitmapStore.fireRotated[step]=newfl;
        }

        Bitmap img= BitmapStore.fireRotated[step];
        int width=img.getHeight();
        canvas.drawBitmap(img,null, new Rect(this.x-width,this.y-width,this.x+width,this.y+width),new Paint());

        step=(step+1)%(360/deg);
    }

    @Override
    void update_onfield() {
        super.update_onfield();

    }
}
