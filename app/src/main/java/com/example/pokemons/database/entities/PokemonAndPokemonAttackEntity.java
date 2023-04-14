package com.example.pokemons.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "pokemons_and_pokemon_attacks", foreignKeys = {
        @ForeignKey(entity = PokemonEntity.class,
                parentColumns = "pokemon_id",
                childColumns = "pokemon_id"),
        @ForeignKey(entity = PokemonAttackEntity.class,
                parentColumns = "pokemon_attack_id",
                childColumns = "pokemon_attack_id")
})
public class PokemonAndPokemonAttackEntity {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "pokemon_and_pokemon_attack_id")
    public String pokemonAndPokemonAttackId;

    @NonNull
    @ColumnInfo(name = "pokemon_id")
    public String pokemonId;

    @NonNull
    @ColumnInfo(name = "pokemon_attack_id")
    public String pokemonAttackId;

    public PokemonAndPokemonAttackEntity(@NonNull String pokemonAndPokemonAttackId, @NonNull String pokemonId, @NonNull String pokemonAttackId) {
        this.pokemonAndPokemonAttackId = pokemonAndPokemonAttackId;
        this.pokemonId = pokemonId;
        this.pokemonAttackId = pokemonAttackId;
    }
}
