package com.example.lazetactic;

public class DamageMap {
    short[][] map;
    DamageMap(int width, int height){
        map = new short[height][width];
    }
    void seta(int x,int y, short damnum){

        ////DamNum:
        //   0:= no damage ,
        //   1:= player1 owns so player2 gets damage ,
        //   2:= player2 owns so player1 gets damage ,
        //   3:= both get Damage ,
        //   4:= not on field , but noone gets damage like 0

        map[y][x]=damnum;
    }

    boolean willIDieHere(int x, int y, boolean firstplayer){

        switch (map[y][x]){

            case 0:
                return false;
            case 1:
                return !firstplayer;
            case 2:
                return firstplayer;
            case 3:
                return true;

            default:
                return true;
        }
    }

    void add(int x, int y, short[] damageDirs, boolean firstplayer){
        for(short dir:damageDirs){
            int[]curCasket= new int[]{x,y};
            while(dir!=0){
                switch (dir%10){
                    case 1:
                        curCasket[0]++;
                        break;
                    case 2:
                        curCasket[1]++;
                        break;
                    case 3:
                        curCasket[0]--;
                        break;
                    case 4:
                        curCasket[1]--;
                        break;
                }
                dir/=10;
            }
            short additive=firstplayer?(short)1:(short)2;
            try {
                map[curCasket[1]][curCasket[0]] = (map[curCasket[1]][curCasket[0]] == additive)?additive: (short)(map[curCasket[1]][curCasket[0]]+additive% 4);
            }catch(Exception e){
                //System.out.println("tried to go out of the field");
            }
        }
    }
}
