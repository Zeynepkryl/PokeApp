package com.example.pokeapp.data.Remote;

import com.example.pokeapp.Models.PokemonDetail;
import com.example.pokeapp.Models.PokemonDetailResponse;
import com.example.pokeapp.Models.PokemonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("pokemon")
    Call<PokemonResponse> getPokemons(@Query("limit") int limit, @Query("offset") int offset);

    @GET("pokemon/{name}")
    Call<PokemonDetail> getPokemonDetail( @Path("name") String name);

}
