package com.app.mobilegameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

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
    int XUpCount = 0;
    int XDownCount = 0;
    int lCount = 0;
    int rCount = 0;

    TextView tvx, tvy, tvz, tvUp, tvLeft, tvRight, tvDown;
    private SensorManager mSensorManager;
    private Sensor mSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);



        tvx = findViewById(R.id.tvX);
        tvy = findViewById(R.id.tvY);
        tvz = findViewById(R.id.tvZ);
        tvUp = findViewById(R.id.tvSteps);
        tvDown = findViewById(R.id.down);
        tvLeft = findViewById(R.id.left);
        tvRight = findViewById(R.id.right);

        // we are going to use the sensor service
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
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
            highLimitU = false;
        }

        if ((x < XDown) && (highLimitD == false)) {
            highLimitD = true;
        }
        if ((x > XDownB) && (highLimitD == true)) {
            // we have a tilt to the north
            XDownCount++;
            tvDown.setText(String.valueOf(XDownCount));
            highLimitD = false;
        }


        if ((y < YLeft) && (highLimitL == false)) {
            highLimitL = true;
        }
        if ((y > YLeftB) && (highLimitL == true)) {
            // we have a tilt to the north
            lCount++;
            tvLeft.setText(String.valueOf(lCount));
            highLimitL = false;
        }



        if ((y > YRight) && (highLimitR == false)) {
            highLimitR = true;
        }
        if ((y < YRightB) && (highLimitR == true)) {
            // we have a tilt to the north
            rCount++;
            tvRight.setText(String.valueOf(rCount));
            highLimitR = false;
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
