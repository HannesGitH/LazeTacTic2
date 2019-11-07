package com.example.lazetactic;

import java.util.ArrayList;


public class Laser_trace {
    Laser origin;
    ArrayList<Laze> lazes=new ArrayList<>();
    Trace_Manager tm=new Trace_Manager();
    Laser_trace(Laser laser){
        origin=laser;
        lazes.add(new Laze((short)(origin.imgrot),false,origin.first_player?(short)1:(short)2,origin.belongsto));
        tm.add(this);
    }
    void update(){

    }
    void remove(){ tm.remove(this);
    }
}
