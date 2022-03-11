package com.example.pokeapp.RoomDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pokeapp.Models.Pokemon;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Pokemon.class}, version = 1)
public abstract class PokemonRoomDatabase extends RoomDatabase {
    public static volatile PokemonRoomDatabase INSTANCE;

    public abstract tmp_Dao favoriteDao();

    public static final ExecutorService databaseWriterService = Executors.newFixedThreadPool(1);

    public static PokemonRoomDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (PokemonRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PokemonRoomDatabase.class, "Pokemon.db").build();

                }
            }
        }
        return INSTANCE;
    }
}
