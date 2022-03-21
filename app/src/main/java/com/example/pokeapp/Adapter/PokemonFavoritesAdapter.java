package com.example.pokeapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pokeapp.Models.Pokemon;
import com.example.pokeapp.R;
import com.example.pokeapp.databinding.LayoutFavoritesItemPokemonsBinding;

import java.util.List;
import java.util.Objects;

import coil.Coil;
import coil.request.ImageRequest;
import coil.target.Target;

public class PokemonFavoritesAdapter extends RecyclerView.Adapter<PokemonFavoritesAdapter.ViewHolder> {
    private final Context context;
    private final List<Pokemon> pokemonFavList;
    private final OnItemClickListener listener;


    public PokemonFavoritesAdapter(Context context, List<Pokemon> pokemonFavList, OnItemClickListener listener) {
        this.context = context;
        this.pokemonFavList = pokemonFavList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_favorites_item_pokemons, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon pokemonItem = pokemonFavList.get(position);
        holder.setData(pokemonItem);

        Glide.with(context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemonItem.getId() + ".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.pokemonImageView);

        Coil.enqueue(new ImageRequest.Builder(context)
                .data("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemonItem.getId() + ".png")
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
                                holder.constraintLayoutRoot.setBackground(getGradientDrawable(p.getDominantSwatch().getRgb()));
                            }
                        });
                    }
                }).build());

    }

    private GradientDrawable getGradientDrawable(int color1) {
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{color1, ContextCompat.getColor(context, R.color.white)});
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        return gradientDrawable;
    }

    @Override
    public int getItemCount() {
        if (pokemonFavList == null)
            return 0;
        return pokemonFavList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LayoutFavoritesItemPokemonsBinding binding;
        ConstraintLayout constraintLayoutRoot;
        ImageView pokemonImageView;
        TextView nameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayoutRoot = itemView.findViewById(R.id.color_root);
            pokemonImageView = (ImageView) itemView.findViewById(R.id.favpokeImageView);
            nameTextView = itemView.findViewById(R.id.favnameTextView);
        }

        public void setData(Pokemon pokemon) {
            nameTextView.setText(pokemon.getName());
            Glide.with(context).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemon.getId() + ".png")
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(pokemonImageView);
            itemView.setOnClickListener(view -> {

                if (listener != null)
                    listener.onItemClick(getAdapterPosition());
            });
        }

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}

