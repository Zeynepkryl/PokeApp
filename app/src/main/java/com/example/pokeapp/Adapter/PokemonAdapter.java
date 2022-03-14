package com.example.pokeapp.Adapter;

;


import android.content.Context;
import android.content.Intent;
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
import com.example.pokeapp.databinding.LayoutMainItemPokemonsBinding;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {
    private final List<Pokemon> pokemonList;
    private final Context context;
    private static PokemonListener listener;


    public PokemonAdapter(Context context, List<Pokemon> pokemonList, PokemonListener listener) {
        this.pokemonList = pokemonList;
        this.context = context;
        this.listener = listener;


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_main_item_pokemons, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pokemon pokemonItem = pokemonList.get(position);
        holder.setData(pokemonItem);

        Glide.with(context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemonItem.getId() + ".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.pokemonImageView);

    }


    @Override
    public int getItemCount() {
        if (pokemonList == null)
            return 0;
        return pokemonList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LayoutMainItemPokemonsBinding binding;
        ImageView pokemonImageView;
        TextView nameTextView;
        ImageView yildizView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pokemonImageView = (ImageView) itemView.findViewById(R.id.pokeImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            yildizView = itemView.findViewById(R.id.yildizView);

        }

        public void setData(Pokemon pokemonItem) {
            nameTextView.setText(pokemonItem.getName());

            System.out.println(pokemonItem.getFavori());

            itemView.setOnClickListener(view -> listener.clickItem(getAdapterPosition()));
            if (pokemonItem.getFavori())
                yildizView.setImageResource(R.drawable.fullstar);
            else
                yildizView.setImageResource(R.drawable.hollowwwstar);

            yildizView.setOnClickListener(view -> {

                if (!pokemonItem.getFavori()){
                    pokemonItem.setFavori(true);
                    listener.onItemSavedClick(getAdapterPosition());
                    yildizView.setImageResource(R.drawable.fullstar);
                }
                else{
                    pokemonItem.setFavori(false);
                    listener.onDeletedClick(getAdapterPosition());
                    yildizView.setImageResource(R.drawable.hollowwwstar);
                }
                pokemonItem.setFavori(!pokemonItem.getFavori());
                notifyDataSetChanged();
            });
        }
    }

    public interface PokemonListener {
        void clickItem(int position);
        void onItemSavedClick(int position);
        void onDeletedClick(int position);
    }
}
