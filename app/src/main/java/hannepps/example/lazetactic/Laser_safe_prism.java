package hannepps.example.lazetactic;

import com.example.lazetactic.R;

public class Laser_safe_prism extends Tower {
    Laser_trace lt;
    Laser_safe_prism(int x, int y,boolean fp){
        super(x,y,fp);
        setImg(R.drawable.laser_safe_prism);
    }
    @Override
    int get_class_number() {
        return 10;
    }

    short[][] mirmat=new short[][]{
            {5,5,1,0},
            {1,5,5,2},
            {3,2,5,5},
            {5,0,3,5},
    };
    @Override Laze laze (Laze laze){

        short ndir=mirmat[this.imgrot][laze.dir];
        short color=(short)(first_player?1:2);
        if(laze.color==(first_player?2:1))color=0;
        lt=new Laser_trace(this,color);
        Laze l=new Laze(ndir,(ndir==5),color,laze.isin);
        return l;
    }

    @Override
    void remove() {
        if(lt!=null) lt.remove();//todo
        super.remove();
    }
    @Override
    void removelt(){
        if(lt!=null) lt.remove();//todo
    }
}

