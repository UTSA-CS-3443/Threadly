package edu.utsa.threadly.module.Database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ClosetWithOutfits {
    @Embedded
    public Closet closet;
    @Relation(
            parentColumn = "closetId",
            entityColumn = "closetOwnerId"
    )
    public List<Outfit> outfits;
}
