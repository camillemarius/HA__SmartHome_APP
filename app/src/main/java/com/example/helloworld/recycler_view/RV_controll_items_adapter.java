package com.example.helloworld.recycler_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloworld.custom_ui_elements.CustomSeekbar;
import com.example.helloworld.custom_ui_elements.CustomSwitch;
import com.example.helloworld.R;

import java.util.ArrayList;

public class RV_controll_items_adapter extends RecyclerView.Adapter<RV_controll_items_adapter.ViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<RV_controll_items_data> list_items;
    private Context context;

    public RV_controll_items_adapter(Context context, ArrayList<RV_controll_items_data> list_items) {
        this.mInflater = LayoutInflater.from(context);
        this.list_items = list_items;
    }


    private ItemListener itemListener;




    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_lampe;

        TextView textView_main_title;
        TextView textView_connectivity_title;
        CustomSwitch switch_lampe_state;

        TextView title_brightness_title;
        CustomSeekbar customSeekBar_brightness_level;

        TextView textView_title_colour;
        ImageView colorWheel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //------------------------------------------------------------------------------------//
            imageView_lampe = itemView.findViewById(R.id.imageView_lampe);

            //------------------------------------------------------------------------------------//
            textView_main_title = itemView.findViewById(R.id.textView_main_title);
            textView_connectivity_title = itemView.findViewById(R.id.textView_connectivity_title);
            switch_lampe_state = itemView.findViewById(R.id.customSwitch_lampe_state);

            //------------------------------------------------------------------------------------//
            title_brightness_title = itemView.findViewById(R.id.title_brightness_title);
            customSeekBar_brightness_level = itemView.findViewById(R.id.customSeekBar_brightness_level);

            //------------------------------------------------------------------------------------//
            textView_title_colour = itemView.findViewById(R.id.textView_title_colour);
            colorWheel = itemView.findViewById(R.id.customcolorWheel);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rv_controll_lamps, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.imageView_lampe.setImageDrawable(list_items.get(position).drawable_image);


        holder.textView_main_title.setText(list_items.get(position).main_title);
        holder.textView_connectivity_title.setText(list_items.get(position).connectivity_title);
        holder.switch_lampe_state.setChecked(list_items.get(position).lampe_state);
        holder.switch_lampe_state.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean state) {
                if (itemListener != null) {
                    itemListener.onStateCheckedChanged(state, holder.getAdapterPosition());
                }
            }
        });


        holder.title_brightness_title.setVisibility(list_items.get(position).brightness_visible);
        holder.title_brightness_title.setText(list_items.get(position).brightness_title);
        holder.customSeekBar_brightness_level.setVisibility(list_items.get(position).brightness_visible);
        holder.customSeekBar_brightness_level.setProgress(list_items.get(position).brightness_level);
        holder.customSeekBar_brightness_level.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        holder.customSeekBar_brightness_level.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (itemListener != null) {
                    itemListener.onProgressBarChangeListener(i, holder.getAdapterPosition());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        holder.textView_title_colour.setVisibility(list_items.get(position).colour_visible);
        holder.textView_title_colour.setText(list_items.get(position).title_colour);
        holder.colorWheel.setVisibility(list_items.get(position).colour_visible);
        holder.colorWheel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);

                int viewX = (int) motionEvent.getX();
                int viewY = (int) motionEvent.getY();

                int viewWidth = view.getWidth();
                int viewHeight = view.getHeight();

                Bitmap image = ((BitmapDrawable)holder.colorWheel.getDrawable()).getBitmap();

                int imageWidth = image.getWidth();
                int imageHeight = image.getHeight();

                int imageX = (int)((float)viewX * ((float)imageWidth / (float)viewWidth));
                int imageY = (int)((float)viewY * ((float)imageHeight / (float)viewHeight));

                int currPixel = image.getPixel(imageX, imageY);

                Log.d("Coordinates", "(" + String.valueOf(Color.red(currPixel)) + ", " + String.valueOf(Color.blue(currPixel)) + ", " + String.valueOf(Color.green(currPixel)) + ") Pixel is: " + currPixel);

                if (itemListener != null) {
                    itemListener.OnColorWheelChanged(imageX, imageY, currPixel, holder.getAdapterPosition());
                }
                //notifyDataSetChanged();

                return false;
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof LinearLayoutManager && getItemCount() > 0) {
            LinearLayoutManager llm = (LinearLayoutManager) manager;
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                int adapter_position_last=0, adapter_position_after=0;
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    switch(newState) {
                        case RecyclerView.SCROLL_STATE_SETTLING:
                            adapter_position_last = adapter_position_after;
                            adapter_position_after = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                            //adapter_position_after = llm.findFirstVisibleItemPosition();
                            Log.d("RV", "adapter_position_last: "+ adapter_position_last+" adapter_position_after: "+adapter_position_after);
                            if(adapter_position_last!=adapter_position_after) {
                                Log.d("RV", "Scrolled");
                            }
                            break;
                    }

                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int visiblePosition = llm.findFirstCompletelyVisibleItemPosition();
                    if(visiblePosition > -1) {
                        //View v = llm.findViewByPosition(visiblePosition);
                        //do something
                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return list_items.size();
    }

    // allows clicks events to be caught
    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public interface ItemListener {
        void OnColorWheelChanged(int x, int y, int color, int position);
        void onStateCheckedChanged(Boolean state, int position);
        void onProgressBarChangeListener(int progress, int position);
    }
}
