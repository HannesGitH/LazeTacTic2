package com.example.lazetactic;


public class Cross extends Tower {
    Cross(int x, int y,boolean f) {
        super(x, y,f);

        this.setImg(R.drawable.cross);
    }
    @Override
    int get_class_number(){
        return 1;
    }
    @Override
    void rotate(){

    }


}
