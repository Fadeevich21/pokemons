package com.example.pokemons.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pokemons.database.entities.PokemonEntity;

import java.util.List;

@Dao
public interface PokemonDao {
    @Query("SELECT * FROM pokemons")
    List<PokemonEntity> getAll();

    @Query("SELECT * FROM pokemons WHERE name LIKE :name LIMIT 1")
    PokemonEntity findByName(String name);

    @Query("SELECT * FROM pokemons WHERE pokemon_id LIKE :pokemonId LIMIT 1")
    PokemonEntity findById(String pokemonId);

    @Insert
    void insert(PokemonEntity pokemonEntity);

    @Delete
    void delete(PokemonEntity pokemonEntity);
}
