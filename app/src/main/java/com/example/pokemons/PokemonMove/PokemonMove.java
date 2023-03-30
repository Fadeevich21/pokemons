package com.example.pokemons.PokemonMove;

import java.io.Serializable;

public class PokemonMove implements Serializable {
    String name;
    String power;
    String type;

    public PokemonMove(String name, String power, String type) {
        this.name = name;
        this.power = power;
        this.type = type;
    }

    public PokemonMove() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
