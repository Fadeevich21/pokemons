package com.example.pokemons.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pokemons.database.entities.PokemonImageEntity;

import java.util.List;

@Dao
public interface PokemonImageDao {
    @Query("SELECT * FROM pokemon_images")
    List<PokemonImageEntity> getAll();

    @Query("SELECT * FROM pokemon_images WHERE pokemon_image_id LIKE :pokemonImageId LIMIT 1")
    PokemonImageEntity findById(String pokemonImageId);

    @Insert
    void insert(PokemonImageEntity pokemonImageEntity);


    @Delete
    void delete(PokemonImageEntity pokemonImageEntity);
}
