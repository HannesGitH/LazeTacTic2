package com.example.lazetactic;

import android.os.AsyncTask;

import java.io.Console;

import static com.example.lazetactic.Constants.tower_size;

public class TowerInitThread extends AsyncTask<TowerInitThreadParseable, Void, Tower[][]> {

    public AsyncResponse delegate = null;

    interface Initer{
        /*<T extends Tower>*/ Tower init(boolean fp);
    }

    private Initer[] inits=new Initer[]{
            new Initer() {
                @Override
                public Tower init(boolean fp) {
                    return new Cross(tower_size*3/4,tower_size*3/4,fp);
                }
            },
            new Initer() {
                @Override
                public Tower init(boolean fp) {
                    return new Standart_mirror(tower_size*3/4,tower_size*3/4,fp);
                }
            },
            new Initer() {
                @Override
                public Tower init(boolean fp) {
                    return new Ice_thrower(tower_size*3/4,tower_size*3/4,fp);
                }
            },
            new Initer() {
                @Override
                public Tower init(boolean fp) {
                    return new Flame_thrower(tower_size*3/4,tower_size*3/4,fp);
                }
            },
            new Initer() {
                @Override
                public Tower init(boolean fp) {
                    return new Laser(tower_size*3/4,tower_size*3/4,fp);
                }
            },
            new Initer() {
                @Override
                public Tower init(boolean fp) {
                    return new Prism(tower_size*3/4,tower_size*3/4,fp);
                }
            },
            new Initer() {
                @Override
                public Tower init(boolean fp) {
                    return new Blocker(tower_size*3/4,tower_size*3/4,fp);
                }
            },
            new Initer() {
                @Override
                public Tower init(boolean fp) {
                    return new Changer(tower_size*3/4,tower_size*3/4,fp);
                }
            },
            new Initer() {
                @Override
                public Tower init(boolean fp) {
                    return new Laser_safe_mirror(tower_size*3/4,tower_size*3/4,fp);
                }
            },
            new Initer() {
                @Override
                public Tower init(boolean fp) {
                    return new Laser_safe_prism(tower_size*3/4,tower_size*3/4,fp);
                }
            },
            new Initer() {
                @Override
                public Tower init(boolean fp) {
                    return new Shooter(tower_size*3/4,tower_size*3/4,fp);
                }
            },
            new Initer() {
                @Override
                public Tower init(boolean fp) {
                    return new Shooter_destroyer(tower_size*3/4,tower_size*3/4,fp);
                }
            }
    };

    Tower [][] all_towers;

    @Override
    protected Tower[][] doInBackground(TowerInitThreadParseable... par) {


        all_towers=new Tower[par[0].Amounts.length][0];
        for(int i=0;i<par[0].Amounts.length;i++){
            all_towers[i]=new Tower[par[0].Amounts[i]];
            for(int j=0;j<par[0].Amounts[i];j++){
                try{all_towers[i][j]=inits[i].init(par[0].first_player);}
                catch (Exception e){all_towers[i][j]=new Tower(tower_size*3/4,tower_size*3/4,par[0].first_player);}
            }
            System.out.println("sind bei towerart nmr: "+(i+1));
        }

        return all_towers;
    }

    @Override
    protected void onPostExecute(Tower[][] all_towers) {
        delegate.processFinish(all_towers);
    }
}
