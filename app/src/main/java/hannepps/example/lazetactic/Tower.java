package hannepps.example.lazetactic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;

import androidx.core.content.ContextCompat;

import com.example.lazetactic.R;

import java.util.Random;


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

    Rect rect;
    Tower(int x, int y,boolean first_player){
        this.x=x;
        this.y=y;
        this.setImg(R.drawable.no_pic_found);
        this.first_player=first_player;

        size=Constants.tower_size;
        rect=new Rect(this.x-size/2,this.y-size/2,this.x+size/2,this.y+size/2);
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

    int size;
    void setLocation(int x, int y){
        this.x=x;
        this.y=y;
        size=Constants.tower_size;
        rect=new Rect(this.x-size/2,this.y-size/2,this.x+size/2,this.y+size/2);
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
        size=Constants.tower_size;
        return (point[0]>x-size/2&&point[1]>y-size/2)&&point[0]<x+size/2&&point[1]<y+size/2;
    }



    void draw(Canvas canvas){
        canvas.drawBitmap(img[imgrot],null,rect ,new Paint());
    }

    void draw_onfield(Canvas canvas){
        size=Constants.tower_resized;
        canvas.drawBitmap(img[imgrot],null, rect,paint);
    }

    boolean destoyed=false;
    void destroy(){
        if(!destoyed){
            this.destoyed=true;
            Matrix transform=new Matrix();
            Random r = new Random();
            int i1 = (r.nextInt(360) + 360);
            transform.postRotate(i1);
            //transform.postScale(r.nextFloat()+1,r.nextFloat()+1); //bringt eh nix weil es beim drawn nochmal gescaled wird
            Bitmap bm = BitmapFactory.decodeResource(Constants.context.getResources(), R.drawable.debris);
            bm=Bitmap.createBitmap(bm,0,0,bm.getWidth(),bm.getHeight(),transform,true);
            for(int i=0;i<4;i++){
                this.img[i]= bm;
            }
            System.out.println("kapuuut");
            if(belongsto!=null)belongsto.free=true;
            remove();
        }
    }
    void remove(){

    }
    short[] get_damagedirs(){
        //1:top 2:right 3:down 4:left
        //so 12:topright 11:toptop and so on
        return new short[]{0};
    }


    Casket belongsto=null;
    void setbelongCasket(Casket c){
        belongsto=c;
    }




    Laze laze(Laze l){
        if(l.color!=(first_player?1:2))destroy();
        return l;
    }
    void removelt(){}//unsauber aber naja brauch ich für prism
}
