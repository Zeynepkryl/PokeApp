package com.example.pokeapp.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pokeapp.Models.PokemonDetail;
import com.example.pokeapp.R;

import com.example.pokeapp.ViewModel.PokemonDetailViewModel;

import com.example.pokeapp.databinding.ActivityDetailBinding;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    ActivityDetailBinding binding;
    TextView nameText;
    TextView weightText;
    TextView heightText;
    ImageView url;
    RecyclerView recyclerView;
    private PokemonDetailViewModel viewModel;
    private List<PokemonDetail> detailList;


    public DetailActivity() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        nameText = findViewById(R.id.nameTextView);
        url = findViewById(R.id.url);
        weightText = findViewById(R.id.weight);
        heightText = findViewById(R.id.height);
        recyclerView = findViewById(R.id.pokemonList);


        detailList = new ArrayList<>();
        viewModel = new ViewModelProvider(this).get(PokemonDetailViewModel.class);


        String name = getIntent().getStringExtra("name");
        int id = getIntent().getIntExtra("id",0);


        viewModel.getDetail(name).observe(this, pokemonDetail -> {

            //ImageBindingAdapter.loadImage(binding.url, pokemonDetail.getUrl());
            binding.name.setText("" + pokemonDetail.getName());
            binding.height.setText("Height: " + String.valueOf(pokemonDetail.getHeight()));
            binding.weight.setText("Weight: " + String.valueOf(pokemonDetail.getWeight()));
            Glide.with(getApplicationContext()).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-ii/crystal/" + id + ".png").into(binding.url);
            System.out.println(url);


        });

    }
}




































        /*
        viewModel = new PokemonDetailViewModel(getApplication(), repository);
        //  viewModel = new ViewModelProvider(this).get(PokemonViewModel.class);

        binding.detailRecyclerview.setAdapter(adapter);
        binding.detailRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        adapter = new PokemonDetailAdapter(getApplicationContext(), detailList);
        Intent intent = getIntent();
        String name = intent.getExtras().getString("name");
        Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();


        viewModel.getDetail().observe(this, new Observer<List<PokemonDetail>>() {
            @Override
            public void onChanged(List<PokemonDetail> pokemonDetails) {
                detailList.clear();
                detailList.addAll(pokemonDetails);

                adapter.notifyDataSetChanged();
            }
        });


         */
























        /*
        viewModel.getAllDetail().observe(this, new Observer<List<PokemonDetail>>() {
            @Override
            public void onChanged(List<PokemonDetail> pokemonDetails) {

            }
        });


         */





