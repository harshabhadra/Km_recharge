package com.formaxit.kmrecharge.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.formaxit.kmrecharge.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderViewHolder> {


    private static final String TAG = SliderAdapter.class.getSimpleName();
    private Context context;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflater = LayoutInflater.from(context).inflate(R.layout.slider_layout_item, null, false);
        return new SliderViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {

        Log.e(TAG,"Binding Views");
        switch (position) {
            case 0:
            case 1:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.rbanner_one)
                        .into(viewHolder.imageViewBackground);
                break;
            case 2:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.banner_1)
                        .into(viewHolder.imageViewBackground);
                break;
            case 3:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.banner_2)
                        .into(viewHolder.imageViewBackground);
                break;

        }
    }

    @Override
    public int getCount() {
        return 4;
    }


    class SliderViewHolder extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;

        public SliderViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.slider_image);
            this.itemView = itemView;
        }
    }
}
