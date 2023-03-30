package com.example.pokemons.PokemonMove;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.pokemons.R;

public class PokemonsMoveAdapter extends Adapter<PokemonsMoveAdapter.PokemonMoveViewHolder> {
    private final PokemonMove[] pokemonMoves;

    public PokemonsMoveAdapter(PokemonMove[] pokemonMoves) {
        this.pokemonMoves = pokemonMoves;
    }

    public int getItemCount() {
        return this.pokemonMoves.length;
    }

    @NonNull
    public PokemonMoveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new PokemonMoveViewHolder(layoutInflater.inflate(R.layout.item_move, parent, false));
    }

    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(@NonNull PokemonMoveViewHolder holder, int position) {
        PokemonMove pokemonMove = this.pokemonMoves[position];

        String name = pokemonMove.getName();
        holder.name.setText(name);

        String power = pokemonMove.getPower();
        holder.power.setText(power);

        String type = pokemonMove.getType();
        holder.type.setText(type);
    }

    static class PokemonMoveViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView power;
        TextView type;

        public PokemonMoveViewHolder(@NonNull View itemView) {
            super(itemView);
            init(itemView);
        }

        private void init(@NonNull View itemView) {
            name = itemView.findViewById(R.id.item_move_name);
            power = itemView.findViewById(R.id.item_move_power);
            type = itemView.findViewById(R.id.item_move_type);
        }
    }
}
