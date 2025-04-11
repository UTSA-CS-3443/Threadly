package edu.utsa.threadly.module.Datbase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "closets")
public class Closet {
    @PrimaryKey(autoGenerate = true)
    public int closetId;
    public String name;




}
