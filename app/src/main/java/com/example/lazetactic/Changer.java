package com.example.lazetactic;
public class Changer extends Tower {
    Changer(int x, int y,boolean fp){
        super(x,y,fp);
        setImg(R.drawable.changer);
    }
    @Override
    int get_class_number() {
        return 8;
    }


    @Override Laze laze (Laze laze){
        Laze l=new Laze(laze.dir,false,(this.first_player?(short)1:(short)2),laze.isin);
        return l;
    }
}
