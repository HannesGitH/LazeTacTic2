package com.example.lazetactic;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

class Casket implements Lazeable{
    final int linewidth=4;
    int left,top,size;
    int[] w_center, mat_coords;
    boolean free=true;
    private Tower occ_by;

    void set(Tower tower){
        occ_by=tower;
        free=false;
        tower.setbelongCasket(this);
        //occ_by.setLocation(w_center[0],w_center[1]);
    }

    Tower occupied_by(){
        return occ_by;
    }

    Casket(int left, int top, int size, int[] mat_coords){
        this.left=left;
        this.top=top;
        this.size=size;
        this.mat_coords=mat_coords;
    }

    void update(){

    }

    void draw(Canvas canvas){
        Paint paint=new Paint();
        paint.setColor(Constants.context.getResources().getColor(R.color.playing_field_background));//rgb(90,255,90));
        int offset_x=(int)(Constants.tower_size*2.167);
        int offset_y=Constants.tower_size/6;
        canvas.drawRect(left+offset_x,top+offset_y,left+size+offset_x,top+size+offset_y,paint);
        paint.setColor(Color.LTGRAY);
        canvas.drawRect(left+linewidth/2+offset_x,top+linewidth/2+offset_y,left+size-linewidth/2+offset_x,top+size-linewidth/2+offset_y,paint);
    }

    void resize(float factor){
        this.size*=factor;
        this.left*=factor;
        this.top*=factor;
        this.w_center= new int[]{this.left+(int)(Constants.tower_size*2.167)+this.size/2, this.top+(int)(Constants.tower_size*0.167)+Constants.tower_resized/2};
        if(occ_by!=null){
            occ_by.setLocation(w_center[0],w_center[1]);
        }
    }

    public Laze laze(Laze l){
        return new Laze(l.dir,true);
    }
}
