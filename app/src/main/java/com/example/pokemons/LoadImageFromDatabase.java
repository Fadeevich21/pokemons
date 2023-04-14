package com.example.pokemons;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.room.Room;

import com.example.pokemons.database.AppDatabase;
import com.example.pokemons.database.entities.PokemonImageEntity;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@SuppressLint("StaticFieldLeak")
public class LoadImageFromDatabase extends AsyncTask<String, Void, Bitmap> {
    private final ImageView imageView;

    private ProgressBar progressBar;

    public LoadImageFromDatabase(ImageView imageView) {
        this.imageView = imageView;
    }

    public LoadImageFromDatabase(ImageView imageView, ProgressBar progressBar) {
        this.imageView = imageView;
        this.progressBar = progressBar;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String pokemonId = strings[0];

        AppDatabase db = Room.databaseBuilder(MyApplication.getAppContext(),
                        AppDatabase.class, "database-name").fallbackToDestructiveMigration()
                .build();

        if (db.pokemonDao().findById(pokemonId) == null)
            return null;

        List<String> imageIds = db.pokemonAndPokemonImageDao().getAllPokemonImageIds(pokemonId);
        if (imageIds == null || imageIds.size() == 0)
            return null;

        String imageId = imageIds.get(0);
        PokemonImageEntity pokemonImageEntity = db.pokemonImageDao().findById(imageId);

        Bitmap image;
        FileInputStream inputStream;
        try {
            inputStream = MyApplication.getAppContext().openFileInput(pokemonImageEntity.image);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            image = BitmapFactory.decodeStream(bufferedInputStream);
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return image;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);

        imageView.setImageBitmap(bitmap);
        imageView.setVisibility(View.VISIBLE);
    }
}
