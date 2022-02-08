package com.example.helloworld.custom_ui_elements;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomColorWheel extends androidx.appcompat.widget.AppCompatImageView {

    private Paint circlePaint;
    PointF point;
    Color color;

    private Rect textBounds = new Rect();

    private String text = "";

    public CustomColorWheel(@NonNull Context context) {
        super(context);
        circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setColor(Color.BLACK);

    }

    public CustomColorWheel(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(10);
        circlePaint.setColor(Color.BLACK);
    }

    // Konsturktor für die Entfaltung der View aus XML, wenn es ein Style-Attribut besitzt
    public CustomColorWheel(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setColor(Color.BLACK);
        //spezielle Initiallisierungen kommen hierher
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        // First draw the regular progress bar, then custom draw our text
        super.onDraw(canvas);
        if (point != null) {
            canvas.drawCircle(point.x, point.y, 40, circlePaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        float x = motionEvent.getX();
        float y = motionEvent.getY();

        Log.d("CustomColorWheel", "onTouchEvent");
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                point = new PointF(x, y);


                /*int viewX = (int) motionEvent.getX();
                int viewY = (int) motionEvent.getY();

                int viewWidth = view.getWidth();
                int viewHeight = view.getHeight();

                Bitmap image = ((BitmapDrawable)holder.colorWheel.getDrawable()).getBitmap();

                int imageWidth = image.getWidth();
                int imageHeight = image.getHeight();

                int imageX = (int)((float)viewX * ((float)imageWidth / (float)viewWidth));
                int imageY = (int)((float)viewY * ((float)imageHeight / (float)viewHeight));

                int currPixel = image.getPixel(imageX, imageY);




                int currPixel = ((BitmapDrawable) getDrawable()).getBitmap().getPixel((int) point.x, (int) point.y);
                Log.d("CustomColorWheel", "(" + String.valueOf(Color.red(currPixel)) + ", " + String.valueOf(Color.blue(currPixel)) + ", " + String.valueOf(Color.green(currPixel)) + ") Pixel is: " + currPixel);
                */
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                point.set(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //point = null;
                invalidate();
                break;
        }
        return true;
    }
}
