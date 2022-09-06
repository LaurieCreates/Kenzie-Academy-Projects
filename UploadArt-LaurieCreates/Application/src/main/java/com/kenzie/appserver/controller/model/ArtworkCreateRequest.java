package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class ArtworkCreateRequest {

    @NotEmpty
    @JsonProperty("artistName")
    private String artistName;

    @NotEmpty
    @JsonProperty("title")
    private String title;

    @NotEmpty
    @JsonFormat(shape =  JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("dateCreated")
    private String dateCreated;

    //inches - height
    @NotEmpty
    @Min(0)
    @Max(240)
    @JsonProperty("height")
    private int height;

    //inches - width
    @NotEmpty
    @Min(0)
    @Max(240)
    @JsonProperty("width")
    private int width;

    @NotEmpty
    @JsonProperty("forSale")
    private boolean forSale;

    @Min(0)
    @JsonProperty("price")
    private int price;

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean forSale() {
        return forSale;
    }

    public void setForSale(boolean forSale) {
        this.forSale = forSale;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
