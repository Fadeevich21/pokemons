package com.example.pokemons.ui;

import static com.example.pokemons.parse.PokemonParser.getJsonArrayData;
import static com.example.pokemons.parse.PokemonParser.getPokemonInfos;

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
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pokemons.MyApplication;
import com.example.pokemons.activities.DetailActivity;
import com.example.pokemons.database.AppDatabase;
import com.example.pokemons.database.entities.PokemonAndPokemonAttackEntity;
import com.example.pokemons.database.entities.PokemonAttackEntity;
import com.example.pokemons.database.entities.PokemonEntity;
import com.example.pokemons.network.NetworkConnect;
import com.example.pokemons.pokemon.PokemonInfo;
import com.example.pokemons.pokemon.PokemonAdapter;
import com.example.pokemons.pokemon.PokemonDecorator;
import com.example.pokemons.R;
import com.example.pokemons.RecyclerViewInterface;
import com.example.pokemons.pokemon_attack.PokemonAttack;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HomeFragment extends Fragment implements RecyclerViewInterface {
    private ArrayList<PokemonEntity> pokemonEntities = new ArrayList<>();
    private RecyclerView recyclerView;
    private PokemonAdapter adapter;
    private SearchView searchView;
    private DownloadPokemonInfo downloadPokemonInfo;

    private boolean isParsed = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createAdapter();
        if (savedInstanceState != null) {
            pokemonEntities = (ArrayList<PokemonEntity>) savedInstanceState.getSerializable("pokemon_info");
            isParsed = savedInstanceState.getBoolean("is_parsed");
        }

        downloadPokemonInfo = new DownloadPokemonInfo();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!isParsed) {
            isParsed = true;
            if (NetworkConnect.isConnected()) {
                Toast.makeText(MyApplication.getAppContext(), "Network is connected", Toast.LENGTH_SHORT).show();
                downloadPokemonInfo.execute("https://api.pokemontcg.io/v2/cards");
            } else {
                Toast.makeText(MyApplication.getAppContext(), "Network is not connected", Toast.LENGTH_SHORT).show();
                new LoadingFromDatabase().execute();
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("pokemon_info", pokemonEntities);
        outState.putBoolean("is_parsed", isParsed);
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
        if (pokemonEntities.isEmpty())
            return;

        ArrayList<PokemonEntity> filteredList = new ArrayList<>();
        for (PokemonEntity item : pokemonEntities) {
            if (item.name.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty())
            Toast.makeText(getActivity(), "No Data Found..", Toast.LENGTH_SHORT).show();
        else
            adapter.filterList(filteredList);
    }

    private void setLayoutManager(View view) {
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    private void createAdapter() {
        adapter = new PokemonAdapter(pokemonEntities, HomeFragment.this);
    }

    private void addItemDecoration() {
        PokemonDecorator decorator = new PokemonDecorator(15);
        recyclerView.addItemDecoration(decorator);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);

        PokemonEntity pokemonEntity = this.adapter.getPokemonEntities().get(position);
        intent.putExtra("pokemon_id", pokemonEntity.pokemonId);

        startActivity(intent);
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadingFromDatabase extends AsyncTask<Void, Void, List<PokemonEntity>> {
        @Override
        protected List<PokemonEntity> doInBackground(Void... voids) {
            AppDatabase db = Room.databaseBuilder(MyApplication.getAppContext(),
                    AppDatabase.class, "database-name").fallbackToDestructiveMigration()
                    .build();

            return db.pokemonDao().getAll();
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void onPostExecute(List<PokemonEntity> result) {
            pokemonEntities.clear();
            pokemonEntities.addAll(result);
            adapter.notifyDataSetChanged();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadPokemonInfo extends AsyncTask<String, Void, ArrayList<PokemonEntity>> {
        protected ArrayList<PokemonEntity> doInBackground(String... urls) {
            try {
                return run(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void onPostExecute(ArrayList<PokemonEntity> result) {
            pokemonEntities.clear();
            pokemonEntities.addAll(result);
            adapter.notifyDataSetChanged();
        }

        private ArrayList<PokemonEntity> run(String url) throws IOException {
            Request request = new Request.Builder().url(url).build();
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();

            String responseBody = getResponseBody(response);
            ArrayList<PokemonInfo> pokemonInfos;
            AppDatabase db = Room.databaseBuilder(MyApplication.getAppContext(),
                    AppDatabase.class, "database-name").fallbackToDestructiveMigration()
                    .build();

            db.clearAllTables();

            try {
                JSONArray jsonQuestions = getJsonArrayData(responseBody);
                pokemonInfos = getPokemonInfos(jsonQuestions);
                preparePokemonInfo(pokemonInfos);

                for (PokemonInfo pokemonInfo : pokemonInfos) {
                    PokemonEntity pokemonEntity = new PokemonEntity(UUID.randomUUID().toString(),
                            pokemonInfo.getName(), pokemonInfo.getImageUrl(),
                            pokemonInfo.getNumber(), pokemonInfo.getHp());
                    db.pokemonDao().insert(pokemonEntity);

                    for (PokemonAttack pokemonAttack : pokemonInfo.getMoves()) {
                        PokemonAttackEntity pokemonAttackEntity = new PokemonAttackEntity(
                                UUID.randomUUID().toString(), pokemonAttack.getName(),
                                pokemonAttack.getPower(), pokemonAttack.getType());
                        db.pokemonAttackDao().insert(pokemonAttackEntity);

                        PokemonAndPokemonAttackEntity pokemonAndPokemonAttackEntity =
                                new PokemonAndPokemonAttackEntity(UUID.randomUUID().toString(),
                                        pokemonEntity.pokemonId,
                                        pokemonAttackEntity.pokemonAttackId);
                        db.pokemonAndPokemonAttackDao().insert(pokemonAndPokemonAttackEntity);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return (ArrayList<PokemonEntity>) db.pokemonDao().getAll();
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

        private void preparePokemonInfo(ArrayList<PokemonInfo> pokemonInfos) {
            Collections.sort(pokemonInfos);
        }
    }
}