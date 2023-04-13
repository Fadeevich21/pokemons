package com.example.pokemons.pokemon;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.pokemons.ImageRequester;
import com.example.pokemons.R;
import com.example.pokemons.RecyclerViewInterface;

import java.util.ArrayList;

public class PokemonAdapter extends Adapter<PokemonViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    private ArrayList<PokemonInfo> pokemonInfo;

    public PokemonAdapter(ArrayList<PokemonInfo> pokemonInfo, RecyclerViewInterface recyclerViewInterface) {
        this.pokemonInfo = pokemonInfo;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filterList(ArrayList<PokemonInfo> filterList) {
        pokemonInfo = filterList;
        notifyDataSetChanged();
    }

    public ArrayList<PokemonInfo> getPokemonInfo() {
        return pokemonInfo;
    }

    public int getItemCount() {
        return this.pokemonInfo.size();
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
        String imageUrl = this.pokemonInfo.get(position).getImageUrl();
        holder.requester = new ImageRequester();
        holder.requester.execute(imageUrl, holder.image, holder.progressBar);
    }

    private void setName(@NonNull PokemonViewHolder holder, int position) {
        String name = this.pokemonInfo.get(position).getName();
        holder.name.setText(name);
    }

    @SuppressLint("DefaultLocale")
    private void setNumber(@NonNull PokemonViewHolder holder, int position) {
        int number = this.pokemonInfo.get(position).getNumber();
        holder.number.setText(String.format("№ %04d", number));
    }

    @SuppressLint("DefaultLocale")
    private void setHp(@NonNull PokemonViewHolder holder, int position) {
        int hp = this.pokemonInfo.get(position).getHp();
        holder.hp.setText(String.format("%d HP", hp));
    }

    @Override
    public void onViewRecycled(@NonNull PokemonViewHolder holder) {
        holder.requester.cancel(true);
    }
}
