package com.example.helloworld.database;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.widget.Switch;

import java.io.Serializable;


public class ClassLamp {

    public Boolean switch_state;
    public int brightness;
    public int color;
    public int x,y;

    public ClassLamp(Boolean switch_state, Integer brightness, int color, int x, int y) {

        this.switch_state = switch_state;

        this.brightness = brightness;

        this.color = color;
        this.x = x;
        this.y = y;
    }
}
