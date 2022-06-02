package com.example.pokeapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pokeapp.Models.Pokemon;
import com.example.pokeapp.Models.PokemonResponse;
import com.example.pokeapp.Repository.PokemonRepository;
import com.example.pokeapp.data.Remote.ApiClient;
import com.example.pokeapp.data.Remote.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonViewModel extends AndroidViewModel {
    private PokemonRepository repository;


    public PokemonViewModel(@NonNull Application application, PokemonRepository repository) {
        super(application);
        this.repository = repository;
    }

    public MutableLiveData<List<Pokemon>> getPokemonList() {
        MutableLiveData<List<Pokemon>> pokemonsList = new MutableLiveData<>();
        ApiService apiService = ApiClient.createApiClient().create(ApiService.class);
        apiService.getPokemons(20, 0).enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(@NonNull Call<PokemonResponse> call, Response<PokemonResponse> response) {
                if (response.isSuccessful())
                    pokemonsList.setValue(response.body().getResults());

            }

            @Override
            public void onFailure(@NonNull Call<PokemonResponse> call, Throwable t) {
                t.getMessage();
            }
        });
        return pokemonsList;

    }

    public void insertPokemon(Pokemon pokemon) {

        Pokemon insertPokemons = new Pokemon();
        insertPokemons.setId(pokemon.getId());
        insertPokemons.setName(pokemon.getName());
        insertPokemons.setUrl(pokemon.getUrl());

        repository.insertPokemon(insertPokemons);
    }

    public void deletePokemon(Pokemon pokemon) {

        Pokemon deletePokemons = new Pokemon();
        deletePokemons.setId(pokemon.getId());
        deletePokemons.setName(pokemon.getName());
        deletePokemons.setUrl(pokemon.getUrl());

        repository.deletePokemon(deletePokemons);
    }



}

