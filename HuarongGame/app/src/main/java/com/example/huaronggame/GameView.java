package com.example.huaronggame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GameView extends GridLayout {
    int column = 4, row = 5, id;
    Card cards[][];

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initGameView();
    }

    public GameView(Context context) {
        super(context);

        initGameView();
    }

    private void initGameView(){
        // 4 x 5 的棋盘
        setColumnCount(column);
        setRowCount(row);

        int id = MainActivity.getMainActivity().getId();
        int cardWidth = getCardWidth();

        cards = new Card[column][row];
        // 添加卡片
        switch (id){
            case 1:
                addCards_mode1(cardWidth, cardWidth);
                break;
            case 2:
                addCards_mode2(cardWidth, cardWidth);
                break;
            case 3:
                addCards_mode3(cardWidth, cardWidth);
                break;
            case 4:
                addCards_mode4(cardWidth, cardWidth);
                break;
            case 5:
                addCards_mode5(cardWidth, cardWidth);
                break;
                default:
        }


    }

    // 根据屏幕大小获取卡片宽度
    private int getCardWidth(){
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        return Math.min((width - 10) / 4, (height - 10) / 5);
    }

    // 添加一张卡片
    @SuppressLint("ClickableViewAccessibility")
    private void addCard(final int cardWidth, final int cardHeight, int x, int y, String name, final int rowspan, final int columnspan){
        Card c = new Card(getContext(), name, rowspan, columnspan);

        // 向棋盘数组添加卡片
        for(int i = x; i <= x + columnspan - 1; i++){
            for(int j = y; j <= y + rowspan - 1; j++){
                cards[i][j] = c;
            }
        }

        GridLayout.LayoutParams param = new GridLayout.LayoutParams();
        param.columnSpec = GridLayout.spec(x, columnspan);
        param.rowSpec = GridLayout.spec(y, rowspan);
        param.width = cardWidth * columnspan;
        param.height = cardHeight * rowspan;
        c.setLayoutParams(param);
        c.setOnTouchListener(new View.OnTouchListener(){
            private int startX, startY, offsetX, offsetY;
            private boolean canleft, canright, canup, candown;
            private boolean left, right, up, down;
            private int view_left, view_right, view_top, view_bottom;
            // 卡片最左上角坐标
            int indexX, indexY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startX = x;
                        startY = y;
                        canleft = canright = canup = candown = true;
                        left = right = up = down = false;
                        view_left = v.getLeft();
                        view_right = v.getRight();
                        view_top = v.getTop();
                        view_bottom = v.getBottom();
                        indexX = view_left / cardWidth;
                        indexY = view_top / cardHeight;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int setX = x - startX;
                        int setY = y - startY;

                        // 判断移动方向
                        if((!left&&!right&&!up&&!down)&&(canleft||canright||canup||candown)){// 未决定移动方向
                            // 根据棋盘分布判断能移动的方向
                            // 边界判断
                            if(indexX == 0) canleft = false;
                            if((indexX + columnspan) == column ) canright = false;
                            if(indexY == 0) canup = false;
                            if((indexY + rowspan) == row ) candown = false;
                            // 判断是否被阻挡
                            if(canleft){
                                for(int i = 0; i < rowspan; i++){
                                    if(cards[indexX - 1][indexY + i] != null) {
                                        canleft = false;
                                        break;
                                    }
                                }
                            }
                            if(canright){
                                for(int i = 0; i < rowspan; i++){
                                    if(cards[indexX + columnspan][indexY + i] != null) {
                                        canright = false;
                                        break;
                                    }
                                }
                            }
                            if(canup){
                                for(int i = 0; i < columnspan; i++){
                                    if(cards[indexX + i][indexY - 1] != null) {
                                        canup = false;
                                        break;
                                    }
                                }
                            }
                            if(candown){
                                for(int i = 0; i < columnspan; i++){
                                    if(cards[indexX + i][indexY + rowspan] != null) {
                                        candown = false;
                                        break;
                                    }
                                }
                            }

                            if(canleft&&!(canright||canup||candown)){//左
                                left = true;
                            }else if(canright&&!(canleft||canup||candown)){//右
                                right = true;
                            }else if(canup&&!(canleft||canright||candown)){//上
                                up = true;
                            }else if(candown&&!(canleft||canright||canup)){//下
                                down = true;
                            }else if(canleft&&canup){//左或上
                                if(setX<0&&(setY>0||(Math.abs(setX)>Math.abs(setY)))){
                                    left = true;
                                }else if(setY<0&&(setX>0||(Math.abs(setY)>Math.abs(setX)))){
                                    up = true;
                                }
                            }else if(canleft&&candown){//左或下
                                if(setX<0&&(setY<0||(Math.abs(setX)>Math.abs(setY)))){
                                    left = true;
                                }else if(setY>0&&(setX>0||(Math.abs(setY)>Math.abs(setX)))){
                                    down = true;
                                }
                            }else if(canright&&canup) {//右或上
                                if(setX>0&&(setY>0||(Math.abs(setX)>Math.abs(setY)))){
                                    right = true;
                                }else if(setY<0&&(setX<0||(Math.abs(setY)>Math.abs(setX)))){
                                    up = true;
                                }
                            }else if(canright&&candown) {//右或下
                                if(setX>0&&(setY<0||(Math.abs(setX)>Math.abs(setY)))){
                                    right = true;
                                }else if(setY>0&&(setX<0||(Math.abs(setY)>Math.abs(setX)))){
                                    down = true;
                                }
                            }else if(canleft&&canright){//左或右
                                if(setX<0)
                                    left = true;
                                else
                                    right = true;
                            }else if(canup&&candown){//上或下
                                if(setY<0)
                                    up = true;
                                else
                                    down = true;
                            }
                        }

                        if(left){
                            v.layout(Math.min(Math.max(view_left - cardWidth, v.getLeft() + setX), view_left),
                                    v.getTop(),
                                    Math.min(Math.max(view_right - cardWidth, v.getRight() + setX), view_right),
                                    v.getBottom());
                        }else if(right){
                            v.layout(Math.max(Math.min(view_left + cardWidth, v.getLeft() + setX), view_left),
                                    v.getTop(),
                                    Math.max(Math.min(view_right + cardWidth, v.getRight() + setX), view_right),
                                    v.getBottom());
                        }else if(up){
                            v.layout(v.getLeft(),
                                    Math.min(Math.max(view_top - cardHeight, v.getTop() + setY), view_top),
                                    v.getRight(),
                                    Math.min(Math.max(view_bottom - cardHeight, v.getBottom() + setY), view_bottom));
                        }else if(down){
                            v.layout(v.getLeft(),
                                    Math.max(Math.min(view_top + cardHeight, v.getTop() + setY), view_top),
                                    v.getRight(),
                                    Math.max(Math.min(view_bottom + cardHeight, v.getBottom() + setY), view_bottom));
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = v.getLeft() - view_left;
                        offsetY = v.getTop() - view_top;
                        int nextX = indexX;
                        int nextY = indexY;

                        if(Math.abs(offsetY)<cardHeight/2&&Math.abs(offsetX)<cardHeight/2){// 移动距离未超过一定距离，回到原位
                            v.layout(view_left, view_top, view_right, view_bottom);
                        }else {// 移动一步
                            MainActivity.getMainActivity().nextStep();// 计步

                            // 改变图像位置
                            if(left){
                                nextX--;
                                v.layout(view_left - cardWidth, view_top, view_right - cardWidth, view_bottom);

                                for(int i = 0; i < rowspan; i++){
                                    cards[indexX - 1][indexY + i] = cards[indexX][indexY + i];
                                    cards[indexX + (columnspan - 1)][indexY + i] = null;
                                }
                            }else if(right){
                                nextX++;
                                v.layout(view_left + cardWidth, view_top, view_right + cardWidth, view_bottom);

                                for(int i = 0; i < rowspan; i++){
                                    cards[indexX + columnspan][indexY + i] = cards[indexX][indexY + i];
                                    cards[indexX][indexY + i] = null;
                                }
                            }else if(up){
                                nextY--;
                                v.layout(view_left, view_top - cardHeight, view_right, view_bottom - cardHeight);

                                for(int i = 0; i < columnspan; i++){
                                    cards[indexX + i][indexY - 1] = cards[indexX + i][indexY];
                                    cards[indexX + i][indexY + (rowspan - 1)] = null;
                                }
                            }else if(down){
                                nextY++;
                                v.layout(view_left, view_top + cardHeight, view_right, view_bottom + cardHeight);

                                for(int i = 0; i < columnspan; i++){
                                    cards[indexX + i][indexY + rowspan] = cards[indexX + i][indexY];
                                    cards[indexX + i][indexY] = null;
                                }
                            }

                            // 判断游戏结束
                            if(rowspan == 2 && columnspan == 2){// 是曹操
                                if(nextX == 1 && nextY == 3){// 到达胜利位置
                                    EditText editText = MainActivity.getMainActivity().editText;
                                    new AlertDialog.Builder(getContext()).setTitle("胜利").setMessage("请输入用户名：").setView(editText).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            MainActivity.getMainActivity().success();
                                        }
                                    }).setCancelable(false).show();
                                }
                            }

                            outputCards();
                        }

                        break;
                }
                return true;
            }
        });
        addView(c);
    }

    // 添加一张 1 x 1 的卡片
    private void addCard(int cardWidth, int cardHeight, int x, int y, String name){
        addCard(cardWidth, cardHeight, x, y, name,1,1);
    }

    // 添加所有卡片--关卡：一步之遥
    private void addCards_mode1(int cardWidth, int cardHeight){
        addCard(cardWidth, cardHeight,0,0,"张飞",2,1);
        addCard(cardWidth, cardHeight,1,2,"曹操",2,2);
        addCard(cardWidth, cardHeight,3,0,"赵云",2,1);
        addCard(cardWidth, cardHeight,0,2,"马超",2,1);
        addCard(cardWidth, cardHeight,1,1,"关羽",1,2);
        addCard(cardWidth, cardHeight,3,2,"黄忠",2,1);
        addCard(cardWidth, cardHeight,0,4,"卒");
        addCard(cardWidth, cardHeight,3,4,"卒");
        addCard(cardWidth, cardHeight,1,0,"卒");
        addCard(cardWidth, cardHeight,2,0,"卒");
    }

    // 添加所有卡片--关卡：横刀立马(经典)
    private void addCards_mode2(int cardWidth, int cardHeight){
        addCard(cardWidth, cardHeight,0,0,"张飞",2,1);
        addCard(cardWidth, cardHeight,1,0,"曹操",2,2);
        addCard(cardWidth, cardHeight,3,0,"赵云",2,1);
        addCard(cardWidth, cardHeight,0,2,"马超",2,1);
        addCard(cardWidth, cardHeight,1,2,"关羽",1,2);
        addCard(cardWidth, cardHeight,3,2,"黄忠",2,1);
        addCard(cardWidth, cardHeight,0,4,"卒");
        addCard(cardWidth, cardHeight,3,4,"卒");
        addCard(cardWidth, cardHeight,1,3,"卒");
        addCard(cardWidth, cardHeight,2,3,"卒");
    }

    // 添加所有卡片--关卡：齐头并进
    private void addCards_mode3(int cardWidth, int cardHeight){
        addCard(cardWidth, cardHeight,0,0,"张飞",2,1);
        addCard(cardWidth, cardHeight,1,0,"曹操",2,2);
        addCard(cardWidth, cardHeight,3,0,"赵云",2,1);
        addCard(cardWidth, cardHeight,0,3,"马超",2,1);
        addCard(cardWidth, cardHeight,1,3,"关羽",1,2);
        addCard(cardWidth, cardHeight,3,3,"黄忠",2,1);
        addCard(cardWidth, cardHeight,0,2,"卒");
        addCard(cardWidth, cardHeight,3,2,"卒");
        addCard(cardWidth, cardHeight,1,2,"卒");
        addCard(cardWidth, cardHeight,2,2,"卒");
    }

    // 添加所有卡片--关卡：兵分三路
    private void addCards_mode4(int cardWidth, int cardHeight){
        addCard(cardWidth, cardHeight,0,1,"张飞",2,1);
        addCard(cardWidth, cardHeight,1,0,"曹操",2,2);
        addCard(cardWidth, cardHeight,3,1,"赵云",2,1);
        addCard(cardWidth, cardHeight,0,3,"马超",2,1);
        addCard(cardWidth, cardHeight,1,2,"关羽",1,2);
        addCard(cardWidth, cardHeight,3,3,"黄忠",2,1);
        addCard(cardWidth, cardHeight,0,0,"卒");
        addCard(cardWidth, cardHeight,3,0,"卒");
        addCard(cardWidth, cardHeight,1,3,"卒");
        addCard(cardWidth, cardHeight,2,3,"卒");
    }

    // 添加所有卡片--关卡：左右布兵
    private void addCards_mode5(int cardWidth, int cardHeight){
        addCard(cardWidth, cardHeight,0,0,"张飞",2,1);
        addCard(cardWidth, cardHeight,1,0,"曹操",2,2);
        addCard(cardWidth, cardHeight,3,0,"赵云",2,1);
        addCard(cardWidth, cardHeight,1,2,"马超",2,1);
        addCard(cardWidth, cardHeight,1,4,"关羽",1,2);
        addCard(cardWidth, cardHeight,2,2,"黄忠",2,1);
        addCard(cardWidth, cardHeight,0,3,"卒");
        addCard(cardWidth, cardHeight,0,4,"卒");
        addCard(cardWidth, cardHeight,3,3,"卒");
        addCard(cardWidth, cardHeight,3,4,"卒");
    }

    private void outputCards(){
        // 打印棋盘信息
        for(int i = 0; i<row;i++){
            for(int j = 0;j<column;j++){
                if(cards[j][i] == null){
                    System.out.print("empty");
                }else
                    System.out.print(cards[j][i].name+' ');
            }
            System.out.println();
        }
    }

}

