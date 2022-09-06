package com.kenzie.appserver.service.model;

public class Artwork {

    private String id;
    private String datePosted;
    private String artistName;
    private String title;
    private String dateCreated;
    private int height;
    private int width;
    private boolean sold;
    private boolean forSale;
    private int price;

    public Artwork() {
    }

    public Artwork(String id, String datePosted, String artistName, String title, String dateCreated, int height,
                   int width, boolean sold, boolean forSale, int price) {
        this.id = id;
        this.datePosted = datePosted;
        this.artistName = artistName;
        this.title = title;
        this.dateCreated = dateCreated;
        this.height = height;
        this.width = width;
        this.sold = sold;
        this.forSale = forSale;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getTitle() {
        return title;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean getSold() {
        return sold;
    }

    public boolean getForSale() {
        return forSale;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Artwork{" +
                "id='" + id + '\'' +
                ", datePosted='" + datePosted + '\'' +
                ", artistName='" + artistName + '\'' +
                ", title='" + title + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", height=" + height +
                ", width=" + width +
                ", sold=" + sold +
                ", forSale=" + forSale +
                ", price=" + price +
                '}';
    }
}
