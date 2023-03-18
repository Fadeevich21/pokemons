package com.example.pokemons;

import java.io.Serializable;

public class PokemonMove implements Serializable {
    int level;
    String name;
    int power;
    int accuracy;
    int pp;
    String type;
    String details;

    public PokemonMove(int level, String name, int power, int accuracy, int pp, String type, String details) {
        this.level = level;
        this.name = name;
        this.power = power;
        this.accuracy = accuracy;
        this.pp = pp;
        this.type = type;
        this.details = details;
    }

    public PokemonMove() {
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getPp() {
        return pp;
    }

    public void setPp(int pp) {
        this.pp = pp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
