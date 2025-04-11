package edu.utsa.threadly.module.Datbase;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
        tableName = "outfits",
        foreignKeys = @ForeignKey(
                entity = Closet.class,
                parentColumns = "closetId",
                childColumns = "closetOwnerId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = @Index("closetId")
)
public class Outfit {
    public int outfitId;
    public String name;
    public int closetOwnerId; //Foreign key to closet
}
