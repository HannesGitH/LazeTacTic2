package hannepps.example.lazetactic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;

import androidx.core.content.ContextCompat;

import com.example.lazetactic.R;

import java.util.ArrayList;


public class Laser_trace {
    Laser origin;
    ArrayList<Laze> lazes=new ArrayList<>();
    Trace_Manager tm= new Trace_Manager();
    Bitmap[][] p_lazes  = new Bitmap[2][4]; //animframe,rotation
    PorterDuffColorFilter filterp2;
    PorterDuffColorFilter filterp1;
    Laser_trace(Laser laser){
        origin=laser;
        lazes.add(new Laze((short)(origin.imgrot),false,origin.first_player?(short)1:(short)2,origin.belongsto));
        tm.add(this);
        p_lazes[0][0]= BitmapFactory.decodeResource(Constants.context.getResources(), R.drawable.trace_1);
        p_lazes[1][0]= BitmapFactory.decodeResource(Constants.context.getResources(), R.drawable.trace_2);
        Matrix rot = new Matrix();
        rot.postRotate(90);
        for(int i=1;i<4;i++) {
            p_lazes[0][i] = Bitmap.createBitmap(p_lazes[0][i - 1], 0, 0, p_lazes[0][i - 1].getWidth(), p_lazes[0][i - 1].getHeight(), rot, true);
            p_lazes[1][i] = Bitmap.createBitmap(p_lazes[1][i - 1], 0, 0, p_lazes[1][i - 1].getWidth(), p_lazes[1][i - 1].getHeight(), rot, true);
        }
        filterp1 = new PorterDuffColorFilter(ContextCompat.getColor(Constants.context, R.color.player_one), PorterDuff.Mode.MULTIPLY);
        filterp2 = new PorterDuffColorFilter(ContextCompat.getColor(Constants.context, R.color.player_two), PorterDuff.Mode.MULTIPLY);

    }
    Laser_trace(Tower laser,Short coll){
        Tower origin=laser;
        lazes.add(new Laze((short)((origin.imgrot-1)%4),false,coll,origin.belongsto));
        tm.add(this);
        p_lazes[0][0]= BitmapFactory.decodeResource(Constants.context.getResources(), R.drawable.trace_1);
        p_lazes[1][0]= BitmapFactory.decodeResource(Constants.context.getResources(), R.drawable.trace_2);
        Matrix rot = new Matrix();
        rot.postRotate(90);
        for(int i=1;i<4;i++) {
            p_lazes[0][i] = Bitmap.createBitmap(p_lazes[0][i - 1], 0, 0, p_lazes[0][i - 1].getWidth(), p_lazes[0][i - 1].getHeight(), rot, true);
            p_lazes[1][i] = Bitmap.createBitmap(p_lazes[1][i - 1], 0, 0, p_lazes[1][i - 1].getWidth(), p_lazes[1][i - 1].getHeight(), rot, true);
        }
        filterp1 = new PorterDuffColorFilter(ContextCompat.getColor(Constants.context, R.color.player_one), PorterDuff.Mode.MULTIPLY);
        filterp2 = new PorterDuffColorFilter(ContextCompat.getColor(Constants.context, R.color.player_two), PorterDuff.Mode.MULTIPLY);

    }
    void update(){

    }
    Paint p=new Paint();
    int b=0;
    void draw(Canvas c){
        b=(b+1)%2;
        Rect r=new Rect();
        for(Laze laze:lazes){
            Lazeable k=laze.isin;
            int[] ce =k.getw_center(); int s = k.getsize()/2; int d=laze.dir;
            switch (laze.color){
                case 1:
                    p.setColorFilter(filterp1);
                    break;
                case 2:
                    p.setColorFilter(filterp2);
                    break;
                default:
                    p=new Paint();
            }
            switch(d){
                case 0:
                     r = new Rect(ce[0]-s,ce[1]-2*s,ce[0]+s,ce[1]);
                     break;
                case 1:
                    r = new Rect(ce[0],ce[1]-s,ce[0]+2*s,ce[1]+s);
                    break;
                case 2:
                    r = new Rect(ce[0]-s,ce[1],ce[0]+s,ce[1]+2*s);
                    break;
                case 3:
                    r = new Rect(ce[0]-2*s,ce[1]-s,ce[0],ce[1]+s);
                    break;
            }

            c.drawBitmap(p_lazes[b][d],null,r,p);//0,0,p);//
        }
    }
    void remove(){ tm.remove(this);
    }

    void calc_dam(Playing_field pf){
        Laze l4=lazes.get(0);
        lazes=new ArrayList<>();
        lazes.add(l4);
        int i=0;
        Laze l2;
        while(!lazes.get(i).end){//todo end
            Laze l=lazes.get(i);
            l2=new Laze(l);
            //Die koordinaten sind leider sehr messed up wegen x und y verwechselung

            int[] coor= new int[]{l2.isin.getmat_coords()[1], l2.isin.getmat_coords()[0]};
            System.out.println(coor[0]+"+"+coor[1]);
            try{
                switch(l2.dir) {
                    case 3:
                        l2.isin=pf.field[coor[0]][coor[1]-1];break;
                    case 2:
                        l2.isin=pf.field[coor[0]+1][coor[1]];break;
                    case 1:
                        l2.isin=pf.field[coor[0]][coor[1]+1];break;
                    case 0:
                        l2.isin=pf.field[coor[0]-1][coor[1]];break;
                }
                Laze l3 = l2.isin.laze(l2);
                lazes.add(l3);
                if(l3.end)return;
                System.out.println(l3.isin.getmat_coords()[0]+","+l3.isin.getmat_coords()[1]+","+l3.dir+"   "+lazes.size());
                i++;
                l2=l3;
            }catch (Exception e){return;}
        }
    }
}
