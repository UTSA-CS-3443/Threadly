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
        if (position == outfitItems.size()) {
            // Add button
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, AddClothingItemActivity.class);
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
            Intent intent = new Intent(context, OutfitViewActivity.class);
            intent.putExtra("outfitName", item.getName());
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