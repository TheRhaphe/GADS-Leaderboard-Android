package com.bmahatchi.gadsleaderboard.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bmahatchi.gadsleaderboard.models.Learner;
import com.bmahatchi.gadsleaderboard.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private final List<Learner> learnerList;
    private final boolean isLeadingLearners;

    RecyclerAdapter(List<Learner> learnerList, boolean isLeadingLearners) {
        this.learnerList = learnerList;
        this.isLeadingLearners = isLeadingLearners;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.learner_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Learner learner = learnerList.get(position);
        holder.name.setText(learner.getName());
        if (isLeadingLearners) {
            holder.details.setText(String.format(Locale.getDefault(), "%d learning hours, %s", learner.getHours(), learner.getCountry()));
        } else {
            holder.details.setText(String.format(Locale.getDefault(), "%d Skill IQ score, %s", learner.getScore(), learner.getCountry()));
        }
        Picasso.get().load(learner.getBadgeUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return learnerList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        TextView details;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.textViewName);
            details = itemView.findViewById(R.id.textViewDetails);
        }
    }
}
