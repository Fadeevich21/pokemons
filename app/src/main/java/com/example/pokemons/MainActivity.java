package com.example.pokemons;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {
    PokemonInfo[] pokemonInfos;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        pokemonInfos = this.getPokemonItemContents();
        PokemonsAdapter adapter = new PokemonsAdapter(pokemonInfos, this);
        RecyclerView recyclerView = this.findViewById(R.id.content);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private PokemonInfo[] getPokemonItemContents() {
        int[] imageIds = this.getImageIds();
        String[] names = this.getResources().getStringArray(R.array.pokemons_names);
        int[] numbers = this.getResources().getIntArray(R.array.pokemons_numbers);
        int[] hps = this.getResources().getIntArray(R.array.pokemons_hps);
        PokemonMove[][] pokemonMoves = getPokemonMovies();

        int n = imageIds.length;
        PokemonInfo[] pokemonInfos = new PokemonInfo[n];
        for (int i = 0; i < pokemonInfos.length; ++i) {
            pokemonInfos[i] = new PokemonInfo();
        }

        for (int i = 0; i < n; ++i) {
            pokemonInfos[i].setImageId(imageIds[i]);
            pokemonInfos[i].setName(names[i]);
            pokemonInfos[i].setNumber(numbers[i]);
            pokemonInfos[i].setHp(hps[i]);
            pokemonInfos[i].setMoves(pokemonMoves[i]);
        }

        return pokemonInfos;
    }

    private PokemonMove[][] getPokemonMovies() {
        int[] levels = getResources().getIntArray(R.array.pokemon_001_move_levels);
        String[] names = getResources().getStringArray(R.array.pokemon_001_move_names);
        int[] powers = getResources().getIntArray(R.array.pokemon_001_move_powers);
        int[] accuracies = getResources().getIntArray(R.array.pokemon_001_move_accuracies);
        int[] pps = getResources().getIntArray(R.array.pokemon_001_move_pps);
        String[] types = getResources().getStringArray(R.array.pokemon_001_move_types);
        String[] details = getResources().getStringArray(R.array.pokemon_001_move_details);
        PokemonMove[] moves = new PokemonMove[levels.length];
        for (int i = 0; i < moves.length; i++) {
            moves[i] = new PokemonMove(levels[i], names[i], powers[i], accuracies[i], pps[i], types[i], details[i]);
        }
        PokemonMove[][] pokemonMoves = new PokemonMove[6][moves.length];
        for (int i = 0; i < pokemonMoves.length; i++) {
            pokemonMoves[i] = moves.clone();
        }
        
        return pokemonMoves;
    }

    private int[] getImageIds() {
        TypedArray typedArray = this.getResources().obtainTypedArray(R.array.pokemons_image_ids);
        int count = typedArray.length();
        int[] imageIds = new int[count];
        for (int i = 0; i < count; ++i) {
            imageIds[i] = typedArray.getResourceId(i, R.drawable.ic_001);
        }

        return imageIds;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);

        intent.putExtra("name", pokemonInfos[position].getName());
        intent.putExtra("imageId", pokemonInfos[position].getImageId());
        intent.putExtra("hp", pokemonInfos[position].getHp());
        intent.putExtra("moves", pokemonInfos[position].getMoves());
        
        startActivity(intent);
    }
}
