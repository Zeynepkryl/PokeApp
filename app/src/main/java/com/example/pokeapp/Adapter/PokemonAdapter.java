package com.example.pokeapp.Adapter;

;


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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.clickItem(getAdapterPosition());
                }
            });
            if (pokemonItem.isFavori())
                yildizView.setBackgroundResource(R.drawable.ic_baseline_star_24);
            else
                yildizView.setBackgroundResource(R.drawable.ic_baseline_star_border_24);

            yildizView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!pokemonItem.isFavori()){
                        pokemonItem.setFavori(true);
                        listener.onItemSavedClick(getAdapterPosition());
                        yildizView.setBackgroundResource(R.drawable.ic_baseline_star_24);


                    }
                    else{
                        pokemonItem.setFavori(false);
                        listener.onDeletedClick(getAdapterPosition());
                        yildizView.setBackgroundResource(R.drawable.ic_baseline_star_border_24);
                    }
                    pokemonItem.setFavori(!pokemonItem.isFavori());
                    notifyDataSetChanged();

                }
            });


        }


    }

    public interface PokemonListener {
        void clickItem(int position);
        void onItemSavedClick(int position);
        void onDeletedClick(int position);
    }
}
