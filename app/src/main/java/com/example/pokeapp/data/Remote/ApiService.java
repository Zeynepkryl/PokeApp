package com.example.pokeapp.data.Remote;

import com.example.pokeapp.Models.Example;
import com.example.pokeapp.Models.PokemonResponse;
import com.example.pokeapp.Models.Sprites;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("pokemon")
    Call<PokemonResponse> getPokemons(@Query("limit") int limit, @Query("offset") int offset);

    @GET("pokemon/{name}")
    Call<Example> getPokemonDetail(@Path("name") String name);

}
