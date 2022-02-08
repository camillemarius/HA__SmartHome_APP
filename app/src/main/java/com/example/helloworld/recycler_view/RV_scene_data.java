package com.example.helloworld.recycler_view;

import android.graphics.drawable.Drawable;

public class RV_scene_data {
    Drawable drawable_icon;
    String scene_title;
    String scene_device_cnt;

    public RV_scene_data(String scene_title, String scene_device_cnt, Drawable drawable_icon) {

        this.scene_title = scene_title;
        this.scene_device_cnt = scene_device_cnt;
        this.drawable_icon = drawable_icon;

    }
}
