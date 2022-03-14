package com.example.pokeapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pokeapp.Models.Pokemon;
import com.example.pokeapp.Models.PokemonModel;
import com.example.pokeapp.Models.PokemonModels;
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
        MutableLiveData<List<Pokemon>> pokemonList = new MutableLiveData<>();
        ApiService apiService = ApiClient.createApiClient().create(ApiService.class);
        apiService.getPokemons(20, 0).enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                if (response.isSuccessful())
                    pokemonList.setValue(response.body().getResults());

            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                t.getMessage();
            }
        });
        return pokemonList;

    }

    public void insertPokemon(Pokemon pokemon) {

        Pokemon pokemon1 = new Pokemon();
        pokemon1.setId(pokemon.getId());
        pokemon1.setName(pokemon.getName());
        pokemon1.setUrl(pokemon.getUrl());
        pokemon1.setFavori(true);

        System.out.println("favori: " + pokemon1.getFavori());
        repository.insertPokemon(pokemon1);
    }

    public void deletePokemon(Pokemon pokemon) {

        Pokemon pokemon1 = new Pokemon();
        pokemon.setId(pokemon.getId());
        pokemon.setName(pokemon.getName());
        pokemon.setUrl(pokemon.getUrl());
        pokemon.setFavori(false);


        repository.deletePokemon(pokemon1);
    }


    public LiveData<List<Pokemon>> getAllPokemons() {
        return repository.getAllPokemon();
    }

    public LiveData<Pokemon> getFavoritePokemon(int id) {
        return repository.getFavoritePokemon(id);
    }


}

