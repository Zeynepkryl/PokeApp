package com.example.pokeapp.Models;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PokemonResult {
    private ArrayList<Pokemon> results;

    public PokemonResult() {

    }

    public PokemonResult(ArrayList<Pokemon> results) {
        this.results = results;

    }

    public ArrayList<Pokemon> getResults() {
        return results;
    }

    public void setResults(ArrayList<Pokemon> results) {
        this.results = results;
    }
}
