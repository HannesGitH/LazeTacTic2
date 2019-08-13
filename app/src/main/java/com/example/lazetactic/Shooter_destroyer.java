package com.example.lazetactic;

public class Shooter_destroyer extends Tower {
    Shooter_destroyer(int x, int y,boolean fp){
        super(x,y,fp);
        setImg(R.drawable.shooter_destroyer);
    }
    @Override
    int get_class_number() {
        return 12;
    }
}
