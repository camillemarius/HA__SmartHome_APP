package com.example.helloworld;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.helloworld.custom_ui_elements.CustomColorWheel;
import com.example.helloworld.custom_ui_elements.CustomSeekbar;
import com.example.helloworld.custom_ui_elements.CustomSwitch;
import com.example.helloworld.database.ClassTheme;
import com.example.helloworld.database.DatabaseHandler;
import com.example.helloworld.recycler_view.RV_controll_items_adapter;
import com.example.helloworld.recycler_view.RV_controll_items_data;
import com.example.helloworld.recycler_view.RV_controll_menu_adapter;
import com.example.helloworld.recycler_view.RV_controll_menu_data;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class controll_lamps extends AppCompatActivity implements RV_controll_menu_adapter.MenuListener, RV_controll_items_adapter.ItemListener{
    LinearLayout item_deckenlampe, item_w_led, item_dekolampe, item_rgb_led, item_lichterkette;
    ImageView imageView_back, imageView_add;
    TextView textView_theme_title, textView_activ_devices;
    CustomSwitch customSwitch_lampe_state;
    CustomSeekbar customSeekBar_brightness_level;
    CustomColorWheel customcolorWheel;

    RV_controll_items_adapter item_adapter;
    RV_controll_menu_adapter menu_adapter;
    LinearLayoutManager layoutManager_items;
    LinearLayoutManager layoutManager_menu;

    DatabaseHandler db = null;
    ClassTheme classTheme = null;
    String theme_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mode_settings);

        //----------------------------------------------------------------------------------------//
        // Intents
        //----------------------------------------------------------------------------------------//
        theme_title  = getIntent().getStringExtra("EXTRA_THEME_TITLE");

        //----------------------------------------------------------------------------------------//
        // Databse
        //----------------------------------------------------------------------------------------//
        db = new DatabaseHandler(getApplicationContext());
        db.deleteAllEntries();
        classTheme = new ClassTheme(theme_title);
        //classTheme = db.restoreClass(theme_title);    //EDIT
        //classTheme = db.restoreClass(theme_title);    //EDIT


        //----------------------------------------------------------------------------------------//
        // Link UI
        //----------------------------------------------------------------------------------------//
        textView_theme_title = findViewById(R.id.textView_theme_title);
        textView_theme_title.setText(theme_title);

        imageView_back = findViewById(R.id.imageView_back);
        imageView_add = findViewById(R.id.imageView_add);

        textView_activ_devices = findViewById(R.id.textView_activ_devices);

        customSwitch_lampe_state = findViewById(R.id.customSwitch_lampe_state);
        customSeekBar_brightness_level = findViewById(R.id.customSeekBar_brightness_level);
        customcolorWheel = findViewById(R.id.customcolorWheel);

        //----------------------------------------------------------------------------------------//
        // Create Lamp Item Scroll List
        //----------------------------------------------------------------------------------------//
        ArrayList<RV_controll_menu_data> menu_list = new ArrayList<>();
        RV_controll_menu_data menu_item = new RV_controll_menu_data(
                "Deckenlampe",ContextCompat.getDrawable(this,R.drawable.icon_lampe));
        menu_list.add(menu_item);

        menu_item = new RV_controll_menu_data(
                "LED Weiss",ContextCompat.getDrawable(this,R.drawable.icon_led));
        menu_list.add(menu_item);

        menu_item = new RV_controll_menu_data(
                "Dekolampe",ContextCompat.getDrawable(this,R.drawable.icon_dekolampe));
        menu_list.add(menu_item);

        menu_item = new RV_controll_menu_data(
                "LED RGB",ContextCompat.getDrawable(this,R.drawable.icon_led));
        menu_list.add(menu_item);

        menu_item = new RV_controll_menu_data(
                "Lichterkette",ContextCompat.getDrawable(this,R.drawable.icon_led));
        menu_list.add(menu_item);

        //----------------------------------------------------------------------------------------//
        // set up RecyclerView
        //----------------------------------------------------------------------------------------//
        layoutManager_menu = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView_menu = findViewById(R.id.recylerview_menu);
        recyclerView_menu.setLayoutManager(layoutManager_menu);
        menu_adapter = new RV_controll_menu_adapter(this, menu_list);
        menu_adapter.setMenuListener(this);
        recyclerView_menu.setAdapter(menu_adapter);

        //----------------------------------------------------------------------------------------//
        // Create Lamp Settings Scroll List
        //----------------------------------------------------------------------------------------//
        ArrayList<RV_controll_items_data> items_list = new ArrayList<>();
        RV_controll_items_data items = new RV_controll_items_data(
                ContextCompat.getDrawable(this, R.drawable.deckenlampe),
                "Deckenlampe", "Verbunden", classTheme.classTheme_List.get(0).switch_state,
                View.GONE, "Helligkeit" ,classTheme.classTheme_List.get(0).brightness,
                View.GONE,"Farbe", classTheme.classTheme_List.get(0).x,classTheme.classTheme_List.get(0).y,classTheme.classTheme_List.get(0).color);
        items_list.add(items);

        items = new RV_controll_items_data(
                ContextCompat.getDrawable(this, R.drawable.diffusor),
                "LED RGB", "Verbunden", classTheme.classTheme_List.get(1).switch_state,
                View.VISIBLE, "Helligkeit" ,classTheme.classTheme_List.get(1).brightness,
                View.VISIBLE, "Farbe", classTheme.classTheme_List.get(1).x,classTheme.classTheme_List.get(1).y,classTheme.classTheme_List.get(1).color);
        items_list.add(items);

        items = new RV_controll_items_data(
                ContextCompat.getDrawable(this, R.drawable.dekolampe),
                "Dekolampe", "Verbunden", classTheme.classTheme_List.get(2).switch_state,
                View.VISIBLE, "Helligkeit" ,classTheme.classTheme_List.get(2).brightness,
                View.GONE, "Farbe", classTheme.classTheme_List.get(2).x,classTheme.classTheme_List.get(2).y,classTheme.classTheme_List.get(2).color);
        items_list.add(items);

        items = new RV_controll_items_data(
                ContextCompat.getDrawable(this, R.drawable.diffusor),
                "LED Weiss", "Verbunden", classTheme.classTheme_List.get(3).switch_state,
                View.VISIBLE, "Helligkeit" ,classTheme.classTheme_List.get(3).brightness,
                View.GONE, "Farbe", classTheme.classTheme_List.get(3).x,classTheme.classTheme_List.get(3).y,classTheme.classTheme_List.get(3).color);
        items_list.add(items);

        items = new RV_controll_items_data(
                ContextCompat.getDrawable(this, R.drawable.rgbstrip),
                "Lichterkette", "Verbunden", classTheme.classTheme_List.get(4).switch_state,
                View.GONE, "Helligkeit" ,classTheme.classTheme_List.get(4).brightness,
                View.GONE, "Farbe", classTheme.classTheme_List.get(4).x,classTheme.classTheme_List.get(4).y,classTheme.classTheme_List.get(4).color);
        items_list.add(items);


        //----------------------------------------------------------------------------------------//
        // set up RecyclerView
        //----------------------------------------------------------------------------------------//
        layoutManager_items = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView_items = findViewById(R.id.recylerview_items);
        recyclerView_items.setLayoutManager(layoutManager_items);
        recyclerView_items.setItemAnimator(new SlideInLeftAnimator());
        //recyclerView_items.setItemAnimator(new SlideInRightAnimator());
        recyclerView_items.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                 if(layoutManager_items.findFirstCompletelyVisibleItemPosition()!=-1) {
                     RV_controll_menu_adapter.shown_item_index = layoutManager_items.findLastCompletelyVisibleItemPosition();
                     menu_adapter.notifyDataSetChanged();
                }
            }
        });
        item_adapter = new RV_controll_items_adapter(this, items_list);
        item_adapter.setItemListener(this);
        recyclerView_items.setAdapter(item_adapter);
        // Scroll to next Item
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView_items);

        //----------------------------------------------------------------------------------------//
        // Listener
        //----------------------------------------------------------------------------------------//



        //db.deleteAllEntries();
        /*ClassLamp data = new ClassLamp(Boolean.FALSE,50, Color.rgb(50,50,50), 10,50);
        ClassTheme classTheme = new ClassTheme(data);
        db.storeClass(classTheme);



        ClassLamp classLamp = new ClassLamp(Boolean.TRUE,100, 0,50,50);
        ClassTheme data_return = new ClassTheme(classLamp);
        data_return = db.restoreClass(1);
        Log.d("DatabaseHandler", "Result after read: "+String.valueOf(data_return.classLamps.get(0).brightness));*/






        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //----------------------------------------------------------------------------------------//
    // RV_menu_items_adapter Listener
    //----------------------------------------------------------------------------------------//
    @Override
    public void OnScrolledListener(View view, int position) {
        Log.d("OnScrolledListener", "OnScrolledListener: "+ position);
        layoutManager_items.scrollToPosition(position);
    }

    void set_text_activ_devices() {
        //textView_activ_devices.setText("");
        //search in db for all on switches
    }


    //----------------------------------------------------------------------------------------//
    // RV_controll_items_adapter Listener
    //----------------------------------------------------------------------------------------//
    @Override
    public void OnColorWheelChanged(int x, int y, int color, int position) {
        Log.d("CustomListener", "OnColorWheelChanged called");
        classTheme.addColorwheelClassTheme(x,y,color,position);
    }

    @Override
    public void onStateCheckedChanged(Boolean state, int position) {
        Log.d("CustomListener", "onStateCheckedChanged called");
        classTheme.addStateClassTheme(state,position);
    }

    @Override
    public void onProgressBarChangeListener(int progress, int position) {
        Log.d("CustomListener", "onProgressBarChangeListener called");
        classTheme.addProgressClassTheme(progress,position);

    }

    @Override
    protected void onPause() {
        super.onPause();
        db.storeClass(classTheme);
    }
}
