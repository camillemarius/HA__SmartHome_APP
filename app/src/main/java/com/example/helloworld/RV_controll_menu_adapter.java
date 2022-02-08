package com.example.helloworld;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RV_controll_menu_adapter extends RecyclerView.Adapter<RV_controll_menu_adapter.ViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<RV_controll_menu_data> list_items;
    private Context context;
    private int shown_item_index = 0;

    private MenuListener menu_listener;

    RV_controll_menu_adapter(Context context, ArrayList<RV_controll_menu_data> list_items) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.list_items = list_items;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView_menu_title;
        ImageView imageView_menu_icon;
        LinearLayout item_deckenlampe;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //------------------------------------------------------------------------------------//
            textView_menu_title = itemView.findViewById(R.id.textView_menu_title);
            imageView_menu_icon = itemView.findViewById(R.id.imageView_menu_icon);
            item_deckenlampe = itemView.findViewById(R.id.item_deckenlampe);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rv_item_controll_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.item_deckenlampe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shown_item_index = holder.getAdapterPosition();

                if (menu_listener != null) {
                    menu_listener.OnScrolledListener(view, shown_item_index);
                }
                notifyDataSetChanged();
            }
        });

        if(shown_item_index==position){
            holder.item_deckenlampe.setBackgroundResource(R.drawable.bg_lamp_menu_blue);
        }
        else {
            holder.item_deckenlampe.setBackgroundResource(R.drawable.bg_lamp_menu_white);
        }
        int paddingDp = 10;
        float density = context.getResources().getDisplayMetrics().density;
        int paddingPixel = (int)(paddingDp * density);
        holder.item_deckenlampe.setPaddingRelative(paddingPixel,paddingPixel,paddingPixel,paddingPixel);


        holder.textView_menu_title.setText(list_items.get(position).main_title);
        holder.imageView_menu_icon.setImageDrawable(list_items.get(position).drawable_icon);
    }


    @Override
    public int getItemCount() {
        return list_items.size();
    }

    // allows clicks events to be caught
    public void setMenuListener(MenuListener itemClickListener) {
        menu_listener = itemClickListener;
    }

    public interface MenuListener {
        void OnScrolledListener(View view, int position);
    }
}
