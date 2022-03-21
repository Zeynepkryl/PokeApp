package com.example.pokeapp.Repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.pokeapp.Models.Pokemon;

import com.example.pokeapp.RoomDatabase.PokemonRoomDatabase;
import com.example.pokeapp.RoomDatabase.tmp_Dao;

import java.util.List;

public class PokemonRepository {

    private tmp_Dao dao;
    private PokemonRoomDatabase database;

    public PokemonRepository(Context context) {
        database = PokemonRoomDatabase.getInstance(context);
        dao = database.favoriteDao();

    }

    public void insertPokemon(Pokemon inpokemon) {
        PokemonRoomDatabase.databaseWriterService.execute(new Runnable() {
            @Override
            public void run() {
                dao.InsertFavoriteMovie(inpokemon);
            }
        });
    }

    public void deletePokemon(Pokemon dpokemon) {
        PokemonRoomDatabase.databaseWriterService.execute(new Runnable() {
            @Override
            public void run() {
                dao.DeleteFavoriteMovie(dpokemon);
            }
        });
    }

    public LiveData<List<Pokemon>> getAllPokemon() {
        return dao.getAllFavorites();
    }

    public LiveData<Pokemon> getFavoritePokemon(int id) {
        return dao.getFavoritePokemon(id);
    }


}
