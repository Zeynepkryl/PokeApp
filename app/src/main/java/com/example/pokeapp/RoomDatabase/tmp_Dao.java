package com.example.pokeapp.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.pokeapp.Models.Pokemon;


import java.util.List;

@androidx.room.Dao
public interface tmp_Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertFavoriteMovie(Pokemon pokemon);

    @Delete()
    void DeleteFavoriteMovie(Pokemon pokemon);


    @Query("SELECT * FROM Pokemons")
    LiveData<List<Pokemon>> getAllFavorites();

    @Query("SELECT * FROM Pokemons Where id = :pokemonId")
    LiveData<Pokemon> getFavoritePokemon(int pokemonId);


}
