package com.example.pokemons.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pokemons.LoadImageFromDatabase;
import com.example.pokemons.MyApplication;
import com.example.pokemons.database.AppDatabase;
import com.example.pokemons.database.entities.PokemonAttackEntity;
import com.example.pokemons.database.entities.PokemonEntity;
import com.example.pokemons.pokemon_attack.PokemonAttackAdapter;
import com.example.pokemons.pokemon_attack.PokemonAttackDecorator;
import com.example.pokemons.R;
import com.example.pokemons.ui.DetailFragment;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String pokemonId = intent.getStringExtra("pokemon_id");

        Bundle bundle = new Bundle();
        bundle.putString("pokemon_id", pokemonId);

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.frame_layout_detail, DetailFragment.class, bundle)
                .commit();

    }
}