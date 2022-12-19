package com.app.mobilegameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity  implements SensorEventListener,
        AdapterView.OnItemSelectedListener  {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private long lastUpdate;

    AnimatedView animatedView = null;
    ShapeDrawable mDrawable = new ShapeDrawable();
    public static int x;
    public static int y;
    Spinner spinner;
    String selectedMode, sel;
    TextView detail;
    EditText etName;
    Integer modeNum;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lastUpdate = System.currentTimeMillis();

        spinner = findViewById(R.id.spinner);
        detail = findViewById(R.id.tvDetail);
        etName = findViewById(R.id.etName);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.spinnerItems, R.layout.spinner);
       adapter.setDropDownViewResource(R.layout.spinner_background);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        sel = adapterView.getItemAtPosition(i).toString();
       modeNum = i;
        detail.setText(sel);
        if(i == 0)
        {
            detail.setText("One color is added to the sequence every round");
            selectedMode = "Easy";

        }
        else if(i == 1)
        {
            detail.setText("The sequence changes every round, increasing the length by 1 extra color");
            selectedMode = "Hard";
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void GameStart(View V)
    {   String n = etName.getText().toString();

        if(n.matches("Name"))
        {
            Toast.makeText(this, "Please Enter You Name",
                    Toast.LENGTH_LONG).show();
        }
        else
        {
            mp = MediaPlayer.create(this, R.raw.start);
            mp.start();

            Intent i = (new Intent(MainActivity.this, Game.class));
            i.putExtra("name", n);
            i.putExtra("mode", sel );
            i.putExtra("modeNum", modeNum.toString());
            startActivity(i);

            // code to call ball moving screen, first test game, not in use anymore.
//        animatedView = new AnimatedView(this);
//        setContentView(animatedView);

        }



    }



        @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer,
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.activity_main, menu);
//        return true;
//    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            x -=  (int) event.values[0] * 3;
            y += (int) event.values[1] * 3;

        }
    }



    public class AnimatedView extends androidx.appcompat.widget.AppCompatImageView {

        static final int width = 150;
        static final int height = 150;

        public AnimatedView(Context context) {
            super(context);
            // TODO Auto-generated constructor stub

            mDrawable = new ShapeDrawable(new OvalShape());
            mDrawable.getPaint().setColor(0xffffAC23);
            mDrawable.setBounds(x, y, x + width, y + height);

        }

        @Override
        protected void onDraw(Canvas canvas) {

            mDrawable.setBounds(x, y, x + width, y + height);
            mDrawable.draw(canvas);
            invalidate();
        }
    }



}
































