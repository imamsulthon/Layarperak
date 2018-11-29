package com.tothon.layarperak.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;
import com.tothon.layarperak.R;
import com.tothon.layarperak.activity.PersonDetailsActivity;
import com.tothon.layarperak.model.Crew;
import com.tothon.layarperak.model.Department;
import com.tothon.layarperak.service.RetrofitAPI;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class FullCrewListAdapter extends ExpandableRecyclerViewAdapter<FullCrewListAdapter.DepartmentViewHolder,
        FullCrewListAdapter.CrewViewHolder> {

    private Context context;

    public FullCrewListAdapter(Context context, List<? extends ExpandableGroup> groups) {
        super(groups);
        this.context = context;
    }

    @Override
    public DepartmentViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_group_all_crew,
                parent, false);
        return new DepartmentViewHolder(view);
    }

    @Override
    public CrewViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_child_all_crew,
                parent, false);
        return new CrewViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(CrewViewHolder holder, int flatPosition, ExpandableGroup group,
                                      int childIndex) {
        Crew crew = (Crew) group.getItems().get(childIndex);
        holder.setCast(crew);
    }

    @Override
    public void onBindGroupViewHolder(DepartmentViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setDepartmentName(group);
    }

    public class DepartmentViewHolder extends GroupViewHolder {

        @BindView(R.id.tv_department)
        TextView departmentName;
        @BindView(R.id.tv_size)
        TextView size;
        @BindView(R.id.arrow)
        ImageView arrow;

        public DepartmentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setDepartmentName(ExpandableGroup group) {
            if (group instanceof Department) {
                departmentName.setText(group.getTitle());
                String itemSize = "(" + String.valueOf(((Department) group).getItemSize()) + ")";
                size.setText(itemSize);
            }
        }

        @Override
        public void expand() {
            animateExpand();
        }

        @Override
        public void collapse() {
            animateCollapse();
        }

        private void animateExpand() {
            RotateAnimation rotate = new RotateAnimation(360, 180, RELATIVE_TO_SELF,
                    0.5f, RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(300);
            rotate.setFillAfter(true);
            arrow.setAnimation(rotate);
        }

        private void animateCollapse() {
            RotateAnimation rotate = new RotateAnimation(180, 360, RELATIVE_TO_SELF,
                    0.5f, RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(300);
            rotate.setFillAfter(true);
            arrow.setAnimation(rotate);
        }
    }

    public class CrewViewHolder extends ChildViewHolder {

        @BindView(R.id.layout)
        RelativeLayout layout;
        @BindView(R.id.iv_thumbnail)
        ImageView ivPhotoProfile;
        @BindView(R.id.tv_name)
        TextView tvCrewName;
        @BindView(R.id.tv_job)
        TextView tvJob;

        public CrewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setCast(Crew crew) {
            Picasso.with(context)
                    .load(RetrofitAPI.POSTER_BASE_URL_SMALL + crew.getProfilePath())
                    .error(R.drawable.tmdb_placeholder)
                    .into(ivPhotoProfile);

            tvCrewName.setText(crew.getName());
            tvJob.setText(crew.getJob());

            layout.setOnClickListener(item -> {
                Intent intent = new Intent(context, PersonDetailsActivity.class);
                intent.putExtra(PersonDetailsActivity.KEY, crew);
                context.startActivity(intent);
            });
        }
    }
}
