package com.example.wallace.myapplication.transformers;

import android.view.View;

/**
 * Created by Wallace on 2016/3/29.
 */
public class DefaultTransformer extends ABaseTransformer {

    @Override
    protected void onTransform(View view, float position) {
    }

    @Override
    public boolean isPagingEnabled() {
        return true;
    }

}
