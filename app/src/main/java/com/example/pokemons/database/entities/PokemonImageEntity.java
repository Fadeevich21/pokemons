package com.example.pokemons.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pokemon_images")
public class PokemonImageEntity {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "pokemon_image_id")
    public String pokemonImageId;

    @NonNull
    @ColumnInfo(name = "image")
    public String image;

    public PokemonImageEntity(@NonNull String pokemonImageId, @NonNull String image) {
        this.pokemonImageId = pokemonImageId;
        this.image = image;
    }
}
