package edu.utsa.threadly.module.Database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "outfits",
        foreignKeys = @ForeignKey(
                entity = Closet.class,
                parentColumns = "closetId",
                childColumns = "closetOwnerId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = @Index("closetOwnerId")
)
public class Outfit {
    @PrimaryKey (autoGenerate = true)
    public int outfitId;
    public String name;
    public int closetOwnerId; //Foreign key to closet
}
