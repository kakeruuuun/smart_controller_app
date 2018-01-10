package com.example.tsuchiyashouhei.smart_controller;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import twitter4j.DirectMessage;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TweetActivity extends FragmentActivity {

    private Twitter mTwitter;
    private String     message;
    private String     room;
    private String     obj;
    private String     status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);

        mTwitter = TwitterUtils.getTwitterInstance(this);


        findViewById(R.id.action_tweet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { sendDM(); }
        });

    }



    private void sendDM() {
        AsyncTask<String, Void, Boolean> task = new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... params) {
                try {
                    mTwitter.sendDirectMessage(params[0], params[1]);
                    return true;
                } catch (TwitterException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (result) {
                    showToast("DMが完了しました！");
                    finish();
                } else {
                    showToast("DMに失敗しました。。。");
                }
            }
        };
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        int roomId = radioGroup.getCheckedRadioButtonId();
        if(roomId == R.id.action_myroom)        room = "myroom";
        else if(roomId == R.id.action_living)   room = "living";

        message = room + "airconoff";
        task.execute("debian114514", message);
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}