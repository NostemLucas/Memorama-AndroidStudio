package com.lemt.lemt_juegomemorama;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


public class ImageAdapter extends BaseAdapter {
    private final Context context;
    int[] drawable;
    int[] pos ;

    public ImageAdapter(Context context, int[] drawable,int[] pos) {
        this.context = context;
        this.drawable=drawable;
        this.pos=pos;

    }

    @Override
    public int getCount() {
        return 16;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Handler handler = new Handler();
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(this.context);
            imageView.setPadding(5,5,5,5);
            imageView.setLayoutParams(new GridView.LayoutParams(170, 280));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        else imageView = (ImageView)convertView;
        imageView.setImageResource(drawable[pos[position]]);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imageView.setImageResource(R.drawable.fondo);
            }
        }, 600);

        return imageView;
    }
}

