package com.example.pokemons.pokemon_move;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemons.R;

public class PokemonMoveViewHolder extends RecyclerView.ViewHolder {
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