package com.example.pokemons.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pokemon_attacks")
public class PokemonAttackEntity {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "pokemon_attack_id")
    public String pokemonAttackId;

    @NonNull
    @ColumnInfo(name = "name")
    public String name;

    @NonNull
    @ColumnInfo(name = "power")
    public String power;

    @NonNull
    @ColumnInfo(name = "type")
    public String type;

    public PokemonAttackEntity(@NonNull String pokemonAttackId, @NonNull String name, @NonNull String power, @NonNull String type) {
        this.pokemonAttackId = pokemonAttackId;
        this.name = name;
        this.power = power;
        this.type = type;
    }
}
