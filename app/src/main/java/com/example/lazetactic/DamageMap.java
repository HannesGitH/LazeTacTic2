package com.example.lazetactic;

public class DamageMap {
    short[][][] map;
    DamageMap(int width, int height){
        map = new short[height][width][2]; //2 ist playeranzahl theoretisch später erweiterbar
    }


    boolean willIDieHere(int x, int y, boolean firstplayer){
        if(firstplayer){
            return map[y][x][1]>0;
        }else{
            return map[y][x][0]>0;
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
            try {
                if(firstplayer) {
                    map[curCasket[1]][curCasket[0]][0]++;
                }else{
                    map[curCasket[1]][curCasket[0]][1]++;
                }
            }catch(Exception e){
                //System.out.println("tried to go out of the field");
            }
        }
    }
    void reduce(int x, int y, short[] damageDirs, boolean firstplayer){
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
            try {
                if(firstplayer) {
                    map[curCasket[1]][curCasket[0]][0]--;
                }else{
                    map[curCasket[1]][curCasket[0]][1]--;
                }
            }catch(Exception e){
                //System.out.println("tried to go out of the field");
            }
        }
    }
}
