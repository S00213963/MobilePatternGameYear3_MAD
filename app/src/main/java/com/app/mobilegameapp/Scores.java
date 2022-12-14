package com.app.mobilegameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class Scores extends AppCompatActivity {
TextView tv;
    StringBuilder sb;
    ListView list;
    String countryList[] = {"India", "China", "australia", "Portugle", "America", "NewZealand"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);




        DatabaseHandler db = new DatabaseHandler(this);
        db.emptyHiScores();     // empty table if required
        tv = findViewById(R.id.tv);

        // Inserting hi scores
        Log.i("Insert: ", "Inserting ..");
        db.addHiScore(new HIScore("20 OCT 2020", "Hard", "Frodo", 12));
        db.addHiScore(new HIScore("28 OCT 2020", "Hard", "Dobby", 16));
        db.addHiScore(new HIScore("20 NOV 2020", "Hard","DarthV", 20));
        db.addHiScore(new HIScore("20 NOV 2020","Hard", "Bob", 18));
        db.addHiScore(new HIScore("22 NOV 2020", "Easy","Gemma", 22));
        db.addHiScore(new HIScore("30 NOV 2020", "Easy","Joe", 30));
        db.addHiScore(new HIScore("01 DEC 2020", "Easy","DarthV", 22));
        db.addHiScore(new HIScore("02 DEC 2020", "Easy","Gandalf", 132));
        List<HIScore> hiScores = db.getAllHiScores();


//        list = findViewById(R.id.listView);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_scores, R.id.textView, countryList);
//        list.setAdapter(arrayAdapter);


        // Reading all scores
        Log.i("Reading: ", "Reading all scores..");



        for (HIScore hs : hiScores) {
            String log =
                    "Id: " + hs.getScore_id() +
                            ", Date: " + hs.getGame_date() +
                            " , Player: " + hs.getPlayer_name() +
                            " , Score: " + hs.getScore();

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
                            " , Score: " + hs.getScore();

            // Writing HiScore to log
            Log.i("Score: ", log);
           // sb.append(log + ", ");
        }

       // tv.setText(sb);

    }
    }
