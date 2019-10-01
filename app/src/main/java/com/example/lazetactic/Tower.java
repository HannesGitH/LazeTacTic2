package com.example.lazetactic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;


class Tower {


    Bitmap[] img=new Bitmap[4];

    int get_class_number(){
        return 0;
    }

    int imgrot=0;

    int x,y;
    Paint paint = new Paint();
    boolean chosen = false;
    boolean first_player;
    ColorFilter filter;


    Tower(int x, int y,boolean first_player){
        this.x=x;
        this.y=y;
        this.setImg(R.drawable.no_pic_found);
        this.first_player=first_player;
    }

    void setImg(int res){
        Matrix rot = new Matrix();
        rot.postRotate(90);
        try {
            if(first_player){
                filter = new PorterDuffColorFilter(ContextCompat.getColor(Constants.context, R.color.player_one), PorterDuff.Mode.MULTIPLY);
            }else{
                filter = new PorterDuffColorFilter(ContextCompat.getColor(Constants.context, R.color.player_two), PorterDuff.Mode.MULTIPLY);
            }
            paint.setColorFilter(filter);
            img[0] = BitmapFactory.decodeResource(Constants.context.getResources(), res);
        }catch(Exception e){
            img[0] = BitmapFactory.decodeResource(Constants.context.getResources(), R.drawable.no_pic_found);
        }

        for(int i=1;i<4;i++){
            img[i]= Bitmap.createBitmap(img[i-1],0,0,img[i-1].getWidth(),img[i-1].getHeight(),rot,true);
        }
    }
    int[] getLocation(){
        return new int[]{this.x,this.y};
    }

    void setLocation(int x, int y){
        this.x=x;
        this.y=y;
    }

    void update(){

    }

    void update_onfield(){

    }

    /*void draw( Canvas canvas ){
        this.paint.setColor(Color.RED);
        int size=Constants.tower_size;
        canvas.drawRect(new Rect(this.x-size/2,this.y-size/2,this.x+size/2,this.y+size/2),paint);
    }

    void draw_onfield( Canvas canvas ){
        this.paint.setColor(Color.RED);
        int size=Constants.tower_resized;
        canvas.drawRect(new Rect(this.x-size/2,this.y-size/2,this.x+size/2,this.y+size/2),paint);
    }*/

    void rotate(){
        imgrot=(imgrot+1)%4;
    }

    boolean is_in(int[] point){
        if(point==null){return false;}
        int size=Constants.tower_size;
        return (point[0]>x-size/2&&point[1]>y-size/2)&&point[0]<x+size/2&&point[1]<y+size/2;
    }



    void draw(Canvas canvas){
        int size=Constants.tower_size;
        canvas.drawBitmap(img[imgrot],null, new Rect(this.x-size/2,this.y-size/2,this.x+size/2,this.y+size/2),new Paint());
    }

    void draw_onfield(Canvas canvas){
        int size=Constants.tower_resized;
        canvas.drawBitmap(img[imgrot],null, new Rect(this.x-size/2,this.y-size/2,this.x+size/2,this.y+size/2),paint);
    }

    boolean destoyed=false;
    void destroy(){
        if(!destoyed){
            destoyed=true;
            for(int i=1;i<4;i++){
                img[i]= BitmapFactory.decodeResource(Constants.context.getResources(), R.drawable.debris);
            }
        }
    }
    short[] get_damagedirs(){
        return null;
    }

}
