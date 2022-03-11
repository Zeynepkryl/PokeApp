package com.example.pokeapp.Models;

import java.util.ArrayList;

public class PokemonDetailResponse {
    private String url;
    private String name;
    private int weight;
    private int height;
    private PokemonDetail detailResults;


    public PokemonDetailResponse(String name, int weight, int height, PokemonDetail pokemonDetail, String url) {
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.url = url;

        this.detailResults = pokemonDetail;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public PokemonDetail getDetailResults() {
        return detailResults;
    }

    public void setDetailResults(PokemonDetail detailResults) {
        this.detailResults = detailResults;
    }
}
