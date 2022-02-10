package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
import com.example.helloworld.recycler_view.RV_scene_adapter;
import com.example.helloworld.recycler_view.RV_scene_data;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RV_scene_adapter.SceneListener {
    LinearLayout item_deckenlampe, item_w_led, item_dekolampe, item_rgb_led, item_lichterkette;
    ImageView imageView_lampe;

    RV_controll_items_adapter item_adapter;
    RV_scene_adapter scene_adapter;
    LinearLayoutManager layoutManager_items;
    LinearLayoutManager layoutManager_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mode_view);

        ArrayList<RV_scene_data> scene_list = new ArrayList<>();
        RV_scene_data scene_item = new RV_scene_data(
            "Romantik","4 Geräte",ContextCompat.getDrawable(this,R.drawable.theme_chill_2));
        scene_list.add(scene_item);

        scene_item = new RV_scene_data(
            "Hell","2 Geräte", ContextCompat.getDrawable(this,R.drawable.theme_night_2));
        scene_list.add(scene_item);

        scene_item = new RV_scene_data(
            "Produktiv","1 Geräte", ContextCompat.getDrawable(this,R.drawable.theme_romantic_2));
        scene_list.add(scene_item);

        scene_item = new RV_scene_data(
            "Nacht","2 Geräte", ContextCompat.getDrawable(this,R.drawable.theme_sonnenuntergang_2));
        scene_list.add(scene_item);

        scene_item = new RV_scene_data(
            "Sonnenuntergang","1 Gerät", ContextCompat.getDrawable(this,R.drawable.theme_chill_2));
        scene_list.add(scene_item);

        //----------------------------------------------------------------------------------------//
        // set up the RecyclerView
        //----------------------------------------------------------------------------------------//
        layoutManager_menu = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView_menu = findViewById(R.id.recylerview_scenes);
        // endless scroll
        recyclerView_menu.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean loading = true;
            int pastVisiblesItems, visibleItemCount, totalItemCount;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) { //check for scroll down
                    visibleItemCount = layoutManager_menu.getChildCount();
                    totalItemCount = layoutManager_menu.getItemCount();
                    pastVisiblesItems = layoutManager_menu.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            Log.v("...", "Last Item Wow !");
                            // Do pagination.. i.e. fetch new data
                            loading = true;
                        }
                    }
                }
            }
        });
        recyclerView_menu.setLayoutManager(layoutManager_menu);
        scene_adapter = new RV_scene_adapter(this, scene_list);
        scene_adapter.setClickListener(this);
        recyclerView_menu.setAdapter(scene_adapter);
    }

    @Override
    public void OnClickListener(View view, int position) {
        Intent intent = new Intent(this, controll_lamps.class);
        startActivity(intent);
    }
}
