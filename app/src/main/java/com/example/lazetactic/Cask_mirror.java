package com.example.lazetactic;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

class Cask_mirror implements Lazeable{
    final int linewidth=4;
    int left,top,size,dir;
    private Bitmap img;
    int[] mat_coords;

    class WrongPlayMatrixValueException extends Exception
    {
        WrongPlayMatrixValueException()
        {
            super("Wrong Rotation in Matrix, Value must be 0<=x<=5");
        }
    }

    Cask_mirror(int left, int top, int size, int dir,int[] mat_coords) throws WrongPlayMatrixValueException {
        this.left=left;
        this.top=top;
        this.size=size;
        this.dir=dir;
        this.mat_coords=mat_coords;

        img= BitmapFactory.decodeResource(Constants.context.getResources(),R.drawable.cask_mirror);

        Matrix rot = new Matrix();

        switch (dir){
            case 5:
                rot.postRotate(90);

            case 4:
                rot.postRotate(90);

            case 3:
                rot.postRotate(90);

            case 2:
                img= Bitmap.createBitmap(img,0,0,img.getWidth(),img.getHeight(),rot,true);
                break;

            default:
                throw new WrongPlayMatrixValueException();
        }
    }

    void update(){

    }

    void draw(Canvas canvas){
        Paint paint=new Paint();
        paint.setColor(Color.BLACK);//rgb(90,255,90));
        int offset_x=(int)(Constants.tower_size*2.167);
        int offset_y=Constants.tower_size/6;

        canvas.drawBitmap(img,null, new Rect(left+offset_x,top+offset_y,left+size+offset_x,top+size+offset_y),paint);
    }

    void resize(float factor) {
        this.size *= factor;
        this.left *= factor;
        this.top *= factor;
    }

    public Laze laze(Laze l){
        int i=(this.dir-l.dir)%4;
        short ndir= (short) (l.dir+1);
        if((l.dir==0&&dir==4)||(l.dir==1&&dir==5)||(l.dir==3&&dir==3)||(l.dir==2&&dir==2)){
            ndir-=2;
        }
        ndir=(short)(ndir%4);
        return new Laze(ndir,(i==1||i==2),(short)l.color,l.isin);
    }
}
