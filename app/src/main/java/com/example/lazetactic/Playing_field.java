package com.example.lazetactic;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

import static com.example.lazetactic.Constants.tower_resized;

class Playing_field {
    ArrayList<Casket> caskets=new ArrayList<>();
    ArrayList<Cask_mirror> mirrors =new ArrayList<>();
    private int width_c;
    private int height_c;

    DamageMap damageMap;

    Lazeable[][] field;

    Playing_field(short[][] field_matrix){
        this.height_c=field_matrix.length;
        this.width_c=field_matrix[0].length;
        field=new Lazeable[height_c][width_c];
        damageMap=new DamageMap(width_c,height_c);
        for (short y_i = 0; y_i < field_matrix.length; y_i++) {
            for (short x_i = 0; x_i < field_matrix[y_i].length; x_i++) {
                int h=Constants.s_height;
                if(h==0){h=60;}
                System.out.println(h);
                switch(field_matrix[y_i][x_i]){
                    case 0:
                        //wall
                        field[y_i][x_i]=new Wall();

                        damageMap.seta(x_i,y_i, (short) 4);
                        break;

                    case 1:
                        //casket
                        field[y_i][x_i]=new Casket(h*(x_i)/6,h*(y_i)/6,h/6,new int[]{x_i,y_i});
                        caskets.add((Casket) field[y_i][x_i]);

                        damageMap.seta(x_i,y_i, (short) 0);
                        break;

                    default:
                        //mirror
                        //  2=◣ ; 3=◤ ; 4=◥ ; 5=◢
                        try {
                            field[y_i][x_i]=new Cask_mirror(h*(x_i)/6,h*(y_i)/6,h/6,field_matrix[y_i][x_i],new int[]{x_i,y_i});
                            mirrors.add((Cask_mirror)field[y_i][x_i]);
                        } catch (Cask_mirror.WrongPlayMatrixValueException e) {
                            e.printStackTrace();
                        }

                        damageMap.seta(x_i,y_i, (short) 0);
                        break;
                }
            }
        }
    }
    void update(){
        for (Casket casket : caskets) {
            casket.update();
        }
        for (Cask_mirror mirror : mirrors) {
            mirror.update();
        }
        for (Tower tower: towers){
            tower.update_onfield();
        }
    }
    void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Constants.context.getResources().getColor(R.color.playing_field_background));//rgb(90,255,90));
        canvas.drawRect(Constants.tower_size*2,0,Constants.s_width,Constants.s_height,paint);
        for (Casket casket : caskets) {
            casket.draw(canvas);
        }
        for (Cask_mirror mirror : mirrors) {
            mirror.draw(canvas);
        }
        for (Tower tower: towers){
            tower.draw_onfield(canvas);
        }
    }

    void setHeight(){
        int h=Constants.s_height;
        int ts=Constants.tower_size;
        int w=Constants.s_width;

        int heightfac=(h-ts/3)/height_c;
        int widthfac=(int)((w-2.333*ts)/width_c);

        tower_resized=Math.min(widthfac,heightfac);

        float factor=(float) tower_resized/caskets.get(0).size;
        for (Casket casket : caskets) {
            casket.resize(factor);
        }
        for (Cask_mirror mirror : mirrors) {
            mirror.resize(factor);
        }
    }

    public boolean full=false;

    Casket nearest=null;

    Casket getNearest(int[] coords){
        int dst=1000000;
        int c_dst;
        for (Casket casket: caskets){
            c_dst=(int) Math.sqrt((casket.w_center[1]-coords[1])*(casket.w_center[1]-coords[1])+(casket.w_center[0]-coords[0])*(casket.w_center[0]-coords[0]));

            full=true;
            if(c_dst<dst&&casket.free){
                dst=c_dst;
                nearest=casket;
                full=false;
            }
        }
        return nearest;
    }

    private ArrayList<Tower> towers=new ArrayList<>();

    void push(Tower tower, boolean firstplayersturn){
        towers.add(tower);
        nearest.set(tower);
        if(damageMap.willIDieHere(nearest.mat_coords[0],nearest.mat_coords[1],firstplayersturn)){
            tower.destroy();
            nearest.free=true;
        }else{
            damageMap.add(nearest.mat_coords[0],nearest.mat_coords[1],tower.get_damagedirs(),firstplayersturn);
        }

    }
}
