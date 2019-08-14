package com.example.lazetactic;

import android.annotation.SuppressLint;
import android.content.Context;
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

        if(finished){//TODO für loadingding
            tower_container_1.setHeight(height);
            tower_container_2.setHeight(height);
            playing_field.setHeight();
        }

        okay_button=new Okay_button();
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

                if(first_players_turn) {
                    if ((int) Math.sqrt((touchloc[1] - okay_button.y) * (touchloc[1] - okay_button.y) + (touchloc[0] - okay_button.x) * (touchloc[0] - okay_button.x)) <= Constants.tower_size && okay_button.izzda) {
                        playing_field.push(tower_container_1.pop());
                        first_players_turn = !first_players_turn;
                    }
                    if (tower_container_1.touch_on(touchloc, event.getAction())) {
                        int[] tmp_center = playing_field.getNearest(touchloc).w_center;
                        tower_container_1.chosen.setLocation(tmp_center[0], tmp_center[1]);
                        okay_button.izzda = true;
                    }
                }else {
                    if ((int) Math.sqrt((touchloc[1] - okay_button.y) * (touchloc[1] - okay_button.y) + (touchloc[0] - okay_button.x) * (touchloc[0] - okay_button.x)) <= Constants.tower_size && okay_button.izzda) {
                        playing_field.push(tower_container_2.pop());
                        first_players_turn = !first_players_turn;
                    }
                    if (tower_container_2.touch_on(touchloc, event.getAction())) {
                        int[] tmp_center = playing_field.getNearest(touchloc).w_center;
                        tower_container_2.chosen.setLocation(tmp_center[0], tmp_center[1]);
                        okay_button.izzda = true;
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

        //init();//TODO für ladescreen dann weg
    }



    public void init(){

        tower_container_1= new Tower_container(new int[]{10,7,2,2,3,2,2,2,1,1,1,1,2,2},true);
        tower_container_1.setHeight(getHolder().getSurfaceFrame().height());
        tower_container_2= new Tower_container(new int[]{10,7,2,2,3,2,2,2,1,1,1,1,2,2},false);
        tower_container_2.setHeight(getHolder().getSurfaceFrame().height());


        short[][] matrix = new short[][]{
                {3, 4, 0, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 0},
                {1, 1, 0, 1, 0, 1, 1, 0, 0},
                {1, 2, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 1, 1, 1, 1, 1, 1, 5},
                {0, 0, 1, 1, 1, 1, 1, 0, 0}
        };
        playing_field=new Playing_field(matrix);

        System.out.println("Gameview created finished");
        finished=true;

        tower_container_1.setHeight(Constants.s_height);
        tower_container_2.setHeight(Constants.s_height);
        //playing_field.setHeight(); //TODO jap
    }

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
                playing_field.draw(canvas);
                if(first_players_turn){
                    tower_container_1.draw(canvas);}
                else{
                    tower_container_2.draw(canvas);}

                okay_button.draw(canvas);
            }else{
                System.out.println("loadingg");
                canvas.drawColor(Color.WHITE);
                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setTextSize(60);
                canvas.drawText((String) Constants.context.getResources().getText(R.string.loading), 400, 400, paint);
                init();
            }

        }
    }

}
