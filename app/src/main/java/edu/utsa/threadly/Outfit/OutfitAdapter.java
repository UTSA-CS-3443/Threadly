package edu.utsa.threadly.Outfit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.utsa.threadly.R;

public class OutfitAdapter extends RecyclerView.Adapter<OutfitAdapter.OutfitViewHolder> {
    private final List<OutfitItem> outfitItems;

    public OutfitAdapter(List<OutfitItem> outfitItems) {
        this.outfitItems = outfitItems;
    }

    public static class OutfitViewHolder extends RecyclerView.ViewHolder {
        TextView title, tags;
        ImageView image;

        public OutfitViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.outfit_title);
            tags = itemView.findViewById(R.id.outfit_tags);
            image = itemView.findViewById(R.id.outfit_image);
        }
    }

    @NonNull
    @Override
    public OutfitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_outfit_card, parent, false);
        return new OutfitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OutfitViewHolder holder, int position) {
        OutfitItem item = outfitItems.get(position);
        holder.title.setText(item.getTitle());
        holder.tags.setText(item.getTags());
        holder.image.setImageResource(item.getImageResId());
    }

    @Override
    public int getItemCount() {
        return outfitItems.size();
    }
}

