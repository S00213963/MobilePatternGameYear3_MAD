package com.app.mobilegameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Bundle;
import java.util.List;
import android.app.ListActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

public class scoreDB extends ListActivity {

    private CommentDataSource datasource;
    public EditText userText = null;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_db);

        userText = (EditText) findViewById(R.id.input1);

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

        datasource = new CommentDataSource(this);
        datasource.open();

        List<Comment> values = datasource.getAllComments();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView

        List<HIScore> top5HiScores = db.getTopFiveScores();
        HIScore hiScore = top5HiScores.get(top5HiScores.size() - 1);
        top5HiScores = db.getTopFiveScores();

        ArrayAdapter<HIScore> adapter = new ArrayAdapter<HIScore>(this,
                android.R.layout.simple_spinner_dropdown_item, top5HiScores);
        setListAdapter(adapter);

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
}