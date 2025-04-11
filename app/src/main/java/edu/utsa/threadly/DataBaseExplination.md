# Closet App Room Database Setup

This document explains how to set up a Room database for an Android Closet app that supports:

- Multiple **Closets**
- Each Closet has multiple **Outfits**
- Each Outfit has multiple **ClothingItems**

---

## Database Relationships

### Closet ➝ Outfit (One-to-Many)
Each Closet has an ID (`closetId`).
Each Outfit stores that ID as a foreign key (`closetOwnerId`) to identify which Closet it belongs to.

### Outfit ➝ ClothingItem (One-to-Many)
Each Outfit has an ID (`outfitId`).
Each ClothingItem stores that ID as a foreign key (`outfitOwnerId`) to identify which Outfit it belongs to.

---

## Entity Classes

### Closet
```
java
@Entity(tableName = "closets")
public class Closet {
    @PrimaryKey(autoGenerate = true)
    public int closetId;

    public String name;
}
```

### Outfit
```
java
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
    @PrimaryKey(autoGenerate = true)
    public int outfitId;

    public String name;
    public int closetOwnerId;
}
```

### ClothingItem
```
java
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
    public int outfitOwnerId;
}
```

---

## Data Classes for Relations

### ClosetWithOutfits
```
java
public class ClosetWithOutfits {
    @Embedded
    public Closet closet;

    @Relation(
        parentColumn = "closetId",
        entityColumn = "closetOwnerId"
    )
    public List<Outfit> outfits;
}
```

### OutfitWithItems
```
java
public class OutfitWithItems {
    @Embedded
    public Outfit outfit;

    @Relation(
        parentColumn = "outfitId",
        entityColumn = "outfitOwnerId"
    )
    public List<ClothingItem> clothingItems;
}
```

---

## DAO Interface
```
java
@Dao
public interface ClosetDao {
    @Insert void insertCloset(Closet closet);
    @Insert void insertOutfit(Outfit outfit);
    @Insert void insertItem(ClothingItem item);

    @Transaction
    @Query("SELECT * FROM closets")
    List<ClosetWithOutfits> getClosetsWithOutfits();

    @Transaction
    @Query("SELECT * FROM outfits WHERE outfitId = :outfitId")
    OutfitWithItems getOutfitWithItems(int outfitId);
}
```

---

## Room Database
```java
@Database(entities = {Closet.class, Outfit.class, ClothingItem.class}, version = 1)
public abstract class ClosetDatabase extends RoomDatabase {
    public abstract ClosetDao closetDao();
}
```

---

## Example Usage
```
java
Closet closet = new Closet();
closet.name = "Summer Closet";

Outfit outfit = new Outfit();
outfit.name = "Beach Vibes";
outfit.closetOwnerId = 1;

ClothingItem item = new ClothingItem();
item.name = "Hawaiian Shirt";
item.category = "Top";
item.imageUri = "content://...";
item.outfitOwnerId = 1;

new Thread(() -> {
    ClosetDao dao = DatabaseClient.getInstance(context).getDatabase().closetDao();
    dao.insertCloset(closet);
    dao.insertOutfit(outfit);
    dao.insertItem(item);
}).start();
```

---

## Why Use This Setup
- Keeps data structured and normalized
- Avoids duplication of data
- Enables efficient querying with Room's `@Relation`
- Automatically cascades deletes to child tables when a parent is deleted

---

## Next Steps
- Add ViewModel + Repository for clean architecture
- Use LiveData or Flow for observing changes in the UI
- Add filtering/searching by category, closet, or outfit

