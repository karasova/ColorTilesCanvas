package com.example.colortilescanvas;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class TilesView extends View {
    int[][] tiles = new int[4][4];
    Resources r = getResources();
    int darkColor = r.getColor(R.color.dark);
    int brightColor = r.getColor(R.color.bright);
    int width, height;
    MainActivity activity;

    public TilesView(Context context) {
        super(context);
        activity = (MainActivity) context;
        fillTiles();
    }

    public TilesView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void fillTiles() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j <4; j++) {
                double cof = Math.random();
                if (cof >= 0.5) {
                    tiles[i][j] = brightColor;
                }
                else {
                    tiles[i][j] = darkColor;
                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = canvas.getWidth();
        height = canvas.getHeight();
        int cur_width = 0, cur_height = 0;
        Paint p = new Paint();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j<4; j++) {
                int color = tiles[i][j];
                p.setColor(color);
                canvas.drawRect(cur_width, cur_height, cur_width + width/4, cur_height + height/4, p);
                cur_width += width/4;
            }
            cur_height += height/4;
            cur_width = 0;
        }
    }

    private boolean win(){
        int color = tiles[0][0];
        for (int i = 0; i<4; i++) {
            for (int j= 0; j<4; j++){
                if (tiles[i][j] != color) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            x /= (width / 4);
            y /= (height / 4);
            tiles[x][y] = tiles[x][y] == darkColor ? brightColor : darkColor;
            for (int i = 0; i < 4; i++) {
                tiles[i][x] = tiles[i][x] == darkColor ? brightColor : darkColor;
                tiles[y][i] = tiles[y][i] == darkColor ? brightColor : darkColor;
            }
            invalidate();
        }
        if (win()) {
            Toast end = Toast.makeText(activity, "Поздравляем! Вы победили!", Toast.LENGTH_LONG);
            end.show();
            fillTiles();
        }
        return true;
    }
}