package com.tothon.layarperak.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class DynamicImageView extends ImageView {

    private float ratio = 0;

    public DynamicImageView(Context context) {
        super(context);
    }

    public DynamicImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (ratio != 0) {
            int width = getMeasuredWidth();
            int height = (int) (ratio * width);
            setMeasuredDimension(width, height);
        }
    }
}
