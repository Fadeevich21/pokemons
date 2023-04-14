package com.example.pokemons.pokemon;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemons.LoadImageFromDatabase;
import com.example.pokemons.R;
import com.example.pokemons.RecyclerViewInterface;
import com.example.pokemons.download.ImageRequester;

public class PokemonViewHolder extends RecyclerView.ViewHolder {
    ImageView image;
    TextView name;
    TextView number;
    TextView hp;
    ProgressBar progressBar;
    ImageRequester requester;
    LoadImageFromDatabase load;

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
        progressBar = itemView.findViewById(R.id.progress_bar);
    }

    private void setOnClickListener(@NonNull View itemView,
                                    RecyclerViewInterface recyclerViewInterface) {
        itemView.setOnClickListener(view -> {
            if (recyclerViewInterface != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION)
                    recyclerViewInterface.onItemClick(position);
            }
        });
    }
}