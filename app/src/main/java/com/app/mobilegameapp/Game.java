package com.app.mobilegameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends AppCompatActivity implements SensorEventListener {

    // experimental values for hi and lo magnitude limits
    private final double XUp = 8.0;     // upper mag limit
    private final double XUpB = 6.0;

    private final double XDown = -4.0;     // upper mag limit
    private final double XDownB = -2.0;


    private final double YRight = 8.0;
    private final double YRightB = 6.0;

    private final double YLeft = -4.0;
    private final double YLeftB = -2.0;// lower mag limit
    boolean highLimitU = false;
    boolean highLimitD = false;
    boolean highLimitL = false;
    boolean highLimitR = false;

    boolean gameStarted = false;



    //Game varibles //
    int rCounter = 0, guessCount = 0, roundTime = 1500, CPUSets = 2, listCounter = 0;
    boolean hardMode = true, haveAddedNew = false, firstRound = true;



    TextView tvy, gameText;
    private SensorManager mSensorManager;
    private Sensor mSensor;

    //buttons
    private final int RED = 1, BLUE = 2, PURPLE = 3, GREEN = 4;

    Animation anim;

    StringBuilder sb;
    Button bRed, bBlue, bYellow, bGreen, play;


    int sequenceCount = 4, n = 0;
    //int[] gameSequence = new int[120];
    List<Integer> gameSequence = new ArrayList<>();
    int arrayIndex = 0;

    String name, mode, modeNum;
    TextView tvMode, tvRoundMess, tvRound, tvPoints;
    int points = 0;
    MediaPlayer mp;

    CountUpTimer timer;

    boolean first = true, timeBonus = false;
    int secondCount = 0;



    public void start(int num) // added time so it picks extra color every round
    {
        CountDownTimer ct = new CountDownTimer(num,  1500) {

            public void onTick(long millisUntilFinished) {

                oneButton(0);

            }

            public void onFinish() {


                gameText.setText(sb);
                for (int i = 0; i< arrayIndex; i++)
                    Log.d("game sequence", String.valueOf(gameSequence.get(i)));

                gameStarted = true;

            }
        };

        ct.start();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();

        Bundle extras = getIntent().getExtras();
        if(extras != null) // getting values from intent
        {
            name = extras.getString("name");
            mode = extras.getString("mode");
            modeNum = extras.getString("modeNum");
        }

        if(Integer.parseInt(modeNum) == 0)
        {
            hardMode = false;
        }
        else
        {
            hardMode = true;
        }


        tvy = findViewById(R.id.tvY);
        bRed = findViewById(R.id.btnRed);
        bBlue = findViewById(R.id.btnBlue);
        bYellow = findViewById(R.id.btnYellow);
        bGreen = findViewById(R.id.btnGreen);
        play = findViewById(R.id.btnPlay);

        gameText = findViewById(R.id.tvGame);
        tvRound = findViewById(R.id.TvRound);
        tvRoundMess = findViewById(R.id.tvRound);
        tvPoints = findViewById(R.id.tvPoints);
        tvMode = findViewById(R.id.tvMode);

        tvMode.setText(mode);
        tvRound.setText(String.valueOf(rCounter));
        tvPoints.setText(String.valueOf(points));



        // we are going to use the sensor service
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        timer = new CountUpTimer(300000) {  // should be high for the run (ms)
            public void onTick(int second) {
                secondCount = second;
                tvRoundMess.setText("Time: " + String.valueOf(second));
            }
        };


    }

    private void oneButton(int num) {

        if (gameStarted == false)
        {
            tvRoundMess.setText("");

            if (hardMode == true)
            {
                n = getRandom(sequenceCount);
                sb.append(String.valueOf(n) + ", ");
            }

            else
            {
                if (firstRound)
                {
                    for (int i = 0; i < 2; i++)
                    {
                        gameSequence.add(getRandom(sequenceCount));
                        sb.append(String.valueOf(n) + ", ");

                    }
                }

                else
                {
                    if(haveAddedNew == false)
                    {
                        gameSequence.add(getRandom(sequenceCount));
                        haveAddedNew = true;
                        sb.append(String.valueOf(n) + ", ");
                    }

                }


                n = gameSequence.get(listCounter);
                listCounter++;
            }


            switch (n) {
                case 1:
                    flashButton(bRed);
                    gameSequence.add(RED);
                    mp = MediaPlayer.create(this, R.raw.purple);
                    mp.start();

                    break;
                case 2:
                    flashButton(bBlue);
                    gameSequence.add(BLUE);
                    mp = MediaPlayer.create(this, R.raw.blue);
                    mp.start();
                    break;
                case 3:
                    flashButton(bYellow);
                    gameSequence.add(PURPLE);
                    mp = MediaPlayer.create(this, R.raw.purple);
                    mp.start();
                    break;
                case 4:
                    flashButton(bGreen);
                    gameSequence.add(GREEN);
                    mp = MediaPlayer.create(this, R.raw.green);
                    mp.start();
                    break;
                default:
                    break;
            }   // end switch

        }
        if(gameStarted == true && guessCount < CPUSets)
        {
            timeBonus = false;
            if(first)
            {
                timer.start();
                first = false;
            }
            sb.delete(0, sb.length());
            if(num != gameSequence.get(guessCount))
            {
                GameOver();

            }

            else
            {
                switch (num) {
                    case 1:
                        flashButton(bRed);
                        guessCount++;
                        mp = MediaPlayer.create(this, R.raw.purple);
                        mp.start();
                        break;
                    case 2:
                        flashButton(bBlue);
                        guessCount++;
                        mp = MediaPlayer.create(this, R.raw.blue);
                        mp.start();
                        break;
                    case 3:
                        flashButton(bYellow);
                        guessCount++;
                        mp = MediaPlayer.create(this, R.raw.purple);
                        mp.start();
                        break;
                    case 4:
                        flashButton(bGreen);
                        guessCount++;
                        mp = MediaPlayer.create(this, R.raw.green);
                        mp.start();
                        break;
                    default:
                        break;
                }



                if (guessCount == CPUSets)
                {
                    timer.cancel();
                    gameStarted = false;

                    CPUSets++; // number of colors CPU can pick increases by 1
                    guessCount = 0;
                    tvRoundMess.setText("Round Complete!");
                    play.setVisibility(View.VISIBLE);
                    play.setText("Next Round");
                    points+= CalPoints();
                    first = true; // starts timer for next round
                    tvPoints.setText(String.valueOf(points));

                    int delay = 1000; // delay for 5 sec.
                    int period = 1000; // repeat every sec.
                    int index = 0;

                    if(timeBonus == true)
                    {
                        tvRoundMess.postDelayed(new Runnable() {
                            public void run() {
                                tvRoundMess.setText("Time Bonus added!!");}
                        }, 1000);

                    }


                    listCounter = 0; //resets list count

                    if(hardMode == true)
                    {
                        gameSequence.removeAll(gameSequence) ; //if in hardmode, clear the list

                    }

                }

            }



        }
    }

    public int CalPoints()
    {


        int points = 50;

        if(secondCount <= CPUSets)
        {
            points += 25;
            timeBonus = true;// if level is complete in less seconds the CPU selected colors,
                          // player gets a points bonus

        }

        if(rCounter <= 2)
        {
            points += + 5* rCounter;

        }
        else if(rCounter > 2 && rCounter <=5)
        {
            points += 25 + 10* rCounter;

        }

        else if(rCounter > 5)
        {
            points += 50 + 20* rCounter;

        }


        return points;
    }
    public void GameOver()
    {
        mp = MediaPlayer.create(this, R.raw.gameover);
        mp.start();
        Intent i = (new Intent(Game.this, scoreDB.class));
        startActivity(i);

    }

    private void flashButton(Button button) {

        anim = new AlphaAnimation(1,0);
        anim.setDuration(1000);

         if (gameStarted)
         {
             anim.setDuration(300);

         }
         else
         {
             anim.setDuration(1000);
         }

        anim.setRepeatCount(0);
        button.startAnimation(anim);

    }

    private int getRandom(int maxValue) {

        return ((int) ((Math.random() * maxValue) + 1));
    }

    public void doPlay(View view) {
        sb = new StringBuilder("Result : ");

        play.setVisibility(View.GONE);
        roundTime += 1500;
        rCounter++;
        tvRound.setText(String.valueOf(rCounter));
        start(roundTime);

    }

    protected void onResume() {
        super.onResume();
        // turn on the sensor
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    /*
     * App running but not on screen - in the background
     */
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);    // turn off listener to save power
    }





    @Override
    public void onSensorChanged(SensorEvent event) {

        if(gameStarted == true)
        {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];




            if ((x > XUp) && (highLimitU == false)) {
                highLimitU = true;
            }
            if ((x < XUpB) && (highLimitU == true)) {
                // we have a tilt to the north


                oneButton(1);
                highLimitU = false;
            }

            if ((x < XDown) && (highLimitD == false)) {
                highLimitD = true;
            }
            if ((x > XDownB) && (highLimitD == true)) {
                // we have a tilt to the north


                oneButton(3);
                highLimitD = false;
            }


            if ((y < YLeft) && (highLimitL == false)) {
                highLimitL = true;
            }
            if ((y > YLeftB) && (highLimitL == true)) {
                // we have a tilt to the north


                oneButton(4);
                highLimitL = false;
            }



            if ((y > YRight) && (highLimitR == false)) {
                highLimitR = true;
            }
            if ((y < YRightB) && (highLimitR == true)) {
                // we have a tilt to the north
              ;

                oneButton(2);
                highLimitR = false;
            }

        }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not used
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
