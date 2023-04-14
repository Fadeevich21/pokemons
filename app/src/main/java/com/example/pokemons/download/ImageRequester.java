package com.example.pokemons.download;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import androidx.room.Room;

import com.example.pokemons.MyApplication;
import com.example.pokemons.database.AppDatabase;
import com.example.pokemons.database.entities.PokemonAndPokemonImageEntity;
import com.example.pokemons.database.entities.PokemonEntity;
import com.example.pokemons.database.entities.PokemonImageEntity;

public class ImageRequester extends AsyncTask<String, Void, Bitmap> {
    private String pokemonId;

    public void execute(String urlString, String pokemonId) {
        this.pokemonId = pokemonId;
        this.execute(urlString);
    }

    protected Bitmap doInBackground(String... urls) {
        Bitmap image = loadImageFromSite(urls[0]);
        if (image == null)
            return null;

        String filename = UUID.randomUUID().toString() + ".jpg";
        FileOutputStream outputStream;
        try {
            outputStream = MyApplication.getAppContext().openFileOutput(filename, Context.MODE_PRIVATE);
            image.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        AppDatabase db = Room.databaseBuilder(MyApplication.getAppContext(),
                        AppDatabase.class, "database-name").fallbackToDestructiveMigration()
                .build();

        if (db.pokemonDao().findById(pokemonId) == null)
            return image;

        List<String> imageIds = db.pokemonAndPokemonImageDao().getAllPokemonImageIds(pokemonId);
        if (imageIds != null && imageIds.size() != 0)
            return image;

        String imageId = UUID.randomUUID().toString();
        db.pokemonImageDao().insert(new PokemonImageEntity(imageId, filename));
        db.pokemonAndPokemonImageDao().insert(new PokemonAndPokemonImageEntity(
                UUID.randomUUID().toString(), pokemonId, imageId));

        return image;
    }

    protected void onPostExecute(Bitmap result) {
    }

    private Bitmap loadImageFromSite(String urlString) {
        URL url;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream inputstream = conn.getInputStream();
            return BitmapFactory.decodeStream(inputstream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}