package com.example.wallace.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wallace.myapplication.adapter.TransformersAdapter;
import com.example.wallace.myapplication.listener.SwipeDismissRecyclerViewTouchListener;
import com.example.wallace.myapplication.transformers.ABaseTransformer;
import com.example.wallace.myapplication.transformers.AccordionTransformer;
import com.example.wallace.myapplication.transformers.BackgroundToForegroundTransformer;
import com.example.wallace.myapplication.transformers.CubeInTransformer;
import com.example.wallace.myapplication.transformers.CubeOutTransformer;
import com.example.wallace.myapplication.transformers.DefaultTransformer;
import com.example.wallace.myapplication.transformers.DepthPageTransformer;
import com.example.wallace.myapplication.transformers.FlipHorizontalTransformer;
import com.example.wallace.myapplication.transformers.FlipVerticalTransformer;
import com.example.wallace.myapplication.transformers.ForegroundToBackgroundTransformer;
import com.example.wallace.myapplication.transformers.RotateDownTransformer;
import com.example.wallace.myapplication.transformers.RotateUpTransformer;
import com.example.wallace.myapplication.transformers.StackTransformer;
import com.example.wallace.myapplication.transformers.ZoomInTransformer;
import com.example.wallace.myapplication.transformers.ZoomOutTransformer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TransformersActivity extends Activity implements AdapterView.OnItemClickListener{
    private ArrayList<String> transformerList;
    private ArrayList<Integer> localImages;
    private TransformersAdapter adapter;

    private RecyclerView lvList;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transformers);

        initDatas();

        initView();
    }

    private void initView() {
        lvList = (RecyclerView) findViewById(R.id.lvList);
        viewPager = (ViewPager) findViewById(R.id.vpView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, transformerList);
//        lvList.setAdapter(arrayAdapter);
//        lvList.setOnItemClickListener(this);
        final MyAdapter myAdapter = new MyAdapter(transformerList);


        lvList.setLayoutManager(layoutManager);
        lvList.setAdapter(myAdapter);

        adapter = new TransformersAdapter(this,localImages);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);



        //RecyclerView 滑动删除
        SwipeDismissRecyclerViewTouchListener listener = new SwipeDismissRecyclerViewTouchListener.Builder(
                lvList,
                new SwipeDismissRecyclerViewTouchListener.DismissCallbacks() {
                    @Override
                    public boolean canDismiss(int position) {
                        return true;
                    }

                    @Override
                    public void onDismiss(View view) {
                        int id = lvList.getChildPosition(view);
                        myAdapter.mDataset.remove(id);
                        myAdapter.notifyDataSetChanged();

                        Toast.makeText(getBaseContext(), String.format("Delete item %d", id), Toast.LENGTH_LONG).show();
                    }
                })
                .setIsVertical(false)
                .setItemTouchCallback(
                        new SwipeDismissRecyclerViewTouchListener.OnItemTouchCallBack() {
                            @Override
                            public void onTouch(int index) {
                                Toast.makeText(getBaseContext(), String.format("Click item %d", index), Toast.LENGTH_LONG).show();
                                String transformerName = transformerList.get(index);
                                Class cls;
                                try {
                                    cls = Class.forName("com.example.wallace.myapplication.transformers." + transformerName);
                                    ABaseTransformer transformer= (ABaseTransformer)cls.newInstance();
                                    viewPager.setPageTransformer(true,transformer);
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                } catch (InstantiationException e) {
                                    e.printStackTrace();
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                .create();

        lvList.setOnTouchListener(listener);
    }

    private void initDatas() {
        transformerList = new ArrayList<>();
        localImages = new ArrayList<>();

        for (int i = 1; i < 9; i++) {
            localImages.add(getResId("iv_" + i, R.drawable.class));
        }

        transformerList.add(DefaultTransformer.class.getSimpleName());
        transformerList.add(AccordionTransformer.class.getSimpleName());
        transformerList.add(BackgroundToForegroundTransformer.class.getSimpleName());
        transformerList.add(CubeInTransformer.class.getSimpleName());
        transformerList.add(CubeOutTransformer.class.getSimpleName());
        transformerList.add(DepthPageTransformer.class.getSimpleName());
        transformerList.add(FlipHorizontalTransformer.class.getSimpleName());
        transformerList.add(FlipVerticalTransformer.class.getSimpleName());
        transformerList.add(ForegroundToBackgroundTransformer.class.getSimpleName());
        transformerList.add(RotateDownTransformer.class.getSimpleName());
        transformerList.add(RotateUpTransformer.class.getSimpleName());
        transformerList.add(StackTransformer.class.getSimpleName());
        transformerList.add(ZoomInTransformer.class.getSimpleName());
        transformerList.add(ZoomOutTransformer.class.getSimpleName());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//        String transformerName = transformerList.get(position);
//        Class cls;
//        try {
//            cls = Class.forName("com.example.wallace.myapplication.transformers." + transformerName);
//            ABaseTransformer transformer= (ABaseTransformer)cls.newInstance();
//            viewPager.setPageTransformer(true,transformer);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
    }

    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

        public List<String> mDataset;

        public MyAdapter(List<String> dataset) {
            super();
            mDataset = dataset;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = View.inflate(viewGroup.getContext(), android.R.layout.simple_list_item_1, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            viewHolder.mTextView.setText(mDataset.get(i));
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            public TextView mTextView;
            public ViewHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView;
            }
        }
    }
}
