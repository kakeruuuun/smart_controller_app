package com.example.tsuchiyashouhei.smart_controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

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
        RadioGroup room_radio = (RadioGroup) findViewById(R.id.RoomGroup);
        RadioGroup obj_radio = (RadioGroup) findViewById(R.id.ObjGroup);
        RadioGroup status_radio = (RadioGroup) findViewById(R.id.StatusGroup);

        int roomId = room_radio.getCheckedRadioButtonId();
        int objId = obj_radio.getCheckedRadioButtonId();
        int statusId = status_radio.getCheckedRadioButtonId();

        switch (roomId)
        {
            case R.id.action_myroom:
                room = "myroom";
                break;
            case R.id.action_living:
                room = "living";
                break;
            default:
                room = "myroom";
        }

        switch (objId)
        {
            case R.id.action_light:
                obj = "light_";
                break;
            case R.id.action_tv:
                obj = "tv____";
                break;
            case R.id.action_aircon:
                obj = "aircon";
                break;
            default:
                obj = "light_";
        }

        switch (statusId)
        {
            case R.id.action_on:
                status = "on_";
                break;
            case R.id.action_off:
                status = "off";
                break;
            default:
                status = "off";
        }



        message = room + obj + status;
        task.execute("debian114514", message);
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}