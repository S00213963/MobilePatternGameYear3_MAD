package com.app.mobilegameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    public void GameStart(View V)
    {
        Intent i = (new Intent(MainActivity.this, Game.class));
//        i.putExtra("distance", disText);
//        i.putExtra("calories", calText);
        startActivity(i);



    }


}