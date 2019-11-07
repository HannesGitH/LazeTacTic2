package com.example.lazetactic;

import android.graphics.Canvas;

import java.util.ArrayList;

public class Trace_Manager implements AsyncRespondTraceInterface {
    public static ArrayList<Laser_trace> traces=new ArrayList<>();

    public static synchronized void add(Laser_trace l){
        traces.add(l);
        System.out.println(traces.size());
    }
    public static synchronized void remove(Laser_trace l){
        traces.remove(l);
    }
    void calculate_damage(){

    }

    @Override
    public void processFinish(){

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
