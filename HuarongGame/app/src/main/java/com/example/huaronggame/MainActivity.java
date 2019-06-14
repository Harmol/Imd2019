package com.example.huaronggame;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class  MainActivity extends AppCompatActivity {
    public TextView textScore;
    private int step, id;
    private static MainActivity mainActivity = null;
    private MediaPlayer mediaPlayer;
    public EditText editText;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    public MainActivity(){
        mainActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 1);

        setContentView(R.layout.activity_main);

        editText = new EditText(this);
        editText.setHint("user");

        step = 0;

        textScore = findViewById(R.id.text_score);
        showStep();

//        // 本地数据库
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
    }

    public void showStep(){
        textScore.setText("Step: " + step);
    }

    public void nextStep(){
        step++;
        showStep();
        // 音效
        MediaPlayer move_sound = MediaPlayer.create(this, R.raw.move_sound);
        move_sound.start();
    }

    public int getId(){
        return id;
    }

    public static MainActivity getMainActivity(){
        return mainActivity;
    }
    public void success(){
        String name = "user";
        String input = editText.getText().toString();
        if(input != null&&input.length() > 0){
            name = input;
        }
        System.out.println("输入用户名：" + name);

        ContentValues values = new ContentValues();
        String type = this.getResources().getString(R.string.round_text1);;
        switch (id){
            case 1:
                type = this.getResources().getString(R.string.round_text1);
                break;
            case 2:
                type = this.getResources().getString(R.string.round_text2);
                break;
            case 3:
                type = this.getResources().getString(R.string.round_text3);
                break;
            case 4:
                type = this.getResources().getString(R.string.round_text4);
                break;
            case 5:
                type = this.getResources().getString(R.string.round_text5);
                break;
        }
        values.put("type", type);
        values.put("name", name);
        values.put("step", step);

        db.insert(DatabaseHelper.TABLE_RECORD, null, values);

        finish();
    }

    @Override
    public void finish() {
        super.finish();
    }
}

