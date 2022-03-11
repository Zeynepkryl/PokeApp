package com.example.pokeapp.Models;

import com.google.gson.annotations.SerializedName;

public class PokemonDetailModel {
    @SerializedName("id")
    int id;
    @SerializedName("url")
    String url;
    @SerializedName("name")
    String name;
    @SerializedName("weight")
    int weight;
    @SerializedName("height")
    int height;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
