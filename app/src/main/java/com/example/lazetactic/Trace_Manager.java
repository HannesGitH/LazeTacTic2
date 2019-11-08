package com.example.lazetactic;

import android.graphics.Canvas;

import java.util.ArrayList;

public class Trace_Manager {
    public static ArrayList<Laser_trace> traces=new ArrayList<>();
    public static ArrayList<Laser_trace> ttmp=new ArrayList<>();

    public static synchronized void add(Laser_trace l){
        traces.add(l);
        System.out.println(traces.size());
    }
    public static synchronized void remove(Laser_trace l){
        ttmp = new ArrayList<>(traces);
        ttmp.remove(l);
    }
    void calculate_damage(Playing_field pf){
        for(Laser_trace t : traces){
            t.calc_dam(pf);
        }
        traces=ttmp;
    }


    void update(){
        for(Laser_trace t : traces){
            t.update();
        }
    }

    void draw(Canvas c){
        for(Laser_trace t : traces){
            t.draw(c);
        }
    }
}
