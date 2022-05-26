package com.example.lm;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class GameField extends View {

    boolean lampsState[][];
    int fieldSizeN = 4;
    int fieldSizeM = 4;
    int r, ro; //радиус и расстояние между окружностями
    float x, y;
    float touchX, touchY;
    public GameField(Context context) {
        super(context);
        //сформировать массив случайно
        Random random = new Random();
        lampsState = new boolean[fieldSizeN][fieldSizeM];
        for (int i = 0; i < lampsState.length; i++) {
            for (int j = 0; j < lampsState[i].length; j++) {
                lampsState[i][j] = random.nextBoolean();
            }
        }
        //рассчитать радиус и расстояние между окружностями
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        r = canvas.getWidth()/(fieldSizeM * 4);
        ro = r;
        //отрисовать окружности согласно массиву состояний
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(4);
        for (int i = 0; i < lampsState.length; i++) {
            for (int j = 0; j < lampsState[i].length; j++) {
                if (lampsState[i][j] == true) {
                    paint.setStyle(Paint.Style.FILL_AND_STROKE);
                }
                else {
                    paint.setStyle(Paint.Style.STROKE);
                }
                x = (j + 1) * (2 * r + ro);
                y = (i + 1) * (2 * r + ro);
                canvas.drawCircle(x, y, r, paint);
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            touchX = event.getX();
            touchY = event.getY();
            calculate();
        }
        return super.onTouchEvent(event);
    }

    void calculate(){
        int i = Math.round(touchY / (2 * r + ro));
        int j = Math.round(touchX / (2 * r + ro));
        if (i<=4 && j<=4) {
            x = j * (2 * r + ro);
            y = i * (2 * r + ro);
            if (Math.pow(touchX - x, 2) + Math.pow(touchY - y, 2) <= r * r) {
                lampsState[i - 1][j - 1] = !lampsState[i - 1][j - 1];
                if(i - 2<4&&j - 1<4&&i-2>=0&&j-1>=0){
                    lampsState[i - 2][j - 1] = !lampsState[i - 2][j - 1];
                }
                if(i<4&&j - 1<4&&i>=0&&j-1>=0){
                    lampsState[i][j - 1] = !lampsState[i][j - 1];
                }
                if(i-1<4&&j - 2<4&&i-1>=0&&j-2>=0) {
                    lampsState[i-1][j - 2] = !lampsState[i-1][j - 2];
                }
                if(i-1<4&&j<4&&i-1>=0&&j>=0) {
                    lampsState[i-1][j] = !lampsState[i-1][j];
                }
                int number=0;
                for (int i1 = 0; i1 < lampsState.length; i1++) {
                    for (int j1 = 0; j1 < lampsState[i1].length; j1++) {
                        if(lampsState[i1][j1]) {
                            number++;
                        }
                        else{
                            break;
                        }
                        if(number==16){
                            System.out.println("Молодец");
                        }
                    }
                }
            }
            invalidate();
        }
    }
}
