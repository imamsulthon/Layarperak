package com.tothon.layarperak.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.etiennelawlor.imagegallery.library.utilities.DisplayUtility;
import com.tothon.layarperak.R;
import com.tothon.layarperak.model.Image;

import java.util.ArrayList;

public class FullScreenImageAdapter extends PagerAdapter {

    private ArrayList<Image> images;
    private FullScreenImageLoader fullScreenImageLoader;

    public FullScreenImageAdapter(ArrayList<Image> images) {
        this.images = images;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) container.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fullscreen_image, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv);
        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.layout);

        String image = images.get(position).getFilePath();
        Context context = imageView.getContext();
        int width = DisplayUtility.getScreenWidth(context);
        fullScreenImageLoader.loadFullScreenImage(imageView, image, width, layout);
        container.addView(view, 0);

        return view;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public interface FullScreenImageLoader {
        void loadFullScreenImage(ImageView iv, String imageUrl, int width, RelativeLayout layout);
    }
    public void setFullScreenImageLoader(FullScreenImageLoader loader) {
        this.fullScreenImageLoader = loader;
    }
}
