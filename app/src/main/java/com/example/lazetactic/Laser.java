package com.example.lazetactic;

public class Laser extends Tower {
    Laser(int x, int y,boolean fp){
        super(x,y,fp);
        setImg(R.drawable.laser);
    }
    @Override
    int get_class_number() {
        return 5;
    }


    Laser_trace lt;
    @Override
    void setbelongCasket(Casket c){
        belongsto=c;
        lt=new Laser_trace(this);
    }
    @Override
    void update_onfield(){
        //lt.update();
    }

    void remove(){
        lt.remove();
    }




}
