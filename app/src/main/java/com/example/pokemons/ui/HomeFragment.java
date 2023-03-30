package com.example.pokemons.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pokemons.Activities.DetailActivity;
import com.example.pokemons.PokemonMove.PokemonMove;
import com.example.pokemons.Pokemons.PokemonInfo;
import com.example.pokemons.Pokemons.PokemonsAdapter;
import com.example.pokemons.Pokemons.PokemonsDecorator;
import com.example.pokemons.R;
import com.example.pokemons.RecyclerViewInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HomeFragment extends Fragment implements RecyclerViewInterface {
    private final String TAG = "flog";

    private PokemonInfo[] pokemonInfo;
    private RecyclerView recyclerView;

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);

        intent.putExtra("name", pokemonInfo[position].getName());
        intent.putExtra("imageUrl", pokemonInfo[position].getImageUrl());
        intent.putExtra("hp", pokemonInfo[position].getHp());
        intent.putExtra("moves", pokemonInfo[position].getMoves());

        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.content);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        new DownloadPokemonsInfo().execute("https://api.pokemontcg.io/v2/cards");

        PokemonsDecorator decoration = new PokemonsDecorator(15);
        recyclerView.addItemDecoration(decoration);

        return view;
    }
    
    @SuppressLint("StaticFieldLeak")
    private class DownloadPokemonsInfo extends AsyncTask<String, Void, PokemonInfo[]> {
        protected PokemonInfo[] doInBackground(String... urls) {
            Log.d(TAG, "doInBackground: start");
            try {
                return run(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.d(TAG, "doInBackground: stop");
            return null;
        }

        protected void onPostExecute(PokemonInfo[] result) {
            PokemonsAdapter adapter = new PokemonsAdapter(result, HomeFragment.this);
            recyclerView.setAdapter(adapter);
        }

        private PokemonInfo[] run(String url) throws IOException {
            Log.d(TAG, "run: start");
            List<PokemonInfo> pokemonInfos = new ArrayList<>();

            Request request = new Request.Builder().url(url).build();
            OkHttpClient client = new OkHttpClient();
            Log.d(TAG, "run: response start");
            Response response = client.newCall(request).execute();
            Log.d(TAG, "run: response stop");

            String responseBody = "";
            if (response.isSuccessful()) {
                responseBody = Objects.requireNonNull(response.body()).string();
                response.close();
            }
            Log.d(TAG, "run: try start");
            try {
                JSONObject jsonResponse = new JSONObject(responseBody);
                JSONArray jsonQuestions = jsonResponse.getJSONArray("data");
                Log.d(TAG, "run: for start ");
                for (int i = 0; i < jsonQuestions.length(); i++) {
                    Log.d(TAG, "run: " + i);
                    JSONObject jsonQuestion = jsonQuestions.getJSONObject(i);
                    PokemonInfo pokemonInfo = parsePokemonInfo(jsonQuestion);
                    pokemonInfos.add(pokemonInfo);
                }
                Log.d(TAG, "run: for end" + pokemonInfos);

                pokemonInfo = pokemonInfos.toArray(new PokemonInfo[0]);
                Arrays.sort(pokemonInfo);
            } catch (JSONException e) {
                Log.d(TAG, "run: catch");
                e.printStackTrace();
            }

            Log.d(TAG, "run: stop");
            return pokemonInfo;
        }

        private PokemonInfo parsePokemonInfo(JSONObject jsonQuestion) throws JSONException {
            PokemonInfo pokemonInfo = new PokemonInfo();
            pokemonInfo.setNumber(jsonQuestion.getJSONArray("nationalPokedexNumbers").getInt(0));
            pokemonInfo.setName(jsonQuestion.getString("name"));
            pokemonInfo.setHp(jsonQuestion.getInt("hp"));
            pokemonInfo.setImageUrl(jsonQuestion.getJSONObject("images").getString("small"));

            PokemonMove[] moves = parsePokemonMoves(jsonQuestion);
            pokemonInfo.setMoves(moves);

            return pokemonInfo;
        }

        private PokemonMove[] parsePokemonMoves(JSONObject jsonQuestion) throws JSONException {
            if (!jsonQuestion.has("attacks"))
                return null;

            JSONArray jsonQuestions = jsonQuestion.getJSONArray("attacks");
            List<PokemonMove> pokemonMoves = new ArrayList<>();
            for (int i = 0; i < jsonQuestions.length(); i++) {
                jsonQuestion = jsonQuestions.getJSONObject(i);
                PokemonMove pokemonMove = parsePokemonMove(jsonQuestion);
                pokemonMoves.add(pokemonMove);
            }

            return pokemonMoves.toArray(new PokemonMove[0]);
        }

        private PokemonMove parsePokemonMove(JSONObject jsonObject) throws JSONException {
            PokemonMove pokemonMove = new PokemonMove();

            String name = getName(jsonObject);
            pokemonMove.setName(name);

            String details = getDetails(jsonObject);
            pokemonMove.setDetails(details);

            String power = getPower(jsonObject);
            pokemonMove.setPower(power);

            String type = getType(jsonObject);
            pokemonMove.setType(type);

            return pokemonMove;
        }

        @NonNull
        private String getName(JSONObject jsonObject) throws JSONException {
            return jsonObject.getString("name");
        }

        @NonNull
        private String getType(JSONObject jsonObject) throws JSONException {
            StringBuilder type = new StringBuilder("type");
            if (jsonObject.has("cost") && jsonObject.getJSONArray("cost").length() != 0) {
                JSONArray cost = jsonObject.getJSONArray("cost");
                type = new StringBuilder(cost.getString(0));
                for (int i = 1; i < cost.length(); i++)
                    type.append(", ").append(cost.getString(i));
            }

            return type.toString();
        }

        @NonNull
        private String getPower(JSONObject jsonObject) throws JSONException {
            String power = jsonObject.getString("damage");
            if (power.equals(""))
                power = "-";

            return power;
        }

        @NonNull
        private String getDetails(JSONObject jsonObject) throws JSONException {
            String details = "details";
            if (jsonObject.has("text")) {
                details = jsonObject.getString("text");
                if (details.equals(""))
                    details = "-";
            }

            return details;
        }
    }
}