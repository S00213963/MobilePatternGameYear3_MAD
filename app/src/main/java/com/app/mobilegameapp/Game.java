package com.app.mobilegameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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

public class Game extends AppCompatActivity implements SensorEventListener {

    // experimental values for hi and lo magnitude limits
    private final double XUp = 4.0;     // upper mag limit
    private final double XUpB = 3.0;

    private final double XDown = -2.0;     // upper mag limit
    private final double XDownB = 0.0;


    private final double YRight = 4.0;
    private final double YRightB = 3.0;

    private final double YLeft = -2.0;
    private final double YLeftB = -0.0;// lower mag limit
    boolean highLimitU = false;
    boolean highLimitD = false;
    boolean highLimitL = false;
    boolean highLimitR = false;

    boolean gameStarted = false;
    int XUpCount = 0;
    int XDownCount = 0;
    int lCount = 0;
    int rCount = 0;
    RelativeLayout layout;

    //Game varibles //
    int roundCounter = 1, guessCount = 0, roundTime = 1500, CPUSets = 2, listCounter = 0;
    boolean hardMode = true, haveAddedNew = false, firstRound = true;



    TextView tvy, tvz, tvUp, tvLeft, tvRight, tvDown, gameText, tvRound;
    private SensorManager mSensorManager;
    private Sensor mSensor;

    //buttons
    private final int RED = 1, BLUE = 2, YELLOW = 3, GREEN = 4;

    Animation anim;

    StringBuilder sb;
    Button bRed, bBlue, bYellow, bGreen, play;


    int sequenceCount = 4, n = 0;
    //int[] gameSequence = new int[120];
    List<Integer> gameSequence = new ArrayList<>();
    int arrayIndex = 0;

    public void test(int num)
    {
        CountDownTimer ct = new CountDownTimer(num,  1500) {

            public void onTick(long millisUntilFinished) {
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1500);
                oneButton(0);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                //mTextField.setText("done!");
                // we now have the game sequence

                gameText.setText(sb);
                for (int i = 0; i< arrayIndex; i++)
                    Log.d("game sequence", String.valueOf(gameSequence.get(i)));
                // start next activity
                gameStarted = true;
                // put the sequence into the next activity
                // stack overglow https://stackoverflow.com/questions/3848148/sending-arrays-with-intent-putextra
                //Intent i = new Intent(A.this, B.class);
                //i.putExtra("numbers", array);
                //startActivity(i);

                // start the next activity
                // int[] arrayB = extras.getIntArray("numbers");
            }
        };

        ct.start();



    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();



        tvy = findViewById(R.id.tvY);


        bRed = findViewById(R.id.btnRed);
        bBlue = findViewById(R.id.btnBlue);
        bYellow = findViewById(R.id.btnYellow);
        bGreen = findViewById(R.id.btnGreen);
        play = findViewById(R.id.btnPlay);

        gameText = findViewById(R.id.tvGame);
        tvRound = findViewById(R.id.tvRound);


        // we are going to use the sensor service
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


    }

    private void oneButton(int num) {

        if (gameStarted == false)
        {


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
         //   Toast.makeText(this, "Number = " + n, Toast.LENGTH_SHORT).show();

            switch (n) {
                case 1:
                    flashButton(bRed);
                    gameSequence.add(RED);
                    break;
                case 2:
                    flashButton(bBlue);
                    gameSequence.add(BLUE);
                    break;
                case 3:
                    flashButton(bYellow);
                    gameSequence.add(YELLOW);
                    break;
                case 4:
                    flashButton(bGreen);
                    gameSequence.add(GREEN);
                    break;
                default:
                    break;
            }   // end switch

        }
        if(gameStarted == true && guessCount < CPUSets)
        {
            sb.delete(0, sb.length());


//            int arraySize = gameSequence.size();
//            for(int i = 0; i < arraySize; i++) {
//                tvz.append(String.valueOf(gameSequence.get(i)));
//            }

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
                        // gameSequence[arrayIndex++] = BLUE;
                        break;
                    case 2:
                        flashButton(bBlue);
                        guessCount++;
                        //  gameSequence[arrayIndex++] = RED;
                        break;
                    case 3:
                        flashButton(bYellow);
                        guessCount++;
                        //  gameSequence[arrayIndex++] = YELLOW;
                        break;
                    case 4:
                        flashButton(bGreen);
                        guessCount++;
                        //  gameSequence[arrayIndex++] = GREEN;
                        break;
                    default:
                        break;
                }

                tvRound.setText(String.valueOf(guessCount));

                if (guessCount == CPUSets)
                {
                    //nextRound///
                    gameStarted = false;
                   // gameSequence = new int[120];

                    CPUSets++;
                    guessCount = 0;
                    tvRound.setText("Game Over");
                    play.setVisibility(View.VISIBLE);
                    play.setText("Next Round");
                    listCounter = 0;

                    if(hardMode == true)
                    {
                        gameSequence.removeAll(gameSequence) ;

                    }

                }

            }



        }


    }
    public void GameOver()
    {
        Intent i = (new Intent(Game.this, ScoreBoard.class));
//             i.putExtra("distance", disText);
//               i.putExtra("calories", calText);
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

    //
    // return a number between 1 and maxValue
    private int getRandom(int maxValue) {

        return ((int) ((Math.random() * maxValue) + 1));
    }

    public void doPlay(View view) {
        sb = new StringBuilder("Result : ");
        play.setVisibility(View.GONE);
        roundTime += 1500;
        test(roundTime);
       // ct.set
    }

    public void nextRound() {
        roundTime += 1500;
        test(roundTime);

    }


    /*
     * When the app is brought to the foreground - using app on screen
     */
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
                XUpCount++;

                oneButton(1);
                highLimitU = false;
            }

            if ((x < XDown) && (highLimitD == false)) {
                highLimitD = true;
            }
            if ((x > XDownB) && (highLimitD == true)) {
                // we have a tilt to the north
                XDownCount++;

                oneButton(3);
                highLimitD = false;
            }


            if ((y < YLeft) && (highLimitL == false)) {
                highLimitL = true;
            }
            if ((y > YLeftB) && (highLimitL == true)) {
                // we have a tilt to the north
                lCount++;

                oneButton(4);
                highLimitL = false;
            }



            if ((y > YRight) && (highLimitR == false)) {
                highLimitR = true;
            }
            if ((y < YRightB) && (highLimitR == true)) {
                // we have a tilt to the north
                rCount++;

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
