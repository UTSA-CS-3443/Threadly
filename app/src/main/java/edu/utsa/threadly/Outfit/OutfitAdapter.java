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

import java.util.ArrayList;
import java.util.List;

import edu.utsa.threadly.R;

public class OutfitAdapter extends RecyclerView.Adapter<OutfitAdapter.OutfitViewHolder> {
    private List<String> outfitItems;
    private final Context context;

    public OutfitAdapter(Context context, List<String> outfitItems) {
        this.context = context;
        this.outfitItems = outfitItems;
    }

    public void updateData(ArrayList<String> newOutfits) {
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
        String item = outfitItems.get(position);
        holder.title.setText(item);

        // Set the position number in the circle
        holder.circle.setText(String.valueOf(position + 1));

        // Set OnClickListener to navigate to a new activity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, OutfitViewActivity.class);
            //fix intent to outfit view activity
            intent.putExtra("outfitName", item); // Pass the outfit name
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return outfitItems.size();
    }
}