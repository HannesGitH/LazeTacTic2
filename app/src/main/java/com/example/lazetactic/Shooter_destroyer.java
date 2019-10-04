package com.example.lazetactic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

public class Shooter_destroyer extends Tower {
    Bitmap flame_u= BitmapFactory.decodeResource(Constants.context.getResources(), R.drawable.shooter_destroyer_flame);
    Bitmap flame2_u= BitmapFactory.decodeResource(Constants.context.getResources(), R.drawable.shooter_destroyer_flame2);
    Shooter_destroyer(int x, int y,boolean fp){
        super(x,y,fp);
        setImg(R.drawable.shooter_destroyer);
        initstuff();
    }
    @Override
    int get_class_number() {
        return 12;
    }
    @Override
    public short[] get_damagedirs(){
        return new short[]{(short)(10*(imgrot+1)+(imgrot+1)%4+1)};
    }

    @Override
    public void draw_onfield(Canvas canvas){
        super.draw_onfield(canvas);

        if(!destoyed) {
            if (flip) {
                canvas.drawBitmap(flame[imgrot], null, r[imgrot], paint);
            } else {
                canvas.drawBitmap(flame2[imgrot], null, r[imgrot], paint);
            }
int a;
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
            rot.postRotate(90);
            flame[i]=Bitmap.createBitmap(flame_u,0,0,flame_u.getWidth(),flame_u.getHeight(),rot,true);
            flame2[i]=Bitmap.createBitmap(flame2_u,0,0,flame2_u.getWidth(),flame2_u.getHeight(),rot,true);
        }
        r[3]=new Rect(this.x+size/2,this.y-size/2*3,this.x+size/2*3,this.y-size/2);
        r[0]=new Rect(this.x+size/2,this.y+size/2,this.x+size/2*3,this.y+size/2*3);
        r[1]=new Rect(this.x-size/2*3,this.y+size/2,this.x-size/2,this.y+size/2*3);
        r[2]=new Rect(this.x-size/2*3,this.y-size/2*3,this.x-size/2,this.y-size/2);
    }

}
