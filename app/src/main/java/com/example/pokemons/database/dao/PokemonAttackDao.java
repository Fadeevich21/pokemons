package com.example.pokemons.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pokemons.database.entities.PokemonAttackEntity;

import java.util.List;

@Dao
public interface PokemonAttackDao {
    @Query("SELECT * FROM pokemon_attacks")
    List<PokemonAttackEntity> getAll();

    @Query("SELECT * FROM pokemon_attacks WHERE name LIKE :name LIMIT 1")
    PokemonAttackEntity findByName(String name);

    @Query("SELECT * FROM pokemon_attacks WHERE pokemon_attack_id LIKE :pokemonAttackId LIMIT 1")
    PokemonAttackEntity findById(String pokemonAttackId);

    @Insert
    void insert(PokemonAttackEntity pokemonAttackEntity);

    @Delete
    void delete(PokemonAttackEntity pokemonAttackEntity);
}
