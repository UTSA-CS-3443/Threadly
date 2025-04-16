package edu.utsa.threadly.Outfit;

public class OutfitItem {
    private final String title;
    private final String tags;
    private final int imageResId;

    public OutfitItem(String title, String tags, int imageResId) {
        this.title = title;
        this.tags = tags;
        this.imageResId = imageResId;
    }

    public String getTitle() { return title; }
    public String getTags() { return tags; }
    public int getImageResId() { return imageResId; }
}

