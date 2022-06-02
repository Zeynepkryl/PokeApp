package com.example.pokeapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokeapp.Models.Pokemon;
import com.example.pokeapp.R;


import java.util.ArrayList;
import java.util.List;

import coil.Coil;
import coil.request.ImageRequest;
import coil.target.Target;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> implements Filterable {
    List<Pokemon> pokemonList;
    private List<Pokemon> filteredPokemonList;
    private final Context context;
    private final PokemonListener listener;
    Boolean isFavorites = true;


    public PokemonAdapter(Context context, List<Pokemon> pokemonList, PokemonListener listener) {
        this.pokemonList = pokemonList;
        this.filteredPokemonList = pokemonList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pokemon pokemonItem = filteredPokemonList.get(position);
        holder.setData(pokemonItem);

        Coil.enqueue(new ImageRequest.Builder(context)

                .data("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemonItem.getId() + ".png")
                .target(new Target() {
                    @Override
                    public void onStart(@Nullable Drawable drawable) {//Data içindeki linke ait görseli draw.şek. bize getirir.

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
                        holder.pokemonImageView.setImageDrawable(drawable);
                    }
                }).build());

    }

    private GradientDrawable getGradientDrawable(int color) {
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{color, ContextCompat.getColor(context, R.color.white)});
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        return gradientDrawable;
    }

    @Override
    public int getItemCount() {
        if (filteredPokemonList == null)
            return 0;
        return filteredPokemonList.size();
    }

    @Override
    public Filter getFilter() {
        return new PokemonFilter();
    }

    private class PokemonFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();
            List<Pokemon> pokemons = pokemonList;
            int count = pokemons.size();
            List<Pokemon> poke = new ArrayList<>(count);

            if (constraint == null || constraint.length() == 0) {
                for (int i = 0; i < count; i++) {
                    Pokemon tempPoke = pokemonList.get(i);
                    poke.add(tempPoke);
                }
            } else {
                //filteredPokemonList.clear();
                for (Pokemon pokemon : pokemonList) {
                    if (pokemon.getName().toUpperCase().startsWith(constraint.toString().toUpperCase())) {
                        poke.add(pokemon);
                    }
                }
            }

            results.count = poke.size();
            results.values = poke;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredPokemonList = (List<Pokemon>) results.values;
            notifyDataSetChanged();
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout constraintLayoutRoot;
        ImageView pokemonImageView;
        TextView nameTextView;
        ImageView yildizView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayoutRoot = itemView.findViewById(R.id.cl_root);
            pokemonImageView = itemView.findViewById(R.id.pokeImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            yildizView = itemView.findViewById(R.id.yildizView);
            isFavorites = false;

        }

        public void setData(Pokemon pokemonItem) {
            nameTextView.setText(pokemonItem.getName());

            if (pokemonItem != null)
                itemView.setOnClickListener(view -> listener.clickItem(getLayoutPosition()));

            if (pokemonItem.getFavori())
                yildizView.setImageResource(R.drawable.ic_star_selected);

            else
                yildizView.setImageResource(R.drawable.ic_star);

            yildizView.setOnClickListener(view -> {

                if (!pokemonItem.getFavori()) {
                    pokemonItem.setFavori(false);
                    listener.onItemSavedClick(getLayoutPosition());
                    yildizView.setImageResource(R.drawable.ic_star_selected);
                } else {
                    pokemonItem.setFavori(true);
                    listener.onDeletedClick(getLayoutPosition());
                    yildizView.setImageResource(R.drawable.ic_star);
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
