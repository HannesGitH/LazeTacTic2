package com.example.lazetactic;

import java.util.ArrayList;

public class Trace_Manager {
    public static ArrayList<Laser_trace> traces=new ArrayList<>();

    public static synchronized void add(Laser_trace l){
        traces.add(l);
    }
    public static synchronized void remove(Laser_trace l){
        traces.remove(l);
    }
}
