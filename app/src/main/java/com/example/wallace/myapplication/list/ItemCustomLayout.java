package com.example.wallace.myapplication.list;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * @author Wallace
 */
class ItemCustomLayout extends FrameLayout {
    private ImageView mBGImage;
    private Drawable mDrawable;
    private Drawable mTransparentDrawable;

    public ItemCustomLayout(Context context) {
        super(context);
        mBGImage = new ImageView(context);
        addView(mBGImage, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mTransparentDrawable = new ColorDrawable(Color.TRANSPARENT);
    }

    public void addCustomView(View customView) {
        if (customView.getLayoutParams() == null) {
            addView(customView, 1, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            addView(customView, 1, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, customView.getLayoutParams().height));
        }
    }

    public View getCustomView() {
        return getChildAt(1);
    }

    public View getRealView() {
        return this;
    }

    public void hideBackground() {
        mBGImage.setVisibility(GONE);
        Compat.setBackgroundDrawable(this, mTransparentDrawable);

    }

    public void showBackground() {
        mBGImage.setVisibility(VISIBLE);
        Compat.setBackgroundDrawable(this, mDrawable);

    }

    public void saveBackground(Drawable drawable) {
        mDrawable = drawable;
        Compat.setBackgroundDrawable(this, mDrawable);
        mBGImage.setImageDrawable(mDrawable);
        showBackground();
    }

    public boolean isBackgroundShowing() {
        return mDrawable == mBGImage.getDrawable();
    }

    public void refreshBackground() {
        hideBackground();
        showBackground();
    }
}
