package com.example.pokeapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.pokeapp.Adapter.PokemonFavoritesAdapter;
import com.example.pokeapp.Models.Pokemon;
import com.example.pokeapp.R;
import com.example.pokeapp.Repository.PokemonRepository;
import com.example.pokeapp.ViewModel.PokemonFavoritesViewModel;
import com.example.pokeapp.databinding.ActivityFavoritesBinding;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {
    ActivityFavoritesBinding binding;
    private RecyclerView recyclerView;
    private PokemonFavoritesAdapter adapter;
    public List<Pokemon> pokemonList;
    PokemonFavoritesViewModel viewModel;
    PokemonRepository pokemonRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoritesBinding.inflate(getLayoutInflater());
        pokemonList = new ArrayList<>();
        setContentView(binding.getRoot());
        recyclerView = findViewById(R.id.favoritesRecyclerView);

        pokemonRepository = new PokemonRepository(getApplicationContext());
        viewModel = new PokemonFavoritesViewModel(getApplication(), pokemonRepository);
        getFavoritesPokemon();

        adapter = new PokemonFavoritesAdapter(getApplicationContext(), pokemonList, new PokemonFavoritesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {


            }
        });
        binding.favoritesRecyclerView.setAdapter(adapter);
        binding.favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void getFavoritesPokemon() {
        viewModel.getAllPokemon().observe(this, new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemonListFromDb) {
                pokemonList.clear();
                pokemonList.addAll(pokemonListFromDb);

                adapter.notifyDataSetChanged();

            }
        });

    }

}