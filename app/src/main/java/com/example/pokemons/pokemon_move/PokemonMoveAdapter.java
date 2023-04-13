package com.example.pokemons.pokemon_move;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.pokemons.R;

import java.util.ArrayList;

public class PokemonMoveAdapter extends Adapter<PokemonMoveViewHolder> {
    private final ArrayList<PokemonMove> pokemonMoves;

    public PokemonMoveAdapter(ArrayList<PokemonMove> pokemonMoves) {
        this.pokemonMoves = pokemonMoves;
    }

    public int getItemCount() {
        return this.pokemonMoves.size();
    }

    @NonNull
    public PokemonMoveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new PokemonMoveViewHolder(layoutInflater.inflate(R.layout.item_move, parent, false));
    }

    public void onBindViewHolder(@NonNull PokemonMoveViewHolder holder, int position) {
        PokemonMove pokemonMove = this.pokemonMoves.get(position);
        setData(holder, pokemonMove);
    }

    private void setData(@NonNull PokemonMoveViewHolder holder, PokemonMove pokemonMove) {
        setName(holder, pokemonMove);
        setPower(holder, pokemonMove);
        setType(holder, pokemonMove);
    }

    private void setName(@NonNull PokemonMoveViewHolder holder, PokemonMove pokemonMove) {
        String name = pokemonMove.getName();
        holder.name.setText(name);
    }

    private void setPower(@NonNull PokemonMoveViewHolder holder, PokemonMove pokemonMove) {
        String power = pokemonMove.getPower();
        holder.power.setText(power);
    }

    private void setType(@NonNull PokemonMoveViewHolder holder, PokemonMove pokemonMove) {
        String type = pokemonMove.getType();
        holder.type.setText(type);
    }
}
