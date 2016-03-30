package com.example.wallace.myapplication.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.wallace.myapplication.R;

import java.util.List;

/**
 * Created by Wallace on 2016/3/30.
 */
public class TransformersAdapter extends PagerAdapter {
    private List<Integer> list;
    private Context context;

    public TransformersAdapter(Context context,List<Integer> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView view = (ImageView) LayoutInflater.from(context).inflate(R.layout.view_image,container,false);
        view.setImageResource(list.get(position));
        container.addView(view);
        return view;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
