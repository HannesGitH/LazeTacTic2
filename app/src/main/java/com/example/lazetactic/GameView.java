package com.example.lazetactic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import static com.example.lazetactic.MainThread.canvas;


class GameView extends SurfaceView implements SurfaceHolder.Callback {


    private Boolean first_players_turn=true;

    private MainThread thread;
    private Tower_container tower_container_1;
    private Tower_container tower_container_2;
    public Playing_field playing_field;
    private Okay_button okay_button;
    public int[] touchloc;

    boolean finished=false;


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        Constants.s_height=height;
        Constants.s_width=width;

        System.out.println(Constants.s_height+"surfaceChanged"+height);

        if(finished){

            System.out.println("seems like finished");
            tower_container_1.setHeight(height);
            tower_container_2.setHeight(height);
            playing_field.setHeight();

            okay_button=new Okay_button(); //war hinter der curly bracket
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(holder,this);
        thread.setRunning(true);
        //System.out.println(thread.getState());
        if(thread.isAlive()){thread.run();}else{thread.start();}

        //System.out.println("surfaceCreated");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
        //System.out.println("surfaceDestroyed");
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (finished) {
            synchronized (getHolder()) {

                touchloc = new int[]{(int) event.getX(), (int) event.getY()};


                if (first_players_turn) {
                    if (event.getAction()==MotionEvent.ACTION_DOWN&&(int) Math.sqrt((touchloc[1] - okay_button.y) * (touchloc[1] - okay_button.y) + (touchloc[0] - okay_button.x) * (touchloc[0] - okay_button.x)) <= Constants.tower_size && okay_button.izzda) {
                        okay_button.izzda=false;
                        Tower t=playing_field.push(tower_container_1.pop(), true);
                        if(t!=null)tower_container_1.push(t);
                        first_players_turn = !first_players_turn;
                    }
                    if (tower_container_1.touch_on(touchloc, event.getAction())) {
                        int i=tower_container_1.chosen.get_class_number()>=13?1:0;
                        int[] tmp_center = playing_field.getNearest(touchloc,true)[i].w_center;
                        tower_container_1.chosen.setLocation(tmp_center[0], tmp_center[1]);
                        if (tower_container_1.chosen != null) okay_button.izzda = true;
                    }
                } else {
                    if (event.getAction()==MotionEvent.ACTION_DOWN&&(int) Math.sqrt((touchloc[1] - okay_button.y) * (touchloc[1] - okay_button.y) + (touchloc[0] - okay_button.x) * (touchloc[0] - okay_button.x)) <= Constants.tower_size && okay_button.izzda) {
                        okay_button.izzda=false;
                        Tower t=playing_field.push(tower_container_2.pop(), false);
                        if(t!=null)tower_container_2.push(t);
                        first_players_turn = !first_players_turn;
                    }
                    if (tower_container_2.touch_on(touchloc, event.getAction())) {
                        int i=tower_container_2.chosen.get_class_number()>=13?1:0;
                        int[] tmp_center = playing_field.getNearest(touchloc,false)[i].w_center;
                        tower_container_2.chosen.setLocation(tmp_center[0], tmp_center[1]);
                        if (tower_container_2.chosen != null) okay_button.izzda = true;
                    }
                }

            }
        }
        return true;
    }

    public GameView(Context context) {

        super(context);
        System.out.println("Gameview created");

        Constants.context=context;

        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);

    }


    boolean init_begin=false;
    public void init(){

        init_begin=true;

        tower_container_1= new Tower_container(new int[]{10,7,2,2,3,2,2,2,1,1,1,2,2,2},true);
        tower_container_1.setHeight(getHolder().getSurfaceFrame().height());
        System.out.println("fst tc finished");

        tower_container_2= new Tower_container(new int[]{10,7,2,2,3,2,2,2,1,1,1,2,2,2},false);
        tower_container_2.setHeight(getHolder().getSurfaceFrame().height());
        System.out.println("snd tc finished");

        short[][] matrix = new short[][]{
                // 0:= wall;  1:=casket; mirrors(. is wall): 2=.\ ◣ ; 3=°/ ◤ ; 4=\° ◥ ; 5=/. ◢
                {3, 4, 0, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 0},
                {1, 1, 0, 1, 0, 1, 1, 0, 0},
                {1, 2, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 1, 1, 1, 1, 1, 1, 5},
                {0, 0, 1, 1, 1, 1, 1, 0, 0}
        };
        System.out.println("start pf");
        playing_field=new Playing_field(matrix);
        System.out.println("end pf");

        okay_button=new Okay_button();

        System.out.println("Gameview created finished");
        finished=true;

        tower_container_1.setHeight(Constants.s_height);
        tower_container_2.setHeight(Constants.s_height);
        playing_field.setHeight();

        //Bitmap bmtmp = Bitmap.createBitmap(Constants.s_width,Constants.s_height, Bitmap.Config.ARGB_8888);
        //cv=new Canvas(bmtmp);
    }
    Canvas cv;

    public void update() {
        if (finished){
            if(first_players_turn){
                tower_container_1.update();
                playing_field.update();
                if(tower_container_1.chosen!=null&&tower_container_1.chosen.x>=Constants.tower_size*2){
                    //okay_button.izzda=true;

                }else{
                    okay_button.izzda=false;
                }
            }else{
                tower_container_2.update();
                playing_field.update();
                if(tower_container_2.chosen!=null&&tower_container_2.chosen.x>=Constants.tower_size*2){
                    //okay_button.izzda=true;

                }else{
                    okay_button.izzda=false;
                }
            }

        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            if(finished){
                canvas.drawColor(Color.WHITE);
                cv=canvas;
                playing_field.draw(canvas);

                okay_button.draw(canvas);
                if(first_players_turn){
                    tower_container_1.draw(cv);//todo evtl cv?
                }else{
                    tower_container_2.draw(cv);//
                }
            }else{

                canvas.drawColor(Color.WHITE);
                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setTextSize(60);
                canvas.drawText((String) Constants.context.getResources().getText(R.string.loading), 400, 400, paint);
                if(!init_begin) {
                    System.out.println("initten");
                    init();
                    System.out.println(finished);
                }
            }

        }
    }

}
