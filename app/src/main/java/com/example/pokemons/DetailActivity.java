package com.example.pokemons;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    TextView nameView;
    TextView hpView;
    ImageView imageView;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        nameView = findViewById(R.id.detail_name);
        hpView = findViewById(R.id.detail_hp);
        imageView = findViewById(R.id.detail_image);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        nameView.setText(name);

        int hp = intent.getIntExtra("hp", 0);
        hpView.setText(String.format("%d HP", hp));

        int imageId = intent.getIntExtra("imageId", R.drawable.ic_001);
        imageView.setImageResource(imageId);

        PokemonMove[] pokemonMoves = (PokemonMove[]) intent.getSerializableExtra("moves");
        PokemonsMoveAdapter adapter = new PokemonsMoveAdapter(pokemonMoves);
        RecyclerView recyclerView = this.findViewById(R.id.detail_moves);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}