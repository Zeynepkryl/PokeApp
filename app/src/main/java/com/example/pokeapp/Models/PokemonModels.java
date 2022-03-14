package com.example.pokeapp.Models;

import com.google.gson.annotations.SerializedName;

public class PokemonModels {
    @SerializedName("id")
    int id;
    @SerializedName("url")
    String url;
    @SerializedName("name")
    String name;

    boolean isFavori=false;

    public PokemonModels(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getFavori() {
        return isFavori;
    }

    public void setFavori(boolean favori) {
        isFavori = favori;
    }
}
