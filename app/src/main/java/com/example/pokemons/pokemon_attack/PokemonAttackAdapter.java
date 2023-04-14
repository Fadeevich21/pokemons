package com.example.pokemons.pokemon_attack;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.pokemons.R;
import com.example.pokemons.database.entities.PokemonAttackEntity;

import java.util.ArrayList;

public class PokemonAttackAdapter extends Adapter<PokemonAttackViewHolder> {
    private final ArrayList<PokemonAttackEntity> pokemonAttacks;

    public PokemonAttackAdapter(ArrayList<PokemonAttackEntity> pokemonAttacks) {
        this.pokemonAttacks = pokemonAttacks;
    }

    public int getItemCount() {
        return this.pokemonAttacks.size();
    }

    @NonNull
    public PokemonAttackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new PokemonAttackViewHolder(layoutInflater.inflate(R.layout.item_move, parent, false));
    }

    public void onBindViewHolder(@NonNull PokemonAttackViewHolder holder, int position) {
        PokemonAttackEntity pokemonAttack = this.pokemonAttacks.get(position);
        setData(holder, pokemonAttack);
    }

    private void setData(@NonNull PokemonAttackViewHolder holder, PokemonAttackEntity pokemonAttack) {
        setName(holder, pokemonAttack);
        setPower(holder, pokemonAttack);
        setType(holder, pokemonAttack);
    }

    private void setName(@NonNull PokemonAttackViewHolder holder, PokemonAttackEntity pokemonAttack) {
        String name = pokemonAttack.name;
        holder.name.setText(name);
    }

    private void setPower(@NonNull PokemonAttackViewHolder holder, PokemonAttackEntity pokemonAttack) {
        String power = pokemonAttack.power;
        holder.power.setText(power);
    }

    private void setType(@NonNull PokemonAttackViewHolder holder, PokemonAttackEntity pokemonAttack) {
        String type = pokemonAttack.type;
        holder.type.setText(type);
    }
}
