package com.example.pokeapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "Pokemons")
public class Pokemon {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "url")
    private String url;
    @ColumnInfo(name = "name")
    private String name;

    boolean isFavori = false;

    public Pokemon(int id, String url, String name) {
        this.url = url;
        this.name = name;
        this.id = id;
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

    public boolean isFavori() {
        return isFavori;
    }

    public void setFavori(boolean favori) {
        isFavori = favori;
    }
}

