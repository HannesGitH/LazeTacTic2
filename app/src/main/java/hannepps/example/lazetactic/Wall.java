package hannepps.example.lazetactic;

public class Wall implements Lazeable {
    Wall(){
    }
    public Laze laze(Laze l){
        return new Laze(l.dir,true,l.color,l.isin);
    }

    @Override
    public int[] getw_center() {
        return new int[]{0,0};
    }
    @Override
    public int[] getmat_coords() {
        return new int[]{0,0};
    }
    @Override
    public int getsize(){
        return 0;
    }
}
