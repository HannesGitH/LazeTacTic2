package hannepps.example.lazetactic;

//todo win condition and win anim

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.lazetactic.R;

import java.util.ArrayList;

import static hannepps.example.lazetactic.Constants.tower_resized;

class Playing_field {
    ArrayList<Casket> caskets=new ArrayList<>();
    ArrayList<Cask_mirror> mirrors =new ArrayList<>();
    private int width_c;
    private int height_c;

    DamageMap damageMap;

    Lazeable[][] field;

    Trace_Manager tm;

    Playing_field(short[][] field_matrix){


        this.height_c=field_matrix.length;
        this.width_c=field_matrix[0].length;
        field=new Lazeable[height_c][width_c];
        damageMap=new DamageMap(width_c,height_c);
        for (short y_i = 0; y_i < field_matrix.length; y_i++) {
            for (short x_i = 0; x_i < field_matrix[y_i].length; x_i++) {
                int h=Constants.s_height;
                if(h==0){h=60;}
                //System.out.println(h);
                switch(field_matrix[y_i][x_i]){
                    case 0:
                        //wall
                        field[y_i][x_i]=new Wall();

                        break;

                    case 1:
                        //casket
                        field[y_i][x_i]=new Casket(h*(x_i)/6,h*(y_i)/6,h/6,new int[]{x_i,y_i});
                        caskets.add((Casket) field[y_i][x_i]);

                        break;

                    default:
                        //mirror
                        //  2=◣ ; 3=◤ ; 4=◥ ; 5=◢
                        try {
                            field[y_i][x_i]=new Cask_mirror(h*(x_i)/6,h*(y_i)/6,h/6,field_matrix[y_i][x_i],new int[]{x_i,y_i});
                            mirrors.add((Cask_mirror)field[y_i][x_i]);
                        } catch (Cask_mirror.WrongPlayMatrixValueException e) {
                            e.printStackTrace();
                        }

                        break;
                }
            }
        }
        tm=new Trace_Manager();
        //nearest.resize(1); //
        //nearestF.resize(1);
    }
    void update(){
        for (Casket casket : caskets) {
            casket.update();
        }
        for (Cask_mirror mirror : mirrors) {
            mirror.update();
        }
        for (Tower tower: towers){
            tower.update_onfield();
        }
        tm.update();
    }
    void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Constants.context.getResources().getColor(R.color.playing_field_background));//rgb(90,255,90));
        canvas.drawRect(Constants.tower_size*2,0,Constants.s_width,Constants.s_height,paint);
        for (Casket casket : caskets) {
            casket.draw(canvas);
        }
        for (Cask_mirror mirror : mirrors) {
            mirror.draw(canvas);
        }
        for (Tower tower: towers){
            tower.draw_onfield(canvas);
        }
        tm.draw(canvas);
    }

    void setHeight(){
        int h=Constants.s_height;
        int ts=Constants.tower_size;
        int w=Constants.s_width;

        int heightfac=(h-ts/3)/height_c;
        int widthfac=(int)((w-2.333*ts)/width_c);

        tower_resized=Math.min(widthfac,heightfac);

        float factor=(float) tower_resized/caskets.get(0).size;
        for (Casket casket : caskets) {
            casket.resize(factor);
        }
        for (Cask_mirror mirror : mirrors) {
            mirror.resize(factor);
        }
    }

    public boolean full=false;

    Casket nearest=new Casket(0,0,1,new int[]{1,1});//null;//caskets.get(0);
    Casket nearestF;


    Casket[] getNearest(int[] coords, boolean firstplayersturn){
        nearestF=nearest;
        int dst=1000000;
        int c_dst;
        for (Casket casket: caskets){
            c_dst=(int) Math.sqrt((casket.w_center[1]-coords[1])*(casket.w_center[1]-coords[1])+(casket.w_center[0]-coords[0])*(casket.w_center[0]-coords[0]));

            full=true;
            if(c_dst<dst){
                if(casket.free)nearest=casket;
                if(!casket.free&&casket.occupied_by().first_player==firstplayersturn)nearestF=casket;
                dst=c_dst;
                full=false;
            }
        }
        return new Casket[] {nearest,nearestF};
    }


    ArrayList<Tower> towers=new ArrayList<>();

    Tower push(Tower tower, boolean firstplayersturn){
        //System.out.println(Arrays.deepToString(damageMap.map));
        if(tower.get_class_number()>=13){//eins der recycler
            Tower t = nearestF.occupied_by();
            if(t.first_player==firstplayersturn){

                try {
                    damageMap.reduce(nearestF.mat_coords[0], nearestF.mat_coords[1], t.get_damagedirs(), t.first_player);
                    if (tower.get_class_number() == 14) t.destroy();
                    else {
                        towers.remove(t);
                        t.remove();
                        tm.calculate_damage(this,towers.size()+2);
                        return t;
                    }
                    tm.calculate_damage(this,towers.size());
                }catch(Exception e){System.err.println(e);}
            }
        }else{
            nearest.set(tower);
            towers.add(tower);
            if(damageMap.willIDieHere(nearest.mat_coords[0],nearest.mat_coords[1],firstplayersturn)){
                tower.destroy();
                nearest.free=true;
                return null;
            }
            tm.calculate_damage(this,towers.size());
            if(!tower.destoyed){
                damageMap.add(nearest.mat_coords[0],nearest.mat_coords[1],tower.get_damagedirs(),firstplayersturn);
                for(Casket casket:caskets){
                    if(!casket.free&&damageMap.willIDieHere(casket.mat_coords[0],casket.mat_coords[1],casket.occupied_by().first_player)){
                        Tower t = casket.occupied_by();
                        damageMap.reduce(casket.mat_coords[0],casket.mat_coords[1],t.get_damagedirs(),t.first_player);
                        t.destroy();
                        tm.calculate_damage(this,towers.size());
                    }
                }
            }
            tm.calculate_damage(this,towers.size());
        }
        return null;
    }


    //ab hier laserzeug



}
