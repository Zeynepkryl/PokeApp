package com.example.pokeapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.pokeapp.Adapter.PokemonAdapter;
import com.example.pokeapp.Models.Pokemon;
import com.example.pokeapp.R;
import com.example.pokeapp.Repository.PokemonRepository;
import com.example.pokeapp.ViewModel.PokemonViewModel;
import com.example.pokeapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private PokemonAdapter adapter;
    private PokemonViewModel viewModel;
    private List<Pokemon> pokemonList;
    private RecyclerView recyclerView;
    private PokemonRepository repository;


    public MainActivity() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button favButton = findViewById(R.id.favbutton);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FavoritesActivity.class));
            }
        });

        pokemonList = new ArrayList<>();
        repository = new PokemonRepository(getApplicationContext());
        viewModel = new PokemonViewModel(getApplication(), repository);
        adapter = new PokemonAdapter(getApplicationContext(), pokemonList, new PokemonAdapter.PokemonListener() {
            @Override
            public void clickItem(int position) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("name", pokemonList.get(position).getName());
                intent.putExtra("id", pokemonList.get(position).getId());

                startActivity(intent);

            }

            @Override
            public void onItemSavedClick(int position) {
                viewModel.insertPokemon(pokemonList.get(position));
                /*Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
                startActivity(intent);*/
                Toast.makeText(getApplicationContext(), "Successfully Saved", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onDeletedClick(int position) {
                viewModel.deletePokemon(pokemonList.get(position));
                Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Successfully Deleted", Toast.LENGTH_LONG).show();

            }
        });


        binding.pokemonList.setLayoutManager(new GridLayoutManager(this, 2));
        binding.pokemonList.setAdapter(adapter);


        //binding.pokemonList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        viewModel.getPokemonList().observe(this, pokemonResult -> {

            pokemonList.clear();
            pokemonList.addAll(pokemonResult);

            adapter.notifyDataSetChanged();

        });

        viewModel.getAllPokemons().observe(this, new Observer<List<Pokemon>>() {

            @Override
            public void onChanged(List<Pokemon> pokemonList) {

            }
        });

    }
}

