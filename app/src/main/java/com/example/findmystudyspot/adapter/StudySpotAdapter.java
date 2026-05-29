package com.example.findmystudyspot.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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

    private final Context context;
    private final ArrayList<StudySpot> list;
    private ArrayList<StudySpot> filteredList;
    private final ArrayList<Integer> favoritePositions = new ArrayList<>();
    private final SharedPreferences sharedPreferences;

    public StudySpotAdapter(Context context, ArrayList<StudySpot> list) {
        this.context = context;
        this.list = list;
        this.filteredList = list;

        this.sharedPreferences = context.getSharedPreferences("Favorites", Context.MODE_PRIVATE);

        for (int i = 0; i < list.size(); i++) {
            if (sharedPreferences.getBoolean(list.get(i).getName(), false)) {
                favoritePositions.add(i);
            }
        }
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
    public StudySpotAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_study_spot, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudySpotAdapter.ViewHolder holder, int position) {
        StudySpot spot = filteredList.get(position);

        holder.spotName.setText(spot.getName());
        holder.spotDescription.setText(spot.getDescription());
        holder.spotImage.setImageResource(spot.getImage());

        boolean isFavorited = sharedPreferences.getBoolean(spot.getName(), false);

        if (isFavorited) {
            holder.favoriteIcon.setImageResource(R.drawable.baseline_favorite_24);
        } else {
            holder.favoriteIcon.setImageResource(R.drawable.baseline_favorite_border_24);
        }

        holder.favoriteIcon.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            boolean dynamicState = sharedPreferences.getBoolean(spot.getName(), false);

            if (dynamicState) {
                editor.remove(spot.getName());
            } else {
                editor.putBoolean(spot.getName(), true);
            }

            editor.apply();
            notifyItemChanged(holder.getAdapterPosition());
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, StudySpotDetailsActivity.class);
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

    @Override
    public Filter getFilter() {
        return spotFilter;
    }

    private final Filter spotFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<StudySpot> filteredTemp = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredTemp.addAll(list);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (StudySpot spot : list) {
                    if (spot.getName().toLowerCase().contains(filterPattern) ||
                            spot.getDescription().toLowerCase().contains(filterPattern)) {
                        filteredTemp.add(spot);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredTemp;
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (ArrayList<StudySpot>) results.values;
            notifyDataSetChanged();
        }
    };
}