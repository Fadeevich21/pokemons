package com.example.pokemons.Pokemon;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.pokemons.ImageRequester;
import com.example.pokemons.R;
import com.example.pokemons.RecyclerViewInterface;

public class PokemonAdapter extends Adapter<PokemonAdapter.PokemonViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    private final PokemonInfo[] pokemonInfo;

    public PokemonAdapter(PokemonInfo[] pokemonInfo, RecyclerViewInterface recyclerViewInterface) {
        this.pokemonInfo = pokemonInfo;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    public int getItemCount() {
        if (this.pokemonInfo == null)
            return 0;

        return this.pokemonInfo.length;
    }

    @NonNull
    public PokemonAdapter.PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                               int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new PokemonAdapter.PokemonViewHolder(layoutInflater.inflate(R.layout.item_content,
                parent, false), recyclerViewInterface);
    }

    public void onBindViewHolder(@NonNull PokemonAdapter.PokemonViewHolder holder, int position) {
        setData(holder, position);
    }

    private void setData(@NonNull PokemonViewHolder holder, int position) {
        setImage(holder, position);
        setName(holder, position);
        setNumber(holder, position);
        setHp(holder, position);
    }

    private void setImage(@NonNull PokemonViewHolder holder, int position) {
        String imageUrl = this.pokemonInfo[position].getImageUrl();
        ImageRequester requester = new ImageRequester();
        requester.execute(imageUrl, holder.image);
    }

    private void setName(@NonNull PokemonViewHolder holder, int position) {
        String name = this.pokemonInfo[position].getName();
        holder.name.setText(name);
    }

    @SuppressLint("DefaultLocale")
    private void setNumber(@NonNull PokemonViewHolder holder, int position) {
        int number = this.pokemonInfo[position].getNumber();
        holder.number.setText(String.format("№ %04d", number));
    }

    @SuppressLint("DefaultLocale")
    private void setHp(@NonNull PokemonViewHolder holder, int position) {
        int hp = this.pokemonInfo[position].getHp();
        holder.hp.setText(String.format("%d HP", hp));
    }


    static class PokemonViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView number;
        TextView hp;

        public PokemonViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            init(itemView);
            setOnClickListener(itemView, recyclerViewInterface);
        }

        private void init(@NonNull View itemView) {
            image = itemView.findViewById(R.id.item_content_img);
            name = itemView.findViewById(R.id.item_content_name);
            number = itemView.findViewById(R.id.item_content_number);
            hp = itemView.findViewById(R.id.item_content_hp);
        }

        private void setOnClickListener(@NonNull View itemView,
                                        RecyclerViewInterface recyclerViewInterface) {
            itemView.setOnClickListener(view -> {
                if (recyclerViewInterface != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(position);
                    }
                }
            });
        }
    }
}
