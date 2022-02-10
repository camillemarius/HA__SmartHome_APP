package com.example.helloworld.recycler_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloworld.R;

import java.util.ArrayList;

public class RV_scene_adapter extends RecyclerView.Adapter<RV_scene_adapter.ViewHolder> {
    private LayoutInflater mInflater;
    private ArrayList<RV_scene_data> list_items;
    private Context context;


    private SceneListener sceneListener;

    public RV_scene_adapter(Context context, ArrayList<RV_scene_data> list_items) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.list_items = list_items;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout item_scene;
        ImageView imageView_scene_image;
        TextView textView_scene_title;
        TextView textView_scene_device_cnt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_scene = itemView.findViewById(R.id.item_scene);
            imageView_scene_image = itemView.findViewById(R.id.imageView_scene_image);
            textView_scene_title = itemView.findViewById(R.id.textView_scene_title);
            textView_scene_device_cnt = itemView.findViewById(R.id.textView_scene_device_cnt);
        }
    }

    @NonNull
    @Override
    public RV_scene_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rv_item_scene, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RV_scene_adapter.ViewHolder holder, int position) {
        holder.item_scene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int shown_item_index = holder.getAdapterPosition();
                if (sceneListener != null) {
                    sceneListener.OnClickListener(view, shown_item_index);
                }
                notifyDataSetChanged();
            }
        });

        holder.textView_scene_title.setText(list_items.get(position).scene_title);
        holder.textView_scene_device_cnt.setText(list_items.get(position).scene_device_cnt);
        holder.imageView_scene_image.setImageDrawable(list_items.get(position).drawable_icon);
    }

    @Override
    public int getItemCount() {
        return list_items.size();
    }

    // allows clicks events to be caught
    public void setClickListener(SceneListener itemClickListener) {
        sceneListener = itemClickListener;
    }

    public interface SceneListener {
        void OnClickListener(View view, int position);
    }
}
