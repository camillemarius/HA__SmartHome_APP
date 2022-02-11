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

import com.example.helloworld.recycler_view.RV_controll_items_adapter;
import com.example.helloworld.recycler_view.RV_controll_items_data;
import com.example.helloworld.recycler_view.RV_controll_menu_adapter;
import com.example.helloworld.recycler_view.RV_controll_menu_data;

import java.util.ArrayList;

public class controll_lamps extends AppCompatActivity implements RV_controll_menu_adapter.MenuListener {
    LinearLayout item_deckenlampe, item_w_led, item_dekolampe, item_rgb_led, item_lichterkette;
    ImageView imageView_back, imageView_add;
    TextView textView_theme_title, textView_activ_devices;

    RV_controll_items_adapter item_adapter;
    RV_controll_menu_adapter menu_adapter;
    LinearLayoutManager layoutManager_items;
    LinearLayoutManager layoutManager_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mode_settings);

        String theme_title  = getIntent().getStringExtra("EXTRA_THEME_TITLE");
        textView_theme_title = findViewById(R.id.textView_theme_title);
        textView_theme_title.setText(theme_title);

        //----------------------------------------------------------------------------------------//
        // Link UI
        //----------------------------------------------------------------------------------------//
        imageView_back = findViewById(R.id.imageView_back);
        imageView_add = findViewById(R.id.imageView_add);

        textView_activ_devices = findViewById(R.id.textView_activ_devices);

        //----------------------------------------------------------------------------------------//
        // Fill Item List
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
        // set up the RecyclerView
        //----------------------------------------------------------------------------------------//
        layoutManager_menu = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView_menu = findViewById(R.id.recylerview_menu);
        recyclerView_menu.setLayoutManager(layoutManager_menu);
        menu_adapter = new RV_controll_menu_adapter(this, menu_list);
        menu_adapter.setMenuListener(this);
        recyclerView_menu.setAdapter(menu_adapter);


        //----------------------------------------------------------------------------------------//
        // Fill Item List
        //----------------------------------------------------------------------------------------//
        ArrayList<RV_controll_items_data> items_list = new ArrayList<>();
        RV_controll_items_data items = new RV_controll_items_data(
                ContextCompat.getDrawable(this, R.drawable.deckenlampe),
                "Deckenlampe", "Verbunden", Boolean.FALSE,
                View.GONE, "Helligkeit" ,20,
                View.GONE,"Farbe", null);
        items_list.add(items);

        items = new RV_controll_items_data(
                ContextCompat.getDrawable(this, R.drawable.diffusor),
                "LED RGB", "Verbunden", Boolean.FALSE,
                View.VISIBLE, "Helligkeit" ,80,
                View.VISIBLE, "Farbe", null);
        items_list.add(items);

        items = new RV_controll_items_data(
                ContextCompat.getDrawable(this, R.drawable.dekolampe),
                "Dekolampe", "Verbunden", Boolean.FALSE,
                View.VISIBLE, "Helligkeit" ,30,
                View.GONE, "Farbe", null);
        items_list.add(items);

        items = new RV_controll_items_data(
                ContextCompat.getDrawable(this, R.drawable.diffusor),
                "LED Weiss", "Verbunden", Boolean.FALSE,
                View.VISIBLE, "Helligkeit" ,100,
                View.GONE, "Farbe", null);
        items_list.add(items);

        items = new RV_controll_items_data(
                ContextCompat.getDrawable(this, R.drawable.rgbstrip),
                "Lichterkette", "Verbunden", Boolean.TRUE,
                View.GONE, "Helligkeit" ,50,
                View.GONE, "Farbe", null);
        items_list.add(items);


        //----------------------------------------------------------------------------------------//
        // set up the RecyclerView
        //----------------------------------------------------------------------------------------//
        layoutManager_items = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView_items = findViewById(R.id.recylerview_items);
        recyclerView_items.setLayoutManager(layoutManager_items);
        //recyclerView_items.setItemAnimator(new SlideInLeftAnimator());
        //recyclerView_items.setItemAnimator(new SlideInRightAnimator());
        recyclerView_items.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //int last_item = layoutManager_items.findFirstVisibleItemPosition();
                 if (dx > 0) {
                     Log.d("recylcerview:", "first: "+String.valueOf(layoutManager_items.findFirstVisibleItemPosition()));
                     Log.d("recylcerview:", "last: "+String.valueOf(layoutManager_items.findLastVisibleItemPosition()));
                     Log.d("recylcerview", "first completely: "+String.valueOf(layoutManager_items.findFirstCompletelyVisibleItemPosition()));
                     Log.d("recylcerview", "last completely: "+String.valueOf(layoutManager_items.findLastCompletelyVisibleItemPosition()));
                     Log.d("Scrolling", "Scrolling UP");
                    // Scrolling up
                } else {
                    Log.d("Scrolling", "Scrolling DOWN");
                    // Scrolling down
                }
                 if(layoutManager_items.findFirstCompletelyVisibleItemPosition())!=-1)
            }
        });
        item_adapter = new RV_controll_items_adapter(this, items_list);
        recyclerView_items.setAdapter(item_adapter);
        // Scroll to next Item
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView_items);


        // Fragment Manager
        /*Fragment selectedFragment = null;
        selectedFragment = new controll_dekolampe();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        item_dekolampe.setBackgroundResource(R.drawable.bg_lamp_menu_blue);*/


        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void OnScrolledListener(View view, int position) {
        Log.d("OnScrolledListener", "OnScrolledListener: "+ position);
        layoutManager_items.scrollToPosition(position);
    }

    void set_text_activ_devices() {
        //textView_activ_devices.setText("");
        //search in db for all on switches
    }


}
