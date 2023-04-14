package com.example.pokemons.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.pokemons.database.dao.PokemonAndPokemonAttackDao;
import com.example.pokemons.database.dao.PokemonAndPokemonImageDao;
import com.example.pokemons.database.dao.PokemonAttackDao;
import com.example.pokemons.database.dao.PokemonDao;
import com.example.pokemons.database.dao.PokemonImageDao;
import com.example.pokemons.database.entities.PokemonAndPokemonAttackEntity;
import com.example.pokemons.database.entities.PokemonAndPokemonImageEntity;
import com.example.pokemons.database.entities.PokemonAttackEntity;
import com.example.pokemons.database.entities.PokemonEntity;
import com.example.pokemons.database.entities.PokemonImageEntity;

@Database(entities = {PokemonEntity.class, PokemonAttackEntity.class,
        PokemonAndPokemonAttackEntity.class, PokemonImageEntity.class,
        PokemonAndPokemonImageEntity.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PokemonDao pokemonDao();
    public abstract PokemonAttackDao pokemonAttackDao();
    public abstract PokemonAndPokemonAttackDao pokemonAndPokemonAttackDao();
    public abstract PokemonImageDao pokemonImageDao();
    public abstract PokemonAndPokemonImageDao pokemonAndPokemonImageDao();
}
