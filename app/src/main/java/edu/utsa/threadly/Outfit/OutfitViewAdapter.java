package edu.utsa.threadly.Outfit;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.utsa.threadly.R;
import edu.utsa.threadly.module.ClothingItem;

public class OutfitViewAdapter extends RecyclerView.Adapter<OutfitViewAdapter.OutfitViewHolder> {
    private List<ClothingItem> outfitItems;
    private final Context context;

    public OutfitViewAdapter(Context context, List<ClothingItem> outfitItems) {
        this.context = context;
        this.outfitItems = outfitItems;
    }

    public void updateData(List<ClothingItem> newOutfits) {
        Log.d("OutfitAdapter", "Updating adapter with " + newOutfits.size() + " items");
        this.outfitItems = newOutfits;
        notifyDataSetChanged();
    }

    public static class OutfitViewHolder extends RecyclerView.ViewHolder {
        ImageView carouselImageView;

        public OutfitViewHolder(@NonNull View itemView) {
            super(itemView);
            carouselImageView = itemView.findViewById(R.id.carousel_image_view);
        }
    }

    @NonNull
    @Override
    public OutfitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.carousel_item, parent, false);
        return new OutfitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OutfitViewHolder holder, int position) {
        ClothingItem item = outfitItems.get(position);

        // Example: Set a placeholder image or load an image dynamically
        holder.carouselImageView.setImageResource(R.drawable.ic_launcher_background);

        // Set OnClickListener to navigate to a new activity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, OutfitViewActivity.class);
            intent.putExtra("outfitName", item.getName()); // Pass the outfit name
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return outfitItems.size();
    }
}