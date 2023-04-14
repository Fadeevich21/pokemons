package com.example.pokemons.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pokemons.database.entities.PokemonAndPokemonImageEntity;

import java.util.List;

@Dao
public interface PokemonAndPokemonImageDao {
    @Query("SELECT * FROM pokemons_and_pokemon_images")
    List<PokemonAndPokemonImageEntity> getAll();

    @Query("SELECT pokemon_image_id FROM pokemons_and_pokemon_images WHERE pokemon_id LIKE :pokemonId")
    List<String> getAllPokemonImageIds(String pokemonId);

    @Insert
    void insert(PokemonAndPokemonImageEntity pokemonAndPokemonImageEntity);

    @Delete
    void delete(PokemonAndPokemonImageEntity pokemonAndPokemonImageEntity);
}
