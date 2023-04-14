package com.example.pokemons.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pokemons.database.entities.PokemonAndPokemonAttackEntity;
import com.example.pokemons.database.entities.PokemonEntity;

import java.util.List;

@Dao
public interface PokemonAndPokemonAttackDao {
    @Query("SELECT * FROM pokemons_and_pokemon_attacks")
    List<PokemonAndPokemonAttackEntity> getAll();

    @Query("SELECT pokemon_attack_id FROM pokemons_and_pokemon_attacks WHERE pokemon_id LIKE :pokemonId")
    List<String> getAllPokemonAttackIds(String pokemonId);

    @Insert
    void insert(PokemonAndPokemonAttackEntity pokemonAndPokemonAttackEntity);

    @Delete
    void delete(PokemonAndPokemonAttackEntity pokemonAndPokemonAttackEntity);
}
