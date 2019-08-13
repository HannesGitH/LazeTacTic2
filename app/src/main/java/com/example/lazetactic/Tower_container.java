package com.example.lazetactic;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import static java.lang.Math.abs;


class Tower_container {



    private int s_height,tower_size;
    private final int different_tower_amount=6;
    Tower chosen;
    private int[] amounts;
    interface Initer{
        /*<T extends Tower>*/ Tower init(boolean fp);
    }
    private Initer[] inits=new Initer[]{
            new Initer() {
                @Override
                public Tower init(boolean fp) {
                    return new Cross(tower_size*3/4,tower_size*3/4,fp);
                }
            },
            new Initer() {
                @Override
                public Tower init(boolean fp) {
                    return new Standart_mirror(tower_size*3/4,tower_size*3/4,fp);
                }
            },
            new Initer() {
                @Override
                public Tower init(boolean fp) {
                    return new Ice_thrower(tower_size*3/4,tower_size*3/4,fp);
                }
            },
            new Initer() {
                @Override
                public Tower init(boolean fp) {
                    return new Flame_thrower(tower_size*3/4,tower_size*3/4,fp);
                }
            },
            new Initer() {
                @Override
                public Tower init(boolean fp) {
                    return new Laser(tower_size*3/4,tower_size*3/4,fp);
                }
            },
            new Initer() {
                @Override
                public Tower init(boolean fp) {
                    return new Prism(tower_size*3/4,tower_size*3/4,fp);
                }
            },
            new Initer() {
                @Override
                public Tower init(boolean fp) {
                    return new Blocker(tower_size*3/4,tower_size*3/4,fp);
                }
            },
            new Initer() {
                @Override
                public Tower init(boolean fp) {
                    return new Changer(tower_size*3/4,tower_size*3/4,fp);
                }
            },
            new Initer() {
                @Override
                public Tower init(boolean fp) {
                    return new Laser_safe_mirror(tower_size*3/4,tower_size*3/4,fp);
                }
            },
            new Initer() {
                @Override
                public Tower init(boolean fp) {
                    return new Laser_safe_prism(tower_size*3/4,tower_size*3/4,fp);
                }
            },
            new Initer() {
                @Override
                public Tower init(boolean fp) {
                    return new Shooter(tower_size*3/4,tower_size*3/4,fp);
                }
            },
            new Initer() {
                @Override
                public Tower init(boolean fp) {
                    return new Shooter_destroyer(tower_size*3/4,tower_size*3/4,fp);
                }
            }
    };

    int scroll_state=0;

    Tower[][] all_towers;

    boolean first_player;

    Tower_container(int[] Amounts,boolean first_player){
        s_height=Constants.s_height;
        //System.out.println(s_height);
        this.amounts=Amounts;
        this.first_player=first_player;
        all_towers=new Tower[Amounts.length][0];

        for(int i=0;i<Amounts.length;i++){
            all_towers[i]=new Tower[Amounts[i]];
            for(int j=0;j<Amounts[i];j++){
                try{all_towers[i][j]=inits[i].init(first_player);}
                catch (Exception e){all_towers[i][j]=new Tower(tower_size*3/4,tower_size*3/4,first_player);}
            }
        }
    }

    private void reInit(){
        for(int i=0;i<all_towers.length;i++)
        {
            setloc(all_towers[i],(i*11+6)*tower_size/10);
        }
    }

    private <T extends Tower> void setloc(T[] t,int y_off){
        for (T tow: t){
            tow.setLocation(tower_size*3/4,y_off+scroll_state);
            tow.imgrot=0;
        }
    }



    void update(){
        for(int i=0;i<all_towers.length;i++)
        {
            update_arr(all_towers[i]);
        }
    }
    private <T extends Tower> void update_arr(T[] t){
        for (T tow: t){
            tow.update();
        }
    }


    void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Constants.context.getResources().getColor(R.color.playing_field_background));
        paint.setTextSize(tower_size/3);
        canvas.drawText("∞",tower_size*1.3f,tower_size*0.8f+scroll_state,paint);
        for(int i=1;i<amounts.length;i++){
            canvas.drawText(Integer.toString(amounts[i]),tower_size*1.33f,s_height/different_tower_amount*(i+0.85f)+scroll_state,paint);
        }
        for(int i=0;i<all_towers.length;i++)
            {
               draw_arr(all_towers[i],canvas);
            }
        }
    private <T extends Tower> void draw_arr(T[] t,Canvas canvas){
        for (T tow: t){
            tow.draw(canvas);
        }
    }

    void setHeight(int height){
        s_height=height;
        this.tower_size= (int)(s_height/different_tower_amount*0.9);
        Constants.tower_size=tower_size;
        reInit();
    }

    Tower pop(){
        try{amounts[chosen.get_class_number()-1]--;}catch (Exception e){return new Tower(chosen.x,chosen.y,first_player);}
        int class_num=chosen.get_class_number()-1;
        if (class_num!=0){
        all_towers[class_num]=pop_from(all_towers[class_num]);}else{
            all_towers[0]=new Cross[]{(Cross) inits[0].init(first_player),(Cross) inits[0].init(first_player)};
        }

        Tower tmp = chosen;
        chosen=null;
        return tmp;
    }

    private <T extends Tower> T[] pop_from(T[] ts){
        //T[] ts_tmp=(T[])new Object[ts.length-1];
        Tower[] ts_tmp=new Tower[ts.length-1];
        for (int i=0;i<ts.length-1;i++){
            ts_tmp[i]=ts[i];
        }
        ts= (T[]) ts_tmp;
        return ts;
    }



    private int[] start;
    private int[] drag_start;
    private int[] lsty;
    boolean touch_on(int[] loc, int action){

        if(drag_start!=null&&lsty!=null&&loc[0] < tower_size * 2&&abs(loc[0]-drag_start[0])<=abs(loc[1]-drag_start[1])){
            int save=scroll_state;
            scroll_state+=loc[1]-lsty[1];
            scroll_state=((scroll_state>=tower_size/2||-scroll_state>=amounts.length*s_height/different_tower_amount-s_height)?save:scroll_state);

            reInit();
        }
        lsty=loc;

        if (action==MotionEvent.ACTION_UP&&chosen!=null&&chosen.is_in(start)){//(touchloc)&&(event.getHistorySize()==0||tower_container.chosen.is_in(new int[]{(int)event.getHistoricalX(0),(int)event.getHistoricalY(0)}))){
            chosen.rotate();
        }if (action==MotionEvent.ACTION_DOWN){
            drag_start=loc;
            if(chosen!=null)start=new int[]{chosen.x,chosen.y};
        }

        if (action!= MotionEvent.ACTION_UP) {
            if (loc[0] < tower_size * 1.2) {
                if (action==MotionEvent.ACTION_DOWN) {
                    if(chosen!=null)reInit(); ///Innefficient
                    choose((loc[1]-scroll_state) * different_tower_amount / s_height);
                }else{

                }

            }
            if (chosen != null) {
                chosen.setLocation(loc[0], loc[1]);
            }
        }else{
            drag_start=null;lsty=null;
            if (chosen != null&&chosen.getLocation()[0]>=tower_size*2) {
                return true;
            }
        }
        return false;
    }
    private void choose(int classnum){
        try{
        int len=all_towers[classnum].length;
        if ( len > 0) {
            chosen = all_towers[classnum][len-1];
        }
        }catch (Exception e){
            chosen=null;
        }
    }

}

