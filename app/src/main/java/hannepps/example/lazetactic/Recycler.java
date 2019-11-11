package hannepps.example.lazetactic;

import com.example.lazetactic.R;

public class Recycler extends Tower {
    Recycler(int x, int y,boolean fp){
        super(x,y,fp);
        setImg(R.drawable.recycle);
    }
    @Override
    int get_class_number() {
        return 13;
    }
}
