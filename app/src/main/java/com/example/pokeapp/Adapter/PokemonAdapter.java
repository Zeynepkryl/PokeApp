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
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pokeapp.Models.Pokemon;
import com.example.pokeapp.R;
import com.example.pokeapp.databinding.LayoutMainItemPokemonsBinding;

import org.w3c.dom.Text;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import coil.Coil;
import coil.request.ImageRequest;
import coil.target.Target;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> implements Filterable {
    private final List<Pokemon> pokemonList;
    private final Context context;
    private final PokemonListener listener;
    Boolean isFavorites = true;


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
        if (pokemonList == null)
            return 0;
        return pokemonList.size();
    }

    @Override
    public Filter getFilter() {
        return new PokemonFilter();
    }

    private class PokemonFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = pokemonList;
                results.count = pokemonList.size();
            } else {
                List<Pokemon> filteredPokemon = new ArrayList<>();
                for (Pokemon name : pokemonList) {
                    if (name.getName().toUpperCase().startsWith(constraint.toString().toUpperCase())) {
                        filteredPokemon.add(name);
                        results.values = filteredPokemon;
                        results.count = filteredPokemon.size();
                    }
                }
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<Pokemon> filtered = (List<Pokemon>) results.values;
            notifyDataSetChanged();
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private LayoutMainItemPokemonsBinding binding;
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
