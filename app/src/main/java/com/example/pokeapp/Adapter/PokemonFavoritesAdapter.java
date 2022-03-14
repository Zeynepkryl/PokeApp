package com.example.pokeapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pokeapp.Models.Pokemon;
import com.example.pokeapp.R;
import com.example.pokeapp.databinding.LayoutFavoritesItemPokemonsBinding;

import java.util.List;

public class PokemonFavoritesAdapter extends RecyclerView.Adapter<PokemonFavoritesAdapter.ViewHolder> {
    private final Context context;
    private final List<Pokemon> pokemonList;
    private final OnItemClickListener listener;


    public PokemonFavoritesAdapter(Context context, List<Pokemon> pokemonList, OnItemClickListener listener) {
        this.context = context;
        this.pokemonList = pokemonList;
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
        Pokemon pokemonItem = pokemonList.get(position);
        holder.setData(pokemonItem);
    }

    @Override
    public int getItemCount() {
        if (pokemonList == null)
            return 0;
        return pokemonList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LayoutFavoritesItemPokemonsBinding binding;
        ImageView pokemonImageView;
        TextView nameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pokemonImageView = (ImageView) itemView.findViewById(R.id.favpokeImageView);
            nameTextView = itemView.findViewById(R.id.favnameTextView);
        }

        public void setData(Pokemon pokemon) {
            nameTextView.setText(pokemon.getName());
            Glide.with(context).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemon.getId() + ".png")
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(pokemonImageView);

            System.out.println("fav: " + pokemon.getFavori());
            System.out.println("id" + pokemon.getId());

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

