package com.example.helloworld.recycler_view;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

public class RV_controll_menu_data {
    Drawable drawable_icon;

    String main_title;
    Drawable drawable_background;

    public RV_controll_menu_data(String main_title, Drawable drawable_icon) {

        this.main_title = main_title;
        this.drawable_icon = drawable_icon;

    }
}
