package com.example.pokemons.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "pokemons_and_pokemon_images", foreignKeys = {
        @ForeignKey(entity = PokemonEntity.class,
                parentColumns = "pokemon_id",
                childColumns = "pokemon_id"
        ),
        @ForeignKey(entity = PokemonImageEntity.class,
                parentColumns = "pokemon_image_id",
                childColumns = "pokemon_image_id")
})
public class PokemonAndPokemonImageEntity {
        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "pokemon_and_pokemon_image_id")
        public String pokemonAndPokemonImageId;

        @NonNull
        @ColumnInfo(name = "pokemon_id")
        public String pokemonId;

        @NonNull
        @ColumnInfo(name = "pokemon_image_id")
        public String pokemonImageId;

        public PokemonAndPokemonImageEntity(@NonNull String pokemonAndPokemonImageId,
                                            @NonNull String pokemonId,
                                            @NonNull String pokemonImageId) {
                this.pokemonAndPokemonImageId = pokemonAndPokemonImageId;
                this.pokemonId = pokemonId;
                this.pokemonImageId = pokemonImageId;
        }
}
