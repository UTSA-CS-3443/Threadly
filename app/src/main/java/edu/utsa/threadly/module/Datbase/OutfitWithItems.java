package edu.utsa.threadly.module.Datbase;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class OutfitWithItems {
    @Embedded
    public Outfit outfit;
    @Relation(
            parentColumn = "outfitId",
            entityColumn = "outfitOwnerId"
    )
    public List<ClothingItem> clothingItems;
}
