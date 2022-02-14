package com.example.helloworld.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClassTheme  implements Serializable {
    public static String theme_title;
    public static List<ClassLamp> classTheme_List = new ArrayList<ClassLamp>();

    public ClassTheme(String theme_title) {
        this.theme_title = theme_title;

        ClassLamp classLamp = new ClassLamp(false,0,0,0,0);
        classTheme_List.add(classLamp);
        classTheme_List.add(classLamp);
        classTheme_List.add(classLamp);
        classTheme_List.add(classLamp);
        classTheme_List.add(classLamp);
    }
    public void addClassTheme(ClassLamp data) {
        classTheme_List.add(data);
    }

    public void addStateClassTheme(boolean state, int position) {
        classTheme_List.get(position).switch_state = state;
    }

    public void addProgressClassTheme(int brightness, int position) {
        classTheme_List.get(position).brightness = brightness;
    }

    public void addColorwheelClassTheme(int x, int y, int color, int position) {
        classTheme_List.get(position).color = color;
        classTheme_List.get(position).x = x;
        classTheme_List.get(position).y = y;
    }
}
