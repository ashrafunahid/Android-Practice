package com.ashrafunahid.eshop.models;

public class RecommendedModels {
    String name, description, price, rating, img_url;

    public RecommendedModels() {

    }

    public RecommendedModels(String name, String description, String price, String rating, String img_url) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
