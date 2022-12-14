package com.app.mobilegameapp;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;
import android.widget.Toast;

public class Game extends AppCompatActivity implements SensorEventListener {

    // experimental values for hi and lo magnitude limits
    private final double XUp = 6.0;     // upper mag limit
    private final double XUpB = 4.0;

    private final double XDown = -3.0;     // upper mag limit
    private final double XDownB = 1.0;


    private final double YRight = 6.0;
    private final double YRightB = 4.0;

    private final double YLeft = -6.0;
    private final double YLeftB = -4.0;// lower mag limit
    boolean highLimitU = false;
    boolean highLimitD = false;
    boolean highLimitL = false;
    boolean highLimitR = false;

    boolean gameStarted = false;
    int XUpCount = 0;
    int XDownCount = 0;
    int lCount = 0;
    int rCount = 0;

    //Game varibles //
    int roundCounter = 1;
    int guessCount = 0;
    int roundTime = 6000;


    TextView tvx, tvy, tvz, tvUp, tvLeft, tvRight, tvDown, gameText, tvRound;
    private SensorManager mSensorManager;
    private Sensor mSensor;

    //buttons
    private final int RED = 1;
    private final int BLUE = 2;

    private final int YELLOW = 3;
    private final int GREEN = 4;

    Animation anim;

    StringBuilder sb;
    Button bRed, bBlue, bYellow, bGreen;
    TextView tv;

    int sequenceCount = 4, n = 0;
    int[] gameSequence = new int[120];
    int arrayIndex = 0;

    public void test(int num1, int num2)
    {
        CountDownTimer ct = new CountDownTimer(num1,  num2) {

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
                    Log.d("game sequence", String.valueOf(gameSequence[i]));
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


        tvx = findViewById(R.id.tvX);
        tvy = findViewById(R.id.tvY);
        tvz = findViewById(R.id.tvZ);
        tvUp = findViewById(R.id.tvSteps);
        tvDown = findViewById(R.id.down);
        tvLeft = findViewById(R.id.left);
        tvRight = findViewById(R.id.right);

        bRed = findViewById(R.id.btnRed);
        bBlue = findViewById(R.id.btnBlue);
        bYellow = findViewById(R.id.btnYellow);
        bGreen = findViewById(R.id.btnGreen);

        gameText = findViewById(R.id.tvGame);
        tvRound = findViewById(R.id.tvRound);



        // we are going to use the sensor service
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


    }

    private void oneButton(int num) {

        if (gameStarted == false)
        {
            n = getRandom(sequenceCount);
            sb.append(String.valueOf(n) + ", ");
         //   Toast.makeText(this, "Number = " + n, Toast.LENGTH_SHORT).show();

            switch (n) {
                case 1:
                    flashButton(bRed);
                    gameSequence[arrayIndex++] = BLUE;
                    break;
                case 2:
                    flashButton(bBlue);
                    gameSequence[arrayIndex++] = RED;
                    break;
                case 3:
                    flashButton(bYellow);
                    gameSequence[arrayIndex++] = YELLOW;
                    break;
                case 4:
                    flashButton(bGreen);
                    gameSequence[arrayIndex++] = GREEN;
                    break;
                default:
                    break;
            }   // end switch

        }
        if(gameStarted == true && guessCount <= sequenceCount)
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
        }
        else
           {
               tvRound.setText("Game Over");
              // nextRound();


           }

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
     //   ct.start();
        test(6000, 1500);
       // ct.set
    }

    public void nextRound() {
        roundTime += 1500;
        test(roundTime, 1500);

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

            tvx.setText(String.valueOf(x));
            tvy.setText(String.valueOf(y));
            tvz.setText(String.valueOf(z));

            if ((x > XUp) && (highLimitU == false)) {
                highLimitU = true;
            }
            if ((x < XUpB) && (highLimitU == true)) {
                // we have a tilt to the north
                XUpCount++;
                tvUp.setText(String.valueOf(XUpCount));
                oneButton(1);
                highLimitU = false;
            }

            if ((x < XDown) && (highLimitD == false)) {
                highLimitD = true;
            }
            if ((x > XDownB) && (highLimitD == true)) {
                // we have a tilt to the north
                XDownCount++;
                tvDown.setText(String.valueOf(XDownCount));
                oneButton(3);
                highLimitD = false;
            }


            if ((y < YLeft) && (highLimitL == false)) {
                highLimitL = true;
            }
            if ((y > YLeftB) && (highLimitL == true)) {
                // we have a tilt to the north
                lCount++;
                tvLeft.setText(String.valueOf(lCount));
                oneButton(4);
                highLimitL = false;
            }



            if ((y > YRight) && (highLimitR == false)) {
                highLimitR = true;
            }
            if ((y < YRightB) && (highLimitR == true)) {
                // we have a tilt to the north
                rCount++;
                tvRight.setText(String.valueOf(rCount));
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
