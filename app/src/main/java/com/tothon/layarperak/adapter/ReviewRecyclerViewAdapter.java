package com.tothon.layarperak.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tothon.layarperak.R;
import com.tothon.layarperak.model.Review;

import java.util.ArrayList;

import at.blogc.android.views.ExpandableTextView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewRecyclerViewAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Review> reviewArrayList;

    public ReviewRecyclerViewAdapter(Context context, ArrayList<Review> reviewArrayList) {
        this.context = context;
        this.reviewArrayList = reviewArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review = reviewArrayList.get(position);
        holder.tvAuthorName.setText(review.getAuthor());
        holder.tvContent.setText(review.getContent());
        holder.expandedButton.setOnClickListener(item -> {
            holder.expandedButton.setImageResource(holder.tvContent.isExpanded() ?
                    R.drawable.ic_expand_more_white_24dp :
                    R.drawable.ic_expand_less_white_24dp);
            holder.tvContent.toggle();
        });
        holder.reviewLayout.setOnClickListener(item -> {
            holder.expandedButton.setImageResource(holder.tvContent.isExpanded() ?
                    R.drawable.ic_expand_more_white_24dp :
                    R.drawable.ic_expand_less_white_24dp);
            holder.tvContent.toggle();
        });
    }

    @Override
    public int getItemCount() {
        return reviewArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_author)
        TextView tvAuthorName;
        @BindView(R.id.tv_content)
        ExpandableTextView tvContent;
        @BindView(R.id.btn_expanded)
        ImageButton expandedButton;
        @BindView(R.id.review_linear_layout)
        LinearLayout reviewLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            Typeface metrophobic = ResourcesCompat.getFont(context, R.font.metrophobic);
            tvContent.setTypeface(metrophobic);
            tvAuthorName.setTypeface(metrophobic);
        }
    }
}
