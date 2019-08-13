package com.example.lazetactic;

public class Prism extends Tower {
    Prism(int x, int y,boolean fp){
        super(x,y,fp);
        setImg(R.drawable.prism);
    }

    @Override
    int get_class_number() {
        return 6;
    }
}
