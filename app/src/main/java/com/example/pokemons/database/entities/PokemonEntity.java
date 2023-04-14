package com.example.pokemons.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "pokemons")
public class PokemonEntity implements Comparable<PokemonEntity>, Serializable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "pokemon_id")
    public String pokemonId;

    @NonNull
    @ColumnInfo(name = "name")
    public String name;

    @NonNull
    @ColumnInfo(name = "image_url")
    public String imageUrl;

    @ColumnInfo(name = "number")
    public int number;

    @ColumnInfo(name = "hp")
    public int hp;

    public PokemonEntity(@NonNull String pokemonId, @NonNull String name, @NonNull String imageUrl, int number, int hp) {
        this.pokemonId = pokemonId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.number = number;
        this.hp = hp;
    }

    @Override
    public int compareTo(PokemonEntity pokemonEntity) {
        return this.number - pokemonEntity.number;
    }
}
