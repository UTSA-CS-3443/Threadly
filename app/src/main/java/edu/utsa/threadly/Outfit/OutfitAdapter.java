package edu.utsa.threadly.Outfit;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.utsa.threadly.R;
import edu.utsa.threadly.module.Outfit;

public class OutfitAdapter extends RecyclerView.Adapter<OutfitAdapter.OutfitViewHolder> {
    private List<Outfit> outfitItems;
    private final Context context;
    public OutfitAdapter(Context context, List<Outfit> outfitItems) {
        this.context = context;
        this.outfitItems = outfitItems;
    }

    public void updateData(List<Outfit> newOutfits) {
        Log.d("OutfitAdapter", "Updating adapter with " + newOutfits.size() + " items");
        this.outfitItems = newOutfits;
        notifyDataSetChanged();
    }

    public static class OutfitViewHolder extends RecyclerView.ViewHolder {
        TextView title, circle;

        public OutfitViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.outfit_title);
            circle = itemView.findViewById(R.id.outfit_circle);
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
        Outfit item = outfitItems.get(position);
        holder.title.setText(item.getName());
        holder.circle.setText(String.valueOf(position + 1));
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, OutfitViewActivity.class);
            intent.putExtra("outfitId", item.getOutfitId());
            intent.putExtra("outfitName", item.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return outfitItems.size();
    }
}