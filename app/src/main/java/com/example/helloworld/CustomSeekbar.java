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
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class CustomSeekbar extends androidx.appcompat.widget.AppCompatSeekBar {

    private Paint textPaint;

    private Rect textBounds = new Rect();

    private String text = "";

    public CustomSeekbar(@NonNull Context context) {
        super(context);
        textPaint = new Paint();
        textPaint.setColor(Color.DKGRAY);

    }

    public CustomSeekbar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        textPaint = new Paint();
        textPaint.setColor(Color.DKGRAY);
    }

    // Konsturktor für die Entfaltung der View aus XML, wenn es ein Style-Attribut besitzt
    public CustomSeekbar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        textPaint = new Paint();
        textPaint.setColor(Color.DKGRAY);
        //spezielle Initiallisierungen kommen hierher
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        // First draw the regular progress bar, then custom draw our text
        super.onDraw(canvas);
        // Now get size of seek bar.
        float width = getWidth();
        float height = getHeight();

        // Now progress position and convert to text.
        text = Integer.toString(getProgress());
        text += "%";

        // Set text size.
        textPaint.setTextSize((height * 2) / 4);
        // Get size of text.
        textPaint.getTextBounds(text, 0, text.length(), textBounds);


        // Calculate y text print position.
        float yPosition = (height / 2) - textBounds.centerY(); //- (textBounds.bottom / 2);
        float xPosition = (width / 2) - textBounds.centerX(); //- (textBounds.bottom / 2);
        //draw progress
        canvas.drawText(text,xPosition,yPosition,textPaint);

        // draw Images
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sun_empty);
        canvas.drawBitmap(bitmap, null, new RectF(1*(height/4), 1*(height/4), 3*(height/4), 3*(height/4)), null);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sun_full);
        canvas.drawBitmap(bitmap, null, new RectF(getWidth()- 3*(height/4), 1*(height/4), width-1*(height/4), 3*(height/4)), null);
    }
}
