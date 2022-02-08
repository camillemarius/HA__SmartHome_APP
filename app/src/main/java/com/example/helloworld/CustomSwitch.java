package com.example.helloworld;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomSwitch extends Switch {

    private Paint textPaint;

    private Rect textBounds = new Rect();

    private String text = "OFF";

    public CustomSwitch(@NonNull Context context) {
        super(context);
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
    }

    public CustomSwitch(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
    }

    // Konsturktor für die Entfaltung der View aus XML, wenn es ein Style-Attribut besitzt
    public CustomSwitch(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        //spezielle Initiallisierungen kommen hierher
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        Log.d("CustomSwitch", "called onDraw for CustomSwitch");
        // First draw the regular progress bar, then custom draw our text
        super.onDraw(canvas);
        // Now get size of seek bar.
        float width = getWidth();
        float height = getHeight();

        if(isChecked()) {
            text = "ON";
        } else {
            text = "OFF";
        }
        // Set text size.
        textPaint.setTextSize((height * 2) / 7);
        // Get size of text.
        textPaint.getTextBounds(text, 0, text.length(), textBounds);

        // Now progress position and convert to text.

        // Calculate y text print position.
        float yPosition = (height / 2) - textBounds.centerY();
        float xPosition = ((width* 2)/ 5) - textBounds.width();
        //draw progress
        canvas.drawText(text,xPosition,yPosition,textPaint);
    }
}
