package com.example.pokeapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokeapp.Adapter.PokemonAdapter;
import com.example.pokeapp.Models.Pokemon;
import com.example.pokeapp.R;
import com.example.pokeapp.Repository.PokemonRepository;
import com.example.pokeapp.ViewModel.PokemonViewModel;
import com.example.pokeapp.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private PokemonAdapter adapter;
    private PokemonViewModel viewModel;
    private List<Pokemon> pokemons;
    private PokemonRepository repository;
    private RecyclerView recyclerView;
    SearchView searchView;


    public MainActivity() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        searchView = findViewById(R.id.searchView);
        searchView.setQueryHint("Search...");


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });


        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FavoritesActivity.class));
            }
        });
        pokemons = new ArrayList<>();
        repository = new PokemonRepository(getApplicationContext());
        viewModel = new PokemonViewModel(getApplication(), repository);
        adapter = new PokemonAdapter(getApplicationContext(), pokemons, new PokemonAdapter.PokemonListener() {
            @Override
            public void clickItem(int position) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("name", pokemons.get(position).getName());
                intent.putExtra("id", pokemons.get(position).getId());

                startActivity(intent);

            }

            @Override
            public void onItemSavedClick(int position) {
                viewModel.insertPokemon(pokemons.get(position));
                /*Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
                startActivity(intent);*/
                Toast.makeText(getApplicationContext(), "Successfully Saved", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onDeletedClick(int position) {
                viewModel.deletePokemon(pokemons.get(position));
                Toast.makeText(getApplicationContext(), "Successfully Deleted", Toast.LENGTH_LONG).show();

            }
        });


        binding.pokemonList.setLayoutManager(new GridLayoutManager(this, 2));
        binding.pokemonList.setAdapter(adapter);


        viewModel.getPokemonList().observe(this, poke -> {
            pokemons.clear();
            pokemons.addAll(poke);

            adapter.notifyDataSetChanged();
        });

    }

}
