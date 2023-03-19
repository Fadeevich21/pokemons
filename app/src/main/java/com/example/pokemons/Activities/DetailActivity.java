package com.example.pokemons.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pokemons.PokemonMove.PokemonMove;
import com.example.pokemons.PokemonMove.PokemonsMoveAdapter;
import com.example.pokemons.PokemonMove.PokemonsMoveDecorator;
import com.example.pokemons.R;

public class DetailActivity extends AppCompatActivity {
    TextView nameView;
    TextView descriptionView;
    TextView hpView;
    ImageView imageView;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initViews();

        Intent intent = getIntent();
        setupViews(intent);
        setAdapter(intent);
        addItemDecoration(10);
    }

    private void setupRecyclerView() {
        recyclerView = this.findViewById(R.id.detail_moves);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupViews(Intent intent) {
        setupNameView(intent);
        setupDescriptionView(intent);
        setupHpView(intent);
        setupImageView(intent);

        setupRecyclerView();
    }

    private void setupImageView(Intent intent) {
        int imageId = intent.getIntExtra("imageId", R.drawable.ic_001);
        imageView.setImageResource(imageId);
    }

    private void setupHpView(Intent intent) {
        int hp = intent.getIntExtra("hp", 0);
        hpView.setText(String.format("%d HP", hp));
    }

    private void setupDescriptionView(Intent intent) {
        String description = intent.getStringExtra("description");
        descriptionView.setText(description);
    }

    private void setupNameView(Intent intent) {
        String name = intent.getStringExtra("name");
        nameView.setText(name);
    }

    private void initViews() {
        nameView = findViewById(R.id.detail_name);
        hpView = findViewById(R.id.detail_hp);
        imageView = findViewById(R.id.detail_image);
        descriptionView = findViewById(R.id.detail_description);
    }

    private void setAdapter(Intent intent) {
        PokemonMove[] pokemonMoves = (PokemonMove[]) intent.getSerializableExtra("moves");
        PokemonsMoveAdapter adapter = new PokemonsMoveAdapter(pokemonMoves);
        recyclerView.setAdapter(adapter);
    }

    private void addItemDecoration(int margin) {
        PokemonsMoveDecorator decoration = new PokemonsMoveDecorator(margin);
        recyclerView.addItemDecoration(decoration);
    }
}