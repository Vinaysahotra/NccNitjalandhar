package com.example.nccnitjalandhar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


public class ImageAdapter extends BaseAdapter {

    private Context mContext;

    public ImageAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public int[]imagearray={R.drawable.ncc,R.drawable.ncc2,R.drawable.ncc3,R.drawable.download1,R.drawable.ncc4,
            R.drawable.ncc5,R.drawable.ncc6,R.drawable.ncc7,R.drawable.ncc8,R.drawable.ncc9,
            R.drawable.ncc10,R.drawable.ncc11,R.drawable.ncc12,R.drawable.ncc13};
    @Override
    public int getCount() {
        return imagearray.length;
    }

    @Override
    public Object getItem(int i) {
        return imagearray[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imagev =new ImageView(mContext);
        imagev.setImageResource(imagearray[i]);
        imagev.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imagev.setLayoutParams(new GridView.LayoutParams(250,300));

        return imagev;

    }
}
