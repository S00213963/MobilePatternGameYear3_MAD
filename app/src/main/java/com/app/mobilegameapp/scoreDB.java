package com.app.mobilegameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.app.ListActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class scoreDB extends ListActivity {

   // private CommentDataSource datasource;
    public EditText userText = null;
    ListView listView;
    String name, score;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_db);

        Bundle extras = getIntent().getExtras();
        if(extras != null) // getting values from intent
        {
            name = extras.getString("name");
            score = extras.getString("score");

        }
        userText = (EditText) findViewById(R.id.input1);

        DatabaseHandler db = new DatabaseHandler(this);


        List<HIScore> top5HiScores = db.getTopFiveScores();
        HIScore hiScore = top5HiScores.get(top5HiScores.size() - 1);
        top5HiScores = db.getTopFiveScores();


        List<String> scoresStr;
        scoresStr = new ArrayList<>();

        for (HIScore hs : top5HiScores) {
            String log =
                    "Id: " + hs.getScore_id() +
                            ", Date: " + hs.getGame_date() +
                            " , Player: " + hs.getPlayer_name() +
                            " , Score: " + hs.getScore();


            scoresStr.add(log);

            // Writing HiScore to log
            Log.i("Score: ", log);
            // sb.append(log + ", ");
        }

//        Log.i("fifth Highest score: ", String.valueOf(hiScore.getScore()) );
//
//       String[] _score = scoresStr.get(2).split(" ");
//
//
//        int myCurrentScore = Integer.parseInt(_score[_score.length-1]);
//        String currentTime = Calendar.getInstance().getTime().toString();
//
//
//        Toast.makeText(this, String.valueOf(myCurrentScore),
//                Toast.LENGTH_LONG).show();
//
//        // if 5th highest score < myCurrentScore, then insert new score
//        if (Integer.parseInt(score) > myCurrentScore) {
//            db.addHiScore(new HIScore(currentTime, name, Integer.parseInt(score)));
//
//        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, scoresStr);
        setListAdapter(adapter);




        }

//        listView = findViewById(R.id.listView);
//        ArrayAdapter<HIScore> arrayAdapter = new ArrayAdapter<HIScore>(this,
//                android.R.layout.simple_list_item_1,
//                hiScores);
//        listView.setAdapter(arrayAdapter);



    public void Restart(View v)
    {
        Intent i = (new Intent(scoreDB.this, MainActivity.class));
        startActivity(i);

    }

    }





//    public void onClick(View view) {
//        @SuppressWarnings("unchecked")
//        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
//        Comment comment = null;
//        switch (view.getId()) {
//            case R.id.add:
//                String inStr = userText.getText().toString();
//                // save the new comment to the database
//                comment = datasource.createComment(inStr);
//                adapter.add(comment);
//                break;
//            case R.id.delete:
//                if (getListAdapter().getCount() > 0) {
//                    comment = (Comment) getListAdapter().getItem(0);
//                    datasource.deleteComment(comment);
//                    adapter.remove(comment);
//                }
//                break;
//        }
//        adapter.notifyDataSetChanged();
//    }
//
//    @Override
//    protected void onResume() {
//        datasource.open();
//        super.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        datasource.close();
//        super.onPause();
//    }
