package com.tothon.layarperak.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

        ImageView imageView = view.findViewById(R.id.iv);
        TextView textView = view.findViewById(R.id.tv_image_description);
        RelativeLayout layout = view.findViewById(R.id.layout);

        Image image = images.get(position);
        String filePath = image.getFilePath();
        String description = "";
        if (image.getMedia() != null) {
            if (image.getMediaType().equals("movie")) {
                description = image.getMedia().getTitle() + " (" + image.getMedia().getDate().substring(0,4) + ")";
            } else if (image.getMediaType().equals("tv")) {
                description = image.getMedia().getTitle() + " (" + image.getMedia().getFirstAirdate().substring(0,4) + ")";
            }
        }
        Context context = imageView.getContext();
        int width = DisplayUtility.getScreenWidth(context);
        fullScreenImageLoader.loadFullScreenImage(imageView, filePath, textView, description, width, layout);
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
        void loadFullScreenImage(ImageView iv, String imageUrl, TextView textView, String description,
                                 int width, RelativeLayout layout);
    }
    public void setFullScreenImageLoader(FullScreenImageLoader loader) {
        this.fullScreenImageLoader = loader;
    }
}
