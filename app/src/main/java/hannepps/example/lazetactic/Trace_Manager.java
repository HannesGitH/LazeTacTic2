package hannepps.example.lazetactic;

import android.graphics.Canvas;

import java.util.ArrayList;

public class Trace_Manager {
    public static ArrayList<Laser_trace> traces=new ArrayList<>();
    public static ArrayList<Laser_trace> ttmp=new ArrayList<>();

    public static synchronized void add(Laser_trace l){
        ttmp = new ArrayList<>(traces);
        ttmp.add(l);
        try{traces=ttmp;}catch(Exception e){ttmp=traces;}
    }
    public static synchronized void remove(Laser_trace l){
        ttmp = new ArrayList<>(traces);
        ttmp.remove(l);
    }
    void calculate_damage(Playing_field pf,int amount){
        for(int i=amount;i>0;i--){
            for(Laser_trace t : traces){
                for(Tower to:pf.towers){
                    if(to.get_class_number()==6||to.get_class_number()==10){to.removelt();System.out.println("removelt");}//todo
                }
                t.calc_dam(pf);
            }
            traces=ttmp;
        }

    }


    void update(){
        for(Laser_trace t : traces){
            t.update();
        }
    }

    void draw(Canvas c){
        traces=ttmp;
        //System.out.println(traces.size());
        for(Laser_trace t : traces){
            t.draw(c);
        }
    }
}
