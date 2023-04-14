package com.example.pokemons.parse;

import com.example.pokemons.pokemon.PokemonInfo;
import com.example.pokemons.pokemon_attack.PokemonAttack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PokemonParser {
    public static JSONArray getJsonArrayData(String responseBody) throws JSONException {
        JSONObject jsonResponse = new JSONObject(responseBody);
        return jsonResponse.getJSONArray("data");
    }

    public static ArrayList<PokemonInfo> getPokemonInfos(JSONArray jsonQuestions) throws JSONException {
        ArrayList<PokemonInfo> pokemonInfos = new ArrayList<>();
        for (int i = 0; i < jsonQuestions.length(); i++) {
            JSONObject jsonQuestion = jsonQuestions.getJSONObject(i);
            PokemonInfo pokemonInfo = parsePokemonInfo(jsonQuestion);
            pokemonInfos.add(pokemonInfo);
        }

        return pokemonInfos;
    }

    private static PokemonInfo parsePokemonInfo(JSONObject jsonQuestion) throws JSONException {
        PokemonInfo pokemonInfo = new PokemonInfo();
        pokemonInfo.setNumber(jsonQuestion.getJSONArray("nationalPokedexNumbers").getInt(0));
        pokemonInfo.setName(jsonQuestion.getString("name"));
        pokemonInfo.setHp(jsonQuestion.getInt("hp"));
        pokemonInfo.setImageUrl(jsonQuestion.getJSONObject("images").getString("small"));

        ArrayList<PokemonAttack> moves = parsePokemonMoves(jsonQuestion);
        pokemonInfo.setMoves(moves);

        return pokemonInfo;
    }

    private static ArrayList<PokemonAttack> parsePokemonMoves(JSONObject jsonQuestion) throws JSONException {
        ArrayList<PokemonAttack> pokemonAttacks = new ArrayList<>();
        if (!jsonQuestion.has("attacks"))
            return pokemonAttacks;

        JSONArray jsonQuestions = jsonQuestion.getJSONArray("attacks");
        for (int i = 0; i < jsonQuestions.length(); i++) {
            jsonQuestion = jsonQuestions.getJSONObject(i);
            PokemonAttack pokemonAttack = parsePokemonMove(jsonQuestion);
            pokemonAttacks.add(pokemonAttack);
        }

        return pokemonAttacks;
    }

    private static PokemonAttack parsePokemonMove(JSONObject jsonObject) throws JSONException {
        PokemonAttack pokemonAttack = new PokemonAttack();

        String name = getName(jsonObject);
        pokemonAttack.setName(name);

        String power = getPower(jsonObject);
        pokemonAttack.setPower(power);

        String type = getType(jsonObject);
        pokemonAttack.setType(type);

        return pokemonAttack;
    }

    private static String getName(JSONObject jsonObject) throws JSONException {
        return jsonObject.getString("name");
    }

    private static String getPower(JSONObject jsonObject) throws JSONException {
        String power = jsonObject.getString("damage");
        if (power.equals(""))
            power = "-";

        return power;
    }

    private static String getType(JSONObject jsonObject) throws JSONException {
        StringBuilder type = new StringBuilder("type");
        if (jsonObject.has("cost") && jsonObject.getJSONArray("cost").length() != 0) {
            JSONArray cost = jsonObject.getJSONArray("cost");
            type = new StringBuilder(cost.getString(0));
            for (int i = 1; i < cost.length(); i++)
                type.append(", ").append(cost.getString(i));
        }

        return type.toString();
    }
}
