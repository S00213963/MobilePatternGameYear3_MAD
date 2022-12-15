package com.app.mobilegameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Scores extends AppCompatActivity {
TextView tv;
    StringBuilder sb;
    ListView listView;
    String countryList[] = {"India", "China", "australia", "Portugle", "America", "NewZealand"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        getSupportActionBar().hide();

        listView = findViewById(R.id.listView);



        DatabaseHandler db = new DatabaseHandler(this);
        db.emptyHiScores();     // empty table if required
      //  tv = findViewById(R.id.tv);

        // Inserting hi scores
        Log.i("Insert: ", "Inserting ..");
        db.addHiScore(new HIScore("Hard", "20 OCT 2020",  "Frodo", 12));
        db.addHiScore(new HIScore("Hard","28 OCT 2020",  "Dobby", 16));
        db.addHiScore(new HIScore("Hard","20 NOV 2020", "DarthV", 20));
        db.addHiScore(new HIScore("Hard","20 NOV 2020", "Bob", 18));
        db.addHiScore(new HIScore("Easy" ,"22 NOV 2020" ,"Gemma", 22));
        db.addHiScore(new HIScore("Easy","30 NOV 2020", "Joe", 30));
        db.addHiScore(new HIScore("Easy","01 DEC 2020", "DarthV", 22));
        db.addHiScore(new HIScore("Easy","02 DEC 2020", "Gandalf", 132));


        List<HIScore> hiScores = db.getAllHiScores();


        listView = findViewById(R.id.listView);
        ArrayAdapter<HIScore> arrayAdapter = new ArrayAdapter<HIScore>(this,
                android.R.layout.simple_list_item_1,
                hiScores);
        listView.setAdapter(arrayAdapter);


        // Reading all scores
        Log.i("Reading: ", "Reading all scores..");

        List<String> scoresStr;
        scoresStr = new ArrayList<>();

        int j = 0;
        for (HIScore hs : hiScores) {
            String log =
                    "Id: " + hs.getScore_id() +
                            ", mode!!!!!: " + hs.getMode() +
                            ", Date: " + hs.getGame_date() +

                            " , Player: " + hs.getPlayer_name() +
                            " , Score: " + hs.getScore();

            scoresStr.add(j++ + " : "  +
                    hs.getPlayer_name() + "\t" +
                    hs.getScore());

            //adapter.add(newUser);
            // Writing HiScore to log
            Log.i("Score: ", log);



        }



        Log.i("divider", "====================");

        HIScore singleScore = db.getHiScore(5);
        Log.i("High Score 5 is by ", singleScore.getPlayer_name() + " with a score of " +
                singleScore.getScore());

        Log.i("divider", "====================");

        // Calling SQL statement
        List<HIScore> top5HiScores = db.getTopFiveScores();

        for (HIScore hs : top5HiScores) {
            String log =
                    "Id: " + hs.getScore_id() +
                            ", Date: " + hs.getGame_date() +
                            " , Player: " + hs.getPlayer_name() +
                            " , Score: " + hs.getScore();

            // Writing HiScore to log
            Log.i("Score: ", log);
        }
        Log.i("divider", "====================");

        HIScore hiScore = top5HiScores.get(top5HiScores.size() - 1);
        // hiScore contains the 5th highest score
        Log.i("fifth Highest score: ", String.valueOf(hiScore.getScore()) );

        // simple test to add a hi score
        int myCurrentScore = 40;
        // if 5th highest score < myCurrentScore, then insert new score
        if (hiScore.getScore() < myCurrentScore) {
            db.addHiScore(new HIScore("08 DEC 2020", "Easy","Elrond", 40));
        }

        Log.i("divider", "====================");

        // Calling SQL statement
        top5HiScores = db.getTopFiveScores();

        for (HIScore hs : top5HiScores) {
            String log =
                    "Id: " + hs.getScore_id() +
                            ", Date: " + hs.getGame_date() +
                            " , Player: " + hs.getPlayer_name() +
                            " , mode: " + hs.getMode() +
                            " , Score: " + hs.getScore();




            // Writing HiScore to log
            Log.i("Score: ", log);
           // sb.append(log + ", ");
        }

//        ArrayAdapter<HIScore> itemsAdapter =
//                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, scoresStr[0]);
//        listView.setAdapter(itemsAdapter);
//
//        TextView t = findViewById(R.id.tv);
//        t.setText(scoresStr.toString());

    }
    }
