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


    RV_scene_adapter scene_adapter;
    LinearLayoutManager layoutManager_menu;

    String scene_1_title = "Romantik";
    String scene_2_title = "Hell";
    String scene_3_title = "Produktiv";
    String scene_4_title = "Nacht";
    String scene_5_title = "Sonnenuntergang";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mode_view);

        ArrayList<RV_scene_data> scene_list = new ArrayList<>();
        RV_scene_data scene_item = new RV_scene_data(
            scene_1_title,"4 Geräte",ContextCompat.getDrawable(this,R.drawable.theme_chill_2));
        scene_list.add(scene_item);

        scene_item = new RV_scene_data(
            scene_2_title,"2 Geräte", ContextCompat.getDrawable(this,R.drawable.theme_night_2));
        scene_list.add(scene_item);

        scene_item = new RV_scene_data(
            scene_3_title,"1 Geräte", ContextCompat.getDrawable(this,R.drawable.theme_romantic_2));
        scene_list.add(scene_item);

        scene_item = new RV_scene_data(
            scene_4_title,"2 Geräte", ContextCompat.getDrawable(this,R.drawable.theme_sonnenuntergang_2));
        scene_list.add(scene_item);

        scene_item = new RV_scene_data(
            scene_5_title,"1 Gerät", ContextCompat.getDrawable(this,R.drawable.theme_chill_2));
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
        switch(position) {
            case 0:
                intent.putExtra("EXTRA_THEME_TITLE", scene_1_title);
                break;

            case 1:
                intent.putExtra("EXTRA_THEME_TITLE", scene_2_title);
                break;

            case 2:
                intent.putExtra("EXTRA_THEME_TITLE", scene_3_title);
                break;

            case 3:
                intent.putExtra("EXTRA_THEME_TITLE", scene_4_title);
                break;

            case 4:
                intent.putExtra("EXTRA_THEME_TITLE", scene_5_title);
                break;
        }
        startActivity(intent);
    }
}
