package com.example.helloworld.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClassTheme  implements Serializable {
    public static List<ClassLamp> classTheme = new ArrayList<ClassLamp>();

    public ClassTheme() {

        ClassLamp classLamp = new ClassLamp(false,0,0,0,0);
        classTheme.add(classLamp);
        classTheme.add(classLamp);
        classTheme.add(classLamp);
        classTheme.add(classLamp);
        classTheme.add(classLamp);
    }
    public void addClassTheme(ClassLamp data) {
        classTheme.add(data);
    }

    public void addStateClassTheme(boolean state, int position) {
        classTheme.get(position).switch_state = state;
    }

    public void addProgressClassTheme(int brightness, int position) {
        classTheme.get(position).brightness = brightness;
    }

    public void addColorwheelClassTheme(int x, int y, int color, int position) {
        classTheme.get(position).color = color;
        classTheme.get(position).x = x;
        classTheme.get(position).y = y;
    }
}
