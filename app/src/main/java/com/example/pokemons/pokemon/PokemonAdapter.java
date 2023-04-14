package com.example.pokemons.pokemon;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.pokemons.LoadImageFromDatabase;
import com.example.pokemons.R;
import com.example.pokemons.RecyclerViewInterface;
import com.example.pokemons.database.entities.PokemonEntity;
import com.example.pokemons.download.ImageRequester;
import com.example.pokemons.network.NetworkConnect;

import java.util.ArrayList;

public class PokemonAdapter extends Adapter<PokemonViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    private ArrayList<PokemonEntity> pokemonEntities;

    public PokemonAdapter(ArrayList<PokemonEntity> pokemonInfo, RecyclerViewInterface recyclerViewInterface) {
        this.pokemonEntities = pokemonInfo;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filterList(ArrayList<PokemonEntity> filterList) {
        pokemonEntities = filterList;
        notifyDataSetChanged();
    }

    public ArrayList<PokemonEntity> getPokemonEntities() {
        return pokemonEntities;
    }

    public int getItemCount() {
        return this.pokemonEntities.size();
    }

    @NonNull
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new PokemonViewHolder(layoutInflater.inflate(R.layout.item_content,
                parent, false), recyclerViewInterface);
    }

    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        holder.progressBar.setVisibility(View.VISIBLE);
        holder.image.setVisibility(View.GONE);
        setData(holder, position);
    }

    private void setData(@NonNull PokemonViewHolder holder, int position) {
        setImage(holder, position);
        setName(holder, position);
        setNumber(holder, position);
        setHp(holder, position);
    }

    private void setImage(@NonNull PokemonViewHolder holder, int position) {
        PokemonEntity pokemonEntity = this.pokemonEntities.get(position);
        if (NetworkConnect.isConnected()) {
            holder.requester = new ImageRequester();
            holder.requester.execute(pokemonEntity.imageUrl, pokemonEntity.pokemonId);
        }
            holder.load = new LoadImageFromDatabase(holder.image, holder.progressBar);
            holder.load.execute(pokemonEntity.pokemonId);
    }

    private void setName(@NonNull PokemonViewHolder holder, int position) {
        String name = this.pokemonEntities.get(position).name;
        holder.name.setText(name);
    }

    @SuppressLint("DefaultLocale")
    private void setNumber(@NonNull PokemonViewHolder holder, int position) {
        int number = this.pokemonEntities.get(position).number;
        holder.number.setText(String.format("№ %04d", number));
    }

    @SuppressLint("DefaultLocale")
    private void setHp(@NonNull PokemonViewHolder holder, int position) {
        int hp = this.pokemonEntities.get(position).hp;
        holder.hp.setText(String.format("%d HP", hp));
    }

    @Override
    public void onViewRecycled(@NonNull PokemonViewHolder holder) {
        if (holder.load != null)
            holder.load.cancel(true);

        if (holder.requester != null)
            holder.requester.cancel(true);
    }
}
