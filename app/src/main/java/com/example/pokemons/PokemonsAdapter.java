package com.example.pokemons;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

public class PokemonsAdapter extends Adapter<com.example.pokemons.PokemonsAdapter.PokemonViewHolder> {
    private final PokemonItemContent[] pokemonItemContents;

    public PokemonsAdapter(PokemonItemContent[] pokemonItemContents) {
        this.pokemonItemContents = pokemonItemContents;
    }

    public int getItemCount() {
        return this.pokemonItemContents.length;
    }

    @NonNull
    public com.example.pokemons.PokemonsAdapter.PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new com.example.pokemons.PokemonsAdapter.PokemonViewHolder(layoutInflater.inflate(R.layout.item_content, parent, false));
    }

    @SuppressLint({"DefaultLocale"})
    public void onBindViewHolder(@NonNull com.example.pokemons.PokemonsAdapter.PokemonViewHolder holder, int position) {
        int imageId = this.pokemonItemContents[position].getImageId();
        holder.image.setImageResource(imageId);
        String name = this.pokemonItemContents[position].getName();
        holder.name.setText(name);
        int number = this.pokemonItemContents[position].getNumber();
        holder.number.setText(String.format("№ %04d", number));
        int hp = this.pokemonItemContents[position].getHp();
        holder.hp.setText(String.format("%d HP", hp));
    }

    static class PokemonViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView number;
        TextView hp;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            init(itemView);
        }

        private void init(@NonNull View itemView) {
            image = itemView.findViewById(R.id.item_content_img);
            name = itemView.findViewById(R.id.item_content_name);
            number = itemView.findViewById(R.id.item_content_number);
            hp = itemView.findViewById(R.id.item_content_hp);
        }
    }
}
