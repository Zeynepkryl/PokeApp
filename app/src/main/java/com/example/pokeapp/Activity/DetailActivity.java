package com.example.pokeapp.Activity;

import android.graphics.Bitmap;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.example.pokeapp.Models.PokemonDetail;
import com.example.pokeapp.R;
import com.example.pokeapp.ViewModel.PokemonDetailViewModel;

import com.example.pokeapp.databinding.ActivityDetailBinding;

import java.util.ArrayList;
import java.util.List;

import coil.Coil;
import coil.request.ImageRequest;
import coil.target.Target;

public class DetailActivity extends AppCompatActivity {
    ActivityDetailBinding binding;
    private PokemonDetailViewModel viewModel;
    private List<PokemonDetail> detailList;


    public DetailActivity() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        detailList = new ArrayList<>();
        viewModel = new ViewModelProvider(this).get(PokemonDetailViewModel.class);


        String name = getIntent().getStringExtra("name");
        int id = getIntent().getIntExtra("id", 0);

        viewModel.getDetail(name).observe(this, pokemonDetail -> {

            binding.name.setText(pokemonDetail.getName());
            binding.baseExperience.setText(String.valueOf(pokemonDetail.getBaseExperience()));
            binding.isDefault.setText(String.valueOf(pokemonDetail.getIsDefault()));
            binding.locationAreaEncounters.setText(String.valueOf(pokemonDetail.getSpecies().getName()));
            binding.weight.setText(String.valueOf(pokemonDetail.getWeight()));
            binding.height.setText(String.valueOf(pokemonDetail.getHeight()));

            Glide.with(getApplicationContext()).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + id + ".png").into(binding.imgPokemon);

        });

        Coil.enqueue(new ImageRequest.Builder(getApplicationContext())
                .data("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + id + ".png")
                .target(new Target() {
                    @Override
                    public void onStart(@Nullable Drawable drawable) {

                    }

                    @Override
                    public void onError(@Nullable Drawable drawable) {

                    }

                    @Override
                    public void onSuccess(@NonNull Drawable drawable) {
                        BitmapDrawable drawBitmap = (BitmapDrawable) drawable;
                        Bitmap bmp = drawBitmap.getBitmap().copy(Bitmap.Config.ARGB_8888, true);
                        Palette.from(bmp).generate(p -> {
                            if (p.getDominantSwatch() != null) {
                                binding.imgPokemon.setBackground(getGradientDrawable(p.getDominantSwatch().getRgb()));
                            }
                        });
                    }
                }).build());
    }

    private GradientDrawable getGradientDrawable(int color1) {
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{color1, ContextCompat.getColor(getApplicationContext(), R.color.white)});
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(40f);
        return gradientDrawable;
    }

}