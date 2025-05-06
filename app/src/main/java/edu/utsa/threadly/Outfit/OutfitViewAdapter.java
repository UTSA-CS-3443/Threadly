package edu.utsa.threadly.Outfit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.utsa.threadly.ClothingItem.AddClothingItemActivity;
import edu.utsa.threadly.ClothingItem.ClothingItemViewActivity;
import edu.utsa.threadly.R;
import edu.utsa.threadly.module.ClothingItem;

/**
 * implements a recycler view for the outfit view cards
 */
public class OutfitViewAdapter extends RecyclerView.Adapter<OutfitViewAdapter.OutfitViewHolder> {
    private List<ClothingItem> outfitItems;
    private final Context context;
    private final String category;

    public OutfitViewAdapter(Context context, List<ClothingItem> outfitItems, String category) {
        this.context = context;
        this.outfitItems = outfitItems;
        this.category = category;
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
        View view;
        if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_card, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carousel_item, parent, false);
        }
        return new OutfitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OutfitViewHolder holder, int position) {


        Log.d("Position", "Position: " + position);

        if (position == outfitItems.size()) {
            Log.d("OutfitViewAdapter", "Binding add button");
            // Add button
            holder.itemView.setOnClickListener(v -> {
                Log.d("OutfitViewAdapter", "Add button clicked");
                Intent intent = new Intent(context, AddClothingItemActivity.class);
                intent.putExtra("outfitId", 1); // -1 indicates no outfit
                intent.putExtra("category", category); // Pass the category if needed
                context.startActivity(intent);
            });
            return;
        }

        // Regular clothing item
        ClothingItem item = outfitItems.get(position);
        String imageUriString = item.getPicture();


        try {
            if (imageUriString != null && !imageUriString.isEmpty()) {
                Uri imageUri = Uri.parse(imageUriString);
                if (imageUri.getScheme() != null && !imageUri.getScheme().isEmpty()) {
                    holder.carouselImageView.setImageURI(imageUri);
                } else {
                    int resId = context.getResources().getIdentifier(
                            imageUriString, "drawable", context.getPackageName());
                    holder.carouselImageView.setImageResource(resId != 0 ? resId : R.drawable.ic_launcher_background);
                }
            } else {
                holder.carouselImageView.setImageResource(R.drawable.ic_launcher_background);
            }
        } catch (Exception e) {
            Log.e("OutfitViewAdapter", "Image load failed", e);
            holder.carouselImageView.setImageResource(R.drawable.ic_launcher_background);
        }

        holder.itemView.setOnClickListener(v -> {
            Log.d("OutfitViewAdapter", "Item clicked: " + item.getName());
            Intent intent = new Intent(context, ClothingItemViewActivity.class);
            intent.putExtra("clothingItemName", item.getName());
            intent.putExtra("clothingItemCategory", item.getType());
            intent.putExtra("clothingItemImage", item.getPicture());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return outfitItems.size() + 1; // +1 for the "Add" button
    }

    @Override
    public int getItemViewType(int position) {
        return position == outfitItems.size() ? 1 : 0; // 1 for "Add" button, 0 for regular items
    }
}