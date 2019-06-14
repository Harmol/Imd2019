package com.example.huaronggame;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.LinkedList;

public class ScoreListActivity extends AppCompatActivity {
    private final LinkedList<String> mWordList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private ScoreListAdapter mAdapter;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 本地数据库
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        Cursor cursor = db.query(DatabaseHelper.TABLE_RECORD, null,
                "type=?", new String[]{getResources().getString(R.string.round_text2)},null,null,"step",null);

        System.out.println("数据库");
        while (cursor.moveToNext()){
            String type = cursor.getString(cursor.getColumnIndex("type"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int step = cursor.getInt(cursor.getColumnIndex("step"));

            String record = "关卡：" + type + "\n用户：" + name + "\n步数：" + step;

            mWordList.addLast(record);
        }

        cursor.close();

        setContentView(R.layout.score_list_layout);

        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new ScoreListAdapter(this, mWordList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}
