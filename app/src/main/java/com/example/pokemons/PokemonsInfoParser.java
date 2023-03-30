package com.example.pokemons;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.pokemons.Pokemons.PokemonInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PokemonsInfoParser {
    OkHttpClient client;
    private final List<PokemonInfo> pokemonInfos = new ArrayList<>();

    public PokemonsInfoParser() {
        client = new OkHttpClient();
    }

    public List<PokemonInfo> runAsync(String url, View view) {
        pokemonInfos.clear();

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseBody = Objects.requireNonNull(response.body()).string();
                try {
                    JSONObject jsonResponse = new JSONObject(responseBody);
                    JSONArray jsonQuestions = jsonResponse.getJSONArray("data");
                    for (int i = 0; i < jsonQuestions.length(); i++) {
                        JSONObject jsonQuestion = jsonQuestions.getJSONObject(i);

                        PokemonInfo pokemonInfo = new PokemonInfo();
                        pokemonInfo.setNumber(jsonQuestion.getJSONArray("nationalPokedexNumbers").getInt(0));
                        pokemonInfo.setName(jsonQuestion.getString("name"));
                        pokemonInfo.setHp(jsonQuestion.getInt("hp"));

                        pokemonInfos.add(pokemonInfo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return pokemonInfos;
    }
}
