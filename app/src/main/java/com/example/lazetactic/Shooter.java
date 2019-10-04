package com.example.lazetactic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

public class Shooter extends Tower {
    Bitmap flame_u= BitmapFactory.decodeResource(Constants.context.getResources(), R.drawable.shooter_flame);
    Bitmap flame2_u= BitmapFactory.decodeResource(Constants.context.getResources(), R.drawable.shooter_flame2);

    Shooter(int x, int y,boolean fp){
        super(x,y,fp);
        setImg(R.drawable.shooter);
        initstuff();
    }
    @Override
    int get_class_number() {
        return 11;
    }

    @Override
    short[]get_damagedirs(){
        return new short[]{1,2,3,4,11,22,33,44};
    }

    @Override
    public void draw_onfield(Canvas canvas){
        super.draw_onfield(canvas);

        if(!destoyed) {
            if (flip) {
                for(int i=0;i<4;i++){
                    canvas.drawBitmap(flame[i], null, r[i], paint);
                }

            } else {
                for(int i=0;i<4;i++){
                    canvas.drawBitmap(flame2[i], null, r[i], paint);
                }
            }

            flip ^= true;
        }
    }


    @Override
    public void setLocation(int x,int y){
        super.setLocation(x,y);
        initstuff();
    }
    boolean flip=true;
    Bitmap flame[] =new Bitmap[4];
    Bitmap flame2[]=new Bitmap[4];
    Rect[] r=new Rect[4];
    void initstuff(){
        //int size=Constants.tower_resized;
        Matrix rot=new Matrix();
        for(int i=0;i<4;i++){
            flame[i]=Bitmap.createBitmap(flame_u,0,0,flame_u.getWidth(),flame_u.getHeight(),rot,true);
            flame2[i]=Bitmap.createBitmap(flame2_u,0,0,flame2_u.getWidth(),flame2_u.getHeight(),rot,true);
            rot.postRotate(90);
        }
        r[3]=new Rect(this.x-size/2*5,this.y-size/2,this.x-size/2,this.y+size/2); //links
        r[2]=new Rect(this.x-size/2,this.y+size/2,this.x+size/2,this.y+size/2*5); //unten
        r[1]=new Rect(this.x+size/2,this.y-size/2,this.x+size/2*5,this.y+size/2); //rechts
        r[0]=new Rect(this.x-size/2,this.y-size/2*5,this.x+size/2,this.y-size/2); //oben
    }
    int a;
}