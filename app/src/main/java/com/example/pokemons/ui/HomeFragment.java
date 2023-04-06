package com.example.pokemons.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pokemons.Activities.DetailActivity;
import com.example.pokemons.PokemonMove.PokemonMove;
import com.example.pokemons.Pokemon.PokemonInfo;
import com.example.pokemons.Pokemon.PokemonAdapter;
import com.example.pokemons.Pokemon.PokemonDecorator;
import com.example.pokemons.R;
import com.example.pokemons.RecyclerViewInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HomeFragment extends Fragment implements RecyclerViewInterface {
    private ArrayList<PokemonInfo> pokemonInfo = new ArrayList<>();
    private RecyclerView recyclerView;
    private PokemonAdapter adapter;
    private SearchView searchView;
    private DownloadPokemonInfo downloadPokemonInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createAdapter();

        if (savedInstanceState != null) {
            pokemonInfo = (ArrayList<PokemonInfo>) savedInstanceState.getSerializable("pokemon_info");
            downloadPokemonInfo = (DownloadPokemonInfo) savedInstanceState.getSerializable("download_pokemon_info");
        } else {
            downloadPokemonInfo = new DownloadPokemonInfo();
        }

        if (pokemonInfo.isEmpty() && new DownloadPokemonInfo().getStatus() != AsyncTask.Status.RUNNING) {
            new DownloadPokemonInfo().execute("https://api.pokemontcg.io/v2/cards");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("pokemon_info", pokemonInfo);
        outState.putSerializable("download_pokemon_info", (Serializable) downloadPokemonInfo);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(view);
        setupViews(view);

        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.content);
        searchView = view.findViewById(R.id.search);
    }

    private void setupViews(View view) {
        setupRecyclerView(view);
        setupSearchView();
    }

    private void setupRecyclerView(View view) {
        setLayoutManager(view);
        recyclerView.setAdapter(adapter);
        addItemDecoration();
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
    }

    private void filter(String text) {
        if (pokemonInfo.isEmpty())
            return;

        ArrayList<PokemonInfo> filteredList = new ArrayList<>();
        for (PokemonInfo item : pokemonInfo) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(getActivity(), "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            adapter.filterList(filteredList);
        }
    }

    private void setLayoutManager(View view) {
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    private void createAdapter() {
        adapter = new PokemonAdapter(pokemonInfo, HomeFragment.this);
    }

    private void addItemDecoration() {
        PokemonDecorator decorator = new PokemonDecorator(15);
        recyclerView.addItemDecoration(decorator);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);

        PokemonInfo pokemonInfoElement = this.pokemonInfo.get(position);
        intent.putExtra("name", pokemonInfoElement.getName());
        intent.putExtra("imageUrl", pokemonInfoElement.getImageUrl());
        intent.putExtra("hp", pokemonInfoElement.getHp());
        intent.putExtra("moves", pokemonInfoElement.getMoves());

        startActivity(intent);
    }


    @SuppressLint("StaticFieldLeak")
    private class DownloadPokemonInfo extends AsyncTask<String, Void, Void> implements Serializable {
        protected Void doInBackground(String... urls) {
            try {
                run(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @SuppressLint("NotifyDataSetChanged")
        protected void onPostExecute(Void result) {
            adapter.notifyDataSetChanged();
        }

        private void run(String url) throws IOException {
            Request request = new Request.Builder().url(url).build();
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();

            String responseBody = getResponseBody(response);
            try {
                JSONArray jsonQuestions = getJsonArrayData(responseBody);
                ArrayList<PokemonInfo> pokemonInfos = getPokemonInfos(jsonQuestions);
                preparePokemonInfo(pokemonInfos);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            }

        @NonNull
        private String getResponseBody(Response response) throws IOException {
            String responseBody = "";
            if (response.isSuccessful()) {
                responseBody = Objects.requireNonNull(response.body()).string();
                response.close();
            }

            return responseBody;
        }

        @NonNull
        private JSONArray getJsonArrayData(String responseBody) throws JSONException {
            JSONObject jsonResponse = new JSONObject(responseBody);
            return jsonResponse.getJSONArray("data");
        }

        private ArrayList<PokemonInfo> getPokemonInfos(JSONArray jsonQuestions) throws JSONException {
            ArrayList<PokemonInfo> pokemonInfos = new ArrayList<>();
            for (int i = 0; i < jsonQuestions.length(); i++) {
                JSONObject jsonQuestion = jsonQuestions.getJSONObject(i);
                PokemonInfo pokemonInfo = parsePokemonInfo(jsonQuestion);
                pokemonInfos.add(pokemonInfo);
            }

            return pokemonInfos;
        }

        private void preparePokemonInfo(ArrayList<PokemonInfo> pokemonInfos) {
            pokemonInfo = pokemonInfos;
            Collections.sort(pokemonInfo);
        }

        private PokemonInfo parsePokemonInfo(JSONObject jsonQuestion) throws JSONException {
            PokemonInfo pokemonInfo = new PokemonInfo();
            pokemonInfo.setNumber(jsonQuestion.getJSONArray("nationalPokedexNumbers").getInt(0));
            pokemonInfo.setName(jsonQuestion.getString("name"));
            pokemonInfo.setHp(jsonQuestion.getInt("hp"));
            pokemonInfo.setImageUrl(jsonQuestion.getJSONObject("images").getString("small"));

            ArrayList<PokemonMove> moves = parsePokemonMoves(jsonQuestion);
            pokemonInfo.setMoves(moves);

            return pokemonInfo;
        }

        private ArrayList<PokemonMove> parsePokemonMoves(JSONObject jsonQuestion) throws JSONException {
            if (!jsonQuestion.has("attacks"))
                return null;

            JSONArray jsonQuestions = jsonQuestion.getJSONArray("attacks");
            ArrayList<PokemonMove> pokemonMoves = new ArrayList<>();
            for (int i = 0; i < jsonQuestions.length(); i++) {
                jsonQuestion = jsonQuestions.getJSONObject(i);
                PokemonMove pokemonMove = parsePokemonMove(jsonQuestion);
                pokemonMoves.add(pokemonMove);
            }

            return pokemonMoves;
        }

        private PokemonMove parsePokemonMove(JSONObject jsonObject) throws JSONException {
            PokemonMove pokemonMove = new PokemonMove();

            String name = getName(jsonObject);
            pokemonMove.setName(name);

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
        private String getPower(JSONObject jsonObject) throws JSONException {
            String power = jsonObject.getString("damage");
            if (power.equals(""))
                power = "-";

            return power;
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
    }
}