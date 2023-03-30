package com.example.pokemons.PokemonMove;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.pokemons.R;

public class PokemonMoveAdapter extends Adapter<PokemonMoveAdapter.PokemonMoveViewHolder> {
    private final PokemonMove[] pokemonMoves;

    public PokemonMoveAdapter(PokemonMove[] pokemonMoves) {
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

    public void onBindViewHolder(@NonNull PokemonMoveViewHolder holder, int position) {
        PokemonMove pokemonMove = this.pokemonMoves[position];
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
