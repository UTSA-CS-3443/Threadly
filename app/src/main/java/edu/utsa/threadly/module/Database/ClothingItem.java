package edu.utsa.threadly.module.Database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "clothing_items",
        foreignKeys = @ForeignKey(
                entity = Outfit.class,
                parentColumns = "outfitId",
                childColumns = "outfitOwnerId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = @Index("outfitOwnerId")
)
public class ClothingItem {
        @PrimaryKey(autoGenerate = true)
        public int itemId;

        public String name;
        public String category;
        public String imageUri;

        public int outfitOwnerId; // Foreign key to Outfit
}
