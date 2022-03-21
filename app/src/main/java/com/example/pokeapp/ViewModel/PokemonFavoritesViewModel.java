package com.example.pokeapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pokeapp.Models.Pokemon;
import com.example.pokeapp.Repository.PokemonRepository;

import java.util.List;

public class PokemonFavoritesViewModel extends AndroidViewModel {
    private PokemonRepository repository;

    public PokemonFavoritesViewModel(@NonNull Application application, PokemonRepository repository) {
        super(application);
        this.repository = repository;
    }

    public LiveData<List<Pokemon>> getAllPokemon() {
        return repository.getAllPokemon();
    }


}
