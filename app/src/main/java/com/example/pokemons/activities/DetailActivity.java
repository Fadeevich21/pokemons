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

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    TextView nameView;
    TextView hpView;
    ImageView imageView;
    ProgressBar progressBar;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initViews();

        Intent intent = getIntent();
        String pokemonId = intent.getStringExtra("pokemon_id");

        setupRecyclerView();
        addItemDecoration(10);
        new SetupViews().execute(pokemonId);
    }

    private void initViews() {
        nameView = findViewById(R.id.detail_name);
        hpView = findViewById(R.id.detail_hp);
        imageView = findViewById(R.id.detail_image);
        progressBar = findViewById(R.id.activity_detail_progress_bar);
    }

    private void setupRecyclerView() {
        recyclerView = this.findViewById(R.id.detail_moves);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void addItemDecoration(int margin) {
        PokemonAttackDecorator decoration = new PokemonAttackDecorator(margin);
        recyclerView.addItemDecoration(decoration);
    }

    @SuppressLint("StaticFieldLeak")
    private class SetupViews extends AsyncTask<String, Void, Void> {
        PokemonEntity pokemonEntity;
        ArrayList<PokemonAttackEntity> pokemonAttackEntities = new ArrayList<>();

        @Override
        protected Void doInBackground(String... strings) {
            AppDatabase db = Room.databaseBuilder(MyApplication.getAppContext(),
                            AppDatabase.class, "database-name").fallbackToDestructiveMigration()
                    .build();

            String pokemonId = strings[0];
            pokemonEntity = db.pokemonDao().findById(pokemonId);
            if (pokemonEntity == null)
                return null;

            List<String> pokemonAttackIds = db.pokemonAndPokemonAttackDao().getAllPokemonAttackIds(pokemonId);
            if (pokemonAttackIds == null)
                return null;

            for (String pokemonAttackId : pokemonAttackIds) {
                PokemonAttackEntity pokemonAttackEntity = db.pokemonAttackDao().findById(pokemonAttackId);
                pokemonAttackEntities.add(pokemonAttackEntity);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            super.onPostExecute(res);
            setupViews(pokemonEntity);
        }

        private void setupViews(PokemonEntity pokemonEntity) {
            setAdapter(pokemonAttackEntities);
            setupNameView(pokemonEntity.name);
            setupHpView(pokemonEntity.hp);
            setupImageView(pokemonEntity.pokemonId);
        }

        private void setupNameView(String name) {
            nameView.setText(name);
        }

        @SuppressLint("DefaultLocale")
        private void setupHpView(int hp) {
            hpView.setText(String.format("%d HP", hp));
        }

        private void setupImageView(String pokemonId) {
            LoadImageFromDatabase loadImageFromDatabase = new LoadImageFromDatabase(imageView, progressBar);
            loadImageFromDatabase.execute(pokemonId);
        }

        private void setAdapter(ArrayList<PokemonAttackEntity> pokemonAttackEntities) {
            PokemonAttackAdapter adapter = new PokemonAttackAdapter(pokemonAttackEntities);
            recyclerView.setAdapter(adapter);
        }
    }
}