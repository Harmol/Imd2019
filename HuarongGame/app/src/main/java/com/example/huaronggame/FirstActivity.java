package com.example.huaronggame;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class FirstActivity extends AppCompatActivity {
    public MediaPlayer mediaPlayer;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        play_bgm();

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
    }

    public void play_bgm(){
        mediaPlayer = MediaPlayer.create(this, R.raw.bgm);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(1.2f, 1.2f);
        mediaPlayer.start();
    }

    public void play_click_sound(){
        // 音效
        MediaPlayer sound = MediaPlayer.create(this, R.raw.move_sound);
        sound.start();
    }

    public void skip_to_choosing(View view) {
        play_click_sound();

        Intent intent = new Intent();
        intent.setClass(FirstActivity.this, ChooseChallenge.class);
        startActivity(intent);
    }

    public void score_list(View view) {
        play_click_sound();

        Intent intent = new Intent();
        intent.setClass(FirstActivity.this, ScoreListActivity.class);
        startActivity(intent);
    }

    public void quite(View view) {
        play_click_sound();

        this.finish();
    }
}
