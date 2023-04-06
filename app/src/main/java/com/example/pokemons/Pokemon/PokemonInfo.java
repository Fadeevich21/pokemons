package com.example.pokemons.Pokemon;

import androidx.annotation.NonNull;

import com.example.pokemons.PokemonMove.PokemonMove;

import java.util.ArrayList;

public class PokemonInfo implements Comparable<PokemonInfo> {
    private String imageUrl;
    private String name;
    private int hp;
    private int number;
    ArrayList<PokemonMove> moves;

    public PokemonInfo() {
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return this.name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public int getHp() {
        return this.hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ArrayList<PokemonMove> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<PokemonMove> moves) {
        this.moves = moves;
    }

    @Override
    public int compareTo(PokemonInfo pokemonInfo) {
        return this.number - pokemonInfo.number;
    }
}
