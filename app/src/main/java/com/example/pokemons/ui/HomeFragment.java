package com.example.pokemons.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pokemons.Activities.DetailActivity;
import com.example.pokemons.Pokemons.PokemonInfo;
import com.example.pokemons.PokemonMove.PokemonMove;
import com.example.pokemons.Pokemons.PokemonsAdapter;
import com.example.pokemons.Pokemons.PokemonsDecorator;
import com.example.pokemons.R;
import com.example.pokemons.RecyclerViewInterface;


public class HomeFragment extends Fragment implements RecyclerViewInterface {
    private PokemonInfo[] pokemonInfos;
    private RecyclerView recyclerView;

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);

        intent.putExtra("name", pokemonInfos[position].getName());
        intent.putExtra("imageId", pokemonInfos[position].getImageId());
        intent.putExtra("hp", pokemonInfos[position].getHp());
        intent.putExtra("moves", pokemonInfos[position].getMoves());
        intent.putExtra("description", pokemonInfos[position].getDescription());

        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        pokemonInfos = this.getPokemonItemContents();
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.content);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        PokemonsAdapter adapter = new PokemonsAdapter(pokemonInfos, this);
        recyclerView.setAdapter(adapter);

        PokemonsDecorator decoration = new PokemonsDecorator(15);
        recyclerView.addItemDecoration(decoration);

        return view;
    }


    private PokemonInfo[] getPokemonItemContents() {
        int[] imageIds = this.getImageIds();
        String[] names = this.getResources().getStringArray(R.array.pokemons_names);
        String[] descriptions = this.getResources().getStringArray(R.array.pokemons_descriptions);
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
            pokemonInfos[i].setDescription(descriptions[i]);
            pokemonInfos[i].setNumber(numbers[i]);
            pokemonInfos[i].setHp(hps[i]);
            pokemonInfos[i].setMoves(pokemonMoves[i]);
        }

        return pokemonInfos;
    }

    private PokemonMove[][] getPokemonMovies() {
        int count = 6;
        int[] levelsIds = new int[]{
                R.array.pokemon_001_move_levels, R.array.pokemon_002_move_levels,
                R.array.pokemon_003_move_levels, R.array.pokemon_004_move_levels,
                R.array.pokemon_005_move_levels, R.array.pokemon_006_move_levels
        };
        int[] namesIds = new int[]{
                R.array.pokemon_001_move_names, R.array.pokemon_002_move_names,
                R.array.pokemon_003_move_names, R.array.pokemon_004_move_names,
                R.array.pokemon_005_move_names, R.array.pokemon_006_move_names
        };
        int[] powersIds = new int[]{
                R.array.pokemon_001_move_powers, R.array.pokemon_002_move_powers,
                R.array.pokemon_003_move_powers, R.array.pokemon_004_move_powers,
                R.array.pokemon_005_move_powers, R.array.pokemon_006_move_powers
        };
        int[] accuraciesIds = new int[]{
                R.array.pokemon_001_move_accuracies, R.array.pokemon_002_move_accuracies,
                R.array.pokemon_003_move_accuracies, R.array.pokemon_004_move_accuracies,
                R.array.pokemon_005_move_accuracies, R.array.pokemon_006_move_accuracies
        };
        int[] ppsIds = new int[]{
                R.array.pokemon_001_move_pps, R.array.pokemon_002_move_pps,
                R.array.pokemon_003_move_pps, R.array.pokemon_004_move_pps,
                R.array.pokemon_005_move_pps, R.array.pokemon_006_move_pps
        };
        int[] typesIds = new int[]{
                R.array.pokemon_001_move_types, R.array.pokemon_002_move_types,
                R.array.pokemon_003_move_types, R.array.pokemon_004_move_types,
                R.array.pokemon_005_move_types, R.array.pokemon_006_move_types
        };
        int[] detailsIds = new int[]{
                R.array.pokemon_001_move_details, R.array.pokemon_002_move_details,
                R.array.pokemon_003_move_details, R.array.pokemon_004_move_details,
                R.array.pokemon_005_move_details, R.array.pokemon_006_move_details
        };

        PokemonMove[][] pokemonMoves = new PokemonMove[count][];
        for (int i = 0; i < count; i++) {
            int[] levels = getResources().getIntArray(levelsIds[i]);
            String[] names = getResources().getStringArray(namesIds[i]);
            int[] powers = getResources().getIntArray(powersIds[i]);
            int[] accuracies = getResources().getIntArray(accuraciesIds[i]);
            int[] pps = getResources().getIntArray(ppsIds[i]);
            String[] types = getResources().getStringArray(typesIds[i]);
            String[] details = getResources().getStringArray(detailsIds[i]);

            PokemonMove[] moves = new PokemonMove[levels.length];
            for (int j = 0; j < moves.length; j++) {
                moves[j] = new PokemonMove(levels[j], names[j], powers[j], accuracies[j], pps[j], types[j], details[j]);
            }
            pokemonMoves[i] = moves;
        }

        return pokemonMoves;
    }

    private int[] getImageIds() {
        @SuppressLint("Recycle") TypedArray typedArray = this.getResources().obtainTypedArray(R.array.pokemons_image_ids);
        int count = typedArray.length();
        int[] imageIds = new int[count];
        for (int i = 0; i < count; ++i) {
            imageIds[i] = typedArray.getResourceId(i, R.drawable.ic_001);
        }

        return imageIds;
    }
}