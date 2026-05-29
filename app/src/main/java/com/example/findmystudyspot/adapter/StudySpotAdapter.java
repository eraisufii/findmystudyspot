package com.example.findmystudyspot.adapter;

import android.content.Context;
import android.content.Intent;
import android.widget.Filter;
import android.widget.Filterable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmystudyspot.R;
import com.example.findmystudyspot.StudySpotDetailsActivity;
import com.example.findmystudyspot.model.StudySpot;

import java.util.ArrayList;

public class StudySpotAdapter extends RecyclerView.Adapter<StudySpotAdapter.ViewHolder>
        implements Filterable {

    Context context;

    ArrayList<StudySpot> list;
    ArrayList<StudySpot> filteredList;

    ArrayList<Integer> favoritePositions = new ArrayList<>();

    public StudySpotAdapter(Context context, ArrayList<StudySpot> list) {

        this.context = context;
        this.list = list;
        this.filteredList = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView spotImage;
        ImageView favoriteIcon;

        TextView spotName;
        TextView spotDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            spotImage = itemView.findViewById(R.id.spotImage);
            favoriteIcon = itemView.findViewById(R.id.favoriteIcon);

            spotName = itemView.findViewById(R.id.spotName);
            spotDescription = itemView.findViewById(R.id.spotDescription);
        }
    }

    @NonNull
    @Override
    public StudySpotAdapter.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_study_spot, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull StudySpotAdapter.ViewHolder holder,
            int position) {

        StudySpot spot = filteredList.get(position);

        holder.spotName.setText(spot.getName());
        holder.spotDescription.setText(spot.getDescription());
        holder.spotImage.setImageResource(spot.getImage());

        // FAVORITE ICON STATE
        if (favoritePositions.contains(position)) {

            holder.favoriteIcon.setImageResource(
                    R.drawable.baseline_favorite_border_24);

        } else {

            holder.favoriteIcon.setImageResource(
                    R.drawable.baseline_favorite_border_24);
        }

        // FAVORITE CLICK
        holder.favoriteIcon.setOnClickListener(v -> {

            int currentPosition = holder.getAdapterPosition();

            if (favoritePositions.contains(currentPosition)) {

                favoritePositions.remove(Integer.valueOf(currentPosition));

            } else {

                favoritePositions.add(currentPosition);
            }

            notifyItemChanged(currentPosition);
        });
        // OPEN DETAILS SCREEN
        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(
                    context,
                    StudySpotDetailsActivity.class
            );

            intent.putExtra("name", spot.getName());
            intent.putExtra("description", spot.getDescription());
            intent.putExtra("image", spot.getImage());

            intent.putExtra("lat", spot.getLatitude());
            intent.putExtra("lng", spot.getLongitude());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    // SEARCH FILTER
    @Override
    public Filter getFilter() {

        return filter;
    }

    Filter filter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            ArrayList<StudySpot> filteredTemp =
                    new ArrayList<>();

            if (constraint == null ||
                    constraint.length() == 0) {

                filteredTemp.addAll(list);

            } else {

                String searchText =
                        constraint.toString()
                                .toLowerCase()
                                .trim();

                for (StudySpot spot : list) {

                    if (spot.getName()
                            .toLowerCase()
                            .contains(searchText)) {

                        filteredTemp.add(spot);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredTemp;

            return results;
        }

        @Override
        protected void publishResults(
                CharSequence constraint,
                FilterResults results) {

            filteredList =
                    (ArrayList<StudySpot>) results.values;

            notifyDataSetChanged();
        }
    };
}