package com.example.helloworld.recycler_view;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.widget.Switch;


public class RV_controll_items_data {
    Drawable drawable_image;

    String main_title;
    String connectivity_title;
    Boolean lampe_state;

    int brightness_visible;
    String brightness_title;
    Integer brightness_level;

    int colour_visible;
    String title_colour;
    Color led_color;

    public RV_controll_items_data(Drawable drawable_image,
                                  String main_title, String connectivity_title, Boolean lampe_state,
                                  int brightness_visible, String brightness_title, Integer brightness_level,
                                  int colour_visible, String title_colour, Color led_color) {

        this.drawable_image = drawable_image;
        this.main_title = main_title;

        this.connectivity_title = connectivity_title;
        this.lampe_state = lampe_state;

        this.brightness_visible = brightness_visible;
        this.brightness_title = brightness_title;
        this.brightness_level = brightness_level;

        this.colour_visible = colour_visible;
        this.title_colour = title_colour;
        this.led_color = led_color;

    }
}
