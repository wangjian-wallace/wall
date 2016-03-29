package com.example.wallace.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

/**
 * 废弃
 */
@Deprecated
public class MainActivity extends Activity {

    private ViewPager imageView;
    private TextView textView;
    private RequestQueue mQueue;
    private ArrayList<String> list;
    private ArrayList<Bitmap> data;
    private List<ImageView> mImageViews = new ArrayList<ImageView>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getData();
        mQueue = Volley.newRequestQueue(this);
        imageView = (ViewPager) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.tvText);


        new Thread(runnable).start();
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            data = new ArrayList<>();
            int i = 0;
            final ArrayList<String> list = (ArrayList<String>) msg.obj;
            for (final String result : list){
                final int index = i;
                ImageRequest imageRequest = new ImageRequest(result, new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        data.add(response);
                        if (index == (list.size()-1)){
                            Message message = new Message();
                            message.obj = data;
                            mHandler.sendMessage(message);
                        }

                    }
                },0,0, ImageView.ScaleType.CENTER_CROP,Bitmap.Config.RGB_565,new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
                mQueue.add(imageRequest);
                i++;
            }
        }
    };
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final ArrayList<Bitmap> arrayList = (ArrayList<Bitmap>) msg.obj;
//            imageView.setImageBitmap(bitmap.get(6));
            for (Bitmap bitmap : arrayList){
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageBitmap(bitmap);
                mImageViews.add(imageView);
            }
            imageView.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int i) {
                    textView.setText((i+1)+"/"+arrayList.size());
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });
            imageView.setAdapter(new PagerAdapter() {
                @Override
                public Object instantiateItem(ViewGroup container, int position) {
                    container.addView(mImageViews.get(position));
                    return mImageViews.get(position);
                }

                @Override
                public void destroyItem(ViewGroup container, int position,
                                        Object object) {
                    container.removeView(mImageViews.get(position));
                }

                @Override
                public int getCount() {
                    return mImageViews.size();
                }

                @Override
                public boolean isViewFromObject(View view, Object o) {
                    return view == o;
                }
            });

        }
    };

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Message message = new Message();
            message.obj = list;
            handler.sendMessage(message);
        }
    };
    private void getData(){
        list = new ArrayList<>();
        list.add("http://pic.mmfile.net/2015/01/27t02.jpg");
        list.add("http://pic.mmfile.net/2015/01/27t03.jpg");
        list.add("http://pic.mmfile.net/2015/01/27t04.jpg");
        list.add("http://pic.mmfile.net/2015/01/27t05.jpg");
        list.add("http://pic.mmfile.net/2015/01/27t06.jpg");
        list.add("http://pic.mmfile.net/2015/01/27t08.jpg");
    }
}
