package hannepps.example.lazetactic;

import com.example.lazetactic.R;

public class Trash extends Tower{
    Trash(int x, int y,boolean fp){
        super(x,y,fp);
        setImg(R.drawable.trash);
    }
    @Override
    int get_class_number() {
        return 14;
    }
}

