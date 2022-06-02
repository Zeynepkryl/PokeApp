package com.example.pokeapp.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Pokemons")
public class Pokemon implements Cloneable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "url")
    public String url;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "isFavori")

    public boolean isFavori = false;

    public Pokemon(int id, String url, String name, boolean isFavori) {
        this.url = url;
        this.name = name;
        this.id = id;
        this.isFavori = isFavori;
    }

    public Pokemon() {

    }


    public int getId() {
        String[] urlPokemon = getUrl().split("/");
        return Integer.parseInt(urlPokemon[urlPokemon.length - 1]);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getFavori() {
        return isFavori;
    }

    public void setFavori(Boolean favori) {
        isFavori = favori;
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Pokemon clonnedPokemon = null;
        try {
            clonnedPokemon = (Pokemon) super.clone();
        } catch (Exception e) {

        }
        return clonnedPokemon;
    }
}

