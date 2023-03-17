package com.example.pokemons;

import android.content.res.TypedArray;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    public MainActivity() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        PokemonItemContent[] pokemonItemContents = this.getPokemonItemContents();
        PokemonsAdapter adapter = new PokemonsAdapter(pokemonItemContents);
        RecyclerView recyclerView = this.findViewById(R.id.content);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private PokemonItemContent[] getPokemonItemContents() {
        int[] imageIds = this.getImageIds();
        String[] names = this.getResources().getStringArray(R.array.pokemons_names);
        int[] numbers = this.getResources().getIntArray(R.array.pokemons_numbers);
        int[] hps = this.getResources().getIntArray(R.array.pokemons_hps);
        int n = imageIds.length;
        PokemonItemContent[] pokemonItemContents = new PokemonItemContent[n];

        int i;
        for(i = 0; i < pokemonItemContents.length; ++i) {
            pokemonItemContents[i] = new PokemonItemContent();
        }

        for(i = 0; i < n; ++i) {
            pokemonItemContents[i].setImageId(imageIds[i]);
            pokemonItemContents[i].setName(names[i]);
            pokemonItemContents[i].setNumber(numbers[i]);
            pokemonItemContents[i].setHp(hps[i]);
        }

        return pokemonItemContents;
    }

    private int[] getImageIds() {
        TypedArray typedArray = this.getResources().obtainTypedArray(R.array.pokemons_image_ids);
        int count = typedArray.length();
        int[] imageIds = new int[count];

        for(int i = 0; i < count; ++i) {
            imageIds[i] = typedArray.getResourceId(i, 2131165285);
        }

        return imageIds;
    }
}
