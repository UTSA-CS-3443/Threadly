package edu.utsa.threadly.module.Datbase;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

public interface ClosetDao {
    // Insert
    @Insert
    void insertCloset(Closet closet);
    @Insert void insertOutfit(Outfit outfit);
    @Insert void insertItem(ClothingItem item);

    // Queries
    @Transaction
    @Query("SELECT * FROM closets")
    List<ClosetWithOutfits> getClosetsWithOutfits();

    @Transaction
    @Query("SELECT * FROM outfits WHERE outfitId = :outfitId")
    OutfitWithItems getOutfitWithItems(int outfitId);
}
