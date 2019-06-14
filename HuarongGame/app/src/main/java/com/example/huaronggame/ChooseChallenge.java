package com.example.huaronggame;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ChooseChallenge extends AppCompatActivity implements View.OnClickListener {
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    TextView v1,v2,v3,v4,v5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosing_layout);

        Button btn1 = findViewById(R.id.button1);
        btn1.setOnClickListener(this);
        v1 = findViewById(R.id.textView1);
        Button btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener(this);
        v2 = findViewById(R.id.textView2);
        Button btn3 = findViewById(R.id.button3);
        btn3.setOnClickListener(this);
        v3 = findViewById(R.id.textView3);
        Button btn4 = findViewById(R.id.button4);
        btn4.setOnClickListener(this);
        v4 = findViewById(R.id.textView4);
        Button btn5 = findViewById(R.id.button5);
        btn5.setOnClickListener(this);
        v5 = findViewById(R.id.textView5);

        // 本地数据库
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        search_best_score();

        // 播放bgm
//        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.main_music_swan_song);
//        mediaPlayer.setLooping(true);
//        mediaPlayer.setVolume(1.2f, 1.2f);
//        mediaPlayer.start();
    }

    public void search_best_score(){
        String c1 = this.getResources().getString(R.string.round_text1);
        String c2 = this.getResources().getString(R.string.round_text2);
        String c3 = this.getResources().getString(R.string.round_text3);
        String c4 = this.getResources().getString(R.string.round_text4);
        String c5 = this.getResources().getString(R.string.round_text5);
        String c[] = {c1, c2, c3, c4, c5};
        int steps[] = {999, 999, 999, 999, 999};

        Cursor cursor = db.query(DatabaseHelper.TABLE_RECORD, null,
                null, null,null,null,null,null);

        System.out.println("数据库");
        while (cursor.moveToNext()){
            String type = cursor.getString(cursor.getColumnIndex("type"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int step = cursor.getInt(cursor.getColumnIndex("step"));

            for(int i = 0; i < 5; i++){
                if(type.equals(c[i])){
                    if(step < steps[i]){
                        System.out.println("更新步数");
                        steps[i] = step;
                        switch (i){
                            case 0:
                                v1.setText("最佳成绩："+step+"步");
                                break;
                            case 1:
                                v2.setText("最佳成绩："+step+"步");
                                break;
                            case 2:
                                v3.setText("最佳成绩："+step+"步");
                                break;
                            case 3:
                                v4.setText("最佳成绩："+step+"步");
                                break;
                            case 4:
                                v5.setText("最佳成绩："+step+"步");
                                break;
                        }
                    }
                    continue;
                }
            }
        }


        cursor.close();
    }

    public void skip_to_challenge(int id) {
        // 音效
        MediaPlayer move_sound = MediaPlayer.create(this, R.raw.move_sound);
        move_sound.start();

        Intent intent = new Intent();
        intent.putExtra("id", id);
        intent.setClass(ChooseChallenge.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int id;
        switch (v.getId()){
            case R.id.button1:
                id = 1;
                break;
            case R.id.button2:
                id = 2;
                break;
            case R.id.button3:
                id = 3;
                break;
            case R.id.button4:
                id = 4;
                break;
            case R.id.button5:
                id = 5;
                break;
                default:
                    id = 1;
        }
        skip_to_challenge(id);
    }

    @Override
    protected void onResume() {
        search_best_score();
        super.onResume();
    }
}
