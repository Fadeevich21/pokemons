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

        int level = pokemonMove.getLevel();
        holder.level.setText(String.valueOf(level));

        String name = pokemonMove.getName();
        holder.name.setText(name);

        int power = pokemonMove.getPower();
        holder.power.setText(String.valueOf(power));

        int accuracy = pokemonMove.getAccuracy();
        holder.accuracy.setText(accuracy + "%");

        String type = pokemonMove.getType();
        holder.type.setText(type);

        String details = pokemonMove.getDetails();
        holder.details.setText(details);
    }

    static class PokemonMoveViewHolder extends RecyclerView.ViewHolder {
        TextView level;
        TextView name;
        TextView power;
        TextView accuracy;
        TextView type;
        TextView details;

        public PokemonMoveViewHolder(@NonNull View itemView) {
            super(itemView);
            init(itemView);
        }

        private void init(@NonNull View itemView) {
            level = itemView.findViewById(R.id.item_move_level);
            name = itemView.findViewById(R.id.item_move_name);
            power = itemView.findViewById(R.id.item_move_power);
            accuracy = itemView.findViewById(R.id.item_move_accuracy);
            type = itemView.findViewById(R.id.item_move_type);
            details = itemView.findViewById(R.id.item_move_details);
        }
    }
}
